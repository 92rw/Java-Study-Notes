# static修饰符

## 类变量

类变量也叫静态变量/静态属性，是该类的所有对象共享的变量，任何一个该类的对象去访问它时，取到的都是相同的值，同样任一个该类的对象去修改它时，修改的也是同一个变量。

内存位置

* static变量在jdk8以及以前，放在方法区的静态域
* jdk8以后放在堆中（class对象），当你的类加载的时候会在堆生成一个类的class对象
*  共识：
  * static变量由同一个类所有对象共享
  * 在类加载的时候生成
  * 类只会加载一次，则静态变量在内存中也只会存在一次

```java
//基本形式
访问修饰符 static 数据类型 变量名;//推荐
static 访问修饰符 数据类型 变量名;

//访问方式
类名.类变量名;//推荐
对象名.类变量名;
//说明：静态变量的访问修饰符的访问权限和范围和普通属性是一样的
```

注意事项：

1. 使用场景

  当我们需要让某个类的所有对象都共享一个变量时，就可以考虑使用类变量（静态变量）

  比如：定义学生类，统计所有学生共交多少钱。Student（name，fee）

2. 类变量与实例变量（普通属性）区别

  类变量是该类的所有对象共享的，而实例变量是每个对象独享的。

3. 加上static称为类变量或静态变量，否则称为实例变量/普通变量/非静态变量

4. 类变量可以通过 `类名.类变量名` 或者 `对象名.类变量名` 来访问

  但java设计者推荐我们使用 `类名.类变量名` 方式访问。【前提是满足访问修饰符的访问权限和范围】

5. 实例变量不能通过 `类名.类变量名` 方式访问

6. 类变量是在类加载时就初始化了，也就是说，即使你没有创建对象，只要类加载了，就可以使用类变量了。

7. 类变量的生命周期是随类的加载开始，随着类消亡而销毁。


构造器不是创建对象 是对对象进行初始化



## 类方法/静态方法



```java
//基本形式
访问修饰符 static 数据返回类型 方法名() {};//推荐
static 访问修饰符 数据返回类型 方法名() {};

//调用方式
类名.类方法名;
对象名.类方法名;
//说明：前提是满足访问修饰符的访问权限和范围
```



类方法经典的使用场景

* 当方法中不涉及到任何和对象相关的成员，则可以将方法设计成静态方法，提高开发效率
* 比如：工具类中的方法utils。Math类、Arrays类、Collections集合类
* 在程序员实际开发，往往会将一些通用的方法，设计成静态方法，这样我们不需要创建对象就可以使用
  了，比如打印一维数组，冒泡排序，完成某个计算任务等

使用细节

* 类方法和普通方法都是随看类的加载而加载，将结构信息存储在方法区：

  * 类方法中无this的参数
  * 普通方法中隐含着this的参数

* 类方法可以通过类名调用，也可以通过对象名调用。

  * 普通方法和对象有关，需要通过对象名调用，比如 对象名.方法名（参数），不能通过类名调用。

* 类方法中不允许使用和对象有关的关键字，比如this和super。普通方法（成员方法）可以
  * 静态方法是早于对象在内存里创建的，如果使用this或super的话，会找不到这个对象
  
* 类方法（静态方法）中只能访问静态变量或静态方法。
  
* 普通成员方法，既可以访问普通变量（方法），也可以访问静态变量（方法）。

小结：静态方法，只能访问静态的成员，非静态的方法，可以访问静态成员和非静态成员（必须遵守访问权限）



## 代码块

代码化块又称为初始化块，属于类中的成员[包装类（enclosing class）/外部类的一部分]，类似于方法，将逻辑语句封装在方法体中，通过{}包围起来

但和方法不同，没有方法名，没有返回，没有参数，只有方法体，而且不用通过对象或类显式调用，而是加载类时，或创建对象时隐式调用。

代码块其实就是匿名方法，本质是还是方法

* 相当于另外一种形式的构造器（对构造器的补充机制，可以做初始化的操作
* 应用场景：多个构造器中都有重复的语包，可以抽取到初始化块中，提高代码的重用性
* 代码块的调用优先于构造器

```java
//基本语法
[修饰符] {
 代码
};
```

说明：

* 修饰符 可选，要写的话，也只能写static
* 代码块分为两类，使用static修饰的叫静态代码块，没有static修饰的，叫普通代码块/非静态代码块
* 逻辑语句可以为任何逻辑语句（输入、输出、方法调用、循环、判断等）
* ;号可以写上，也可以省略。

注意事项：

* static代码块也叫静态代码块，作用就是对类进行初始化，而且它随着类的加载而执行，
  并且只会执行一次。如果是普通代码块，每创建一个对象，就执行一次
* 普通的代码块，在创建对象实例时，会被隐式的调用。被创建一次，就会调用一次。
  如果只是使用类的静态成员时，普通代码块并不会执行
* 创建一个对象时，在一个类调用顺序是：（重点，难点）：
  1. 调用静态代码块和静态属性初始化（注意：静态代码块和静态属性初始化调用的优先级一样，
     如果有多个静态代码块和多个静态变量初始化，则按他们定义的顺序调用）
  2. 调用普通代码块和普通属性的初始化（注意：普通代码块和普通属性初始化调用的优先级一样，
     如果有多个普通代码块和多个普通属性初始化，则按定义顺序调用)
  3. 调用构造方法

* 构造器的最前面其实隐含了super()和调用普通代码块。
  静态相关的代码块、属性初始化，在类加载时，就执行完毕，因此是优先于构造器和普通代码块执行的

* 创建一个子类时（继承关系），他们的静态代码块，静态属性初始化，普通代码块，普通属性初始化，构造方法的调用顺序如下：
  1. 父类的静态代码块和静态属性（优先级一样，按定义顺序执行）
  2. 子类的静态代码块和静态属性（优先级一样，按定义顺序执行）
  3. 父类的普通代码块和普通属性初始化（优先级一样，按定义顺序执行）
  4. 父类的构造方法
  5. 子类的普通代码块和普通属性初始化（优先级一样，按定义顺序执行）
  6. 子类的构造方法

* 静态代码块只能直接调用静态成员（静态属性和静态方法），普通代码块可以调用任意成员。

[类加载的时机](Studynote88_LoadClass.md)
