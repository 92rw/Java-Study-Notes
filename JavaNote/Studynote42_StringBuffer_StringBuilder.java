public class Studynote42_StringBuffer_StringBuilder {
    public static void main(String[] args) {
        //默认创建大小为 16 的 char[]，存放字符内容
        StringBuffer stringBuffer1 = new StringBuffer();

        //通过构造器指定 char[] 大小
        StringBuffer stringBuffer2 = new StringBuffer(100);

        //通过输入 String 创建 StringBuffer
        StringBuffer stringBuffer3 = new StringBuffer("Hello");
    }
}
/*
java.lang.StringBuffer代表可变的字符序列，可以对字符串内容进行增删，final类无法继承
很多方法与String相同，但StringBuffer是可变长度的。
StringBuffer是一个容器。继承 AbstractStringBuilder，实现Serializable,Appendable,CharSequence(和StringBuilder一样)
父类有属性 char[] value 不是final，用于存放字符串内容

参加算法类的比赛时为了减少运行时间经常用这个

String VS StringBuffer
1）String保存的是字符串常量，里面的值不能更改，每次String类的更新实际上就是更改地址，效率较低
//private final char value[];
2）StringBuffer保存的是字符串变量，里面的值可以更改，每次StringBuffer的更新实际上可以更新内容，不用每次更新地址，
效率较高//char[] value;//这个放在堆，不需要每次更换地址（创建新对象）

StringBuffer对象在创建时，就会分配一个大小为传入字符串+16的字符数组，所以后续修改的时候，在原数组上操作即可。
除非修改后的字符串大小超过字符数组的长度，这时才需要分配新的字符数组
 */

class StringBufferConvert{
    public static void main(String[] args) {
        //String -> StringBuffer
        String str = "Hello World";
        //方式1：使用构造器
        //返回的是StringBuffer对象，对str本身没有影响
        StringBuffer sb1 = new StringBuffer(str);
        //方式2：使用append方法（小于16字符时，比上一种创建的长度更小）
        StringBuffer sb2 = new StringBuffer();
        sb2 = sb2.append(str);

        //StringBuffer -> String
        StringBuffer sb = new StringBuffer("Stringbuffer convert to String");
        //方式1：使用构造器
        String s1 = new String(sb);
        //方式2：使用StringBuffer 提供的 toString() 方法
        String s2 = sb.toString();

    }
}

/*
●StringBuffer类常见方法
1)增append
2)删delete(start,end)
3）改replace(start,end,string)//将start----end间的内容替换掉，不含end
4）查indexOf//查找子串在字符串第1次出现的索引如果找不到返回-1
5)插insert
6）获取长度length
 */

class StringBufferMethod{
    public static void main(String[] args) {

        StringBuffer s = new StringBuffer("hello");
        //增
        s.append(',');// "hello,"
        s.append("张三丰");//"hello,张三丰"
        s.append("赵敏").append(100).append(true).append(10.5);//"hello,张三丰赵敏100true10.5"
        System.out.println(s);//"hello,张三丰赵敏100true10.5"


        //删
        /*
          删除索引为>=start && <end 处的字符
          解读: 删除 11~14的字符 [11, 14)左闭右开区间
         */
        s.delete(11, 14);
        System.out.println(s);//"hello,张三丰赵敏true10.5"

        //改
        //使用 周芷若 替换 索引9-11的字符 [9,11)左闭右开区间
        s.replace(9, 11, "周芷若");
        System.out.println(s);//"hello,张三丰周芷若true10.5"

        //查找指定的子串在字符串第一次出现的索引，如果找不到返回-1
        int indexOf = s.indexOf("张三丰");
        System.out.println(indexOf);//6

        //插
        //将字符串插在当前下标9的前面，原索引自动后移
        s.insert(9, "赵敏");
        System.out.println(s);//"hello,张三丰赵敏周芷若true10.5"
        //长度
        System.out.println(s.length());//22
        System.out.println(s);

    }
}

