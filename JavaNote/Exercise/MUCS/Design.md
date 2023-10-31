# 多用户通信系统

## 内容简介

多用户通信系统（Multi user communication system），由命令行界面实现

### 涉及到的技术：

项目框架设计
java面向对象编程
网络编程
多线程
IO流
集合

### 需求分析

1. 用户登录
2. 拉取在线用户列表
3. 无异常退出
4. 私聊
5. 群聊
6. 发文件
7. 服务器推送新闻

### 功能实现

服务端

1. 当有客户端连接到服务器后，会得到一个socket
2. 启动一个线程，该线程持有该socket对象，也就是说socket是该线程属性
3. 为了更好地管理线程，需要使用集合 HashMap 来管理

客户端

1. 和服务端通信时，使用对象方式，可以使用对象流来读写
2. 当客户端连接到服务端后，也会得到socket
3. 启动一个线程，该线程持有socket
4. 为了更好管理线程，也将该线程放入到集合

## 项目开发流程

需求分析 30%

1. 需求分析师：懂技术+行业
2. 出一个需求分析报告(白皮书)，该项目功能.客户具体要求

设计阶段 20%

1. 架构师/项目经理
2. 设计工作(UML类图，流程图，模块设计，数据库，架构)
3. 原型开发
4. 组建团队

实现阶段 20%

1. 程序员/码农
2. 完成架构师的模块功能
3. 测试自已模块 -> 自检后交付测试，也叫冒烟测试

测试阶段 20%

1. 测试工程师
2. 单元测试，测试用例,白盒测试，黑盒测试，集成测试

实施阶段

1. 实施工程师(开发能力，环境配置部署能力)
2. 项目正确的部署到客户的平台，并保证运行正常
3. 出差多

维护阶段

1. 发现bug并解决
2. 项目升级

## 设计过程：

### 1. Common 目录下进行通讯的类

* User类：记录用户名和密码数据，进行网络通信（Serializable接口）
* Message类：表示客户端和服务器通信时的消息对象
* MessageType接口：表示消息类型
  备注：实际开发环境中需要将此文件夹部署在不同工程项目中，而且需要建立在一样的路径下

### 2. 客户端的命令行界面

客户端 ClientWindow 目录下的类

* ClientWindow.utils 文件夹：存放工具类
* ClientWindow.show 文件夹：用户界面（ClientView 类用于显示用户交互页面）

### 3. 客户端和服务器端的交互

ClientWindow.service 文件夹

* UserClientService 验证用户登录信息
* ClientConnectServerThread 建立连接服务器的线程
* ClientThreadCollection 管理客户端连接服务器的线程

在ClientView 类中加入 UserClientService 对象，用于判断用户是否有权限进入二级交互界面

说明：不创建线程专门处理读写的话，主线程会被阻塞

客户端创建一个 User 对象，发送到服务端，服务端验证完成后返回 Message 对象，如果成功，则进入系统

### 4. 服务端监听并保持客户端连接

Frame 文件夹：启动后台的相关服务
ServicePort.server 文件夹：完成服务器的功能

* BackgroundServer 监听端口，处理用户发送的信息
* ServerConnectClientThread 建立连接客户端的线程
* ServerThreadCollection 管理服务器连接客户端的线程

读取客户端发过来的 User 对象，执行判断后再返回 Message 对象

在 BackgroundServer 中创建集合，存放多个用户（如果涉及多线程，建议使用ConcurrentHashMap）

### 5. 读取在线用户列表

用户端：

* 给 MessageType 接口增加属性
* 给 UserClientService 类增加获取在线用户列表的方法
* 在 ClientConnectServerThread 的 run() 方法中增加判断服务器返回数据类型的语句，并做响应处理
* 在 ClientView 中调用 UserClientService 新增的方法

服务器端

* 在 ServerConnectClientThread 的 run() 方法中增加判断客户端发送数据类型的语句，并做响应处理
* 在 ServerThreadCollection 中编写方法，遍历集合得到在线用户信息

### 6.无异常退出

在用户界面选择退出系统后，main 线程结束，socket 所在的子线程未结束。因此需要在main 线程调用方法，给服务器端发送一个退出系统的 message对象。

客户端

 UserClientService 新增 logout() 方法，调用 System.exit(0) 指令完成退出；
服务器端

* 在 ServerThreadCollection 新增删除元素的方法

* 在 ServerConnectClientThread 的 run() 方法中将 socket 关闭，在集合中删除，结束循环退出线程。

如果提示 java.io.EOFException 错误，可以在服务器端关闭 socket 前先 Thread.sleep(100);

### 7.私聊消息

客户端

