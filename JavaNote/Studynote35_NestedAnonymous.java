public class Studynote35_NestedAnonymous {
    public static void main(String[] args) {
        //匿名内部类的三种形式
        NestedAnonymous n2 = new NestedAnonymous();
        n2.m1();//基于接口
        n2.m2();//基于类
        n2.m3();//基于抽象类

        System.out.println("=========================");

        //匿名内部类可以当做实参直接传递，简洁高效
        f1(new USB() {//这个new的意思是匿名内部类,底层意思其实是弄了一个类来实例化这个匿名内部类的，相当于直接传入一个对象
            @Override
            public void use() {
                System.out.println("直接调用 USB 接口");
            }
        });
        //传统方法：需要新建类才能实现接口调用
        f1(new USB3());
    }

    //静态方法，形参是接口类型
    public static void f1(USB usb){
        usb.use();
    }

}


/*
匿名内部类：涉及到继承、多态、动态绑定、内部类
1）类   2）内部类   3）没有名字   4）是一个对象
说明：匿名内部类是定义在外部类的局部位置，比如方法中，并且没有类名

1.匿名内部类的基本语法
new 类或接口(参数列表){
类体
};

//匿名内部类就是实现了某个接口或继承某个父类的实例对象，重写了方法，这里是向上转型的
//匿名内部类作为实参的时候看成对象，接收后动态绑定匿名内部类

2.匿名内部类的语法比较奇特。因为匿名内部类既是一个类的定义，同时它本身也是一个对象。
因此从语法上看，它既有定义类的特征，也有创建对象的特证，

3.可以直接访问外部类的所有成员，包含私有的
4.不能添加访问修饰符,因为它的地位就是一个局部变量。
5．作用域：仅仅在定义它的方法或代码块中。
6.匿名内部类 -访问-> 外部类成员[访问方式：直接访问]
7.外部其他类 -不能访问-> 匿名内部类（因为匿名内部类只是一个局部变量）
8.如果外部类和内部类的成员重名时，内部类访问的话，默认遵循就近原则，如果想
访问外部类的成员，则可以使用（外部类名.this.成员）去访问

//匿名内部类需要分号，因为匿名内部类需要立即创建对象，创建对象语句是需要分号的
 */
class NestedAnonymous{
    public void m1(){
        //匿名内部类1：基于接口
        //功能：使用 USB 接口，并创建对象
        //传统方式：写一个类，实现接口并创建对象
        //需求：只实现一次功能，后续不再使用
        /*
        typec 的编译类型是 USB，运行类型是 匿名内部类
        创建了接口对应的类，名称为 NestedAnonymous$1，只是系统分配仅使用一次
        class NestedAnonymous$1 implements USB{
            @Override
            public void use() {
                System.out.println("USB 接口被使用");
            }
        }

        这里并不是实例化接口，本质还是在new一个类对象，这个对象类型是匿名内部类的类型，同时我们这个这个类得实现USB接口
        jdk 底层逻辑：方法区创建匿名内部类，在堆中创建实例 NestedAnonymous$1，返回地址给 typec 对象，方法区的类销毁保留对象
        匿名内部类创建了一个对象就是使用一次（无法使用同一个内部类创建多个实例），但对象还在，其他类可以创建很多个对象，使用很多次，个人理解
         */
        USB typec = new USB(){
            @Override
            public void use() {
                System.out.println("USB 接口被使用");
            }
        };//必须有这个分号
        System.out.println("typec 的运行类型为 " + typec.getClass());
        typec.use();
    }
    public void m2() {
        //匿名内部类2：基于类
        //在什么地方运行那么这个匿名内部类的名字就跟这个地方有关，但是运行类型只与Father有关，也就是名字不影响运行类型
        /*
        s1 的编译类型是 Sample，运行类型是匿名对象 NestedAnonymous$2
        此时代码相当于
        class NestedAnonymous$2 extends Sample{
            public void test(){
                System.out.println("匿名内部类重写了 test() 方法");
                }
            }
        向下转型是把子类的对象赋值给父类的引用，然后把这个父类的引用强转为子类赋值给子类的引用，
        这里子类是匿名的，拿不到你的运行类型，所以既无法强转也无法赋值，所以不能下转型
        调不到你的特有方法，所以匿名内部类只能执行父类的方法

        因为继承的本质就是可以重写父类的方法，而匿名内类的本质就是继承类或实现接口，所以需要重写/实现方法
        */
        //匿名类可能是实现接口的类或是实现继承重写的类
        AnonymousSample1 s1 = new AnonymousSample1("样本1") {
            public void test(){
                System.out.println("基于普通类的匿名内部类重写了 test() 方法");
            }
        };
        System.out.println("s1 对象的运行类型= "+ s1.getClass());
        System.out.println("---------");
        s1.test();

        AnonymousSample1 s2 = new AnonymousSample1("样本2");//没有大括号，只是相当于创建一个新对象
        System.out.println("s2 对象的运行类型= "+ s2.getClass());
        System.out.println("---------");
        s2.test();//调用 Sample 类的方法。而非内部类

        new AnonymousSample1("样本3") {
            public void test2(String str){
                System.out.println("新的匿名内部类重写了 test2() 方法，输入值为 = " + str);
                super.test2(str);
            }
        }.test2("override");//匿名内部类作为对象使用，执行完后栈销毁，里面类和对象也没了
        System.out.println("---------");
    }

