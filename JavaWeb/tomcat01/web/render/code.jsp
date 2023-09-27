<%@ page import="java.util.ArrayList" %>
<%@ page import="com.javaweb.jsp.Station" %><%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 5:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>演示代码脚本</title>
</head>
<body>
<h1>演示代码脚本</h1>
    <%
    //创建ArrayList ,放入两个 station 对象
    ArrayList<Station> stationList = new ArrayList<>();
    stationList.add(new Station(18086, "大港", "DGA"));
    stationList.add(new Station(18065, "沧口", "CKO"));
%>
<table bgcolor="#f0f8ff" border="1px" width="300px">
    <tr>
        <th>车站代码</th>
        <th>车站名</th>
        <th>拼音码</th>
    </tr>
        <%
        for (int i = 0; i < stationList.size(); i++) {
            //先取出 station 对象
            Station station = stationList.get(i);
    %>
    <tr>
        <td><%=station.getStaNum()%></td>
        <td><%=station.getStaName()%></td>
        <td><%=station.getPyCode()%></td>
    </tr>
        <%
        }
    %>