* 接收用户希望给某个其它在线用户聊天的内容（在ClientView 中新增发送消息的代码，并新建 UserMessageService 对象）
* 将消息构建成 Message 对象，通过对应的 socket 发送给服务器（新建 UserMessageService 类实现）
* 在线程中读取到服务器端发送的 Message 消息并显示 （在 ClientConnectServerThread 中新增接收消息的代码）

服务端

* 可以读取到客户端发送给某个客户的消息（在 ServerConnectClientThread 中新增相关的代码）
* 从管理线程的集合中，根据 Message 对象信息访问相关线程
* 将 Message 对象转发给指定客户 （在 ServerThreadCollection 中构造方法，判断消息接收方是否在线）
* (离线消息的实现) 在服务器端新建 ConcurrentHashMap ，key = receiverId，value = ArrayList<Message>

### 8.群聊消息

客户端：

* 在ClientView 中调用 UserMessageService 类新增的群发消息方法

* 在 ClientConnectServerThread 中新增接收消息的代码

服务器端

* 在 ServerThreadCollection 中构造方法，返回监听用户端口的线程
* 在 ServerConnectClientThread 调用方法转发消息



### 9.文件传输

客户端：

* 将文件以字节数组的形式读取到客户端（在 ClientView 类让发送方填写相关数据）
* 把文件对应的字节数组封装到 Message 对象（给MessageType 和Message 增加成员属性）
* 把 Message 对象发送给服务端（新建 UserFileService 类）
* 在接收到 Message 对象后，保存到对应目录下（在 ClientConnectServerThread 类接收服务器转发的对象）

服务端

* 接收到 Message 对象
* 拆解 Message 对象的 receiverId，获取该用户通信线程
* 把 Message 对象转发给指定用户

### 10.服务器群发消息

* 推送消息/新闻，本质其实就是群发消息（在服务器端也创建工具类，用于接收输入信息）
* 在服务器启动一条独立线程，专门负责发送消息（新建 ServerMessage 类继承 Runnable 接口）
* 指定消息类型与用户群发相同，客户端不需要额外增加代码



## 已优化的代码：

1. 因为服务器端的任务就是完成交互，频繁创建流会造成流损坏异常，所以在创建线程时就创建输出流，在服务器的方法中直接调用。

2. 如果服务器端或用户端非正常退出，在的run()方法中捕获到异常后，通过 System.exit(0) 退出循环。

3. 用户不可向自己发起私聊；如果私聊消息发送失败，向发送方返回信息

4. 如果某个线程中的 socket 同时运行多个对象输出流，将抛出异常 java.io.StreamCorruptedException: invalid type code: AC。因此每次使用时必须新建对象输出流

5. 将离线消息的相关方法设置为静态属性，方便其他类进行调用，用户发送消息失败时返回失败原因

## 有待优化的功能：

1. 离线留言的其他思路：

用到了线程通信wait()，单独写一个类继承线程类,在run中死循环然后启动就wait()阻塞，如有客户端登录成功就用notify()唤醒线程

2. 用户在接收文件时，可选择是否接收，也可选择接收到一个默认的磁盘路径。

而主线程在循环监听用户输入，所以接收方无法指定存储路径。在 socketThread线程考虑一下别的方法接收用户输入，比如 JOptionPane；

可以将文件传输功能封装到新的线程中，避免文件过大造成界面卡顿，尝试增加文件传输的进度条。

3. 目前用户端管理线程的集合只有连接服务器的一个线程，可以增加其他线程并存放进集合中

4. 离线传文件

可以指定新的MessageType，先在服务器端暂存到temp文件夹，改变对象的senderId和src属性，或是新建对象再发给离线用户

5. 服务器强制用户下线

## 经验总结

* 对象的序列化反序列化是根据序列化版本id进行的，没有显式得写出来会默认根据类的属性和方法分配一个。导致对象序列化入库之后，若类被修改，反序列化将会报错。所以显式加上序列化版本id，避免反序列化报错
* 接口里写成员变量默认被 static final 修饰
* 单元测试不能使用控制台，需要在main方法调试
* 时刻保持链接并使用多线程同时运行可以使得输入的时候可以时刻从服务器获得信息，减缓了反复链接的延迟和资源浪费
* 如果需要用break退出外层循环，可以在外层循环添加标签号，指定break退出的标签。
* 对象流在网络传输时readObject只读一个对象，底层做了处理不用设置结束标记，无需flush和shutdown
* String类型不可用==判断值是否相等
* 将多种类型的消息封装到类中（文件就是字节数组），实现传输信息代码的复用

[返回主目录](https://github.com/92rw/Java-Study-Notes/tree/main)