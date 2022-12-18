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
    descriptions_list = []
    authors_list = []

    for item in data['items']:
        titles_list.append(item['title'])
        authors_list.append(item['author'])
        descriptions_list.append(item['description'])
        page_size+=1
    
    content_1 = {
        "title" : titles_list[0],
        "author" : authors_list[0],
        "descriptions" : descriptions_list[0]
    }
    content_2 = {
        "title" : titles_list[1],
        "author" : authors_list[1],
        "descriptions" : descriptions_list[1]
    }
    content_3 = {
        "title" : titles_list[2],
        "author" : authors_list[2],
        "descriptions" : descriptions_list[2]
    }
    content_4 = {
        "title" : titles_list[3],
        "author" : authors_list[3],
        "descriptions" : descriptions_list[3]
    }
    content_5 = {
        "title" : titles_list[4],
        "author" : authors_list[4],
        "descriptions" : descriptions_list[4]
    }
    content_6 = {
        "title" : titles_list[5],
        "author" : authors_list[5],
        "descriptions" : descriptions_list[5]
    }
    content_7 = {
        "title" : titles_list[6],
        "author" : authors_list[6],
        "descriptions" : descriptions_list[6]
    }
    content_8 = {
        "title" : titles_list[7],
        "author" : authors_list[7],
        "descriptions" : descriptions_list[7]
    }
    content_9 = {
        "title" : titles_list[8],
        "author" : authors_list[8],
        "descriptions" : descriptions_list[8]
    }
    content_10 = {
        "title" : titles_list[9],
        "author" : authors_list[9],
        "descriptions" : descriptions_list[9],
    }
        # titles = titles_list[page_size]
        # authors = authors_list[page_size]
        # descriptions = descriptions_list[page_size]
