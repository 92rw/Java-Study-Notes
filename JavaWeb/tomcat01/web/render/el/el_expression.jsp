<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 8:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>el表达式的快速入门</title>
</head>
<body>
<h1>el表达式的快速入门</h1>
<%
    request.setAttribute("name", "El表达式");
%>
<%--表达式脚本和el表达式的区别
    1. 如果name是 null, request.getAttribute("name") 返回的是null字符串
    2. 如果name是 null, ${name}, 返回的""
--%>
<h1>jsp表达式脚本</h1>
名字= <%=request.getAttribute("name") == null ? "": request.getAttribute("name")%><br/>
<h1>el表达式</h1>
名字= ${name}<br/>
<h1>el的运算符</h1>
<%
    request.setAttribute("num1", 90);
    request.setAttribute("num2", 30);
%>
num1+num2=${num1 + num2}<br/>
num1>num2=${num1 > num2}<br/>
<h1>el的三元运算符</h1>
<%
    request.setAttribute("score", 70);
%>
是否及格= ${score >= 60 ? "及格": "不及格"}
</body>
</html>