call .\..\zookeeper\bin\zkServer.cmd

timeout /T 5

start /d.\target\eap-user\bin\  start_dev.bat

timeout /T 10

start /d.\target\eap-portal\bin\  start_dev.bat

timeout /T 10