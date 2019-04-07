ls /opt/tomcat/
ls /opt/tomcat/conf/
cat /opt/tomcat/conf/tomcat-users.xml
# My tomcat webapps are found at /var/lib/tomcat6/webapps
# The application I wish to deploy is the main (ROOT) application
webapps_dir=/opt/tomcat/webapps
# Remove existing assets (if any)
rm -rf $webapps_dir/FeedMe 
ls /home/travis/build/AlexColello/CS310GroupC/target
# Copy WAR file into place
cp /home/travis/build/AlexColello/CS310GroupC/target/FeedMe.war $webapps_dir
# Restart tomcat
sudo systemctl restart tomcat

sudo systemctl status tomcat

curl -v -u root:password http://127.0.0.1:8080/manager/text/list

page="$(curl http://localhost:8080/FeedMe/jsp/login.jsp/)"
printf "%s" $page