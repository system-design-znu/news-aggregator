import asyncio
import configparser
import datetime
import logging
import aiohttp
import pickle
import uuid
from aio_pika import (
    connect as rabbit_mq_connector,
    Message as rabbit_mq_message,
    DeliveryMode as rabbit_mq_delivery_mode,
    ExchangeType as rabbit_mq_exchange_type,
)
from aio_pika.exceptions import ChannelClosed as rabbit_mq_does_not_exists_exception
from aio_pika.abc import AbstractIncomingMessage
from aiormq.exceptions import ChannelInvalidStateError
from time import (
    time, sleep
)
from signal import (
    signal, SIGINT, SIG_DFL
)
from threading import Event

config = configparser.ConfigParser()
config.read('config.ini')
is_running_in_docker = True if config['docker']['is_running_in_docker'] == 'True' else False
localhost_addr = 'host.docker.internal' if is_running_in_docker else 'localhost'

rabbit_mq_conn, shutdown_future, connection_is_ready = [
    None for i in range(3)]
local_async_queue = asyncio.Queue()
worker_unique_id = 'fetch_worker_' + str(uuid.uuid4()).replace('-', '')
logging.basicConfig(format='%(asctime)s - %(levelname)s:  %(message)s',
                    datefmt='%d-%b-%y %H:%M:%S', level=logging.INFO)


async def initializer():
    global rabbit_mq_conn, connection_is_ready
    connection_is_ready = asyncio.Future()
    rabbit_mq_conn = await rabbit_mq_connector(f"amqp://guest:guest@{localhost_addr}:18009/")


# async def on_message(message: AbstractIncomingMessage) -> None:
#     async with message.process():


async def url_job_receiver():
    """The while loop in this function will be executed in certain times
    and sends the processable URLs to a RabbitMQ exchange to be received by
    "URL Fetching Unit".
    """
    async with rabbit_mq_conn:
        global connection_is_ready
        connection_is_ready.set_result(0)
        rabbit_mq_channel = await rabbit_mq_conn.channel()
        await rabbit_mq_channel.set_qos(prefetch_count=1)
        try:
            # Checking whether the exchange we want to use exists. If not, an
            # error will be thrown by RabbitMQ. We catch that, refresh the
            # channel (because it will be closed when an exception occurs) and
            # create a new exchange.
            await rabbit_mq_channel.declare_exchange('news_aggregator_direct', rabbit_mq_exchange_type.DIRECT, durable=True, passive=True)
        except rabbit_mq_does_not_exists_exception:
            rabbit_mq_channel = await rabbit_mq_conn.channel()
            rabbit_mq_direct_exchange = await rabbit_mq_channel.declare_exchange('news_aggregator_direct', rabbit_mq_exchange_type.DIRECT, durable=True)
            logging.info(
                'A new rabbit mq exchange created: news_aggregator_direct')
        else:
            rabbit_mq_direct_exchange = await rabbit_mq_channel.declare_exchange('news_aggregator_direct', rabbit_mq_exchange_type.DIRECT, durable=True)
        rabbit_mq_worker_queue = await rabbit_mq_channel.declare_queue(worker_unique_id, durable=True, exclusive=True)
        await rabbit_mq_worker_queue.bind(rabbit_mq_direct_exchange, routing_key=worker_unique_id)
        async with rabbit_mq_worker_queue.iterator() as iterator:
            async for message in iterator:
                async with message.process():
                    logging.info(
                        f'A new fetch job received: {message.body.decode("utf-8")}')
                    await local_async_queue.put(message.body.decode('utf-8'))


async def url_fetch_worker():
    await connection_is_ready
    rabbit_mq_channel = await rabbit_mq_conn.channel()
    try:
        # Checking whether the exchange we want to use exists. If not, an
        # error will be thrown by RabbitMQ. We catch that, refresh the
        # channel (because it will be closed when an exception occurs) and
        # create a new exchange.
        await rabbit_mq_channel.declare_exchange('news_aggregator_direct', rabbit_mq_exchange_type.DIRECT, durable=True, passive=True)
    except rabbit_mq_does_not_exists_exception:
        rabbit_mq_channel = await rabbit_mq_conn.channel()
        rabbit_mq_direct_exchange = await rabbit_mq_channel.declare_exchange('news_aggregator_direct', rabbit_mq_exchange_type.DIRECT, durable=True)
        logging.info(
            'A new rabbit mq exchange created: news_aggregator_direct')
    else:
        rabbit_mq_direct_exchange = await rabbit_mq_channel.declare_exchange('news_aggregator_direct', rabbit_mq_exchange_type.DIRECT, durable=True)
    rabbit_mq_urls_queue = await rabbit_mq_channel.declare_queue('serialized_not_processed_xmls', durable=True)
    await rabbit_mq_urls_queue.bind(rabbit_mq_direct_exchange, routing_key='serialized_not_processed_xmls')
    while True:
        ready_url = await local_async_queue.get()
        async with aiohttp.ClientSession() as session:
            try:
                async with session.get(ready_url) as response:
                    received_response = (await response.text()).strip()

                    if '<?xml' in received_response[:10]:
                        try:
                            await rabbit_mq_direct_exchange.publish(
                                rabbit_mq_message(
                                    pickle.dumps((await response.text())),
                                    delivery_mode=rabbit_mq_delivery_mode.PERSISTENT
                                ),
                                routing_key=rabbit_mq_urls_queue.name
                            )
                            logging.info(
                                f'The fetch operation for the url "{ready_url}" has been done.')
                        except ChannelInvalidStateError as e:
                            logging.critical(
                                'Channel is closed. Reopening...')
                            rabbit_mq_channel = await rabbit_mq_conn.channel()
                            logging.info('Channel opened again.')
                    else:
                        logging.error(
                            f'The provided url "{ready_url}" is not a valid rss. The fetch operation aborted.')
            except (aiohttp.ClientConnectorError, Exception) as e:
                logging.critical(f'Something went wrong during fetch for the url: "{ready_url}". Probably you have to check your internet connection.')
                    
loop = asyncio.new_event_loop()
loop.run_until_complete(initializer())
loop.create_task(url_job_receiver())
loop.create_task(url_fetch_worker())
loop.run_forever()
