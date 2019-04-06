ls /var/lib/
# My tomcat webapps are found at /var/lib/tomcat6/webapps
# The application I wish to deploy is the main (ROOT) application
webapps_dir=/var/lib/tomcat9/webapps
# Remove existing assets (if any)
rm -rf $webapps_dir/ROOT
# Copy WAR file into place
cp /home/travis/build/AlexColello/CS310GroupC/target/ROOT.war $webapps_dir
# Restart tomcat
service tomcat restart
