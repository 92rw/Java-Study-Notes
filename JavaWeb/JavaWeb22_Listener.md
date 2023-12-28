# 监听器Listener

## 八种监听器概述

ServletContext监听器

* ServletContextListener监听ServletContext对象
* ServletContextAttributeListener监听对ServletContext属性的操作，比如增加、删除、修改

HttpSession监听器

* HttpSessionListener监听Session对象创建或销毁，即生命周期监听（可用于监控用户上线sessionCreated方法/离线sessionDestroyed方法）
* HttpSessionAttributeListener监听Session中的属性操作
* HttpSessionActivationListener感知HTTP会话的active和passivate情况，passivate是指非活动的session被写入（钝化）到持久设备（比如硬盘），active相反
  * 注意：HttpSessionActivationListener不需要web.xml配置文件
* HttpSessionBindingListener感知Session绑定的事件监听器（可实现一对一监听，不需要在web.xml文件配置）
  注意：HttpSessionBindingListener不需要web.xml配置文件

ServletRequest监听器

* ServletRequestListener监听Request对象（监控某个IP访问网站的频率，记录用户访问的链接）
* ServletRequestAttributeListener监听Requset中的属性操作

## 说明

1. Listener监听器它是JavaWeb的三大组件之一。JavaWeb的三大组件分别是：Servlet程序、Listener监听器、Filter过滤器
2. Listener是JavaEE的规范，就是接口
3. 监听器的作用是，监听某种变化（对象创建/销毁，属性增删改），触发对应方法完成相应的任务
4. JavaWeb中的监听器（共八个），目前最常用的是ServletContextListener和HttpSessionListener

### ServletContextListener监听器

1. 作用：监听ServletContext创建或销毁（当Web应用启动时，就会创建ServletContext），即生命周期监听
2. 应用场景（1)加载初始化的配置文件，比如spring的配置文件（2）任务调度（配合定时器Timer/TimerTask）
3. 相关方法

```java
public void contextInitialized(ServletContextEvent sce)//创建Servletcontext时触发void
public void contextDestroyed(ServletContextEvent sce)//销毁Servletcontext时
```

### ServletContextAttributeListener监听器

重写的三个方法分别对应ServletContext属性的添加、修改、删除，在修改时使用getName和getValue方法得到的是修改前的数据。

## 新建监听器

方法一：右键 ->New -> Create New Listener

方法二：新建类实现对应的监听器接口并重写方法，需要在web.xml文件中进行配置

```xml
    <listener>
        <listener-class>目录.类名</listener-class>
    </listener>
```

### ServletRequestListener监听器演示

```java
/**
* 1.当一个类实现了某个对象的 Listener 接口，那么该类就是一个监听器
* 2.监听器可以监听的事件，由该类实现的监听接口决定
* 3.当Web应用启动时，会产生 Event 事件，调用监听器的对应事件处理方法，并传递事件对象
* 4.可以通过 Event 事件对象，获取需要的信息，进行业务处理
* 5.为了实现监听效果（让tomcat知道监听器的存在），需要在web.xml中配置
*/
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

public class MyRequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        System.out.println("监听到 ServletRequest 对象创建");
        ServletRequest servletRequest = servletRequestEvent.getServletRequest();
        System.out.println("记录访问日志....");
        System.out.println("访问IP= " + servletRequest.getRemoteAddr());
        System.out.println("访问的资源= " + ((HttpServletRequest)servletRequest).getRequestURL());
    }
    
    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        System.out.println("监听到 ServletRequest 对象被销毁");
    }
}
```

