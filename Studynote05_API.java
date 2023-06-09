/*
Java API(Application Programming Interface应用程序变成接口)文档,
中文版matools.com

Java类的组织形式

JDK→包（package）→接口（interface），类（class），异常
类→字段（field），构造器（constructor），成员方法（method）

*/

//软件包→类→方法
//直接搜索



/*标识符的明明规则和规范

标识符概念
1.Java对各种变量、方法和类等命名时使用的字符序列称为标识符
2.凡是自己可以起名字的地方都叫标识符int num1=90:


标识符的命名规则（必须遵守）
1.由26个英文字母大小写，0-9，_或$组成（不含-）
2.数字不可以开头。例如int 3ab=1;
3.不可以使用关键字和保留字，但能包含关键字和保留字。
4.Java中严格区分大小写，长度无限制。int totalNum = 10; int n = 90:
5.标识符不能包含空格。int a b = 90;（×）


标识符命名规范[更加专业]
1.包名：多单词组成时所有字母都小写：aaa.bbb.ccc/比如 com.hsp.crm
2.类名、接口名：多单词组成时，所有单词的首字母大写：XxxYyyZzz[大驼峰]，比如：TankShotGame
3.变量名、方法名：多单词组成时，第一个单词首字母小写，第二个单词开始每个单词首字母大写：xxxYyyZzz[小驼峰，简称驼峰法],
比如：tankShotGame
4.常量名：所有字母都大写。多单词时每个单词用下划线连接：XXX_YYY_ZZZ，比如：定义一个所得税率TAX_RATE
5.后面我们学习到类，包，接口，等时，我们的命名规范要这样遵守，更加详细的看文档。
*/

/*
关键字
定义：被Java语言赋予了特殊含义，用做专门用途的字符串（单词）
特点：关键字中所有字母都为小写

用于定义数据类型的关键字
class	interface	enum	byte	short
int	long	float	double	char
boolean	void

用于定义数据类型值的关键字
true	false	null

用于定义流程控制的关键字
if	else	switch	case	default
while	do	for	break	continue
return

用于定义访问权限修饰符的关键字
private	protected	public

用于定义类，函数，变量修饰符的关键字
abstract	final	static	synchronized

用于定义类与类之间关系的关键字
extends	implements

用于定义建立实例及引用实例，判断实例的关键字
new	this	super	instanceof

用于异常处理的关键字
try	catch	finally	throw	throws

用于包的关键字
package	import

其他修饰符关键字
native	strictfp	transient	volatile	assert

保留字
现有Java版本尚未使用，但以后版本可能会作为关键字使用。自己命名标识符时要避免使用这些保留字
byValue、cast、future、.generic、inner、operator、outer、rest、var、goto、const
*/


/*
键盘输入语句
●
介绍
在编程中，需要接收用户输入的数据，就可以使用键盘输入语句来获取。
Inputjava,需要一个 扫描器（对象），就是Scanner

步骤：
1)导入该类的所在包，java.util.*
2)创建该类对象（声明变量）
3)调用里面的功能

案例演示：可以从控制台接收用户信息，[姓名，年龄，薪水]
*/

import java.util.Scanner;//表示把java.util下的Scanner类（简单文本扫描器）导入文件

class Input{
	public static void main(String[] args){
		//演示接受用户的输入
		//步骤1：引入 Scanner 类所在的包
		//步骤2：创建 Scanner 对象，new 创建一个对象
		Scanner myScanner = new Scanner(System.in);
		//步骤3：接收用户输入
		//将右边用户输入的值赋给等号左边的变量
		//当程序执行到next方法时，会等待用户输入
		System.out.println("请输入姓名");
		String name = myScanner.next();//接收用户输入String
		System.out.println("请输入年龄");
		int age = myScanner.nextInt();//接收用户输入int
		System.out.println("请输入薪水");
		double salary = myScanner.nextDouble();//接收用户输入double
		System.out.println("人的信息如下");
		System.out.println("姓名="+ name
			+" 年龄=" + age + " 薪水=" + salary);
	}
}
