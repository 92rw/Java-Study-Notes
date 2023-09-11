# 函数

## 合计/统计函数

```mysql
#count返回查询结果共有多少行
SELECT COUNT(*)|COUNT(列名) FROM 表名
	WHERE where_definition
-- 说明：返回所有满足条件的行数
-- count(*) 不排除null
-- count(列) 排除null

#sum返回满足where条件的行的和，一般使用在数值列
SELECT SUM(列名) {, SUM(列名)...} FROM table_name
	[WHERE where_definition]
-- 说明：SUM只对数值有用，其他会报错
-- 对多列求和，需要用逗号","分隔

#统计某列数据的平均值
SELECT SUM(列名)/COUNT(*) FROM 表名;
SELECT AVG(列名) FROM 表名;
--注意：AVG函数会排除null

最大值、最小值使用MAX(列名)，MIN(列名)
```



## 字符串函数

| 函数表达式                                         | 说明                                                         |
| -------------------------------------------------- | ------------------------------------------------------------ |
| **CHARSET(str)**                                   | 返回字符串所在charset                                        |
| **CONCAT(string2 [,…])**                           | 连接字符串 ->将多个列拼接成一列                              |
| INSTR(string, substring)                           | 返回substring在string中出现的位置，没有返回0                 |
| **UCASE(string2)**                                 | 转换成大写                                                   |
| **LCASE(string2)**                                 | 转换成小写                                                   |
| LEFT(string2，length))<br/>RIGHT(string2，length)) | 从string2中的左边起取length个字符<br/>从string2中的右边起取length个字符 |
| **LENGTH(string)**                                 | string长度[字节长度]                                         |
| **REPLACE(str, search_str, replace_str)**          | 在str中用replace_str替换search_str                           |
| STRCMP(string1, string2)                           | 逐字符比较两字串大小，                                       |
| **SUBSTRING(str, position [,length])**             | 从str的position开始【从1开始计算】，取length个字符【如果length长度未指定，则截取到字符串最后】 |
| LTRIM(string2)<br/>RTRIM (string2)<br/>trim        | 去除左侧空格<br/>去除右侧空格<br/>去除两端空格               |

```mysql
#以首字母小写的方式显示所有员工的姓名
SELECT CONCAT(LCASE(SUBSTRING(ename,1,1)), SUBSTRING(ename,2)) FROM emp;

#显示姓名长度5个字符的员工
SELECT * FROM emp WHERE LENGTH(ename)=5 / WHERE ename LIKE '_____'

#显示员工姓名，用'a'替换'A'
SELECT REPLACE(ename, 'A', 'a') FROM emp
```



## 数学函数

| 函数表达式                        | 说明                                                         |
| --------------------------------- | ------------------------------------------------------------ |
| ABS(num)                          | 绝对值                                                       |
| BIN(decimal_number)               | 十进制转二进制                                               |
| CEILING(number2)                  | 向上取整，得到比num2大的最小整数                             |
| CONV(number2, from_base, to_base) | 进制转换：将from_base进制的number2，转成to_base进制输出      |
| FLOOR(number2)                    | 向下取整，得到比num2小的最大整数                             |
| FORMAT(number, decimal_places)    | 保留小数位数（四舍五入）                                     |
| HEX(DecimalNumber)                | 转十六进制                                                   |
| LEAST(number, number2 [,...])     | 求最小值                                                     |
| MOD(numerator,denominator)        | 求余数                                                       |
| RAND([seed])                      | 使用rand()，则每次返回 0 ≤ v ≤ 1.0的不同值；将[seed]输入一个数字，则每次返回相同的随机数 |

距离：ROUND()函数四舍五入，FLOOR()函数忽略余数

## 日期时间函数

| 函数表达式                                                   | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| CURRENT_DATE()                                               | 当前日期                                                     |
| CURRENT_TIME()                                               | 当前时间                                                     |
| CURRENT_TIMESTAMP()                                          | 当前时间截                                                   |
| NOW()                                                        | 当前时间截                                                   |
| DATE(datetime)                                               | 返回datetime的日期部分                                       |
| DATE_ADD(date2,INTERVAL  d_value d_type)                     | 在date2中加上d_type的d_value时间                             |
| DATE_SUB(date2,INTERVAL d_value  d_type)                     | 在date2上减去d_type的d_value时间                             |
| DATEDIFF(date1,date2)                                        | date1 - date2的天数差，可以为负                              |
| TIMEDIFF(date1,date2)                                        | 两个时间差(多少小时多少分钟多少秒)                           |
| YEAR(datetime)<br/>Month(datetime)<br/>DAY(datetime)<br/>DATE(datetime) | 年<br/>月<br/>日<br/>年月日                                  |
| UNIX_TIMESTAMP()<br/>FROM_UNIXTIME()                         | 1970-1-1到现在的秒数<br/>把UNIX_TIMESTAMP()时间戳，转成指定格式的时间 |

