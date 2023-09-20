from apscheduler.schedulers.blocking import BlockingScheduler
import sys
def myfunc():
    with open("sampletext.txt","a") as f:
        print("working...")
    

scheduler = BlockingScheduler()

job = scheduler.add_job(myfunc, 'interval', seconds=1)


scheduler.start()

# def run_program():
#     scheduler.start()

# def print_hello():
#     print("hello world")
#     return 0


# if __name__ == '__main__':
#     globals()[sys.argv[1]]()
 