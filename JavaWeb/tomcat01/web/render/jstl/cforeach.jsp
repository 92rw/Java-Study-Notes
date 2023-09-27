<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.javaweb.jsp.Station" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 10:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>c:forEach 标签</title>
</head>
<body>
<h1>c:forEach 标签</h1>
<hr/>
<h1>第1种遍历方式从i到j</h1>
<ul>
    <%--
    1.遍历 1 到 5，
    2. 输出 begin 属性设置开始的索引 end 属性设置结束的索引
    3. var 属性表示循环的变量(也是当前正在遍历到的数据)
    4. 等价 for (int i = 1; i <= 5; i++) {}
    5. 在默认情况下, i 每次会递增1，可以在step属性中定义每次自增的值
    --%>
    <c:forEach begin="1" end="5" var="i">
        <li>排名=${i}</li>
    </c:forEach>
</ul>
<hr/>
<h1>第2种遍历方式：遍历数组</h1>
<%
    request.setAttribute("sports", new String[]{"打篮球", "乒乓球"});
%>
<%--
    <c:forEach items="${ requestScope.sports }" var="item"/>
    1. items 遍历的集合/数组
    2. var 遍历到的数据
    3. 等价 for (Object item: arr) {}
--%>
<c:forEach items="${requestScope.sports}" var="sport">
    运动名称= ${sport}<br/>
</c:forEach>
<hr/>
<h1>第3种遍历方式：遍历Map</h1>
<%
    Map<String, Object> map = new HashMap<>();
    map.put("广州", "020");
    map.put("天津", "022");
    map.put("南京", "025");
    request.setAttribute("cities", map);
%>
<%--
    1. items 遍历的map集合
    2. var 遍历到的数据
    3. entry.key 取出key
    4. entry.value 取出值
--%>
<c:forEach items="${requestScope.cities}" var="city">
    城市区号: ${city.key}--${city.value}<br/>
</c:forEach>
<hr/>
<h1>第4种遍历方式：遍历List</h1>
<%
    List<Station> stations = new ArrayList<>();
    stations.add(new Station(18089, "青岛", "QDA"));
    stations.add(new Station(18088, "青岛北", "QDB"));
    stations.add(new Station(18077, "四方", "SFA"));
    request.setAttribute("stations", stations);
%>
<%--
    items 表示遍历的集合
    var 表示遍历到的数据
    begin 表示遍历的开始索引值 ,从0开始计算
    end 表示结束的索引值
    step 属性表示遍历的步长值
    varStatus 属性表示当前遍历到的数据的状态,可以得到step,begin,end等属性值
    //老师提示, 对于jstl标签，能看懂，会使用即可
--%>
<c:forEach items="${requestScope.stations}" var="station">
    火车站信息: ${station.staNum}-${station.staName}-${station.pyCode}<br/>
</c:forEach>
</body>
</html>
