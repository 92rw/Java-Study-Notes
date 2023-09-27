<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 6:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>当前文件的scope域对象</title>
</head>
<body>
<%
    //在不同的域对象中，放入数据
    //因为四个域对象，是不同的对象，因此name(key) 相同时，并不会冲突
    pageContext.setAttribute("k1", "pageContext数据(k1)");
    request.setAttribute("k1", "request数据(k1)");
    session.setAttribute("k1", "session数据(k1)");
    application.setAttribute("k1", "application数据(k1)");

    //做一个请求转发的操作
    request.getRequestDispatcher("/render/scopeanother.jsp").forward(request, response);

//    //做一个重定向
//    String contextPath = request.getContextPath();//返回的就是 web路径=>/jsp
//    //response.sendRedirect("/jsp/scope2.jsp");
//    response.sendRedirect(contextPath + "/render/scopeanother.jsp");
%>
<h1>四个域对象，在本页面获取数据的情况</h1>
pageContext-k1: <%=pageContext.getAttribute("k1")%><br/>
request-k1: <%=request.getAttribute("k1")%><br/>
session-k1: <%=session.getAttribute("k1")%><br/>
application-k1: <%=application.getAttribute("k1")%><br/>
</body>
</html>
