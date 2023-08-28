# JDK，JRE

### JDK 基本介绍

1. JDK 的全称(Java Development Kit Java 开发工具包)
2. JDK = JRE + java 的开发工具[java, javac,javadoc,javap 等]
3. JDK 是提供给Java 开发人员使用的，其中包含了java 的开发工具，也包括了JRE。

### JRE 基本介绍

1. JRE(Java Runtime Environment Java 运行环境) JRE = JVM + Java 的核心类库[类]
2. 包括Java 虚拟机(JVM Java Virtual Machine)和Java 程序所需的核心类库等，如果想要运行一个开发好的Java 程序，计算机中只需要安装JRE 即可。

### JDK、JRE 和JVM 的包含关系

1. JDK = JRE + 开发工具集（例如Javac,java 编译工具等)
2. JRE = JVM + Java SE 标准类库（java 核心类库）
3. 如果只想运行开发好的.class 文件只需要JRE



# 编写Java程序

```java
//源文件的基本组成是 class（类），但只能有一个 public class
//应用程序的执行入口是 main() 方法，每个语句以 ";" 结束
public class Hello {
	public static void main(String[] args){
		System.out.println("Hello,world");
	}
}
/*对代码的相关说明
1. public class Hello 表示 Hello 是一个 public 的类
2. Hello{} 表示一个类的开始和结束
3. public static void main(String[] args) 表示一个主方法，即我们程序的入口
4. main(){} 表示方法的开始和结束
5. System.out.println("Hello,world"); 表示输出 "Hello,world" 到屏幕
6. “;”表示语句结束
*/
```

总结

1. public class 名称需与 java 文件名一致，不得以数字开头

2. javac 将文件中每个类都编译为对应的 .class 的字节码文件，java 读取时文件名不需要带 ".class"

3. main方法也可写在非 public 的类中

4. 源文件的基本组成是 class（类），但只能有一个 public class

   


## Java代码规范

1. 类、方法的注释，要以 javadoc （文档注释）方式来写。
2. 非 javadoc 的注释，往往是给代码的维护者看的，着重告述读者为什么这样写，如何修改，注意事项等
3. 使用 tab 按键，实现缩进，默认整体向右边移动；可用 shift + tab 整体向左移
4. 运算符和 = 两边习惯性各加一个空格。比如：2 + 4 * 5 + 345 - 89
5. 源文件使用 utf-8 编码
6. 行宽度不要超过 80 字符
7. 代码编写分为 次行风格 和 行尾风格（推荐使用） 



## 初学Java易犯错误

1. 找不到文件
   * 解决方法：源文件名不存在或者写错，或者当前路径错误 
2. 主类名和文件名不一致
   * 解决方法：声明为public的主类应与文件名一致，否知编译失败
3. 缺少分号
   * 解决方法：编译失败，注意错误出现的行数，再到源代码中指定位置改错。
4. 常见错误总结
   * 学习编程最容易犯的错是语法错误（初学者）。Java要求你必须按照语法规则编写代码。
   * 如果你的程序违反了语法规则，例如：忘记了分号、大括号、引号，或者拼错了单词，java编译器都会报语法错误。尝试着去看懂编译器会报告的错误信息。
   * 举例：1和l，0和o，中英文符号（分号，双引号），拼写错误
   * 不好修改的错误其实是业务错误、环境错误





```java
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
//		System.out.println("老韩说：\"要好好学习 Java，有前途。\"");

//		\'  ：一个'（单引号）
		System.out.println("老韩说：\'要好好学习 Java，有前途。\'");

//		\r  ：一个回车（回到句子初始）
		//解读：1.输出“韩顺平教育”；2.\r回车从头替换
		System.out.println("韩顺平教育\r北京"); //北京平教育
		System.out.println("韩顺平教育\r\n北京"); //韩顺平教育，北京
	}
}
```

