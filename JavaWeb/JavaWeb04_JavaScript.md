# JavaScript

JavaScript能改变HTML内容，能改变HTML属性，能改变HTML样式（CSS），能完成页面的数据验证。

1. JavaScript是一种解释型的脚本语言，C、C++等语言先编译后执行，而JavaScript是在程序的运行过程中逐行进行解释
2. JavaScript是一种基于对象的脚本语言，可以创建对象，也能使用现有的对象。（有对象概念）
3. JavaScript是弱类型的，对变量的数据类型不做严格的要求，变量的数据类型在运行过程可以变化。

## JavaScript的两种使用方式

1、使用script标签写js代码

可以在head和body中嵌入script，执行顺序从上到下，建议放在head

2、使用script标签引入.js文件

```<script type="text/javascript" src="./js/test.js"></script>```

说明：如果同时使用者两种方式，程序不会报错但只能生效一个

## 查看JS错误信息

进入到调试器Ctrl+Shift+I（或是快捷键F12），查看错误提示

[使用Console技巧提高JS调试技能 | 三钻 Benny X Guo](https://blog.bennyxguo.com/post/8c6e317d34a70d85c4a6b275416b9854)

[「1024」专属序猿的快乐，惊奇迷惑代码大赏 | 未读代码 (wdbyte.com)](https://www.wdbyte.com/2020/10/2020-1024/#据说很nb)

## 变量

通过typeof() 方法，可以返回变量的数据类型

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>JavaScript是弱类型的</title>
    <!--案例演示-->
    <!--
        老师解释
        1. js代码可以写在 script标签中
        2. type="text/javascript" 表示这个脚本(script)类型是javascript
        3. type="text/javascript" 可以不写，但是建议写上
        4. js语句结尾可以不写 ; 建议写上
        5. var表示后面的内容是变量、
    -->
    <script type="text/javascript">
        //弱类型
        //输出 alert() 使用弹框方式
        //输出 console.log() 在调试输出，此处也可是error()warn()等

        var age = 10;//数值
        console.log("age=" + age)
        console.log(typeof age);//typeof, 输出变量的类型
        age = "北京";//重新赋值
        console.log("age=" + age)
        console.log(typeof age);

    </script>
</head>
<body>

</body>
</html>

```

数据类型

|  js类型  |          含义          |
| :------: | :--------------------: |
|  number  |    数值（包含小数）    |
|  string  | 字符串（包含单个字符） |
|  object  |          对象          |
| boolean  |          布尔          |
| function |          函数          |

注意事项：
String类型可以双引号括起来，也可以用单引号括起来

特殊值

|  js类型   |              含义               |
| :-------: | :-----------------------------: |
| undefined | 变量未赋初始值时，默认undefined |
|   null    |              空值               |
|    NaN    | Not a Number，不能识别的非数值  |

## 运算符

算术运算符、赋值运算符和java类似

条件运算符类似java的三元运算符

关系运算符中，还有一种全等运算符"==="，判断值和数据类型是否完全一致

逻辑运算符只有短路与"&&"、短路或"||"、非"!"

- 所有的变量都可以作为boolean类型的变量使用
- 0、null、undefined、""(空字符串)、NaN 都是false
- "&&"和"||"运算的短路现象：有确定结果后，后面的表达式不再执行
  - "&&"表达式有一个为假时，返回第一个假的表达式；全为真时，返回最后一个表达式
  - "||" 表达式有一个为真时，返回第一个真的表达式；全为假时，返回最后一个表达式

```javascript
var n1 = 1;
var n2 = 3;
var res4 = n1++ || n2++;
alert("n1=" + n1+ ",n2="+ n2);//n1=2,n2=3
```

## 数组

```javascript
//js数组的定义和扩容都比java更简单
//数组定义方式1
var cars1 = ["Audi", "BMW", "Volvo"];

//数组定义方式2
var cars2 = [];
cars2[0] = "奥迪";
cars2[1] = "宝马";
cars2[2] = "沃尔沃";

//数组定义方式3
var cars3 = new Array("TSLA","XPEV","NIO");

//数组定义方式4
var cars4 = new Array();
console.log(typeof cars4);//object
cars4[0] = "法拉利";
cars4[1] = "保时捷";
cars4[4] = "布加迪";//赋值时跳过下标，中间没赋值的元素为undefined

//数组的输出
console.log("cars1=" + cars1);
console.log(cars1[1]);//索引从零开始编号
console.log(cars1[5]);//如果元素不存在，返回undefined

//数组的遍历
for(i = 0; i < cars1.length; i++) {
    console.warn(cars1[i]);
}
```

## 函数Function

如果不调用函数，那么该函数时不会执行。

定义函数的方式：①使用function关键字②将函数赋给变量

执行函数的方式：①主动调用②通过事件触发

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>函数快速入门</title>
    <script type="text/javascript">
        //没有形参没有返回值的函数(由变量定义)
        var f1 = function() {
            //alert()方法不能直接输出隐形参数的内容，需要遍历取值
            var string = ""
            for(i = 0; i < arguments.length; i++) {
                string += arguments[i]
            }
            alert(string)
            //如果希望console.log输出对象的数据，使用逗号","遍历
            console.log("arguments", arguments);//可在控制台中查看隐形参数arguments的内容
        }

        //有形参和返回值的函数(由function关键字定义)
        //js函数的不需要指定数据类型，形参的类型由传入的实参决定，返回数据类型由返回数据决定
        function f2(n1, n2) {
            return n1 + n2;
        }
        alert("f2(1,\"唔\",51)=" + f2(1,"唔",51));//代码块中主动调用函数
    </script>
</head>
<body>
<!--
    这里表示给button绑定了onclick事件
    当用户点击了该button，就会触发 hi() 函数
-->
<button onclick="f1(1,1,4,5,1,4)">点击这里</button>
</body>
</html>
```

函数的注意事项：

1. JS中函数的重载会覆盖掉上一次的定义，调用时如果没有传入实参，那么形参就是undefined
2. 函数的arguments隐形参数（作用域在function函数内）
   * 隐形参数：在function函数中不需要定义，可以直接用来获取所有参数的变量。
   * 隐形参数类似ava的可变参数，操作类似数组。public void fun (int...args
   * 如果函数有形参，在传入实参的时候，仍然按照顺序匹配；不管是否匹配上，所有实参都会传入arguments；如果形参个数＞实参个数，则未匹配的形参值为undefined

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>函数小练习</title>
    <script type="text/javascript">
        //要求：编写一个函数。用于计算所有参数相加的和并返回，如果实参不是number，就过滤掉
        var sum = function() {
            var res = 0
            for (i = 0; i < arguments.length; i++) {
                if (typeof(arguments[i]) == "number"){
                    res += arguments[i]
                }
            }
            return res
        }
        alert(sum(57057,"蓝村西",12306))
    </script>
</head>
<body>
</body>
</html>
```

console.log()的说明：

* 字符串 , 对象：输出对象的完整信息
* 字符串 + 对象：字符串拼接，只能看到对象的类型

## 自定义对象

1.对象的定义

```javascript
//1.对象的定义（Object形式）
var 对象名 = new Object();//对象实例（空对象）
对象名.属性名 = 值;//定义一个属性
对象名.函数名 = function(){}//定义一个函数

//2.对象的定义（{}形式）
var 对象名 = {
属性名: 值, //定义属性
属性名: 值, //多个属性和函数之间用逗号","隔开
函数名: function(){},//定义函数
函数名: function(){}
}
```

2.对象的访问

对象名.属性
对象名.函数名();

说明：

- 函数内可以用 "this.属性名" 获取属性值，外部访问需要使用 “对象名.属性名”

- 如果没有定义属性就使用，不会报错但是会提示undefined
- 如果使用没有定义的方法，会报错Uncaught TypeError

## 事件Event

事件的分类

* onload 加载完成事件
* onclick 单击事件
* onblur 失去焦点事件：注册用户，鼠标移开后判断名称是否合法
* onchange 内容改变事件：下拉菜单选择省市
* onsubmit 表单提交事件

1. 事件的注册（绑定）：当事件响应（触发）后要浏览器执行哪些操作代码（一般是函数），叫事件注册或事件绑定
2. 静态注册事件：通过html标签的事件属性直接赋予事件响应后的代码，这种方式叫静态注册
3. 动态注册事件：通过js代码得到标签的dom对象，然后再通过 dom对象.事件名=function(){} 这种形式叫动态注册



```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>事件触发条件</title>
    <script type="text/javascript">
        //动态注册函数
        //方法一：dom对象.事件名=fucntion(){}
        window.onload = function () {//页面加载完成后触发

            //方法二：获取标签对应的dom对象，并设置其方法
            var Button02 = document.getElementById("btn02");
        	//Button02就是按钮对应的dom对象
            Button02.onclick = function () {//点击后触发
                alert("按钮的dom类型是" + Button02)
            }

            var fname2 = document.getElementById("fname2");
            fname2.onblur = function () {//失去焦点后触发
                fname2.value = fname2.value.toLowerCase()
            }

            var province = document.getElementById("province");
            province.onchange = function () {
                alert("已切换城市")
            }
        }

        function f2() {
            alert("静态注册方法f2()")
        }

        function upperCase() {
            //先得到fname输入框的value
            var fname = document.getElementById("fname");
            fname.value = fname.value.toUpperCase();
        }
        function changeArea(){
            alert("已完成地区更改")
        }

    </script>
</head>
<body>
hello~
<input type="text" value="测试"/>
<br/>

<button onclick="f2()">执行f2()方法</button>
<button id="btn02">获取按钮类型</button>
<br/>

静态绑定转大写：
<input type="text" id="fname" onblur="upperCase()"/><br/>
动态绑定转小写
<input type="text" id="fname2"/><br/>

<select onchange="changeArea()">
    <option>--选择地区--</option>
    <option>山河四省</option>
    <option>西南五省</option>
    <option>东南六省</option>
</select>

<select id="province">
    <option>--选择城市--</option>
    <option>北京</option>
    <option>上海</option>
    <option>广州</option>
</select>
</body>
</html>
```

## 表单提交事件

onsubmit：注册按钮被点击，则调用action中的链接提交表单（需求：如果用户名或密码为空，不能提交表单）。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>onsubmit 表单提交事件</title>
    <script type="text/javascript">
        //静态注册表单提交事件
        function register() {
            //先得到输入的用户名和密码
            var username = document.getElementById("username");
            var pwd = document.getElementById("pwd");
            //判断是否为空""
            if ("" == username.value || "" == pwd.value) {
                alert("用户名和密码不能为空, 不能提交");
                return false;//不提交
            }
            //表示要提交
            return true;
        }
        //动态注册表单提交事件
        window.onload = function () {
            //使用折半法, 观察原页面是否真的是最新的, 是不是修改的页面和访问的页面一致
            //得到 from2 表单dom对象
            var form2 = document.getElementById("form2");

            // 给form2绑定onsubmit事件
            // 动态注册中， onsubmit绑定的函数，会直接将结果(false,true)返回给onsubmit，因此不需要再return
            form2.onsubmit = function () {
                if(form2.username.value == "" || form2.pwd.value == "") {
                    alert("用户名和密码不能为空, 不能提交");
                    return false;//不提交
                }
                return true;
            }
        }

    </script>
</head>
<body>
<h1>注册用户（静态方法）</h1> <!-- 静态注册表单提交事件,onsubmit中必须要有return -->
<form action="ok.html" onsubmit="return register()">
    u: <input type="text" id="username" name="username"/><br/>
    p: <input type="password" id="pwd" name="pwd"/><br/>
    <input type="submit" value="注册用户"/>
</form>
<h1>注册用户（动态方法）</h1> <!-- 动态注册表单提交事件 -->
<form action="ok.html" id="form2">
    u: <input type="text" name="username"/><br/>
    p: <input type="password" name="pwd"/><br/>
    <input type="submit" value="注册用户"/></form>


</body>
</html>

```

