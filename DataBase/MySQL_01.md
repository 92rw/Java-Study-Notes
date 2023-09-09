# MySQL数据库

## 当前项目使用的程序

基本信息：

版本：MySQL 5.7.19

解压目录：C:\DataBase\mysql-5.7.19-winx64

安装时需要使用VisualC++2013运行库如果缺乏驱动，下载  VC redist packages for x64  安装即可解决



## 数据库三层结构

1. 所谓安装 MySQL 数据库，就是在主机安装一个数据库管理系统 DBMS（DataBase Manage System），这个管理程序可以管理多个数据库。DBMs(databasemanagesystem）
   
2. 一个数据库中可以创建多个表，以保存数据（信息）

3. MySQL平台：数据库管理系统（负责监听端口的程序）> 数据库 （目录下的data文件夹）> 表、视图……（data文件夹下的对象）
4. （总结）MySQL数据库：普通表的本质仍然是文件

#### 数据库中表的结构

* 行（row）列（column）
* 表的一行称为一条记录
* 在 java 程序中，一行记录往往使用对象表示

### 连接 MySql

#### 使用命令行窗口连接 MySQL 数据库
mysql -h 主机ip地址 -P 端口 -u 用户名 -p密码

注意：

1.登录前，保证服务启动

2.-p和密码间没有空格，如果-p没有密码，回车后会要求输入密码

3.如果没有写-h 主机，默认本机，没有写-P 端口，默认3306

4.实际工作中，监听端口一般不是3306



#### 使用图形化操作软件

1.Navicat：Ctrl+/注释

2.SQLyog：

Shift+Ctrl+C注释，Shift+Ctrl+R取消注释

Ctrl+F12格式化

CTRL + 鼠标齿轮 可以改变显示大小，方便学习命令行语句



#### 使用java程序操作数据库



### MySQL常用数据类型（列类型/字段类型）

| 分类      | 数据类型 | 说明   |
| :----:        |    :---   |     :--- |
| 数值类型（位类型） | bit(M) [M指定位数，默认值1，范围1-64] | 按照位的方式显示<br/>查询时，仍然可以使用添加的数值<br/>如果数值只有0,1可以使用bit(1)节省空间 |
|  数值类型（整数）  | tinyint [1个字节]<br/>smallint [2个字节]<br/>mediumint [3个字节]<br/>**int** [4个字节]<br/>bigint[8个字节]<br/> | 默认有符号，除非设置```unsigned```。<br/>在能够满足需求的情况下，尽量选择占用小的类型 |
|  数值类型（小数）  | float [单精度4字节]<br/>**double** [双精度8字节]<br/>**decimal[M,D]** [默认10,0，最大65,30] | M指定总位数（精度）,D小数点后的位数（标度）。                |
|  文本(字符串)类型  | **char** 0-255字符，固定长度<br/>**varchar** 0~65532字节，可变长度<br/>**text** 0~65535(2^16)<br/>mediumtext 0~(2^24)<br/>longtext 0~(2^32) | 创建时，括号中指定的size是字符数<br/>varchar在utf8下最大编码21844个字符（(65535-3) /3）<br/>varchar存放实际数据大小后，还需要占用1-3字节记录存放内容长度<br/>char的价值：查询速度比varchar快，存放固定长度的md5、邮编、手机号、身份证号等<br/>text最大容量和varchar一致，创建时不需要指定长度 |
| 二进制类型 | blob 0~65535(2^16 -1)<br/>longblobt 0~(2^32 -1) |  |
| 时间日期 | year [年]<br/>date [日期 年月日]<br/>time [时间 时分秒]<br/>**datetime** [年月日时分秒 YYYY-MM-DD HH:mm:ss]<br/>**timestamp** [时间戳，可自动更新] | 详见《MYSQL参考手册》<br/>自动更新时间戳的代码```NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP``` |
