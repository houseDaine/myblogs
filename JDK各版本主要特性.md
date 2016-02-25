# JDK各版本主要特性

标签（空格分隔）： java

---

- 整理了JDK各版本的主要特性,其中各版本可以在这里[下载](http://www.oracle.com/technetwork/java/archive-139210.html)

## JDK1.0
> 发布时间: 1996年1月23日

jdk1.0当时还是一个纯解释执行的Java虚拟机，jdk1.0版本的代表技术：Java虚拟机，Applet，AWT等。

## JDK1.1
> 发布时间: 1997年2月18日

jdk1.1的代表技术：JAR文件格式，JDBC，JavaBean，RMI , 内部类，反射。
 
## JDK1.2
> 发布时间: 1998年12月4日

技术体系被分为三个方向，J2SE，J2EE，J2ME。代表技术：EJB，Java Plug-in，Swing。该版本中虚拟机第一次内置了JIT编译器。语言上：Collections集合类等。
 
## JDK1.3
> 发布时间: 2000年5月8日

jdk1.3对于jdk1.2的改进重要在于一些类库，JNDI服务从jdk1.3开始被作为一项平台级服务提供。
 
## JDK1.4
> 发布时间: 2002年2月13日

该版本是Java走向成熟的一个版本，代表技术：正则表达式，异常链，NIO，日志类，XML解析器等。

## JDK1.5
> 发布时间: 2004年9月29日

1. 自动装箱与拆箱：
2. 枚举
3. 静态导入
4. 可变参数
5. 内省
6. 泛型
7. For-Each循环 
 
## JDK1.6
> 发布时间: 2006年12月11日

1. Desktop类和SystemTray类 
2. 使用JAXB2来实现对象与XML之间的映射 
3. 理解StAX 
4. 使用Compiler API 
5. 轻量级Http Server API 
6. 插入式注解处理API(Pluggable Annotation Processing API) 
7. 用Console开发控制台程序 
8. 对脚本语言的支持如: ruby, groovy, javascript. 
9. Common Annotations 
10. Web服务元数据
11. JTable的排序和过滤
12. 更简单,更强大的JAX-WS
13. 嵌入式数据库 Derby
 
## JDK1.7
> 发布时间: 2011年7月28日

1. 对集合（Collections）的增强支持
2. 在Switch中可用String
3. 数值可加下划线 例如：int one_million = 1_000_000;
4. 支持二进制文字  例如：int binary = 0b1001_1001;
5. 简化了可变参数方法的调用
6. 运用List tempList = new ArrayList<>(); 即泛型实例化类型自动推断
7. 语法上支持集合，而不一定是数组
8. 新增一些取环境信息的工具方法
9. Boolean类型反转，空指针安全,参与位运算
10. 两个char间的equals
11. 安全的加减乘除
12. map集合支持并发请求，且可以写成 Map map = {name:"xxx",age:18};
 
## JDK8
> 发布时间 :  2014年3月

1. 允许在接口中有默认方法实现
2. Lambda 表达式
3. 函数式接口 @FunctionalInterface
4. 方法与构造函数引用
5. 使用 :: 关键字来传递方法或者构造函数引用
6. java.util.stream 
7. 支持多重注解了
8. IO/NIO 的改进   
9. 安全性上的增强
10. 新的日期/时间API java.time

## JDK9(未发布)
1. Jigsaw 项目;模块化源码
2. 简化进程API
3. 轻量级 JSON API
4. 钱和货币的API
5. 改善锁争用机制
6. 代码分段缓存
7. 智能Java编译, 第二阶段
8. HTTP 2.0客户端
9. Kulla计划: Java的REPL实现