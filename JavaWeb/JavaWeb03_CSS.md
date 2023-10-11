# CSS

层叠样式表（Cascading Style Sheets）

在CSS出现前，修改HTML元素的样式需要为每个HTML元素单独定义样式属性，CSS可将内容与样式分离，提高web开发的工作效率。

CSS的效果演示：[The Mad Magazine Fold-In Effect in CSS | Thomas Park](https://thomaspark.co/2020/06/the-mad-magazine-fold-in-effect-in-css/)

CSS的语法

1. CSS语法可以分为两部分：（1）选择器（2）声明
2. 声明由属性和值组成，多个声明之间用分号分隔，一般每行只描述一个属性
3. 最后一条声明可以不加分号
4. CSS的注释：```/*注释内容*/```



## CSS 的三种使用方式

方式1：在标签的style属性上设置css样式

方式2：在head标签中，使用style标签来定义需要的CSS样式

方式3（推荐使用）：把CSS样式stylesheet写成单独的CSS文件，再通过link标签引入（在href指定具体路径），可省略

```<link rel="stylesheet" href="引入的css文件" type="text/css" />```

说明：```type="text/css"```可以有，也可以不写，例如

```<link rel="stylesheet" href="./css/example.css" />```



## 常用样式

### 字体

```css
font-size：指定大小，可以按照像素大小
font-weight：指定是否粗体
font-family：指定字体类型（需要在电脑中安装有此字体）

text-transform: capitalize 使文本以大写字母开头

color：指定颜色
```

颜色表示方式：

①英文名，比如 Aqua；

②rgb值，比如rgb(0,255,255)；

③十六进制表示值，比如#00FFFF



### 边框

width/height 可指定具体像素值，也可以是占用页面的百分比

border:1px dashed blue 分别指定边框的宽度、线条类型（fashed虚线/solid实线）、颜色

background-color



### div居中显示

```css
/*文本框在页面居中：设置其左右边距*/
margin-left: auto; 
margin-right: auto;

/*文字在文本框居中，也可设置为left或right*/
text-aligh: center
```

### 去掉超链接的下划线

text-decoration: none;

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>css 快速入门</title>
<!--
    老师解读
    1. 在head标签内，出现了 <style type="text/css"></style>
    2. 表示要写css内容
    3. div{} 表示对div元素进行样式的指定, div就是一个选择器(元素/标签选择器)
    4. width: 300px(属性); 表示对div样式的具体指定, 可以有多个
    5. 如果有多个，使用; 分开即可, 最后属性可以没有; 但是建议写上
    6. 当运行页面时，div就会被 div{} 渲染，修饰
    7. 小经验：在调试css时，可以通过修改颜色，或者大小来看
    8. css的注释是 /* */ ，类似java。快捷键 ctrl+/，
-->
<style type="text/css">
    div {
        /*边框设置*/
        width: 200px;
        height: 100px;
        background-color: aqua;
        border: 3px solid rebeccapurple;
        /*边框居中显示*/
        margin-left: auto;
        margin-right: auto;
        /*字体设置*/
        font-size: 35px;
        font-weight: bold;
        font-family: 微软雅黑;
        /*字体居中显示*/
        text-align: center;
    }
    a {
        /*超链接去下划线*/
        text-decoration: none;
    }
</style>
</head>
<body>
<div>你好啊</div>
<a href="https://www.baidu.com/">百度</a>
</body>
</html>
```



## 表格显示效果

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>表格细线</title>
    <style type="text/css">
        /*
            设置边框 : border: 1px solid black
            将边框合并: border-collapse: collapse;
            指定宽度： width
            设置边框： 给 td, th 指定即可 border: 1px solid black;

            老韩解读
            1. table, tr, td 表示组合选择器
            2. 就是table(外边框)和tr（每一行）还有td（每个表格）,都用统一的样式指定, 提高复用性
         */
        table, tr, td {
            width: 300px;
            border: 1px solid black;
            border-collapse: collapse;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <td align=center colspan="3">星期一菜谱</td>
    </tr>

    <tr>
        <td rowspan=2>素菜</td>
        <td>青草茄子</td>
        <td>花椒扁豆</td>
    </tr>
    <tr>
        <td>小葱豆腐</td>
        <td>炒白菜</td>
    </tr>
    <tr>
        <td rowspan=2>荤菜</td>
        <td>油闷大虾</td>
        <td>海参鱼翅</td>
    </tr>
    <tr>
        <td>红烧肉</td>
        <td>烤全羊</td>
    </tr>
</table>

</body>

</html>

```



## 列表去修饰

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>列表去修饰</title>
    <style type="text/css">
        ul {
            /*说明:list-style:none表示去掉默认的修饰*/
            list-style: none;
            /*
            也可以指定无序列表修饰的样式（此处指定为方形）
            list-style-type: square;
            */
        }
    </style>
</head>
<body>
<ul>
    <li>三国演义</li>
    <li>红楼梦</li>
    <li>西游记</li>
    <li>水浒传</li>
</ul>
</body>
</html>

```





## CSS 选择器

### 元素选择器（全部）

1.最常见的CSS选择器是元系选择器。换句话说，文档的元系就是最基本的选择器。

2.CSS元素/标签选择器通常是某个HTML元素，比如P、h1、a、div等

元素选择器会修饰所有对应的元素

### ID选择器（唯一）

1.id选择器可以为标有特定id的HTML元素指定特定的样式。

2.id选择器以“#"来定义。

使用方式：在元素标签中定义id（不能重复），然后在 ```<style>``` 标签中指定具体样式，id选择器名称为"#元素id值"

### 类选择器（部分）

通过class属性选择来使用样式，需要在被修饰元素上设置class属性。

基本语法 ```.class 属性值{属性: 值;}```

class属性的值可以重复



### 组合选择器

可以让多个选择器共用同一个css样式代码

语法格式 选择器1, 选择器2, ...选择器n {属性: 值;}



### 优先级说明

行内样式>ID选择器>class选择器>元素选择器

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>选择器的优先级说明</title>
    <!--
        老韩解读
        1. 优先级 行内样式 > id选择器 > class选择器 > 元素选择器
    -->
    <style type="text/css">
        #id1 {
            color: yellow;
        }
        .cls1 {
            color: green;
        }
        div {
            color: brown;
        }
        p {
            width: 400px;
            height: 100px;
            border: solid red;
            /*border-width 按照 上右下左 顺时针方向指定*/
            border-width: 20px 2px 8px 8px;
        }
    </style>
</head>
<body>
<h1>选择器的优先级说明</h1>
<div style="color: red" id="id1" class="cls1">韩顺平教育</div>
<p>p段落</p>
</body>
</html>

```

