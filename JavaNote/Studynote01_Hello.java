
//学习《韩顺平 零基础30天学会Java》课程的第一篇笔记

/*
Java编写步骤
1. 编写java的源代码
2. javac 编译 ,得到对应的 .class 字节码文件
3. java 运行, 本质就是把 .class 加载到jvm  运行
*/

//javac 默认跟随系统设置采用 GBK，因此执行时需如输入 -encoding UTF-8 解决乱码问题



/*对代码的相关说明
1. public class Hello 表示 Hello 是一个 public 的类
2. Hello{} 表示一个类的开始和结束
3. public static void main(String[] args) 表示一个主方法，即我们程序的入口
4. main(){} 表示方法的开始和结束
5. System.out.println("Hello,world"); 表示输出 "Hello,world" 到屏幕
6. “;”表示语句结束
*/

//源文件的基本组成是 class（类），但只能有一个 public class
public class Studynote01_Hello {
	
	//应用程序的执行入口是 main() 方法，每个语句以 ";" 结束
	public static void main(String[] args){
		System.out.println("Hello,world");
	}
	
}

//利用非public class 定义一段计算测试并输出结果
class TestMathSum{
	//利用代码完成两个数相加
	public static void main(String[] args){

		//定义变量
		int n1 = 10;
		int n2 = 20;
		//求和
		int sum = n1 + n2;
		//输出结果
		System.out.println("结果=" + sum);
	}
}

class ChangeChar{
//学习转义符的使用
	public static void main(String[] args){
//		在命令行按tab键可快速实现命令补全
//		\t  ：一个制表位，实现对齐的跟随能
		System.out.println("北京\t天津\t上海");

//		\n  ：换行符
		System.out.println("jack\nsmith\tmary");

//		\\  ：一个\（斜杠）
		System.out.println("C:\\Windows\\System32\\cmd.exe");
		
//		\"  ：一个"（双引号）
		System.out.println("老韩说：\"要好好学习 Java，有前途。\"");

//		\'  ：一个'（单引号）
		System.out.println("老韩说：\'要好好学习 Java，有前途。\'");

//		\r  ：一个回车（回到句子初始）
		//解读：1.输出“韩顺平教育”；2.\r回车从头替换
		System.out.println("韩顺平教育\r北京"); //北京平教育
		System.out.println("韩顺平教育\r\n北京"); //韩顺平教育，北京
	}
}


/*
总结1：public class 名称需与 java 文件名一致，不得以数字开头
总结2：javac 将文件中每个类都编译为对应的 .class 的字节码文件，java 读取时文件名不需要带 ".class"
总结3：main方法也可写在非 public 的类中
总结4：JDK = JRE + java开发工具，JRE = JVM + 核心类库
*/

/*
初学Java易犯错误

1.找不到文件
解决方法：源文件名不存在或者写错，或者当前路径错误

2.主类名和文件名不一致
解决方法：声明为public的主类应与文件名一致，否知编译失败

3.缺少分号
解决方法：编译失败，注意错误出现的行数，再到源代码中指定位置改错。

4.常见错误总结
学习编程最容易犯的错是语法错误（初学者）。Java要求你必须按照语法规则编写代码。
如果你的程序违反了语法规则，例如：忘记了分号、大括号、引号，或者拼错了单词，java编译器都会报语法错误。尝试着去看懂编译器会报告的错误信息。

举例：1和l，0和o，中英文符号（分号，双引号），拼写错误
不好修改的错误其实是业务错误、环境错误

*/

/*
环境变量path配置及其作用
1. 环境变量的作用是为了在dos的任意目录，可以去使用java 和 javac命令
2. 先配置 JAVA_HOME = 指向jdk安装的主目录
3. 编辑path环境变量，增加 %JAVA_HOME%\bin 
*/
