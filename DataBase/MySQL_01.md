# MySQL数据库

关系型数据库：表的每一行称为记录（Record），表的每一列称为字段（Column），通过主键和外键维护

### 结构化查询语言SQL

* DDL：Data Definition Language
  [02数据定义语句](MySQL_02.md)，[08约束](MySQL_08.md)，[09索引](MySQL_09.md)

* DML：Data Manipulation Language

  [03数据库操作语句](MySQL_03.md)，

* DQL：Data Query Language

  [04函数](MySQL_04.md)，[05查询加强](MySQL_05.md)，[06多表查询](MySQL_06.md)，[07表外连接](MySQL_07.md)，[12视图](MySQL_12.md)，

其他功能

[01连接MySQL](MySQL_01.md)，[10事务和锁](MySQL_10.md)，[11表类型和存储引擎](MySQL_11.md)，[13用户权限管理](MySQL_13.md)



+ 常见错误

| **错误点**            | **风险**         | **如何避免？**          |
| --------------------- | ---------------- | ----------------------- |
| `UPDATE` 不加 `WHERE` | 修改整张表数据   | **必须添加 `WHERE`**    |
| `DELETE` 不加 `WHERE` | 删除所有行       | **加 `WHERE` 限制范围** |
| `TRUNCATE` 误用       | 清空表，无法回滚 | **谨慎使用**            |
| `INSERT` 缺少列名     | 字段错位         | **显式写出列名**        |
| `SELECT *`            | 影响查询效率     | **只查询需要的字段**    |
| **事务未提交**        | 更改未生效       | **使用 `COMMIT`**       |

另外参考

[实用SQL语句 - SQL教程 - 廖雪峰的官方网站](https://liaoxuefeng.com/books/sql/mysql/useful-sql/index.html)：数据更新



## 使用Java程序操作数据库

[MyBatis的快速入门](MyBatis_01.md)，

[实现MyBatis的底层机制（一）](MyBatis_02.md)，

[实现MyBatis的底层机制（二）](MyBatis_03.md)



### 连接 MySql

#### 使用命令行窗口进入MySQL 客户端
```powershell
mysql -h 主机ip地址 -P 端口 -u 用户名 -p 密码
```

注意：

1. 登录前，保证服务启动

2. `-p` 和密码间没有空格，如果 `-p` 没有密码，回车后会要求输入密码

3. 如果没有写 `-h` 主机，默认本机，没有写 `-p` 端口，默认3306

4. 实际工作中，监听端口一般不是3306



#### 使用图形化操作软件

1. Navicat：Ctrl+/注释

2. SQLyog：
   * Shift+Ctrl+C注释，Shift+Ctrl+R取消注释
   * Ctrl+F12格式化
   * CTRL + 鼠标齿轮 可以改变显示大小，方便学习命令行语句

3. 参考 [管理MySQL - SQL教程 - 廖雪峰的官方网站](https://liaoxuefeng.com/books/sql/mysql/management/index.html)


