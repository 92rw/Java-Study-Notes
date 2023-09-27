<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 12/8/2023
  Time: 11:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>显示评论</title>
</head>
<body>
<h1>评论信息显示</h1>
用户名: <%=request.getParameter("username")%><br><br>
评论内容: <%=request.getParameter("content")%>
</body>
</html>

