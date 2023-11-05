# 满汉楼项目说明
满汉楼项目功能多，界面复杂，涉及到复杂的awt和swing技术和事件编程，做如下调整

1. 去掉界面和事件处理（工作中使用很少），使用控制台界面
2. 完成满汉楼项目的登录、订座、点餐和结账、查看账单等功能
3. 提示：在实际工作中，独立完成项目新功能常重要，这是锻炼编程能力和思想的重要途径



## 程序框架图

界面层

* MHLView.java：调用service层的类，得到结果，显示数据

业务层/service层

* EmployeeService：组织sql并调用相应xxxDao完成综合的需求
* DiningTableService
* MenuService
* BillService

DAO层

* BasicDAO：1.将各个DAO共同操作放到这里；2.简化代码，提高维护性和可读性

* EmployeeDAO：完成对employee表的增删改查，可以特有操作

* DiningTableDAO

* MenuDAO

* BillDAO

utils工具类

- Utility.java：读取输入
- JDBC10：基于druid数据库连接池的工具类
- druid.properties：连接数据库的配置文件

domain/JavaBean/pojo：调用数据库中的元素

- Employee

- DiningTable
- Menu
- Bill



## 功能实现

1. 准备工具类Utility，提高开发效率，并搭建项目的整体结构
2. 编写界面类MHLView.java
3. 在MySQL中创建[数据库和表](./DataBaseSetting.md)，方便后期调用；在domain目录下新建Employee.java用于反射
4. BasicDAO代码中执行基础的业务逻辑，编写EmployeeDAO继承BasicDAO实现特有的方法
5. 完成业务层EmployeeService，并在MHLView新建EmployeeService对象并调用此对象验证用户信息
6. 一条龙服务：mysql->domain->dao->service->view，实现餐桌（DiningTable）状态查询
7. 在业务层DiningTableService编写方法，完成餐桌预订功能：①检测餐桌是否存在②检测餐桌的状态。先确认是否预订，再判断桌子存在和空闲状态与否，减少调用数据库查询，不浪费资源
8. 一条龙服务：mysql->domain->dao->service->view，完成菜单的查询操作
9. 在业务层MenuService编写方法，完成点餐功能：①对餐桌号、菜品编号做合理性校验②点餐成功后，修改餐桌状态③生成账单（另一条业务逻辑）-> 再高级点可以弄个材料表,每被点一道菜,材料表也减少对应的材料
10. 账单的业务逻辑：传入MenuService对象，以便调用其方法获取菜品金额；传入DiningTableService对象，以便调用其方法修改餐桌状态
11. 完成账单显示和结账的相关代码。结账的业务逻辑：①校验餐桌号②修改bill表中的状态③餐桌状态、订餐人和联系电话复原为“空”④不需要增加新表，不需要增加新类，需要增加方法
12. 多表查询的实现（例如在账单中显示菜品名称）：①新建domain类接收组合查询结果（这个项目采用此方案）②使用BeanListHandler会提示空指针异常，因此将一个类封装到另一个类中，采用MapListHandle方法从数据库中得到List<map<String,Object>>。例如，将Department类封装到Employee类中，新建```List<Employee>```，在迭代query时新建类对象并接收拆包后的结果，再将得到的department对象赋值给employee对象，然后将employee对象加入到新List -> A类有一个属性B类，A类关联的表里面存不了B类，存的是B类的字段，再存取的时候转换一下

## 项目总结

1. 在实际开发中，公司都会提供相应的工具类和开发库，可以提高开发效率
2. 编写完方法后，进行单元测试（把SQL语句放在查询分析器进行测试，编写测试方法检查工具类可用性）
3. 如果某个方法涉及到的代码内容较多，可将其部分功能抽取出来组成新方法 [idea抽取方法的快捷键_笔记大全_设计学院 (python100.com)
4. 软件分层是逻辑概念
   * 可以用不同包来存放对应的类
   * 体现了一个调用的关系
   * 可以各司其职
5. 目前表的字段主键名都是id，建议改成 `表名_id` 的形式
6. 这个项目是为了讲解jdbc与数据库的知识点，所以没有用到多线程、高并发那些，还有锁的使用，真正的项目肯定还要考虑很多东西
7. 各司其职：涉及到跨表操作时，表和类需要一一对应，提供给其他类调用，而不是将跨表的业务写在单个的类中
8. 事务的使用：自己在工具类 再写一个获得连接的方法 并设置手动提交 需要使用事务的时候 就用这个连接，使用ThreadLocal（给每个线程分配一把锁）来解决，在框架中比如mybatis提供了事务支持
9. 避免多表联合查询时候出现重复列名的方法：①在容易重复的列名字段前加表名②domain类起新名，在查询数据库时利用```SELECT XX AS XXX```方法修改显示出的列名
10. 分表设计：员工信息字段较多，而且员工数也很多。为了提高效率，将用户详细信息（员工号、个人资料、照片等）和登录信息（员工号、密码）分开。登录时仅查询登录表login，登陆成功则查询employee表获取详细信息