<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: 92rw
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jsp简单求和计算器</title>
</head>
<body>
<h1>jsp求和计算器</h1>
<%
    //在jsp用%和<>组成的标签中，可以写java代码
    int i = 10;
    int j = 25;
    int res = i + j;


    //在jsp中，可以直接使用内置对象，比如out
    // PrintWriter writer = response.getWriter();//可以直接调用response对象
    out.print(i + " + " +  j  + " = " + res);
%>
</body>
</html>
76764646774