'''
Created on Feb 26, 2017

@author: Jarred
'''

import requests
from sys import stdout as out

with requests.Session() as c:
    login = "https://mystudent.fjuhsd.net/Parent/LoginParent.aspx?page=GradebookSummary.aspx"
    grades = "https://mystudent.fjuhsd.net/Parent/GradebookSummary.aspx"
    
    username='justjarred@hotmail.com'
    password='letmein'
    
    data={'checkCookiesEnabled':'true', 'checkMobileDevice':'false', 'checkStandaloneMode':'false', 'checkTabletDevice':'false',
          'portalAccountUsername':username, 'portalAccountPassword':password, 'portalAccountUsernameLabel':'', 'submit':''}
    
    
    c.get(login)
    c.post(login, data=data)
    
    for byte in c.get(grades).content:
        out.write(chr(byte))
    