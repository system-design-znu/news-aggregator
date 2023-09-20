import json 
from collections import ChainMap
import redis
# import feedparser as fp
# f = open('whole_data.json','r',encoding="utf8")
# data = json.loads(f.read())
# # # dxml = data['xml']
# # # print(dxml)
# # # # feed = fp.parse(dxml)
# # # # print(feed)
# # # # for f in feed.entries:
# # # #     print(f.title)

# # print(data)

# # data = json.loads('whole_data.json')
# # print(data)
# for d in data:
#     feed = fp.parse(d['xml'])
#     for f in feed.entries:
#         print(f)
#         # print('title:',f['title'])
#         # print('link:',f['link'])
#         # print('summary:',f['summary'])
#         # print('pubdate:',f['published'])
#         # if('tags' not in f):
#         #     if(['links'[0]['href'] in f):
#         #         print('enclink:',f['links'][0]['href'])
#         #         print('enctype:',f['links'][0]['type'])
#         # else:
#         #     print('category:',f['tags'][0]['scheme'])
#         #     if(['links'][1]['href'] in f):
#         #         print('enclink:',f['links'][1]['href'])
#         #         print('enctype:',f['links'][1]['type'])


#         # if ('author' in f):
#         #     print('author:',f['author'])
#         # print("------------------------")
#         print("title:",f.title)
#         print(f.link)
#         if('description' in f):
#             print("desc:",f.description)
#         print("date:",f.published)
#         if(f.enclosures):
#             print("enclink:",f.enclosures[0]['href'])
#             print("enctype",f.enclosures[0]['type'])
#         if('tags' in f):
#             print("category:",f.tags[0]['term'])
#         if('author' in f):
#             print("author:",f.author)
#         print("-------------------------")

#         # print(f.description)
pool = redis.ConnectionPool(host='localhost', port=6379, db=0)
redis = redis.Redis(connection_pool=pool)
# redis.set('mykey', 'Hello from Python!')
records = [({'id' : 0, 'name': 'hossein'}),({'id':1,'name':'ahmad'}),({'id':2,'name':'majid'})]
# # print(list(records))
# news = {'news' : records}
# for i in range(len(records)):
#     key = 'news{}'.format(i)
#     redis.set(key,str(records[i]))
# redis.json().set('news','$',news)

redis.set('news',str(records))