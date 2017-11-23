setlocal 

set name="eap-portal-service"
set port=8984
set jarfile=eap-portal-service.jar

start %name% /MIN java -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=%port% -jar "%cd%\..\%jarfile%"

endlocal


exit






