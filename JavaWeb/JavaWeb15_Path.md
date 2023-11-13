# Web工程路径

## URI和URL的区别

* URI = Universal Resource Identifier，Identifier：标识符
* URL = Universal Resource Locator，Locator：定位器

从字面上来看，URI可以**在站点内部**唯一标识一个资源，URL可以提供**外部**找到该资源的路径

## 服务器端的配置

服务器端配置urlPatterns时前面必须带/，否则tomcat初始化时会报错

## 相对路径

解决路径过长的方案。说明：使用相对路径来解决，一个非常重要的规则：页面所有的相对路径。

```html
<!--
在默认情况下，都会参考当前浏览器地址栏的路径
http://ip地址:端口号/目录.../资源
来进行跳转。所以我们可以直接这样写，用post方法将数据提交到 http://ip地址:端口号/目录.../ok
此时也可以用"../"表示进入上一级目录

备注：使用服务器渲染技术jsp/thymeleaf，可以动态得到 工程路径
-->
<!--例如这个html的文件目录是http://localhost:8080/webpath/jerrymouse/a.html-->
<form action="../ok"method="post"><!--访问http://localhost:8080/webpath/a-->
<form action="/ok"method="post"><!--访问http://localhost:8080/ok-->
<form action="ok"method="post"><!--访问http://localhost:8080/webpath/jerrymouse/ok-->
    请输入密码:<input type="text" name="username"/><br><br>
    <input type="submit" name="确定"/><br><br>
</form>

```

存在问题：相对路径会让项目内相互调用的关系变得复杂

如果需要指定页面相对路径参考的的路径，可以使用base标签来指定

## base标签

解决路径过长的方案

1. base标签是HTML语言中的基准网址标记，它是一个单标签，位于网页头部文件的head标签内
2. 一个页面最多只能使用一个base元素，用来提供一个指定的默认自标，是一种表达路径和连接网址的标记
3. 常见的url路径形式分别有相对路径与绝对路径，如果base标签指定了目标，浏览器将通过这个目标来解析当前文档中的所有相对路径，包括的标签有（a、img、link、form）
4. 浏览器解析时会在路径前加上base给的目标，而页面中的相对路径也都转换成了绝对路径。使用了base标签就应带上href属性和target属性

```html
<!--
表示当前网页所有资源都是以base标签的地址作为参照
注意事项：
1.末尾必须带有斜杠/，否则仅表示一种资源
2.base标签由浏览器进行解析
3.在实际开发中，往往不是直接访问一个资源，而是在服务端进行转发或者重定向
-->
<base href="http://localhost:8080/webpath/">
<!--浏览器会将字符串开头出现的"/"解析成“http://localhost:8080/”-->
<base href="/webpath/">
<a href="a.html">返回a.html</a>
<!--不带斜杠的地址，根据base标签得到http://localhost:8080/webpath/a.html-->
<a href="/a.html">返回/a.html</a>
<!--带斜杠的地址，解析得到http://localhost:8080/a.html-->
```



## 利用服务器端转发

