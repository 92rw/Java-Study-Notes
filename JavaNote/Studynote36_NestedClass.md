# 嵌套类/内部类

类的五大成员：属性、方法、构造器、代码块、内部类

```java
package 包名;
class 类名 extends 父类 implements 接口名{
    成员变量/属性;
    构造方法/构造器;
    成员方法/方法;
    代码块;
}
```

## 内部类

定义在类里面的类。最大的特点就是可以直接访问私有属性，并且可以体现类与类之间的包含关系

基本语法

```java
class Outer{//外部类
    class Inner{//内部类
        
    }
}

class Other{}//其他类
```

应用场景

* 内部类方法可以访问该类定义所在作用域中的数据，包括被 private 修饰的私有数据
* 内部类可以对同一包中的其他类隐藏起来，隐藏接口的实现类
* 内部类可以解决 java 单继承的缺陷
* 使用匿名内部类定义回调函数

### 分类

定义在外部类局部位置上（比如方法内）：
1）局部内部类（有类名）
2）匿名内部类（没有类名，重点）

定义在外部类的成员位置上：
1）成员内部类（没有static修饰）
2）静态内部类（使用static修饰）

|                | 局部内部类                                 | 匿名内部类                       | 成员内部类                                                   | 静态内部类                                                |
| -------------- | ------------------------------------------ | -------------------------------- | ------------------------------------------------------------ | --------------------------------------------------------- |
| 定义位置       | 外部类局部位置（方法体或代码块）           | 外部类局部位置（方法体或代码块） | 外部类的成员位置（没有static修饰）                           | 外部类的成员位置（使用static修饰）                        |
| 访问外部类     | 直接访问                                   | 直接访问                         | 直接访问                                                     | 直接访问（仅限静态成员）                                  |
| 外部类访问     | 创建对象，调用方法（注意：必须在作用域内） | 不能访问                         | 创建对象，再访问                                             | 创建对象，再访问                                          |
| 其他类访问     | 不允许                                     | 不能访问                         | ①外部类. new 成员内部类(); ②外部类编写方法返回成员内部类实例 | ①new 外部类.静态内部类()②外部类编写方法返回静态内部类实例 |
| 作用域         | 定义的方法体或代码块中                     | 定义的方法体或代码块中           | 整个外部类                                                   | 整个外部类                                                |
| 访问权限修饰符 | 不能设置                                   | 不能设置                         | 可以设置                                                     | 可以设置                                                  |



## 局部内部类

说明：局部内部类是定义在外部类的局部位置，比如方法中，并且有类名

* 可以直接访问外部类的所有成员，包含私有的
* 不能添加访问权限修饰符public private protected，因为它的地位就是一个局部变量。
* 可以使用final修饰，因为局部变量也可以使用final
* 如果外部类和局部内部类的成员重名时，默认遵循就近原则，如果想访问外部类的成员，则可以使用 `外部类名.this.成员` 去访问
  

```java
/*
练习实例1
1.编一个类A，在类中定义局部内部类B，B中有一个私有常量 NAME ，有一个方法show()打印常量name。进行测试
2.进阶：A中也定义一个私有的变量 name，在show方法中打印测试
 */

class LocalA{
    private String name = "variable A";
    public void f1(){
        class B{
            private final String NAME = "constant B";//常量用 final 修饰，用大写命名
            public void show(){
                //如果有属性重名，需要 外部类名.this.属性名
                System.out.println("局部内部类 NAME = " + NAME + "，外部类 name = " + name);
            }
        }
        B b = new B();//局部内部类需要在方法体中创建此类，否则无法调用内部类的功能
        b.show();
    }
}
class LocalAExercise{
    public static void main(String[] args) {
        new LocalA().f1();
    }
}
```



## 匿名内部类 Anonymous Class

说明：匿名内部类是定义在外部类的局部位置，比如方法中，并且没有类名

匿名内部类的基本语法

```java
new 类或接口名(参数列表){
	类体;
};
```

