

class beginner{
	public static void main(String[] args){
		//第一题的相关代码
		A01 a01 = new A01();
		double[] arr = {};
		Double res = a01.max(arr);
		if(res != null){
			System.out.println("arr的最大值为 " + res);
		} else{
			System.out.println("arr的输入有误，数组不能为null或{}");
		}

		//第二题的相关代码
		A02 a02 = new A02();
		String[] strs = {"parsley", "sage", "rosemary", "thyme"};
		Integer index = a02.find("sampoo",strs);
		if(index != null){
			if(index != -1)	System.out.println("字符串中存在此元素，其编号为 " + index);
			else System.out.println("字符串中不存在此元素");
			
		} else{
			System.out.println("strs的输入有误，数组不能为null或{}");
		}

		//第三题相关代码
		Book book = new Book("古剑奇谭", 200);
		book.info();
		book.updatePrice();//更新价格
		System.out.println("进行价格更新");
		book.info();
	}
}

//第一题：编写类A01,定义方法ma×,实现求某个double数组的最大值，并返回
class A01{
	//引入包装类Double
	public Double max(double[] arr){
		//处理异常，考虑空对象及长度为0
		if(arr != null && arr.length > 0){
			double max = arr[0];
			for(int i = 0; i < arr.length; i++){
				if(arr[i] > max) max = arr[i];
				}
			return max;
		} else{//若输入的数组为空
			return null;
		}
	}
}


//第二题：编写类A02,定义方法find,实现查找某字符串数组中的元素查找，并返回索引，如果找不到，返回-1
class A02{
	public Integer find(String findStr,String[] strs){
		if(strs != null && strs.length > 0){
			for(int i = 0; i < strs.length; i++){
				if(findStr.equals(strs[i])){
					return i;
				}
			}
			//如果没有，就返回-1
			return -1;
		} else{
			return null;
		}
	}
}

/*第三题：编写类Book,定义方法updatePrice,实现更改某本书的价格，
具体：如果价格>150，则更改为150，如果价格>100，更改为100,否则不变；
*/
class Book{
	String name;
	double price;
	public Book(String name, double price) {
		this.name = name;
		this.price = price;
	}
	public void updatePrice(){
		//如果方法中没有 price 局部变量，this.price 等价于 price
		if(this.price > 150) {
			this.price = 150;
		} else if(this.price > 100){//上面那段代码已经筛选掉了大于150的数
			this.price = 100;
		}
	}

	//显示书籍的情况
	public void info(){
		System.out.println("书名=" + this.name +" 价格=" + this.price);
	}
} 

//编写类AO4,实现数组的复制功能copyArr,输入旧数组，返回一个新数组，元素和旧数组一样
class A04{
	public int[] copyArr(int[] oldArr){
		//在堆中开辟空间
		int[] newArr = new int[oldArr.length];
		for(int i = 0; i < oldArr.length; i++){
			newArr[i] = oldArr[i];
		}
		return newArr;
	}
}



//编程创建一个Calc计算类，在其中定义2个变量表示两个操作数，定义四个方法实现求和、差、乘、商(要求除数为的话，要提示)
class Calc{
	double num1;
	double num2;
	public Calc(double num1, double num2){
		this.num1 = num1;
		this num2 = num2;
	}

	public double sum(){
		return num1 + num2;
	}

	public double minus(){
		return num1 - num2;
	}

	public double multifly(){
		return num1 * num2;
	}

	public Double divide(){
		if(num2 == 0){
			System.out.println("除数不能为零");
			return null;
		} else{
			return num1 / num2;
		}
	}
}

/*
创建一个Employee类，
属性有(名字，性别，年龄，职位，薪水)，
提供3个构造方法，可以初始化
(1)(名字，性别，年龄，职位，薪水)，
(2)(名字，性别，年龄)
(3)(职位，薪水)，要求充分复用构造器
*/
class Employee {
	//成员属性
	String name;
	char gender;
	int age;
	String job;
	double sal;
	
	//因为要求复用构造器，因此先写属性少的
	//职位，薪水
	public Employee(String job, double sal){
		this.job = job;
		this.sal = sal;
	}

	//名字，性别，年龄
	public Employee(String name, char gender, int age){
		this.name = name;
		this.gender = gender;
		this.age = age;
	}

	public Employee(String name, char gender, int age, String job, double sal){
		this(name, gender, age);//构造器调用只能使用一次
		this.job = job;
		this.sal = sal;		
	}	
}

/*将对象作为参数传递给方法。
题目要求：
(1)定义一个 Circle 类，包含一个 double 型的 radius 属性代表圆的半径，findArea()方法返回圆的面积。
(2)定义一个类 PassObject ,在类中定义一个方法 printAreas(),该方法的定义如下：
public void printAreas(Circle c,int times)
/方法签名/声明
(3)在printAreas()方法中打印输出1到times之间的每个整数半径值，以及对应的面积。
例如，times为5，则输出半径1,2,3,4,5，以及对应的圆面积。
(4)在main方法中调用printAreas()方法，调用完毕后输出当前半径值。
*/
import java.lang.Math;
class PassPara{
	public static void main(String[] args){
		Circle c = new Circle();
		PassObject po = new PassObject();
		po.printAreas(c, 5);
	}
}

class Circle{
	double radius;//半径
	//因为在圆这个类里已经声明了成员变量，类里的方法可以直接调用这个变量，不需要形参
	//只需要在含参构造器里有就可以了，然后创建对象的时候把参数传给构造器，方法就能不需要参数就能调用了
	//因为创建的时候有参数的构造器把默认无参构造器覆盖掉了，就没法创建无参对象
	//写一个无参构造器是因为，有参构造器的创建会覆盖无参构造器，这也算是构造器的重载
	public Circle(){}
	public Circle(double radius) {
		this.radius = radius;
	}
	//面积
	public double findArea(){
		return Math.PI * radius * radius;
	}
	//添加方法setRadius，用于修改对象的半径值
	public void setRadius(double radius) {
		this.radius = radius;
	}
}

class PassObject{
	public void printAreas(Circle c,int times){
		System.out.println("radius\tarea");
		for(int i = 1; i <= times; i++){
			//每次new一个新circle浪费资源，因此复用
			c.setRadius(i);
			System.out.println((double)i + "\t" + c.findArea());
		}
	}
}