```java
@WebServlet(urlPatterns = {"/toB"})
public class redirectServlet extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//功能：通过转发来定位b.html
    //1.如果字符串以"/"或非地址开头，服务器端都会在字符串前拼接 "/工程路径/"
    //  因为因为服务器自带IP地址和端口号，实际上等价于 "http://localhost:8080/工程路径/"
    //2.例如Tomcat配置的工程路径（Application context）是"/jerrymouse"，那么响应体的文件是"http://localhost:8080/jerrymouse/b.html"
    //3.此时浏览器显示的路径是"http://localhost:8080/jerrymouse/toB"
	request.getRequestDispatcher("/b.html").forward(request,response);
    //request.getRequestDispatcher("b.html").forward(request,response);//和上面效果相同，但不建议使用
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

### 注意事项

1. Web工程的相对路径和绝对路径

   * 相对路径

     |  符号  | 浏览器解析的内容 |
     | :----: | :--------------: |
     |   .    |     当前目录     |
     |   ..   |    上一级目录    |
     | 资源名 | 当前目录/资源名  |

   * 绝对路径：“http&#58;//ip:port/工程路径/资源路径”
   
   * 在实际开发中，路径都使用绝对路径，而不是相对路径

2. web地址的解析主体

   *  / 斜杠如果被浏览器解析，得到的地址是：“http&#58;//ip[域名]:port/ ”，比如：&lt;a href="/">斜杠&lt;/a>

   * ／斜杠如果被服务器解析，得到的地址是：“/工程路径/”  ，也就是“http&#58;//ip[域名]:port/工程路径/”。比如：XML文件中：&lt;url-pattern>/servelturl&lt;/url-pattern>

     ```java
     //得到工程发布后的实际磁盘路径
     String realPath = getServletContext().getRealPath("/");
     
     //请求转发和请求包含中，得到RequestDispatcher对象
     RequestDispatcher rd = request.getRequestDispatcher("/");//服务器解析为“/工程目录/”
     //如果此处传参getRequestDispatcher(request.getContextPath() +"路径")，这个路径必须以“/”开头否则报错
     rd.forward(request, response);//请求转发
     rd.include(request, response);//请求包含
     
     //重定向的推荐写法
     //得到Tomcat配置的域名路径
     String contextPath = getServletContext().getContextPath();//得到的是“/tomcat”（给Tomcat配置的Application context）
     response.sendRedirect(contextPath + "/实际路径");//不依赖浏览器的地址栏，随Tomcat域名动态改变，定位稳定无歧义
     ```

3. 在JavaWeb中路径最后带/和不带/含义不同，一定要小心，比如：
   * &lt;a href="/a/servlet03">网址1&lt;/a>：servlet03表示资源
   * &lt;a href="/a/servlet03/">网址2&lt;/a>：servlet03表示路径

4. 注意：服务器在返回302重定向时，如果返回的代码是 `response.sendRedirect("/")`，虽然这段代码写在服务器端，但是会被浏览器解析为 `http&#58;//ip[域名]:port/`

5. 相对路径的使用局限：需要直接定位。如果服务器端返回给浏览器的页面经过了服务器端的请求转发，浏览器又会根据当前访问地址进行相对路径的查找，会找不到页面。
6. 加入base标签后，无论是从哪种方式访问，都可以访问到相同的网页

## 总结

在编写资源路径时，考虑这么几点：

* 路径第一个字符：

  | 路径第一个字符 | 浏览器              | 服务器     |
  | -------------- | ------------------- | ---------- |
  | 前面有/        | http://域名:端口号/ | /工程路径/ |
  | 前面没有/      | 地址栏去掉资源部分  | /工程路径/ |

* 路径末尾：【有/代表路径，没有/代表资源】

不同方式获取到的不同路径

| java代码                             | 获取内容                                                     |
| ------------------------------------ | ------------------------------------------------------------ |
| getServletPath()                     | 获取能够与“url-pattern”中匹配的路径，注意是完全匹配的部分，*的部分不包括。 |
| getPathInfo()                        | 与getServletPath()获取的路径互补，能够得到的是“url-pattern”中*d的路径部分 |
| getContextPath()                     | 获取项目的根路径                                             |
| getRequestURI                        | 获取根路径到地址结尾                                         |
| getRequestURL                        | 获取请求的地址链接（浏览器中输入的地址）                     |
| getServletContext().getRealPath("/") | 获取“/”在机器中的实际地址                                    |
| getScheme()                          | 获取使用的协议(http 或https)                                 |
| getProtocol()                        | 获取协议的名称(HTTP/1.11)                                    |
| getServerName()                      | 获取域名(xxx.com)                                            |
| getLocalName                         | 获取IP                                                       |

参考资料：[关于request.getServletPath()，request.getContextPath()的总结_jwnba24的博客-CSDN博客](https://blog.csdn.net/qq_27770257/article/details/79438987)

# 动态获取WEB路径的技术

使用jsp或thymeleaf技术

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  $END$ 动态的获取到工程路径: <%=request.getContextPath()%>
  </body>
</html>

```

