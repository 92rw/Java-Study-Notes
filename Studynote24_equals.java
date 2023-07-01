
/*
==和equals的对比

名称             概念               用于基本数据类型                    用于引用类型
==            比较运算符         可以，判断值是否想等            可以，判断两个对象是否想等

equals      Object类的方法              不可以             可以，默认判断两个对象是否想等，但是
        java类都可以使用equals                           子类往往重写该方法，判断对象属性是否想等

1.==:比较运算符，既可以判断基本类型，又可以判断引用类型
2.==:如果判断基本类型，判断的是值是否相等。
3.==:如果判断引用类型，判断的是内存地址是否相等，即判定是不是同一个对象（和编译类型和运行类型无关）
//不是相同类型，无法进行比较，使用==判断可能出现编译报错
4.equals：是Object类中的方法，只能判断引用类型，查看Jdk源码:光标放在该方法，快捷键 Ctrl + B
5，默认判断的是地址是否相等（是否同一对象），子类中往往重写该方法，用于判断内容是否相等。比如Integer,String

//String类重写了equals方法，使其可以比较字符串值是否相等，但其他的类型就需要自己重写equals方法，否则就只能比较地址
//因为String相同的字符串虽然在常量池都是同一个地址，但是在堆中是不同地址，所以需要重写equals
//Integer是一个类，这个类有很多方法，是引用数据类型；而int是基本数据类型，就只是用来表示这个变量是int类型

==判断栈上的，equals判断的堆上的，应该是吧
一个判断地址，一个判断内容
 */



public class Studynote24_equals {
    public static void main(String[] args) {
        D d = new D();
        D e = d;
        System.out.println(d == e);
        C c = d;//向上转型
        System.out.println(c == d);
        int num1 = 10;
        double num2 = 10.0;
        System.out.println(num1 == num2);//基本数据类型，判断是否相等

        System.out.println("=====判断 Integer 对象是否相同====");
        //查看源码:光标放在该方法，快捷键 Ctrl + B;
        Integer integer1 = new Integer(1024);
        Integer integer2 = new Integer(1027);
        //比较值的是Integer的equals方法，这里用的==，比较的是内存地址
        System.out.println(integer1 == integer2);//判断引用数据内存地址是否相等
        //如果传入的两个值在-128～127之间，那么会把这两个一样的数值放在一个叫常量池的东西里，那么这个时候两个数值比较就是true了
        System.out.println(integer1.equals(integer2));

        System.out.println("=====判断 String 对象是否相同====");
        String str1 = new String("Hello");
        String str2 = new String("Hello");
        System.out.println(str1 == str2);//判断是否同一对象，false
        System.out.println(str1.equals(str2));//判断值是否相等true
        //常量池找的是直接赋值的，（String a ="sda"） == （String b ="sda"）是true

        System.out.println("=====判断 Human 对象是否相同====");
        Human human1 = new Human("Fukushima", 35, '男');
        Human human2 = new Human("Fukushima", 35, '男');
        //若下方未重写equals方法，则调用Object类中的方法，仅判断内存地址是否相同
        System.out.println(human1.equals(human2));
    }
}

class C{}
class D extends C{}

/*重写 equals 方法
案例：判断两个 Human 对象的内容是否相等，树果各个属性值都一样，则返回true，否则返回false */
class Human{
    private String name;
    private int age;
    private char gender;
    //重写 equals 方法
    //形参定义成 Human 那就不叫重写，是方法的重载。要跟Object类的equals()同名同参才是重写啊！
    //重载（Overload）的方法是用静态绑定完成，而重写（Override）的方法则使用动态绑定完成
    public boolean equals(Object obj){
        //若两个对象是同一对象，则返回true
        if(this == obj) {
            return true;
        }
        //类型判断
        //instanceof判断的是运行类型，因为多态参数的性质，形参列表是Object类型，那么参数的编译类型就是Object
        //Object 没办法调用String类型的成员，需要向下转型
        if(obj instanceof Human){//被比较的也需要是Human类
            //传参的时候向上转型了，在运行时它只认变量声明(运行类型)的类型 Object
            //如果不开展向下转型，将无法获取 Human 类特有属性的参数
            Human h = (Human)obj;
            //return里面的equals根据动态绑定机制 name的类型的String所以引用String类中的equals的重写方法
            return this.name.equals(h.name) && this.age == h.age && this.gender == h.gender;
        }
        //如果类型不同，返回false
        return false;

        /*
        //也可以用这种方式写
        if (!(obj instance of Human)) {
            return false;
        }
        Human h = (Human)obj;
        return this.name.equals(h.name) && this.age == h.age && this.gender == h.gender;

         */
    }

    public Human(String name, int age, char gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
}