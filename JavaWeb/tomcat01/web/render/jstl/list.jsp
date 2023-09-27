<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 11:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>车站显示</title>
</head>
<body>
<h1>车站信息显示</h1>
<table border="1px" width="400px">
    <tr>
        <td>车站代码</td>
        <td>车站名</td>
        <td>拼音码</td>
    </tr>
<%--使用c:forEach循环显示--%>
    <c:forEach items="${stations}" var="station">
        <%--使用c:if对结果进行筛选--%>
        <c:if test="${station.id > 16295}">
            <tr>
                <td>${station.staNum}</td>
                <td>${station.staName}</td>
                <td>${station.pyCode}</td>
            </tr>
        </c:if>
    </c:forEach>
</table>
</body>
</html>
