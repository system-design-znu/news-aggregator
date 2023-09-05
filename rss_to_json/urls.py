from .views import ArchiveList, ArchiveCreate
from django.urls import path

urlpatterns = [
    path('', ArchiveList.as_view()),
    path('create/', ArchiveCreate.as_view()),
]
