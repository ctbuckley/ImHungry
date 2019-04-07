sudo groupadd tomcat
sudo mkdir /opt/tomcat
sudo useradd -s /bin/nologin -g tomcat -d /opt/tomcat tomcat

cd ~
wget https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.17/bin/apache-tomcat-9.0.17.tar.gz
sudo tar -zxvf apache-tomcat-9.0.17.tar.gz -C /opt/tomcat --strip-components=1 &> /dev/null

cd /opt/tomcat
sudo chgrp -R tomcat /opt/tomcat
sudo chmod -R g+r conf
sudo chmod g+x conf
sudo chown -R tomcat webapps/ work/ temp/ logs/

sudo update-java-alternatives -l

echo -e "[Unit]\nDescription=Apache Tomcat Web Application Container\nAfter=syslog.target network.target\n\n[Service]\nType=forking\n\nEnvironment=JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64\nEnvironment=CATALINA_PID=/opt/tomcat/temp/tomcat.pid\nEnvironment=CATALINA_HOME=/opt/tomcat\nEnvironment=CATALINA_BASE=/opt/tomcat\nEnvironment='CATALINA_OPTS=-Xms512M -Xmx1024M -server -XX:+UseParallelGC'\nEnvironment='JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom'\n\nExecStart=/opt/tomcat/bin/startup.sh\nExecStop=/bin/kill -15 $MAINPID\n\nUser=tomcat\nGroup=tomcat\n\n[Install]\nWantedBy=multi-user.target" | sudo tee /etc/systemd/system/tomcat.service
sudo systemctl daemon-reload

sudo systemctl start tomcat
sudo systemctl status tomcat

sudo ufw allow 8080