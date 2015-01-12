#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_20")
    if window('Log in'):
        click('Log in')

        if window('Log in(1)'):
            click('\u786e\u5b9a')
        close()

        window_closed('Log in')
    close()

    pass