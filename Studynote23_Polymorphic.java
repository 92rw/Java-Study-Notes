

//快捷键光标移到父类 按住alt+enter快速创建子类
/*
多态Polymorphic
提高复用性和维护性
1.方法的多态：重载、重写
2.对象的多态（核心）
(1)一个对象的编译类型和运行类型可以不一致 ->
Animal animal = new Dog();  //向上转型(父类的引用指向子类的一个对象)[animal编译类型是Animal,运行类型Dog]
Dog d = (Dog)animal //向下转型
animal = new Cat();     //[animal的运行类型变成了Cat,编译类型仍然是Animal]


(2)编译类型在定义对象时，就确定了，不能改变
////你强转了之后运行只会运行父类中的方法，而不会调用子类中的方法

(3)运行类型是可以变化的。
//可以通过getClasss()来查看运行类型

(4)编译类型看定义时=号的左边，运行类型看=号的右边
//等号左边是存放地址的变量，它存放的是右边元素的地址
//注意：看左边的时候如果调用父类方法必须要被子类重写，因为运行时要看子类，不重写会报错

●多态的前提是：两个对象（类）存在继承关系
●多态的向上转型
1)本质：父类的引用指向子类的对象
2)语法：父类类型 引用名 = new 子类类型();
3)特点：编译类型看左边，运行类型看右边。
可以调用父类中的所有成员（需遵守访问权限），不能调用子类中特有成员；
最终运行效果看子类的具体实现，调用方法时从子类（运行类型）开始查找方法
//即:父类没编译到的子类特有成员，在运行时子类会从自己本类开始查找运行。
//多态中强调：编写java程序时，引用类型变量只能调用其编译时类型的变量，不能调用其运行时类型变量。
//jvm并不会根据引用对象里的具体类型去更改它的对象，方法区只加载了父类的属性或者方法
//在编译阶段能调用哪些成员（属性或者方法），由编译类型决定


●多态的向下转型
1）语法：子类类型 引用名=（子类类型）父类引用
2）只能强转父类的引用，不能强转父类的对象
3）要求父类的引用必须指向的是当前自标类型的对象
4）可以调用子类类型中所有的成员
//是在实际使用的过程中，为了代码的简便性，一般会直接使用父类作为数据类型
//当子类出现特定方法的时候就需要使用向下转型，进行特定方法的使用
//向下转化，就相当于是将已经创建好的对象，将其引用转化成对象本来的类型，需要强制转换
//总结：类型转换是临时的，不是永久的。java是强类型语言
//（强制转换只不过是给你这个人另外的一个身份，但是你还是你）

●多态的注意事项
1.属性没有重写之说！属性的值看编译类型
//向上转型不能调用子类的特有方法包括属性，这跟jvm方法调用的静态和动态绑定机制有关
2.instanceof比较操作符，用于判断对象的运行类型是否为XX类型或XX类型的子类型
*/
public class Studynote23_Polymorphic {//主类
    public static void main(String[] args) {
        Sub s = new Sub();
        System.out.println(s.count);//s的编译类型，20
        s.display();                //s的运行类型，20
        Base b = s;                 //向上转型
        System.out.println(b == s); //指向堆中的相同地址，true
        //属性看编译，方法看运行
        System.out.println(b.count);//b的编译类型，10
        b.display();                //b的运行类型是Sub，20

        System.out.println("=========================");
        DynamicA a = new DynamicB();    //向上转型
        System.out.println(a.sum());    //父类sum()调用子类的getl()
        System.out.println(a.sum1());   //调用子类的方法，因此输入子类的i
    }
}

class Base{//父类
    int count = 10;
    public void display(){
        System.out.println(this.count);
    }
}

class Sub extends Base{//子类
    int count = 20;
    public void display(){
        System.out.println(this.count);
    }
}

/*
动态绑定机制DynamicBinding
1.当调用对象方法的时候，该方法会和该对象的内存地址/运行类型绑定
2.当调用对象属性时，没有动态绑定机制，哪里声明，那里使用

//意思就是存在继承关系时候，调用方法时会自动绑定运行类型，即从运行类型开始查找
//属性则是哪里调用就用哪的，不会从运行类型查找
 */
class DynamicA{
    public int i = 10;
    public int sum(){
        return getl() + 10;//调用方法，和运行类型动态绑定
    }
    public int sum1(){
        return i + 10;//调用属性，哪里声明哪里使用（就近原则）
    }
    public int getl(){
        return i;
    }
}
class DynamicB extends DynamicA{
    public int i = 20;
//    public int sum(){
//        return i + 20;
//    }
    public int getl(){
        return i;//没有动态绑定机制，就近原则，输出20
    }
//    public int sum1(){
//        return i + 10;
//    }
}

