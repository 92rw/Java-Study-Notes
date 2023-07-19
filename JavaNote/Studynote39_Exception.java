//如果程序员，认为一段代码可能出现异常/问题，可以使用try-catch异常处理机制来解决
//你的程序是你写的，但是外部的输入你是管不了的，有可能是 无效的输入 或者 错的输入，
// 所以你的程序应当考虑到有可能出现的异常，并捕获处理它，而不是让程序崩溃
//保证程序的健壮性，避免一个不算致命的问题，就导致整个系统崩溃
//springboot的这个配置文件，底层应该也是这样读取的吧
//使用try-catch方法：将可能出错的代码选中，快捷键 Ctrl+Alt+T 选6；抛出异常到类定义中：快捷键Alt+Shift+Enter
//编程的思想是，把可能会发生的事情都要预想到，有可能文件不存在，文件名不对，这都会发生异常，所以要抛出


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Studynote39_Exception {
}
/*
●基本概念
Java 语言中，将程序执行中发生的不正常情况称为“异常”。（开发过程中的语法错误和逻辑错误不是异常）
●执行过程中所发生的异常事件可分为两类
1）Error（错误）：Java 虚拟机无法解决的严重问题。如：JVM 系统内部错误、资源耗尽等严重情况。
比如：StackOverflowError[栈溢出]和 OOM(out of memory)，Error 是严重错误，程序会崩溃。
2）Exception：其它因编程错误或偶然的外在因素导致的一般性问题，可以使用针对性的代码进行处理。
例如空指针访问，试图读取不存在的文件，网络连接中断等等
Exception分为两大类：运行时异常[程序运行时发生]和编译时异常[编程时，编译器检查出 的异常]。


●异常体系图
             Object  Serializable（可序列化接口）
                 ↑      ⇡
                Throwable
      ----------- ↗  ↖------------------
      |                                 |
    Error严重错误                    Exception可以捕获的异常
      ↑                           ↗                ↖
StackOverflowError      RuntimeException运行时异常  编译异常（FileNotFoundException等）
OutOfMemoryError                ↑
                        NullPointerException空指针异常
                        ArithmeticException算数异常
                        ArrayIndexOutOfBoundsException数组索引越界异常
                        ClassCastException类型转换异常
                        NumberFormatException数字格式异常
1.异常分为两大类，运行时异常和编译时异常
2.运行时异常，编译器不要求强制处置的异常。一般是指编程时的逻辑错误，是程序员应该避免其出现的异常。
java.lang.RuntimeException类及它的子类都是运行时异常
3.对于运行时异常，可以不作处理，因为这类异常很普遍，若全处理可能会对程序的可读性和运行效率产生影响
4.编译时异常，是编译器要求必须处置的异常


 */


//常见的运行时异常举例
//1)NullPointerException 空指针异常：当应用程序试图在需要对象的地方使用 null 时，抛出该异常
class NullPointerExceptionExample{
    public static void main(String[] args) {
        String name = null;
        System.out.println(name.length());
    }
}
//2)ArithmeticException 算数异常：
// 当出现异常的运算条件时，抛出此异常。例如，一个整数“除以零”时，抛出此类的一个实例
class ArithmeticExceptionExample{
    public static void main(String[] args) {
        int num1 = 15;
        int num2 = 0;
        double num3 = num1/ num2;
    }
}

