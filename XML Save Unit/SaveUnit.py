import asyncio
import logging
import time
import motor.motor_asyncio
import aiohttp
import signal
import nest_asyncio
import pickle
from aio_pika import (
    connect as rabbit_mq_connector,
    Message as rabbit_mq_message,
    DeliveryMode as rabbit_mq_delivery_mode,
    ExchangeType as rabbit_mq_exchange_type,
)
from aio_pika.exceptions import ChannelClosed as rabbit_mq_exchange_does_not_exists_exception
from aio_pika.abc import AbstractIncomingMessage
nest_asyncio.apply()


rabbit_mq_conn, rabbit_mq_conn_two, shutdown_future, shutdown_future_two, urls_local_queues, saving_queue = [
    None for i in range(6)]

initialize_future = asyncio.Future()

logging.basicConfig(format='%(asctime)s - %(levelname)s:  %(message)s',
                    datefmt='%d-%b-%y %H:%M:%S', level=logging.INFO)


async def gracefully_async_exit_handler():
    await shutdown_future_two


def gracefully_sync_exit_handler(sig, frame):
    """Stops the fetch worker gracefully.
    Args:
        sig (int): The signal id this handler is supposed to handle. In our
        case, it is the interrupt signal (2).
        frame (frame): The stack frame is stored in this variable. We don't need
        to use it.
    """
    global shutdown_command_issued, shutdown_event, shutdown_future
    shutdown_future.set_result(0)
    asyncio.run(gracefully_async_exit_handler())
    quit()


signal.signal(signal.SIGINT, gracefully_sync_exit_handler)


async def initializer():
    global rabbit_mq_conn, rabbit_mq_conn_two, aiohttp_client, urls_local_queues, saving_queue
    rabbit_mq_conn = await rabbit_mq_connector("amqp://guest:guest@localhost/")
    rabbit_mq_conn_two = await rabbit_mq_connector("amqp://guest:guest@localhost/")
    urls_local_queues = asyncio.Queue()
    saving_queue = asyncio.Queue()
    initialize_future.set_result(0)
    print('initialize finished')


async def on_message(message: AbstractIncomingMessage) -> None:
    global shutdown_future_two
    async with message.process():
        await urls_local_queues.put(message.body)
        # print(f"[x] {message.body!r}")


async def url_job_receiver():
    global rabbit_mq_conn, shutdown_future, shutdown_future_two
    shutdown_future = asyncio.Future()
    shutdown_future_two = asyncio.Future()
    async with rabbit_mq_conn:
        rabbit_mq_channel = await rabbit_mq_conn.channel()
        await rabbit_mq_channel.set_qos(prefetch_count=1)
        rabbit_mq_urls_queue = await rabbit_mq_channel.declare_queue('serialized_not_processed_xmls', durable=True)
        await rabbit_mq_urls_queue.consume(on_message)
        await shutdown_future
    shutdown_future_two.set_result(0)


async def url_fetch_worker():
    await initialize_future
    print('fetch worker executed')
    while True:
        deserialized_xml = pickle.loads(await urls_local_queues.get())
        await saving_queue.put(deserialized_xml)
        print(deserialized_xml)
        print('=' * 50)
        urls_local_queues.task_done()


async def xml_save_worker():
    await initialize_future
    print('xml worker executed')
    client = motor.motor_asyncio.AsyncIOMotorClient('localhost', 27017)
    db = client.fetched_xmls
    collection = db.whole_data
    while True:
        ready_to_be_saved_xml = await saving_queue.get()
        await collection.insert_one(
            {
                "xml": ready_to_be_saved_xml,
                "save_epoch_time": time.time()
            }
        )
        saving_queue.task_done()

loop = asyncio.new_event_loop()
loop.run_until_complete(initializer())
loop.create_task(url_job_receiver())
loop.create_task(url_fetch_worker())
loop.create_task(xml_save_worker())
loop.run_forever()
