import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Studynote49_Collection {
}
/*
数组
1）长度开始时必须指定，而一旦指定，不能更改
2）保存的必须为同一类型的元素
3）使用数组进行增加元素比较麻烦

·集合
1可以动态保存任意多个对象，使用比较方便！
2)提供了一系列方便的操作对象的方法：add、remove、set、get等
3）使用集合添加，删除新元素

单列集合：Collection接口的重要子接口List和Set
            Iterable
                ⇡
           Collection
        ↗            ↖
    List接口          Set接口
     ⇡                  ⇡
 ArrayList            HashSet
 Vector               TreeSet
 LinkedList

双列集合()
//双列就是放成对值，一个值用来索引
                Map接口
                   ⇡
        |----------✛-----------|
    HashMap     Hashtable   TreeMap
       ↑            ↑
LinkedHashMap   Properties

在开发中，选择什么集合实现类，主要取决于业务操作特点，然后根据集合实现类特性进行选择，分析如下：
1）先判断存储的类型（一组对象[单列]或一组键值对[双列]）
2）一组对象[单列]：Collection接口
    允许重复：List
        增删多：LinkedList[底层维护了一个双向链表]//双链表增删更快
        改查多：ArrayList[底层维护Object类型的可变数组]//数组的索引定位比较快
    不允许重复：Set
        无序：HashSet[底层是HashMap，维护了一个哈希表即（数组+链表+红黑树)]
        排序：TreeSet
        插入和取出顺序一致：LinkedHashSet[底层是LinkedHashMap]维护数组+双向链表//插入和取出顺一致，但存储时是无序存储的
3）一组键值对[双列]：Map
    键无序：HashMap[底层是：哈希表jdk7：数组+链表，jdk8数组+链表+红黑树]
    键排序：TreeMap
    键插入和取出顺序一致：LinkedHashMap[底层是HashMap]
    读取文件：Properties[继承Hashtable]
4）补充：单列线程安全：Vector，双列线程安全：Hashtable



 */

/*
Collection接口
特点
1）Collection实现子类可以存放多个元素，每个元素可以是object
2）有些Collection的实现类，可以存放重复的元素，有些不可以
3）有些Collection的实现类，有些是有序的（List)，有些不是有序（Set）
4）Collection接口没有直接的实现子类，是通过它的子接口Set和List来实现的

常用方法（由于是接口，因此需要用实例化的类演示）
1）add：添加单个元素
2）remove:刷除指定元素
3）contains:查找元素是否存在
4）size:获取元素个数
5）isEmpty:判断是否为空
6）clear：清空
7）addAll:添加多个元素
8）containsAll：查找多个元素是否都存在
9）removeAll：删除多个元素
 */
class CollectionMethod{
    public static void main(String[] args) {
        //接口的引用指向实现类的对象，向上转型（接口多态）
        //只能调用接口里的方法，而不能调用实现类方法。因此可演示接口特有的方法
        List list = new ArrayList();
        //add:添加单个元素
        list.add("jack");
        list.add(10);//list.add(new Integer(10))
        list.add(true);
        System.out.println("list=" + list);

        //remove:删除指定元素，只会删除第一个出现的
        //list.remove(0);//按下标删除元素
        //不能按index删除的是因为你的编译类型是Collection接口，这个接口是不能按索引删除的
        list.remove(true);//指定删除某个元素
        //集合存放的是包装类型，删除对象需要使用包装类型
        //默认按下标删除，想删指定数字就要创建一个包装类对象（装箱），new一个或者valueOf都可以
        //list.remove((Integer)10);//为了防止和删除某个编号混淆，让参数变成Integer对象
        System.out.println("list=" + list);

        //contains:查找元素是否存在
        System.out.println(list.contains("jack"));//T

        //size:获取元素个数
        System.out.println(list.size());//2

        //isEmpty:判断是否为空
        System.out.println(list.isEmpty());//F

        //clear:清空（慎重使用）
        list.clear();
        System.out.println("list=" + list);

        //addAll:添加多个元素
        ArrayList list2 = new ArrayList();
        list2.add("红楼梦");
        list2.add("三国演义");
        list.addAll(list2);
        System.out.println("list=" + list);

        //containsAll:查找多个元素是否都存在
        System.out.println(list.containsAll(list2));//T

        //removeAll：删除多个元素
        list.add("聊斋");
        list.removeAll(list2);
        System.out.println("list=" + list);//[聊斋]

    }
}

