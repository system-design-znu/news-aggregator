from .views import SendJsonList
from django.urls import path

urlpatterns = [
    path('', SendJsonList.as_view()),
]
