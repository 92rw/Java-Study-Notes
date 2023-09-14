# 表复制



为了对某个sql语句进行效率测试，我们需要海量数据时，可以使用此法为表创建海量数据

```mysql
#先创建一张表my_tab02，把原来的结构（列信息）复制到新表
CREATE test_tab LIKE tab01;

#自我复制
INSERT INTO test_tab SELECT * FROM test_tab

#查询自增结果
SELECT COUNT(*) FROM test_tab
```



## 删掉一张表的重复记录

```mysql
#创建一张临时表my_tmp，结构和原表一样
CREATE TABLE my_tmp LIKE origin_table;
#通过DISTINCT关键字去重，将结果复制到my_tmp
INSERT INTO my_tmp SELECT DISTINCT * FROM origin_table;
#清除掉原来表的记录/此处也可以DROP原表给新表改名
DELETE FROM origin_table;
#把my_tmp表的记录复制到原来的表
INSERT INTO origin_table SELECT * FROM my_tmp;
#drop掉临时表
DROP TABLE my_tmp;
```



### 合并查询

UNION ALL 将两个查询结果合并，不会去重

UNION 将两个查询结果合并且去重

要求：查询的列必须相同





### 表外连接

1.左外连接（如果左侧的表完全显示我们就说是左外连接）

```SELECT ...FROM 表1 LEFT JOIN 表2 ON 条件```

2.右外连接（如果右侧的表完全显示我们就说是右外连接

说明：内连接就是笛卡尔，只是加了筛选，排除了错误情况。实际开发中，绝大多数情况下使用内连接

就算没有和主表匹配的记录，也会显示出来



```mysql
#列出部门名称和这些部门的员工信息，同时列出那些没有员工的部门
#这里因为需要显示所有部门，因此考虑使用外连接
SELECT dname, emp.*
	FROM dept LEFT JOIN emp ON dept.deptno = emp.deptno
	ORDER BY dname
```



