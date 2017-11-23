 name="eap-component-service"

 jarfile=`dirname $0`/../${name}.jar

 java  -Dzkserver="127.0.0.1:2181" -jar "${jarfile}"