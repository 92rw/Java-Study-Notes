<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 10/8/2023
  Time: 7:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP计算器</title>
    <!--使用js+正则表达式完成数据校验-->
    <script type="text/javascript">
        function check() {
            //得到 num1 和 num2值
            var num1 = document.getElementById("num1").value;
            var num2 = document.getElementById("num2").value;

            //验证 正则表达式, 整数 => 在java基础讲过 => 学习技术一定要经常回顾
            //我们学习的所有java技术=> 其实都是基础组合[oop,io,反射,注解,集合,线程,网络]
            var reg = /^[-]?([1-9]\d*|0)$/;
            if (!reg.test(num1)) {//如果不满足验证条件
                alert("num1 不是一个整数");
                document.getElementById("num1").value+= "（请重新输入）";
                return false;//放弃提交
            }
            if (!reg.test(num2)) {//如果不满足验证条件
                alert("num2 不是一个整数");
                document.getElementById("num2").value+= "（请重新输入）";
                return false;//放弃提交
            }
            return true;//提交到action指定的位置
        }
    </script>
</head>
<body>
<h1>JSP计算器</h1>
<form action="<%=request.getContextPath()%>/calServlet"
      method="post" onsubmit="return check()">
    num1: <input type="text" id="num1" name="num1"><br/>
    num2: <input type="text" id="num2" name="num2"><br/>
    运算符号:
    <select name="oper">
        <option value="+">+</option>
        <option value="-">-</option>
        <option value="*">*</option>
        <option value="/">/</option>
    </select><br/>
    <input type="submit" value="提交计算">
</form>
</body>
</html>
