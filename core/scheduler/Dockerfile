FROM python:latest
ADD scheduler.py /
COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt

COPY  . .
CMD [ "python","scheduler.py","print_hello;run_program" ]