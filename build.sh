mvn install:install-file -Dfile=./lib/dubbo-2.8.4a.jar -DgroupId=com.alibaba -DartifactId=dubbo -Dversion=2.8.4a -Dpackaging=jar
mvn clean
mvn install
mkdir target
rm -rf target/*
cp -rf eap-user/eap-user-service/target target/eap-user
cp -rf eap-portal/eap-portal-service/target target/eap-portal
cp -rf eap-component/eap-component-service/target target/eap-component
cp *.properties target/