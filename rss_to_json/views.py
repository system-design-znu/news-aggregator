from .models import SendJson
from .serializers import SendJsonSerializer
from rest_framework import generics, permissions

# Create your views here.

class SendJsonList(generics.CreateAPIView):
    permission_classes = (permissions.AllowAny,)
    queryset = SendJson.objects.all()
    serializer_class = SendJsonSerializer
    