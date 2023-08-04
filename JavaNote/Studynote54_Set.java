import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Studynote54_Set {
}
/*
Set接口基本介绍
1）无序（添加和取出的顺序不一致），没有索引
2)不允许重复元素，所以最多包含一个null
3）重要实现类：HashSet，TreeSet

Set接口的常用方法
和List接口一样，Set接口也是 Collection 的子接口，因此，常用方法和 Collection 接口一样

Set 接口的遍历方式
同 Collection 的遍历方式一样，因为Set接口是Collection接口的子接口。
1.可以使用选代器
2.增强for
3.不能使用索引的方式来获取
 */

class SetMethod {
    public static void main(String[] args) {
        //老韩解读
        //1. 以Set 接口的实现类 HashSet 来讲解Set 接口的方法
        //2. set 接口的实现类的对象(Set接口对象), 不能存放重复的元素, 可以添加一个null
        //3. set 接口对象存放数据是无序(即添加的顺序和取出的顺序不一致)
        //4. 注意：存入的顺序不一致但存放的位置已经固定了，所以取的时候就是固定的顺序
        //hashset使用hash值来确定存储顺序，每个元素hash值唯一。故不能重复，位置固定
        Set set = new HashSet();
        set.add("john");
        set.add("lucy");
        set.add("john");//重复
        set.add("jack");
        set.add("hsp");
        set.add("mary");
        set.add(null);//
        set.add(null);//再次添加null
        for(int i = 0; i <10;i ++) {
            System.out.println("set=" + set);//遍历十次，每次取出的顺序都一致
        }

        //遍历
        //方式1： 使用迭代器
        System.out.println("=====使用迭代器====");
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Object obj =  iterator.next();
            System.out.println("obj=" + obj);
        }

        set.remove(null);

        //方式2: 增强for
        System.out.println("=====增强for====");

        for (Object o : set) {
            System.out.println("o=" + o);
        }

        //set 接口对象，不能通过索引（普通for循环）来获取
        //虽然有 size() 方法，但是没有 get() 方法
    }
}

/*
Set 接口实现类：HashSet
HashSet的全面说明
1）HashSet实现了Set接口,实际上是HashMap，
2）可以存放null值，但是只能有一个null;不能有重复元素/对象
3）HashSet不保证元素是有序的，取决于hash后，再确定索引的结果(不保证存放顺序和取出时一致)
//HashSet,调用了一个map方法，而map方法中的key是不能被重复的
 */
class HashSet_ {
    public static void main(String[] args) {
        //1. 构造器的源码
        /*
            public HashSet() {
                map = new HashMap<>();
            }
         2. HashSet 可以存放null ,但是只能有一个null,即元素不能重复
         //<>是泛型,可以简单理解为一个类型的统一表示,为了区分数值类型
         */
        Set hashSet = new HashSet();
        hashSet.add(null);
        hashSet.add(null);
        System.out.println("hashSet=" + hashSet);
    }
}
class HashSet1 {
    public static void main(String[] args) {
        HashSet set = new HashSet();

        //说明
        //1. 在执行add方法后，会返回一个boolean值
        //2. 如果添加成功，返回 true, 否则返回false
        //3. 可以通过 remove 指定删除哪个对象
        System.out.println(set.add("john"));//T
        System.out.println(set.add("lucy"));//T
        System.out.println(set.add("john"));//F
        System.out.println(set.add("jack"));//T
        System.out.println(set.add("Rose"));//T


        set.remove("john");
        System.out.println("set=" + set);//3个

        //将 set 对象清空
        set  = new HashSet();
        System.out.println("set=" + set);//0
        //4 Hashset 不能添加相同的元素/数据?
        set.add("lucy");//添加成功
        set.add("lucy");//加入不了

        set.add(new HashSetDog("tom"));//OK
        set.add(new HashSetDog("tom"));//Ok
        //这两个新对象只是名字属性相同，在堆中的地址不一样，因此是两个对象
        //HashSetDog 类没有重写equals，就没有重写hashCode方法，new出来的对象hashCode不同，所有可以添加成功
        System.out.println("set=" + set);


        //看源码，做分析
        set.add(new String("hsp"));//ok
        set.add(new String("hsp"));//加入不了.
        //因为String类重写了equals和hashcode方法，使得new出来内容一样的String对象的哈希码是一样的，
        // 哈希码一样set集合就无法添加重复的对象。光hashCode()一样还不行，equals()还得进一步确认一下。
        //HashSetDog 类没重写方法，所以equal只是判断地址是否相同，显然地址不同，所以可以算两个元素
        // 但String重写了equal方法，所以会进行毕竟比较，字符相同，则会当成同一个元素，故第二次输入失败
        System.out.println("set=" + set);


    }
}

class HashSetDog { //定义了Dog类
    private String name;

    public HashSetDog(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HashSetDog{" +
                "name='" + name + '\'' +
                '}';
    }
}
/*
HashSet底层机制说明
分析HashSet底层是HashMap,HashMap底层是(数组+链表+红黑树)
//链表只是为了用来处理哈希冲突，红黑树是为了解决冲突过多链表过长查找元素过慢

*/

class HashSetStructure {
    public static void main(String[] args) {
        //模拟一个HashSet的底层 (HashMap 的底层结构)

        //1. 创建一个数组，数组的类型是 Node[]
        //2. 有些人，直接把 Node[] 数组称为 表
        HashSetNode[] table = new HashSetNode[16];

        //3. 创建结点
        HashSetNode john = new HashSetNode("john", null);

        table[2] = john;
        HashSetNode jack = new HashSetNode("jack", null);
        john.next = jack;// 将jack 结点挂载到john
        HashSetNode rose = new HashSetNode("Rose", null);
        jack.next = rose;// 将rose 结点挂载到jack

        HashSetNode lucy = new HashSetNode("lucy", null);
        table[3] = lucy; // 把lucy 放到 table表的索引为3的位置.
        System.out.println("table=" + table);


    }
}
class HashSetNode { //结点, 存储数据, 可以指向下一个结点，从而形成链表
    Object item; //存放数据
    HashSetNode next; // 指向下一个结点

    public HashSetNode(Object item, HashSetNode next) {
        this.item = item;
        this.next = next;
    }
}

/*

1.HashSet底层是HashMap
2.添加一个元素时，先得到hash值-会转成->索引值（要存放在哈希表中的位适号）
3.找到存储数据表table，看这个索引位置是否已经存放的有元素
4.如果没有，直接加入
5.如果有，调用equals比较，如果相同，就放弃添加，如果不相同，则添加到最后（以链表的方式添加）
6.在Java8中，如果一条链表的元素个数超过 TREEIFY_THRESHOLD 默认是8），并且table的大小 >=
MIN_TREEIFY_CAPACITY（默认64)，就会进行树化（红黑树）
 */

