# windows/linux Java开发环境-配置

标签（空格分隔）： java
---
## 服务器端
| Item      |    version| download|
| :-------- | --------:| :--: |
| JDK		| 	1.7 	|  http://www.oracle.com  	|
| tomcat	| 	7 		|  http://tomcat.apache.org 	|
| nexus		| 	2.8.0 	|  http://www.sonatype.org/nexus |
| maven		| 	3+ 		|  http://maven.apache.org	|

## 本地
暂略接口.....


## 相关配置
### 一、JDK环境变量配置
> 
JAVA_HOME : `C:\Java\jdk1.7.0_65`
CLASSPATH : `%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;`
PATH : `%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;`

注意:安装jdk8时,环境中的JAVA_HOME仍然用的jdk7,但是java -version后显示的却是jdk8,解决方法如下:
1. 安装jdk8后【系统变量】中的Path开头多了：`C:\ProgramData\Oracle\Java\javapath`,删除这个路径
2. 删除c:\windows\system32下java.exe,javaw.exe,javaws.exe

二、Tomcat配置
1. tomcat 只要解压即可，需要配置的地方是服务端口，默认是8080，在
 `~\conf\server.xml`中可修改`
< Connector port="8080" protocol="HTTP/1.1".....

2.conf.xml:厉害配置 < Context docBase="wm_cityplatform"  path="" reloadable="true" crossContext="true"/>
> 

3. tomcat安装成服务 管理员身份运行c:\windows\system32\cmd.exe:
到tomcat bin目录
  `service.bat install tomcat_cityplatform

  删除服务：
  `service.bat remove tomcat_cityplatform` 或者：` sc delete tomcat_cityplatform`

4.重启: 
 管理员权限打开c:\windows\system32\cmd.exe:命令行-->taskmgr-->结束对应进程 -->结束对应服务
 
### 三、Nexus配置
1. 配置PATH:
> c:\nexus-2.8.0-05\bin;
2. 配置端口
 > 默认是8081，在`~\nexus-2.8.0-05\conf\nexus.properties
   `中可修改：`application-port=8081`

3. 安装windows服务：
 >运行\nexus-2.8.0\bin\jsw\windows-x86-64下的install-nexus.bat 即可，选择对应的系统。
4. 启动nexus： 
>//以管理员的身份在DOS里运行如下命令：
nexus install
nexus start

5. 启动成功
> 访问`http://localhost:8081/nexus`，进入nexus登录页。

6. 开启远程索引:
 > 将以下三个仓库
* `Apache Snapshots`，
* `Codehaus Snapshots`，
* `Central`
的`Download Remote Indexes`修改为true

7. 加代理仓库
一般加几个主流的即可
* mvnrepository:http://www.mvnrepository.com/
* jboss:https://repository.jboss.org/
* oschina: http://maven.oschina.net/content/groups/public/



----

# Linux下Java环境配置 

标签（空格分隔）： java
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
切换回普通用户时，重新运行一遍生效命令。

##linux tomcat 安装

mv apache-tomcat-7.0.2.tar.gz /usr/local
tar -zxvf apache-tomcat-7.0.2.tar.gz
cd /usr/local/apache-tomcat-7.0.2/bin

设置tomcat java环境变量，修改catalina.sh文件：
vim apache-tomcat-7.0.2/bin/catalina.sh
在文件开头注释后插入：
JAVA_HOME="/usr/java/jdk1.6.0_12"

启动：
./startup.sh
关闭：
./shutdown.sh

查看tomcat是否启动，查看到进程号4003：
ps -ef|grep java

直接杀死tomcat进程 kill -9 进程号：
kill -9 4003




远程查看tomcat的控制台 
进入tomcat/logs/文件夹下 
键入指令：tail -f catalina.out 
就可以查看控制台了









