import asyncio
import requests
import aiohttp
import logging
import re
from aio_pika import (
    connect as rabbit_mq_connector,
    Message as rabbit_mq_message,
    DeliveryMode as rabbit_mq_delivery_mode,
    ExchangeType as rabbit_mq_exchange_type,
)
from aio_pika.exceptions import ChannelClosed as rabbit_mq_does_not_exists_exception
from time import (
    time, sleep
)
from signal import (
    signal, SIGINT, SIG_DFL
)
import datetime
from threading import Event

# Making asyncio.run work although there is an running event loop in the
# current thread.
# import nest_asyncio
# nest_asyncio.apply()

rabbit_mq_conn = None
shutdown_command_issued = False
shutdown_event = asyncio.Event()

running_on_docker = True
localhost_addr = 'host.docker.internal' if running_on_docker else 'localhost'

available_workers = dict()
logging.basicConfig(format='%(asctime)s - %(levelname)s:  %(message)s', datefmt='%d-%b-%y %H:%M:%S', level=logging.INFO)

def get_workers_list(user='guest', password='guest', host=localhost_addr, port=18010, virtual_host=None):
    url = 'http://%s:%s/api/queues/%s' % (host, port, virtual_host or '')
    response = requests.get(url, auth=(user, password))
    queues = [q['name'] for q in response.json()]
    return queues

async def get_workers_list_async(user='guest', password='guest', host=localhost_addr, port=18010, virtual_host=None):
    async with aiohttp.ClientSession() as session:
        url = 'http://%s:%s/api/queues/%s' % (host, port, virtual_host or '')
        async with session.get(url, auth=aiohttp.BasicAuth(user, password)) as response:
            workers = [worker['name'] for worker in await response.json() if 'fetch_worker' in worker['name']]
            return workers


async def add_fetch_worker(user='guest', password='guest', host=localhost_addr, port=18010, virtual_host=None):
    global available_workers
    url = 'http://%s:%s/api/queues/%s' % (host, port, virtual_host or '')
    while True:
        async with aiohttp.ClientSession() as session:
            async with session.get(url, auth=aiohttp.BasicAuth(user, password)) as response:
                result = await response.json()
                workers = [worker['name'] for worker in result]
                for worker in workers:
                    if 'fetch_worker' in worker:
                        if worker not in available_workers.keys():
                            logging.info(f'A new fetch worker joined to the system: "{worker}"')
                            available_workers.update(
                                {
                                    worker: dict()
                                }
                            )
        await asyncio.sleep(3)


async def check_worker_is_alive():
    rabbit_mq_channel = await rabbit_mq_conn.channel()
    try:
        await rabbit_mq_channel.declare_queue('serialized_not_processed_xmls', durable=True)
    except rabbit_mq_does_not_exists_exception:
        return 
    

async def choose_worker(url):
    global available_workers
    pattern = r'^(?:https?:\/\/)?(?:[^@\/\n]+@)?(?:www\.)?([^:\/\n]+)'
    domain = re.search(
        pattern, url).group(1)
    fetch_intervals_per_domain = 5  # 10 Minutes
    # available_workers = { worker: { 'domain': 'last_fetch_time' } }
    temp_available_workers = available_workers.copy()
    for worker, worker_info in available_workers.items():
        if not worker in await get_workers_list_async():
            del temp_available_workers[worker]
            logging.error(f'The worker "{worker}" has been disconnected.')
            continue
        if domain in available_workers[worker].keys():
            if time() - available_workers[worker][domain] > fetch_intervals_per_domain:
                if worker in await get_workers_list_async():
                    return worker
            else:
                continue
        else:
            return worker
    available_workers = temp_available_workers
    return None


# This kind of representation of the URLs and their "fetch-count per 5 minutes"
# is temporary. These data will be extracted from the database in future
# commits.
rss_container = [
    {
        'url': 'https://www.isna.ir/rss/tp/149',
        'fetch_counts_per_five_minutes': 300,
        'priority': 1
    },
    {
        'url': 'https://fararu.com/fa/rss',
        'fetch_counts_per_five_minutes': 150,
        'priority': 1
    },
    {
        'url': 'https://www.shahrekhabar.com/rssfeeds',
        'fetch_counts_per_five_minutes': 10,
        'priority': 2
    },
    {
        'url': 'https://www.khabarvarzeshi.com/rss-help',
        'fetch_counts_per_five_minutes': 1,
        'priority': 2
    },
    {
        'url': 'https://www.khabaronline.ir/rss-help',
        'fetch_counts_per_five_minutes': 3,
        'priority': 3
    },
]


