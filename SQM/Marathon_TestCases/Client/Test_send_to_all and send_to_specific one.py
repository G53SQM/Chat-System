#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_20")
    if window('Log in'):
        click('Log in')
    close()

    if window('Chat System @Andrew'):
        select('input', 'asdasdsdad')
        click('Send')
        doubleclick('JList_0', 'Lisa')
        select('JList_0', '[Lisa]')
        select('input', '[To Lisa]: adsads')
        click('Send')
        doubleclick('JList_0', 'Zac')
        select('JList_0', '[Zac]')
        select('input', '[To Zac]: sdfsdfasd')
        click('Send')
        select('input', 'adssdfwe')
        click('Send')
        select('input', 'qweqwdad')
        click('Send')
        doubleclick('JList_0', 'Zac')
        select('JList_0', '[Zac]')
        select('input', '[To Zac]: qwesda')
        click('Send')
        doubleclick('JList_0', 'Lisa')
        select('JList_0', '[Lisa]')
        select('input', '[To Lisa]: asdsaf')
        click('Send')
        select('input', 'adasd')
        click('Clear')
        select('JList_0', '[Zac]')
        select('input', '[To Zac]: dasdasd')
        click('Clear')
        select('input', 'adsda')
        click('Send')
        click('com.sun.java.swing.plaf.windows.WindowsSplitPaneDivider_0', 1, 52)
        doubleclick('JList_0', 'Lisa')
        select('JList_0', '[Lisa]')
        select('input', '[To Lisa]: adsdas')
        click('Send')
        window_closed('Chat System @Andrew')
    close()

    pass