# 用winsw将springboot应用安装为windows服务启动

## 前言
`springboot`应用可以打包成可运行的jar包运行，用`java -jar myapp.jar`命令就可启动项目，但是在生产环境下，不可能启动项目就要开一个命令窗口，一般的方案是将应用设置成服务启动，如果打包成`war`包形式，放在tomcat等web服务器里启动，则将tomcat设为服务即可，如果是`jar`包，则该如何设置，`spring`[官方文档](http://docs.spring.io/spring-boot/docs/1.4.1.BUILD-SNAPSHOT/reference/htmlsingle/#deployment-windows)里推荐的是用`winsw`这款工具。

## winsw



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