class StringBufferExercise1{
    public static void main(String[] args) {
        String str = null;
        StringBuffer sb = new StringBuffer();
        sb.append(str);//根据源码，调用父类 AbstractStringBuilder 的appendNull 方法，执行后得到字符串"null"
        System.out.println(sb.length());
        System.out.println(sb);

        //调用底层源码 super(str.length() + 16)，抛出空指针异常
        StringBuffer sb1 = new StringBuffer(str);
        System.out.println(sb1);
    }
}
/*
练习实例2：对整数超过三位的金额数值自动加逗号
思路分析：使用到 StringBuffer 的insert， 需要将 String 转成 StringBuffer
 */
class StringBufferExercise2{
    public static void main(String[] args) {
        String price = "7125614.59";
        //格式处理：保证输入的价格带两位小数
        if (price.indexOf('.') == -1 ) price += ".00";
        StringBuffer sb = new StringBuffer(price);
        //找到小数点索引，在该位置前3位插入逗号

        for (int i = sb.lastIndexOf(".") - 3; i > 0; i -= 3) {
            sb = sb.insert(i, ",");
        }
        System.out.println(sb);
    }
}

/*
StringBuilder类
1）一个可变的字符序列。此类提供一个与StringBuffer兼容的APl，但不保证同步。（存在多线程问题）
该类被设计用作StringBuffer的一个简易替换，用在字符串缓冲区被单个线程使用的时候。
如果可能，建议优先采用该类，因为在大多数实现中，它比StringBuffer要快。
2）在StringBuilder上的主要操作是append和insert方法，可重载这些方法以接受任意类型的数据

继承 AbstractStringBuilder，实现Serializable,Appendable,CharSequence(和StringBuffer一样)
均代表可变的字符序列，方法是一样的
特点：1.StringBuilder是 final 类，不能被继承
2.继承了 AbstractStringBuilder 属性char[] value，在堆中存放字符序列
3.实现了Serializable接口，可以串行化（网络传输和保存到文件）
4.各个方法没有做互斥的处理，即没有 synchronized 关键字，因此在单线程下使用

●String、StringBuffer和StringBuilder的比较
1）StringBuilder和StringBuffer非常类似，均代表可变的字符序列，而且方法也一样
2）String：不可变字符序列，效率低，但是复用率高。
//任意个String对象都可以指向常量池的同一个String ，即复用率高；但是因为是final的所以
每次更改内容都要创建一个新对象，改变String对象的指向，即效率低
3）StringBuffer：可变字符序列、效率较高（增删）、线程安全
4）StringBuilder：可变字符序列、效率最高、线程不安全
5）String使用注意说明：
strings="a";//创建了一个字符串
s+="b"；//实际上原来的"a"字符串对象已经丢弃了，现在又产生了一个字符串s+"b”（也就是"ab）。
如果多次执行这些改变串内容的操作，会导致大量副本字符串对象存留在内存中，降低效率。
如果这样的操作放到循环中，会极大影响程序的性能
=>结论：如果我们对String做大量修改，不要使用String


●使用的原则，结论
1.如果字符串存在大量的修改操作，一般使用StringBuffer或StringBuilder
2.如果字符串存在大量的修改操作，并在单线程的情况，使用StringBuilder
3.如果字符串存在大量的修改操作，并在多线程的情况，使用StringBuffer
4.如果我们字符串很少修改，被多个对象引用，使用String，比如配置信息等
 */

class StringTimeTest {
    public static void main(String[] args) {
        long startTime = 0L;
        long endTime = 0L;

        //耗时最短
        StringBuilder builder = new StringBuilder("");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 40000; i++) {//StringBuilder 拼接 20000次
            builder.append(String.valueOf(i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("StringBuilder的执行时间：" + (endTime - startTime));


        //耗时次短
        StringBuffer buffer = new StringBuffer("");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 40000; i++) {//StringBuffer 拼接 20000次
            buffer.append(String.valueOf(i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("StringBuffer的执行时间：" + (endTime - startTime));


        //耗时最长
        String text = "";
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 40000; i++) {//String 拼接 20000
            text = text + i;
        }
        endTime = System.currentTimeMillis();
        System.out.println("String的执行时间：" + (endTime - startTime));

    }
}
