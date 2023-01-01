import asyncio
import datetime
import aiohttp
import pickle
from aio_pika import (
    connect as rabbit_mq_connector,
    Message as rabbit_mq_message,
    DeliveryMode as rabbit_mq_delivery_mode,
    ExchangeType as rabbit_mq_exchange_type,
)
from aio_pika.exceptions import ChannelClosed as rabbit_mq_exchange_does_not_exists_exception
from aio_pika.abc import AbstractIncomingMessage
from aiormq.exceptions import ChannelInvalidStateError
from time import (
    time, sleep
)
from signal import (
    signal, SIGINT, SIG_DFL
)
from threading import Event


rabbit_mq_conn, rabbit_mq_conn_two, shutdown_future = [
    None for i in range(3)]
local_async_queue = asyncio.Queue()


async def initializer():
    global rabbit_mq_conn, rabbit_mq_conn_two
    rabbit_mq_conn = await rabbit_mq_connector("amqp://guest:guest@localhost/")


async def on_message(message: AbstractIncomingMessage) -> None:
    async with message.process():
        await local_async_queue.put(message.body.decode('utf-8'))


async def url_job_receiver():
    """The while loop in this function will be executed in certain times
    and sends the processable URLs to a RabbitMQ exchange to be received by
    "URL Fetching Unit".
    """
    async with rabbit_mq_conn:
        rabbit_mq_channel = await rabbit_mq_conn.channel()
        await rabbit_mq_channel.set_qos(prefetch_count=1)
        try:
            # Checking whether the exchange we want to use exists. If not, an
            # error will be thrown by RabbitMQ. We catch that, refresh the
            # channel (because it will be closed when an exception occurs) and
            # create a new exchange.
            await rabbit_mq_channel.declare_exchange('news_aggregator_direct', rabbit_mq_exchange_type.DIRECT, durable=True, passive=True)
        except rabbit_mq_exchange_does_not_exists_exception:
            rabbit_mq_channel = await rabbit_mq_conn.channel()
            rabbit_mq_direct_exchange = await rabbit_mq_channel.declare_exchange('news_aggregator_direct', rabbit_mq_exchange_type.DIRECT, durable=True)
            print('[Exchange Created] => news_aggregator_direct')
        else:
            rabbit_mq_direct_exchange = await rabbit_mq_channel.declare_exchange('news_aggregator_direct', rabbit_mq_exchange_type.DIRECT, durable=True)
        rabbit_mq_urls_queue = await rabbit_mq_channel.declare_queue('fetchable_url', durable=True)
        await rabbit_mq_urls_queue.bind(rabbit_mq_direct_exchange, routing_key='fetchable_url')
        await rabbit_mq_urls_queue.consume(on_message)
        await asyncio.Future()


async def url_fetch_worker():
    rabbit_mq_channel = await rabbit_mq_conn.channel()
    try:
        # Checking whether the exchange we want to use exists. If not, an
        # error will be thrown by RabbitMQ. We catch that, refresh the
        # channel (because it will be closed when an exception occurs) and
        # create a new exchange.
        await rabbit_mq_channel.declare_exchange('news_aggregator_direct', rabbit_mq_exchange_type.DIRECT, durable=True, passive=True)
    except rabbit_mq_exchange_does_not_exists_exception:
        rabbit_mq_channel = await rabbit_mq_conn.channel()
        rabbit_mq_direct_exchange = await rabbit_mq_channel.declare_exchange('news_aggregator_direct', rabbit_mq_exchange_type.DIRECT, durable=True)
        print('[Exchange Created] => news_aggregator_direct')
    else:
        rabbit_mq_direct_exchange = await rabbit_mq_channel.declare_exchange('news_aggregator_direct', rabbit_mq_exchange_type.DIRECT, durable=True)
    rabbit_mq_urls_queue = await rabbit_mq_channel.declare_queue('serialized_not_processed_xmls', durable=True)
    await rabbit_mq_urls_queue.bind(rabbit_mq_direct_exchange, routing_key='serialized_not_processed_xmls')
    while True:
        ready_url = await local_async_queue.get()
        async with aiohttp.ClientSession() as session:
            async with session.get(ready_url) as response:
                received_response = await response.text()
                if '<?xml' in received_response[:10]:
                    try:
                        await rabbit_mq_direct_exchange.publish(
                            rabbit_mq_message(
                                pickle.dumps((await response.text())),
                                delivery_mode=rabbit_mq_delivery_mode.PERSISTENT
                            ),
                            routing_key=rabbit_mq_urls_queue.name
                        )
                    except ChannelInvalidStateError as e:
                        print(
                            'Re opening the channel...')
                        rabbit_mq_channel = await rabbit_mq_conn.channel()

loop = asyncio.new_event_loop()
loop.run_until_complete(initializer())
loop.create_task(url_job_receiver())
loop.run_until_complete(asyncio.sleep(2))
loop.create_task(url_fetch_worker())
loop.run_forever()
