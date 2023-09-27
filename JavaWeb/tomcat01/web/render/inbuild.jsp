<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 5:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jsp内置对象</title>
</head>
<body>
<h1>jsp内置对象</h1>
<%
    //梳理jsp的内置对象
    //1.out 类型是 JspWriter 父类就是 Writer.
    out.println("jsp out");
    //2.request是HttpServletRequest
    request.getParameter("age");
    //3.response就是 HttpServletResponse
    //response.sendRedirect("http://www.baidu.com");
    //4.session 就是 HttpSession
    session.setAttribute("job", "PHP工程师");
    //5.application类型就是 ServletContext（域对象）
    application.setAttribute("name", "老韩老师");
    //6.pageContext 在Servlet中没有对应的类，可以存放数据(属性), 但是存放的数据只能在本页面使用
    pageContext.setAttribute("age", 100);
    //7.exception 异常对象 使用比较少
    //8.page 内置对象，类似 this
    out.println("page=" + page);
    //9.config 内置对象的类型就是ServletConfig
    String pwd = config.getInitParameter("pwd");
%>

</body>
<br>age: <%=pageContext.getAttribute("age")%>
</html>
