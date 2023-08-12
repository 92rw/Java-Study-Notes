/**
 * 演示局部内部类
 */
class NestedClass {
    public static void main(String[] args) {
        //局部内部类
        NestedLocal n1 = new NestedLocal();
        n1.m1();
        System.out.println("运行时创建的对象 hashCode 为 " + n1);

        System.out.println("=========================");
    }
}

class NestedLocal{
    private int n1 = 100;
    private void m2(){
        System.out.println("外部类的私有方法 m2() 被调用");
    }
    public void m1(){
        //局部内部类通常是定义在外部类的局部位置，通常在方法中
        class Inner01{//局部内部类
            private int n1 = 180;
            //内部类的特点，一没有借助类实例化的对象访问其实例成员，而访问时不考虑访问权限修饰符的限制作用
            public void f1(){
                //外部类和局部内部类的成员重名时，默认遵循就近原则，如果想访问外部类的成员，则可以使用（外部类名.this.成员）去访问
                //（外部类名.this.成员）得到的是调用 m1() 方法的对象
                System.out.println("内部类的私有属性 n1 = " + n1 + " 外部类的私有属性 n1 = " + NestedLocal.this.n1);
                m2();
                //通过 hashCode 可以判断 this 和 NestedLocal.this 不是一个对象
                System.out.println("this的对象 hashCode 为 " + this);
                System.out.println("类名.this的对象 hashCode 为 " + NestedLocal.this);
            }
        }
        final class Inner02 extends Inner01{
            //局部内部类不能用 final 之外的修饰符，可以体现继承关系
        }
        //外部类在方法中，可以创建对象并调用方法
        Inner02 i1 = new Inner02();
        i1.f1();
    }
    {
        //局部内部类可以定义在代码块中
        class Inner03{}
    }
}



/**
 * 演示匿名内部类
 */
class NestedAnonymousExample {
    public static void main(String[] args) {
        //匿名内部类的三种形式
        NestedAnonymous n2 = new NestedAnonymous();
        n2.m1();//基于接口
        n2.m2();//基于类
        n2.m3();//基于抽象类

        System.out.println("=========================");

    }
}
interface USB {//接口
    void use();
}

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
2.要求调用 CellPhone2 对象的testWork方法，使用匿名内部类
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

/**
 * 演示成员内部类和静态内部类
 */
class NestedMenber {
    public static void main(String[] args) {
        //其他外部类访问内部成员类，有两种方式：
        // 方式1：因为成员内部类是一个成员！访问成员“外部类对象.成员”
        // 该成员是个类，所以“外部类对象.new成员内部类”=内部类对象
        // 内部类对象访问方法“外部类对象.new成员内部类.方法”
        OuterClass01 o1 = new OuterClass01();
        OuterClass01.Inner i1 = o1.new Inner();
        i1.say();

        //方式2：在外部类中编写一个方法，返回对象
        OuterClass01.Inner i2 = o1.getInnerInstance();
        i2.say();

        System.out.println("==================");

        //其他外部类访问静态内部类，可以直接通过类名去调
        //成员内部类需通过外部类的实例对象去访问内部类
        OuterClass02.Inner i3 = new OuterClass02.Inner();
        i3.say();
        //编写一个方法，返回静态内部类的对象实例
        OuterClass02 o2 = new OuterClass02();
        OuterClass02.Inner i4 = o2.getInner();
        i4.say();

        OuterClass02.m1();
    }

}


class OuterClass01{
    private int n1 = 10;
    public String name = "Kanagawa";
    class Inner{
        public int n1 = 15;
        public void say(){
            System.out.println("OuterClass01 的 n1 = " + OuterClass01.this.n1 +
                    " name = "+  name + " Inner 的 n1 = " + n1);
        }
    }
    public Inner getInnerInstance(){
        return new Inner();
    }

}

class OuterClass02{
    private int n1 = 10;
    private static String n2 = "Niigata";
    static class Inner{
        public void say(){
            System.out.println("OuterClass01 的 n2 = " + n2);//只能访问静态属性
        }
    }
    public static void m1(){
        Inner i0 = new Inner();
        i0.say();
    }
    public Inner getInner(){//可以改成静态方法，这样不需要创建对象也能外部调用
        return new Inner();
    }
}

/*
练习实例
有一个Car类，有属性temperature（温度），车内有Air（空调）类，有吹风的功能flow
Air会监视车内的温度，如果温度超过40度则吹冷气。如果温度低于0度则吹暖气，如果在这之间则关掉空调。
实例化具有不同温度的Car对象，调用空调的flow方法，测试空调吹的风是否正确
 */

class NestedExerciseCar{
    private double temperature;

    public NestedExerciseCar(double temperature) {
        this.temperature = temperature;
    }

    class Air{//成员内部类
        void flow(){
            if(temperature > 40){
                System.out.println("空调冷气开启");
            } else if (temperature < 0) {
                System.out.println("空调暖气开启");
            } else System.out.println("空调关闭");
        }
    }
    //提供方法返回成员内部类
    public Air testAir(){
        return new Air();
    }
}
class NestedMemberExercise{
    public static void main(String[] args) {
        NestedExerciseCar car1 = new NestedExerciseCar(30);
        car1.testAir().flow();
    }
}
