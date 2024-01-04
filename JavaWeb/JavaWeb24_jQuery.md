# jQuery

1. jQuery是一个快速、简洁的javaScript库，使用户能更方便地处理HTML，css，DOM
2. .提供方法、events、选择器，并且方便地为网站提供 AJAX 交互
3. 其宗盲是：WRITELESS, DO MORE，写更少的代码做更多的事情
4. jQuery实现了浏览器的兼容问题

相关文档：W3School，[jQuery API 3.5.1 速查表](https://jquery.cuishifeng.cn/)

## 使用

Download the compressed, production jQuery 3.7.0，就是压缩过的

Download the uncompressed, development jQuery 3.7.0，是没有压缩过的

使用标签script，引入jQuery库文件即可使用

```html
<script type="text/javascript" src="./script/jquery-3.6.0.min.js"></script>
```

演示JavaScript和jQuery的按钮提示效果

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>jQuery快速入门</title>
    <!--    引入jquery库-->
    <script type="text/javascript" src="script/jquery-3.7.0.min.js"></script>
    <script type="text/javascript">
        /**
         * 使用dom编程
         * 1. 代码比较麻烦
         * 2. document.getElementById("btn01") 返回的是dom对象
         */
        // 使用原生js+dom完成：当页面加载完毕后，就执行function
        window.onload = function () {
            //1. 得到id=btn01 的dom对象
            var btn01 = document.getElementById("btn01");
            //2. 绑定点击事件
            btn01.onclick = function () {
                alert("hello, js");
            }
        }

        /**
         * 说明
         * 1. jquery的底层仍然是js,只是做了封装。使用时需要引入
         * 2. $(function () {} 等价  window.onload = function () {}
         * 3. $() 得到了一个jQuery对象，本质是对DOM对象的包装 [可以定义 function $(id) {} ...]
         *    $("#btn02") 底层: document.getElementById("btn02")
         *    通过断点调试，可以发现这是个数组对象
         * 4. jquery中，获取对象的方法是 $("#id"), 必须在id前有#
         *    因此 $("#btn02") 不能写成 $("btn02")
         * 5. 通过$("#btn02") 返回的对象就是 jquery对象(即进行了封装)，而不是原生的dom对象
         *
         * 备注：编程中，默认 jquery对象的命名以$开头(不是必须，但是约定)
         *
         */

        $(function (){
            var $btn02 = $("#btn02");
            $btn02.click(function (){
                alert("hello,jquery...~~~")
            })
        });

    </script>
</head>
<body>
<button id="btn01">JavaScript按钮</button>
<br><br>
<button id="btn02">使用jQuery按钮</button>

</body>
</html>
```

编程小技巧: 

* 不明的地方可以做测试
* 使用debug直接看对象构成
*  尽量能够看到效果，或者这个对象构成 => 了解
* 多动手
* 在练习代码使用时，输出的提示信息尽量不同，以示区别

## jQuery对象

1. jQuery对象就是对DOM对象进行包装后产生的对象

* 比如：`$("#test").html()` 意思是指：获取ID为test的元素内的html代码。其中html()是jQuery里的方法
* 这段代码等同于用DOM实现代码：`document.getElementById("id").innerHTML`

2. jQuery对象是jQuery独有的。如果一个对象是jQuery对象，那么它就可以使用jQuery里的方法，比如 $("#id").html();

3. 约定：如果获取的是jQuery对象，那么要在变量前面加上$（可用于判断网站前端使用的技术栈）

```html
var $variable：jQuery对象
var variable：DOM对象
```

4. 简化js编写的内容，例如在页面加载时调用的方法

```html
<script type="text/javascript">
	window.onload = function() {}//使用js的方法编写方法
    $(function(){})//使用jQuery编写方法
</script>
```



### DOM对象转为jQuery对象

1.对于一个DOM对象，只需要用$( ) 把DOM对象包装起来，就可以获得一个jQuery对象了。```$(DOM对象)```
2.DOM对象转换成JQuery对象后，就可以使用jQuery的方法了

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>DOM对象转成jQuery对象</title>
    <script type="text/javascript" src="./script/jquery-3.7.0.min.js"></script>
    <script type="text/javascript">
        window.onload = function (){
            //演示通过dom对象来获取输入框的value
            //username就是dom对象
            var username = document.getElementById("username");
            alert("username value= " + username.value);

            //通过jquery对象来获取输入框的value
            //把username dom 对象转成 jquery对象
            var $username = $(username);
            //使用jquery的方法来获取value
            alert("$username value= " + $username.val())
        }
    </script>
</head>
<body>
用户名 <input type="text" id="username" name="username" value="韩顺平教育"/>
</body>
</html>
```

三个简单实用的用于 DOM 操作的 jQuery 方法：

- text() - 设置或返回所选元素的文本内容
- html() - 设置或返回所选元素的内容（包括 HTML 标记）
- val() - 设置或返回表单字段的值

### jQuery对象转为DOM对象

1.两种转换方式将一个jQueiy对象转换成DoM对象：[index］和.get(index)

* jQuery对象是一个数组对象，可以通过[index]的方法，来得到相应的DOM对象
* jQuery本身提供，通过.get(index)方法，得到相应的DOM对象

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>jquery对象转成dom对象</title>
<!--    一定要引入jquery-->
    <script type="text/javascript" src="./script/jquery-3.7.0.min.js"></script>
    <script type="text/javascript">

        window.onload = function (){
            //得到jquery对象
            var $username = $("#username");
            alert("$username value= " + $username.val())

            //准备把jquery对象->dom
            //(1)jquery是一个数组对象，封装了dom对象
            //(2)可以通过[index] 来获取，也可以通过get(index)
            //(3)一般来说 index 是 0
            //方式1
            var username1 = $username[0];
            alert(username1)// 输出username 是 object HtmlInputElement
            alert("username1 value=" + username1.value);

            //方式2 => 老师提醒，在练习学习过程, 输出的提示信息尽量不同
            var username2 = $username.get(0);
            alert("username2 value~~~=" + username2.value)
        }
    </script>
</head>
<body>
用户名 <input type="text" id="username" name="username" value="这是一个文本框"/>
</body>
</html>
```

