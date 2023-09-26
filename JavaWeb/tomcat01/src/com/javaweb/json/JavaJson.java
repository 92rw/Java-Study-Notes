package com.javaweb.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 演示 JavaBean 和 JSON字符串 的转换
 */
public class JavaJson {
    public static void main(String[] args) {
        //从外部包中导入Gson类并创建对象
        Gson gson = new Gson();

        //1.演示JavaBean和JSON字符串互转
        //新建JavaBean对象
        Book book = new Book(58, "羊毛战记");

        //演示JavaBean -> JSON字符串
        String strBook = gson.toJson(book);
        System.out.println("strBook= " + strBook);

        //JSON字符串 -> Javabean对象
        //strBook是JSON字符串
        //Book.class将 JSON字符串 -> Javabean对象
        //底层是反射机制
        Book beanBook = gson.fromJson(strBook, Book.class);
        System.out.println("beanBook= " + beanBook);

        //2.演示List和JSON字符串互转
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(149,"Java核心技术卷"));
        bookList.add(new Book(108,"Java编程思想"));

        //把对象、集合转成字符串比较简单，底层只需要遍历后根据JSON格式拼接即可
        String strList = gson.toJson(bookList);
        System.out.println("strList= " + strList);

        //JSON字符串 -> List对象
        //把json字符串转成集合，需要使用gson提供的自定义泛型类TypeToken
        //通过TypeToken类的getType方法，得到目标类型的完成路径，然后通过gson反射
        Type type = new TypeToken<List<Book>>() {}.getType();

        //System.out.println("type= " + type);//得到的Type实例，是目标对象List<Book>所在类的完整路径
        //System.out.println(type.getClass());//用这个方法可以发现type实例是一个匿名内部类，和 TypeToken类 无关

        List<Book> bookList2 = gson.fromJson(strList, type);
        System.out.println("bookList2= " + bookList2);

        /*
        关于TypeToken的详解
        package com.google.gson.reflect;
        public class TypeToken<T>


        1）如果在新建TypeToken<E>时不带花括号{}，会报错并提示'TypeToken()' has protected access in 'com.google.gson.reflect.TypeToken'
        原因是TypeToken的无参构造器是被访问修饰符protected修饰的，使用new方法无法直接调用
            protected TypeToken() {
                this.type = getSuperclassTypeParameter(this.getClass());
                this.rawType = Types.getRawType(this.type);
                this.hashCode = this.type.hashCode();
            }
        2）因为protected只能同类、同包、子类拥有访问权限，而加入{}相当于重写了构造器，此时 new TypeToken<List<Book>>() {}相当于新建了一个匿名内部类
        3）这个新建的匿名内部类是TypeToken的子类，其隐式无参构造器会用super()方法调用到TypeToken类
         */

        //3.演示Map和JSON字符串互转
        Map<String, Book> bookMap = new HashMap<>();
        bookMap.put("book1",new Book(129,"深入理解JVM虚拟机"));
        bookMap.put("book2",new Book(69,"Java并发编程实战"));

        //Map对象 -> JSON字符串
        String strBookMap = gson.toJson(bookMap);
        System.out.println("strBookMap= " + strBookMap);

        //JSON字符串 -> Map对象
        Map<String, Book> bookMap2 = gson.fromJson(strBookMap,
                new TypeToken<Map<String, Book>>() {
                }.getType());
        System.out.println("bookMap2= " + bookMap2);
    }
}
