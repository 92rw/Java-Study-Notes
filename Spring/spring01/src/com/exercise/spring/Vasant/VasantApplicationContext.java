package com.exercise.spring.Vasant;

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
            //得到类加载路径，也可用class对象的 getResource("/").getPath()，此处直接使用输入流读取
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
