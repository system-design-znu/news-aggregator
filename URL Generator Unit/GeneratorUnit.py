import asyncio
import requests
import aiohttp
import logging
import re
import asyncpg
import openpyxl
import pathlib
import hashlib
import time
import configparser
from aio_pika import (
    connect as rabbit_mq_connector,
    Message as rabbit_mq_message,
    DeliveryMode as rabbit_mq_delivery_mode,
    ExchangeType as rabbit_mq_exchange_type,
)
from aio_pika.exceptions import ChannelClosed as rabbit_mq_does_not_exists_exception
import time
from signal import (
    signal, SIGINT, SIG_DFL
)

config = configparser.ConfigParser()
config.read('config.ini')
db_user = config['postgres']['user']
db_pass = config['postgres']['pass']
db_port = config['postgres']['port']
db_table_name = config['postgres']['table_name']
rabbit_mq_host = 'rabbit_mq'
postgres_host = 'postgres_db'
database_conn = None
old_excel_file_hash = None
excel_file_path = str(pathlib.Path(__file__).parent.resolve()) + "/RSS.xlsx"
initializer_finish_event, change_excel_trigger_event, shutdown_event, update_rss_container_trigger_event, generator_finish_work_event = [asyncio.Event() for i in range(5)]
rabbit_mq_conn = None
shutdown_command_issued = False
# is_running_in_docker = True if config['docker']['is_running_in_docker'] == 'True' else False
# localhost_addr = 'host.docker.internal' if is_running_in_docker else 'localhost'
available_workers, rss_container = dict(), list()
logging.basicConfig(format='%(asctime)s - %(levelname)s:  %(message)s', datefmt='%d-%b-%y %H:%M:%S', level=logging.INFO)


async def initializer():
    global rabbit_mq_conn
    global database_conn
    while True:
        try:
            rabbit_mq_conn = await rabbit_mq_connector(f"amqp://guest:guest@{rabbit_mq_host}:5672/")
        except:
            continue
        else:
            break
    try:
        database_conn = await asyncpg.connect(user=db_user, password=db_pass, host=postgres_host, port=db_port, database=db_table_name)
    except asyncpg.exceptions.InvalidCatalogNameError:
        conn = await asyncpg.connect(user=db_user, password=db_pass, host=postgres_host)
        values = await conn.execute(
        	f'''
        		CREATE DATABASE {db_table_name};
        	'''
		)
        await conn.close()
        database_conn = await asyncpg.connect(user=db_user, password=db_pass, host=postgres_host, port=db_port, database=db_table_name)
    logging.info('Initialization finished.')
    initializer_finish_event.set()


async def excel_file_hash_checker():
    await initializer_finish_event.wait()
    global old_excel_file_hash
    while True:
        md5 = hashlib.md5()
        BUF_SIZE = 65536
        with open(excel_file_path, 'rb') as f:
            start_time = time.time()
            while True:
                data = f.read(BUF_SIZE)
                if not data:
                    break
                md5.update(data)
                if time.time() - start_time > 0.1:
                    await asyncio.sleep(0)

        if old_excel_file_hash == None or old_excel_file_hash.hexdigest() != md5.hexdigest():
            logging.info('An update for the RSS urls is required because the hash of excel file containing the urls changed recently.')
            old_excel_file_hash = md5
            change_excel_trigger_event.set()
        await asyncio.sleep(1)

    
def get_data_from_excel():
    dataframe = openpyxl.load_workbook(excel_file_path)
    dataframe1 = dataframe.active
    rows_to_write = list()
    for index, row in enumerate(range(0, dataframe1.max_row)):
        if index == 0:
            continue
        temp_list = list()
        for index, col in enumerate(dataframe1.iter_cols(1, dataframe1.max_column)):
            if index in [1, 4]:
                if index == 4:
                    temp_list.append(60 / float(col[row].value))
                else:
                    temp_list.append(col[row].value)
        rows_to_write.append(temp_list)
    return rows_to_write
    
    
