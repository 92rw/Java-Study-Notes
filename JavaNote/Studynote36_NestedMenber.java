//成员内部类、静态内部类就是一个外部类的一个成员，里面可以放多个属性或者变量，访问规则和本类调用规则一样
//静态内部类绝不能用this访问，所有静态方法也一样，this，super都不能用

import Exercise.StudyNote.Car;

public class Studynote36_NestedMenber {
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

        //
        OuterClass02.m1();
    }

}
/*
成员内部类
说明：成员内部类定义在外部类的成员位置(不在代码块和其他方法中)，并没有 static 修饰
1.可以直接访问外部类的所有成员，包含私有的
2.可以添加任意访问修饰符（public、protected、默认、private）因为它的地位就是一个成员。
3.作用域：和外部类的其他成员一样，为整个类体
比如前面案例，在外部类的成员方法中创建成员内部类对象，再调用方法
4.成员内部类---访问---->外部类（比如：属性）[访问方式：直接访问]
5.外部类---访问------>内部类（说明）[访问方式：创建对象，再访问]
6.外部其他类--访问---->成员内部类[两种方法]
7.如果外部类和内部类的成员重名时，内部类访问的话，默认遵循就近原则，如果想访问外部类的成员，
则可以使用（外部类名.this.成员）去访问
 */

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

/*
静态内部类
说明：静态内部类是定义在外部类的成员位置，并且有static修饰
1.可以直接访问外部类的所有静态成员，包含私有的，但不能直接访问静态成员
2.可以添加任意访问修饰符（public、protected、默认、private）.因为它的地位就是一个成员
3.作用域：同其他的成员，为整个类体
4.静态内部类 -访问-> 外部类（比如：静态属性）[访问方式：直接访问所有静态成员]
5.外部类 -访问-> 静态内部类[访问方式：创建对象，再访问]
6.外部其他类 -访问-> 静态内部类[
7，如果外部类和静态内部类的成员重名时，静态内部类访问的时，默认遵循就近原则，如果想访问外部类的成员，则可以使用（外部类名成员）去访问
 */

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
