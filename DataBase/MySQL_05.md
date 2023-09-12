# 查询加强

使用WHERE子句

* 在MySQL中，日期类型可以直接比较，需要注意格式（日期和天数为两位数字）

```SELECT * FROM emp WHERE hiredate > '1991-12-25'```



使用LIKE操作符

%：表示0到任意多个字符

_：表示单个字符.

```mysql
#显示第二个字符为大写P的所有单词
SELECT words FROM wordlist
	WHERE words LIKE '_P%'
```



使用ORDER BY语句

```mysql
#AND是在WHERE过滤中用，还有分组是把相同的合在一起了，信息就不完整了
#列的结果降序，和查询表结构的语句都是DESC
SELECT * FROM emp
	ORDER BY depno ASC, sal DESC;
```



分页查询

```SELECT ... LIMIT start, rows```

表示从start+1行开始取，取出row行，start从0开始计算

```mysql
#显示某一页的内容
SELECT * FROM 表名
		ORDER BY 列名 ASC/DESC
		LIMIT 每页显示记录数*(页数-1), 每页显示记录数
```



分组增强

```mysql
#显示雇员总数以及获得补助的雇员数
#获得补助就是comm列非空，可以通过COUNT函数本身的性质调用
SELECT COUNT(*), COUNT(comm) FROM emp;

#统计没有获得补助的雇员数
#调用判断语句
SELECT COUNT(*), COUNT(IF(comm IS NULL, 1，NULL)) FROM emp;

#统计管理者的总人数
#需要对mgr列进行去重
SELECT COUNT(DISTINCT mgr) FROM emp;
```

总结

```mysql
SELECT column1, column2, column3... FROM table
		GROUP BY column
		HAVING condition
		ORDER BY column
		LIMIT start, rows;
```

案例

```mysql
#请统计各个部门的平均工资，并且是大于1000的，并且按照平均工资从高到低排序，并取出前两行记录
SELECT deptno, AVG(sal) AS avg_sal
	FROM emp
	GROUP BY depno
	HAVING avg_sal > 1000
	ORDER BY avg_sal DESC
	LIMIT 0,2;
--说明：用FORMAT()函数的要注意它的返回类型是string，不可以直接跟数字进行比较

#列出最低薪金大于1500的各项工作
#分组后才能使用having
SELECT min(sal) min_sal, job FROM emp GROUP BY job HAVING min_sal > 1500

#列出薪水高于平均工资的所有员工
SELECT * FROM emp WHERE sal > (SELECT AVG(sal) FROM emp)

#得到所有员工年工资，并从低到高排序
SELECT ename, (sal*12 + IFNULL(comm,0)) "年工资" FROM emp ORDER by "年工资"
#列出所有部门的详细信息和部门人数
SELECT dept.*, tmp.c "部门人数" 
	FROM dept, (SELECT COUNT(*) c, deptno FROM emp GROUP BY deptno) tmp
	WHERE dept.deptno = tmp.deptno
```

