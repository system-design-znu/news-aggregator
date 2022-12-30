import asyncio
from aio_pika import (
    connect as rabbit_mq_connector,
    Message as rabbit_mq_message,
    DeliveryMode as rabbit_mq_delivery_mode,
    ExchangeType as rabbit_mq_exchange_type,
)
from aio_pika.exceptions import ChannelClosed as rabbit_mq_exchange_does_not_exists_exception
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
import nest_asyncio
nest_asyncio.apply()

rabbit_mq_conn = None
shutdown_command_issued = False
shutdown_event = asyncio.Event()

# This kind of representation of the URLs and their "fetch-count per 5 minutes"
# is temporary. These data will be extracted from the database in future
# commits.
rss_container = [
    {
        'url': 'https://www.isna.ir/rss-help',
        'fetch_counts_per_five_minutes': 300,
    },
    {
        'url': 'https://fararu.com/fa/rss',
        'fetch_counts_per_five_minutes': 150
    },
    {
        'url': 'https://www.shahrekhabar.com/rssfeeds',
        'fetch_counts_per_five_minutes': 10
    },
    {
        'url': 'https://www.khabarvarzeshi.com/rss-help',
        'fetch_counts_per_five_minutes': 1
    },
    {
        'url': 'https://www.khabaronline.ir/rss-help',
        'fetch_counts_per_five_minutes': 3
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


# Handling the interrupt signal.
signal(SIGINT, gracefully_sync_exit_handler)


async def initializer():
    global rabbit_mq_conn
    rabbit_mq_conn = await rabbit_mq_connector("amqp://guest:guest@localhost/")


async def rss_container_updater():
    ...


async def generator():
    """The while loop in this function will be executed in certain times
    and sends the processable URLs to a RabbitMQ exchange to be received by
    "URL Fetching Unit".
    """
    global shutdown_command_issued, shutdown_event
    async with rabbit_mq_conn:
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
        rabbit_mq_urls_queue = await rabbit_mq_channel.declare_queue('url', durable=True)
        await rabbit_mq_urls_queue.bind(rabbit_mq_direct_exchange, routing_key='url')
        while True:
            if not shutdown_command_issued:
                shutdown_command_issued = False
                for index, rss_info in enumerate(rss_container):
                    # print('this is the rss info0', rss_info)
                    if time() - rss_info['last_process_time'] > rss_info['next_fetch_happens_in']:
                        rss_info['last_process_time'] = time()
                        print(f'[{datetime.datetime.fromtimestamp(time()).strftime("%Y-%m-%d %H:%M:%S")}] \
> Putting the url {rss_info["url"]} in the RabbitMQ\'s exchange.')
                        await rabbit_mq_direct_exchange.publish(
                            rabbit_mq_message(
                                bytes(rss_info['url'], encoding='utf-8'),
                                delivery_mode=rabbit_mq_delivery_mode.PERSISTENT
                            ),
                            routing_key=rabbit_mq_urls_queue.name
                        )
                        print('sent')
                await asyncio.sleep(0.5)
            else:
                break
    print('The connection with the RabbitMQ node closed.')
    shutdown_event.set()


loop = asyncio.new_event_loop()
loop.run_until_complete(initializer())
loop.create_task(generator())
loop.run_forever()
