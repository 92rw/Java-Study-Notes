

# 数据库CRUD语句

SQL语句分类

DDL：数据定义语句[create表，库...]

DML：数据操作语句[增加insert，修改update，删除delete]

DQL：数据查询语句[select]

DCL：数据控制语句[管理数据库：比如用户权限 grant revoke］



Create Read Update Delete

## 1.Insert语句(添加数据）

```mysql
INSERT INTO table_name [(column [, column...])]
			VALUES (value [, value...])
```



注意事项：

- 插入的数据应与字段的数据类型相同。比如把‘abc'添加到int类型会错误，但是'30'这种可以自动转型

- 数据的长度应在列的规定范围内，例如：不能将一个长度为80的字符串加入到长度为40的列中。

- 在values中列出的数据位置必须与被加入的列的排列位置相对应。

- 字符和日期型数据应包含在单引号中。

- 列可以插入空值[前提是该字段允许为空，也就是创建时没有要求NOT NULL]

- insert into table_name (列名..) values (), (), ()的形式添加多条记录

- 如果是给表中的所有字段添加数据，可以不写前面的字段名称

- 默认值的使用：如果某个列没指定NOT NULL，那么添加数据时若没有向该字段赋值，会默认null；如果有DEFAULT则赋值DEFAULT

  

## 2.Update语句(更新数据)

```mysql
UPDATE table_name
		SET col_name = expr1 [, col_name2 = expr2...]
		[WHERE where_definition] --不写这条的话默认修改所有
```

注意事项：

- update和alter的区别：update是更新表里面的数据(修改记录)，alter是修改表的结构(修改字段)
- UPDATE语法可以用新值更新原有表行中的各列
- SET子句指示要修改哪些列和要给予哪些值。
- WHERE子句指定应更新哪些行。如没有WHERE子句，则更新所有的行（慎用！！！）
- 如果需要修改多个字段，可以通过set 字段1=值1, 字段2=值2...

## 3.Delete语句(删除数据）

```mysql
DELETE FROM table_name
	[WHERE where_definition] --不写这条的话默认删除所有记录
```

注意事项：

- 如果不使用where子句，将删除表中所有数据。
- Delete语句不能删除某一列的值，只能设为null或者''->修改表数据，删除列->修改表结构 ```ALTER TABLE 表名 DROP 列名```
- 使用delete语句仅删除记录，不删除表本身。如要删除表，使用 ```drop table 表名；```



## 4.Select语句(查找数据）

```mysql
SELECT [DISTINCT] * {column1, column2, column3...}
		FROM table_name;
-- 说明：
-- SELECT 指定查询哪些列的数据
-- DISTINCT 可选，指显示结果时，是否显示重复数据
-- * 号代表查询所有列，column 指定列名查询
-- FROM 指定查询哪张表。

```

执行顺序：from > where > group by > select > order by

```mysql
#查询时可指定列的别名
SELECT 列名 as 别名 from table_name;
-- 命名时尽量避免使用中文字符

#使用表达式对查询的列进行运算
SELECT column1 expression, column2 expression...
		FROM table_name;
		ORDER BY column asc|desc, ...
-- 说明
-- 1.Orderby指定排序的列，排序的列既可以是表中的列名，也可以是语句后指定的列名。
-- 2.Asc升序[默认]、Desc降序
-- 3.ORDER BY子句应位于SELECT语句的结尾
```



在where子句中经常使用的运算符

```
比较运算符
> < >= <= = <> !=   大于、小于、≥、≤、等于、不等于
BETWEEN...AND..     在某个闭区间内
IN(set)				在set集合中的值
LIKE/NOT LIKE		模糊查询（'江%'表示以江开头，'%江%'表示包含江）
IS NULL				判断是否为空

逻辑运算符
and		多个条件同时成立
or		多个条件任一成立
not		不成立，例：where not(salary>100)
```



对结果分组并过滤

```mysql
SELECT column1, column2, column3... FROM table_name
		GROUP BY column HAVING...
-- 简记：因为执行顺序的原因，导致分组前用where，分组后用Having
```



备注：可以使用FROM DUAL语句，利用系统表作为测试表



查看MySQL数据库保存的用户数据

```mysql
SELECT * FROM mysql.user
-- 说明：mysql.user 表示 数据库.表，这种格式不需要切换数据库就能完成查询
```



补充阅读：

[为什么不建议你使用SELECT* | 蝉沐风 (chanmufeng.com)](https://www.chanmufeng.com/posts/storage/MySQL/为什么不建议你使用SELECT*.html)
