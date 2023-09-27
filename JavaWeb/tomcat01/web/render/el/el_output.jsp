<%@ page import="com.javaweb.jsp.Book" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 8:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>el 表达式输出数据演示</title>
</head>
<body>
<h1>el 表达式输出数据演示</h1>
<%
    //创建Book 对象，放入相关的属性
    //private String name;//书名
    //private String[] writer;//作者
    //private List<String> reader;//读者
    //private Map<String, Object> topics;//评价
    Book book = new Book();
    book.setName("昆虫总动员");
    book.setWriter(new String[]{"jack", "tom"});
    ArrayList<String> readers = new ArrayList<>();
    readers.add("老赵");
    readers.add("老李");
    book.setReader(readers);//放入readers

    //创建topics
    HashMap<String, String> topics = new HashMap<>();
    topics.put("topic1", "这是我看过的最好的动画片");
    topics.put("topic2", "不错的电影~~");
    book.setTopics(topics);

    //把book 放入到request域对象
    request.setAttribute("bookkey", book);

%>
book对象: ${bookkey}<br/>
book.name= ${bookkey.name}<br/>
book.writer= ${bookkey.writer}<br/>
book.writer[0]= ${bookkey.writer[0]}<br/><%-- 数组默认是整个对象，除非重写toString方法 --%>

book.readers= ${bookkey.reader}<br/><%-- List接口底层重写了toString方法 --%>
book.readers第2个= ${bookkey.reader.get(1)}<br/>
book.readers第2个= ${bookkey.reader[1]}<br/>

book.topics= ${bookkey.topics}<br/><%-- Map接口不支持随机访问，需要通过key获取 --%>
book.topics第一个评论= ${bookkey.topics["topic1"]}<br/>

</body>
</html>