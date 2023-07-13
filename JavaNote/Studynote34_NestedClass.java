//局部内部类 匿名内部类 成员内部类 静态内部类

public class Studynote34_NestedClass {
    public static void main(String[] args) {
        //局部内部类
        NestedLocal n1 = new NestedLocal();
        n1.m1();
        System.out.println("运行时创建的对象 hashCode 为 " + n1);

        System.out.println("=========================");
    }
}
/*
●类的五大成员：属性、方法、构造器、代码块、内部类

package 包名
class 类名 extends 父类 implements 接口名{
    成员变量/属性
    构造方法/构造器
    成员方法/方法
    代码块
}


内部类：定义在类里面的类。最大的特点就是可以直接访问私有属性，并且可以体现类与类之间的包含关系

●基本语法
class Outer{//外部类
    class Inner{//内部类

    }
}
class Other{}//其他类

●内部类的分类
>定义在外部类局部位置上（比如方法内）：
1）局部内部类（有类名）
2）匿名内部类（没有类名，重点）

>定义在外部类的成员位置上：
1）成员内部类（没用static修饰）
2）静态内部类（使用static修饰）

 */

/*
局部内部类
说明：局部内部类是定义在外部类的局部位置，比如方法中，并且有类名。
1，可以直接访问外部类的所有成员，包含私有的
2.不能添加访问修饰符，因为它的地位就是一个局部变量。局部变量是不能使用修饰符的。
但是可以使用final修饰，因为局部变量也可以使用final
3.作用域：仅仅在定义它的方法或代码块中
4.局部内部类 --访问->外部类的成员[访问方式：直接访问]
5.外部类 -访问-> 局部内部类的成员[访问方式：创建对象，调用方法（注意：必须在作用域内）]
6.外部其他类 -不能访问-> 局部内部类（因为局部内部类地位是一个局部变量）
7.如果外部类和局部内部类的成员重名时，默认遵循就近原则，如果想访问外部类的成员，则可以使用（外部类名.this.成员）去访问
System.out.println（"外部类的n2="+外部类名.this.n2）

//因为在方法中，所以当成方法的一个局部变量，不过是以类的形式而存在的，所以可以访问外部类的成员
//但是不能添加访问修饰符，作用域也仅仅在定义它的方法或代码中，要使用也只能在方法中实例化对象

//外部类通过在方法体中创建局部内部类的实例化 调用局部内部类成员 的成员，然后在主方法中
//创建外部类的实例，调用，方法，方法里有内部类的实例，然后内部类的实例最后调用内部类的

1：个人理解：使用局部内部类的情况为当一个类里面需要反复使用到某段方法时，可以封装成一个类给该类内部使用，
2：内部类是私有的给自己内部使用的，还可以使用final线程安全上方法也有所不同

●关于局部内部类需要记住的内容：
1）定义域：方法体/代码块
2）作用域：方法体/代码块
3）本质仍然是一个类
 */
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

/*
练习实例1
1.编一个类A，在类中定义局部内部类B，B中有一个私有常量 NAME ，有一个方法show()打
印常量name。进行测试
2.进阶：A中也定义一个私有的变量 name，在show方法中打印测试
 */

class LocalA{
    private String name = "variable A";
    public void f1(){
        class B{
            private final String NAME = "constant B";//常量用 final 修饰，用大写命名
            public void show(){
                System.out.println("局部内部类 NAME = " + NAME + "，外部类 name = " + name);//如果重名，需要 外部类名.this.属性名
            }
        }
        B b = new B();//局部内部类需要在方法体中创建此类，否则无法调用内部类的功能
        b.show();
    }
}
class LocalExercise{
    public static void main(String[] args) {
        new LocalA().f1();
    }
}


