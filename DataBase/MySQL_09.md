# MySQL 索引

索引：不用加内存，不用改程序，不用调SQL，查询速度得到数量级的提升

```mysql
CREATE INDEX 索引名 ON 表名 (列名)
```

说明：

- 索引本身会占用硬盘空间
- 创建索引后，只对创建了索引的列有效

没有索引时，会进行全表扫描；使用索引会形成数据结构，比如B+树

索引的代价：占用磁盘空间；执行DML（update,delete,insert)语句会重新维护索引，影响速度

DML（Data Manipulation Language）语句：数据操纵语句。

## 索引的类型

主键索引：主键默认加索引，不用自己增加语句

唯一索引（UNIQUE）

普通索引（INDEX）

全文索引（FULLTEXT）【适用于MyISAM】
一般在开发中，不使用MySQL自带的全文索引，而是使用：全文搜素框架Solr和ElasticSearch（ES）



### 索引使用

创建索引：创建表的时候写了UNIQUE则默认为唯一索引，写了PRIMARY KEY则默认主键索引

```mysql
#添加为唯一索引
create UNIQUE index 索引名 on 表 (col_name [(length)] [ASC|DESC],...)

#添加为普通索引的方式
create index 索引名 on 表 (col_name [(length)] [ASC|DESC],...)
alter table 表名 ADD INDEX [索引名] (col_name,...)

#添加为主键索引
alter table 表名 add primary key (col_name)
```



说明：

- 如果某列的值不会重复，则优先考虑unique索引，否则使用普通索引
- 允许在同一个字段建多个索引，但是索引名不能重复，不然就提示已经存在、



### 删除索引

```mysql
drop index 索引名 on 表名
alter table 表名 drop index 索引名

#删除主键索引
alter table 表名 drop primary key
```





### 修改索引

先删除，再添加新的索引



### 查询索引


```mysql
SHOW INDEX FROM 表名
SHOW INDEXES FROM 表名
SHOW KEYS FROM 表名
DESC 表名 #显示的信息不够详细
```



使用索引的条件

1.较频繁的作为查询条件字段应该创建索引
select * from emp where empno = 1
2.唯一性太差的字段不适合单独创建索引，即使频繁作为查询条件
select * from emp where sex = '男'
3.更新非常频繁的字段不适合创建索引
select * from emp where logincount = 1
4.不会出现在WHERE子句中字段不该创建索引