/*
Collection接口元素遍历
方式1：利用父接口 Interable 的 Interator（迭代器）
1）Iterator 对象称为选代器，主要用于遍历 Collection 接口中的元素
2）所有实现了 Collection 接口的集合类都有一个 iterator() 方法，用以返回一个
实现了 Iterator 接口的对象，即可以返回一个选代器
3)Iterator的结构.[图:]
4）Iterator 仅用于追历集合，Iterator 本身并不存放对象

注意：在调用 iterator.next()方法之前必须要调用iterator.hasNext()进行检测。
若不调用，且下一条记录无效，直接调用 iterator.next()会抛出NoSuchElementException异常
 */
class SetBookInfo {
    private String name;
    private String author;
    private double price;

    public SetBookInfo(String name, String author, double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    @Override
    public String toString() {
        return "SetBookInfo{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }
}

class CollectionIterator{
    public static void main(String[] args) {
        //向上转型
        Collection col = new ArrayList();

        col.add(new SetBookInfo("三国演义", "罗贯中", 10.1));
        col.add(new SetBookInfo("小李飞刀", "古龙", 5.1));
        col.add(new SetBookInfo("红楼梦", "曹雪芹", 34.6));

        //1.遍历集合前，需要得到对应的迭代器
        Iterator iterator = col.iterator();

        //2.使用 while循环 遍历，快捷键 itit，查看哪些快捷键可使用 Ctrl+J
        // 利用 hasNext() 判断是否还有下一个元素
        while(iterator.hasNext()){
            //next()作用：①指针下移②将下移以后集合位置上的元素返回
            //object是所有类的父类，迭代器的next不能返回自建的对象
            Object next = iterator.next();
            System.out.println(next);//无法通过println() 直接打印
        }
        //3.当迭代器退出 while 循环后，iterator 迭代器指向最后的元素
        //直接调用 iterator.next()会抛出NoSuchElementException异常
        //4.如果需要再次遍历，需要重置迭代器
        iterator = col.iterator();//重新赋值，用于第二次遍历

    }
}
/*
Collection接口元素遍历
方式2：增强 for 循环
特点：增强for就是简化版的iterator，本质一样。只能用于遍历集合或数组

>基本语法
for（元素类型元素名：集合名或数组名）
访问元素
 */
class CollectionFor{
    public static void main(String[] args) {
        Collection col = new ArrayList();

        col.add(new SetBookInfo("三国演义", "罗贯中", 10.1));
        col.add(new SetBookInfo("小李飞刀", "古龙", 5.1));
        col.add(new SetBookInfo("红楼梦", "曹雪芹", 34.6));

        //使用增强 for 循环，可以理解为简化版本的迭代器，快捷键 iter 或 I
        showEnhancedFor();//这段仅用于演示增强for循环的使用

        //用Object类不用Book：col扔出来的数据是Object类
        for(Object book : col) {
            System.out.println("book = " + book);
        }
    }
    protected static void showEnhancedFor(){
        int[] nums = {1,8,10,90};
        for (int i : nums) {
            System.out.println("i = " + i);
        }
    }
}
/*
1.创建3个Dog{name，age}对象，放入到ArrayList中，赋给 List 引用
2.用选代器和增强for循环两种方式来遍历
3.重写Dog的toString方法，输出name和age
 */
class SetDog{
    private String name;
    private int age;

    public SetDog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "SetDog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
class CollectionExercise{
    public static void main(String[] args) {
        List list = new ArrayList();//向上转型
        list.add(new SetDog("小黑",3));
        list.add(new SetDog("大黄",5));
        list.add(new SetDog("灰狼",8));

        //使用增强for
        for (Object o : list) {
            System.out.println(o);
        }

        System.out.println("====使用迭代器遍历====");
        //使用迭代器
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object next =  iterator.next();
            System.out.println(next);
        }
    }
}