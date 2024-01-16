## 软件项目开发阶段

```mermaid
graph TD

A --> B
B --> C
C -->|开发| D
D -->|测试| C
D --> E
E --> F
A["需求分析阶段 20%
1.需求分析师/产品经理
2.具备：技术背景+行业背景
3.完成需求分析→出需求分析白皮书
4.准确分析客户/项目需求完成的功能"]

B["设计阶段 20%
1.架构师/醒目经理
2.能力：技术全面，项目/人员管理
3.工作：设计工作（UML类图，时序图，
流程图，部署……数据库（表）设计，
模块划分，界面UI =>设计文档
4.组建团队，技术选型，界面原型开发"]

C["开发阶段/实现阶段 30%
1.程序员（开发工程师）
2.看文档→理解需求→完成指定模块功能
3.完成需求的不同模块，对小组长负责
4.代码的review和测试"]

D["测试阶段 20%
1.测试工程师
2.完成单元测试、集成测试、系统测试
3.技术（测试用例、黑盒测试、白盒测试、
压力测试、自动化测试工具、bug管理系统）
4.编写测试脚本，和开发交替进行"]

E["实施阶段
1.实施工程师
2.把开发好的项目部署到系统（本地/对方
公司内部），涉及出差，开发能力要求不高
3.熟悉各种环境、网络、操作系统linux、
数据库、网络设备、框架软件安装"]

F["维护阶段：
1.大项目有专门的维护部门，小公司售后、对接人员
2.对项目进行维护，解决使用过程中出现的错误
3.重启→重新安装软件配置→重装系统→反馈给开发（项目经理）"]
```

## 项目的设计

分层的目的是为了解耦，降低代码的耦合度，有利项目的维护和升级

### 经典的JavaEE三层架构（示意图）

```mermaid
sequenceDiagram
  participant A as 浏览器
  participant B as 服务器（Server）
  participant C as 服务器架构
  participant D as DB
  Note left of A:http://ip:port/工程<br>路径/资源
  A ->> B : http请求
  rect rgb(191, 223, 255)
    Note over B,D:服务器
    Note left of C:web层/视图层/View层<br>功能：1.接收用户请求<br>2.调用Service层，完成业务处理<br>3.返回响应数据<br>4.可能做重定向、转发<br>技术：html,css,js,jquery<br>vue/其他框架,servlet,SpringMVC
    
    Note over C:Service层/业务层<br>功能：完成业务处理<br>提供业务API-方法<br>技术：Java技术，Spring
	Note right of C:DAO层/数据层/数据持久层<br>功能：1.完成对数据库的操作<br>2.CRUD操作：Create创建<br>Read/Retrieve查询/检索<br>Update更新Delete删除<br>技术：JDBC，DBUtils<br>数据库连接池，MyBatis
  end
  
  B ->> A : http响应
Note left of A:解析返回的数据，<br>在浏览器展示
```

项目具体分层

| 分层           | 对应包                               | 说明                      |
| -------------- | ------------------------------------ | ------------------------- |
| web层          | furns.web/servlet/controller/handler | 接收用户请求，调用service |
| service层      | furns.service                        | Service接口包             |
|                | furns.service.impl                   | Service接口实现类         |
| dao持久层      | furns.dao                            | Dao接口包                 |
|                | furns.dao.impl                       | Dao接口实现类             |
| 实体 bean 对象 | furns.pojo/entity/domain/bean        | JavaBean类                |
| 工具类         | furns.utils                          | 工具类                    |
| 测试包         | furns.test                           | 完成对dao/service测试     |

在业务逻辑复杂时，有的操作需要在service层同时操作多个DAO，因此service层不可省略



### MVC设计模式

1、什么是MVC

MVC 全称∶ Model 模型、View 视图、Controller 控制器。

MVC 最早出现在JavaEE 三层中的Web 层，它可以有效的指导WEB 层的代码如何有效分离，单独工作。

View 视图∶只负责数据和界面的显示，不接受任何与显示数据无关的代码，便于程序员和美工的分工合(Vue/JSP/Thymeleaf/HTML)

Controller 控制器∶只负责接收请求，调用业务层的代码处理请求，然后派发页面，是一个"调度者"的角色(Servlet), 这个概念会贯穿javaee

Model 模型∶将与业务逻辑相关的数据封装为具体的JavaBean 类，其中不掺杂任何与数据处理相关的代码(JavaBean/Domain/Pojo)

2、MVC 是一种思想

MVC 的理念是将软件代码拆分成为组件，单独开发，组合使用（目的是为了解耦合）, 也有很多落地的框架比如SpringMVC

```mermaid
graph LR
A[客户端] -->|url请求| B["控制器Controller（Servlet）<br>调用 service，完成业务处理"]
B-->|"重定向<br>请求转发"|D["视图view 用于展示数据<br>JSP,html,css,vue"]
B -->|调用|C["模型Model（Javabean）<br>通过Service-DAO获取的数据"]
C-->|返回|B
D-->|响应|A
C-->E[数据库]
```

MVC是一种思想，体现的是数据显示、数据处理和业务调用的分离、解耦，SpringMVC就是MVC的落地体现

1. model 最早期就是javabean, 就是早期的jsp+servlet+javabean
2. 后面业务复杂度越来越高, model 逐渐分层/组件化(service + dao)
3. 后面有出现了持久成技术(service + dao + 持久化技术(hibernate / mybatis / mybatis-plus))
4. 还是原来的mvc ，但是变的更加强大了
