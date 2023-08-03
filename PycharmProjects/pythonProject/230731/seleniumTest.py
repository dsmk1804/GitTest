"""

"""

import requests
from bs4 import BeautifulSoup

text = requests.get('https://neolook.com/zb/zboard.php?id=post2005')
text.encoding = 'ms949'
print(text)
print(text.status_code)
print(text.text)

soup = BeautifulSoup(text.text,"html.parser")
#print(soup)
trs = soup.find_all('tr')
print(trs)

# trs는 ResultSet 객체로 여러 요소들을 담고 있는 리스트와 같은 형태로 가정합니다.
# 개별 요소에 대해 find() 메서드를 사용해야 합니다.


