'''
Created on Feb 23, 2017
Last updates on Feb 23, 2017

@author: Jarred
'''

from urllib.request import urlopen, Request
from sys import stdout

def main():
    r=Request("https://mystudent.fjuhsd.net/Parent/LoginParent.aspx?page=GradebookSummary.aspx")
    conn=urlopen(r)
    conn.add_header("Cookie", "ASP.NET_SessionId=pufk4nl3w5pjs4mcxpah2vhx")
    bytes=conn.read()
    for c in bytes:
        try:
            stdout.write(chr(c))
        except:
            pass

if __name__ == '__main__':
    main()

headers={'Host':'mystudent.fjuhsd.org', 'Connection':'keep-alive', 'Origin':'example.com', 'Cache-Control':'max-age=0',
             'Upgrade-Insecure-Requests':'1', 'Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
             'Accept-Encoding':'gzip, deflate, br', 'Accept-Language':'en-US,en;q=0.8', 'Content-Type':'keep-alive',
             'Cookie':'ASP.NET_SessionId=bjg0f0etkzf4ponexzpt0lh4'}