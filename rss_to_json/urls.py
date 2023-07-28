from .views import ArchiveList
from django.urls import path

urlpatterns = [
    path('', ArchiveList.as_view()),
]
