## HTML

1. HTML（Hyper Text Mark-up Language）即超文本标签语言（展示内容很多）
2. HTML文本是由HTML标签组成的文本，可以包括文字、图形、动画、声音、表格、链接等
3. HTML的结构包括头部（Head）、主体（Body）两大部分，其中头部描述浏览器所需的信息，而主体体则包含所要说明的具体内容。

参考资料：

[HTML Tutorial (w3schools.com)](https://www.w3schools.com/html/)

[HTML（超文本标记语言） | MDN (mozilla.org)](https://developer.mozilla.org/zh-CN/docs/Web/HTML)

运行方式：本地运行、远程访问



说明：

1，HTML文件不需要编译，直接由浏览器进行解析执行
2.可以选择的浏览器，是你电脑安装有的浏览器，如果没有安装这个浏览器，会报错

```html
<!--文档类型说明 注释 -->
<!DOCTYPE html>
<!--表示整个html页面的开始，使用语言的地区 en 表示英国美国 en-US-->
<html lang="en">
<!--头信息-->
<head>
    <!--charset文件的字符集-->
    <meta charset="UTF-8">
    <!--文件标题-->
    <title>HTML标题</title>
</head>
<!--body体，主体部分，设置背景色为黄色-->
<body bgcolor="yellow">
<!--内容 注释的快捷键 ctrl + / ->
hello, world
<!--单标签-->
<hr/>
mary <br/>
smith
</body>
</html>

```



## html的标签/元素

1. HTML标签用两个尖括号"<>"括起来
2. HTML标签一般是双标签，如```<b>```和```</b>```前一个标签是起始标签，后一个标签为结束标签
3. 两个标签之间的文本是html元素的内容
4. 某些标签称为"单标签"，因为它只需单独使用就能完整地表达意思，如```<br/>```换行，```<hr/>```生成横线
5. HTML元素指的是从开始标签到结束标签的所有代码。



使用细节：

- 标签不能交叉嵌套
- 标签必须正确关闭
- 注释不能嵌套
- html 语法不严谨。有时候标签不闭合，属性值不带””也不报错
- 一些符号可以通过字符实体的方式显示

## 文字显示

标题标签

```
标题使用<h1>-<h6>标签进行定义。<h1>定义最大的标题。<h6>定义最小的标题 
align=对齐属性（left: 左对齐[默认]，center：居中，right：右对齐）
style：样式（在css中详细介绍）
<h3 style="color: blue">this is a h3</h3>
```

字体标签

    font 标签是字体标签,它可以用来修改文本的字体,颜色,大小(尺寸)
    <font size = 文本大小 face = 字体 color = 颜色>文本</font>
    (1)color 属性修改颜色
    (2)face 属性修改字体
    (3)size 属性修改文本大小
    多说一句，对应标签的属性，顺序不做要求

字符实体

    把 <hr />  变成文本 显示在页面上
    常用的特殊字符:
    < : &lt;
    > : &gt;
    空格 : &nbsp;

## 外部链接

超链接是指从一个网页指向一个目标的链接关系。这个目标可以是另一个网页，也可以是相同网页上的不同位置，还可以是一个图片，一个电子邮件地址，一个文件，甚至是一个应用程序

    a 标签是 超链接
    href 属性设置连接的地址，mailto表示发邮件
    target 属性设置哪个目标进行跳转
        _self : 表示当前页面(默认值), 即使用当前替换目标页
        _blank :  表示打开新页面来进行跳转
        
    <a href="http://www.sohu.com" target="_blank">搜狐2</a><br/>
    <a href="mailto:tom@sohu.com">联系管理员</a>

## 列表

无序列表

    ul :  表示无序列表
    li :  列表项
    type属性：指定列表项前的符号

有序列表

    ol :  表示有序列表
    li :  列表项
    type属性：指定列表项前排序方式
    type 设定数目款式，其值有五种，默认为 start="1"。
    i可以取以下值中的任意一个：
    1 阿拉伯数字 1, 2, 3, ...
    a 小写字母 a, b, c, ...
    A 大写字母 A, B, C, ...
    i 小写罗马数字 i, ii, iii, ...
    I 大写罗马数字 I, II, III, ... 。

## 图像标签

    应用实例：使用 img 标签显示一张美女的照片。
    img:  标签是图片标签,用来显示图片
    src:  属性可以设置图片的路径
    width:  属性设置图片的宽度 ->在进行图片缩放时，建议指定 width 或者 height 即可，浏览器会按照比例显示
    height: 属性设置图片的高度
    border:  属性设置图片边框大小
    alt:  属性设置当指定路径找不到图片时,用来代替显示的文本内容
    相对路径:从工程名开始算
    绝对路径:盘符:/目录/文件名
    
    路径：在 web 中路径分为相对路径和绝对路径两种
    相对路径: . 表示当前文件所在的目录
            .. 表示当前文件所在的上一级目录
    文件名 : 表示当前文件所在目录的文件,相当于 ./文件名 ./ 可以省略
    绝对路径: 正确格式是: http://IP地址:port/工程名/资源路径 ， 即使用url方式定位资源
            错误格式是: 盘符:/目录/文件名

## 表格标签

    table： 标签是表格标签 border： 设置表格边框宽度
    width： 设置表格宽度  height： 设置表格高度
    align： 设置表格相对于页面的对齐方式
    cellspacing： 设置单元格间距
    tr ：是行标签 th ：是表头标签  td ：是单元格标签
    align： 设置单元格文本对齐方式  b ：是加粗标签
    px:表示像素 - 和屏幕分辨率相关

表格标签的应用：跨行跨列表格

    column列
    合并列 : colspan="列数"
    合并行 : rowspan="行数"
    cellspacing : 指定单元格间的空隙大小 :0 表示没有空隙
    bordercolor: 指定表格边框的颜色
    border: 表格边框
    width： 表格的宽度
    老韩思路:
    1. 先把整个表格的完整的行和列，展示出来5*3
    2. 再使用合并的技术，来处理
    3. 如果是16进制的颜色，前面#

## 表单标签

```html
<form action="url" method=*>
...
<input type=submit><input type=reset>
</form>
```

标签的应用演示

```html
1. form 表示表单
2. action: 将form表单的数据提交给哪个url,设置提交的服务器地址
3. method： 提交方式（不区分大小写）,常用 get 和 post，默认是get
4. input type = *
text 文本框，password 密码框，submit 提交按钮，reset 重置按钮

小技巧: 为了个汉字对齐，输入全角的空格即可
说明：如果form表单的元素，没有写name属性，则数据不会提交
<h1>登录系统</h1>
<form action="ok.html" method="get">
    用户名:<input type="text" name="username"><br/>
    密　码:<input type="password" name="username"><br/>
    <input type="submit" value="登录"> <input type="reset" value="重新填写">
</form>
```


其他input元素：checked表示默认为选中状态，如果input的name不同，则无法识别为相互制约的选项

​    

```
input type=button 是按钮 value 属性修改按钮上的文本
input type=file 是文件上传域
input type=hidden 是隐藏域
select 标签是下拉列表框
option 标签是下拉列表框中的选项
selected="selected"设置默认选中
```

```html
请选择喜欢的水果：
	<input type="checkbox" name="水果">香蕉<p>
    <input type="checkbox" name="水果"checked>苹果<p>
婚姻状况：
	<input type="radio" name="marriage" value="no"checked>未婚<p>
    <input type="radio" name="marriage" value="yes">已婚<p>
    <input type="hidden"name="add "value="hoge@hoge.jp">这里有一个隐藏的表单元素<p>
    <input type="image"name="submit"align="top"src="shan.jpg">
```

select/option/textarea标签

说明：option value里面的值是提交给网页的数据，外部显示的是用户看到的内容；textarea 表示多行文本输入框 （起始标签和结束标签中的内容是默认值）
rows 属性设置可以显示几行的高度
cols 属性设置每行可以显示几个字符宽度

```html
请选择城市：
<select name="city">
<option value="tsingtao" selected>青岛</option>
<option value="peking">北京</option>
<option value="shanghai">上海</option>
<option value="canton">广州</option>
<option value="amoy">厦门</option>
</select>
自我介绍：
<textarea name="comment"rows="4"cols="25"></textarea>
请选择头像<input type="file" name="myfile"><br/>
```




​    

    老师提醒，一定一定使用form 标签将input元素包起来
    一定一定要给input元素设置 name属性，否则，数据提交不到服务器
    注意：checkbox是复选框，如果希望是同一组，要保证name属性一致
    注意：在checkbox  select radio 提交数据的时候是value属性的值，而不是后方显示的值

表单格式化：用无边框表格，约束表单的显示。在form中嵌套table





    1. 如果form表单的元素，没有写name属性，则数据不会提交
    2. 对应select checkbox radio 标签，提交的数据是value指定的值
    3. 对应checkbox 复选框，可以提交多个字，但是name是统一的，例如sprot=xx&sport=yy
    4. 提交的数据，一定要放在 form标签内，否则数据不会提交
    
    Get 和 Post 的区别简述
    1. Get 请求数据是显示在浏览器地址栏，地址是action属性[+?+请求参数]，请求参数的格式是name1=value1&name2=value2...例如：http://localhost:63342/html/ok.html?us，ername=jack&pwd1=111&pwd2=11&sport=lq&sport=sq&gender=male&city=bj&myfile=
    2. Get 请求不安全的，而且数据是有长度限制的（由浏览器进行限制）, 建议有重要信息，不要使用Get
    3. Post的浏览器地址栏只有action属性值，提交的数据携带在http请求中，不会展示在地址栏，相对安全
    4. Post 传输的数据长度理论上没有限制，但是在实际的应用中，也不能太大 , 即合理即可

## div标签

```
1.<div>标签可以把文档分割为独立的、不同的部分
2.<div>是一个块级元素。它的内容自动地开始一个新行。（不需要<br/>）
```

## p标签

定义段落，会自动在其前后创建一些空白



## span标签

1. span标签是内联元素（内嵌元素，行内元素），不像块级元素（如：div标签、p标签等）有换行的效果。
2. 如果不对span应用样式，span标签没有任何的显示效果
3. 语法：```<span>内容</span>```，往往是为了在某段文字中去单独控制某个关键内容