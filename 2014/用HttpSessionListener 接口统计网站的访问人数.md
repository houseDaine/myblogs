# 用HttpSessionListener 接口统计网站的访问人数

当一个浏览器第一次访问网站的时候，应用服务器会新建一个HttpSession对象 ，并触发 HttpSession创建事件 ，如果注册了HttpSessionListener事件监听器，则会调用HttpSessionListener事件监听器的sessionCreated方法。相反，当这个浏览器访问结束超时的时候，应用服务器会销毁相应的HttpSession对象，触发 HttpSession销毁事件，同时调用所注册HttpSessionListener事件监听器的sessionDestroyed方法。所以用HttpSessionListener接口可以统计网站的访问人数。

第一步：新建一个session监听器实现HttpSessionListener接口，HttpSessionListener接口只有两个方法，session创建的时候调用sessionCreated方法，session销毁的时候调用sessionDestroyed方法。

```
     public class VistorCountSessionListener implements HttpSessionListener {

      @Override
      public void sessionCreated (HttpSessionEvent event) {
     }

      @Override
      public void sessionDestroyed (HttpSessionEvent event) {
      }
}

```

第二步：  在web.xml中配置上述建立的监听器

```
<listener>
   <listener-class>com.component.webInfo.listener.VistorCountSessionListener</listener-class>
</listener>

```

第三步：在sessionCreated方法中完成相关方法。思路如下，将人数信息存于数据库表VistorCount中，表有两个字段，ID（varchar），count（number）。当一次访问的时候，因为表中无数据，所以保存一条数据，访问人数count默认为1，第二次访问时，将对应的数据增加  1即可。为了方法不同用户在同时访问，相关方法应该用synchronized锁定。

```
     //在sessionCreated 方法中，调用相关service的更新访问人数的方法
      @Override
      public void sessionCreated (HttpSessionEvent event) {
          IVistorCountService service = ApplicationContextUtil.getContext().getBean(IVistorCountService.class);
          service .updateVistorCount ();
      }

```

IVistorCountService接口实现中的updateVistorCount 如下：

```
      @Override
      public synchronized void updateVistorCount() {
            //根据ID查找Bean对象
            VistorCount bean = dao.select(VistorCount.VISTOR_COUNT_ID);
            //如果bean为空，说明是第一次访问，则添加一条新数据，否则在原来的访问数上加1
            if( bean == null) {
                bean = new VistorCount();
                bean.setId(VistorCount.VISTOR_COUNT_ID);
                bean.setCount(1);
                 dao.insert( bean);
            } else {
                bean.setCount(bean.getCount()+1 );
                 dao.updateNotNull(bean);
            }
      }

```

 