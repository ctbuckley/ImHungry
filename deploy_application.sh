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

sleep 60

sudo systemctl status tomcat
curl -v -u main_user:main_password https://127.0.0.1:8443/manager/text/list