# Install tomcat on linux

---
### First 
```
su root
```

### Download：
```
wget http://apache.fayea.com/tomcat/tomcat-7/v7.0.64/bin/apache-tomcat-7.0.64.tar.gz
```
### Copy
```
cp apache-tomcat-7.0.64.tar.gz /usr/local/
```

### tar
```
tar zxvf apache-tomcat-7.0.64.tar.gz 
```

### startup
```
cd apache-tomcat-7.0.64/bin
./startup.sh
```
### success
```
http://localhost:8080/
```

### close
```
./shutdown.sh
```






