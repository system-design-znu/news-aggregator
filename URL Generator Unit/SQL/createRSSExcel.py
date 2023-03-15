from bs4 import BeautifulSoup
import requests
import xlsxwriter

def write_to_excel(excelName, allList, writeFrom):
    workbook = xlsxwriter.Workbook(excelName)
    worksheet = workbook.add_worksheet()
    alfabet = ["A", "B", "C", "D", "E", "F", "G"]
    i = writeFrom
    j = 0
    counter = -1
    myStr = ""
    for subList in allList:
        counter += 1
        if counter != 0:
            myStr = alfabet[0] + str(i + 1)
            worksheet.write(myStr, counter)
        for item in subList:
            if counter == 0:
                myStr = alfabet[j] + str(i + 1)
                worksheet.write(myStr, item)
                j += 1
            else:
                j += 1
                myStr = alfabet[j] + str(i + 1)
                worksheet.write(myStr, item)
        j = 0
        i += 1
    workbook.close()

URL1 = "https://www.isna.ir/rss-help"                 # done
URL2 = "https://fararu.com/fa/rss"                    # done
URL3 = "https://www.shahrekhabar.com/rssfeeds"        # done
URL4 = "https://www.khabarvarzeshi.com/rss-help"      # done
URL5 = "https://www.khabaronline.ir/rss-help"         # done
URL6 = "https://www.tasnimnews.com/fa/rss"
URL7 = "https://www.mehrnews.com/rsslist"             # done
URL8 = "https://www.yjc.news/fa/rss"
URL9 = "https://www.jamaran.news/links/categorized"
URL10 = "https://www.varzesh3.com/rss/list"
URL11 = "https://www.farsnews.ir/rsslinks"
URL12 = "https://khabarfarsi.com/rss"
URL14 = "https://www.iribnews.ir/fa/rss"
URL15 = "https://www.khabarfoori.com/feeds/all"
URL16 = "https://www.hamshahrionline.ir/rss-help"     # done
URL18 = "www.mashreghnews.ir/rss"
URL19 = "https://www.tabnak.ir/fa/rss"                # done
URL20 = "https://aftabnews.ir/fa/rss"                 # done
#URL21 = "https://www.bartarinha.ir/feeds"
URL22 = "http://www.parseek.com/newsapi/"
URL23 = "https://www.bazarebours.com/feeds/all"
URL24 = "https://www.bidarbourse.com/rss-help"        # done
URL25 = "https://basijnews.ir/fa/rss"                 # done
URL26 = "https://ana.press/fa/rss"                    # done
URL27 = "https://shahr.ir/rss-help"                   # done
URL28 = "https://eghtesaad24.ir/fa/rss"
URL29 = "https://www.iscanews.ir/rss-help"            # done
URL30 = "http://www.icana.ir/Fa/rss"                  # done
URL31 = "https://snn.ir/fa/rss"                       # done
URL32 = "https://www.asriran.com/fa/rss"              # done
URL33 = "https://www.alef.ir/press/rss/index.html"
URL34 = "https://www.rajanews.com/rss-list"
URL35 = "https://www.jahannews.com/vfhfagiiw.html"

def getTable(url, allList, source):
    web = requests.get(url)
    soup = BeautifulSoup(web.content, "html.parser")
    myTable = soup.find('table')
    count = 0
    for x in myTable.find_all('tr'):
        newList = []
        counter = 0
        for item in x.find_all('th'):
            newList.append(item.text)
        for item in x.find_all('td'):
            item = item.text
            item = item.strip()
            item = item.replace("\n", "")
            if counter == 0:
                item = item.replace("_x000D_", "")
            newList.append(item)
            counter += 1
        newList.append(source)
        if newList[1].__contains__("http"):
            if count < 5:
                newList.append("15")
                newList.append("1")
            else :
                newList.append("60")
                newList.append("2")
            count += 1
            allList.append(newList)
    return allList

def getFaRss(url, allList, source):
    web = requests.get(url)
    soup = BeautifulSoup(web.content, "html.parser")
    count = 0
    div = soup.find('div', class_="rss_block")
    for row in div.find_all('div', attrs = {'class':'rss_row'}):
        newList = []
        str1 = row.find('div').text
        str1 = str1.strip()
        str1 = str1.replace(":", "")
        newList.append(" تازه ها > " + str1)
        newList.append(row.find('a').text)
        newList.append(source)
        if count < 5:
            newList.append("15")
            newList.append("1")
            count += 1
        else :
            newList.append("60")
            newList.append("2")
        allList.append(newList)
    div2 = soup.find('div', class_="rss_block", attrs={'style':"padding-top: 0px;"})
    if div2 is None:
        div2 = soup.find('div', class_="rss_block", attrs={'style':"padding-top: 0;"})
    for row in div2.find_all('div', attrs = {'class':'rss_row'}):
        newList = []
        str1 = row.find('div').text
        str1 = str1.strip()
        str1 = str1.replace(":", "")
        newList.append(" پر بیننده ترین > " + str1)
        newList.append(row.find('a').text)
        newList.append(source)
        if count < 5:
            newList.append("15")
            newList.append("1")
            count += 1
        else :
            newList.append("60")
            newList.append("2")
        allList.append(newList)
    div3 = soup.find('div', class_="rss_block nomp")
    for row in div3.find_all('div', attrs={'class':'rss_row'}):
        newList = []
        str1 = row.find('div').text
        str1 = str1.strip()
        str1 = str1.replace(":", "")
        newList.append(" پر بحث ترین > " + str1)
        newList.append(row.find('a').text)
        newList.append(source)
        if count < 5:
            newList.append("15")
            newList.append("1")
            count += 1
        else :
            newList.append("60")
            newList.append("2")
        allList.append(newList)
    return allList

