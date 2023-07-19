public class Studynote40_WrapperClass {
}
/*
包装类的分类
1.针对八种基本定义相应的引用类型——包装类
2.有了类的特点，就可以调用类中的方法。

基本数据类型      包装类           继承                  实现接口
boolean         Boolean                       Comparable,Serializable
char            Character                     Comparable,Serializable
byte            Byte            Number      Comparable,Serializable(父类)
short           Short           Number      Comparable,Serializable(父类)
int             Integer         Number      Comparable,Serializable(父类)
long            Long            Number      Comparable,Serializable(父类)
float           Float           Number      Comparable,Serializable(父类)
double          Double          Number      Comparable,Serializable(父类)

实现 Serializable 接口表示可以串行化，即在网络传输
实现 Comparable 接口表示可以相互比较

●演示包装类和塞本数据类型的相互转换，
以 int 和 Integer 演示：
1）jdk5前的手动装箱和拆箱方式，装箱：基本类型->包装类型，反之，拆箱
2）jdk5以后（含jdk5）的自动装箱和拆箱方式
3）自动装箱底层调用的是valueOf方法，比如Integer.valueOf())

个人理解：
1.8个基本数据类型都有一个包装类，把变量转成对应包装类的对象叫装箱，这样可以使用包装类里的各种方法
2.包装类无论进行什么运算都会执行拆箱操作，然后本质就是基本数据类型的运算，就符合基本数据类型的自动类型提升，
最后得到结果在自动装箱操作
 */

class IntegerConvertInt{
    public static void main(String[] args) {
        //演示 int <--> Integer 的装箱和拆箱

        //jdk5 前是手动装箱和手动拆箱
        //手动装箱 int -> Integer
        int n1 = 100;
        Integer integer1 = new Integer(n1);
        Integer integer2 = Integer.valueOf(n1);
        //手动拆箱 Integer -> int
        int i = integer1.intValue();

        //jdk5 后，自动装箱和自动拆箱
        int n2 = 200;
        Integer integer3 = n2; //底层使用的是 valuesOf() 方法
        int n3 = integer3; //底层使用的是 intValue() 方法
    }
}

class WrapperAutoConvert{
    public static void main(String[] args) {
        //此处三元运算符自动转换数值为精度最高的 Double 型，因此输出 1.0 而不是 1
        Object obj1 = true ? new Integer(1) : new Double(2.0);
        System.out.println(obj1);
        /*三元运算符是一个整体，看最高精度类型
        三元操作符必须要返回一个数据。而且类型要确定，不可能条件为真时返回int类型，条件为假时返回float类型，
        编译器是不允许如此的，所以它就会进行类型转换了. 会根据运算符的精确度类型进行自动类型转换
         */
        System.out.println(obj1 instanceof Double);

        Object obj2;
        if (true) obj2 = new Integer(1);
        else obj2 = new Double(2.0);
        System.out.println(obj2);
    }
}

class WrapperConvertString{
    public static void main(String[] args) {
        //包装类(Integer) -> String
        Integer i = 100;//自动装箱
        //方式1
        String str1 = i + "";
        //方式2：包装类重写了 toString() 方法，因此直接返回具体值
        String str2 = i.toString();
        //方式3：String 类的 valueOf() 方法可以传入 Object 参数
        String str3 = String.valueOf(i);

        //String -> 包装类(Integer)
        String str = "12306";
        //方法1：如果String类型的变量要转成 int，boolean,long,double 类型的变量就要用包装类，反之亦然
        Integer i1 = Integer.parseInt(str);//此处使用到自动装箱
        //方法2：构造器
        Integer i2 = new Integer(str);
    }
}

class WrapperCommonMethods{
    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE);//返回最小值
        System.out.println(Integer.MAX_VALUE);//返回最大值

        System.out.println(Character.isDigit('a'));//判断是不是数字
        System.out.println(Character.isLetter('a'));//判断是不是字母
        System.out.println(Character.isUpperCase('a'));//判断是不是大写
        System.out.println(Character.isLowerCase('a'));//判断是不是小写

        System.out.println(Character.isWhitespace('a'));//判断是不是空格
        System.out.println(Character.toUpperCase('a'));//转成大写
        System.out.println(Character.toLowerCase('A'));//转成小写
    }
}

class IntegerInput{
    public static void main(String[] args) {
        new IntegerInput().method1();
    }
    public void method1(){
        Integer i = new Integer(1);//新建 Integer 对象
        Integer j = new Integer(1);
        System.out.println(i == j);//false，两个对象内存地址不相等

        //调用 valueOf() 方法，数组 IntegerCache 范围是-128~127
        //该方法对数组内的数是地址相等的同一对象，范围外则新建对象
        //Java对部分经常使用的数据采用缓存技术，在类第一次被加载时换创建缓存和数据。
        // 当使用等值对象时直接从缓存中获取，从而提高了程序执行性能。
        //-127-128会在缓冲区创建Integer对象数组，先去缓冲区找，没有才会创建，有了就直接用有的
        //为什么是这个范围呢？因为他就是规定一个字节的范围当做缓存，知道是利用了缓存机制就行了
        //这个范围是bite的范围 同时0和整数部分也是ASCII的范围 直接记就是
        // 计算机语言是美国人发明的 这个范围已经可以表达所有的英文和常用符号 所以对这些最最常用到的东西弄个缓存 提升效率
        Integer m = 1;
        Integer n = 1;
        System.out.println(m == n);//true，比较数组中两个相同地址

        Integer x = 128;//超出 IntegerCache 范围，创建新的对象
        Integer y = 128;
        System.out.println(x == y);
        System.out.println(x instanceof Integer);//false，两个新对象内存不同

        Integer a = 128;
        int b = 128;
        System.out.println(a == b);//true，只要有基本数据类型，会把包装类拆箱再==判断值是否相等
    }
}
//补充说明：Float、Double中的valueOf()，永远返回新创建的对象，因为一个范围内的整数是有限的，但是小数却是无限的，无法保存在缓存中