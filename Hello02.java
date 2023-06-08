//Javadoc

/**
* @author  92rw
* @version 1.0
*/

//应用实例
//javadoc -d 文件夹名 -xx -yy 文件名.java

/*
Java代码规范
1. 类、方法的注释，要以 javadoc （文档注释）方式来写。
2. 非 javadoc 的注释，往往是给代码的维护者看的，着重告述读者为什么这样写，如何修改，注意事项等
3. 使用 tab 按键，实现缩进，默认整体向右边移动；可用 shift + tab 整体向左移
4. 运算符和 = 两边习惯性各加一个空格。比如：2 + 4 * 5 + 345 - 89
5. 源文件使用 utf-8 编码
6. 行宽度不要超过 80 字符
7. 代码编写分为 次行风格 和 行尾风格（推荐） 
*/

//Dos: Disk Operation System

/*

●常用的dos命令

1.查看当前目录是有什么：dir
dir d:\abc2\test200

2.切换到其他盘下：cd(change directory)
案例演示：切换到c盘
cd /D C:

3.切换到当前盘的其他目录下（使用相对路径和绝对路径演示）
案例演示：
cd d:\abc2\test200
cd ..\..\abc2\test200

4.切换到上一级：
案例演示：cd ..

5.切换到根目录：cd \
案例演示：cd \

6.查看指定的目录下所有的子级目录 tree3

7.清屏 cls

8.退出 exit

9.仅作了解：
md[创建目录，利用空格可新建多个文件]

rd[删除目录]

copy[拷贝文件]

del[删除文件]

echo[输入内容到文件]
echo 100 > 100.txt
//大于号覆盖，两个大于号追加

type 新建文件
type nul > abc.txt
//新建空白文件

move[剪切]

help 使用说明
*/


public class Hello02{
	public static void main(String[] args){
		System.out.println("本章学习内容：代码结构，转义符，注释（单行、多行），Javadoc，DOS");
	}
}