# jQuery DOM操作

## 加载DOM

在页面加载完毕后，浏览器会通过JayaScript为DOM元素添加事件。
在常规的JavaScript代码中，通常使用window.onload = function(){} 方法，在jQuery中使用$(document).ready()方法

| 方法     | window.onload                                          | xxxxxxxxxx import Vue from 'vue';import ElementUI from 'element-ui';import 'element-ui/lib/theme-chalk/index.css';import App from './App.vue';​Vue.use(ElementUI);//使用插件​new Vue({  el: '#app',  render: h => h(App)});javascript |
| -------- | ------------------------------------------------------ | ------------------------------------------------------------ |
| 执行时机 | 必须等待网页中的所有内容（包括图片）加载完毕后才能执行 | 网页中的所有DOM结构绘制完毕后就执行，可能DOM元素关联的东西并没有加载完 |
| 编写个数 | 不能同时编写多个                                       | 能同时编写多个                                               |
| 简化写法 | 无                                                     | $()                                                          |

```javascript
//关于页面加载完毕后, 触发的方法 的几种写法
        //1. js 的原生方式
        window.onload = function () {
            alert("页面加载完毕后。。方式1")
        }
        //2.方式 jquery方式
        $(document).ready(function () {
            alert("页面加载完毕后。。方式2")
        })
        //3. 上面的方式可以简化成常用写法
        $(function () {
            alert("页面加载完毕后。。方式3")
        })
```



## 属性操作

利用attr()方法，向其传入一个参数（属性名），得到属性的值；向其传入两个参数（属性名、属性值）可以修改属性



1.attr()：获取属性和设置属性

2.attr()传递一个参数时，即为某元素的获取指定属性

3.attr()传递两个参数时，即为某元素设置指定属性的值

4.jQuery中有很多方法都是一个函数实现获取和设置，如：attr()，html)，text()，val()，height()，width()，css()等

5.removeAttr()：删除指定元素的指定属性

* 注意：在处理多选框时，attr不如prop方法，原因是attr只能一次赋值，之后传入系统的值都不会更改，但是prop实时显示实际值。

应用实例：find-assign.html

## 创建节点

创建节点介绍
1.创建节点：使用jQuery的工厂函数 $(html标签)，会根据传入的html标记字符串创建一个jQuery对象
并返回

2.动态创建的新元素节点不会被自动添加到文档中，而是需要使用其他方法将其插入到文档中

3.当创建单个元素时，需注意闭合标签和使用标准的 XHTML 格式.例如创建一个 \<p> 元素，

```javascript
可以使用：$("<p/>")或$("<p></p>"),
不能使用：$("<p>") 或$("</P>")
```

4.创建文本节点就是在创建元素节点时直接把文本内容写出来；创建属性节点也是在创建元素节点时一起创建



第1组方法（内部插入法）

1.append(content)：向每个匹配的元素的内部的结尾处追加内容举例

2.appendTo(content)：将每个匹配的元素追加到指定的元素中的内部结尾处

3.prepend(content)：向每个匹配的元素的内部的开始处插入内容

4.prependTo(content)：将每个匹配的元素插入到指定的元素内部的

说明：内部插入法是在元素内插入内容（该内容变成该元素的子元素或节点）



第2组方法（外部插入法）

1.after(content)：在每个匹配的元素之后插入内容

2.before(content)：在每个匹配的元素之前插入内容

3.insertAfter(content)：把所有匹配的元素插入到另一个、指定的元素元素集合的后面

4.insertBefore(content)：把所有匹配的元素插入到另一个、指定的元素元素集合的前面

说明：

* 外部插入法是在元素的外面插入内容（其内容变成元素的兄弟节点）
* 对于无序列表，内部插入不会增加注解符号，外部插入会增加

6.注意：以上方法不但能将新的DOM元素插入到文档中，也能对原有的DOM元素进行移动

应用实例：create.html（创建新节点并添加到列表），move.html（在现有列表内移动）



## 删除节点

1. remove()：从DOM中删除所有匹配的元素，传入的参数用于根据jQuery表达式来筛选元素。当某个节点用remove() 方法删除后，该节点及其所有后代节点将被同时删除。这个方法的返回值是一个指向已被删除的节点引用
2. empty()：清空元素中的所有后代节点（仍保留属性节点）