/*
多态的应用
1）多态数组：数组的定义类型为父类类型，里面保存的实际元素类型为子类类型
2）多态参数：方法定义的形参类型为父类类型，实参类型允许为子类类型
 */

/*
多态数组应用实例：现有一个继承结构如下：要求创建1个 Persons 对象、2个 Pupil 对象和2个 Graduate 对象
统一放在数组中，并调用say方法
应用实例升级：如何调用子类特有的方法，比如 Pupil 有一个 study ，Graduate 有一个 essay 怎么调用？
 */
class PolyArray{
    public static void main(String[] args) {
        //创建对象并赋值
        Persons[] persons = new Persons[5];
        persons[0] = new Persons("Aomori",20);
        persons[1] = new Pupil("Iwate",3,4.5);
        persons[2] = new Pupil("Miyagi",4,4.3);
        persons[3] = new Graduate("Akita",28,3590);
        persons[4] = new Graduate("Yamagata",30,1245.0);
        
        //遍历多态数组，调用say
        for (int i = 0; i < persons.length; i++) {
            //persons[i]的编译类型是Persons
            //根据动态绑定机制，运行类型根据实际情况由JVM判断
            System.out.println(persons[i].say());
            //判断运行类型，需要范围小的子类在前
            if(persons[i] instanceof Pupil){
                Pupil pupil = (Pupil)persons[i];//向下转型
                pupil.study();
            } else if(persons[i] instanceof Graduate){
                ((Graduate)persons[i]).essay();//将两条语句简化为一句
            } else {
                System.out.println("人物类型有误，请自行检查");
            }
        }
    }
}

class Persons{
    private String name;
    private int age;

    public Persons(String name, int age) {
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
        return "name= " + name + "\tage= " + age;
    }
}

class Pupil extends Persons{
    private double score;

    public Pupil(String name, int age, double score) {
        super(name, age);
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    //重写父类方法

    @Override
    public String say() {
        return super.say() + "\tscore= " + score;
    }
    public void study(){//名字是private属性，需要通过getName方法访问
        System.out.println("小学生" + getName() +"正在上课");
    }
}

class Graduate extends Persons{
    private double salary;

    public Graduate(String name, int age, double salary) {
        super(name, age);
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    //重写父类
    @Override
    public String say() {
        return super.say() + "\tsalary= " + salary;
    }
    public void essay(){
        System.out.println("研究生" + getName() + "正在写论文");
    }
}

/*
定义员工类Employee，包含姓名和月工资[private]，以及计算年工资 getAnnual 的方法。
普通员工和经理继承了员工，经理类多了奖金bonus属性和管理manage方法，普通员工类多了work方法，
普通员工和经理类要求分别重写getAnnual方法
测试类中添加一个方法showEmpAnnal(Employee e)，实现获取任何员工对象的年工资，
并在main方法中调用该方法[e.getAnnualo]
测试类中添加一个方法，testWork，如果是普通员工，则调用work方法，如果是经理，则调用manage方法
 */
class PolyParameter{
    public static void main(String[] args) {
        Worker fukushima = new Worker("Fukushima", 2000);
        Manager ibaraki = new Manager("Ibaraki", 30000, 100000);
        PolyParameter polyParameter = new PolyParameter();
        polyParameter.showEmpAnnal(fukushima);
        polyParameter.testWork(fukushima);
        polyParameter.showEmpAnnal(ibaraki);
        polyParameter.testWork(ibaraki);
    }

    //测试方法需要写在main函数外
    public void showEmpAnnal(Employee e){
        System.out.println(e.getAnnual());
    }
    public void testWork(Employee e){
        if(e instanceof Worker){
            ((Worker) e).work();//向下转型
        } else if(e instanceof Manager){
            ((Manager) e).manage();
        } else {
            System.out.println("非普通员工或经理");
        }
    }
}
class Employee{
    //包含姓名和月工资[private]
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    //计算年工资 getAnnual 的方法
    public double getAnnual(){
        return salary * 12;
    }
}

//普通员工和经理继承了员工，经理类多了奖金bonus属性和管理manage方法，普通员工类多了work方法，
class Manager extends Employee{
    private double bonus;

    public Manager(String name, double salary, double bonus) {
        super(name, salary);
        this.bonus = bonus;
    }
    public void manage(){
        System.out.println("经理 " + getName() + " 正在对接客户");
    }
    //重写
    @Override
    public double getAnnual() {
        return super.getAnnual() + bonus;
    }
}

class Worker extends Employee{
    public Worker(String name, double salary) {
        super(name, salary);
    }
    public void work(){
        System.out.println("普通员工 " + getName() + " 正在工作");
    }

    @Override
    public double getAnnual() {
        return super.getAnnual();//普通员工无其他收入，此处无改进
    }
}