//import java.util.ArrayList;
//
////算术位移：a >> b 就是 a 除以 2的b次方
///*
//ArrayList注意事项
//1）permits all elements,including null，ArrayList可以加入null,且多个
//2）ArrayList是由数组来实现数据存储的
//3）ArrayList基本等同于Vector，除了ArrayList是线程不安全，没有synchronized修饰（执行效率高）
//在多线程情况下，不建议使用ArrayList
//
//底层机制
//1)ArrayList 中维护了一个 Object[] 数组 elementData
//此数组源码为 transient Object[] elementData; //transient 表示瞬间，短暂。表示该属性不会被序列化
//序列化就是串行化，Serialization的另一种翻译。序列化简单来说就是把对象转为二进制字节，方便传输
//2）当创建 ArrayList 对象时，如果使用的是无参构造器，则初始 elementData 容量为0（jdk7是10）,第1
//次添加，则扩容 elementData 为10，如需要再次扩容，则扩容 elementData 为1.5倍
//3）当添加元素时：先判断是否需要扩容，如果需要扩容，则调用grow方法，否则直接添加元素到合适位置
//如果使用的是指定大小的构造器，则初始 elementData 容量为指定大小，如果需要扩容则直接扩容 elementData 为1.5倍
////这些代码的作者是 Josh Bloch ava 集合框架创办人，Joshua Bloch 领导了很多 Java 平台特性的设计和实现，包括 JDK 5.0 语言增强以及屡获殊荣的 Java 集合框架。
//
//
//·4）如果使用的是无参构造器，如果第一次添加，需要扩容的话，则扩容elementData为10
//如果需要再次扩容的话，则扩容 elementData 为1.5倍。
//5如果使用的是指定容量capacity的构造器，则初始elementData容量为capacity
//6）如果使用的是指定容量capacity的构造器，如果需要扩容，则直接扩容elementData为
//1.5倍。
//
// */
//
//public class Studynote51_ArrayList {
//    public static void main(String[] args) {
//
//        //Idea 默认情况下，Debug 显示的数据是简化后的，如果希望看到完整的数据，需要做设置.
//        //File -> Settings -> Build, Execution, Deployment -> Debugger -> Data Views -> Java
//        //取消勾选 Enable alternative view for Collections classes
//
//        //使用无参构造器创建ArrayList对象
//        ArrayList list = new ArrayList();//创建一个空的 elementData 数组，调用源码 elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA
//        //ArrayList list = new ArrayList(8);//创建指定大小的数组，this.elementData = new Object[initialCapacity];
//        //使用for给list集合添加 1-10数据
//        for (int i = 1; i <= 10; i++) {
//            list.add(i);//int 是基本类型数据 底层是个Object数组，此处会装箱
//        }
//        /*
//        除8种基础数据类型 （byte，short ，int，long,float ,double,char,boolean）以外都是Object的子类，所以需要自动装箱
//        你不能直接地向集合(Collections)中放入原始类型值，因为集合只接收对象（引用数据类型）。通常这种情况下你的做法是，
//        将这些原始类型的值转换成对象，然后将这些转换的对象放入集合中。就是把基本类型数据转为object子类的对象，这样才能储存
//         */
//        /*
//        list.add(i)的执行机制：
//        1）先确定是否要扩容：
//        向 ensureCapacityInternal 方法输入minCapacity 表示现在要想实现这个数组，该数组的最小长度，并不是真是的长度，其数值为 (当前对象内字段数 + 1)
//        如果当前数组为空（elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA），
//        将DEFAULT_CAPACITY（10） 和(对象内字段数 + 1) 中的最大值赋给 minCapacity，然后执行 ensureExplicitCapacity(minCapacity)：
//
//        ensureExplicitCapacity 方法：
//        ①modCount++ 记录当前集合被修改次数（防止多个线程同时修改，）②如果 elementData 大小不够，调用 grow() 方法扩容：
//        如果 minCapacity（需要的内存容量）大于数组的长度就说明这个数组不够长了 需要扩容
//
//        grow() 方法扩容机制：
//        ①判断 minCapacity 和 原数组长度+算术右移 oldCapacity + (oldCapacity >> 1)大小，将更大的值赋给 newCapacity
//        ②判断 newCapacity 和 MAX_ARRAY_SIZE（2147483639） 的大小，决定是否调用hugeCapacity（仅在字符元素数过大时使用）
//        ③执行 elementData = Arrays.copyOf(elementData, newCapacity)，完成扩容
//        2）执行赋值
//            elementData[size++] = e;
//            return true;
//        备注：
//        JDK11移除了ensureCapacityInternal和ensureExplicitCapacity
//        所谓的扩容，就是创建了新的Object数组，然后再拷贝过去，这样如果数据打那么也很需要时间的
//         */
//        //使用for给list集合添加 11-15数据
//        for (int i = 11; i <= 15; i++) {
//            list.add(i);
//        }
//        list.add(100);
//        list.add(200);
//        list.add(null);
//
//    }
//}
//
///**
// * 按要求实现：
// * (1) 封装一个新闻类，包含标题和内容属性，提供get、set方法，重写toString方法，打印对象时只打印标题；
// * (2) 只提供一个带参数的构造器，实例化对象时，只初始化标题；并且实例化两个对象：
// * 新闻一：新冠确诊病例超千万，数百万印度教信徒赴恒河“圣浴”引民众担忧
// * 新闻二：男子突然想起2个月前钓的鱼还在网兜里，捞起一看赶紧放生
// * (3) 将新闻对象添加到ArrayList集合中，并且进行倒序遍历；
// * (4) 在遍历集合过程中，对新闻标题进行处理，超过15字的只保留前15个，然后在后边加“…”
// * (5) 在控制台打印遍历出经过处理的新闻标题；
// */
//
//class ArrayListExercise {
//    public static void main(String[] args) {
//        ArrayList arrayList = new ArrayList();
//        arrayList.add(new ArrayListNews("新冠确诊病例超千万，数百万印度教信徒赴恒河\"圣浴\"引民众担忧"));
//        arrayList.add(new ArrayListNews("男子突然想起2个月前钓的鱼还在网兜里，捞起一看赶紧放生"));
//
//        int size = arrayList.size();
//        for (int i = size - 1; i >= 0; i--) {
//            //System.out.println(arrayList.get(i));
//            ArrayListNews news = (ArrayListNews)arrayList.get(i);
//            System.out.println(processTitle(news.getTitle()));
//        }
//
//    }
//    //专门写一个方法，处理现实新闻标题 process处理
//    public static String processTitle(String title) {
//
//        if(title == null) {
//            return "";
//        }
//
//        if(title.length() > 15) {
//            return title.substring(0, 15) + "..."; //[0,15)
//        } else {
//            return title;
//        }
//
//    }
//}
//
//
//class ArrayListNews {
//    private String title;
//    private String content;
//
//    public ArrayListNews(String title) {
//        this.title = title;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    @Override
//    public String toString() {
//        return "News{" +
//                "title='" + title + '\'' +
//                '}';
//    }
//}
