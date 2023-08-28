# 注释（comment）

1. 单行注释//
2. 多行注释/* */ （多行注释里面不允许有多行注释嵌套）
3. 文档注释/** */

注释内容可以被JDK提供的工具javadoc所解析，生成一套以网页文件形式体现的该程序的说明文档,一般写在类

```
javadoc -d 生成的doc所存的文件夹名 -author -version Comment02.java
```

## javadoc 常见标签

| 标签          | 描述                                                   | 示例                                                         |
| ------------- | ------------------------------------------------------ | ------------------------------------------------------------ |
| @author       | 标识一个类的作者                                       | @author description                                          |
| @deprecated   | 指名一个过期的类或成员                                 | @deprecated description                                      |
| {@docRoot}    | 指明当前文档根目录的路径                               | Directory Path                                               |
| @exception    | 标志一个类抛出的异常                                   | @exception exception-name explanation                        |
| {@inheritDoc} | 从直接父类继承的注释                                   | Inherits a comment from the immediate surperclass.           |
| {@link}       | 插入一个到另一个主题的链接                             | {@link name text}                                            |
| {@linkplain}  | 插入一个到另一个主题的链接，但是该链接显示纯文本字体   | Inserts an in-line link to another topic.                    |
| @param        | 说明一个方法的参数                                     | @param parameter-name explanation                            |
| @return       | 说明返回值类型                                         | @return explanation                                          |
| @see          | 指定一个到另一个主题的链接                             | @see anchor                                                  |
| @serial       | 说明一个序列化属性                                     | @serial description                                          |
| @serialData   | 说明通过writeObject( ) 和 writeExternal( )方法写的数据 | @serialData description                                      |
| @serialField  | 说明一个ObjectStreamField组件                          | @serialField name type description                           |
| @since        | 标记当引入一个特定的变化时                             | @since release                                               |
| @throws       | 和 @exception标签一样.                                 | The @throws tag has the same meaning as the @exception tag.  |
| {@value}      | 显示常量的值，该常量必须是static属性。                 | Displays the value of a constant, which must be a static field. |
| @version      | 指定类的版本                                           | @version info                                                |



# DOS命令

Dos：磁盘操作系统，Disk Operation System

### Java编写步骤

1. 编写java程序的源代码
2. 通过 javac 命令编译，得到对应的 .class 字节码文件
3. 通过 java 命令运行，本质就是把 .class 加载到 jvm 运行

说明：javac 默认跟随系统设置采用 GBK，因此执行时需如输入 -encoding UTF-8 解决乱码问题

### 环境变量path配置及其作用

1. 环境变量的作用是为了在dos的任意目录，可以去使用java 和 javac命令
2. 先配置 JAVA_HOME = 指向jdk安装的主目录
3. 编辑path环境变量，增加 %JAVA_HOME%\bin 

## 常用的dos命令


```powershell
#1.查看当前目录是有什么：dir
dir d:\abc2\test200

#2.切换到其他盘下：cd(change directory)
cd /D C: #案例演示：切换到c盘

#3.切换到当前盘的其他目录下（使用相对路径和绝对路径演示）
cd d:\abc2\test200
cd ..\..\abc2\test200

#4.切换到上一级：
cd ..

#5.切换到根目录：cd \
cd \

#6.查看指定的目录下所有的子级目录
tree3

#7.清屏
cls

#8.退出
exit

#9.仅作了解：
md #[创建目录，利用空格可新建多个文件]
rd #[删除目录]
copy #[拷贝文件]
del #[删除文件]
echo #[输入内容到文件]
echo 100 > 100.txt #大于号覆盖，两个大于号追加
type #新建文件
type nul > abc.txt #新建空白文件
move #剪切
help #使用说明
```

