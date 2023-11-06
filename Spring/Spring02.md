# 实现 Spring机制（基于XML文件）

简单的 Spring 基于 XML 配置的程序

需求说明

* 自己写一个简单的 Spring 容器，通过读取 beans.xml，获取第1个 JavaBean 的对象
* 给该的对象属性赋值，放入到容器中，输出该对象信息
* 不适用 Spring 原生框架，自己简单模拟实现

实现过程

1. 编写 VasantApplicationContext.java 容器
   * 定义 ConcurrentHashMap 属性 singletonObjects
   * 解析 beans.xml -> 使用DOM4J
   * 得到bean 的 id，class，配置的属性名和属性值
   * 使用反射生成 JavaBean 对象，并赋值
   * 将创建好的对象实例，放入到集合中
   * 提供getBean(id) 方法，返回对应的bean对象
2. 其他说明：
   * 引入了外部的 Dom4j 包，原生Spring框架不需要

```java
import com.exercise.spring.bean.Monster;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于实现Spring的简单容器机制
 * 实现beans.xml文件的解析，生成对象并放入容器中，
 * 提供 getBean(id) 方法，返回对应的对象
 */
public class VasantApplicationContext{
    private ConcurrentHashMap<String, Object> singletonObject = new ConcurrentHashMap<>();

    //构造器：接收容器的配置文件，比如 beans.xml，该文件默认在 src目录下
    public VasantApplicationContext(String iocBeanXMLFile) throws DocumentException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //1.创建 SAXReader 并读取数据
        SAXReader saxReader = new SAXReader();
//        得到类加载路径，也可用class对象的 getResource("/").getPath()，此处直接使用输入流读取
        InputStream is = this.getClass().getResourceAsStream("/" + iocBeanXMLFile);
        Document document = saxReader.read(is);

        //2.得到根元素 rootDocument
        Element rootElement = document.getRootElement();

        //3.得到第一个bean元素
        Element bean = (Element)rootElement.elements("bean").get(0);

        //4.获取到第一个 bean 元素自身配置的 id 和 class 属性
        String classId = bean.attributeValue("id");
        String classFullPath = bean.attributeValue("class");

        //5.得到bean元素内部的各个 property 属性 =>Spring框架中，存放在 beanDefinitionMap
        List<Element> property = bean.elements("property");
        //可以对List进行遍历输出，因为该对象仅三个属性，此处代码做了简化
        Integer monsterId = Integer.parseInt(property.get(0).attributeValue("value"));
        String name = property.get(1).attributeValue("value");
        String skill = property.get(2).attributeValue("value");

        //6.使用反射创建对象
        Class<?> aClass = Class.forName(classFullPath);
        Monster o = (Monster)aClass.newInstance();//得到实例化对象
        o.setMomsterId(monsterId);
        o.setName(name);
        o.setSkill(skill);
        //原本应使用反射完成赋值，此处简化描述，直接赋值
//        Method[] declaredMethods = aClass.getDeclaredMethods();
//        for (Method declaredMethod : declaredMethods) {
//            if (declaredMethod.getName().equalsIgnoreCase("set" + name)) {
//                try {
//                    declaredMethod.invoke(o,name);
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//        }


        //7.将创建好的对象放入集合
        singletonObject.put(classId, o);
    }

    //提供获取 singletonObject 的方法
    public Object getBean(String id) {
        return  singletonObject.get(id);
    }
}

```

测试类

```java
package com.exercise.spring.test;

import com.exercise.spring.Vasant.VasantApplicationContext;
import com.exercise.spring.bean.Monster;

public class VasantApplicationContextTest {
    public static void main(String[] args) throws Exception{
        VasantApplicationContext ioc = new VasantApplicationContext("beans.xml");
        Monster monster01 = (Monster)ioc.getBean("monster01");
        System.out.println(monster01);
    }
}

```



更加详细的实现原理参考：[fuzhengwei/small-spring: 🌱《 Spring 手撸专栏》，本专栏以 Spring 源码学习为目的，通过手写简化版 Spring 框架，了解 Spring 核心原理。在手写的过程中会简化 Spring 源码，摘取整体框架中的核心逻辑，简化代码实现过程，保留核心功能，例如：IOC、AOP、Bean生命周期、上下文、作用域、资源处理等内容实现。 (github.com)](https://github.com/fuzhengwei/small-spring)