# This for loop adds two new keys to each rss's dict (i.e.: last_process_time).
# We'll use these newly added key-value pairs to schedule the insertion of each
# url correctly in the RabbitMQ exchange.
for rss_info in rss_container:

    # Setting the rss_info type to dict so we can get the IDE's recommended
    # methods for python dictionaries.
    rss_info: dict

    rss_info.update(
        {
            # Storing current time in Unix time.
            'last_process_time': time(),
            # For a correct scheduling, we need to know when the next insertion
            # to the exchange happens (in seconds).
            'next_fetch_happens_in': 300 / rss_info['fetch_counts_per_five_minutes']
        }
    )

rss_container.sort(key=lambda x: x['priority'], reverse=True)

async def gracefully_async_exit_handler():
    await shutdown_event.wait()


def gracefully_sync_exit_handler(sig, frame):
    """Closes the database and stops the generator gracefully.

    Args:
        sig (int): The signal id this handler is supposed to handle. In our
        case, it is the interrupt signal (2).

        frame (frame): The stack frame is stored in this variable. We don't need
        to use it.
    """
    # TODO: Close database connection and stop generator
    global shutdown_command_issued, shutdown_event
    shutdown_command_issued = True
    asyncio.run(gracefully_async_exit_handler())
    quit()


# # Handling the interrupt signal.
# signal(SIGINT, gracefully_sync_exit_handler)


async def initializer():
    global rabbit_mq_conn
    rabbit_mq_conn = await rabbit_mq_connector(f"amqp://guest:guest@{localhost_addr}:18009/")


async def rss_container_updater():
    ...


async def generator():
    """The while loop in this function will be executed in certain times
    and sends the processable URLs to a RabbitMQ exchange to be received by
    "URL Fetching Unit".
    """
    global shutdown_command_issued, shutdown_event, available_workers
    async with rabbit_mq_conn:
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
            logging.info('A new rabbit mq exchange created: news_aggregator_direct')
        else:
            rabbit_mq_direct_exchange = await rabbit_mq_channel.declare_exchange('news_aggregator_direct', rabbit_mq_exchange_type.DIRECT, durable=True)
        pattern = r'^(?:https?:\/\/)?(?:[^@\/\n]+@)?(?:www\.)?([^:\/\n]+)'
        while True:
            if not shutdown_command_issued:
                shutdown_command_issued = False
                for index, rss_info in enumerate(rss_container):
                    if time() - rss_info['last_process_time'] > rss_info['next_fetch_happens_in']:
                        rss_info['last_process_time'] = time()
                        if chosen_worker:=await choose_worker(rss_info['url']):
                            await rabbit_mq_direct_exchange.publish(
                                rabbit_mq_message(
                                    bytes(rss_info['url'], encoding='utf-8'),
                                    delivery_mode=rabbit_mq_delivery_mode.PERSISTENT
                                ),
                                routing_key=chosen_worker
                            )
                            temp_dict = available_workers[chosen_worker].copy()
                            temp_dict.update(
                                {
                                    re.search(pattern, rss_info['url']).group(1): time()
                                }
                            )
                            available_workers[chosen_worker] = temp_dict
                            logging.info(f'The url "{rss_info["url"]}" putted and will be fetched by the worker "{chosen_worker}" soon.')
                        else:
                            logging.critical(f'There is no ready worker to fetch the url "{rss_info["url"]}".')
                await asyncio.sleep(0.5)
            else:
                break
    logging.info('The connection with the RabbitMQ node closed.')
    shutdown_event.set()


loop = asyncio.new_event_loop()
loop.run_until_complete(initializer())
loop.create_task(generator())
loop.create_task(add_fetch_worker())
loop.run_forever()
