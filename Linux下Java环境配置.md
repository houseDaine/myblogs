# Linux下Java环境配置 

---

## 查看Java是否安装
```
java -version
```

## JDK的下载
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

## 创建安装目录
```
mkdir /usr/java
```
## 拷备: 
```
cp jdk-jdk-8u60-linux-i586.tar.gz /usr/java
```

## 解压
```
tar zvxf jdk-8u60-linux-i586.tar.gz
```

## 使用root权限
```
su root
```

如果切换su root时提示Authentication failure：
```
$ su  root
Password:
su: Authentication failure
Sorry.
```
则如下操作那可:
```
$ sudo passwd root
Enter new UNIX password:
Retype new UNIX password:
passwd: password updated successfully
```
这时候就可以进入根目录了。

## 环境变量配置
```
vim /etc/profile
```

在/etc/profile中配置环境变量:
```
export JAVA_HOME=/usr/java/jdk1.8.0_60
export JRE_HOME=$JAVA_HOME/jre
export CLASSPATH=$JAVA_HOME/lib:$JRE_HOME/lib:$CLASSPATH
export PATH=$JAVA_HOME/bin:$JRE_HOME/bin:$PATH
```

## 生效
```
source /etc/profile
```