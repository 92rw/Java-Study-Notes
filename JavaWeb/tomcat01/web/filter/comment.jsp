<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 12/8/2023
  Time: 11:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>发表评论</title>
</head>
<body>
<h1>发表对阿凡达电影评论</h1>
请注意文明发言 ${errorInfo}
<form method="post" action="<%=request.getContextPath()%>/filter/comment/showComment.jsp">
    用户: <input type="text" name="username"><br/>
    评论: <textarea rows="10" name="content" cols="20"></textarea><br/>
    <input type="submit" value="发表评论">
</form>
</body>
</html>
