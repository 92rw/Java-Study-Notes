# XML解析技术

原理

1. 不管是html文件还是xml文件，都是标记型文档，都可以使用w3c组织制定的dom技术来解析
2. document对象表示的是整个文档（可以是html文档，也可以是xml文档），可以通过java进行解析（例如使用 [dom4j](https://dom4j.github.io/)）

早期JDK为我们提供了两种xmI解析技术：DOM和Sax

* dom解析技术是W3C组织制定的，而所有的编程语言都对这个解析技术使用了自已语言的特点进行实现。Java对dom技术解析也做了实现。
* sun公司在JDK5版本对dom解析技术进行升级：SAX（Simple API for XML）SAX解析，它是以类似事件机制通过回调告诉用户当前正在解析的内容。是一行一行的读取xml文件进行解析的。不会创建大量的dom对象。所以它在解析xml的时候，在性能上优于Dom解析。
* 这两种技术已经过时

第三方的XML解析技术

* jdom在dom基础上进行了封装
* dom4j又对jdom进行了封装
* pull主要用在Android手机开发，类似sax，都是事件机制解析xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!--
    解读: web.xml主要用来配置该web应用使用到的Servlet
    配置servlet
    1. servlet-name: 给Servlet取名(程序员决定), 该名字唯一
    2. servlet-class: Servlet的类的全路径: Tomcat在反射生成该Servlet需要使用
    配置servlet-mapping
    1.名字和servlet-name保持一致
    2. url-pattern: 这个就是该servlet访问的url的配置(路径)，取名是程序员决定的，必须以/开头
    3. 这时我们应该这样访问servlet "http://localhost:8080/项目名"+ url-pattern

    6. load-on-startup 表示在tomcat 启动时，会自动的加载servlet实例

    小技巧： alt+r => 老师配置
        -->
    <servlet><!--演示Servlet接口实现类的使用-->
        <servlet-name>ServletInterface</servlet-name>
        <servlet-class>com.javaweb.servlet.ServletInterface</servlet-class>
        <load-on-startup>-1</load-on-startup><!--如果设置为1，则tomcat启动时自动调用-->
    </servlet>
    <servlet-mapping><!--需要和对应的servlet名字一致-->
        <servlet-name>ServletInterface</servlet-name>
        <url-pattern>/ServletInterface</url-pattern>
    </servlet-mapping>
    <servlet><!--演示Servlet接口实现类的使用-->
        <servlet-name>HttpServletExample</servlet-name>
        <servlet-class>com.javaweb.servlet.HttpServletExample</servlet-class>
    </servlet>
    <servlet-mapping><!--需要和对应的servlet名字一致-->
        <servlet-name>HttpServletExample</servlet-name>
        <url-pattern>/HttpServletExample</url-pattern>
    </servlet-mapping>

    <servlet>
        <servlet-name>HttpProtocolServlet</servlet-name>
        <servlet-class>com.javaweb.http.HttpProtocolServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>HttpProtocolServlet</servlet-name>
        <url-pattern>/http</url-pattern>
    </servlet-mapping>
    <!--配置DBServlet-->
    <servlet>
        <servlet-name>DBServlet</servlet-name>
        <servlet-class>com.javaweb.servlet.DBServlet</servlet-class>
        <!--配置信息,而不是硬编码到程序-->
        <init-param>
            <param-name>username</param-name>
            <param-value>root</param-value>
        </init-param>
        <init-param>
            <param-name>pwd</param-name>
            <param-value>123456</param-value>
        </init-param>
    </servlet>

    <servlet-mapping>
        <servlet-name>DBServlet</servlet-name>
        <url-pattern>/db</url-pattern>
    </servlet-mapping>
    <!--配置整个网站的信息-->
    <context-param>
        <param-name>website</param-name>
        <param-value>https://92rw.github.io/</param-value>
    </context-param>
    <context-param>
        <param-name>user</param-name>
        <param-value>92rw</param-value>
    </context-param>
</web-app>
```



# Dom4j

## 介绍

1. Dom4j 是一个简单、灵活的开放源代码的库（用于解析/处理XML文件）。Dom4j 是由早期开发 JDOM 的人分离出来而后独立开发的。
2. 与 JDOM 不同的是，dom4j 使用接口和抽象基类，虽然 Dom4j 的 API 相对要复杂一些，但它提供了比 JDOM 更好的灵活性。
3. Dom4j 是一个非常优秀的 Java XML API，具有性能优异、功能强大和极易使用的特点。现在很多软件采用Dom4j
4. 使用Dom4j开发，需下载dom4j相应的jar文件

## 在dom4j中获得Document对象



通过查看源代码可以得知，SAXReader的read()方法可以传入Reader接口对象、Read对象、InputStream对象

- `clazz.getClassLoader().getResourceAsStream(String pathName)` ：默认从类路径下获取，不能以`/`开头
- `clazz.getResourceAsStream(String pathName)`：默认从当前类的包下获取，以`/`开头时从类路径下获取
- 传入FileReader对象（Reader接口实现类）或File对象需要指定上一级目录，

```java
//方法1：读取XML文件，获得document对象（解析本地文件）
SAXReader reader = new SAXReader();	//创建 Dom4J 的解析器对象
//1.1 传入Reader接口的File对象
String path = this.getClass().getResource("/").getPath();
Document document = reader.read(new File(path + "input.xml"));//加载 XML 文件成为 Document 对象
//1.2 传入对象输入流
InputStream is = this.getClass().getResourceAsStream("/input.xml");//注意需要用斜杠表示根目录
Document document = reader.read(is);

//方法2：解析XML形式的文本，得到document对象（从网络获取）
String text="<members></members>";
Document document = DocumentHelper.parseText(text);

//方法3：主动创建document对象（通过IO流新建文件）
Document document = DocumentHelper.createDocument();//创建根节点
Element root = document.addElement("members");
```



说明：使用Xpath结合DOM，可以更简单地跨层获取子元素数据（官方文档的 Powerful Navigation with XPath 子章节）



学习新的API的方法：在代码中下断点，了解其运行原理，结合具体的属性和方法进行使用

对于xml文件的增删改，需要利用IO流将内存中的数据刷入到磁盘文件中，涉及到文件编码格式的问题



Dom4j案例演示：需要导入students.xml和trains.xml文件



参考文章

[【Java】关于getResourceAsStream 方法的使用 - HypoPine - 博客园 (cnblogs.com)](https://www.cnblogs.com/HypoPine/p/16168173.html)