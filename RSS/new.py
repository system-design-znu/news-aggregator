def read_from_file():
    f = open("D:\Programming\VSCode\Code\Python Code\VSCode\Project1\sql\input.txt", "r")
    return f.readline().splitlines()[0], f.readline().splitlines()[0], f.readline().splitlines()[0], f.readline().splitlines()[0], f.readline().splitlines()[0], f.readline().splitlines()[0], f.readline().splitlines()

user = ''
password = ''
host = ''
port = ''
database = ''
excelpath = ''
tableName = ''
user, password, host, port, database, excelpath, tableName = read_from_file()

print(user, end=" ")
print(password, end="")
