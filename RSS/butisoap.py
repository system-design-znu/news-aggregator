from bs4 import BeautifulSoup
import requests
import xlsxwriter

def write_to_excel(excelName, allList, writeFrom):
    workbook = xlsxwriter.Workbook(excelName)
    worksheet = workbook.add_worksheet()
    alfabet = ["A", "B", "C", "D", "E", "F", "G"]
    i = writeFrom
    j = 0
    myStr = ""
    for subList in allList:
        for item in subList:
            myStr = alfabet[j] + str(i + 1)
            worksheet.write(myStr, item)
            j += 1
        j = 0
        i += 1
    workbook.close()

URL = "https://www.isna.ir/rss-help"
URL2 = "https://fararu.com/fa/rss"

def getTable(url):
    web = requests.get(url)
    soup = BeautifulSoup(web.content, "html.parser")
    myTable = soup.find('table')
    mylist = []
    allList = []
    for x in myTable.find_all('tr'):
        mylist.append(x.text)
    for item in mylist:
        newList = []
        newList = item.split("\n")
        counter = 0
        for temp in newList:
            newList[counter] = newList[counter].replace(" ", "")
            counter += 1
        print(len(newList))
        allList.append(newList)
    return allList

def getTable2(url):
    web = requests.get(url)
    soup = BeautifulSoup(web.content, "html.parser")
    myTable = soup.find('table')
    allList = []
    for x in myTable.find_all('tr'):
        newList = []
        for item in x.find_all('th'):
            newList.append(item.text)
        for item in x.find_all('td'):
            item = item.text.replace(" ", "")
            item = item.replace("\n", "")
            newList.append(item)
        allList.append(newList)
    return allList

write_to_excel("hello.xlsx", getTable2(URL), 0)
