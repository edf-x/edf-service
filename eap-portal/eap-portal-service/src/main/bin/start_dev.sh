
 name="eap-portal-service"

 port=8984 

 jarfile=`dirname $0`/../${name}.jar 

 java  -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=${port} -jar "${jarfile}"