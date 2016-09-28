# 用winsw将springboot应用安装为windows服务启动

## 前言
`springboot`应用可以打包成可运行的jar包运行，用`java -jar myapp.jar`命令就可启动项目，但是在生产环境下，不可能启动项目就要开一个命令窗口，一般的方案是将应用设置成服务启动，如果打包成`war`包形式，放在tomcat等web服务器里启动，则将tomcat设为服务即可，如果是`jar`包，则该如何设置，`spring`[官方文档](http://docs.spring.io/spring-boot/docs/1.4.1.BUILD-SNAPSHOT/reference/htmlsingle/#deployment-windows)里推荐的是用`winsw`这款工具。

## winsw
`winsw`是一款可将可执行程序安装成`Windows Service`的开源小工具，地址：https://github.com/kohsuke/winsw   

    注意： 要使用winsw，电脑上必须已经安装`.NET framework`

### [下载](http://repo.jenkins-ci.org/releases/com/sun/winsw/winsw/)
点击上面的下载地址，下载`winsw`，当前最新版本是`1.19`，



https://github.com/kohsuke/winsw

https://github.com/kohsuke/winsw


```
<service>
  <id>myapp</id>
  <name>myapp</name>
  <description>This service runs myapp project.</description>
  <executable>java</executable>
  <arguments>-jar "myapp.jar" --server.port=8080</arguments>
  <logmode>rotate</logmode>
</service>

```