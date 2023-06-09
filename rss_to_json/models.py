import urllib.request as ulrr
from django.db import models
from json import loads

# Create your models here.

class SendJson(models.Model):
    url = "https://api.rss2json.com/v1/api.json?rss_url=https://www.irna.ir/rss"
    response = ulrr.urlopen(url).read().decode('utf8')
    disarray_data = loads(response)
    raw_information = disarray_data['items']

    titles_list = []
    authors_list = []
    dates_list = []
    for ele in range(len(raw_information)):
        titles_list.append((raw_information[0])['title'])
        authors_list.append((raw_information[0])['author'])
        dates_list.append((raw_information[0])['pubDate'])

    cnt = 0
    data = []
    for cnt in range(10):
        data_dict = {
            'title' : titles_list[cnt],
            'author' : authors_list[cnt],
            'publish date' : dates_list[cnt],
        }
        data.append(data_dict)

    page_size = cnt+1
