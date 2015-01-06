#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_20")
    if window('Log in'):
        click('Log in')
    close()

    if window('Chat System @Andrew'):
        select('input', 'test')
        click('Send')
        doubleclick('JList_0', 'Lisa')
        select('JList_0', '[Lisa]')
        select('input', '[To Lisa]: test')
        click('Send')
        doubleclick('JList_0', 'Zac')
        select('JList_0', '[Zac]')
        select('input', '[To Zac]: echo')
        click('Send')
        select('input', 'group')
        click('Send')
        select('input', 'asdf')
        click('Send')
        doubleclick('JList_0', 'Zac')
        select('JList_0', '[Zac]')
        select('input', '[To Zac]: echo echo')
        click('Send')
        doubleclick('JList_0', 'Lisa')
        select('JList_0', '[Lisa]')
        select('input', '[To Lisa]: test')
        click('Send')
        window_closed('Chat System @Andrew')
    close()

    pass
