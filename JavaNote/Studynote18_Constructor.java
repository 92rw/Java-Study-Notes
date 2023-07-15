/*构造器Constructor

●基本介绍
构造方法又叫构造器(constructor),是类的一种特殊的方法，它的主要作用是完成对新对象的初始化。

它有几个特点：
1)方法名和类名相同
2)没有返回值
3)在创建对象时，系统会自动的调用该类的构造器完成对像的初始化。

●基本语法
[修饰符] 方法名(形参列表) {
	方法体;
}

●说明：
1)构造器的修饰符可以默认，也可以是public protected private
2)构造器没有返回值
3)方法名和类名字必须一样
4)参数列表和成员方法一样的规则
5)构造器的调用，由系统完成

●使用细节
1.一个类可以定义多个不同的构造器，即构造器重载
比如：我们可以再给Person:类定义一个构造器，用来创建对象的时候，只指定人名，
不需要指定年龄
2.构造器名和类名要相同
3.构造器没有返回值
4.构造器是完成对象的初始化，并不是创建对象
5.在创建对象时，系统自动的调用该类的构造方法
6..如果没有定义构造方法，系统会自动给类生成一个默认无参构造器（也叫默认构造方法），
比如Person(){},可使用javap指令反编译查看对应的class文件
7.一旦定义了自己的构造器，默认的构造器就覆盖了，就不能再使用默认的无参构造器，除非显式的定义一下，即：Dog(){}
*/

//构造器的意义是，在创建多个对象时直接赋值，减少重复操作
//在创建人类的对象时，直接指定其年龄和姓名
class Constructor01 {
	public static void main(String[] args){
		//在创建新对象时，直接通过构造器指定名字和年龄
		ThisPerson p1 = new ThisPerson("smith",80);
		System.out.println("p1的细节如下");
		System.out.println("p1对象的name= " + p1.name);
		System.out.println("p1对象的age= " + p1.age);

		ThisPerson p2 = new ThisPerson("tom");
		System.out.println("p2的细节如下");
		System.out.println("p2对象的name= " + p2.name);
		System.out.println("p2对象的age= " + p2.age);

		ThisPerson p3 = new ThisPerson();
		System.out.println("p3的细节如下");
		System.out.println("p3对象的name= " + p3.name);
		System.out.println("p3对象的age= " + p3.age);
	}
}

class ThisPerson{
	String name;
	int age;

	//构造器
	//构造器直接初始化对象，没有返回值，也不能写void（方法必定有返回值和void）
	//构造器的名称必须和类名（Person）一样
	//形参列表的规则和成员方法一样
	public ThisPerson(String pName, int pAge) {
		System.out.println("\n构造器1被调用");
		name = pName;
		age = pAge;
	}

	//构造器重载
	public ThisPerson(String pName) {
		System.out.println("\n构造器2被调用");
		name = pName;
	}

	public ThisPerson(){
		System.out.println("\n构造器3被调用");
		age =18; //设置所有人的age属性都为18
	}
}



class ThisDog {
	public ThisDog(String dName){ //自己定义了一个构造器，覆盖系统默认构造器
	}
	ThisDog(){//想使用无参构造器，需要显式定义一下
	}
}


/*
案例
class Person{//类
	int age = 90;
	String name;
	Person(String n, int a){//构造器
		name = n;
		age = a;
	}
}

Person p = new Person("小倩",20);

流程分析（将字节码文件加载到内存中，进行内存解析的过程）
1.在方法区中加载Person类信息(Person.class)，只加载一次
2.在堆中分配空间(地址0x1122)
3.完成对象初始化
1）默认初始化，：age=0，name=null（属性均加载为默认值）
2）显式初始化：age=90，name=null
3) 构造器初始化：age=20，name="小倩"（储存在方法区）
4.将对象在对重的地址，返回给栈中的名称p

堆中的空间是真正的对象，p只是对象的引用（对象名）

*/
class This01{
	public static void main(String[] args){

		ThisCat cat1 = new ThisCat("tom",2);
		System.out.println("cat1的hashcode值为"+ cat1.hashCode());
		cat1.info();

		System.out.println("==============================");
		ThisCat cat2 = new ThisCat();
		System.out.println("cat2的hashcode值为"+ cat2.hashCode());
		cat2.info();

		System.out.println("==============================");
		cat2.info2();
	}
}

class ThisCat{//类
	//成员参数
	String name = "yolo";
	int age = 35;
	
	//构造器
/*
	public Cat(String dName, int dAge) {
		name = dName;
		age = dAge;
	此处构造器形参不能直接写成属性名，原因是
	public Cat(String name, int age){
	在方法体中声明的变量和方法的参数被称作局部变量，并且成员变量和局部变量在访问时遵循就近原则
	根据作用域原则，构造器的name和age都只是局部变量，而不是属性
	运行info时输出的是属性，属性未赋值因此将输出默认初始值
	==>使用this关键字解决
		this.name = name;
		this.age = age;
	//this指向当前变量的属性
*/

	public ThisCat(String name, int age){
		System.out.println("此处进入Cat(String name, int age)构造器");
		this.name = name;
		this.age = age;
		System.out.println("this的hashcode值为" + this.hashCode());
		//每个对象创建后都会有一个隐藏的this，this里存放着指向自己所在对象的地址
		//总结：哪个对象调用，this就代表哪个对象
	}

	public ThisCat(){//访问构造器的语法，只能在构造器中使用，且必须放在第一条语句
		this("jack",100);
		System.out.println("此处进入Cat()构造器");
	}

	//成员方法
	public void info(){
		System.out.println("this的hashcode值为" + this.hashCode());
		System.out.println(name + "\t" + age + "\t");
	}
	
	public void info2(){
		String name = "smith";
		System.out.println("name= "+ name + " age = "+ age);//根据就近原则，访问局部变量的信息
		System.out.println("name= "+ this.name + " age = "+ this.age);//this关键字用于准确访问本类的属性，不受局部变量影响
	}
}
/*
虚拟机会给每个对象分配this，this代表当前对象

使用细节
1.this关键字可以用来访问本类的属性、方法、构造器
2.this用于区分当前类的属性和局部变量
3.访问成员方法的语法：this.方法名(参数列表);
4.访问构造器语法：this(参数列表)：注意只能在构造器中使用
（即：只能在构造器中访问另外一个构造器，且this必须放在第一条语句）
5.this不能在类定义的外部使用，只能在类定义的方法中使用。

*/


/*
定义Person类，里面有name、age属性，并提供compareTo比较方法，
用于判断是否和另一个人相等，提供测试类TestPerson用于测试，
名字和年龄完全一致，就返回true,否则返回false

*/


class This02{
	public static void main(String[] args){

		ThisPersonInfo TestPerson = new ThisPersonInfo("mary",20);
		ThisPersonInfo p1 = new ThisPersonInfo("smith",20);
		ThisPersonInfo p2 = new ThisPersonInfo("mary",20);
		System.out.println(p1.compareTo(TestPerson));
		System.out.println(p2.compareTo(TestPerson));

	}
}

class ThisPersonInfo{
	String name;
	int age;
	public ThisPersonInfo(String name, int age){
		this.name = name;
		this.age = age;
	}
	public boolean compareTo(ThisPersonInfo p){
		return this.name.equals(p.name)  && this.age == p.age;
		//如果两个人名字一样，则公用一块内存空间，所以在此处的String也可以用==判断
	}
}	
