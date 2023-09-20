#!/usr/bin/env python
# coding: utf-8

# In[31]:
import requests

import psycopg2
import mongodb_fetcher as mf

md = mf.MongoFetcher(27017,"nag","sample_news")

class pySql():
    def __init__(self,data,user,password,host,port,database):
        self.data = data
        self.connection = psycopg2.connect(user = user, 
                                         password = password,
                                         host = host,
                                         port = port,
                                         database = database,
                                         options ="-c search_path=dbo,sample")
        self.cursor = self.connection.cursor()
        
    def insert_news(self):
        for d in self.data: 
            piq = """INSERT INTO news  (id_link, title, description, enclosure_link, enclosure_type, category, pubdate, author)
                    VALUES (%s,%s,%s,%s,%s,%s,%s,%s)
                    ON CONFLICT (id_link) DO NOTHING;"""
            record = (d['link'],d['title'],d['summary'],d['links'][1]['href'],d['links'][1]['type'], d['tags'][0]['scheme'],d['published'],d['author'])
            self.cursor.execute(piq,record)
        self.connection.commit()
        print(self.cursor.rowcount,'record inserted')
        self.cursor.close()
        self.connection.close()
        
    def select_all_news(self):
        saq = """select* from news"""
        self.cursor.execute(saq)
        records = self.cursor.fetchall()
        return records
        
        
    
            
            
data = md.returnall()        
ps = pySql(data,'postgres','123456','localhost',5432,'postgres')
# ps.insert_news()
data = ps.select_all_news()
lst = []
i = 0
for d in data:
    lst.append({
        
        'id' : i,
        'title' : d[1],
        'author' : d[-1]
    })
    i = i + 1





