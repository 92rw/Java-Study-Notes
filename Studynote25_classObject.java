//问来不来得及就没必要学了，真的有毅力愿意坚持达成目标，为什么要问来不来得及呢？
// 世界上没有太晚导致的来不及，只有不够努力导致的来不及

/*
类 Object 是类层次结构的根类。每个类都使用 Object 作为超类。所有对象（包括数组）都实现这个类的方法
比较常用的方法有：
equals(Object); //指示其他某对象是否与此对象“相等”。
finalize()  //当垃圾回收器确定不存在对该对象的更多引用时，由对象的垃圾回收器调用此方法
getClass()  //返回此 Object 的运行时类
toString()  //返回该对象的字符串表示
 */

public class Studynote25_classObject {
    public static void main(String[] args) {
        //演示 hashCode 方法
        hashA hashA1 = new hashA();
        hashA hashA2 = new hashA();
        hashA hashA3 = hashA1;
        System.out.println("hashA1.hashCode= " + hashA1.hashCode());
        System.out.println("hashA2.hashCode= " + hashA2.hashCode());
        System.out.println("hashA3.hashCode= " + hashA3.hashCode());

        //演示 toString 方法
        Monster monster = new Monster("小钻风", "巡山", 1000);
        System.out.println(monster.toString() + "\thashCode= " + monster.hashCode());
        //当直接输出一个对象时，会默认调用 对象名.toString()
        System.out.println(monster);

        //演示 finalize 方法
        monster = null; //此处不会运行垃圾回收机制的代码，因为空间不是立刻被回收
        System.gc();//主动调用垃圾回收器
        System.out.println("唐僧师徒离开了狮驼岭");//这句话比垃圾回收的运行先完成（线程异步），不必等待上一句代码完成
    }
}
class hashA{}
/*
hashCode 方法
1）提高具有哈希结构的容器的效率！
2）两个引用，如果指向的是同一个对象，则哈希值肯定是一样的！
3）两个引用，如果指向的是不同对象，则哈希值是不一样的（总结：Hash值是地址的映射）
4）哈希值主要根据地址号来的！，不能完全将哈希值等价于地址
//java跑在虚拟机上，没有办法拿到内部的地址
5)案例演示[HashCode_java]:obj.hashCode([测试:Aobj1=newA0;Aobj2=newAO:A
obj3=obj1]
6）后面在集合，中hashCode如果需要的话，也会重写
 */
class Monster {
    private String name;
    private String job;
    private double sal;

    public Monster(String name, String job, double sal) {
        this.name = name;
        this.job = job;
        this.sal = sal;
    }

    //重写 toString 方法，输出对象的属性
    //使用快捷键Alt+Insert -> toString
    @Override
    public String toString() { //默认重写后，一般是输出对象的属性，也可定制其他
        return "Monster{" +
                "name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", sal=" + sal +
                '}';
    }
    //重写finalize方法
    //键盘输入f，其余程序可自行生成
    @Override
    protected void finalize() throws Throwable {
        //super.finalize();//系统自动生成的代码：调用父类的finalize
        System.out.println(name + " 已被孙猴子打死");
    }
}

/*
toString 方法
基本介绍
默认返回：全类名（包名+类名）+@+哈希值的十六进制，【查看Object的toString方法】
子类往往重写toString方法，用于返回对象的属性信息

2.重写toString方法，打印对象或拼接对象时，都会自动调用该对象的toString形式

3.当直接输出一个对象时，toString方法会被默认的调用
 */

/*
finalize 方法(java9 之后不推荐使用)
当垃圾回收器确定不存在对该对象的更多引用时，由对象的垃圾回收器调用此方法
1.当对象被回收时，系统自动调用该对象的finalize方法。子类可以重写该方法做一些释放资源的操作
2.什么时候被回收：当某个对象没有任何引用时，则jvm就认为这个对象是一个垃圾对象，就会使用垃圾回收机制来销毁该对象，
在销毁该对象前，会先调用finalize方法。程序员可以在finalize中写自己的业务逻辑代码（释放资源，数据库连接，打开文件）
如果不重写finalize，就会调用 Object 中的finalize，重写后可实现自己的逻辑
3.垃圾回收机制的调用，是由系统来决定（GC算法），也可以通过System.gc()主动触发垃圾回收机制（不是100%触发）

 */