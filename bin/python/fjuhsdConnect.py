'''
Created on Feb 26, 2017

It logs on to Aeries and stores its cookies from Aeries in the specified output file
Syntax is python %0 <username> <password> <output.file>
@author: Jarred
'''

import requests
from requests.utils import dict_from_cookiejar

from sys import argv

with requests.Session() as c:
    login = "https://mystudent.fjuhsd.net/Parent/LoginParent.aspx?page=GradebookSummary.aspx"
    grades = "https://mystudent.fjuhsd.net/Parent/GradebookSummary.aspx"
    
    username=argv[1]
    password=argv[2]
    
    data={'checkCookiesEnabled':'true', 'checkMobileDevice':'false', 'checkStandaloneMode':'false', 'checkTabletDevice':'false',
          'portalAccountUsername':username, 'portalAccountPassword':password, 'portalAccountUsernameLabel':'', 'submit':''}
    
    
    c.get(login)
    c.post(login, data=data)
    
    with open(argv[3], 'w') as out:
        cookies=dict_from_cookiejar(c.cookies)
        for cookie in cookies:
            out.write(cookie+'='+cookies[cookie]+'; ')
    