
# Code Review 工具Phabricator安装配置及使用教程

## Phabricator是什么

在官网上，Phabricator被描述成一个完整的软件开发平台，说通俗点，其实就是一种Code Review工具。官网地址：https://www.phacility.com/

## 同类工具

- Gerrit：https://www.gerritcodereview.com/
- Bitbucket：https://bitbucket.org/product
- ReviewBoard：https://www.reviewboard.org/
- FishEye：https://www.atlassian.com/software/fisheye
- Upsource：https://www.jetbrains.com/upsource/
- Sonarqube：https://www.sonarqube.org/

## 安装要求

安装Phabricator时，要注意的一点就是Mysql和php的版本要求。另外因为其是Php写的，所以一个基本的环境就是`LAMP`环境。下面是官网列出的一些需要安装的环境：

- MySQL: You need MySQL. We strongly recommend MySQL 5.5 or newer.
- PHP: You need PHP 5.2 or newer.
- git (usually called "git" in package management systems)
- Apache (usually "httpd" or "apache2") (or nginx)
- MySQL Server (usually "mysqld" or "mysql-server")
- PHP (usually "php")
- Required PHP extensions: mbstring, iconv, mysql (or mysqli), curl, pcntl (these might be something like "php-mysql" or "php5-mysql")
- Optional PHP extensions: gd, apc (special instructions for APC are available below if you have difficulty installing it), xhprof (instructions below, you only need this if you are developing Phabricator)

## 开始安装

### 1. Phabricator安装及配置

- 建一个安装目录：mkdir phabricator

- 进入这个目录，获取Phabricatort和它的安装包：

  ```
  $ cd somewhere/ # pick some install directory
  somewhere/ $ git clone https://github.com/phacility/libphutil.git
  somewhere/ $ git clone https://github.com/phacility/arcanist.git
  somewhere/ $ git clone https://github.com/phacility/phabricator.git
  ```


```
phabricator/ $ ./bin/config set mysql.host value
phabricator/ $ ./bin/config set mysql.port value
phabricator/ $ ./bin/config set mysql.user value
phabricator/ $ ./bin/config set mysql.pass value

```


### 2. Apache安装及配置 
vim /etc/httpd/conf/httpd.conf

```
 ServerName 192.168.1.209
<VirtualHost *>
  # Change this to the domain which points to your host.

  # Change this to the path where you put 'phabricator' when you checked it
  # out from GitHub when following the Installation Guide.
  #
  # Make sure you include "/webroot" at the end!
  DocumentRoot /opt/data/phabricator/phabricator/webroot

  RewriteEngine on
  RewriteRule ^(.*)$          /index.php?__path__=$1  [B,L,QSA]
</VirtualHost>

<Directory "/opt/data/phabricator/phabricator/webroot">
  Order allow,deny
  Allow from all
</Directory>
~
```

/etc/init.d/httpd start

### 3. Mysql安装及配置

### 4. Nginx安装及配置 
```
erver {
        listen       80;
        server_name  localhost;
    root        /opt/data/phabricator/phabricator/webroot;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            #root   html;
            #index  index.html index.htm;
             index index.php;
        rewrite ^/(.*)$ /index.php?__path__=/$1 last;
        }
```

./usr/local/nginx/sbin/nginx

## 使用入门

启动后，界面没反应，要开启daemons
https://secure.phabricator.com/book/phabricator/article/managing_daemons/



