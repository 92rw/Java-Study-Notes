//接口=抽象+多态+继承+动态分配
//接口：子类继承父类，重写父类的方法，能同时继承多个父类
//可以看作是对 Java 单继承机制的补充

public class Studynote33_Interface {
    public static void main(String[] args) {
        System.out.println(Interface01.n1);//接口中的属性是静态的
        System.out.println(Interface04.n1);//java 中的静态属性和静态方法是可以被继承的，但是不能被子类重写

        //多态参数：接口类型的变量，可以指向实现了该接口的类对象实例
        Interface01 i1 = new Interface04();
        i1 = new Interface05();
        //多态传递：接口就是更抽象的抽象类，而抽象类他也是类
        Interface02 i3 = new Interface05();//子接口继承父接口，子接口被实现，相当于父接口也被实现

        //多态数组
        Interface01[] i2 = new Interface01[3];
        i2[0] = new Interface04();
        i2[1] = new Interface05();
        i2[2] = new Interface04();
        for (int i = 0; i < i2.length; i++) {
            i2[i].hi();//动态绑定机制，调用接口方法
            if(i2[i] instanceof Interface05){
                ((Interface05)i2[i]).responce();//向下转型后调用特有方法
            }
        }

        //接口类型的变量，可以指向实现了该接口的类对象实例


    }
}
interface Interface01{
    //接口中的属性，默认为 public static final
    int n1 = 10;
    //写方法：在接口中，抽象方法可以省略 abstract 关键字
    public void hi();
    //在 jdk8 及以后，可以有默认实现方法，但需要用 default 关键字修饰
    default public void ok(){
        System.out.println("Interface01 的默认方法 ok() 被调用");
    }
    //在 jdk8 及以后，可以有静态方法
    public static void greet(){
        System.out.println("Interface01 的静态方法 greet() 被调用");
    }
}

interface Interface02 extends Interface01{}//接口不能继承类，但是可以继承接口


interface Interface03{
    public void hi();
}

class Interface04 implements Interface02, Interface03{//类可以实现多个接口
    //同时实现多个接口，同名方法只用实现一个
    @Override
    public void hi() {
        System.out.println("Interface04 的 hi() 方法被调用");
    }
}

class Interface05 implements Interface02{

    @Override
    public void hi() {
        System.out.println("Interface05 的 hi() 方法被调用");
    }
    public void responce(){
        System.out.println("Interface05 的 responce() 方法被调用");
    }
}
/*
●基本介绍
接口就是给出一些没有实现的方法，封装到一起，到某个类要使用的时候，在根据具体情况把这些
方法写出来。语法：
interface 接口名{
    //属性
    //方法（抽象方法，默认实现方法，静态方法）
}
class 类名 implements 接口名{
    自己属性；
    自己方法；
    必须实现的接口的抽象方法
}

小结：
1.在 Jdk7.0 前接口里的所有方法都没有方法体，即：都是抽象方法
2.在 Jdk8.0 后接口类可以有静态方法，默认方法，也就是说接口中可以有方法的具体实现

●使用细节
1）接口不能被实例化
2）接口中所有的方法是 public 方法，接口中抽象方法，可以不用 abstract 修饰
3）一个普通类实现接口，就必须将该接口的所有方法都实现（创建子类时快捷键 Alt+Enter 快速重写）
4）抽象类实现接口，可以不用实现接口的方法。
5）一个类同时可以实现多个接口
6）接口中的属性，只能是final的，而且是 public static final 修饰符。
7）接口中属性的访问形式：接口名.属性名
8）一个接口不能继承其它的类，但是可以继承多个别的接口
9）接口的修饰符只能是 public 和默认，这点和类的修饰符是一样的。
 */

/*
接口中为什么不能使用静态代码块而可以使用静态方法呢？个人理解：1. 由于接口不能实例化所以属性不能改变值，
直接在创建的时候赋值类似默认final 2. 代码块不能被public或者abstract修饰

接口很有意思的点就是可以实现多继承，在方法重载时可以自动识别接口类型从而调用对应的方法，很有趣的
抽象类是extend继承关系且只能继承一个抽象类，但一个类是可以实现多个接口类下的方法的
抽象类不能被实例化,也就不能在其他函数被调用,就没法运用实例化了
但是万一你要实现两个或以上的接口就不能用抽象类了，类是单继承的，而接口可以多实现
这里是对象多态的形式，形参定义接口，实参把对应对象扔进去，通过动态绑定调用各自对象的方法
抽象类是extend继承关系且只能继承一个抽象类，但一个类是可以实现多个接口类下的方法的

实现类使用接口中定义的属性，而实现类的实例对象可以通过接口名来访问接口中定义的属性常量，因为实例对象本质上是实现类的一个实例，
而实现类已经实现了接口，因此实例对象也可以访问接口中定义的属性常量。
 */

