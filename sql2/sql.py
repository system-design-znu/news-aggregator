import pandas as pd
import sqlalchemy as db
import psycopg2

user = 'postgres'
password = '123456'
host = 'localhost'
port = '5432'
database = 'RSS'
excelpath = 'D:\Programming\VSCode\Code\Python Code\VSCode\Project1\sql\RSS.xlsx'
txtpath = "D:\Programming\VSCode\Code\Python Code\VSCode\Project1\sql\Configfile.txt"
tableName = "RSS_Fetch"

def read_from_file():
    f = open("D:\Programming\VSCode\Code\Python Code\VSCode\Project1\sql\Configfile.txt", "r")
    return f.readline().splitlines()[0], f.readline().splitlines()[0], f.readline().splitlines()[0], f.readline().splitlines()[0], f.readline().splitlines()[0], f.readline().splitlines()[0], f.readline().splitlines()[0]

def remove_substring(user, password, host, port, database, excelpath, tableName):
    user = user.replace("user :","")
    password = password.replace("password :","")
    host = host.replace("host :","")
    port = port.replace("port :","")
    database = database.replace("database :","")
    excelpath = excelpath.replace("excelpath :","")
    tableName = tableName.replace("tableName :","")
    return user, password, host, port, database, excelpath, tableName

user, password, host, port, database, excelpath, tableName = read_from_file()
user, password, host, port, database, excelpath, tableName = remove_substring(user, password, host, port, database, excelpath, tableName)
engine = db.create_engine("postgresql+psycopg2://"+user+":"+password+"@"+host+":"+port+"/"+database)
connection = engine.connect()

def get_table_data(table_name2, sql_connection):
    sql_query = pd.read_sql_table(table_name=table_name2, con=sql_connection)
    df = pd.DataFrame(sql_query, columns = ['id', 'url', 'source','title','fetch','priority'])
    mylist = []
    for temp in range(df.index.stop):
        newList = []
        newList.append(str(df.loc[temp,'id']))
        newList.append(str(df.loc[temp,'url']))
        newList.append(str(df.loc[temp,'source']))
        #newList.append(str(df.loc[temp,'title']))
        newList.append(str(df.loc[temp,'fetch']))
        newList.append(str(df.loc[temp,'priority']))
        mylist.append(newList)
    return mylist

def write_data_in_txt(all_list):
    with open('readme.txt', 'w') as f:
        for mylist in all_list:
            for line in mylist:
                f.write(line)
                f.write(', ')
            f.write('\n')

def write_from_excel(input_engine, excel_path):
    with pd.ExcelFile(excel_path) as xls:
        df = pd.read_excel(xls)
        df.to_sql(name='RSS_Fetch', con=input_engine, if_exists='replace', index=False)


write_from_excel(engine, excelpath)
#write_data_in_txt(get_table_data(tableName, connection))