//3)ArrayIndexOutOfBoundsException 数组索引越界异常:
// 用非法素引访问数组时抛出的异常。如果素引为负或大于等于数组大小，则该索引为法索引
class ArrayIndexOutOfBoundsExceptionExample{
    public static void main(String[] args) {
        int[] arr = {1,2,4};
        for (int i = 0; i <= arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}

//4)ClassCastException 类型转换异常：当试图将对象强制转换为不是实例的子类时，抛出该异常。
class CastExampleA {}
class CastExampleB extends CastExampleA {}
class CastExampleC extends CastExampleA {}
class ClassCastExceptionExample{
    public static void main(String[] args) {
        CastExampleA a = new CastExampleB();//向上转型
        CastExampleB b = (CastExampleB)a;//向下转型
        CastExampleC c = (CastExampleC)a;//类型转换异常
    }
}

//5）NumberFormatException 数字格式不正确异常：当应用程序试图将字符串转换成一种数值类型，
//但该字符串不能转换为适当格式时，抛出该异常 => 使用异常我们可以确保输入是满足条件数字
class NumberFormatExceptionExample{
    public static void main(String[] args) {
        String name = "Name";
        int num = Integer.parseInt(name);//并不是语法错误。如果你弄一个switch，里面有不一样的类型的name的赋值就有可能能运行
        System.out.println(num);
    }
}


/*
编译异常
●介绍
编译异常是指在编译期间，就必须处理的异常，否则代码不能通过编译

常见的编译异常（一般发生在网络、数据库、文件操作时）
SQLException    //操作数据库时，查询表可能发生异常
IOException     //操作文件时，发生的异常
FileNotFoundException   //当操作一个不存在的文件时，发生异常
ClassNotFoundException  //加载类，而该类不存在时，异常
EOFException    //操作文件，到文件末尾，发生异常
IllegalArguementException   //参数异常
 */

class FileNotFoundExceptionExample{
    public static void main(String[] args) {
        try {
            FileInputStream fis;
            fis = new FileInputStream("d:\\aa.jpg");
            int len;
            while ((len=fis.read()) != -1){
                System.out.println(len);
            }
            fis.close();
        } catch (IOException e) {
            //如果没发生异常，catch代码块不执行
            System.out.println(e.getMessage());
        }
    }
}

/*
异常处理
异常处理就是当异常发生时，对异常处理的方式，运行时二选一

●异常处理的方式
1. try-catch-finally程序员在代码中捕获发生的异常，自行处理
        try{
            //可能出错的代码块
        }catch (Exception e){
            //当异常发生时，系统将异常封装成 IOException 对象 e，传递给 catch
            //这里的异常Exception e用到了多态，反正出现的异常都是这个的子类
            //得到异常对象后，程序员可自行处理
        }finally{
            //不管 try 代码块是否有异常发生，始终执行此代码
            //此代码块除了关闭JVM都会执行，也就是说保证某段代码一定会执行就放在finally里面
            //通常将释放资源的代码放在此处执行
        }
●使用细节
    1）如果异常发生了，则异常发生后面的代码不会执行，直接进入到catch块
    2）如果异常没有发生，则顺序执行try的代码块，不会进入到catch
    3）如果希望不管是否发生异常，都执行某段代码（比如关闭连接，释放资源等）
    4）可以有多个catch语句，捕获不同的异常（进行不同的业务处理），要求父类异常在后，子类异常在前
    比如（Exception 在后，NullPointerException 在前），如果发生异常，只会匹配一个 catch
    5）可以进行try-finall配合使用，这种用法相当于没有捕获异常，因此程序会直接崩掉。应用场景：不管是否异常都必须执行某个业务逻辑（finally 外的在崩溃后不会执行）
●执行顺序
    1）如果没有出现异常，则执行try块中所有语句，不执行catch块中语句，如果有finally，最后还需要执行finally里而的语句
    2）如果出现异常，则try块中异常发生后，try块剩下的语句不再执行。将执行catch块中的语句，如果有finally，最后还需要执行finally里而的语句！

2. throws
如果没有采用try-catch，默认采用这个方式
将发生的异常抛出，交给调用者（方法）来处理，最顶级的处理者就是JVM
JVM的处理方式：输出异常信息，退出程序

throw异常处理
●基本介绍
1）如果一个方法（中的语句执行时）可能生成某种异常，但是并不能确定如何处理这种异常，则此方法应显示地声明抛出异常，
表明该方法将不对这些异常进行处理，而由该方法的调用者负责处理。
2）在方法声明中用throws语句可以声明抛出异常的列表，throws后面的异常类型可以是方法中产生的异常类型，也可以是它的父类，也可以是异常列表

●注意事项和使用细节
1）对于编译异常，程序中必须处理，比如try-catch或者throws
2）对于运行时异常，程序中如果没有处理，默认就是throws的方式处理
3）子类重写父类的方法时，对抛出异常的规定：子类重写的方法，所抛出的异常类型要么和父类抛出的异常一致，要么为父类抛出的异常的类型的子类型
4）在throws过程中，如果有方法try-catch，就相当于处理异常，就可以不必throws
 */

class ThrowExceptionExample {
    public static void main(String[] args) {

    }
    public static void f1() {
        //f2();//因为 f2() 抛出的是编译异常，这时要求 f1() 方法必须处理编译异常。要么try-catch，要么继续throw
        f3();//因为 f2() 抛出运行异常，java 对运行异常有默认处理机制，不会报错
    }
    public static void f2() throws FileNotFoundException,NullPointerException {//让方法的调用者处理，可以抛出多种
        FileInputStream fis = new FileInputStream("d://aa.txt");
    }
    public static void f3() throws ArithmeticException{}
}


//代码案例1：结合return 语句，了解返回值的顺序
class ExceptionExample01 {
    public static int method() {
        int i = 1;//i = 1
        try {
            i++;// i=2
            String[] names = new String[3];
            if (names[1].equals("tom")) { //空指针
                System.out.println(names[1]);
            } else {
                names[3] = "name";
            }
            return 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            return 2;
        } catch (NullPointerException e) {
            return ++i;  // i = 3 => 保存临时变量 temp = 3;
        } finally {
            ++i; //i = 4
            System.out.println("i=" + i);// i = 4，此处没有return
        }
    }

    public static void main(String[] args) {
        System.out.println(method());// 3
    }
}

//如果用户输入的不是一个整数，京就提示他反复输入，直到输入一个整数为止
class ExceptionExample02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = 0;
        String inputStr = "";

        System.out.println("请输入一个整数");

        while (true){
            try{
                inputStr = scanner.next();
                num = Integer.parseInt(inputStr);
                break;
            } catch(Exception e) {
                System.out.println("需要输入整数，请重新输入");
            }
        }
        System.out.println("你输入的整数是 " + num);
    }
}
/*说明：此处如果使用nextInt() 会无限循环报错，解决方法是用在catch里面调用next方法清除缓存值，或者把scan放循环里每次造新的
当我们再次要求用户输入时，该内存中的数据并没有被取出，系统会以为内存中的数据是用户输入的，就直接读取内存中的数据，因为还是nextInt()读取数据，读取不出来，所以会一直循环。

我们创建scanner时，系统会创建内存，我们输入的字母会先存入该内存，当我们使用nextInt()调用时，因为是要调用int类型，所以会报错。
 */

