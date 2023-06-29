/*
继承Extends
●继承可以解决代码复用，让我们的编程更加靠近人类思维
当多个类存在相同的属性（变量）和方法时，可以从这些类中抽象出父类，在父类中定义这些相同的属性和方法，
所有的子类不需要重新定义这些属性和方法，只需要通过extends来声明继承父类即可。

//实际开发时需要用封装来限制调用方的权限，在子类提供访问方式

●继承的基本语法
class 子类 extends 父类{
}

子类就会自动拥有父类定义的属性和方法
父类/基类/超类->子类/派生类

●继承给编程带来的便利
1)代码的复用性提高了
2)代码的扩展性和维护性提高了

●细节问题
1.子类继承了所有的属性和方法，非私有属性和方法可以在子类直接访问
但是私有属性和方法不能在子类直接访问，要通过父类提供公共的方法去访问
public：哪都能实例访问还是继承
protected：同包可实例访问和继承；不同包不能访问能继承
默认：同包可实例访问和继承，而不同包不能访问不能继承
private：只能同类访问 ->要通过(父类)提供的公共方法访问

2.子类必须调用父类的构造器，完成父类的初始化
因为要初始初始化和加载，对象的本质就是在调用构造，因为俩者是继承关系，所以同时加载
因为父类中的数据子类可以直接获取,所以子类对象在建立时,需要先查看父类是如何对这些数据进行初始化

3.当创建子类对象时，不管使用子类的哪个构造器，默认情况下总会去调用父类的无参构造器，
如果父类没有提供无参构造器，则必须在子类的构造器中用super去指定使用父类的哪个构造器完成对父类的初始化工作，否则，编译不会通过
1）子类如果想初始化对象，就得先让父类初始化对象
2）如果父类没有，或者只有无参构造器，子类不管调用无参/有参构造器，都得先调用父类的无参构造器
3）如果父类有一个有参构造器，并且把无参构造器覆盖。而子类想初始化对象，就得在构造器里面写super()指定调用的父类构造函数

4.如果希望指定去调用父类的某个构造器，则显式的调用一下

5.super()在使用时，需要放在构造器第一行，不能再成员方法使用

6.super()和this()都只能放在构造器第一行，因此这两个方法不能共存在一个构造器

7.Object是所有类的父类（可通过Ctrl+H查看）

8.父类构造器的调用不限于直接父类！将一直往上追潮直到Object类（顶级父类）

9.子类最多只能继承一个父类（指直接继承），即java中是单继承机制。
思考：如何让A类继B类和C类？【

10.不能滥用继承，子类和父类之间必须满足is-a的逻辑关系
Animal
Cat extents Animal
 */

//构造器才是对象的初始化，需要对象空间，类加载涉及到后面的static知识，类加载不需要new的对象空间即可初始化赋值，不要把类加载和构造器混淆
//在new子类对象的时候，会触发父类的构造器，所以会初始化父类的属性，所以子类就有了父类可以访问的属性
class ExtendsTheory {
    public static void main(String[] args) {
        Son son = new Son();
        //按照查找关系来返回信息
        //1）检查子类是否有该属性
        //2）子类有该属性，且可以访问，则返回信息
        //3）子类没有这个属性，就看父类有没有这个信息。父类有且可访问，则返回信息
        //4）父类没有则继续找上级父类，直到Object
        //父子类修改各自对象值时不会影响对方
        System.out.println(son.name);
        //覆盖机制：如果父类的属性是私有的，不会再跳过去访问爷类，而是报错
        //可加上super.来指定调用的内容
        //System.out.println(son.age);
        System.out.println(son.getAge());
        System.out.println(son.hobby);


        //
        System.out.println("===================");
        ExtendsExampleB b = new ExtendsExampleB();

        //在main中，分别创建Personinfo和Student对象，调用say方法输出自我介绍。
        System.out.println("=============");
        Personinfo personinfo = new Personinfo("孔乙己", 45);
        System.out.println(personinfo.say());
        Student student = new Student("田所",24,114514,1919.81);
        System.out.println(student.say());
    }
}

class GrandPa{
    String name="大头爷爷";
    String hobby="旅游";
    protected int age = 85;
}
class Father extends GrandPa{
    String name="大头爸爸";
    private int age = 39;
    //在子类调用私有属性，需要用成员方法
    public int getAge() {
        return age;
    }
}
class Son extends Father{
    String name="大头儿子";
}

class ExtendsExampleA{
    ExtendsExampleA(){System.out.println("a");}
    ExtendsExampleA(String name){
        System.out.println("a name");
    }
}
class ExtendsExampleB extends ExtendsExampleA{
    //这个无参构造器在调用本类的 this("abc") 之后输出
    ExtendsExampleB(){this("abc");
        System.out.println("b");}
    ExtendsExampleB(String name){
        //此处存在一个默认的隐藏 super()，将输出A的无参构造器
        System.out.println("b name");
    }
}

