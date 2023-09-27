<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 6:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>当前文件的scope域对象</title>
</head>
<body>
<h1>在其他页面获取数据的情况</h1>
pageContext-k1: <%=pageContext.getAttribute("k1")%><br/>
request-k1: <%=request.getAttribute("k1")%><br/>
session-k1: <%=session.getAttribute("k1")%><br/>
application-k1: <%=application.getAttribute("k1")%><br/>
</body>
</html>
