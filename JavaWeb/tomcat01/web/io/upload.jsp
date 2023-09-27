<%--
  Created by IntelliJ IDEA.
  User: 79897
  Date: 16/8/2023
  Time: 3:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!-- 指定了base标签 -->
    <base href="<%=request.getContextPath()+"/"%>>">
    <style type="text/css">
        input[type="submit"] {
            outline: none;
            border-radius: 5px;
            cursor: pointer;
            background-color: #31B0D5;
            border: none;
            width: 70px;
            height: 35px;
            font-size: 20px;
        }

        img {
            border-radius: 50%;
        }

        form {
            position: relative;
            width: 200px;
            height: 200px;
        }

        input[type="file"] {
            position: absolute;
            left: 0;
            top: 0;
            height: 200px;
            opacity: 0;
            cursor: pointer;
        }
    </style>

    <script type="text/javascript">
        function prev(event) {
            //获取展示图片的区域
            var img = document.getElementById("prevView");
            //获取文件对象
            var file = event.files[0];
            //获取文件阅读器： Js的一个类，直接使用即可
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function () {
                //给img的src设置图片url
                img.setAttribute("src", this.result);
            }
        }
    </script>

</head>
<body>
<!-- 表单的enctype属性要设置为multipart/form-data
    enctype="multipart/form-data" 表示提交的数据是多个部分构造，有文件和文本
 -->

<form action="uploadfile" method="post" enctype="multipart/form-data">
    上传图片: <img src="test/img/caution.png" alt="" width="200" height="200" id="prevView">
    <%--    小伙伴愿意完成自己测试--%>
    <input type="file" name="pic" id="" value="" onchange="prev(this)"/>

    图片说明: <input type="text" name="name"><br/>

    <input type="submit" value="上传"/>
</form>
</body>
</html>
