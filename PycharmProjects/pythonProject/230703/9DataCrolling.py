# pip install selenium
import time

from selenium import webdriver

brow = webdriver.Chrome()
brow.get("http://www.naver.com")
time.sleep(3)

try:
    elem = brow.find_element(By.CSS_SELECTIOR, "service_name")
    elem.click()

except Exception as e:
    print(e)
finally:
    brow.close()
    brow.quit()