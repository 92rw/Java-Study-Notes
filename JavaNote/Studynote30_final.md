# final修饰符

可以修饰 `类、属性、方法`，表示修饰对象不可变

* final修饰类表示不可被继承
* final修饰方法表示不可被重写
* final修饰属性，表示不可被修改：基本类型值不可变，对象属性引用的地址不可变

在某些情况下，程序员可能有以下需求，就会使用到final：

1. 当不希望类被继承时

2. 当不希望父类的某个方法被子类覆盖/重写（override）时，可以用final关键字修饰
   * 访问修饰符 final 返回类型 方法名

3. 当不希望类的的某个属性的值被修改,可以用final修饰
   * public final double TAX_RATE = 0.08
   * private是访问修饰符，你只是不能直接访问修改，通过提供的方法可以修改；final是直接不能修改
   * 虽然private不提供set方法也可以做到让人无法修改，但是private的属性和方法没办法被子类调用

4. 当不希望某个局部变量被修改，可以使用final修饰

```java
public class FinalErrorTest {
    // 定义一个final修饰的实例变量
    // 系统不会对final成员变量进行默认初始化
	final int age;
	{
        System.out.println("初始化块执行");
        // age没有初始化，所以此处代码将引起错误。
    //        System.out.println(age);
        printAge();  //合法，会访问到age变量，值为默认值0
        age = 6;
        System.out.println(age);
    }
    public void printAge(){
        System.out.println(age);
    }
    public static void main(String[] args) {
        new FinalErrorTest();
	}
}
```



注意事项：

1. final修饰的属性又叫常量，一般用XX_XX_XX来命名，必须指定初始值
2. final修饰的属性在定义时必须赋初值，以后不能再修改，赋值可以在如下位置之一
   * 定义时：如 public final double TAX_RATE=0.08
   * 在构造器中
   * 在代码块中

3. 如果fina修饰的属性是静态的，则初始化的位置只能是
   * 定义时
   * 在静态代码块中
     * 静态属性是在调用【类】的时候创建的，而构造器的调用需要创建【对象实例】
     * 构造器不可以是静态的，因为构造器中隐含了this和super

4. final修饰的类，不能被继承，可以实例化；final修饰的函数不能被重写，可以被继承
5. 一般来说，如果一个类已经是final类了，就没有必要再将方法修饰成final方法
6. final不能修饰构造方法（即构造器）
7. final和static往往搭配使用，效率更高，不会导致类加载。底层编译器做了优化处理
8. 包装类（lntegerDouble，Float，Boolean等都是final），String也是 final 类



```java
//当定义一个final形参时，可保证这个形参的值在方法体运行中不被改变，无法被重新赋值
//重新赋值会提示编译错误：Cannot assign a value to final variable
public int addOne(final int x) {
    return ++x; // return x + 1;
}
```

补充说明：

1. 重写的前提是继承，如果父类的方法设置了 `private final` 修饰符，那么子类的同名同参方法不构成重写，而是定义新方法
2. final修饰的变量，只能在定义处或构造器赋值，无法通过方法修改
3. final修饰的方法，可以被继承但不能被任何类重写



### 有初始值的final变量

当定义final变量时就为该变量指定了初始值，而且该初始值可以在编译时就确定下来，那么这个final变量本质上就是一个宏变量，编译器会把程序中所有用到该变量的地方直接替换成该变量的值。

如果被赋的表达式只是基本的算术表达式或字符串连接运算，没有访问普通变量，调用方法，Java编译器同样会将这种final变量当成“宏变量”处理。示例如下：

```java
public class FinalReplaceTest{
	public static void main(String[] args){
        // 下面定义了4个final“宏变量”
        final int a = 5 + 2;
        final double b = 1.2 / 3;
        final String str = "胡" + "啊呦";
        final String book = "Java核心技术：" + 99.0;
        // 下面的book2变量的值因为调用了方法，所以无法在编译时被确定下来
        final String book2 = "Java核心技术：" + String.valueOf(99.0);  //①
        System.out.println(book == "Java核心技术：99.0");  //true
        System.out.println(book2 == "Java核心技术：99.0"); //false
	}
}

```

示例中，即使字符串连接运算中包含隐式类型（将数值转换成字符串）转换，编译器依然可以在编译时就确定a，b，str，book这4个变量的值，因此它们都是“宏变量”。

定义book2变量时显式使用方法将数值99.0转换为字符串，但由于该变量的值需要调用String类的方法，因此编译器无法编译时确定book2的值，book2不会被当成宏变量处理。

book是一个宏变量，他将被直接替换成"Java核心技术：99.0"，所以第一个判断为true，相等。book2则不相等。



参考资料

[全面理解Java中的final修饰符_胡啊呦的博客-CSDN博客](https://blog.csdn.net/hxhaaj/article/details/81334461)

[一文彻底搞懂 Java final 关键字 | Java程序员进阶之路 (tobebetterjavaer.com)](https://tobebetterjavaer.com/oo/final.html#_02、final-方法)
