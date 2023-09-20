#!/usr/bin/env python
# coding: utf-8

# In[38]:


import pymongo

class MongoFetcher():
    def __init__(self, host_address, dbname, colname):
        self.client = pymongo.MongoClient("mongodb://localhost:{}/".format(host_address))
        self.db = self.client[dbname]
        self.col = self.db[colname]
        self.data = list(self.col.find())
        
    def showall(self):
#         print(self.data)
        for data in self.data:
            print(data['title'])
            print(data['link'])
            print(data['summary'])
            print(data['published'])
            print(data['tags'][0]['scheme'])
            print(data['links'][1]['href'])
            print(data['links'][1]['type'])
            print(data['author'])
#             print(data['enclosure']['link'])
#             print(data['enclosure']['type'])
            print("--------------------------")
            
        
    def returnall(self):
        return self.data
        
        
    
# mf = MongoFetcher(27017,"nag","sample_news")
# mf.returnall()



# In[ ]:




