# 多表查询

多表香询是指基于两个和两个以上的表查询.在实际应用中，查询单个表可能不能满足需求

使用语句```SELECT * FROM emp, dept```查询到的结果：

* 从第一张表中，取出一行，和第二张表的每一行组合[含有两张表的所有列]

* 总共返回的记录数：第一张表行数*第二张表行数
* 这样多表查询默认处理返回的结果，称为笛卡尔集
* 多表查询的本质就是通过where子句对默认的笛卡尔积数据进行筛选

对笛卡尔集进行筛选：

- 遇到有重复列名的情况下，需要指定 表名.列名
- 多表查询过滤条件不能少于 表的个数-1，否则会出现笛卡尔集
- 

```mysql
#显示员工所在的工资等级
SELECT ename, sal, grade FROM emp, salgrade
	WHERE sal BETWEEN losal AND hisal
	order by sal desc
```



## 自连接

在同一张表的连接查询

* 将同一张表当做两张表使用
* 需要向表设置别名alias
* 如果列名不明确，也可以指定
* AS语句可以省略

```mysql
#显示公司员工名字和上级名字
SELECT worker.ename AS '职员名', boss.ename AS '上级名'
	FROM emp AS worker, emp AS boss
	WHERE worker.mgr = boss.empno;
	
#列出受雇日期晚于其直接上级的所有员工
SELECT worker.ename "员工名", worker.hiredate "员工入职时间", leader.ename "上级名", leader.hiredate "上级入职时间"
	FROM emp worker, emp leader
	WHERE worker.hiredate > leader.hiredate and worker.mgr = leader.empno
```



## 子查询subquery

嵌入在其他SQL语句中的SELECT语句，也叫嵌套查询

单行子查询：只返回一行数据

多行子查询：返回多行数据，使用关键字 IN

```mysql
-- 利用嵌套查询，将单行子查询嵌入到多行子查询中，完成需求
#要求：查询和员工SMITH相同部门的所有员工
#单行子查询：返回一行数据，SMITH员工所在部门的编号
SELECT deptno FROM emp
	WHERE ename = 'SMITH'

#多行子查询，条件为单行子查询的结果
SELECT * FROM emp 
	WHERE deptno = (
		SELECT deptno
		FROM emp
		WHERE ename = 'SMITH')
```





```mysql
-- 查询和部门10岗位相同的雇员名字、岗位、工资、部门号，但是不含10部门自己的
-- 1.查询10号部门有哪些工作
-- 注意：因为岗位可能出现重复，需要用DISTINCT过滤
SELECT DISTINCT job FROM emp WHERE deptno = 10

-- 2.把上面查询结果当做子查询使用
SELECT ename, job, sal, deptno FROM emp
	WHERE job IN (SELECT DISTINCT job FROM emp WHERE deptno = 10)
	AND deptno <> 10 #不等号的使用
```



显示工资比部门30所有员工工资高的员工姓名、工资和部门号

```mysql
#使用ALL操作符
SELECT ename, sal, deptno FROM emp
	WHERE sal > ALL(SELECT sal FROM emp WHERE deptno = 30)

#使用MAX()方法
SELECT ename, sal, deptno FROM emp
	WHERE sal > (SELECT MAX(sal) FROM emp WHERE deptno = 30)
```

显示工资比部门30某个员工工资高的员工姓名、工资和部门号

```mysql
#使用ANY操作符
SELECT ename, sal, deptno FROM emp
	WHERE sal > ANY(SELECT sal FROM emp WHERE deptno = 30)

#使用MIN()方法
SELECT ename, sal, deptno FROM emp
	WHERE sal > (SELECT MIN(sal) FROM emp WHERE deptno = 30)
```



### 将子查询作为临时表使用

把子查询当做一张临时表，可以解决很多复杂的查询

案例：查询ecshop数据库中，价格最高的商品

解题思路：化繁为简
题意是要 查询ecshop库中goods表中各类别价格最高的商品，可以拆分成 
先找出各类别对应最高价格的表1，
用表1和原始表 进行匹配找出对应的商品名字、价格、类别

```mysql
#创建子查询，得到各个类别的最高价
SELECT cat_id, max(shop_price) AS max_price FROM ecs_goods GROUP BY cat_id
	
#将子查询和原表一起查询
SELECT goods_id, ecs_goods.cat_id, goods_name, shop_price
	FROM (SELECT cat_id, max(shop_price) AS max_price FROM ecs_goods GROUP BY cat_id) temp, ecs_goods
	WHERE ecs_goods.cat_id = temp.cat_id AND shop_price = max_price
```



### 多列子查询

返回的结果是多个列数据，注意顺序需要前后一致

```(字段1，字段2...) = (SELECT 字段1，字段2... FROM...)```

查询与ALLEN的部门、岗位相同的所有雇员（不含SMITH本人）

```mysql
#得到ALLEN的部门、岗位
SELECT deptno, job FROM emp WHERE ename = 'ALLEN'
#把上面的语法当做子查询，使用多列子查询的语法进行匹配
SELECT * FROM emp
WHERE (deptno, job) = (SELECT deptno, job FROM emp WHERE ename = 'ALLEN')
AND ename <> 'ALLEN'
```





```mysql
#查找每个部门中，工资高于该部门平均工资的人的资料
SELECT * FROM emp, (SELECT AVG(sal) AS avg_sal, deptno FROM emp GROUP BY deptno) temp
WHERE sal>avg_sal AND emp.deptno = temp.deptno

#查找每个部门中，工资最高的人的资料
SELECT * FROM emp, (SELECT MAX(sal) AS max_sal, deptno FROM emp GROUP BY deptno) temp
WHERE sal=max_sal AND emp.deptno = temp.deptno
```

查询每个部门的信息（包括：名称、编号、地址）和人员数量

```mysql
#构建临时表
SELECT COUNT(*), deptno FROM emp GROUP BY deptno
#开展多表查询
SELECT dept.*, per_num
FROM dept, (SELECT COUNT(*) as per_num, deptno FROM emp GROUP BY deptno) tmp
WHERE dept.deptno = tmp.deptno

--说明：表名.* 得到所有表中的列
--当多个表的列不重复时，才可以直接写列名
```