/*
自定义异常
●基本概念
当程序中出现了某些“错误”，但该错误信息并没有在Throwable子类中描述处理，这个时候可以自己已设计异常类，用于描述该错误信息。
●自定义异常的步骤
1）定义类：自定义异常类名（程序员自己写）继承Exception或RuntimeException
2）如果继承 Exception，属于编译异常
3）如果继承 RuntimeException，属于运行异常（一般来说，继承RuntimeException）


案例
当我们接收Person对象年龄时,要求范围在 18~120 之间，否则抛出一个自定义异常
（要求继承RuntimeException），并给出提示信息
 */
class CustomException {
    public static void main(String[] args) {
        int age = 121;
        if(!(age >= 18 && age <=120)) {
            throw new AgeException("年龄需要在 18~120 之间");//可以直接throw new RuntimeException("年龄不符合正常范围");
        }
        System.out.println("年龄范围正确");
    }
}

//自定义异常
//一般情况下，自定义异常继承 RuntimeException（运行时异常）好处是，可以使用默认的处理机制
//继承Exception则说明该类可能会是个编译异常，这样在main函数中就一定要显式地抛出AgeBoundaryException异常
class AgeException extends RuntimeException {
    public AgeException(String message){//构造器
        super(message);
    }
}

/*
throw 和 throws 的区别
                 意义              位置       后面跟的东西
throws     异常处理的一种方式     方法声明处      异常类型
throw   手动生成异常对象的关键字    方法体中       异常对象
 */

class ReturnExceptionDemo{
    static void methodA(){
        try {
            System.out.println("进入方法A");
            throw new RuntimeException("制造异常");
        } finally {
            System.out.println("调用方法A的finally");
        }
    }

    static void methodB() {
        try {
            System.out.println("进入方法B");
            return;//return是结束方法，但是先要执行完finally再结束
        } finally {
            System.out.println("调用方法B的finally");
        }
    }

    public static void main(String[] args) {
        try {
            ReturnExceptionDemo.methodA();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ReturnExceptionDemo.methodB();
    }
}

/*
编写应用程序接收命令行的两个参数（整数），计算两数相除

要求：对数据格式不正确(NumberformatException)、缺少命令行参数(ArrayIndexOutOfBoundsException)、
除0(ArithmeticException)进行异常处理

题目解析：1）命令行输入即加载 args，2）double 型除零会得到 Infinity，不会出现算数异常
 */
class DivisionException{
    public static void main(String[] args) {
        try {
            //先验证输入参数的个数是否正确
            if (args.length != 2){
                throw new ArrayIndexOutOfBoundsException("参数个数不正确");
            }

            //将接收到的参数转成整数
            int n1 = Integer.parseInt(args[0]);
            int n2 = Integer.parseInt(args[1]);

            double res = cal(n1,n2);
            System.out.println("计算结果为 " + res);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("参数格式不正确，需要输入整数");
        } catch (ArithmeticException e) {
            System.out.println("出现除0的异常");
        }
    }
    public static double cal(int n1, int n2){
        return n1 / n2;
    }
}