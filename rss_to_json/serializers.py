from rest_framework import serializers
from .models import SendJson

class SendJsonSerializer(serializers.ModelSerializer):

    class Meta:
        model = SendJson
        fields = ('id', 'page_size', 'data')
        