from rest_framework import generics, permissions, status
from rest_framework.response import Response
from .models import Archive
from .serializers import ArchiveSerializer
from .fetch_news import News

# Create your views here.

class ArchiveList(generics.ListAPIView):
    permission_classes = (permissions.AllowAny,)
    serializer_class = ArchiveSerializer
    
    def get_queryset(self):
        return Archive.objects.all() # must not use both .all() and .get_queryset()

    def get_serializer_class(self):
        return self.serializer_class
    

class ArchiveListLT(ArchiveList): # last ten news ordered by id that inherit from ArchiveList
    def get_queryset(self):
        queryset = super().get_queryset()
        return queryset.order_by('-id')[:10][::-1]


class ArchiveCreate(generics.GenericAPIView):
    permission_classes = (permissions.AllowAny,)
    queryset = Archive.objects.all()
    serializer_class = ArchiveSerializer

    def get(self, request, *args, **kwargs):
        news = News.irna_news() # Fetching data from the News class

        for data in news: # Creating instances of Archive from the fetched data
            title = data['title'],
            author = data['author'],
            publish_date = data['publish date']

            archive_instance = Archive.objects.create(
                title=title,
                author=author,
                publish_date=publish_date
            )

            archive_instance.save()

        return Response("Data inserted successfully", status=status.HTTP_201_CREATED)