def getShahrekhabar(url, allList, source):
    web = requests.get(url)
    soup = BeautifulSoup(web.content, "html.parser")
    count = 0
    div = soup.find('div', class_="padding10 overflow_hidden margin20")
    for row in div.find_all('div', attrs = {'class':'margin-bottom-15 margintop10'}):
        newList = []
        a = row.find('a', href=True)
        newList.append(a.text)
        newList.append(a['href'])
        newList.append(source)
        if count < 5:
            newList.append("15")
            newList.append("1")
        else :
            newList.append("60")
            newList.append("2")
        allList.append(newList)
        count += 1
    return allList

def getFararo(url, allList, source):
    web = requests.get(url)
    soup = BeautifulSoup(web.content, "html.parser")
    count = 0
    div = soup.find('div', class_="rss_block")
    for row in div.find_all('div', attrs = {'class':'row rss_row'}):
        newList2 = []
        str2 = row.find('div', attrs={'class':'rss_list_pn'}).text
        str2 = str2.strip()
        str2 = str2.replace(":", "")
        newList2.append(" تازه ها > " + str2)
        newList2.append(row.find('a').text)
        newList2.append(source)
        if count < 5:
            newList2.append("15")
            newList2.append("1")
            count += 1
        else :
            newList2.append("60")
            newList2.append("2")
        allList.append(newList2)
        for row2 in row.find_all('div', attrs = {'class':'row'}):
            newList = []
            str1 = row2.find('div', class_="rss_list_pn_cat").text
            str1 = str1.strip()
            str1 = str1.replace(":", "")
            newList.append(" تازه ها > " + str1)
            newList.append(row2.find('a').text)
            newList.append(source)
            newList.append("60")
            newList.append("2")
            allList.append(newList)
    div2 = soup.find('div', class_="rss_block", attrs={'style':"padding-top: 0px;"})
    for row in div2.find_all('div', attrs = {'class':'row rss_row'}):
        newList2 = []
        str2 = row.find('div', attrs={'class':'rss_list_pn'}).text
        str2 = str2.strip()
        str2 = str2.replace(":", "")
        newList2.append(" پر بیننده ترین > " + str2)
        newList2.append(row.find('a').text)
        newList2.append(source)
        if count < 5:
            newList.append("15")
            newList2.append("1")
            count += 1
        else :
            newList2.append("60")
            newList2.append("2")
        allList.append(newList2)
        for row2 in row.find_all('div', attrs = {'class':'row'}):
            newList = []
            str1 = row2.find('div', class_="rss_list_pn_cat").text
            str1 = str1.strip()
            str1 = str1.replace(":", "")
            newList.append(" پر بیننده ترین > " + str1)
            newList.append(row2.find('a').text)
            newList.append(source)
            newList.append("60")
            newList.append("2")
            allList.append(newList)
    div3 = soup.find_all('div', class_="rss_block")[1]
    for row in div3.find_all('div', attrs = {'class':'row rss_row'}):
        newList2 = []
        str2 = row.find('div', attrs={'class':'rss_list_pn'}).text
        str2 = str2.strip()
        str2 = str2.replace(":", "")
        newList2.append(" پر بحث ترین > " + str2)
        newList2.append(row.find('a').text)
        newList2.append(source)
        if count < 5:
            newList2.append("15")
            newList2.append("1")
            count += 1
        else :
            newList2.append("60")
            newList2.append("2")
        allList.append(newList2)
        count += 1
        for row2 in row.find_all('div', attrs = {'class':'row'}):
            newList = []
            str1 = row2.find('div', class_="rss_list_pn_cat").text
            str1 = str1.strip()
            str1 = str1.replace(":", "")
            newList.append(" پر بحث ترین > " + str1)
            newList.append(row2.find('a').text)
            newList.append(source)
            newList.append("60")
            newList.append("2")
            allList.append(newList)
    return allList

allList = []

new_list = []
new_list.append("source")
new_list.append("id")
new_list.append("url")
new_list.append("title")
new_list.append("fetch")
new_list.append("priority")

allList.append(new_list)

allList = getTable(URL1, allList, "isna")
allList = getFararo(URL2, allList, "fararu")
allList = getShahrekhabar(URL3, allList, "shahrekhabar")
allList = getTable(URL4, allList, "khabarvarzeshi")
allList = getTable(URL5, allList, "khabaronline")
allList = getTable(URL7, allList, "mehrnews")
allList = getTable(URL16, allList, "hamshahrionline")
allList = getFaRss(URL19, allList, "tabnak")
allList = getFaRss(URL20, allList, "aftabnews")
allList = getTable(URL24, allList, "bidarbourse")
allList = getFaRss(URL25, allList, "basijnews")
allList = getFaRss(URL26, allList, "press")
allList = getTable(URL27, allList, "shahr")
allList = getTable(URL29, allList, "iscanews")
allList = getTable(URL30, allList, "icana")
allList = getFaRss(URL31, allList, "snn")
allList = getFaRss(URL32, allList, "asriran")

for subList in allList:
    subList[0], subList[1], subList[2] = subList[1], subList[2], subList[0]

write_to_excel("RSS.xlsx", allList, 0)


