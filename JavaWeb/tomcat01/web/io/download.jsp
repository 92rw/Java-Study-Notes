<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 16/8/2023
  Time: 6:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文件下载</title>
    <base href="<%=request.getContextPath()+"/"%>>">
</head>
<body>
<h1>文件下载</h1>
<a href="fileDownLoadServlet?name=monkey.png">点击下载猴子图片</a><br/><br/>
</body>
</html>
