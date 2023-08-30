# 设计模式

设计模式是在大量的实践中总结和理论化之后优选的代码结构、编程风格、以及解决问题的思考方式。

工厂模式是一种选择器形式，其保证了使用抽象类去包含无数需要创建的实例，而单例模式是只操作一个类。可以理解为，工厂模式是单例模式的升级

## 单例设计模式

单例设计模式，是静态方法和属性的经典使用。就是采取一定的方法保证在整个的软件系统中，对某个类只能存在一个对象实例，并且该类只提供一个取得其对象实例的方法。

所以，单例模式的实现方式：

1. 只有`private`修饰的构造器，确保外部无法实例化；
2. 创建`private static`变量，持有唯一实例，保证全局唯一性；
3. 通过`public static`方法返回此唯一实例，使外部调用方能获取到实例。



2.单例模式有两种方式：①饿汉式②懒汉式

* 二者最主要的区别在于创建对象的时机不同：饿汉式是在类加载就创建了对象实例而懒汉式是在使用时才创建
* .饿汉式不存在线程安全问题，懒汉式存在线程安全问题。（多线程同时创建对象时，会创建多个对象，破坏单例模式）
* 饿汉式存在浪费资源的可能。因为如果程序员一个对象实例都没有使用，那么饿汉式创建的对象就浪费了，懒赖汉式是便用时才创建，就不存在这个同题。

### 饿汉式

优点：访问速度快，线程安全

缺点：提前构建实例，造成资源浪费

```java
public class HungrySingleton {
	public static void main(String[] args) {
//		Bank bank = new Bank(); 
        
		Bank bank1 = Bank.getInstance();
		Bank bank2 = Bank.getInstance();
		System.out.println(bank1 == bank2);		
	}
}

//单例的饿汉式
class Bank{
	//1.私有化类的构造器
	private Bank(){}
	
	//2.内部创建类的对象，要求此对象也必须声明为静态的
	private static Bank instance = new Bank();
	
	//3.提供公共的静态的方法，返回类的对象。
	public static Bank getInstance(){
		return instance;
	}
}
```



### 懒汉式

优点：节省内存资源

缺点：线程不安全

```java
public class LazySingleton {
	public static void main(String[] args) {
		Order order1 = Order.getInstance();
		Order order2 = Order.getInstance();
		
		System.out.println(order1 == order2);
	}
}

class Order{
	//1.私有化类的构造器
	private Order(){}
	
	//2.声明当前类对象，没有初始化，此对象也必须声明为 static 的
	private static Order instance = null;
	
	//3.声明 public、static 的返回当前类对象的方法
	public static Order getInstance(){
		if(instance == null){
			instance = new Order();			
		}
		return instance;
	}
}
```



### 应用场景

* 网站的计数器，一般也是单例模式实现，否则难以同步。
* 应用程序的日志应用，一般都使用单例模式实现，这一般是由于共享的日志文件一直处于打开状态，因为只能有一个实例去操作，否则内容不好追加。
* 数据库连接池的设计一般也是采用单例模式，因为数据库连接是一种数据库资源。
* 读取配置文件的类，一般也只有一个对象。没有必要每次使用配置文件数据，都生成一个对象去读取
* Windows 的 **Task Manager (任务管理器)**就是很典型的单例模式
* Windows 的 **Recycle Bin(回收站)**也是典型的单例应用。在整个系统运行过程中，回收站一直维护着仅有的一个实例。
* 在javaSE标准类中，java.lang.Runtime就是经典的单例模式



### 单例模式的升级

参考资料：[单例模式 | 蝉沐风 (chanmufeng.com)](https://www.chanmufeng.com/posts/basic/design-pattern/单例模式.html)

