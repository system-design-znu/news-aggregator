from rest_framework import generics, permissions, status
from rest_framework.response import Response
from .models import Archive
from .serializers import ArchiveSerializer
from .fetch_news import News

class ArchiveList(generics.ListAPIView):
    permission_classes = (permissions.AllowAny,)
    queryset = Archive.objects.all()
    serializer_class = ArchiveSerializer

class ArchiveCreate(generics.GenericAPIView):
    permission_classes = (permissions.AllowAny,)
    queryset = Archive.objects.all()
    serializer_class = ArchiveSerializer

    def get(self, request, *args, **kwargs):
        # Fetching data from the News class
        news = News.irna_news()

        # Creating instances of Archive from the fetched data
        for data in news:
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
