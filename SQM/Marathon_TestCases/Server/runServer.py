#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_20")
    if window('Chat system server'):
        click('Start server')
        window_closed('Chat system server')
    close()

    pass