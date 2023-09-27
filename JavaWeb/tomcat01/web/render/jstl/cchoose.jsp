<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 10:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>c:choose标签的使用</title>
</head>
<body>
<h1>c:choose标签的使用</h1>
<%
    request.setAttribute("score", 50);

    //request.setAttribute("k1", "request-k1的值");
    //session.setAttribute("k1", "session-k1的值");
    //application.setAttribute("k1", "application-k1的值");
    //pageContext.setAttribute("k1", "pageContext-k1的值~");
%>
<%--
1. 如果${requestScope.score} 那么就明确的指定从request域对象取出数据
2. 如果${score}, 这是就按照从小到大的域范围去获取 pageContext->request->session->application
--%>
k1=${k1}
<c:choose>
    <c:when test="${requestScope.score > 80}">
        <h1>${score}-成绩优秀</h1>
    </c:when>
    <c:when test="${requestScope.score >= 60}">
        <h1>${score}-成绩一般, 及格了</h1>
    </c:when>
    <c:otherwise>
        <h1>${score}-没有及格，下次努力~</h1>
    </c:otherwise>
</c:choose>
</body>
</html>
