<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 9:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jstl的快速入门</title>
</head>
<body>
<h1>jstl的快速入门</h1>
<%--解读
    1. c:if ... 类似 if 语句
    2. if(10>2){
       out.println("<h1>10 > 2 成立~</h1>")
    }
--%>
<c:if test="${10 < 2}">
    <h1>10 > 2 成立~</h1>
</c:if>

<c:set scope="request" var="num1" value="20"></c:set>
<c:set scope="request" var="num2" value="10"></c:set>
<c:if test="${num1 > num2}">
    <h1>${num1} > ${num2}</h1>
</c:if>
</body>
</html>