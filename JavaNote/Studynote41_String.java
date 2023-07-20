//public class Studynote41_String {
//    String str = new String("String");
//    //此处把修饰数组的final去掉，不影响结果
//    final char[] ch = {'j','a','v','a'};//数组用final修饰,指的是数组内容指向栈中main方法ch的引用不可更改,并不是指内容
//
//    public void change(String str, char ch[]){//执行代码时，基本数据类型传的值就是实际数据值，引用类型传的值是地址
//        str = "java";//change() 和 main() 创建的对象在不同的栈空间，这条语句没有影响 ex.str 的对象引用
//        //this.str = "java";//根据就近原则，不加this只改变change 方法的str，加入this会改ex对象的str
//        ch[0] = 'L';
//    }
//
//    public static void main(String[] args) {
//        Studynote41_String ex = new Studynote41_String();
//        //调用方法会开一个新栈，方法里的形参是在这个新栈立面创建的变量，新栈里面的str和堆里面的str分别是两个参数
//        //所以新栈里的str会到常量池，重新创建一个字符串常量对象Java，然后当方法执行完毕，这个栈里面的str也销毁了
//        ex.change(ex.str,ex.ch);//
//        System.out.print(ex.str + " and ");
//        System.out.println(ex.ch);
//        //被final修饰的引用变量所指的的对象不能被改变（这是str不改变的原因），被final修饰的引用变量所指的对象的内容可以被改变（这是ch改变的原因）
//    }
//}
///*
//String类
//接口：Comparable,Serializable,CharSequence（JDK17 中另外实现 ConstantDesc 和 Constable）
//实现 Serializable 接口表示可以串行化，即在网络传输
//实现 Comparable 接口表示可以相互比较
//
//1）String对象用于保存字符串，也就是一组字符序列
//2)字符串常量对象是用双引号括起的字符序列。例如："你好"、"12.97"、"boy"等
//3）字符串的字符使用Unicode字符编码，一个字符（不区分字母还是汉字）占两个字节 (char 类的字符也是占用两个自己)
//4) String和所有包装类都是final，不能被继承
//
//5）String类有很多构造器（实现构造器重载），较常用构造方法：
//String s1 = new String();
//String s2 = new String(String original);
//String s3 = new String(char[] a);
//String s4 = new String(char[] a,int startlndex,int count)
//String s5 = new String(byte[] b)
// */
//class StringConstructor{
//    public static void main(String[] args) {
//        String s1 = "Tokyo";
//        String s2 = new String("Tokyo");
//        System.out.println(s1 == s2);//false：s1指向常量池，s2指向堆中的对象。==向值类型判断值，向对象类型判断地址
//        System.out.println(s1.hashCode() == s2.hashCode());//true，String重写了hashcode()方法，只要内容一样，hashcode就一样
//        System.out.println(s1.equals(s2));//true
//        /*
//        当调用intern方法时，如果池已经包含一个等于此String对象的字符串（用equals(Object)方法确定），则返回池中的字符串。
//        否则，将此String对象添加到池中，并返回此String对象的引用
//
//        s.intern() 判断s字符串的内容是否已经存在于常量池中，存在就返回该字符串；不存在就在常量池中创建一个对象保存该字符串，返回这个对象的地址
//        对于任意两个字符串 s 和 t，当且仅当 s.equals(t) 为 true 时，s.intern() == t.intern() 才为 true。
//         */
//        System.out.println(s1 == s2.intern());//true
//        System.out.println(s2 == s2.intern());//false，s2指向堆中对象，s2.intern指向常量池的地址
//    }
//}
///*
//两种创建String对象的区别
//方式1：直接赋值   String s1 = "Tokyo";
//先从常量池查看是否有"Tokyo"数据空间，如果有，直接指向；如果没有则重新创建，然后指向。s1 最终指向的是常量池的空间地址
//
//方式2：调用构造器 String s2 = new String("Tokyo");
//先在堆中创建空间，里面维护了 value 属性，指向常量池的"Tokyo"空间。如果常量池没有"Tokyo"，重新创建；
//如果有，直接通过value指向。最终指向的是堆中的空间地址。
//
//注意：常量池里面的本质也是对象，JDK1.8之后常量池也存储在Java堆里
//
//解读；（1）b.intern方法最终返回的是常量池的地址（对象）：
// */
//
///*
//字符串的特性
//1）String是一个final类，代表不可变的字符序列（修改对象时会创建一个新的对象，原对象内容不会改变）
//2）字符串是不可变的。一个字符串对象一旦被分配，其内容是不可变的
//
//String 有属性 private final char value[]; //jdk1.8及以前String使用的是char数组，jdk1.9及以后使用的是byte数组。
//理解：String对象一旦声明则不能轻易改变，如果要改变则需要先断开原有的对象引用，再开辟新的对象，之后再指向新的对象空间。
//
//这个 value[] 用于存放字符串内容，不可以修改（不能指向其他内存空间，但是指向的内存空间内的数据可以修改
//使用final修饰一个对象引用变量时，该变量指向的对象不能被改变，但是这并不意味着该对象的属性值不能被改变
//其实就是fianl修饰的话，就是值不能修改，而value数组的值就是一组地址，指向字符数组中第一个值的地址，所以value的值（地址值）不可修改而已，而地址值里面指向的真实的值是可以被修改的
//java是值传递。final后数组的值不能修改，但数组内的值可以。因为数组的值指的是地址。数组内的值是地址上的数据。final只是写死了数组对应的地址，但没有限制地址上的数据变化与否
//
//类是final修饰的->类的内容不可改（限制继承和重写）；接收实例化对象的变量没有final->并不能限制String的引用对象，
//
//引用对象可以随便乱指，final String s1="h";你看现在s1能不能乱指。
//
// */
//class StringFeature1{
//    public static void main(String[] args) {
//        String s = "Guten Tag";
//        System.out.println(s.hashCode());
//        //"Guten Tag"地址没有改变，s 的值改了是因为指向了另外一个常量池中的地址
//        s = "Good Morning";
//        System.out.println(s.hashCode());//两个字符串开辟了两个常量池空间
//    }
//}
//
///*
//特性1（常量相加，在池中）
//String a = "Hello" + "abc";
//在常量池中仅创建一个对象，原因是编译器会自动优化：
//判断创建的常量池对象，是否有引用指向。运算顺序从右向左，先拼接再创建
//
//特性2（变量相加，在堆中）
//String a = "Hello";
//String b = "abc";
//String c = a + b;
//第三句代码的执行过程为：
//1）创建一个 StringBuilder sb = StringBuilder();
//2）执行 sb.append("Hello");
//3）执行 sb.append("abc");
//4）String c = sb.toString();
//最终 c 指向堆中的对象 (String) value[] -> 池中 "Helloabc"
//因此在常量池中创建三个对象
//
//此处c == "Helloabc" 返回false，其他版本的jdk中 c == c.intern() 可能返回true：原因是那些版本
//StringBuilder的toString方法中调用的String构造器是用字符数组的 所以不会再常量池中生成对象
//因为当intern()池中没找到字符串，会创建HashtabeEntry对象里面的value指向了c对象，所以true
// */
//class StringFeature2{
//    public static void main(String[] args) {
//        String a = "Hello";
//        String b = "abc";
//        //因为执行字符串拼接时，会默认调用StringBuffer 底层先在堆中创建空间放字符数组指向通过其.append方法在池里进行拼接的
//        String c = a + b;//底层代码：返回堆中的地址
//        String d = (a + b).intern();//指向常量池
//        System.out.println(c == "Helloabc");//false，c指向堆，字符串指向常量池
//        System.out.println(c == c.intern());//false，原因同上
//        System.out.println(d == "Helloabc");
//    }
//}
//
///*
//String类的常见方法
//说明
//String类是保存字符串常量的。每次更新都需要重新开辟空间，效率较低，
//因此java设计者还提供了StringBuilder和StringBuffer来增强String的功能，并提高效率。
//
//equals//区分大小写，判断内容是否相等
//equalsIgnoreCase//忽略大小写的判断内容是否相等(可用于验证码)
//length//获取字符的个数，学符串的长度
//indexOf//获取字符在字符串中第1次出现的索引,索引从0开始，如果找不到返回-1
//lastIndexOf//获取字符在字符串中最后1次出现的索引,索引从0开始，如找不到，返回-1
//substring//截取指定范围的子串
//trim//去前后空格
//charAt：获取某索引处的字符，注意不能使用Str[index]这种方式，因为value属性是private不可在外部类访问
//toUpperCase
//toLowerCase
//concat
//replace//替换字符串中的字符
//split//分割字符串，对于某些分割字符，我们需要转义比如| \\等。例如：古诗词的标点，文件路径
//compareTo//比较两个字符串的大小（用于判断两次输入密码是否一致）
//toCharArray//转换成字符数组
//format//格式字符串，%s字符串 %c字符 %d整型 %.2f浮点型。案例，将一个人的信息格式化输出
// */
//class StringMethod01 {
//    public static void main(String[] args) {
//        //1. equals 经过方法重写. 比较内容是否相同，区分大小写
//        String str1 = "hello";
//        String str2 = "Hello";
//        System.out.println(str1.equals(str2));//
//
//        // 2.equalsIgnoreCase 忽略大小写的判断内容是否相等
//        String username = "johN";
//        if ("john".equalsIgnoreCase(username)) {
//            System.out.println("Same!");
//        } else {
//            System.out.println("Different!");
//        }
//
//        // 3.length 获取字符的个数，字符串的长度
//        System.out.println("Jerry".length());
//
//        // 4.indexOf 获取字符在字符串对象中第一次出现的索引，索引从0开始，如果找不到，返回-1
//        String s1 = "wer@terwe@g";
//        int index = s1.indexOf('@');
//        System.out.println(index);// 3
//        System.out.println("weIndex=" + s1.indexOf("we"));//0
//
//        // 5.lastIndexOf 获取字符在字符串中最后一次出现的索引，索引从0开始，如果找不到，返回-1
//        s1 = "wer@terwe@g@";
//        index = s1.lastIndexOf('@');
//        System.out.println(index);//11
//        System.out.println("ter的位置=" + s1.lastIndexOf("ter"));//4
//
//        // 6.substring 截取指定范围的子串
//        String name = "hello,张三";
//        //下面name.substring(6) 从索引6开始截取后面所有的内容
//        System.out.println(name.substring(6));//截取后面的字符
//        //name.substring(0,5)表示从索引0开始截取，截取到索引 5-1=4位置（前闭后开）
//        System.out.println(name.substring(2,5));//llo
//
//    }
//}
//
//class StringMethod02 {
//    public static void main(String[] args) {
//        // 1.toUpperCase转换成大写
//        String s = "heLLo";
//        System.out.println(s.toUpperCase());//s并没有改变成大写 只是调用了方法输出的是大写而已
//
//        // 2.toLowerCase
//        System.out.println(s.toLowerCase());//返回变成的字符串，要用s接收才会改变s
//
//        // 3.concat拼接字符串：concat会返回一个新的字符串，这和append有区别，append返回的仍然是原对象，空间一样
//        String s1 = "宝玉";
//        s1 = s1.concat("林黛玉").concat("薛宝钗").concat("together");
//        System.out.println(s1);//宝玉林黛玉薛宝钗together
//
//        // 4.replace 替换字符串中的字符，不会直接作用在原字符串
//        s1 = "宝玉 and 林黛玉 林黛玉 林黛玉";
//        //在s1中，将 所有的 林黛玉 替换成薛宝钗，需要将结果重新赋值给原对象
//        s1 = s1.replace("林黛玉", "薛宝钗");
//        String s11 = s1.replace("宝玉", "jack");
//        System.out.println(s1);//宝玉 and 薛宝钗 薛宝钗 薛宝钗
//        System.out.println(s11);//jack and 薛宝钗 薛宝钗 薛宝钗
//
//        // 5.split 分割字符串, 对于某些分割字符，我们需要 转义比如 | \\等
//        String poem = "锄禾日当午,汗滴禾下土,谁知盘中餐,粒粒皆辛苦";
//        //以 , 为标准对 poem 进行分割 , 返回一个数组
//        String[] split = poem.split(",");
//        System.out.println("==这首诗的内容为===");
//        for (int i = 0; i < split.length; i++) {
//            System.out.println(split[i]);
//        }
//        //在对字符串进行分割时，如果有特殊字符，需要加入 转义符 \
//        String address = "E:\\aaa\\bbb";
//        split = address.split("\\\\");//需要使用转义符才能得到两个斜杠
//        System.out.println("==分割后内容===");
//        for (int i = 0; i < split.length; i++) {
//            System.out.println(split[i]);
//        }
//
//        // 6.toCharArray 转换成字符数组
//        s = "happy";
//        char[] chs = s.toCharArray();
//        for (int i = 0; i < chs.length; i++) {
//            System.out.print(chs[i] + "\t");
//        }
//        System.out.println();
//
//        // 7.compareTo 比较两个字符串的大小，如果前者大，则返回正数，后者大，则返回负数，如果相等，返回0
//        // sort方法就是通过调用这个实现
//        // (1) 顺序遍历各字符，返回首个不相同字符的ASCII码差值
//        // (2) 如果遍历完长度较短的字符仍未找到不同字符，则返回长度的差值
//        String a = "jzck1";// len = 3
//        String b = "jack";// len = 4
//        System.out.println(a.compareTo(b)); // 返回值是 'c' - 'a' = 2的值
//
//        // 8.format 格式字符串
//        /* 占位符有:
//         * %s 字符串 %c 字符 %d 整型 %.2f 浮点型
//         *
//         */
//        String name = "john";
//        String nickname = "jon";
//        int age = 10;
//        double score = 56.857;
//        char gender = '男';
//        //将所有的信息都拼接在一个字符串.
//        String info =
//                "我的姓名是" + name + "年龄是" + age + ",成绩是" + score + "性别是" + gender + "。希望大家喜欢我！";
//
//        System.out.println(info);
//
//
//        //说明，
//        //1. %s , %d , %.2f %c 称为占位符，这些占位符由后面变量来替换
//        //2. %s 表示后面由 字符串来替换；%d 是整数来替换；%c 使用char 类型来替换
//        //3. %.2f 表示使用小数来替换，替换后，只会保留小数点两位, 并且进行四舍五入的处理
//        //4.如果直接输出，不能用println，需要用printf
//
//        String formatStr = "我的姓名是%s，大家也可以叫我%s，年龄是%d，成绩是%.2f 性别是%c.希望大家喜欢我！";
//
//        String info2 = String.format(formatStr, name, nickname, age, score, gender);
//
//        System.out.println("info2=" + info2);
//    }
//}