/*
Super关键字
●基本介绍
super代表父类的引用，用于访问父类的属性、方法、构造器
//this.调用本类属性和方法，super.调用父类的属性和方法
//this()调用本类的构造函数，super()调用父类的构造函数

●基本语法
1.访问父类的属性，但不能访问父类的private
super.属性名；
2.访问父类的方法，不能访问父类的private方法
super.方法名(参数列表);
3.访问父类的构造器：
super(参数列表);只能放在构造器的第一句，只能出现一句！

//如果子类和父类都用一个名字的属性和方法，加super则访问父亲类，否则默认访问子类


●super给编程带来的便利/细节
1.调用父类的构造器的好处(分工明确，父类属性由父类初始化，子类的属性由子类初始化）
2.当子类中有和父类中的成损（属性和方法）重名时，为了访问父类的成员，必须通过super。
如果没有重名，使用 super. this. 直接访问是一样的效果！
属性和方法都需要在方法或者构造器里面调用，在构造器里用super()和this()时只能放在第一排，两者只能用其一
如果找到了但是不能访问，则报错private access；没有找到，则提示方法不存在can not resolve

3.super的访问不限于直接父类，如果爷爷类和本类中有同名的成员，也可以使用
super:去访问爷爷类的成员；如果多个基类(上级类)中都有同名的成员，使用super访问遵循就近原则和访问权限的规则


super和this的比较
No.    区别点              this                       super
1     访问属性    访问本类中的属性，如果本类        从父类开始查找属性
                没有此属性则从父类中继续查找
2     调用方法    访问本类中的方法，如果本类        从父类开始查找方法
                没有此方法则从父类中继续查找
3     调用构造器       调用本类构造器                调用父类构造器
                    必须放在构造器首行          必须放在子类构造器首行
4       特殊          表示当前对象                子类中访问父类对象

 */

/*

方法重写/覆盖(override)
●基本介绍
方法覆盖（重写）就是子类有一个方法，和父类的某个方法的名称、返回类型、参数一样
即：子类的这个方法覆盖了父类的那个方法
重写是为了让儿子在与父亲相同的东西上面有自己的独特之处，同时为了减轻代码量
//重载在同一类中，方法名一样参数不一样；重写在有继承的两个类中，参数和方法名都一样
//重载一般用做和父级同名方法的自定义 因为父级方法不能满足子类的数据处理需求

●使用细节//根本原理是为了优先调用子类中的方法
1.子类方法的形参列表，方法名称，要和父类方法的形参列表，方法名称完全一样。
//方法签名包括参数列表和方法名,不包括返回类型,更加不包括访问修饰符和异常类型
//重写就是方法签名一样但是里面的方法体或者返回值不一样
//多态是函数名一样但是函数签名不一样

2.子类方法的返回类型和父类方法返回类型一样，或者是父类返回类型的子类
比如父类返回类型是Object,子类方法返回类型是String
//子类与父类的返回类型要么是相同的基本数据类型，要么是父类返回的类型与子类返回的类型构成父子类关系
//错误提示：incompatible return type

3.子类方法不能缩小父类方法的访问权限（大于等于父类）
public > protected > 默认 > private

  名称      发生范围   方法名      形参列表      返回类型       修饰符
  重载        本类     相同    类型、个数、顺序   无要求        无要求
overload                       至少一个不同
  重写       父子类    不同         相同       和父类一致    不能缩小父类
override                                     或是其子类    方法访问范围


 */

/*
1.编写一个Personinfo类，包括属性/private(name、age),构造器、方法say(返回自我介绍的字符串）。
2.编写一个Student类，继承Personinfo类，增加id、scorel属性/private,以及构造器，定义sy方法（返回自我介绍的信息）。
3.在main中，分别创建Personinfo和Student对象，调用say方法输出自我介绍。
 */
//编写Personinfo类
class Personinfo{
    private String name;
    private int age;

    public Personinfo(String name, int age) {
        //可以无参，但是方法要加this.注意就行，但是无参快捷方便呀
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String say(){
        return "My name is " + name + ". I'm " + age + " years old.";
    }
}
//2.编写一个Student类，继承Person类，增加id、scorel属性/private,以及构造器，定义sy方法（返回自我介绍的信息）。
class Student extends Personinfo{
    private int id;
    private double score;

    public Student(String name, int age, int id, double score) {
        super(name, age);//自动调用父类构造器
        this.id = id;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String say(){//super的好处：代码复用
        return super.say() + " My id number is " + id + ". My score is " + score;
    }
}