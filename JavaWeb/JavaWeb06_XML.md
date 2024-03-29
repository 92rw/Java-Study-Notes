# XML

可扩展标记语言，被设计用来传输和存储数据。

在java项目开发中：Spring框架的ico配置文件beans.xml，mybatis的Mapper.xml，Maven的pom.xml，Tomcat的server.xml和web.xml，解析技术DOM4j



应用场景：

* 解决程序间数据传输的问题（现已大量被json取代）
  比如qq之间的数据传送，用xml格式来传送数据，具有良好的可读性，可维护性。
* xmI可以做配置文件
  设置服务器程序启动时，去读取它应当监听的端口号、还有连接数据库的用户名和密码
* 充当小型的数据库（如今一些程序采用自己的数据格式存放，）
  能存储复杂的数据关系。我们程序中可能用到的数据，如果放在数据库中读取不合适（因为你要增加维护数据库工作），可以考虑直接用xml来做小型数据库，而且直接读取文件显然要比读取数据库快



## XML的组成

一个XML文件分为如下几部分内容

### 1、文档声明

### 2、元素

#### XML元素

指XML文件中出现的标签，一个标签分为开始标签和结束标签。

元素，节点，标签 指代相同概念

一个标签有如下几种书写形式

包含标签体：```<a>www.sohu.cn</a>```
不含标签体：```<a></a>```，简写为：```<a/>```

一个标签中也可以嵌套若干子标签。但所有标签必须合理的嵌套，绝对不允许交叉嵌套

#### 根元素

1. 每个XML文档必须有且只有一个根元素。
2. 根元素是一个完全包括文档中其他所有元素的元素。
3. 根元素的起始标记要放在所有其他元素的起始标记之前。
4. 根元素的结束标记要放在所有其他元素的结束标记之后

#### XML元素命名规则

1. 区分大小写，例如，\<P>和\<p>是两个不同的标记
2. 不能以数字开头
3. 不能包含空格
4. 名称中间不能包含冒号（:）
5. 如果标签单词需要间隔，建议使用下划线 比如 <book_title>hello</book_title>



### 3、属性

1. 属性值用双引号（"）或单引号（'）分隔（如果属性值中有'，用"分隔；有"，用'分隔）
2. 一个元素可以有多个属性，它的基本格式为：<元素名 属性名="属性值">
3. 特定的属性名称在同一个元素标记中只能出现一次
4. 属性值不能包括& 字符

### 4、注释

1. ```<!--这是一个注释-->```
2. 注释内容中不要出现--：
3. 不要把注释放在标记中间：错误写法```<Name<!--thename-->>ToM</Name>```
4. 注释不能嵌套
5. 可以在除标记以外的任何地方放注释

### 5、特殊字符

对于一些单个学符，若想显示其原始样式，也可以使用转义的形式予以处理

| 转义符       | 符号   |
| ------------ | ------ |
| ```&lt;```   | &lt;   |
| ```&gt;```   | &gt;   |
| ```&amp;```  | &amp;  |
| ```&quot;``` | &quot; |
| ```&apos;``` | &apos; |
| ```&#59;```  | &#59;  |
| ```&#58;```  | &#58;  |
|              |        |

空格的替代符号有以下几种：

| 名称      | 编号     | 描述                        |
| --------- | -------- | --------------------------- |
| \&nbsp;   | &\#160;  | 不断行的空白（1个字符宽度） |
| \&ensp;   | &\#8194; | 半个空白（1个字符宽度）     |
| \&emsp;   | &\#8195; | 一个空白（2个字符宽度）     |
| \&thinsp; | &\#8201; | 窄空白（小于1个字符宽度）   |

参考链接

[常用转义字符_牧码ya的博客-CSDN博客](https://blog.csdn.net/tian_ci/article/details/84307202)

### 6、CDATA节

有些内容不想让解析引擎执行，而是当作原始内容处理（当做普通文本），可以使用CDATA包括起来，CDATA节中的所有字符都会被当作简单文本，而不是XML标记。

* 语法：```<![CDATA[文本]]>```
* 可以输入任意字符（除]]>外）
* 不能嵌套

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!--
    解读
    1 xml声明 :表示该文件的类型，放在文档第一行。包括version（版本）和encoding（文档字符编码）
    2. students: root元素/根元素, 程序员自己来定
    3. <student></student> 表示students一个子元素, 可以有多个
    4. id就是属性 name, age, gender 是student元素的子元素

可以被映射成XML DOM 元素
-->
<students>
    <!--
        举例:
        id='01' 也是正确写法
        如果属性值有" 则使用' 包括属性 比如 id="xxx'yyy"
        如果属性值有' 则使用" 包括属性 比如 id='xxx"yyy'
        属性名在同一个元素标记只能出现一次 <stduent id="01" id="03"> 错误的，但是子元素可以重复
        属性值不能包括& 字符 比如： <stduent id="0&1"> 是错误的
    -->
    <student id="100">
        <name>jack</name>
        <age>&lt;10</age><!--使用转义符来表达特殊字符-->
        <gender>男</gender>
    </student>
    <student id="200">
        <name>mary</name>
        <age>18</age>
        <gender>女</gender>
        <code>
            <!--如果希望把某些字符串,当做普通文本，使用CDATA包裹起来 -->
            <![CDATA[
                <script data-compress=strip>
                function h(obj){
                obj.style.behavior='url(#default#homepage)';
                var a = obj.setHomePage('//www.baidu.com/');
                }
            </script>
            ]]>

        </code>
    </student>
</students>

```

格式正规的XML文档-小结
遵循如下规则的XML文档称为格式正规的XML文档：

* XML声明语句```<?xml version="1.0" encoding="utf-8"?>```
* 必须有且仅有一个根元素
* 标记区分大小写
* 属性值用引号
* 标记成对
* 空标记关闭
* 元素正确嵌套



参考资料

[10、Servlet - lidongdongdong~ - 博客园 (cnblogs.com)](https://www.cnblogs.com/lidong422339/p/17571262.html)：演示web.xml文件的其他应用
