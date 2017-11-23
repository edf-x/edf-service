
 name="eap-component-service"
 
 port=8985

 jarfile=`dirname $0`/../${name}.jar 

 java -Dzkserver="127.0.0.1:2181" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=${port} -jar "${jarfile}"