# Linux系统MySQL的安装与使用

---

## 安装与配置MySQL    
### 安装MySQL数据库
MySQL数据库分服务器和客户端，服务器用于管理和维护数据库，客户端用于连接和访问数据库，可以用下列命令安装服务器和客户端。安装期间将会提示输入数据库管理员root的密码。
```
sudo apt-get install mysql-server
```
### 配置文件my.cnf
MySQL的配置文件是/etc/mysql/my.cnf,主要用于配置数据库文件的存储位置，日志文件等，以下是安装之后默认的配置参数。

![mysql配置参数1](http://www.iacheron.com/wp-content/uploads/2015/07/2015.07.24_19h25m36s_003_.jpg)
![mysql配置参数2](http://www.iacheron.com/wp-content/uploads/2015/07/2015.07.24_19h25m24s_002_.jpg)
注意：
每次修改my.cnf时，都需要重新启动mysqld守护进程，可以使用如下命令：
```
sudo /etc/init.d/mysql restart
```

## MySQL的简单使用
### 使用mysql
进入mysql命令环境，使用如下命令，-u后跟用户名，-p后跟密码，没有则为空
```
mysql -u -root -p 
```

### 创建 查询 使用和删除数据库
创建：
```
create database test;
```
查询： 
```
show databases;
```
使用：
```
use test;
```
删除：
```
drop database test;
```

###设置用户及访问权限
mysql的用户和密码都存在一个专用的数据库mysql中，管理员root可以在其中添加用户及赋权，命令如下：
```
GRANT ALL PRIVILEGES ON database TO username@"servername" IDENTIFIED  BY 'password';
```
如果flush配置变量没有设为ON，或者启动mysql进程时没有使用“--flush”选项，需要使用flush命令，才能使添加的帐户的生效。
```
flush privileges
```
## SQL脚本与批处理
可以将多条命令写入一个文件，以I/O的方式运行mysql，批量执行命令，命令如下 ：
```
mysql -u username -p [password] < scriptfile
```
例如新建一个文件：test.sql,内容如下：
```
show databases;
use test;
show tables;
select * from user;
```

利用命令执行,则将会显示执行结果：
```
mysql -u root -p < test.sql;
```
如果是在mysql交互环境下，则可用“source” 或 “ \ . ” 命令运行sql脚本：
```
source test.sql;
```
或
```
\. test.sql
```