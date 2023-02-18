from .models import SendJson
from .serializers import SendJsonSerializer
from rest_framework import generics

# Create your views here.

class SendJsonList(generics.CreateAPIView):
    queryset = SendJson.objects.all()
    serializer_class = SendJsonSerializer