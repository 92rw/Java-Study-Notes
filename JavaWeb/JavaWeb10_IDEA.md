# 使用IDEA开发JavaWeb

官方文档：[Getting started | IntelliJ IDEA Documentation (jetbrains.com)](https://www.jetbrains.com/help/idea/getting-started.html)

## 程序使用

* 对于需要后期补充业务逻辑的代码，可以在注释中加入 todo，程序会高亮显示以便后期完善优化
* 对于某个类Ctrl+Alt+U可选择查看类图，类图中按住Ctrl+Alt+B 查看对应接口的子接口和实现子类，类图中按住Ctrl+Alt+P查看父类，对某个类按F4进入其java文件页面
* 在接口按住Ctrl+H可查看实现子类
* 按住Ctrl+Alt+← 回到上次访问的位置（在访问过的的页面间切换），定位到页面顶部Ctrl+Home，定位到页面底部Ctrl+End
* File->Settings->Keymap 对复制文件名设置快捷键 Ctrl+Alt+F，用于快速复制文件名
* 格式化已写的代码 Ctrl+Alt+L
* 切换选中字符的大小写 Ctrl+Shift+U

* 实现文件名联动修改：通过Refactor功能，点击左下角的Do Refactor
* 对于外部拷贝到项目中的文件，可能会因为未加载到out目录导致获取失败。此时需要Build ->Rebuild Project 让程序重新识别资源
* 在接口中增加新的方法后，可以直接在方法按Alt+Enter快速进入实现类，重写方法的具体实现
* 查看方法源代码：Ctrl+B定位到编译类型的方法，Ctrl+Alt+B定位到具体实现类的方法（如果有多个实现子类，需要选择查看哪个类的方法）
* 如果项目目录的多层层级关系被压缩成一行，可在设置选项中取消勾选 Compact Middle Packages

### 关键词搜索

* 搜索项目中的引用地址：按两下Shift
* 在某个目录下搜索（Find in path）：Ctrl+Shift+F
* 按名称查询某个类：Ctrl+N

### 便捷化设定

* 在IDEA给xml文件注释时，如果不希望注释前部包含空的制表符，可以在File->Settings->Editor->Code Style->XML->Code Generation中取消勾选"Line comment at first column"
* 在IDEA给HTML文件注释时，如果不希望注释前部包含空的制表符，可以在File->Settings->Editor->Code Style->HTML->Code Generation中取消勾选"Line comment at first column" 和 "Block comment at first column"
* 在方法之间设置分隔符号，可在File->Settings->Editor->General->Appearance中勾选"Show method separators"
* 标签页管理：File->Settings->Editor->General->Editor Tabs，取消勾选"Show tabs in one row"让所有打开过的都显示；在下方Tab limit输入最大标签数
* 去掉代码重复提示：File-->Settings->Editor-->Inspections，找到General，将duplicated code后面的勾去掉，然后点击Apply即可

### 断点调试

* 在断点调试时，可以使用Ctrl+Shift+F10快速执行鼠标点击的代码，方法名会显示在上方下拉框内
* 在开发JavaWeb时，无法在代码中右键选择debug，需要使用右上角的debug按钮
* 在执行某次请求并暂停断点调试时，点击下方左侧的播放按钮（Resume Program）放行该次访问
* 需要得到某个方法执行后返回的参数值，选中字段然后 右键->Evaluate Expression
* 对于参数复杂的对象，除了用toString方法输出，也可以debug查看参数值
* 调试输出：File->Settings->Build,Execution,Deployment->Debugger->Stepping中，取消勾选Do not step into the classes
* 学习源代码时，在File->Settings->Build,Execution,Deployment->Debugger->Data Views->Java中，取消勾选 "Hide null elements in arrays and collections" 和 "Enable alternative view for Collections classes"可以查看当前集合包括空值的所有元素；选中Sort values alphabetically可以让元素按字母顺序排序
* 在显示集合中存在的对象时，结果可能不全。此时在需调试的代码所在行右键选择 Evaluate Expression，在 Result 中会显示更加完整的结果
* 对于调试界面的某个变量，右键选择Customize Data Views，勾选 Enable alternative view for Collections classes

## 设置JavaWeb工具

1、项目名称处右键选择Add Framework Support，勾选Web Application (4.0)，此时项目中新增web文件夹，可以进行服务器的相关配置

2、右上角Edit Configurations.配置Tomcat服务器：

①Deployment（发布方式）处新增war exploded包，在Application context设置项目的访问路径（只有一个"/"代表输入域名即可访问）

②Server处修改热加载选项 On 'Update' action 和 On frame deactivation 为 Update classes and resources

注意事项：

1.热加载选项

（1）on update action：执行更新操作时，Tomcat会自动更新类和资源（当jsp/html文件修改时，可以生效，但是如果你修改的java文件，需要Redepoly才会生效）
（2）on frame deactivation：表示IDEA失去焦点（比如最小化），也会导致jsp/html发生更新，java文件仍需要Redepoly

2.在Tomcat Server Settings中修改端口

（1）只会作用于当前项目，不会影响Tomcat目录下的配置文件server.xml
（2）多个程序不能同时占用一个端口号，运行前需检查可用性

3.当Tomcat启动时，会生成out目录，该目录就是原项目资源的映射，浏览器访问的是out目录下的文件，而不是编写源码的文件。在IDEA中选择Build -> Rebuild可重新生成out文件

4.当我们从外部拷贝资源到项目（图片，文件，js，css等），如果出现404不能访问错误，解决方式：先rebulid project，再重启Tomcat（本质：同步资源）

## 目录简介

.idea文件夹：idea使用的文件

out文件夹：真正访问的项目目录/工作目录

src文件夹：存放java程序，servlet，过滤器，监听器service controller，dao

web文件夹：直接目录，存放web工程的资源文件（html，js，css，jsp等）

web/WEB-INF文件夹：受服务器保护的目录，浏览器无法直接访问

web/WEB-INF/web.xml文件：动态web工程的配置部署描述文件，配置web组件（Servlet程序，Filter过滤器，Listener监听器，Session超时……）

web/WEB-INF/lib文件夹：存放第三方jar包（IDEA需要自己导入，导入后可在Project Structure -> Libraries查看）