# 数据库和表的操作

### 基本信息说明

1. 如果需要多行代码一起运行，需要使用分号 ”;“
2. 在创建、删除数据库，表的时候，为了规避关键字，一般会在前后加入反引号 "`"
3. 不要用powershell，要用cmd，powershell导出的sql文件有格式问题。
4. 使用数据库 use 数据库名

### 创建数据库

```mysql
#格式：CREATE DATABASE [IF NOT EXISTS] db_name [create_specification [, create_specification] ...]

#案例：创建一个使用utf8字符集，校对规则utf8_bin的数据库db01
CREATE DATABASE db01 CHARACTER SET utf8 COLLATE utf8_bin
```



* CHARACTER SET：指定数据库采用的字符集，如果不指字符集，默认utf8
* COLLATE：指定数据库字符集的校对规则，常用的 utf8_bin[区分大小写]、utf8_general_ci[不区分大小写]（默认）[举例说明database.sql文件]
* 在数据库下创建表，如果不指定规则，默认使用所在数据库的



### 查看、删除数据库

```mysql
SHOW DATABASES #查看当前服务器中的所有数据库

SHOW CREATE DATABASE db_name #显示 db_name 数据库的定义信息

DROP DATABASE [IF EXISTS] db_name #删除 db_name 数据库
```



### 备份、恢复数据库

#### 备份数据库（注意：在DOS执行）

```mysql
#备份数据库
mysqldump -u 用户名 -p密码 -B 数据库1 数据库2 数据库n > 文件名.sql

#备份数据库中的表（没有-B）
mysqldump -u 用户名 -p密码 数据库名 表1 表2 表n > 文件名.sql
```



#### 恢复数据库（注意：进入MySQL命令行再执行）

* 方法1：

```mysql
source 文件名.sql
#不要用powershell，要用cmd，powershell导出的sql文件有格式问题无法在powershell中导入。
```

* 方法2：

​     将文件中的内容全部复制到图形化界面的查询编辑器中执行（如果文件较大，时间较长）

说明：只恢复一张表的话，要先用use命令切换一下数据库 然后再用source



## 表的操作

```mysql
CREATE TABLE table_name
（
	field1 datatype,
	field2 datatype,
	field3 datatype
)character set 字符集 collate 校对规则 engine 存储引擎
```

* field：指定列名datatype：指定列类型（字段类型）
* characterset：如不指定则为所在数据库字符集
* collate：如不指定则为所在数据库校对规则
* engine：引擎(这个涉及内容较多，后面单独讲解)





### MySQL常用数据类型（列类型/字段类型）

|        分类        | 数据类型                                                     | 说明                                                         |
| :----------------: | :----------------------------------------------------------- | :----------------------------------------------------------- |
| 数值类型（位类型） | bit(M) [M指定位数，默认值1，范围1-64]                        | 按照位的方式显示<br/>查询时，仍然可以使用添加的数值<br/>如果数值只有0,1可以使用bit(1)节省空间 |
|  数值类型（整数）  | tinyint [1个字节]<br/>smallint [2个字节]<br/>mediumint [3个字节]<br/>**int** [4个字节]<br/>bigint[8个字节]<br/> | 默认有符号，除非设置```unsigned```。<br/>在能够满足需求的情况下，尽量选择占用小的类型 |
|  数值类型（小数）  | float [单精度4字节]<br/>**double** [双精度8字节]<br/>**decimal[M,D]** [默认10,0，最大65,30] | M指定总位数（精度）,D小数点后的位数（标度）。                |
|  文本(字符串)类型  | **char** 0 ~ 255字符，固定长度<br/>**varchar** 0 ~ 65532字节，可变长度<br/>**text** 0 ~ 65535(2^16)<br/>mediumtext 0 ~ (2^24)<br/>longtext 0 ~ (2^32） | 创建时，括号中指定的size是字符数<br/>varchar在utf8下最大编码21844个字符（(65535-3) /3）<br/>varchar存放实际数据大小后，还需要占用1-3字节记录存放内容长度<br/>char的价值：查询速度比varchar快，存放固定长度的md5、邮编、手机号、身份证号等<br/>text最大容量和varchar一致，创建时不需要指定长度 |
|     二进制类型     | blob 0 ~ 65535(2^16 -1)<br/>longblobt 0 ~ (2^32 -1)          |                                                              |
|      时间日期      | year [年]<br/>date [日期 年月日]<br/>time [时间 时分秒]<br/>**datetime** [年月日时分秒 YYYY-MM-DD HH:mm:ss]<br/>**timestamp** [时间戳，可自动更新] | 详见《MYSQL参考手册》<br/>自动更新时间戳的代码```NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP``` |



#### 修改表

```mysql
#添加列
ALTER TABLE tablename
ADD (column datatype [DEFAULT expr] 
    NOT NULL DEFAULT '' AFTER column1 #在column1后添加默认值''的列
	[, column datatype]...);
	
#修改列
ALTER TABLE tablename
MODIFY (column datatype [DEFAULT expr]
		[, column datatype]...);

#删除列
ALTER TABLE tablename
DROP (column);

#修改列名
ALTER TABLE tablename
CHANGE 列名 新列名 列数据信息; #列数据类型必须写

#可以查看表所有的列
DESC 表名; 

#修改表名
RENAME table 表名 to 新表名;

#修改表字符集
alter table 表名 character set 字符集;
```

