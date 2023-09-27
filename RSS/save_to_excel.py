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

a = ['a1', 'a2']
b = ['b1', 'b2']
c = ['c1', 'c2']
d = [a, b, c]
write_to_excel('hello.xlsx', d, 5)