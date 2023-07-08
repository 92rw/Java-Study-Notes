public class Studynote29_Singleton {
    public static void main(String[] args) {


        System.out.println(GirlFriend.n1);//饿汉式运行时会调用构造器
        System.out.println(Waifu.n1);//懒汉式仅调用静态属性

        //饿汉式类加载时就创建对象，懒汉式只有第一次调用get方法时才会new对象
        System.out.println("=============================");

        Waifu wf1 = Waifu.getInstance();
        Waifu wf2 = Waifu.getInstance();
        System.out.println("判断两个对象是否想等的结果为 " + (wf1 == wf2));

        System.out.println("=============================");

        //通过方法可以获取对象
        GirlFriend gf1 = GirlFriend.getInstance();
        System.out.println(gf1);
        //虽然多次获取，但是输出相同
        GirlFriend gf2 = GirlFriend.getInstance();
        System.out.println(gf2);

        //两个对象的内存地址也相同
        System.out.println("判断两个对象是否想等的结果为 " + (gf1 == gf2));



    }
}
/*
●设计模式
1.静态方法和属性的经典使用
2.设计模式是在大量的实践中总结和理论化之后优选的代码结构、编程风格、以及解决问题的思考方式。

工厂模式是一种选择器形式，其保证了使用抽象类去包含无数需要创建的实例，而单例模式是只操作一个类。可以理解为，工厂模式是单例模式的升级

●单例模式
1．所谓类的单例设计模式，就是采取一定的方法保证在整个的软件系统中，对某个类只能存在一个对象实例，并且该类只提供一个取得其对象实例的方法
2.单例模式有两种方式：①饿汉式②懒汉式
1）构造器私有化 => 防止直接new对象
2）类的内部创建对象 => 该对象是 static
3）向外暴露一个静态的公共方法getInstance


//一般来说，单例实例不应该被外部直接访问和修改，因为这可能导致单例实例被意外地篡改，从而破坏了单例的唯一性。
// 因此在实现饿汉式单例模式时，我们会将该单例实例的访问权限设置为private
饿汉式就是在静态变量初始化，方法返回，懒汉式就是在静态方法中new
 */


class GirlFriend {
    public static int n1 = 180;//用于检验代码运行逻辑，和下文无关
    private String name;

    //在构造器内部直接创建对象，为了在静态方法中返回此对象，需要修饰为 static
    private static GirlFriend gf = new GirlFriend("美少女");

    //将构造器私有化，防止外部创建并初始化这个实例
    private GirlFriend(String name) {
        System.out.println("饿汉式构造器被调用");
        this.name = name;
    }

    //提供公共的静态方法，返回 gf 对象
    //getInstance() 方法不用静态的话，你想用它就只能创建对象了，但这个类又不允许你重新new一个，矛盾了，所以要加static
    //这样外部通过 类名.类方法名 就可以返回对象，并且也加载了类信息
    public static GirlFriend getInstance() {
        return gf;
    }

    @Override
    public String toString() {
        return "GirlFriend{" +
                "name='" + name + '\'' +
                '}';
    }
}

/*
饿汉式可能造成创建了对象但是没有使用，即：调用其他数据时也完成了方法构造
懒汉式：只有在用户使用 getInstance() 时才创建对象，后面再次调用时会返回上次创建的对象
 */

class Waifu {
    public static int n1 = 180;//用于检验代码运行逻辑，和下文无关
    private String name;
    //定义属性对象，但是不赋值
    private static Waifu wf;

    //将构造器私有化
    private Waifu(String name) {
        this.name = name;
    }

    //提供public的static方法，返回对象实例
    public static Waifu getInstance() {
        if (wf == null) {
            System.out.println("懒汉式构造器被调用");
            wf = new Waifu("美娇妻");
        }
        return wf;
    }
}

/*

懒汉式和饿汉式
1.二者最主要的区别在于创建对象的时机不同：饿汉式是在类加载就创建了对象实例而懒汉式是在便用时才创建
2.饿汉式不存在线程安全问题，懒汉式存在线程安全问题。（多线程同时创建对象时，会创建多个对象，破坏单例模式）
3.饿汉式存在浪费资源的可能。因为如果程序员一个对象实例都没有使用，那么饿汉式创建的对象就浪费了，
懒赖汉式是便用时才创建，就不存在这个同题。
4.在我们javaSE标准类中，java.lang.Runtime就是经典的单例模式
 */