/*
●实现接口 vS 继承类
>接口和继承解决的问题不同
继承的价值主要在于：解决代码的复用性和可维护性
接口的价值主要在于：设计，设计好各种规范（方法），让其它类去实现这些方法

>接口比继承更加灵活
接口比继承更加灵活，继承是满足 is-a 的关系，而接口只需满足 like-a 的关系

>接口在一定程度上实现代码解耦[接口规范性+动态绑定]

//接口的目的不是为了减少代码冗余，而是为了实现规范（抽象和规范了功能），和抽象类是不一样的。
//继承高复用可维护， 接口多实现，其他方法调用传参时候就可以任选一个接口类型，更规范也减少代码量
 */

/*
●接口的多态特性
1）多态参数：接口类型的变量引用（编译类型），可以指向实现了接口的对象实例（运行类型）
//形参是父类类型，实参可以是子类，传参的时候进行了向上转型，然后再利用动态绑定查找到子类的方法然后调用，
2）多态数组
演示一个案例：给Usb数组中，存放Phone和相机对象，Phone类还有一个特有的方法call
(），请遍历Usb数组，如果是Phone对象，除了调用Usb接口定义的方法外，还需要调用
Phone特有方法call
3）接口存在多态传递现象
 */

interface InterfaceA{
    int x = 0;
}
class InterfaceB{
    int x = 1;
}
class InterfaceC extends InterfaceB implements InterfaceA{
    public void pX(){
        //访问接口的 x 使用 InterfaceA.x
        //访问父类的 x 使用 super.x
        System.out.println("x1 = " + InterfaceA.x + " , x2 = " + super.x);
    }

    public static void main(String[] args) {
        new InterfaceC().pX();
    }
}


/*
练习实例
1.有一个交通工具接口类Vehicles，有work接口
2.有 Horse 类和 Boat 类分别实现 Vehicles
3.创建交通工具工厂类，有两个方法分别获得交通工具 Horse 和 Boat
4.有 Voyager 类，有 name 和 Vehicles 属性，在构造器中为两个属性赋值
5.实例化Person对象“唐僧”，要求一般情况下用 Horse 作为交通工具，遇到大河时用 Boat 作为交通工具
6.增加一个交通工具“芭蕉扇”，用于翻越火焰山
编程过程：需求-->理解-->代码
 */

//1.有一个交通工具接口类Vehicles，有work接口
interface Vehicles{
    public void work();
}

//2.有 Horse 类和 Boat 类分别实现 Vehicles
class Horse implements Vehicles{
    @Override
    public void work() {
        System.out.println("一般情况下用 Horse 作为交通工具");
    }
}

class Boat implements Vehicles{
    @Override
    public void work() {
        System.out.println("遇到大河时用 Boat 作为交通工具");
    }
}

//3.创建交通工具工厂类，有两个方法分别获得交通工具 Horse 和 Boat
class VehiclesFactory{

    //不可向上转型并将新建的 Vehicles 对象直接返回，因为接口是抽象类不能实例化
    //因为这是工具类，因此使用 static 修饰，不需要新建实例
    public static Horse horse = new Horse();//唐僧沿路仅用一匹马，使用饿汉式限定
    private VehiclesFactory(){}//单例模式禁止创建新对象
    public static Horse getHorse(){
        //return new Horse();
        return horse;
    }
    public static Boat getBoat(){
        return new Boat();
    }

    //用于实现过火焰山的功能
    public static BananaLeafFan getFan(){
        return new BananaLeafFan();
    }
}

//4.有 Voyager 类，有 name 和 Vehicles 属性，在构造器中为两个属性赋值
class Voyager {
    private String name;
    private Vehicles vehicle;

    public Voyager(String name, Vehicles vehicle) {
        this.name = name;
        this.vehicle = vehicle;
    }
//5.实例化Person对象“唐僧”，要求一般情况下用 Horse 作为交通工具，遇到大河时用 Boat 作为交通工具.(编程思想：把具体要求封装成方法。)
    public void passRiver(){
        if(!(vehicle instanceof Boat)) {//包括 vehicles == null 和 vehicle instance of Horse 两个条件
            vehicle = VehiclesFactory.getBoat();
        }
        vehicle.work();
//        Boat boat = VehiclesFactory.getBoat();
//        boat.work();
    }
    public void common(){
        //先判断当前属性 vehicles 是否已经存在，如果已经存在则提供一匹马
        if(!(vehicle instanceof Horse)) {
            //多态（向上转型）
            vehicle = VehiclesFactory.getHorse();
        }
        vehicle.work();
    }

    public void Firehill(){
        if(!(vehicle instanceof BananaLeafFan)) {
            //多态（向上转型）
            vehicle = VehiclesFactory.getFan();
        }
        vehicle.work();
    }
}

//6.增加一个交通工具“芭蕉扇”，用于翻越火焰山
class BananaLeafFan implements Vehicles{
    @Override
    public void work() {
        System.out.println("使用芭蕉扇过火焰山");
    }
}



class InterfaceExercise{
    public static void main(String[] args) {
        Voyager tang = new Voyager("唐僧", new Horse());
        tang.common();
        tang.passRiver();
        tang.Firehill();//火焰山
        tang.common();
        tang.passRiver();
    }
}