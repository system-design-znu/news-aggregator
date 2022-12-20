import asyncio
from time import (
    time, sleep
)
from signal import (
    signal, SIGINT, SIG_DFL
)


def gracefully_exit_handler(sig, frame):
    # TODO: Close database connection and stop generator
    print('Database connection closed and generator stopped.')
    quit()

signal(SIGINT, gracefully_exit_handler)

async def generator():
    while True:
        print('Generator Works!')
        await asyncio.sleep(1)


loop = asyncio.get_event_loop()
loop.run_until_complete(generator())
