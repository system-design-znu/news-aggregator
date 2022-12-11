from django.db import models
import urllib.request as ulrr
import json

# Create your models here.

class SendJson(models.Model):
    url = "https://api.rss2json.com/v1/api.json?rss_url=https://www.irna.ir/rss"
    response = ulrr.urlopen(url).read().decode('utf8')
    data = json.loads(response)

    titles_list = []
    for item in data['items']:
        titles_list.append(item['title'])
    string_titles = ' ||| '.join([str(elem) for remove_number,elem in enumerate(titles_list)])