async def update_urls_table():
    await initializer_finish_event.wait()
    while True:
        await change_excel_trigger_event.wait()
        if not update_rss_container_trigger_event.is_set():
            logging.info('RSS table update is in progress...')
            await database_conn.execute(
                '''
                    DROP TABLE IF EXISTS public.rss_fetch;
                    CREATE TABLE public.rss_fetch (
                    url varchar NOT NULL,
                    fetch_count_per_sixty_minutes varchar NOT NULL);
                '''
            )
            rows_to_write = await asyncio.get_running_loop().run_in_executor(None, get_data_from_excel)
            await database_conn.execute(
                '''
                    INSERT INTO public.rss_fetch (url,fetch_count_per_sixty_minutes)
                    VALUES {}
                '''.format(''.join(["('{}', '{}'),".format(i, j) for i, j in rows_to_write])[:-1] + ';')
            )
            change_excel_trigger_event.clear()
            # rss container not can be updated through database
            update_rss_container_trigger_event.set()
            logging.info('RSS table update finished.')


def get_workers_list(user='guest', password='guest', host=rabbit_mq_host, port=15672, virtual_host=None):
    url = 'http://%s:%s/api/queues/%s' % (host, port, virtual_host or '')
    response = requests.get(url, auth=(user, password))
    queues = [q['name'] for q in response.json()]
    return queues


async def get_workers_list_async(user='guest', password='guest', host=rabbit_mq_host, port=15672, virtual_host=None):
    async with aiohttp.ClientSession() as session:
        url = 'http://%s:%s/api/queues/%s' % (host, port, virtual_host or '')
        async with session.get(url, auth=aiohttp.BasicAuth(user, password)) as response:
            workers = [worker['name'] for worker in await response.json() if 'fetch_worker' in worker['name']]
            return workers


async def add_fetch_worker(user='guest', password='guest', host=rabbit_mq_host, port=15672, virtual_host=None):
    await initializer_finish_event.wait()
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
    await initializer_finish_event.wait()
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
            if time.time() - available_workers[worker][domain] > fetch_intervals_per_domain:
                if worker in await get_workers_list_async():
                    return worker
            else:
                continue
        else:
            return worker
    available_workers = temp_available_workers
    return None

async def rss_container_updater():
    await initializer_finish_event.wait()
    global rss_container
    while True:
        await update_rss_container_trigger_event.wait()
        rss_container = list()
        received_rss_data_from_database = await database_conn.fetch(
            '''
                SELECT url, fetch_count_per_sixty_minutes
                FROM public.rss_fetch;
            '''
        )
        for record in received_rss_data_from_database:
            temp_list = []
            for value in record.values():
                temp_list.append(value)
            rss_container.append(
                {
                    'url': temp_list[0],
                    # 'fetch_counts_per_sixty_minutes': float(temp_list[1]),
                    'fetch_counts_per_sixty_minutes': 60,
                    'priority': temp_list[0].count('/')
                }
            )
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
                    'last_process_time': time.time(),
                    # For a correct scheduling, we need to know when the next insertion
                    # to the exchange happens (in seconds).
                    'next_fetch_happens_in': 3600 / rss_info['fetch_counts_per_sixty_minutes']
                }
            )
        rss_container.sort(key=lambda x: x['priority'], reverse=True)
        update_rss_container_trigger_event.clear()
        await asyncio.sleep(0.1)

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


async def generator():
    await initializer_finish_event.wait()
    """The while loop in this function will be executed in certain times
    and sends the processable URLs to a RabbitMQ exchange to be received by
    "URL Fetching Unit".
    """
    sleep_time = 2
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
                # if the rss container is not under an update
                if not update_rss_container_trigger_event.is_set():
                    if rss_container is not None:
                        for index, rss_info in enumerate(rss_container):
                            if time.time() - rss_info['last_process_time'] > rss_info['next_fetch_happens_in']:
                                rss_info['last_process_time'] = time.time()
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
                                            re.search(pattern, rss_info['url']).group(1): time.time()
                                        }
                                    )
                                    available_workers[chosen_worker] = temp_dict
                                    logging.info(f'The url "{rss_info["url"]}" putted and will be fetched by the worker "{chosen_worker}" soon.')
                                else:
                                    logging.critical(f'There is no ready worker to fetch the url "{rss_info["url"]}".')
                            else: await asyncio.sleep(sleep_time)
                        await asyncio.sleep(sleep_time)
                    else: await asyncio.sleep(sleep_time)
                else: await asyncio.sleep(sleep_time)
            else: break
    logging.info('The connection with the RabbitMQ node closed.')
    shutdown_event.set()


loop = asyncio.new_event_loop()
loop.create_task(initializer())
loop.create_task(excel_file_hash_checker())
loop.create_task(update_urls_table())
loop.create_task(rss_container_updater())
loop.create_task(generator())
loop.create_task(add_fetch_worker())
loop.run_forever()
