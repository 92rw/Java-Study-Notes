# 项目设计-程序框架图  

* 明确系统有哪些类（文件）
* 明确类之间的调用关系

实现功能的三部曲：明确完成功能 -> 思路分析 -> 代码实现

各司其职，化繁为简

## 项目介绍

实现基于文本界面的《房屋出租软件》

能够实现对房屋信息的添加、修改和删除（用数组实现），并能够打印房屋明细表

功能界面包括：

* 新增房源
* 查找房屋
* 删除房屋
* 修改房屋信息
* 房屋列表
* 退出

## 主要文件

### 1. HouseRentApp.java

```
main() {//程序入口
//创建Houseview对象
//调用该对象，显示主菜单
}
```

### 2. HouseView.java 界面

* 显示主菜单
* 接收用户输入
* 调用其他类(HouseService类)完成对房屋信息的各种操作  

#### 代码实现分析：  

* 编写 mainMenu() 方法显示主菜单
* 编写 listHouses() 方法调用 HouseService 的房屋列表
* 编写 addHouses() 方法接收用户输入
* 编写 delHouses() 方法接收用户输入id
* 编写 findHouse() 方法接收用户输入id
* 编写 updateHouse() 方法接收用户输入id

###　3. HouseService.java 业务层

* 响应 HouseView 的调用
* 完成房屋信息 CRUD (create,read,update,delete)  

####  代码实现分析：

* 定义 House[] 数组保存房间对象
* 编写 list() 方法返回房屋信息
* 编写 add(House newHouse) 方法把新对象加入数组，返回 boolean
* 编写 del(int delId) 方法删除房屋信息，返回 boolean
* 编写 find(int delId) 方法返回 House 对象，否则返回 null

### 4.House.java (domain/model/数据层)

* 一个House对象表示一个房屋信息
* 储存的信息包括：编号，房主，地址，电话，月租，状态（未出租/已出租）  

### 工具类

- Utility.java 完成获取用户各种输入


#### Utility类的使用  

准备工具类 Utility，提高开发效率
在实际开发中，公司都会提供相应的工具类和开发库，可以提高开发效率
程序员也需要能够看懂别人写的代码，并能够正确的调用

#### 这个程序中用到的Utility类

因为工具类不需要实例化对象，所以设计的时候把属性和方法都设计为静态方式。在使用的时候就不需要new，不占内存。

如果方法和对象联系不大的时候就可以设置为静态的。使用时可以直接用 类.方法() 语句，因为方法设置为 static，是一个静态方法

静态访问静态 可直接类名调用，静态访问非静态 new对象后才能调用

### 已优化的代码

1、增加按地址查询房屋类型的代码块

2、利用 Utility.readString() 函数的重载，省略对输入空值的条件判断语句

[返回主目录](https://github.com/92rw/Java-Study-Notes/tree/main)
