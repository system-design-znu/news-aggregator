import pymongo

class MongoFetcher():
    def __init__(self, host_address, dbname, colname):
        self.client = pymongo.MongoClient("mongodb://localhost:{}/".format(host_address))
        self.db = self.client[dbname]
        self.col = self.db[colname]
        
    def returnall(self):
        self.data = self.col.find()
        data_list = list(self.data)
        print(data_list)
        return data_list
    
mf = MongoFetcher(27017,"nag","news")
mf.returnall()

