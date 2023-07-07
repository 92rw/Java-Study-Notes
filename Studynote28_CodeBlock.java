/*
代码化块又称为初始化块，属于类中的成员[包装类（enclosing class）/外部类的一部分]，类似于方法，将逻辑语句封装在方法体中，通过{}包围起来
但和方法不同，没有方法名，没有返回，没有参数，只有方法体，而且不用通过对象或类显式调用，而是加载类时，或创建对象时隐式调用。

//代码块其实就是匿名方法，本质是还是方法

1）相当于另外一种形式的构造器（对构造器的补充机制，可以做初始化的操作
2）应用场景：多个构造器中都有重复的语包，可以抽取到初始化块中，提高代码的重用性’
3）代码块的调用优先于构造器

●基本语法
[修饰符]{
代码
};


1）修饰符 可选，要写的话，也只能写static
2）代码块分为两类，使用static修饰的叫静态代码块，没有static修饰的，叫普通代码块/非静态代码块。
3）逻辑语句可以为任何逻辑语句（输入、输出、方法调用、循环、判断等）
4）;号可以写上，也可以省略。

●注意细节
1）static代码块也叫静态代码块，作用就是对类进行初始化，而且它随着类的加载而执行，
并且只会执行一次。如果是普通代码块，每创建一个对象，就执行一次

2）类什么时候被加载
①创建对象实例时（new）
②创建子类对象实例，父类也会被加载
③使用类的静态成员时（静态属性，静态方法）
（就是类加载时，要看看有没有静态，会先从超类往本类开始找静态，找完所有的静态后，又开始从超类开始找普通代码块和构造器，
刚刚也说了，构造器在普通后面，父类的普通找完后，再找构造器之后就会往下找普通和构造器）

3）普通的代码块，在创建对象实例时，会被隐式的调用。被创建一次，就会调用一次。
如果只是使用类的静态成员时，普通代码块并不会执行

4）创建一个对象时，在一个类调用顺序是：（重点，难点）：
①调用静态代码块和静态属性初始化（注意：静态代码块和静态属性初始化调用的优先级一样，
如果有多个静态代码块和多个静态变量初始化，则按他们定义的顺序调用）
③调用普通代码块和普通属性的初始化（注意：普通代码块和普通属性初始化调用的优先级一样，
如果有多个普通代码块和多个普通属性初始化，则按定义顺序调用)
③调用构造方法

5）构造器的最前面其实隐含了super()和调用普通代码块。
静态相关的代码块，属性初始化，在类加载时，就执行完毕，因此是优先于构造器和普通代码块执行的

6）我们看一下创建一个子类时（继承关系），他们的静态代码块，静态属性初始化，普通代码块，普通属性初始化，构造方法的调用顺序如下：
①父类的静态代码块和静态属性（优先级一样，按定义顺序执行）
②子类的静态代码块和静态属性（优先级一样，按定义顺序执行）
③父类的普通代码块和普通属性初始化（优先级一样，按定义顺序执行）
④父类的构造方法
③子类的普通代码块和普通属性初始化（优先级一样，按定义顺序执行）
③子类的构造方法儿

7）静态代码块只能直接调用静态成员（静态属性和静态方法），普通代码块可以调用任意成员。

 */

class BlockC {
    public static int n1 = 888;
    static {
        System.out.println("超类静态代码块");
    }

    {
        System.out.println("超类非静态代码块");
    }
    public BlockC(){
        System.out.println("超类构造器");
    }
    public BlockC(String str){
        System.out.println("超类的有参构造器");
    }
}

class BlockB extends BlockC{
    static {
        System.out.println("父类静态代码块");
    }
    {
        System.out.println("父类非静态代码块");
    }

    public BlockB(){
        System.out.println("父类构造器");
    }
    public BlockB(String str){
        super(str);
        System.out.println("父类的有参构造器");
    }
}

class BlockA extends BlockB {
    static{
        System.out.println("子类静态代码块");
    }

    {
        System.out.println("子类非静态代码块");
    }

    public BlockA(){
        this("子类有参构造器");
        System.out.println("子类构造器");
    }
    public BlockA(String str){
        super(str);
        System.out.println("子类的有参构造器");
    }

    public static String b1 = "子类静态属性";
}

class BlockD{
    //无参构造器优先级最低
    public BlockD() {
        System.out.println("无参构造器被调用");
    }
    private static int n1 = getN1();
    //静态代码块和静态属性优先级一样，但因为属性在前因此先被调用
    static {
        System.out.println("静态代码块被调用");
    }
    public static int getN1(){
        System.out.println("静态方法被调用");
        return 180;
    }

    {
        System.out.println("普通代码块被调用");
    }
    private int n2 = getN2();
    //普通代码块和普通属性优先级一样，但因为代码块在前因此先被调用
    public int getN2(){
        System.out.println("普通方法被调用");
        return 180;
    }
}
/*
加载类时是先加载类信息在去初始化对象的。加载类的信息就要先加载静态的部分，
初始化类时先隐式调用super在调用普通代码块最后是构造方法。
 */
public class Studynote28_CodeBlock {
    public static void main(String[] args) {
        BlockA a1 = new BlockA("haha");
        //BlockA a2 = new BlockA();
        System.out.println("==================");
        BlockD d = new BlockD();
        System.out.println("==================");
        BlockE e = new BlockE();
    }
}

class BlockE{
    private static int n3 = getN3();
    {
        System.out.println("静态代码块");
    }
    public static int getN3() {
        System.out.println("非静态方法");
        return 180;
    }
}