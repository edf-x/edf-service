setlocal 

set name="eap-component-service"
set port=8985
set jarfile=eap-user-service.jar

start %name% /MIN java -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=%port% -jar "%cd%\..\%jarfile%"

endlocal

exit







