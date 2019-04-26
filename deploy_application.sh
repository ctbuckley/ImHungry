ls /opt/tomcat/
ls /opt/tomcat/conf/
cat /opt/tomcat/conf/tomcat-users.xml
# My tomcat webapps are found at /var/lib/tomcat6/webapps
# The application I wish to deploy is the main (ROOT) application
webapps_dir=/opt/tomcat/webapps
# Remove existing assets (if any)
rm -rf $webapps_dir/FeedMe 
# Copy WAR file into place
cp /home/travis/build/AlexColello/CS310GroupC/target/FeedMe.war $webapps_dir
# Restart tomcat
sudo systemctl restart tomcat

sleep 30

sudo ls /opt/tomcat/logs
sudo cat /opt/tomcat/logs/catalina.out

sudo systemctl status tomcat
curl -v -u main_user:main_password http://127.0.0.1:8080/manager/text/list

sudo ls /opt/tomcat/logs
sudo cat /opt/tomcat/logs/catalina.out
