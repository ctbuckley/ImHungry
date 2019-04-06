ls /opt
ls /home/travis/build/AlexColello/CS310GroupC/target
# My tomcat webapps are found at /var/lib/tomcat6/webapps
# The application I wish to deploy is the main (ROOT) application
webapps_dir=/opt/tomcat/webapps
# Remove existing assets (if any)
rm -rf $webapps_dir/FeedMe

jar -cvf /home/travis/build/AlexColello/CS310GroupC/target/FeedMe.war /home/travis/build/AlexColello/CS310GroupC/target/FeedMe/*  
ls /home/travis/build/AlexColello/CS310GroupC/target
# Copy WAR file into place
cp /home/travis/build/AlexColello/CS310GroupC/target/FeedMe.war $webapps_dir
# Restart tomcat
sudo systemctl restart tomcat
