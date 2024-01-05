# jQuery选择器

1. 选择器是jQuery的核心，在jQuery中，对事件处理，遍历DOM和Ajax操作都依赖于选择器
2. jQuery选择器的优点
   * 简洁的写法
     * \$("#id")  等价于 document.getElementById("id");
     * $("tagName")  等价于 document.getElementsByTagName"tagName");
   * 完善的事件处理机制
     * 如果没有获取到DOM对象（得到的是null），获取value时js会报错，需要用if语句进行判断并处理
     * 如果没有获取到jQuery对象，只会提示undefined不会报错，程序健壮性更强

## 基本选择器

| 选择器       | 实例              | 返回值                                    | 说明                                                         |
| ------------ | ----------------- | ----------------------------------------- | ------------------------------------------------------------ |
| *            | $("*")            | 所有元素                                  | 匹配所有元素，多用于结合上下文来搜索                         |
| #id值        | $("#lastname")    | id="lastname" 的元素                      | 直接选择html中的id="lastname"                                |
| .class值     | $(".intro")       | 所有 class="intro" 的元素                 | 直接选择html代码中class="myclass"的元素或元素组（因为在同一html页面中class是可以存在多个同样值的） |
| element      | $("p")            | 所有 \<p> 元素                            | html已经定义的标签元素，例如div，input，a等等，类似于getElementByTagName |
| .class.class | $(".intro.demo")  | 所有 class="intro" 且 class="demo" 的元素 |                                                              |
| s1,s2,s3     | $("th,td,.intro") | 所有带有匹配选择的元素                    | 指定任意多个选择器，并将结果合并到结果中。.intro表示匹配class="intro"的元素 |

应用实例：编写代码实现下列功能

1. 改变id为one的元素的背景色为 #0000FF
2. 改变cass为mini的所有元素的背景色为 #FF0033
3. 改变元素名为\<div>的所有元素的背景色为 #00FFFF
4. 改变所有元素的背景色为 #00FF33
5. 改变所有的\<span>元素和id为two的元素的背意色为 #3399FF

代码实现：base-selector.html