说明：

- INTERVAL 后面可以是 YEAR MINUTE SECOND HOUR DAY等
- 在实际开发中，我们也经常使用int来保存一个unix时间戳，通过 FROM_UNIXTIME() 转换



查询在10分钟内发布的新闻

```mysql
SELECT * FROM news
	WHERE DATE ADD(send_time, INTERVAL 10 MINUTE) >= NOW() #方法一
	WHERE send_time >= DATE_SUB(NOW(), INTERVAL 10 MINUTE) #方法二
```

查询时间差

```mysql
SELECT DATEDIFF('2022-11-30', '1926-08-17') FROM DUAL; #两个日期差
SELECT DATEDIFF('10:11:11', '06:10:10') FROM DUAL; #两个时间差
SELECT FROM_UNIXTIME(19260817, '%Y-%m-%d %H:%i:%s') FROM DUAL; #格式是规定好的 
```



两个日期差```SELECT DATEDIFF('2022-11-30', '1926-08-17') FROM DUAL```

查询两个时间差```SELECT DATEDIFF('10:11:11', '06:10:10') FROM DUAL```



```mysql
#如果活到100岁，还剩多少天
#先求出100岁时的具体日期，然后计算日期差
SELECT DATEDIFF(DATE_ADD('1950-12-25', INTERVAL 100 YEAR),NOW()) FROM DUAL

#以年月日的方式显示员工的服务年限
#方法1：利用from_days()函数
SELECT ename,FROM_DAYS(DATEDIFF(now(),HIREDATE)) FROM emp

#方法2：将相差日期加在公元元年
SELECT ename, DATE_ADD('0001-01-01',INTERVAL DATEDIFF(NOW(),hiredate) DAY) FROM emp


```



## 加密和系统函数

| 函数表达式    | 说明                                                         |
| ------------- | ------------------------------------------------------------ |
| USER()        | 查询当前登录到数据库的用户 --用户名@IP地址                   |
| DATABASE()    | 当前使用的数据库名称                                         |
| MD5(str)      | 为字符串算出一个32位MD5字符串 ->在数据库中存放的是加密后的密码，防止注入 |
| PASSWORD(str) | 从原文密码str计算并返回密码字符串，通常用于对mysql数据库的用户密码加密 |

补充：

- 以前的网站，都是可以找回原始密码的（存放明文），现在的找回密码要重新设置一个新的（存放MD5）
- MySQL数据库的用户密码使用PASSWORD函数加密



```mysql
#演示用户表，存放密码时使用MD5
CREATE TABLE users
(id INT, `name` VARCHAR(32) NOT NULL DEFAULT '', pwd CHAR(32) NOT NULL DEFAULT '');

#添加用户时的语句
INSERT INTO users
	VALUES(1, '张三', MD5('password'));

```



## 流程控制函数

| 函数表达式                                                   | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| IF(expr1，expr2，expr3)                                      | 如果expr1为True，则返回expr2，否则返回expr3 -> 类似java的三元运算符 |
| IFNULL(expr1，expr2)                                         | 如expr1不为空，则返回expr1，否则返回expr2                    |
| SELECT CASE WHEN expr1 THEN expr2 WHEN expr3 THEN expr4 ELSE  expr5 END;【类似多重分支，相当于子查询】 | 如果expr1为TRUE，则返回expr2，如果expr2为t，返回expr4，否则返回expr5 |

注意：

- 在函数表达式中，判断空值使用IS NULL，判断非空使用IS NOT NULL，判断具体值用=

```mysql
#找出不收佣金或佣金低于100的员工
SELECT * FROM emp WHERE IFNULL(comm,0) <100

#找出各月倒数第三天受雇的所有员工
#函数last_day(日期)，可以返回该日期所在月份的最后一天
SELECT * FROM emp WHERE LAST_DAY(hiredata) -2 = hiredate

#WHERE MONTH(DATE_ADD(hiredate, INTERVAL 3 DAY)) != MONTH(hiredate) 也可以使用，考虑到12月之后变成1月
#也可以用DATEDIFF函数
```

