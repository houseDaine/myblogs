# 将springboot应用注册为windows服务

## 前言
`springboot`应用可以打包成可运行的jar包运行，用`java -jar myapp.jar`命令就可启动项目，但是在生产环境下，不可能启动项目就要开一个命令窗口，一般的方案是将应用设置成服务启动，如果打包成`war`包形式，放在tomcat等web服务器里启动，则将tomcat设为服务即可，如果是`jar`包，则该如何设置，`spring`[官方文档](http://docs.spring.io/spring-boot/docs/1.4.1.BUILD-SNAPSHOT/reference/htmlsingle/#deployment-windows)里推荐的是用`winsw`这款工具。

## winsw
`winsw`是一款可将可执行程序安装成`Windows Service`的开源小工具，官网地址：https://github.com/kohsuke/winsw   

    注意： 要使用winsw，电脑上必须已经安装`.NET framework`

### 下载
下载**winsw**:**:http://repo.jenkins-ci.org/releases/com/sun/winsw/winsw/，当前最新版本是`1.19`，下载后得到：`winsw-1.19-bin.exe`

### 使用
- 1.为了方便，将打包的项目jar和winsw放在同一目录，我这里放在D盘
- 2.将`winsw-1.19-bin.exe`重命名为和`myapp.jar`同名：`myapp.exe`
- 3.在当前目录下新建一个xml文件：`myapp.xml`,内容如下：

  ```
  <service>
    <id>myapp</id>
    <name>myapp</name>
    <description>This service runs myapp project.</description>
    <executable>java</executable>
    <arguments>-jar "myapp.jar"</arguments>
    <logmode>rotate</logmode>
  </service>
  ```
 相关参数说明如下：
 - id:id是安装成windows服务后的服务名，id必须是唯一的。
 - name：name是服务的简写名字，name也必须是唯一的，这里我设为和id相同。
 - description:服务的文字说明。
 - executable:执行的命令，因为启动springboot应用的命令是`java -jar myapp.jar`,所以这里写java，如果java没有安装到系统path里，则这里必须全路径。
 - arguments：命令执行参数， 如果端口号要在这里设置，可以在后面添上：`--server.port=8080`
 - 当然还有其它属性，请参考官网上的[说明](https://github.com/kohsuke/winsw)。

- 4.运行以下命令，将应用注册为服务：
  
  ```
  myapp.exe install
  ```
- 5.运行上面的命令后，在windows的服务里面能看到名为`myapp`的服务，到此安装成功。
- 6.除了`install`命令外，还有其它命令：

 - uninstall：删除服务
 - start：启动服务
 - stop：停止服务
 - restart：重启服务
 - status：输出当前服务的状态
