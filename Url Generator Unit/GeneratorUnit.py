import asyncio
from time import (
    time, sleep
)
from signal import (
    signal, SIGINT, SIG_DFL
)
import datetime


# This kind of representation of the URLs and their "fetch-count per 5 minutes" is
# temporary. These data will be extracted from the database in future commits.
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

# This for loop adds a new key to each rss's dict (i.e.: last_process_time).
# We'll use this key-value pair to schedule the insertion of each url in the
# RabbitMQ exchange.
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

print(rss_container)


def gracefully_exit_handler(sig, frame):
    """Closes the database and stops the generator gracefully.

    Args:
        sig (int): The signal id this handler is supposed to handle. In our
        case, it is the interrupt signal (2).

        frame (frame): The stack frame is stored in this variable. We don't need
        to use it.
    """
    # TODO: Close database connection and stop generator
    print('Database connection closed and generator stopped.')
    quit()


# Handling the interrupt signal.
signal(SIGINT, gracefully_exit_handler)


async def generator():
    """The while loop in this function will be executed in certain times
    and sends the processable URLs to a RabbitMQ exchange to be received by
    "URL Fetching Unit".
    """
    while True:
        for index, rss_info in enumerate(rss_container):
            if time() - rss_info['last_process_time'] > rss_info['next_fetch_happens_in']:
                rss_info['last_process_time'] = time()
                print(f'[{datetime.datetime.fromtimestamp(time()).strftime("%Y-%m-%d %H:%M:%S")}] \
> Putting the url number {index} in the RabbitMQ\'s exchange.')
        await asyncio.sleep(0.5)


loop = asyncio.new_event_loop()
loop.run_until_complete(generator())
