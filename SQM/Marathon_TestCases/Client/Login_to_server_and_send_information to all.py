#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_20")
    if window('Log in'):
        click('Log in')
    close()

    if window('Chat System @Andrew'):
        select('input', 'afdsda')
        click('Send')
        select('input', 'dasweerwefr')
        click('Send')
        select('input', 'fasdfserqrw')
        click('Send')
        select('input', 'sadasdaweqw')
        click('Send')
        select('input', 'sdfsdfsdfs')
        click('Send')
        select('input', 'fsdfsdf')
        click('Clear')
        select('input', 'sdfsdfs')
        click('Clear')
        select('input', 'sdfsdf')
        click('Send')
        select('input', 'sdfsxcxz')
        click('Clear')
        select('input', 'fdsfsdbfdgswd')
        click('Send')
        select('input', 'sdasefwvxvsadf')
        click('Send')
        window_closed('Chat System @Andrew')
    close()

    pass