参考链接：[jQuery Selectors (w3schools.com)](https://www.w3schools.com/jquery/jquery_ref_selectors.asp)



## 层级选择器

如果想通过DOM元素之间的层次关系来获取特定元素，例如后代元素，子元素，相邻元素，兄弟元素等，则需要使用层级选择器

| 选择器              | 实例                 | 返回值                               |
| ------------------- | -------------------- | ------------------------------------ |
| ancestor descendant | $("form input")      | 在给定的祖先元素下匹配所有后代元素   |
| parent > child      | $("form > input")    | 在给定的父元素下匹配所有子元素。     |
| prev + next         | $("prev + next")     | 匹配所有紧接在prev元素后的next元素   |
| prev ~ siblings     | $("prev ~ siblings") | 匹配prev元素之后的所有siblings元素。 |

注意：

* 区分后代元素与子元素
* $("#two~ div")选择器只能选择 "#two" 元素后面的同辈\<div>元素
* $("#two").siblings("div")可以选择"#two"元素所有的同辈\<div>元素，和位置无关

应用实例：编写代码实现下列功能

1. 改变 \<body> 内所有 \<div> 的背景色为 #0000FF
2. 改变 \<body> 内子 \<div> 的背景色为 #FF0033
3. 改变 id 为 one 的下一个 \<div> 的背景色为 #0000FF
4. 改变 id 为 two 的元素后面的所有兄弟 \<div> 的元素的背景色为 #0000FF
5. 改变 id 为 two 的元素所有 \<div> 兄弟元素的背景色为 #0000FF

代码实现：layer-selector.html

参考链接：[jQuery Cheat Sheet (oscarotero.com)](https://oscarotero.com/jquery/)



## 基础过滤选择器

| 选择器         | 实例                   | 返回值                                | 说明     |
| -------------- | ---------------------- | ------------------------------------- | -------- |
| :first         | $("p:first")           | 第一个 \<p> 元素                      |          |
| :last          | $("p:last")            | 最后一个 \<p> 元素                    |          |
| :not(selector) | $("input:not(:empty)") | 所有不为空的 input 元素               | 类似“非” |
| :even          | $("tr:even")           | 所有偶数 \<tr> 元素                   | 从0计数  |
| :odd           | $("tr:odd")            | 所有奇数 \<tr> 元素                   | 从0计数  |
| :eq(index)     | $("ul li:eq(3)")       | 列表中的第四个元素（index 从 0 开始） |          |
| :gt(no)        | $("ul li:gt(3)")       | 列出 index 大于 3 的元素              |          |
| :lt(no)        | $("ul li:lt(3)")       | 列出 index 小于 3 的元素              |          |
| :header        | $(":header")           | 所有标题元素 \<h1> - \<h6>            |          |
| :animated      | 所有动画元素           |                                       |          |

说明：

* 元素匹配顺序：从上到下，从左到右

应用实例：编写代码实现下列功能

1.改变第一个 div 元素的背景色为 #0000FF

2.改变最后一个 div 元素的背景色为 #0000FF

3.改变class不为 one 的所有 div 元素的背景色为 #0000FF

4.改变索引值为偶数的 div 元素的背景色为 #0000FF

5.改变索引值为奇数的 div 元素的背景色为 #0000FF

6.改变索引值为大于 3 的 div 元素的背景色为 #0000FF

7.改变索引值为等于 3 的 div 元素的背景色为 #0000FF

8.改变索引值为小于 3 的 div 元素的背景色为 #0000FF

9.改变所有的标题元素的背景色为 #0000FF

代码实现：base-fliter-selector.html



## 内容过滤选择器

| 选择器          | 实例                             | 运行结果                               |
| --------------- | -------------------------------- | -------------------------------------- |
| :contains(text) | $(":contains('W3School')")       | 返回所有包含字符串"W3School"的元素     |
| :empty          | $(":empty")                      | 返回无子（元素）节点的所有元素         |
| :has(selector)  | $("div:has(p)").addClass("test") | 给所有包含p元素的div标签加class="test" |
| :parent         | $("td:parent")                   | 返回含有子元素或文本的元素             |

* 如果某个div的子div中包含contains匹配的字符串，那么contains选择器也会选中该div
* $("div.mini")表示class="mini"的div元素（子div），\$("div:has('.mini')")表示class="mini"对应div元素的父div

应用实例：编写代码实现下列功能

1.改变含有文本 ‘di’ 的 div 元素的背景色为 black

2.改变不包含子元素(或者文本元素) 的 div 的背景色为 pink

3.改变含有 class 为 mini 元素的 div 元素的背景色为 green

4.改变含有子元素(或者文本元素)的div元素的背景色为 yellow

5.改变索引值为大于 3 的 div 元素的背景色为 #0000FF

6.改变不含有文本 di; 的 div 元素的背景色 pink

代码实现：content-filter-selector.html



## 可见度过滤选择器

| 选择器   | 实例               | 运行结果             |
| -------- | ------------------ | -------------------- |
| :hidden  | $("p:hidden")      | 所有隐藏的 \<p> 元素 |
| :visible | $("table:visible") | 所有可见的表格       |

* (:hidden)能匹配到 css 中 display:note 和 html 中 input type="hidden" 的元素

应用实例：编写代码实现下列功能

1.改变含有文本 ‘di’ 的 div 元素的背景色为 black

2.改变不包含子元素(或者文本元素) 的 div 的背景色为 pink

3.改变含有 class 为 mini 元素的 div 元素的背景色为 green

4.改变含有子元素(或者文本元素)的div元素的背景色为 yellow

5.改变索引值为大于 3 的 div 元素的背景色为 #0000FF

6.改变不含有文本 di; 的 div 元素的背景色 pink

代码实现：visible-filter-selector.html

## 属性过滤选择器

通过元素的属性获取相应的元素

| 选择器                        | 实例                          | 运行结果                                                |
| ----------------------------- | ----------------------------- | ------------------------------------------------------- |
| [attribute]                   | $("[href]")                   | 所有带有 href 属性的元素                                |
| [attribute=value]             | $("[href='#']")               | 所有 href 属性的值等于 "#" 的元素                       |
| [attribute!=value]            | $("[href!='#']")              | 所有 href 属性的值不等于 "#" 的元素，以及不含href的元素 |
| [attribute^=value]            | $("[href^='News']")           | 所有 href 属性值以 "News"  开头的元素                   |
| [attribute$=value]            | \$("[href$='.jpg']")          | 所有 href 属性值以 ".jpg"  结尾的元素                   |
| [attribute*=value]            | $("[href*='Monodraw']")       | 所有 href 属性值包含 "Monodraw"  的元素                 |
| \[过滤器1]\[过滤器2][过滤器3] | \$("input\[id][name$='man']") | 满足所有过滤条件的元素                                  |

* $("[href!='#']") 除了选中href不等于"#"的元素外，还选中了不含href的元素

应用实例：编写代码实现下列功能

1.含有属性title 的div元素

2.属性title值等于test的div元素

3.属性title值不等于test的div元素(没有属性title的也将被选中)

4.属性title值 以te开始 的div元素

5.属性title值 以est结束 的div元素

6.属性title值 含有es的div元素

7.选取有属性id的div元素，然后在结果中选取属性title值含有“es”的 div 元素

代码实现：attribute-fliter-selector.html

## 子元素过滤选择器

| 选择器           | 实例                    | 运行结果                                           |
| ---------------- | ----------------------- | -------------------------------------------------- |
| :nth-child(条件) | $("ul li:nth-child(2)") | 匹配其父元素下的奇偶元素或第N个子元素，编号从1开始 |
| :first-child     | $("ul li:first-child")  | 为每个父元素匹配第一个子元素                       |
| :last-child      | $("ul li:last-child")   | 为每个父元素匹配最后一个子元素                     |
| :only-child      | $("ul li:only-child")   | 匹配父元素中的唯一子元素                           |

* 子元素过滤选择器:nth-child的数值从1开始编号，基础过滤选择器的eq()从0开始编号
* ":fitst"只匹配一个元素，":fitst-child"为每个父元素匹配一个子元素
* :nth-child(index/even/odd/equation)详解
  * :nth-child(even/odd)：能选取每个父元素下的索引值为偶（奇）数的元素
  * :nth-child(2)：能选取每个父元素下的索引值为2的元素
  * nth-child(3n)：能选取每个父元素下的索引值是3的倍数的元素
  * nth-child(3n+1)：能选取每个父元素下的索引值是3n+1的元素

应用实例：编写代码实现下列功能

1.每个class为one的div父元素下的第2个子元素

2.每个class为one的div父元素下的第一个子元素

3.每个class为one的div父元素下的最后一个子元素

4.如果class为one的div父元素下的仅仅只有一个子元素，那么选中这个子元素

代码实现：child-filter-selector.html

## 表单对象属性选择器

| 选择器    | 实例                        | 运行结果                |
| --------- | --------------------------- | ----------------------- |
| :enabled  | $(":enabled")               | 所有激活的 input 元素   |
| :disabled | $(":disabled")              | 所有禁用的 input 元素   |
| :selected | $("select option:selected") | 所有被选取的 input 元素 |
| :checked  | $(":checked")               | 所有被选中的 input 元素 |

* checked匹配的是复选框、单选框等，selected匹配的是select中的option

应用实例：编写代码实现下列功能

1.利用 jQuery 对象的 val() 方法改变表单内 type=text 可用 \<input> 元素的值

2.利用 jQuery 对象的 val() 方法改变表单内 type=text 不可用 \<input> 元素的值

3.利用 jQuery 对象的 length 属性获取多选框选中的个数

4.利用 jQuery 对象的 text() 方法获取下拉框选中的内容

代码实现：form-object-filter-selector.html

## 表单选择器

| 选择器    | 实例              | 运行结果                              |      |
| --------- | ----------------- | ------------------------------------- | ---- |
| :input    | $(":input")       | 所有 \<input> 元素                    |      |
| :text     | $(":text")        | 所有 type="text" 的 \<input> 元素     |      |
| :password | $(":password")    | 所有 type="password" 的 \<input> 元素 |      |
| :radio    | $(":radio")       | 所有 type="radio" 的 \<input> 元素    |      |
| :checkbox | $(":checkbox")    | 所有 type="checkbox" 的 \<input> 元素 |      |
| :submit   | $(":submit")      | 所有 type="submit" 的 \<input> 元素   |      |
| :reset    | $(":reset")       | 所有 type="reset" 的 \<input> 元素    |      |
| :button   | $(":button")      | 所有 type="button" 的 \<input> 元素   |      |
| :image    | $(":image")       | 所有 type="image" 的 \<input> 元素    |      |
| :file     | $(":file")        | 所有 type="file" 的 \<input> 元素     |      |
| :hidden   | $("input:hidden") |                                       |      |

* 直接使用:hidden会匹配到页面中所有不可见元素，包括宽度或高度为0的；使用$("input:hidden")可将选中范围缩小为表单元素

应用实例：区分 \$(":button")，\$("input[type='button']") 和 $("button")

代码实现：form-filter-selector.html

## 练习代码

练习1：给网页中所有段落元素添加onclick事件。代码实现：Homework1.html

练习2：使一个特定的表格隔行变色。代码实现：Homework2.html
