# pip install selenium
import time

from selenium import webdriver

brow = webdriver.Chrome()
brow.get("http:/neolook.com/archives")

brow.find_element()

time.sleep(10)

brow.close()
brow.quit()