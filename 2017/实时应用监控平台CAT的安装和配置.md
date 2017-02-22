# 实时应用监控平台CAT安装和配置

## CAT是什么
CAT是一个Java语言编写的实时监控系统，能够跟各种流行的中间件框架集成（MVC框架、RPC框架、数据库框架、缓存框架等），实现对应用各层级的系统运行状况监控。

- 官网： [https://github.com/dianping/cat](https://github.com/dianping/cat) 


## CAT支持的监控消息类型

- Transaction: 适合记录跨越系统边界的程序访问行为,比如远程调用，数据库调用，也适合执行时间较长的业务逻辑监控，Transaction用来记录一段代码的执行时间和次数。
- Event :用来记录一件事发生的次数，比如记录系统异常，它和transaction相比缺少了时间的统计，开销比transaction要小。
- Heartbeat: 表示程序内定期产生的统计信息, 如CPU%, MEM%, 连接池状态, 系统负载等。
  Metric 用于记录业务指标、指标可能包含对一个指标记录次数、记录平均值、记录总和，业务指标最低统计粒度为1分钟。
- Trace: 用于记录基本的trace信息，类似于log4j的info信息，这些信息仅用于查看一些相关信息


##运行环境

- Java 6 以上
- Web 应用服务器，如：Apache Tomcat、JBoss Application Server、WebSphere Application Server、WebLogic Application Server（可选项，内置Netty应用服务器）
- MySQL 数据库
- Maven 3 以上（只编译和安装时需要）



## 开始安装

前提：已安装jdk、git、maven、mysql

- 获取源码：

  ```
  git clone https://github.com/dianping/cat.git
  ```

- 进入cat目录，执行maven构建命令

  ```
  cd cat
  clean install -Dmaven.test.skip=true
  ```

此处如果编译不成功，不能下载相关jar包，则可以手动下载，包在cat的`mvn-repo`分支下(https://github.com/dianping/cat/tree/mvn-repo) ,下载后拷贝到本地的maven仓库下

  ```
  git checkout mvn-repo
  cp -R * ~/.m2/repository
  ```

- 将构建成功后的war包重命名，并拷贝到tomcat的webapp下

  ```
  mv cat-alpha-1.4.0.war cat.war
  cp cat.war tomcat/webapp
  ```

- 启动tomcat，访问如下地址，后台默认登录`catadmin/catadmin`

  ```
  http://localhost:8080/cat/
  ```

## 创建数据库

- 安装好mysql后，登录mysql，创建表空间

  ```
  create database cat；
  ```

- 执行cat源码下的数据库脚本`cat/script/Cat.sql`，创建相关表

  ```
  use cat；
  source Cat.sql;
  ```

## 修改服务配置

- 到`/data/appdatas/cat/`下，如果没有则新建此目录，**确保这个目录有读写权限**

  ```
  cd /data/appdatas/cat/
  ```

- 将cat源码下`script`目录下的三个配置文件(client.xml、server.xml、datasources.xml)拷贝到上述目录

  ```
  cp /cat/script/*.xml /data/appdatas/cat/
  ```

*** 修改配置文件之前的几项假设：***

- cat.war 包部署在10.8.40.26、10.8.40.27、10.8.40.28三台机器上,10.8.40.26为三台机器中的主服务器，TCP端口只能局域网内访问；
- 数据库采用 MySQL安装在10.8.40.147上；
- 暂不启用HDFS存储服务；
- 暂不启用LDAP服务；

### 1. 修改客户端配置文件：client.xml

打开/data/appdatas/cat/client.xml客户端配置文件，

``` 
<config mode="client"
xmlns:xsi="http://www.w3.org/2001/XMLSchema"
xsi:noNamespaceSchemaLocation="config.xsd">
    <servers>
        <server ip="10.8.40.26" port="2280" http-port="8080" />
        <server ip="10.8.40.27" port="2280" http-port="8080" />
        <server ip="10.8.40.28" port="2280" http-port="8080" />
    </servers>
</config>
```

配置说明：

- mode : 定义配置模式，固定值为client;--暂未使用
- servers : 定义多个服务端信息;
- server : 定义某个服务端信息;
- ip : 配置服务端（cat-home）对外IP地址
- port : 配置服务端（cat-home）对外TCP协议开启端口，固定值为2280;
- http-port : 配置服务端（cat-home）对外HTTP协议开启端口, 如：tomcat默认是8080端口，若未指定，默认为8080

###  2. 修改数据库配置文件：datasources.xml

打开/data/appdatas/cat/datasources.xml数据库配置文件，

```
<data-sources>
    <data-source id="cat">
        <maximum-pool-size>3</maximum-pool-size>
        <connection-timeout>1s</connection-timeout>
        <idle-timeout>10m</idle-timeout>
        <statement-cache-size>1000</statement-cache-size>
        <properties>
            <driver>com.mysql.jdbc.Driver</driver>
            <url><![CDATA[jdbc:mysql://10.8.40.147:3306/cat]]></url>
            <user>root</user>
            <password>mysql</password>
            <connectionProperties>
                <![CDATA[useUnicode=true&autoReconnect=true]]>
            </connectionProperties>
        </properties>
    </data-source> 
    <data-source id="app">
        <maximum-pool-size>3</maximum-pool-size>
        <connection-timeout>1s</connection-timeout>
        <idle-timeout>10m</idle-timeout>
        <statement-cache-size>1000</statement-cache-size>
        <properties>
            <driver>com.mysql.jdbc.Driver</driver>
            <url><![CDATA[jdbc:mysql://10.8.40.147:3306/cat]]></url>
            <user>root</user>
            <password>mysql</password>
            <connectionProperties>
                <![CDATA[useUnicode=true&autoReconnect=true]]>
            </connectionProperties>
        </properties>
    </data-source>
</data-sources>
```

配置说明：

- 主要修改项为：url（数据库连接地址）、user（数据库用户名）、password（数据用户登录密码）

###  3.修改服务端服务配置:server.xml 

　打开/data/appdatas/cat/server.xml服务端服务配置文件，

```
<config local-mode="false" hdfs-machine="false" job-machine="true" alert-machine="true">
    <storage local-base-dir="/data/appdatas/cat/bucket/" max-hdfs-storage-time="15" local-report-storage-time="7" local-logivew-storage-time="7">
        <hdfs id="logview" max-size="128M" server-uri="hdfs://10.8.40.31/user/cat" base-dir="logview"/>
        <hdfs id="dump" max-size="128M" server-uri="hdfs://10.8.40.32/user/cat" base-dir="dump"/>
        <hdfs id="remote" max-size="128M" server-uri="hdfs://10.8.40.33/user/cat" base-dir="remote"/>
    </storage>
    <console default-domain="Cat" show-cat-domain="true">
        <remote-servers>10.8.40.26:8080,10.8.40.27:8080,10.8.40.28:8080</remote-servers>
    </console>
    <ldap ldapUrl="ldap://10.8.40.21:389/DC=dianpingoa,DC=com"/>
</config>
```

配置说明：

- local-mode : 定义服务是否为本地模式（开发模式），在生产环境时，设置为false,启动远程监听模式。默认为 false;
- hdfs-machine : 定义是否启用HDFS存储方式，默认为 false；
- job-machine : 定义当前服务是否为报告工作机（开启生成汇总报告和统计报告的任务，只需要一台服务机开启此功能），默认为 false；
- alert-machine : 定义当前服务是否为报警机（开启各类报警监听，只需要一台服务机开启此功能），默认为 false；
- storage : 定义数据存储配置信息
- local-report-storage-time : 定义本地报告存放时长，单位为（天）
- local-logivew-storage-time : 定义本地日志存放时长，单位为（天）
- local-base-dir : 定义本地数据存储目录
- hdfs : 定义HDFS配置信息，便于直接登录系统
- server-uri : 定义HDFS服务地址
- console : 定义服务控制台信息
- remote-servers : 定义HTTP服务列表，（远程监听端同步更新服务端信息即取此值）
- ldap : 定义LDAP配置信息
- ldapUrl : 定义LDAP服务地址

### 4.修改路由配置

登入 cat系统，修改路由配置，打开浏览器，输入[http://10.8.40.26:8080/cat/](http://10.8.40.26:8080/cat/)

在`配置--》全局告警配置--》客户端路由`，进入配置路由界面：

```
<?xml version="1.0" encoding="utf-8"?>
<router-config backup-server="127.0.0.1" backup-server-port="2280">
   <default-server id="127.0.0.1" weight="0.8" port="2280" enable="true"/>
   <network-policy id="default" title="default" block="false" server-group="default_machine">
   </network-policy>
   <server-group id="default_machine" title="????">
      <group-server id="127.0.0.1"/>
   </server-group>
   <domain id="cat">
      <group id="default">
         <server id="127.0.0.1" port="2280" weight="1.0"/>
      </group>
   </domain>
</router-config>
```

- 把backup-server设置为当前服务器对外IP地址，端口固定为2280;

* default-server定义可跳转的路由地址，可以设置多个。default-server的id属性配置可路由的cat-home服务IP地址，端口固定为2280;若需要禁用路由地址，可把enable设置为false。
* 点击“提交”按钮，保存修改的路由配置


### 5. 复制配置到27、28两机器

- 拷贝 10.8.40.26机器/data/appdatas/cat/目录中client.xml、server.xml、datasources.xml三个配置文件到27、28两机器相同目录中

- 修改server.xml配置中的 job-machine 和 alert-machine属性，都设置为false,禁用生成报告和报警功能，只开启监听功能
- 启动27、28上的Tomcat,开启cat服务，完成服务端的配置及启动
- （*若服务端只分配一台服务器，按10.8.40.26完成安装配置即可*）


*这里记录我安装配置的过程，更详细的文档查看[官网](https://github.com/dianping/cat)*