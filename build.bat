call mvn -f . clean
call mvn -f . install

if exist target rd target /s/q
mkdir target

xcopy eap-user\eap-user-service\target\* target\eap-user\     /Y /Q /S
xcopy eap-portal\eap-portal-service\target\* target\eap-portal\     /Y /Q /S
xcopy eap-component\eap-component-service\target\* target\eap-component\     /Y /Q /S

timeout /T 10

