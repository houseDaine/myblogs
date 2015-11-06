# Java开发环境-配置

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
CLASSPATH : `%JAVA_HOME%\dt.jar;%JAVA_HOME%\tools.jar;`
PATH : `%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;`

二、Tomcat配置
> tomcat 只要解压即可，需要配置的地方是服务端口，默认是8080，在
 `~\conf\server.xml`中可修改`<Connector port="8080" protocol="HTTP/1.1".....`
 
 
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










