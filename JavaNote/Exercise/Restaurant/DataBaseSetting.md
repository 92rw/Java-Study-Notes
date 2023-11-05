```
#创建数据库
CREATE DATABASE mhl;

#创建employee表（主键id，empId,name,pwd,jpb等）
create table employee (
	id int primary key auto_increment, #自增
	empId varchar(50) unique not null default '',#员工号
	pwd char(32) not null default '',#密码md5
	name varchar(50) not null default '',#姓名
	job varchar(50) not null default '' #岗位
)charset=utf8; 

insert into employee values(null, '6668612', md5('123456'), '张三丰', '经理');
insert into employee values(null, '6668622', md5('123456'),'小龙女', '服务员');
insert into employee values(null, '6668633', md5('123456'), '张无忌', '收银员');
insert into employee values(null, '666', md5('123456'), '老韩', '经理');

#餐桌表
create table diningTable (
	id int primary key auto_increment, #自增, 表示餐桌编号
	state varchar(20) not null default '',#餐桌的状态
	orderName varchar(50) not null default '',#预订人的名字
	orderTel varchar(20) not null default ''
)charset=utf8; 

insert into diningTable values(null, '空','','');
insert into diningTable values(null, '空','','');
insert into diningTable values(null, '空','','');

#菜谱
create table menu (
	id int primary key auto_increment, #自增主键，作为菜谱编号(唯一)
	name varchar(50) not null default '',#菜品名称
	type varchar(50) not null default '', #菜品种类
	price double not null default 0#价格
)charset=utf8; 

insert into menu values(null, '八宝饭', '主食类', 10);
insert into menu values(null, '叉烧包', '主食类', 20);
insert into menu values(null, '宫保鸡丁', '热菜类', 30);
insert into menu values(null, '山药拨鱼', '凉菜类', 14);
insert into menu values(null, '银丝卷', '甜食类', 9);
insert into menu values(null, '水煮鱼', '热菜类', 26);
insert into menu values(null, '甲鱼汤', '汤类', 100);
insert into menu values(null, '鸡蛋汤', '汤类', 16);

#账单流水, 考虑可以分开结账, 并考虑将来分别统计各个不同菜品的销售情况
create table bill (
	id int primary key auto_increment, #自增主键
	billId varchar(50) not null default '',#账单号可以按照自己规则生成 UUID
	menuId int not null default 0,#菜品的编号, 也可以使用外键
	nums SMALLINT not null default 0,#份数
	money double not null default 0, #金额
	diningTableId int not null default 0, #餐桌
	billDate datetime not null ,#订单日期
	state varchar(50) not null default '' # 状态 '未结账' , '已经结账', '挂单', '坏账'
)charset=utf8;

insert into menu values(null,?,?,?,0,?,now(),'未结账')
drop table bill;
```

