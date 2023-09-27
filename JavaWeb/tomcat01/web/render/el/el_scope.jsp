<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 9:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>演示el的四个常用的隐藏对象(域对象)</title>
</head>
<body>
<h1>演示el的四个常用的隐藏对象(域对象)</h1>
<%
    request.setAttribute("k1", "request-k1数据");
    pageContext.setAttribute("k1", "pageContext-k1数据");
    session.setAttribute("k1", "session-k1数据");
    application.setAttribute("k1", "application-k1数据");
%>
<h1>jsp脚本方式获取</h1>
request域中的k1= <%=request.getAttribute("k1")%><br/>
<h1>el方式来获取域对象的数据</h1>
直接获取的k1= ${k1}<br>
request域中的k1= ${requestScope.k1}<br/>
pageContext域中的k1= ${pageScope.k1}<br/>
session域中的k1= ${sessionScope.k1}<br/>
application域中的k1= ${applicationScope.k1}<br/>
</body>
</html>