应用实例：remove.html



## 复制节点

1、clone()：克隆匹配的DOM元素，返回值为克隆后的副本.但此时复制的新节点不具有任何行为.
2、clone(true)：复制元素的同时也复制元素中的的事件

```javascript
<button>保存</button>
<p>段落</p>
//克隆节点，不克隆事件
$("button").clone().appendTo("p");
//克隆节点，克隆事件
$("button").clone(true).appendTo("p");
```

应用实例：clone.html



## 替换节点

1.replaceWith()：将所有匹配的元素都替换为指定的HTML或DOM元素

2.replaceAll()：颠倒了的replaceWith()方法

3．注意：若在替换之前，已经在元素上绑定了事件，替换后原先绑定的事件会与原先的元素一起消失

```javascript
//<p>段落</p>
//方式一
$（"p").replaceWith（"<button>登陆</button>"）
//方式二
$("<button>登陆</button>").replaceAll("p");
```

应用实例：replace.html


## 样式操作

1.获取class和设置class：class是元素的一个属性，所以获取class和设置class都可以使用attr()方法来完成

2.追加样式：addclass()

3.移除样式：removeclass()---从匹配的元素中删除全部或指定的class

4.切换样式：toggleclass()---控制样式上的重复切换.如果类名存在则删除它，如果类名不存在则添加它，

5.判断是否含有某个样式：hasclass()---判断元素中是否含有某个class，如果有，则返回true；否则返回false

应用实例：css-style.html

## 获取HTML，文本和值

html()-设置或返回所选元系的内容（包括HTML标记）。

text()-设置或返回所选元素的文本内容，既适用于HTML也可用于XML文档

val()-设置或返回表单字段（文本框、下拉列表、单选框）的值，类似JavaScript的value书刷了



应用实例：login.html

JavaScript中DOM可以通过 this.defaultValue 获取表单元素的默认值属性

## 常用遍历节点方法

1. 取得配元素的所有子元素组成的集合：children() 该方法只考虑子元素而不考虑任何后代元素
2. 取得匹配元素后面同辈元素的集合：next()/nextAll()
3. 取得匹配元素前面同辈元素的集合：prev()/prevAll()
4. 取得匹配元素前后所有的同辈元素：siblings()
5. 获取指定的第几个元素：nextAll().eq(index)
6. 对获取到的同辈元素进行过滤nextAll().filter("标签")

说明：

* 后面的同辈元素不一定属性相同，div标签执行prevAll() 方法可以获取到p标签等
* 如果要获取后面的同辈div标签，可以用nextAll().filter("div")

应用实例：sibling.html

## CSS-DOM操作

| CSS 属性       | 描述                                     |
| -------------- | ---------------------------------------- |
| css()          | 设置或返回匹配元素的样式属性。           |
| height()       | 设置或返回匹配元素的高度。               |
| width()        | 设置或返回匹配元素的宽度。               |
| offset()       | 返回第一个匹配元素相对于文档的位置。     |
| offsetParent() | 返回最近的定位祖先元素。                 |
| position()     | 返回第一个匹配元素相对于父元素的位置。   |
| scrollLeft()   | 设置或返回匹配元素相对滚动条左侧的偏移。 |
| scrollTop()    | 设置或返回匹配元素相对滚动条顶部的偏移。 |
| opacity()      | 设置或返回匹配元素的透明度。             |

* 在设置高度和宽度值时，如果传入数字则默认单位是px，如果要使用其他单位需要传入字符串，例如$("p:first").height("2em");
* offset()方法返回的对象包含top和left两个属性，该方法只对可见元素有效



## 综合案例

1）通过按钮和点击实现左右两个列表框中的内容移动

①点击->把左边选中的移动到右边

②点击==>把左边全部移动到右边

③双击左边或者右边的某个选项，将其自动移动到另外一边

思路：（1）绑定事件（2）选择对应的对象（3）内部插入

代码实现：multiselect.html



2）显示选中的多选框个数及名称，通过按钮改变选中的多选框

代码实现：checkbox.html



3）按下按钮，可以自动选中input标签的某个值

代码实现：selectbutton.html



4）动态管理用户信息

代码实现：dynamic.html

