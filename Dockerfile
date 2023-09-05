FROM python:3.8-slim-buster
WORKDIR /usr/src/app
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
COPY  . .
RUN python manage.py makemigrations
RUN python manage.py migrate
RUN echo "from django.contrib.auth import get_user_model; User = get_user_model(); User.objects.create_superuser('behnam', 'mahdi.mohamadiha.znu@gmail.com', 'behn@m.@dmin')" | python manage.py shell
EXPOSE 8000
CMD python manage.py runserver 0.0.0.0:8000
