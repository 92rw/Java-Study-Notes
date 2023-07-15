public class Studynote30_final {
    public static void main(String[] args) {
        Final01 f = new Final01();//不能继承的final类，可以实例化
        System.out.println(Final02.sqrt2);
        System.out.println(Final02.sqrt3);//执行final static时不执行静态代码块

        int radius = 2;
        double area = new FinalCircle(radius).calArea();
        System.out.println("半径为 " + radius + " 的圆面积为"+ area);
    }
}

class FinalCircle{
    private double radius;
    private final double PI;

    public FinalCircle(double radius) {
        this.radius = radius;
        PI = 3.1415926;//在构造器中给 final 属性赋值
    }

    public double calArea(){
        return PI * radius * radius;
    }
}
/*
final关键字：可以修饰类、属性、方法和局部变量
1.final修饰类表示不可被继承 2.final修饰方法表示不可被重写 3.final修饰属性，表示不可被修改 4.final修饰变量，表示不可被修改（即为常量）
在某些情况下，程序员可能有以下需求，就会使用到final：

1）当不希望类被继承时，可以用final修饰
2）当不希望父类的某个方法被子类覆盖/重写（override）时，可以用final关键字修饰
案例演示：访问修饰符 final 返回类型 方法名
3）当不希望类的的某个属性的值被修改,可以用final修饰
案例演示：public final double TAX_RATE = 0.08
//private是访问修饰符，你只是不能直接访问修改，通过提供的方法可以修改；final是直接不能修改
//所以虽然private不提供set方法也可以做到让人无法修改，但是private的属性和方法没办法被子类调用
4）当不希望某个局部变量被修改，可以使用final修饰

注意事项：
1）final修饰的属性又叫常量，一般用XX_XX_XX来命名
2）final修饰的属性在定义时必须赋初值并以后不能再修改，赋值可以在如下位置之一选择一个位置赋初值即可）：
①定义时：如 public final double TAX_RATE=O.08
②在构造器中
③在代码块中
3）如果fina修饰的属性是静态的，则初始化的位置只能是
①定义时 ③在静态代码块 不能在构造器中赋值。
//静态属性是在调用【类】的时候创建的，而构造器的调用需要创建【对象实例】
//构造器不可以是静态的，因为构造器中隐含了this和super
4）final修饰的类，不能被继承，可以实例化
final修饰的函数不能被重写，可以被继承
5）一般来说，如果一个类已经是final类了，就没有必要再将方法修饰成final方法
6）final不能修饰构造方法（即构造器）
7）final和static往往搭配使用，效率更高，不会导致类加载。底层编译器做了优化处理
8）包装类（lntegerDouble，Float，Boolean等都是final),String也是final类
 */

final class Final01{
    public static final double CONST_NUMBER;
    static {
        CONST_NUMBER = 2.71;
    }
}

class Final02{
    public final static double sqrt2 = 1.414;
    static {
        System.out.println("静态代码块被执行");
    }
    public final static double sqrt3 = 1.732;//执行此代码不会导致类加载
}
/*
类加载的过程：1.载入(JVM 在该阶段的主要目的是将字节码从不同的数据源（可能是 class 文件、也可能是 jar 包，甚至网络）

3.验证(JVM 会在该阶段对二进制字节流进行校验，只有符合 JVM 字节码规范的才能被 JVM 正确执行)
4.准备(JVM 会在该阶段对类变量（也称为静态变量，`static` 关键字修饰的）分配内存并初始化（对应数据类型的默认初始值，如 0、0L、null、false 等）)
(4.准备的注释)4.1作用：为类变量分配内存&设置类变量的初始值
(4.准备的注释)4.2具体流程：1为类的static变量在方法区中分配内存 2.将上述变量的初始值设置为0(非开发者定义的值)
5.解析(解析阶段是虚拟机将常量池内的符号引用替换为直接引用的过程。符号引用就是一组符号来描述目标，可以是任何字面量；直接引用就是直接指向目标的指针、相对偏移量或一个间接定位到目标的句柄。
6.(续5)解析动作主要针对类或接口、字段、类方法、接口方法、方法类型、方法句柄和调用点限定符这7类符号引用进行。下面将讲解前面4种引用的解析过程。)
7.初始化(初始化是指为类的静态变量赋予正确的初始值，JVM负责对类进行初始化，主要对类变量进行初始化)
8.由于为常量，故此类型的已经定义了值的常量，从准备阶段就已经被直接赋予了开发者定义的值，并没有进入到解析和初始化阶段，故此类中的其他静态变量和静态代码块都不会被调用。

 */
