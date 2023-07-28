from .models import Archive
from .serializers import ArchiveSerializer
from rest_framework import generics, permissions
from .data import News

# Create your views here.

class ArchiveList(generics.ListAPIView, generics.CreateAPIView):
    permission_classes = (permissions.AllowAny,)
    queryset = Archive.objects.all()
    serializer_class = ArchiveSerializer

    def create(self, request, *args, **kwargs):
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

        return super().create(request, *args, **kwargs)