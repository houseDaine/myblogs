# spring定时器的使用

定时器的作用就是让程序在某个特定的时间被触发，自动执行一段代码。最近工作中做会议管理用到了定时器，当会议的结束时间已经过了，那么让会议的状态自动变为已结束，我用spring的两种定时器方式来实现，Java的Timer类和OpenSymphony的Quartz。

一、Timer类定时器实现

1.新建一个类，继承java.util.TimerTask的TimerTask，实现它的run方法，在run方法中做一些相关操作

```
public class MyTimeTask extends TimerTask {
  @Override
  public void run() {
    //在这里写你的操作
    System.out.print("Coming in the method.......");
  }
}

```

2.在spring的配置文件中，配置spring的定时器，其中timerTask属性告诉ScheduledTimerTask运行哪个定时器,period属性指每隔多长时间执行一次，这里3600000指一小时，delay属性指在服务器启动后，延迟多长时间开始执行。

```
<bean id="myTimeTask" class="com.iacheron.MeetingTimeTask"></bean>
        
<bean id="scheduleReportTask" class="org.springframework.scheduling.timer.ScheduledTimerTask">
        <property name="timerTask" ref="myTimeTask" />    
        <property name="period">
            <value>3600000</value>
        </property>
        <property name="delay" >
            <value>10000</value>
        </property>
</bean>

```

3.启动定时器,在spring配置文件中加TimerFactoryBean,负责启动

```
<bean class="org.springframework.scheduling.timer.TimerFactoryBean">
  <property name="scheduledTimerTasks"> 
    <list>
      <ref bean="scheduleReportTask"/>
    </list> 
  </property> 
</bean>

```

 

二、Quartz定时器实现

用quartz来实现定时器有两种方式，一种是在配置文件中设置任务类，一种是任务类继承QuartzJobBean(和前一种基本上差不多，这里不提)，前一种方式因为类还是普通类，所以更加灵活。

1.新建一个类，在类中建一个方法，方法名自定义

```
public class MeetingJob {
     public void execute(){
          System.out.println("do something");
     }
}
     

```

 2.在spring配置文件中定义目标bean和要执行的方法，targetObject引入目标bean，targetMethod的value属性为在第一步中类中的方法名

```
<bean id="meetingJob" class="com.iacheron.MeetingJob"/> 
<bean id="meetingJobMethod" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean"> 
     <property name="targetObject"> 
         <ref bean="meetingJob"/> <!--目标bean的id-->
     </property> 
     <property name="targetMethod"> 
         <value>execute</value> <!-- 要执行的方法名称 -->
     </property> 
</bean>

```

3.配置一个触发器，这里要用cronExpression表达式来设置时间

```
<bean id="cronTriggerBean" class="org.springframework.scheduling.quartz.CronTriggerBean">   
  <property name="jobDetail" ref="meetingJobMethod" />  
  <property name="cronExpression" value="0/5 * * * * ?"></property> 5秒发送一次 
</bean>

```

4.配置一个调度工厂

```
<bean id="springJobSchedulerFactoryBean" class="org.springframework.scheduling.quartz.SchedulerFactoryBean">  
   <property name="triggers">  
       <list><ref bean="cronTriggerBean"/></list>  
   </property>  
</bean>
```