# 数据交换和异步请求

## Ajax基本介绍

1. AJAX即"Asynchronous Javascript And XML"（异步JavaScript和XML)
2. Ajax是一种浏览器异步发起请求（指定发哪些数据），局部更新页面的技术

Ajax特点：异步请求、发送指定数据、局部刷新

### 经典应用场景

1. 搜索引擎根据用户输入关键字，自动提示检索关键字
2. 动态加载数据，按需取得数据（树形菜单、联动菜单
3. 改善用户体验。（输入内容前提示、带进度条文件上传
4. 电子商务应用。【购物车、邮件订阅】
5. 访问第三方服务。【访问搜索服务、rss阅读器】
6. 页面局部刷新



### Ajax出现前后，前后端交互的变化

#### 传统web数据通信方式

过程

1. 浏览器发出http请求（携带数据uname=xx&pwd=xx）
2. 服务端接收数据，并处理
3. 通过http响应，把数据返回给浏览器

缺点

1. 表单提交是把该表单的所有数据，都提交给服务端，数据大而且没有意义
2. 在服务端没有Http响应前，浏览器前端页面处于等待/挂起状态
3. 不能进行局部刷新，而是刷新整个页面

#### 使用Ajax数据通信方式

前端页面的变化

1. 创建XMLHttpRequest对象（Ajax引擎对象）
2. 通过XMLHttpRequest对象，发送指定数据（异步发送）
3. XMLHttpRequest指定当数据返回时，由哪个函数来处理（事件绑定）
4. 得到返回数据（格式可以多样）后，进行DOM操作，完成局部刷新（页面/数据）

优点

1. 可以通过XMLHttpRequest对象，发送指定数据，数据量小，速度快
2. XMLHttpReguest是异步发送，在服务端没有Http响应前，浏览器不需要等待，用户可以进行其它操作
3. 可以局部刷新，提高用户体验



## JavaScript原生的Ajax请求

### Ajax XHR

应用实例：验证用户名是否存在

* 在输入框输入用户名
* 点击验证用户名，使用ajax方式服务端验证该用户名是否已经占用了，如果该用户已经占用，以json格式返回该用户信息
* 假定用户名为king，就不可用，其它用户名可以
* 对页面进行局部刷新，显示返回信息

实现过程：

1. 新建用户类（entity/User.java）JavaBean/pojo/entity/domain（同一概念，不同称呼），新建Servlet（servlet/CheckUserServlet1.java）用于接收前端发送的数据
2. 前端页面（login.html）新建XMLHttpReguest对象，在open方法中设置目标链接，用send方法将数据传给服务器端
3. 后端处理数据，向前端返回信息
4. 前端根据后端返回的信息，根据具体业务逻辑对数据进行处理



应用实例2：在上面的基础上，后端调用数据库来判断用户是否存在

* 创建数据库 ajax_db，创建表 user

实现过程，采用分层模式：

1. 新建util包，JdbcByDruid工具类，可以从数据库连接池返回连接（引入commons-dbutils-1.3.jar，druid-1.1.10.jar，mysql-connector-java-5.137-bin.jar）
2. 新建dao包，BasicDAO用于数据库交互，UserDAO继承BasicDAO
3. 新建service包，编写UserService.java提供业务方法。
4. 在MySQL数据库新建User表，字段包括：①id②username，③password④email

```mysql
CREATE DATABASE ajax_db;
USE ajax_db;
-- 创建表，名称和顺序需要和JavaBean一致
CREATE TABLE `user` (#注意USER是关键字
id INT PRIMARY KEY,
username VARCHAR(32) NOT NULL DEFAULT '',
`password` char(32) NOT NULL DEFAULT '',
email VARCHAR(32) NOT NULL DEFAULT '') CHARSET utf8 ENGINE INNODB;
```

向数据库中添加数据

```mysql
-- 添加数据
INSERT INTO `user` VALUES (65535, "king", MD5("0xFFFF"), "postmaster@outlook.com");
INSERT INTO `user` VALUES (65536, "queen", MD5("0x10000"), "postmaster@outlook.com");

SELECT * FROM `user`
```

代码实现：dblogin.html

注意事项：

1. MySQL表中的字段，需要和JavaBean的字段保持一致，否则数据在封装时会报错
2. JavaSE中的路径在src目录下，JavaWeb路径在out目录下，获取src目录的文件需要利用类加载器
3. JDBC采用反射的方式新建JavaBean对象，因此必须设置无参构造器

### 原生Ajax请求存在的问题

1.编写原生的Ajax要写很多的代码，还要考虑浏览器兼容问题，使用不方便。

2.在实际工作中，一般使用JavaScript的库（比如Jquery）发送Ajax请求，从而解决这个问题



## jQuery操作Ajax

### $.ajax方法

该方法可传入多种参数，完整的参数详见手册。常用参数

* url：请求的地址
* type：请求的方式get或post，默认get
* data：发送到服务器的数据。将自动转换为请求字符串格式
* success：成功时的回调函数
* error：失败后的回调函数
* dataType：指定返回的数据类型，常用json或text



### \$.get方法和 $.post方法

常用参数：

* url：请求的URL地址
* data：请求发送到服务器的数据
* success：成功时回调函数
* type：返回内容格式，xml，html，script，json，text

说明：底层调用 $.ajax() 方法实现异步请求



### $.getJSON方法

常用参数：

* url：请求发送到的url
* data：请求发送到服务器的数据
* success：成功时回调函数

说明：底层调用 $.ajax() 方法实现异步请求



### 应用实例

应用实例：验证用户名是否存在

* 在输入框输入用户名
* 点击验证用户名，使用ajax方式服务端验证该用户名是否已经占用了，如果该用户已经占用，以json格式返回该用户信息
* 假定用户名为king，就不可用，其它用户名可以
* 对页面进行局部刷新，显示返回信息

代码实现：jqlogin.html，CheckUserServlet3.java