# 约束

用于确保数据库数据满足特定的商业规则，

在mysql中，约束包括:not null，unique，primary key，foreign key和check五种



## primary key（主键）
```字段名 字段类型 primary key```

用于唯一的标示表行的数据，当定义主键约束后，该列不能重复

细节说明：

* primary key不能重复而且不能为null
* 一张表最多只能有一个主键，但可以是复合主键
* 主键的指定方式有两种

  * ```字段名 primakry key```
* 在表定义最后写```primary key （列名）```，可以指定复合主键

* 使用```desc 表名```，可以看到primary key的情况
*  #列出部门名称和这些部门的员工信息，同时列出那些没有员工的部门#这里因为需要显示所有部门，因此考虑使用外连接SELECT dname, emp.*    FROM dept LEFT JOIN emp ON dept.deptno = emp.deptno    ORDER BY dnamemysql



## NOT NULL（非空）

如果在列上定义了NOT NULL，那么插入数据时必须为列提供数据

```字段名 字段类型 NOT NULL```



## UNIQUE（唯一）

当定义了唯一约束后，该列值是不能重复的

```字段名 字段类型 unique```

注意：

- 如果没有指定not null，则unique字段可以有多个null
- 一张表可以有多个unique字段
- 主键自带NOT NULL和UNIQUE
- unique和主键最大的区别应该是。复合主键两个相同才报错。unique约束两个字段。一个相同就直接报错。不需要两个都相同。



## FOREIGN KEY（外键）

用于定义主表和从表之间的关系：外键约束要定义在从表上，主表则必须具有主键约束或是unigue约束。

想要删除主表的约束 字段 必须把从表使用到的字段 记录删除

```FOREIGN KEY (本表字段名) REFERENCES 主表名(主键名或unique字段名)```

说明：

- 有了外键，程序员很难控制数据，大部分公司都不推荐使用外键
- 外键指向的表的字段，要求是primarykey或者是unique
- 表的类型是innodb，这样的表才支持外键
- 外键字段的类型要和主键字段的类型一致（长度可以不同）
- 外键字段的值，必须在主键字段中出现过，或者为nu[前提是外键字段允许为null]
- 一旦建立主外键的关系，数据不能随意删除了（foreign key constraint fail）



## CHECK

用于强制行数据必须满足的条件，假定在sal列上定义了check约束，并要求sal列值在1000~2000之间，如果不在1000~2000之间就会提示出错。oracle和sql server均支持check，但是mysql5.7尚不支持check，只做语法校验，但不会生效（MySQL8.0已支持）。

```mysql
CREATE TABLE test_table (id INT PRIMARY KEY,
				sal DOUBLE CHECK(sal > 1000 AND sal < 2000));

INSERT INTO test_table VALUES(1,1)#添加成功，不会报错
```



在mysql中实现check的功能，一般是在程序中控制，或者通过触发器完成。



案例演示：商店售货系统表设计

现有一个商店的数据库shop_db，记录客户及其购物情况，由下面三个表组成：

* 商品goods（商品号goods_id，商品名goods_name，单价unitprice，商品类别category，供应商provider);
* 客户customer（客户号customer_id，姓名name，住址address，电邮email，性别sex，身份证card_Id);
* 购买purchase（购买订单号order_id，客户号customer_id，商品号goods_id，购买数量nums);

建表，在定义中要求声明[进行合理设计]：

1. 每个表的主外键：
2. 客户的姓名不能为空值：
3. 电邮不能够重复;
4. 客户的性别[男|女]
5. 单价unitprice 在 1.0 -9999.99 之间 check

```mysql
CREATE DATABASE shop_db;
CREATE TABLE goods (goods_id INT PRIMARY KEY, goods_name VARCHAR(64) NOT NULL DEFAULT '',
	unitprice DECIMAL(10,2)NOT NULL DEFAULT 0 CHECK (unitprice BETWEEN 1.0 AND 9999.99),
	category INT NOT NULL DEFAULT 0, provider VARCHAR(64) NOT NULL DEFAULT '');
CREATE TABLE customer (customer_id CHAR(8) PRIMARY KEY,
	`name` VARCHAR(64) NOT NULL DEFAULT '', address VARCHAR(64) NOT NULL DEFAULT '',
	email VARCHAR(64) UNIQUE NOT NULL, sex ENUM('男','女') NOT NULL, #使用枚举
	card_Id CHAR(18));
CREATE TABLE purchase (order_id INT UNSIGNED PRIMARY KEY, #设置无符号因此不能为负
	customer_id CHAR(8) NOT NULL DEFAULT '', goods_id INT NOT NULL DEFAULT 0,
	nums INT NOT NULL DEFAULT 0,
	FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	FOREIGN KEY (goods_id) REFERENCES goods(goods_id));
#主表要设置主键才能添加外键
#要设置约束的名字的话 ，用 constraint <索引名> 。。。。
```

## 自增长

在某张表中，存在一个id列（整数）我们希望在添加记录的时候，该列从1开始，自动增长

```字段名 整形 primary key auto_increment```

例如字段1设置为自增长，那么添加数据时，输入方式有

```mysql
INSERT INTO XXX(字段1, 字段2...) VALUES(NULL,'值1'...);
INSERT INTO XXX(字段2...) VALUES('值1',...);
INSERT INTO XXX VALUES(NULL,'值1'...)
```

使用细节

- 一般来说自增长是和primarykey配合使用的
- 自增长也可以单独使用[但是需要配合一个unique]
- 自增长修饰的字段为整数型的（虽然小数也可以但是非常非常少这样使用）
- 自增长默认从1开始，也可以通过如下命令修改```alter table 表名 auto increment = 新的开始值;```
- 如果添加数据时，给自增长字段（列）指定有数值，则以指定的值为准；
