# MySQL管理

## MySQL用户

MySQL中的用户，都存储在系统数据库mysql中user表中。在项目开发时，由MySQL数据库管理人员（root）根据需要向不同的开发人员赋予不同的开发权限。



其中user表的重要字段说明：

1. host：允许登录的"位置"，localhost表示该用户只允许本机登录，也可以指定ip地址，比如：192.168.1.100
2. user：用户名；
3. authentication_string:密码，是通过mysql的password()函数加密之后的密码



```mysql
#创建用户
create user '用户名'@'登录IP地址' identified by '登录密码'

#删除用户
drop user '用户名'@'登录IP地址'

#查询用户及权限
select * from mysql.user
```

注意：如果出现MySQL服务器使用——skip-grant-tables选项运行，因此无法执行此语句，执行 flush privileges;后再创建就可以了

不同的数据库用户，登录到DBMS后，根据响应的权限，可以操作的库和数据对象（表、视图、触发器）不相同



```mysql
#修改自己密码
set password = password('新密码');

#修改他人的密码（需要有修改用户密码的权限）->access denied
set password for '用户名'@'登录IP地址' = password('新密码');

#备注：8.0后面的版本为：SET PASSWORD FOR '用户名'@'登录位置'='密码'
#或者：ALTER USER '用户名'@'localhost' IDENTIFIED WITH MYSQL_NATIVE_PASSWORD BY '密码';
```



### 给用户授权

基本语法：

```my
grant 权限列表 on 库.对象名 to '用户名'@'登录位置' [identified by '密码']
```



说明：

1.权限列表中，多个权限用逗号分开

```
grant select on .....
grant select, delete, create on ....
grant all [privileges] on .... //表示赋予该用户在该对象上的所有权限
```

2.有关“库.对象名”的特别说明

```
**：代表本系统中的所有数据库的所有对象（表，视图，存储过程）
库.*：表示某个数据库中的所有数据对象（表，视图，存储过程等）
```

3.identified by可以省略，也可以写出

（1）如果用户存在，就是修改该用户的密码。

（2）如果该用户不存在，就是创建该用户！



回收用户授权
```revoke 权限列表 on 库.对象名 from '用户名'@'登录位置'```
权限生效指令
```FLUSH PRIVILEGES;```



案例演示：

1.创建一个用户（qda），密码qdk，并且只可以以本地登录，不让远程登录mysql

2.创建库和表testdb下的news表，要求：使用root用户创建

3.给用户分配查看news表和添加数据的权限

4.测试看看用户是否只有这几个权限

5.修改密码为abc，要求：使用root用户完成

6.重新登录

7.使用root用户回收权限，然后删除你的用户

```mysql
#创建新用户（默认情况下，该用户只能看到一个默认的系统数据库）
CREATE USER 'qda'@'localhost' IDENTIFIED BY 'qdk';

#创建数据库和表，添加一条测试数据
CREATE DATABASE testdb;
CREATE TABLE testdb.news(id INT, content VARCHAR(32));
INSERT INTO testdb.news VALUES(1,'文汇报');

#给用户赋权
GRANT SELECT, INSERT ON testdb.news TO 'qda'@'localhost';

#登录用户，测试代码 UPDATE news SET content = '大公报' WHERE id = 1 提示失败，说明没有UPDATE权限

#修改用户密码
SET PASSWORD FOR 'qda'@'localhost' = PASSWORD('abc')

#回收所有权限
REVOKE ALL ON testdb.news FROM 'qda'@'localhost'

#删除用户
DROP USER 'qda'@'localhost'
```

细节说明

- 在创建用户的时候，如果不指定Host，则为%，%表示表示所有IP都有连接权限```create user xxx;```
- 也可以这样指定```create user 'xxx'@'192.168.1.%'```
  表示xxx用户使用192.168.1.*网段的ip都可以登录MySQL
- 在删除用户的时候，如果 host 不是 %,需要明确指定```'用户'@'host值'```