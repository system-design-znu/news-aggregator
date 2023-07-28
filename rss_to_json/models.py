from django.db import models
from .data import News

# Create your models here.

class Archive(models.Model):
    title = models.CharField(max_length=126)
    author = models.CharField(max_length=126)
    publish_date = models.DateTimeField("Publish date")
