# 配置管理平台Apollo搭建指南

Acheron注：

Apollo 的官方文档写得非常友好全面，官方地址：https://github.com/ctripcorp/apollo/wiki

这里只是记录一下我在服务器上搭建的过程。

## 一、环境要求

- Java： 1.8+
  - 检查：`java -version`
- MySql：5.6.5+
  - 检查：`SHOW VARIABLES WHERE Variable_name = 'version';`

## 二、部署步骤

### 2.1 导入数据库生成脚本

官方提供的数据库脚本有两个，在`scripts/sql`下，`apolloportaldb.sql`和`apolloconfigdb.sql`

```
// 创建：ApolloPortalDB
source /your_local_path/sql/apolloportaldb.sql
// 创建：ApolloConfigDB
source /your_local_path/sql/apolloconfigdb.sql
// 检查是否导入成功：
select `Id`, `Key`, `Value`, `Comment` from `ApolloPortalDB`.`ServerConfig` limit 1;
select `Id`, `Key`, `Value`, `Comment` from `ApolloConfigDB`.`ServerConfig` limit 1;
```

### 2.2 Apollo自身的一些配置

#### 2.2.1 配置ApolloPortalDB.ServerConfig

- 1.apollo.portal.envs - 可支持的环境列表:

默认值是dev，多个以逗号分隔即可（大小写不敏感），如：

```
DEV,FAT,UAT,PRO
```

> 注意：只在数据库添加环境是不起作用的，需要配合修改scripts/build.sh，添加新增环境对应的meta server地址。

- organizations - 部门列表:

```
[{"orgId":"TEST1","orgName":"样例部门1"},{"orgId":"TEST2","orgName":"样例部门2"}]
```

- wiki.address: portal上“帮助”链接的地址，默认是Apollo github的wiki首页。


####2.2.2 配置ApolloConfigDB.ServerConfig
- 1.eureka.service.url - Eureka服务Url，如有多个，用逗号分隔（注意不要忘了/eureka/后缀）：

```
http://1.1.1.1:8080/eureka/,http://2.2.2.2:8080/eureka/

```

### 2.3 配置数据库连接信息

- vim scripts/build.sh

```
#apollo config db info
apollo_config_db_url=jdbc:mysql://localhost:3306/ApolloConfigDB?characterEncoding=utf8
apollo_config_db_username=用户名
apollo_config_db_password=密码（如果没有密码，留空即可）

# apollo portal db info
apollo_portal_db_url=jdbc:mysql://localhost:3306/ApolloPortalDB?characterEncoding=utf8
apollo_portal_db_username=用户名
apollo_portal_db_password=密码（如果没有密码，留空即可）
```



### 2.4 配置各环境meta service地址

- vim scripts/build.sh：修改各环境meta service服务地址。 如果某个环境不需要，也可以直接删除对应的配置项

```
dev_meta=http://localhost:8080
fat_meta=http://localhost:8080
uat_meta=http://localhost:8080
pro_meta=http://localhost:8080
```

### 2.5 执行编译、打包

```
./build.sh 
```
### 2.6 部署运行

#### 2.6.1 部署apollo-configservice

将`apollo-configservice/target/`目录下的`apollo-configservice-x.x.x-github.zip`上传到服务器上，解压后执行scripts/startup.sh即可。如需停止服务，执行scripts/shutdown.sh.

```
cd apollo-configservice/target/
scp apollo-configservice-x.x.x-github.zip 209:/opt/apollo/config
ssh 209
cd /opt/apollo/config
unzip apollo-configservice-x.x.x-github.zip
运行：./scripts/startup.sh
停止：./scripts/shutdown.sh
```

注：如要调整服务的监听端口，可以修改startup.sh中的`SERVER_PORT`。另外apollo-configservice同时承担meta server职责，如果要修改端口，注意要同时修改scripts/build.sh中的meta server url信息以及ApolloConfigDB.ServerConfig表中的`eureka.service.url`配置项。

#### 2.6.2 部署apollo-adminservice

同上：

将`apollo-adminservice/target/`目录下的`apollo-adminservice-x.x.x-github.zip`上传到服务器上，解压后执行scripts/startup.sh即可。如需停止服务，执行scripts/shutdown.sh.

> 注：如要调整服务的监听端口，可以修改startup.sh中的`SERVER_PORT`。

#### 2.6.3 部署apollo-portal

同上：

将`apollo-portal/target/`目录下的`apollo-portal-x.x.x-github.zip`上传到服务器上，解压后执行scripts/startup.sh即可。如需停止服务，执行scripts/shutdown.sh.

apollo-portal的默认端口是8080，和apollo-configservice一致，所以如果需要在一台机器上同时启动apollo-portal和apollo-configservice的话，需要修改apollo-portal的端口。直接修改startup.sh中的`SERVER_PORT`即可，如`SERVER_PORT=8070`。

## 三、Java客户端使用

### 3.1 配置Appid 

classpath:/META-INF/app.properties文件：

> app.id=YOUR-APP-ID

### 3.2 配置Environment

- 对于Mac/Linux，文件位置为`/opt/settings/server.properties`

- 对于Windows，文件位置为`C:\opt\settings\server.properties`

保证settings目录文件权限：chmod 777 /opt/settings

文件内容形如：

```
env=DEV
```

### 3.3 配置本地缓存路径

本地缓存路径位于以下路径，所以请确保`/opt/data`或`C:\opt\data\`目录存在，且应用有读写权限。

- **Mac/Linux**: /opt/data/{*appId*}/config-cache
- **Windows**: C:\opt\data\{*appId*}\config-cache

保证data目录文件权限：chmod 777 /opt/data

### 3.4 配置日志地址

保证logs目录文件权限：chmod 777 /opt/logs

### 3.5 Maven Dependency

```
<dependency>
        <groupId>com.ctrip.framework.apollo</groupId>
        <artifactId>apollo-client</artifactId>
        <version>0.7.0</version>
 </dependency>
```

### 3.6 使用

```
@Configuration
@EnableApolloConfig
public class AppConfig {
  
}
```



```
@ApolloConfig
private Config config;

@Test
public void testApollo(){
	String name = config.getProperty("name", "hello");
    assertEquals(name,"Acheron");
}
```

