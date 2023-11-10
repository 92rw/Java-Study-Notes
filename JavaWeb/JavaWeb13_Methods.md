# ServletConfig

## 基本介绍

1. ServletConfig类是为Servlet程序的配置信息的类
2. Servlet程序和ServletConfig对象都是由Tomcat负责创建
3. Servlet程序默认是第1次访问的时候创建，ServletConfig在Servlet程序创建时，就创建一个对应的ServletConfig对象



### 常用属性

| 属性名         | 类型            | 标签               | 描述                                                         | 是否必需 |
| -------------- | --------------- | ------------------ | ------------------------------------------------------------ | -------- |
| name           | String          | \<servlet-name>    | 指定 Servlet 的 name 属性。 如果没有显式指定，则取值为该 Servlet 的完全限定名，即包名+类名。 | 否       |
| value          | String[ ]       | \<url-pattern>     | 该属性等价于 urlPatterns 属性，两者不能同时指定。 如果同时指定，通常是忽略 value 的取值。 | 是       |
| urlPatterns    | String[ ]       | \<url-pattern>     | 指定一组 Servlet 的 URL 匹配模式。                           | 是       |
| loadOnStartup  | int             | \<load-on-startup> | 指定 Servlet 的加载顺序。                                    | 否       |
| initParams     | WebInitParam[ ] | \<init-param>      | 指定一组 Servlet 初始化参数。                                | 否       |
| asyncSupported | boolean         | \<async-supported> | 声明 Servlet 是否支持异步操作模式。                          | 否       |
| description    | String          | \<description>     | 指定该 Servlet 的描述信息。                                  | 否       |
| displayName    | String          | \<display-name>    | 指定该 Servlet 的显示名。                                    | 否       |

## 应用实例

读取web.xml中配置的初始化参数init-param（写在对应的&lt;servlet&gt;&lt;/servlet中），以便调用数据访问MySQL数据库

实际项目中不建议注解方式配置，因此每次修改都需要重新生成class文件

```java
// 属于类级别的注解，标注在继承了 HttpServlet 的类之上。常用的写法是将 Servlet 的相对请求路径（即 value）直接写在注解内，可以省略urlPatterns 属性名
@WebServlet("/MyServlet") 

//如果 @WebServlet 中需要设置多个属性，则属性之间必须使用逗号隔开
@WebServlet(asyncSupported = true, name = "myServlet", description = "name描述", loadOnStartup = 1, urlPatterns = {
        "/MyServlet", "/*" }, initParams = {
                @WebInitParam(name = "编程帮", value = "www.biancheng.net", description = "init参数1"),
                @WebInitParam(name = "京东", value = "www.jd.com", description = "init参数2") })

```

演示：DBServlet.java

# ServletContext

## 基本介绍

1. ServletContext是一个接口，它表示Servlet上下文对象
2. 一个web工程，只有一个ServletContext对象实例
3. ServletContext对象在web工程启动的时候创建，在web工程停止的时销毁
4. ServletContext对象可以通过ServletConfig.getServletContext方法获得对ServletContext对象的引用，也可以通过this.getServletContext()来获得其对象的引用。
5. 由于一个WEB应用中的所有Servlet共享同一个ServletContext对象，因此Servlet对象之间可以通过ServletContext对象来实现多个Servlet间通讯。ServletContext对象通常也被称之为域对象。



## 作用

* 获取web.xml中配置的上下文参数context-param【和整个web应用相关，而不属于某个Servlet】
* 获取当前的动态工程路径，格式：/工程路径
* 获取工程部署后在服务器硬盘上的绝对路径（浏览器实际访问的是out文件夹）
* 像Map一样存取数据，实现多个Servlet共享数据 ->如果要实现容器并发，可以使用线程安全的ConcurrentHashMap

## 应用实例

1. 取到web.xml的&lt;context-param>信息
2. 获取项目的域名路径
3. 获取项目发布后的真实工作路径
4. 统计网站被访问次数

演示文件：VisitedCount.java

# HttpServletRequest

## 基本内容

1. HttpServletRequest类继承ServletRequest类，HttpServletRequest对象代表客户端的请求
2. 当客户端（浏览器或其他）通过HTTP协议访问服务器时，HTTP请求头中的所有信息都封装在这个对象中
3. 通过这个对象的方法，可以获得客户这些信息。

## 常用方法

| 方法名                  | 作用                                                   |
| ----------------------- | ------------------------------------------------------ |
| getRequestURI()         | 获取请求的资源路径                                     |
| getRequestURL()         | 获取请求的统一资源定位符（绝对路径）=主机地址+URI      |
| getRemoteHost()         | 获取客户端的主机，获取客户端ip地址getRemoteAddr()      |
| getHeader()             | 获取请求头                                             |
| getParameter()          | 获取请求的参数                                         |
| getParameterValues()    | 获取请求的参数（多个值的时候使用）                     |
| getMethod()             | 获取请求的方式 GET或 POST                              |
| setAttribute(key,value) | 设置域数据                                             |
| getAttribute(key)       | 获取域数据，例如在下一个页面读取上个页面封装来的域数据 |
| getRequestDispatcher()  | 获取请求转发对象                                       |



### 使用细节

1.获取doPost参数接收中文乱码解决方案，注意setCharacterEncoding("utf-8")要写在request.getParameter(String var1)前

2.注意：如果通过PrintWriter writer返回数据给浏览器，建议将获取参数代码写在writer.print()方法之前，否则可能无法正确获取参数值(doPost)
3.处理http响应数据中文乱码问题：response.setContentType("text/html;charset=utf-8");

### 应用实例

1）如发现某个ip 在10s中，访问的次数超过 100次，就封ip
实现思路：用ConcurrentHashMap集合保存[ip:访问次数]，设置线程/定时扫描，做出处理

2）对于不允许从外部网站访问的页面，提示非法地址或是重定向

实现思路：利用request.getHeader("Referer")方法获取访问来自哪个地址，进行相应处理

3）获取用户填写的表单信息，进行处理

演示文件：HttpRequestExample.java和SignUp.html，RegisterServlet.java和Register.html

# HttpRequestResponse

1.每次HTTP请求，Tomcat会创建一个HttpServletResponse对象传递给Servlet程序去使用。
2.HttpServletRequest表示请求过来的信息，HttpServletResponse表示所有响应的信息，如果需要设置返回给客户端的信息，通过HttpServletResponse对象来进行设置即可

设置响应头：

setStatus(int sc) 方法

setHeader(String name, String value)方法

输出流的方法：

* getWriter()方法：常用于回传字符串
* getOutputStream()方法：常用于下载（处理二进制数据）
* 两个流同时只能使用一个。使用了字节流，就不能再使用字符流，反之亦然，否则就会报错

中文乱码问题的处理方法：

方案1（使用较少）：

```java
//设置服务器字符集为UTF-8
response.setCharacterEncoding("UTF-8");
//通过响应头，设置浏览器也便用UTF-8字符集
response.setHeader("Content-Type"，"text/html; charset=UTF-8");
```

方案2：

```java
/*老韩解读：
1.setContentType会设置服务器和客户端都用utf-8字符集，还设置了响应头
2.setContentType要在获取流对象（getWriter）之前调用才有效
*/
response.setContentType("text/html;charset=utf-8")
PrintWriter writer = response.getwriter();
```