    public void m3(){
        //匿名内部类3：基于抽象类
        AnonymousSample2 s3 = new AnonymousSample2(){
            public void show(){
                System.out.println("基于抽象类的匿名内部类重写了 show() 方法");
            }
        };
        s3.show();
    }
}

interface USB {//接口
    void use();
}

class AnonymousSample1{//类
    public AnonymousSample1(String name){
        System.out.println("新建 " + name + " 对象调用了外部类的构造器");
    }//构造器
    public void test(){
        System.out.println("调用外部类的 test() 方法");
    }//方法
    public void test2(String str){
        System.out.println("外部类的 test2() 方法得到的输入值 = " + str);
    }
}

abstract class AnonymousSample2{//抽象类
    abstract void show();//加absract是抽象方法，不加就是普通方法必须带大括号
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
    }
}
//使用匿名内部类可以省略新建这个类的过程，直接在运行过程中完成调用

/*
练习实例1
1.有一个铃声接口Bell，里面有个ring方法
2.有一个手机类Cellphone1，具有闹钟功能alarmclock，参数是Bell类型
3.测试手机类的闹钟功能，通过匿名内部类（对象）作为参数，打印：懒赖猪起床了
4.再传入另一个匿名内部类（对象），打印小伙伴上课了
 */
interface Bell{
    void ring();
}
class Cellphone1{
    public void alarmclock(Bell bell){
        bell.ring();
    }
}
class AnonymousExercise1{
    public static void main(String[] args) {
        Cellphone1 cellphone = new Cellphone1();
        //1.传递的是实现了 Bell 接口的匿名内部类
        //2.重写了ring() 方法
        //3.根据动态绑定，运行重写的方法
        cellphone.alarmclock(new Bell() {
            @Override
            public void ring() {
                System.out.println("懒虫起床啦");
            }
        });

        cellphone.alarmclock(new Bell() {
            @Override
            public void ring() {
                System.out.println("小伙伴上课了");
            }
        });
    }
}

/*
练习实例2
1.计算器接口具有work方法，功能是运算，
有一个手机类 Cellphone2，定义方法testwork测试计算功能，调用计算接口的work方法
2.要求调用 CellPhone2 对象的testWork方法，使用上名内部类
 */
class AnonymousExercise2{
    public static void main(String[] args) {
        Cellphone2 cellphone = new Cellphone2();
        cellphone.testWork(new inputNum() {//匿名内部类，同时也是传入的对象。此处以计算乘法为例
            @Override
            public int work(int a, int b) {
                return a * b;
            }
        }, 3,5);
    }
}


class Cellphone2{
    public void testWork(inputNum nums,int n1, int n2) {
        //调用该方法时直接传入 inputNum 接口的匿名内部类，可实现不同的计算任务
        int result = nums.work(n1,n2);
        System.out.println("result = " + result);
    }
}

interface inputNum{
    //题目没有要求 work 方法怎么计算，此处也不需要在接口中体现
    int work(int a,int b);
}
