from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time

#单击选择单位按钮
def school_select():
    driver.find_element_by_id("selectSchoolA").click()
    driver.find_element_by_id("H").click()
    driver.find_element_by_id("16").click()
    time.sleep(5)#加载慢了找不到元素
    driver.find_element_by_id("1731").click()

#用户登陆
def user_login():
    school_select()
    driver.find_element_by_id("unameId").send_keys('15827279440')
    driver.find_element_by_id("passwordId").send_keys('Zzy971129')

    #获取验证码图片的位置
    # location = driver.find_element_by_id("numVerCode").location
    # size = driver.find_element_by_id("numVerCode").size
    # left = location['x']
    # top = location['y']
    # right = location ['x'] + size['width']
    # bottom = location['y'] + size['height']
    # print(left,top,right,bottom)

    #网页截图
    driver.maximize_window()
    # ratio = 1.5 #网页与截图的缩放比例，巨坑
    # driver.get_screenshot_as_file("screenImg.png")
    #
    # img = Image.open("screenImg.png").crop((left*ratio,top*ratio-30,right*ratio,bottom*ratio-30))
    # img.save("1.png")
    # img = img.convert('L')
    # img = ImageEnhance.Contrast(img)
    # img = img.enhance(2.0)
    # img.save("number.png")
    # img.load()
    # time.sleep(2)

    #识别验证码并发送
    # code = pytesseract.image_to_string(img).strip()   #你这识别率也太低了吧。。。巨坑
    # print(code)
    code = input("验证码为：")
    driver.find_element_by_id("numcode").send_keys(code)
    driver.find_element_by_class_name("zl_btn_right").click()

#菜单栏鼠标悬停图标显示
def menu_display():
    button = driver.find_element_by_id("zne_tkc_icon")
    webdriver.ActionChains(driver).move_to_element(button).perform()
    time.sleep(1)

    button = driver.find_element_by_id("zne_tz_icon")
    webdriver.ActionChains(driver).move_to_element(button).perform()
    time.sleep(1)

    button = driver.find_element_by_id("zne_bj_icon")
    webdriver.ActionChains(driver).move_to_element(button).perform()
    time.sleep(1)

    button = driver.find_element_by_id("zne_yp_icon")
    webdriver.ActionChains(driver).move_to_element(button).perform()
    time.sleep(1)

    button = driver.find_element_by_id("zne_wdxz_icon")
    webdriver.ActionChains(driver).move_to_element(button).perform()
    time.sleep(1)

    button = driver.find_element_by_id("zne_txl_icon")
    webdriver.ActionChains(driver).move_to_element(button).perform()
    time.sleep(1)

#菜单栏交互测试
def menu_interact():
    driver.find_element_by_class_name("manageBtn").click()
    time.sleep(2)
    driver.find_element_by_id("mainphoto").click()
    time.sleep(2)
    driver.find_element_by_id("zne_tkc_icon").click()
    time.sleep(2)
    driver.find_element_by_id("zne_tz_icon").click()
    time.sleep(2)
    driver.find_element_by_id("zne_bj_icon").click()
    time.sleep(2)
    driver.find_element_by_id("zne_yp_icon").click()
    time.sleep(2)
    driver.find_element_by_id("zne_wdxz_icon").click()
    time.sleep(2)
    driver.find_element_by_id("zne_txl_icon").click()
    time.sleep(2)
    driver.find_element_by_class_name("rightF").click()
    time.sleep(2)
    driver.find_element_by_id("zne_kc_icon").click()

#菜单栏测试
def menu_test():
    menu_display()
    menu_interact()

#课程测试
def course_test():
    driver.switch_to.frame("frame_content")
    driver.find_element_by_link_text("软件质量与测试19春").click()
    handles = driver.window_handles

    driver.switch_to.window(handles[0])
    driver.close()#关闭原窗口

    #模拟滑动滚动条
    driver.switch_to.window(handles[1])
    e = driver.find_element_by_link_text("维护中质量")
    webdriver.ActionChains(driver).move_to_element(e).perform()

    course_resourse_test()
    time.sleep(3)

    course_menu_test()

#课程资源测试
def course_resourse_test():
    driver.find_element_by_link_text("过程中质量").click()
    time.sleep(3)
    driver.back()

#课程导航栏测试
def course_menu_test():
    # driver.find_element_by_class_name("navshow").find_element_by_link_text("首页")
    # time.sleep(3)
    # driver.close()

    driver.find_element_by_link_text("任务").click()
    time.sleep(3)
    handles = driver.window_handles
    driver.switch_to.window(handles[1])
    driver.close()

    driver.switch_to.window(handles[0])
    driver.find_element_by_link_text("统计").click()
    time.sleep(3)

    driver.find_element_by_link_text("资料").click()
    time.sleep(3)

    driver.find_element_by_link_text("通知").click()
    time.sleep(3)

    driver.find_element_by_link_text("作业").click()
    time.sleep(3)

    driver.find_element_by_link_text("考试").click()
    time.sleep(3)

    driver.find_element_by_link_text("讨论").click()
    time.sleep(3)


if __name__ == '__main__':
    time.sleep(3)#方便录制
    #打开浏览器
    driver = webdriver.Chrome('D:/chromedriver_win32/chromedriver.exe')
    #打开网页
    driver.get("http://i.mooc.chaoxing.com/space/")

    #用户登陆测试
    user_login()
    print("用户登陆完成！\n")

    #菜单栏交互测试
    menu_test()
    print("菜单栏测试完成！\n")

    time.sleep(3)
    course_test()
    print("课程板块测试完成！")
    time.sleep(3)

    print("全部测试完成！")
    driver.quit()


#driver.back()

#能否改变大小，移动和滚动

#窗口中内容是否能用鼠标，键盘访问

#下拉菜单，按钮，对话框等正常显示使用