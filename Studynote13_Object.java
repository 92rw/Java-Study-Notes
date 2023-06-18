/*
单独变量来解决 =>不利于数据管理
数组 =>无法体现数据类型；只能通过[下标]获取信息，造成变量名字和内容的对应关系不明确

类与对象
1.类是抽象、概念的数据类型，包含属性、行为
2.对象是类的个体，一个具体的实例，创建/实例化对象
（Similar to数组：一个数组名只能指向一个具体数组，但是一个具体的数组可将地址赋给多个数组名；
名字不包含具体数据，只有地址才能访问数据）

对象在内存中的存在形式:名称/对象名/对象引用在栈，数据空间（属性、基本数据类型）在堆，引用类型（String类型）在方法区的常量池

类的信息（属性、行为）在方法区中


Java内存的结构分析
1.栈：一般存放基本数据类型（局部变量）
2.堆：存放对像(Cat cat,数组等)
3.方法区：常量池(常量，比如字符串)，类加载信息


*/


/*
1.从概念或叫法上看：成员变量=属性=fied（字段）（即成员变量用来表示属性）
案例演示：Car(name,price,color)
2.属性Properties是类的一个组成部分，一般是基本数据类型，也可是引用类型(对象，数组)。
比如我们前面定义猫类的int age就是属性

1)属性的定义语法同变量，示例：访问修饰符 属性类型 属性名;
访问修饰符：控制属性的访问范围
有四种访问修饰符public,proctected,默认，private



2)属性的定义类型可以为任意类型，包含基本类型或引用类型
3)属性如果不赋值，有默认值，规则和数组一致。
即：int 0,short 0, byte 0, long 0, float 0.0, double 0.0, char \u0000, boolean false,
String null
案例演示：[Person:类]

*/

/*
如何创建对象
1.先声明再创建
Cat cat;//声明Cat类的对象cat
cat = new Cat();创建

2.直接创建
Cat cat = new Cat()

2.直接创建

*/



//若两个名称指向同一对象，再对其中一个名称赋值null，则仅会给其中一个空指针异常，不会导致对象的消失


/*成员方法Method


*/
public class Studynote13_Object {
	public static void main(String[] args) {

		Person a = new Person();
		a.age = 10;
		a.name = "小明";
		Person b;
		b = a;
		System.out.println(b.name);
		a.speak();	//方法写好后，如果不去调用（使用），不会输出
		a.cal01();	//调用cal01方法
		a.cal02(5);	//调用cal02方法，同时n=5
		//调用getSum方法，同时num1=10,num2=20
		//把方法getSum返回的值，赋给变量returnRes
		int returnRes = a.getSum(10,20);
		System.out.println("getSum方法返回的值= " + returnRes);
		b.age = 200;
		b = null;
		System.out.println(a.age);
		System.out.println(b.age);

	}

}


/*

Java创建对象的流程简单分析
Person p new Person();
p.name="jack”；
p.age 10

1.先加载Person类信息(属性和方法信息，只会加载一次)
2.在堆中分配空间，进行默认初始化（看规则）
3.把地址赋给p,p就指向对象
4.进行指定初始化，比如p.name="jack" p.age=10

------------------------------------

Person p1 = new Person();
int returnRes = p1.getSum(10, 20);
System.out.println("getSum方法返回的值="+returnRes)

--其方法调用对应的代码为
	public int getSum(int num1, int num2) {
		int res = num1 + num2;
		return res;
	}

}
执行原理：
在栈中创建数据空间"main栈"
在栈中创建独立的数据空间"getSum栈"
将int值num1和num2拷贝到getSum栈中，运行得到res
结果返回到main栈中，getSum栈销毁
*/




class Person{
	//属性
	String name;
	int age;

	//成员方法
	//1.pub1ic表示方法是公开
	//2.void:表示方法没有返回值
	//3.speak():speak是方法名，()形参列表
	//4.{)方法体，可以写我们要执行的代码
	//5.System.out.println("我是一个好人")；表示我们的方法就是输出一句话
	public void speak() {
		System.out.println("我是一个好人");
	}

	////添加ca101成员方法，可以计算从1+...+1000的结果
	public void cal01() {
		//可以用循环语句
		int res = 0;
		for(int i = 1; i <=1000; i++) {
			res += i;
		}
		System.out.println("计算结果= " + res);
	}

	//添加ca102成员方法，该方法可以接收一个数n,计算从1+...+n的结果
	//1.(intn)形参列表,表示当前有一个形参，可以接收用户输入
	public void cal02(int n) {
		int res = 0;
		for(int i = 1; i <=n; i++) {
			res += i;
		}
		System.out.println("计算结果= " + res);
	}

	//添加getSum成员方法，可以计算两个数的和
	//1.访问修饰符pub1ic表示方法是公开的
	//2,int:表示方法执行后，返回一个int值
	//3.getSum方法名
	//4.(int num1,int num:2)形参列表，2个形参，可以接收用户传入的两个数
	//5.return res表示把res的值返回


	public int getSum(int num1, int num2) {
		int res = num1 + num2;
		return res;
	}

}

