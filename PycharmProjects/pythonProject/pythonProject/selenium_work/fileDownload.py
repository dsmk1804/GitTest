# 파일 다운로드

import time
from selenium import webdriver
from selenium.webdriver.chrome.options import Options

chrome_options = Options()
chrome_options.add_experimental_option('prefs', {'download.default_directory':r'C:\Users\Nadocoding\Desktop\PythonWorkspace'})

browser = webdriver.Chrome(options=chrome_options)
browser.get('https://ko.wikipedia.org/wiki/%EA%B3%A0%EC%96%91%EC%9D%B4')
browser.switch_to.frame('iframeResult')

# download 링크 클릭
elem = browser.find_element_by_xpath('/html/body/p[2]/a')
elem.click()

time.sleep(5)
browser.quit()