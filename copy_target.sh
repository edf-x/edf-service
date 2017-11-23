mkdir target
rm -rf target/*
cp -rf eap-user/eap-user-service/target target/eap-user
cp -rf eap-portal/eap-portal-service/target target/eap-portal
cp -rf eap-component/eap-component-service/target target/eap-component
cp *.properties target/