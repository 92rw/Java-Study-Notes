<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 9:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>pageContext 对象的使用</title>
</head>
<body>
<h1>pageContext 对象的使用</h1>
<%--
    //通过request对象来获取和HTTP协议相关的数据
    request.getScheme() 它可以获取请求的协议
    request.getServerName() 获取请求的服务器 ip 或域名
    request.getServerPort() 获取请求的服务器端口号
    getContextPath() 获取当前工程路径
    request.getMethod() 获取请求的方式（GET 或 POST）
    request.getRemoteHost() 获取客户端的 ip 地址
    session.getId() 获取会话的唯一标识


--%>
<hr/>
<%--解读
    1.我们可以通过pageContext.request.xx 俩获取和http协议相关的信息
    2.相当于替代 request.getMethod()....
--%>
协议： ${ pageContext.request.scheme }<br>
服务器 ip：${ pageContext.request.serverName }<br>
服务器端口：${ pageContext.request.serverPort }<br>
工程路径：${ pageContext.request.contextPath }<br>
请求方法：${ pageContext.request.method }<br>
客户端 ip 地址：${ pageContext.request.remoteHost }<br>
会话id ：${ pageContext.session.id }<br>
<h1>使用jsp表达式脚本获取如上信息</h1>
ip地址: <%=request.getRemoteHost() %> <br>
<h1>使用el表达式形式获取信息-简化写法</h1>
<%  //把request对象赋值成为页面的属性，方便简化获取
    pageContext.setAttribute("req", request);
%>
ip地址(简化获取): ${req.remoteHost} <br>
获取请求方法(简化获取): ${req.method} <br>
</body>
</html>
