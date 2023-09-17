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
        return Archive.objects.all()  # must not use both .all() and .get_queryset()

    def get_serializer_class(self):
        return self.serializer_class
    

class ArchiveListLT(ArchiveList):  # last ten news ordered by id that inherit from ArchiveList
    def get_queryset(self):
        queryset = super().get_queryset()
        return queryset.order_by('-id')[:10][::-1]


class ArchiveCreate(generics.GenericAPIView):
    permission_classes = (permissions.AllowAny,)
    queryset = Archive.objects.all()
    serializer_class = ArchiveSerializer

    def get(self, request, *args, **kwargs):
        news = News.irna_news()  # Fetching data from the News class
        skipped = False

        for data in news:  # Creating instances of Archive from the fetched data
            title = data['title']
            author = data['author']
            publish_date = data['publish date']

            if (Archive.objects.filter(title = title).exists() and
                 Archive.objects.filter(author = author).exists() and
                 Archive.objects.filter(publish_date = publish_date).exists()):  # Check if news already exists
                skipped = True
                continue  # Skip insertion if news already exists

            Archive.objects.create(
                title = title,
                author = author,
                publish_date = publish_date
            )

        if skipped:
            return Response("Skipped insertion. Data already exists.", status=status.HTTP_409_CONFLICT)
        else:
            return Response("Data inserted successfully.", status=status.HTTP_201_CREATED)
