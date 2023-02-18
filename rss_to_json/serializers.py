from rest_framework import serializers
from .models import SendJson

class SendJsonSerializer(serializers.ModelSerializer):

    class Meta:
        model = SendJson
        fields = ('id', 'page_size', 'content_1', 'content_2', 'content_3', 'content_4', 'content_5', 'content_6', 'content_7', 'content_8', 'content_9', 'content_10')
        