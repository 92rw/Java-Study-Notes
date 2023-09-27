<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 11/8/2023
  Time: 8:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>后台管理</title>
    <base href="<%=request.getContextPath() %>/filter/manage/"/>
</head>
<body>
<h1>后台管理</h1>
<%
    //验证request对象是和前面的filter是一个对象
    System.out.println("admin.jsp接收到的request：" + request);
    String username = (String) request.getSession().getAttribute("username");
%>
欢迎管理员 <%=username%> 登录系统<br>
<a href="#">用户列表</a>||<a href="#">添加用户</a>||<a href="#">删除用户</a>
<hr/>
<img src="XianZhan.png"/><img src="QingdaoZhan.png"/>
</body>
</html>
