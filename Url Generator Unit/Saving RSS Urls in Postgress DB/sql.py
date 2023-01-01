import pandas as pd
import sqlalchemy as db
import psycopg2

user = 'postgres'
password = 'Ah-As-22'
host = 'localhost'
port = '5432'
database = 'RSS'
path = 'E:\Programming\Python\Python Code\VSCode\Project1\sql\RSS.xlsx'
tableName = "RSS_Fetch"

engine = db.create_engine("postgresql+psycopg2://"+user+":"+password+"@"+host+":"+port+"/"+database)
connection = engine.connect()

def get_table_data(table_name2, sql_connection):
    sql_query = pd.read_sql_table(table_name=table_name2, con=sql_connection)
    df = pd.DataFrame(sql_query, columns = ['id', 'url', 'source','title','fetch'])
    mylist = []
    for temp in range(df.index.stop):
        newList = []
        newList.append(str(df.loc[temp,'id']))
        newList.append(str(df.loc[temp,'url']))
        newList.append(str(df.loc[temp,'source']))
        #newList.append(str(df.loc[temp,'title']))
        newList.append(str(df.loc[temp,'fetch']))
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

#write_from_excel(engine, path)
write_data_in_txt(get_table_data(tableName, connection))
