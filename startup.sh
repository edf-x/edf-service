nohup sh ./../zookeeper/bin/zkServer.sh start > ./zookeeper.log 2>&1 &
sleep 5

cd ./target/eap-user/bin/
nohup sh startup.sh > eap-user-nohup.log 2>&1 &
cd ../../../
sleep 15

cd ./target/eap-portal/bin/
nohup sh startup.sh > eap-portal-nohup.log 2>&1 & 
cd ../../../
sleep 20

cd ./target/eap-component/bin/
nohup sh startup.sh > eap-component-nohup.log 2>&1 &
cd ../../../
sleep 10

cd ./../webserver/
nohup node index.js > eap-webserver-nohup.log 2>&1 &