- 匿名内部类是没有访问修饰符的。
- 匿名内部类必须继承一个抽象类或者实现一个接口
- 匿名内部类中不能存在任何静态成员或方法
- 匿名内部类是没有构造方法的，因为它没有类名。
- 与局部内部类相同，匿名内部类也可以引用局部变量。此变量也必须声明为 final
- 匿名内部类作为实参的时候看成对象，接收后动态绑定匿名内部类
- 如果外部类和内部类的成员重名时，内部类访问的话，默认遵循就近原则，如果想访问外部类的成员，则可以使用（外部类名.this.成员）去访问
- 匿名内部类需要分号，因为匿名内部类需要立即创建对象，创建对象语句是需要分号的

```java
interface USB {//接口
    void use();
}

class NestedAnonymousExample1 {
    //静态方法，形参是接口类型
    public static void f1(USB usb){
        usb.use();
    }
    
    public static void main(String[] args) {
        //匿名内部类可以当做实参直接传递，简洁高效
        f1(new USB() {//这个new的意思是匿名内部类,底层意思其实是弄了一个类来实例化这个匿名内部类的，相当于直接传入一个对象
            @Override
            public void use() {
                System.out.println("直接调用 USB 接口");
                System.out.println(this.getClass());
            }
        });
        //传统方法：需要新建类才能实现接口调用
        f1(new USB3());
    }
}

/*
匿名内部类的最佳实践：当做实参直接传递。
应该便于那些复用性不高的类，创建会浪费资源，不如一次性的，用完就丢/同时都需要改的话，硬编码改一个都改了，两种方法优劣不一样，根据情况使用
 */
class USB3 implements USB {
    //这种方式被称作硬编码
    @Override
    public void use() {
        System.out.println("新建外部类实现 USB 接口");
        System.out.println(this.getClass());
    }
}
//使用匿名内部类可以省略新建这个类的过程，直接在运行过程中完成调用
```

在 Java 编译器编译后，匿名内部类被编译为 "外部类名$数字"，编号从1开始



## 成员内部类 Inner Class

说明：成员内部类定义在外部类的成员位置(不在代码块和其他方法中)，并没有 static 修饰

* 可以直接访问外部类的所有成员，包含私有的
* 可以添加任意访问修饰符（public、protected、默认、private）因为它的地位就是一个成员。
* 作用域：和外部类的其他成员一样，为整个类体
* 如果外部类和内部类的成员重名时，内部类访问的话，默认遵循就近原则，如果想访问外部类的成员，则可以使用（外部类名.this.成员）去访问

```java
public class Main {
    public static void main(String[] args) {
        Outer outer = new Outer("Nested"); // 实例化一个Outer
        Outer.Inner inner = outer.new Inner(); // 实例化一个Inner
        inner.hello();
    }
}

class Outer {
    private String name;

    Outer(String name) {
        this.name = name;
    }

    class Inner {
        void hello() {
            System.out.println("Hello, " + Outer.this.name);
        }
    }
}

```

在 Java 编译器编译后的`.class`文件中，Outer类被编译为`Outer.class`，而`Inner`类被编译为`Outer$Inner.class`。

## 静态内部类 Static Nested Class

说明：静态内部类是定义在外部类的成员位置，并且有static修饰

* 可以直接访问外部类的所有静态成员，包含私有的，但不能直接访问静态成员
* 可以添加任意访问修饰符（public、protected、默认、private），不依赖所在的类
* 如果外部类和静态内部类的成员重名时，静态内部类访问的时，默认遵循就近原则，如果想访问外部类的成员，则可以使用（外部类名成员）去访问

[静态嵌套类（Static-Nested-Class）和内部类（Inner-Class）的不同？](https://blog.eurkon.com/post/11ea51ea.html#静态嵌套类（Static-Nested-Class）和内部类（Inner-Class）的不同？)：静态嵌套类在实例化时可以不依赖于外部类；非静态的内部类在创建时需要依赖其外部类对象，因此外部类的静态方法需要先创建外部类实例再调用内部类的构造方法



参考资料

[java内部类的四大作用_奋斗的bigHead的博客-CSDN博客](https://blog.csdn.net/u013728021/article/details/87358517)
