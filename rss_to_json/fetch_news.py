import urllib.request as ulrr
import xml.etree.ElementTree as ET

class News:

    @staticmethod
    def irna_news():
        url = "https://www.irna.ir/rss"
        response = ulrr.urlopen(url).read().decode('utf8')

        # Parse XML data
        root = ET.fromstring(response)

        titles_list = []
        authors_list = []
        dates_list = []
        for item in root.findall('.//item'):
            title = item.find('title').text if item.find('title') is not None else 'No title'
            author = item.find('author').text if item.find('author') is not None else 'No author'
            pub_date = item.find('pubDate').text if item.find('pubDate') is not None else 'No date'

            titles_list.append(title)
            authors_list.append(author)
            dates_list.append(pub_date)

        data = []
        for cnt in range(min(10, len(titles_list))):
            data_dict = {
                'title': titles_list[cnt],
                'author': authors_list[cnt],
                'publish date': dates_list[cnt],
            }
            data.append(data_dict)

        return data


if __name__ == '__main__':
    def fetch_and_print_news():
        news = News.irna_news()
        for item in news:
            print(f"Title: {item['title']}")
            print(f"Author: {item['author']}")
            print(f"Publish Date: {item['publish date']}\n")

    fetch_and_print_news()
