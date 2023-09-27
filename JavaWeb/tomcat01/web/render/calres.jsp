<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 7:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>计算结果</title>
</head>
<body>
<h1>计算结果</h1>
<%=request.getAttribute("res")%><br>
<a href="<%=request.getContextPath()%>/render/calc.jsp">返回重来一把</a>
</body>
</html>
