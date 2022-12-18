from django.db import models
import urllib.request as ulrr
import json

# Create your models here.

class SendJson(models.Model):
    url = "https://api.rss2json.com/v1/api.json?rss_url=https://www.irna.ir/rss"
    response = ulrr.urlopen(url).read().decode('utf8')
    data = json.loads(response)

    page_size=0

    titles_list = []
    for item in data['items']:
        titles_list.append(item['title'])
        page_size+=1
    titles = titles_list

    descriptions_list = []
    for item in data['items']:
        descriptions_list.append(item['description'])
    descriptions = descriptions_list

    authors_list = []
    for item in data['items']:
        authors_list.append(item['author'])
    authors = authors_list