/*
成员方法
--------------------
定义
访问修饰符 返回数据类型 方法名(形参列表..){//方法体
语句;
return返回值;

1.参数列表：表示成员方法输入
2.返回数据类型：表示成员方法输出，Void表示没有返回值
3.方法主体：表示为了实现某一功能代码块
4.return语句不是必须的。


-------------------------
成员方法的好处
1.提高代码的复用性
2.可以将实现的细节封装起来，然后供其他用户来调用即可。

--------------------------
使用细节
访问修饰符（作用是控制方法使用的范围）
如果不写默认访问，[有四种：public, protected, 默认, private]/


返回数据类型
1.一个方法最多有一个返回值[使用数组可返回多个结果]
2.返回类型可以为任意类型，包含基本类型或引用类型(数组，对象)
3.如果方法要求有返回数据类型，则方法体中最后的执行语句必须为return值;而且要求
返回值类型必须和return的值类型一致或兼容
4.如果方法是void,则方法体中可以没有return语句，或者只写return;

方法名
在实际工作中，我们的方法都是为了完成某个功能，所以方法名要有一定含义，遵循驼峰命名法。
最好见名知义，表达出该功能的意思即可，比如得到两个数的和getSum,开发中按照规范

形参列表
1.一个方法可以有0个参数，也可以有多个参数，中间用逗号隔开，比如getSum(int n1,int n2)
2.参数类型可以为任意类型，包含基本类型或引用类型，比如printArr(int[][] map)
3.调用带参数的方法时，一定对应着参数列表传入相同类型或兼容类型的参数
4.方法定义时的参数称为形式参数，简称形参；方法调用时的参数称为实际参数，简称实参，实
参和形参的类型要一致或兼容、个数、顺序必须一致！

方法体
里面写完成功能的具体的语句，可以为输入、输出、变量、运算、分支、循环、方法调用，但
里面不能再定义方法！即：方法不能嵌套定义，

*/

/*
方法调用小结
1.当程序执行到方法时，就会开辟一个独立的空间（栈空间）
2.当方法执行完毕，或者执行到return语句时，就会返回，
3.返回到调用方法的地方
4.返回后，继续执行方法后面的代码
5.当main方法（栈）执行完毕，整个程序退出

方法调用细节说明
1.同一个类中的方法调用:直接调用即可。比如print(参数)
案例演示：A类sayOk调用print()


2.跨类中的方法A类调用B类方法：需要通过对象名调用。比如对象名。方法名（参
数）：案例演示：B类sayHello调用print（）


3.特别说明一下：跨类的方法调用和方法的访问修饰符相关，先暂时这么提一下，
后面我们讲到访问修饰符时，还要再细说。

*/
class MethodDetail {
	public static void main(String[] args){
		A a = new A();
		a.sayOK();//方法调用细节1
		a.m1();//方法调用细节2
		a.print(5,5,'△');
	}
}

class A {

	public void print(int n) {
		System.out.println("print方法被调用 n = " + n);//先输出这个
	}

	public void sayOK(){
		print(10);
		System.out.println("继续执行sayOK()~~~");//后输出这个
	}

	public void m1() {
		System.out.println("m1()方法被调用");
		//创建B对象，调用B类的方法
		B b = new B();
		b.hi();
		System.out.println("m1()方法继续执行");
	}

/*
根据行、列、字符打印对应行数和列数的字符，
比如：行：4，列：4，字符#，则打印相应的效果
####
####
####
####
*/

	public void print(int row, int col, char c) {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++){
				System.out.print(c);
			}
			System.out.println();
		}
	}
}


class B {
	public void hi() {
		System.out.println("B类中的 hi()被执行");//先输出这个
	}
}


/*
成员方法传参机制

基本数据类型，传递的是值（值拷贝），形参的任何改变不影响实参！
引用类型传递的是地址（传递也是值,但是值是地址），可以通过形参影响实参
*/

/*
编写一个方法copyPerson,可以复制一个Person对象，返回复制的对象。克隆对象，
注意要求得到新对象和原来的对象是两个独立的对像，只是他们的属性相同
*/
class MethodParameter{
	public static void main(String[] args){
		Person p = new Person();
		p.name = "milan";
		p.age = 22;
		myTools tools = new myTools();
		Person p2 = tools.Copyperson(p);
		System.out.println(p2 ==p);
		//下面的代码用于检验两个对象的String是否在同一方法区
		p2.name = "hanako";
		System.out.println(p2.name);
		System.out.println(p.name);		
		}
}

class Person {

	String name;
	int age;
}

class myTools {

	public Person Copyperson(Person p) {
		Person p2 = new Person();
		p2.name = p.name;
		p2.age = p.age;
		return p2;
	}
}


/*
作用域Scope

1.在java编程中，主要的变量就是属性（成员变量）和局部变量，
2.我们说的局部变量一般是指在成员方法中定义的变量。
3.java中作用域的分类
全局变量：也就是属性，作用域为整个类体。定义时可直接赋值
局部变量：也就是除了属性之外的其他变量，作用域为定义它的代码块中
4.全局变量（属性）可以不赋值，直接使用，因为有默认值，局部变量必须赋值后才能使用，因为没有默认值。

注意事项
1.属性和局部变量可以重名，访问时遵循就近原则。
2.在同一个作用域中，比如在同一个成员方法中，两个局部变量，不能重名。[举例
3.属性生命周期较长，伴随着对象的创建而创建，伴随着对象的销毁而销毁。
局部变量，生命周期较短，伴随着它的代码块的执行而创建，伴随着代码块的结束而销毁。即在一次方法调用过程中。
4.作用域范围不同
全局变量/属性：可以被本类使用，或其他类使用（通过对象调用）
局部变量：只能在本类中对应的方法中使用
5.修饰符不同
全局变量/属性可以加修饰符
局部变量不可以加修饰符

方法中的同名变量的声明会使得同名的成员变量在该方法中被屏蔽
*/