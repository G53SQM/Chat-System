#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_20")
    if window('Log in'):
        click('Log in')
    close()

    if window('Chat System @Andrew'):
        window_closed('Chat System @Andrew')
    close()

    pass