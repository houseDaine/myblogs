


## JENKINS_HOME目录更改

https://wiki.jenkins-ci.org/display/JENKINS/Administering+Jenkins

 	主目录	C:\Users\Acheron\.jenkins	Help for feature: 主目录
		
	
Jenkins储存所有的数据文件在这个目录下. 你可以通过以下几种方式更改：

    使用你Web容器的管理工具设置JENKINS_HOME环境参数.
    在启动Web容器之前设置JENKINS_HOME环境变量.
    (不推荐)更改Jenkins.war(或者在展开的Web容器)内的web.xml配置文件. 

这个值在Jenkins运行时是不能更改的. 其通常用来确保你的配置是否生效.
