/*
当父类的一些方法不能确定时，可以用abstract关键字来修饰该方法，这个方法
就是抽象方法，用abstract来修饰该类就是抽象类
当一个类中存在抽象方法时，需要将该类声明为abstract类。一般来说，抽象类会被继承，有其子类来实现抽象方法
抽象类是为子类重写服务的，向上转型不能调用子类特有的方法，向下转型违背了继承的初衷

1）用abstract关键字来修饰一个类时，这个类就叫抽象类
访问修饰符 abstract 类名{
}
2）用abstract关键字来修饰一个方法时，这个方法就是抽象方法
访问修饰符 abstract 返回类型 方法名(参数列表); //没有方法体
3）抽象类的价值更多作用是在于设计，是设计者设计好后，让子类继承并实现抽象类

●使用细节
1）抽象类不能被实例化（但是有默认构造器，因为子类来继承的时候，需要调用，理解抽象类就是为子类服务的。）
2）抽象类不一定要包含abstract方法。也就是说，抽象类可以没有abstract方法
3）一旦类包含了abstract方法,则这个类必须声明为abstract
4）abstract只能修饰类和方法，不能修饰属性和其它
5）抽象类可以有任意成员【因为抽象类还是类】，比如：非抽象方法、构造器、静态属性等等
6）抽象方法不能有方法体，即：抽象方法不包含{}
7）如果一个类继承了抽象类，则它必须实现抽象类的所有抽象方法，除非它自己也声明为abstract类
8）抽象方法不能使用private、final和static来修饰，因为这些关键字都是和重写相违背的。
private子类不能直接访问；final本意是不希望子类重写；
static修饰的静态方法有具体的实现，能被类直接调用，而abstract只有方法声明没有具体的实现（无方法体），需要在子类或实现类中去编写完整的方法处理逻辑后才能使用。
抽象方法也不能用static修饰，如果用static修饰了，那么我们就可以直接通过类名调用了，而抽象方法压根没有主体，没有任何业务逻辑，这样就毫无意义了。

被private,final和static修饰的方法都是静态绑定机制的，也就是说，它们属于该类独有的，静态绑定机制对静态方法来说，就是用一个父类引用符号去调用子类的静态方法时，调用的是子类的方法

//方法不能用private但是属性可以，因为这些属性可以用构造方法和get来获取

 */
/*
编写一个Employeeinfo类，声明为抽象类，包含如下三个属性：name，id，salary。提供必要的构造器和抽象方法：work()。
对于Manager02类来说，他既是员工，还具有奖金（bonus）的属性。请使用继承的思想，设计CommonEmployee02类和Manager02类，
要求类中提供必要的方法进行属性访问，实现work，提示“经理/普通员工 名字 工作中"..OOP的继承+抽象类


 */
public class Studynote31_abstract {
    public static void main(String[] args) {
        Manager02 manager = new Manager02("Saitama",114,514);
        manager.setBonus(10000);
        manager.work();
        CommonEmployee02 employee = new CommonEmployee02("Chiba",1919,810);
        employee.work();

    }
}

abstract class Employeeinfo{
    private String name;
    private int id;
    private double salary;
    //将work做成抽象方法
    public abstract void work();
    //子类也可以实现的功能
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    //构造器
    public Employeeinfo(String name, int id, double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;

    }
}

class Manager02 extends Employeeinfo{
    private double bonus;

    public Manager02(String name, int id, double salary) {
        super(name, id, salary);
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public void work() {
        System.out.println("经理 " + getName() + " 正在工作");
    }
}

class CommonEmployee02 extends Employeeinfo{
    public CommonEmployee02(String name, int id, double salary) {
        super(name, id, salary);
    }

    @Override
    public void work() {
        System.out.println("普通员工 " + getName() + " 正在工作");
    }
}
/*
当一个指向子类对象的父类引用变量来调用父子同名的静态方法时，只会调用父类的静态方法。这是因为静态方法只能被继承，不能被重写，
如果子类有和父类相同的静态方法，那么父类的静态方法将会被隐藏，对于子类不可见，也就是说，子类和父类中相同的静态方法是没有关系的方法，他们的行为不具有多态性。

但是父类的静态方法可以通过 父类.方法名 调用。重写static方法编译器虽然不会报错，但是重写之后不具有多态属性，没有意义。
多态的本质是动态绑定，即运行的时候再去确定实际使用的对象类型是什么，进而确定实际上调用的方法是哪个。所以可以看出多态的实现
一定要依赖于对象存在的。那么进而我们就可以认为，重写也要依赖于对象实例。

static方法随着类加载而加载的，它只跟类相关，而与对象无关。所以它并不适用于上面讨论的概念。

重写效果是什么：对于相同的方法，子类和父类要具有不同的行为，这使得子类的功能较父类来说有一定的扩展。这种效果使得重写允许了多态的实现，
重写是多态实现的前提（之一）。即当一个父类的对象实际上引用了子类对象时，向该对象发送消息调用方法时，实际上调用的是子类重写过的方法。这才是重写的主要作用，而不是简单扩展。

如果你一直使用一个子类的对象调用某个方法的话，那么子类和父类中的方法完全可以没有任何关系。重写使得子类和父类中相同的方法有所联系，而且子类和父类对于相同的方法具有不同的行为。
 */