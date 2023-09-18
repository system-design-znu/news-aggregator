# Use an official Python runtime as the base image
FROM python:3.8-slim-buster

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the project files to the working directory
COPY . .

# Install project dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Database migrations
RUN python manage.py makemigrations
RUN python manage.py migrate

# Create a superuser
RUN echo "from django.contrib.auth import get_user_model; User = get_user_model(); User.objects.create_superuser('behnam', 'mahdi.mohamadiha.znu@gmail.com', 'behn@m.@dmin')" | python manage.py shell

# Modify swagger static files reference
RUN sed -i 's/staticfiles/static/g' /usr/local/lib/python3.8/site-packages/rest_framework_swagger/templates/rest_framework_swagger/index.html

# Install Redis server
RUN apt-get update && apt-get install -y redis-server

# Expose the ports for Django (8000) and Redis (6379)
EXPOSE 8000
EXPOSE 6379

# Start the Django development server and Redis server
CMD redis-server --daemonize yes && \
    python manage.py runserver 0.0.0.0:8000
