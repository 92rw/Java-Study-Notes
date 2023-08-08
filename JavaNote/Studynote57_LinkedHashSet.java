import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Studynote57_LinkedHashSet {
}
/*
//通俗解释：逻辑上有序，物理存储上无序（根据哈希函数计算存储地址）
介绍
1）LinkedHashSet 是 HashSet 的子类
2）LinkedHashSet 底层是一个 LinkedHashMap，底层维护了一个数组+双向链表
3）LinkedHashSet 根据元素的hashCode值来决定元素的存储位置，同时使
用链表维护元素的次序（图），这使得元素看起来是以插入顺序保存的
4）LinkedHashSet 不允许添重复元素

源码解读
1）在LinkedHastSet中维护了一个hash表和双向链表
（LinkedHashSet有head和tail）
2）每一个节点有 before 和 after 属性，这样可以形成双向链表
3）在添加一个元素时，先求 hash 值，在求索引，确定该元素在 hashtable 的位置，然后将添加的元素加入到双向链表（如果已经存在，不添加[原则和hashset一样]）
tail.next=newElement//简单指定
newElement.pre=tail
tail=newEelment;
4）这样的话，我们遍历LinkedHashSet也能确保插入顺序和遍历顺序一致

//before after就相当于LinkedList的pre next; head tail 就相当于 first last
//before和after在newNode方法里，不过这个方法被LinkedHashSet重写了。所以返回的是Entry内部类
保留原来每个索引下单链表，在每个结点的属性中再添加before和after两个引用，指向插入的前后结点。
next是物理层面的顺序，before after是逻辑层面的顺序。每个结点还有next属性，所以定位到索引位置后，
逐个比较是按 next 走的，而不是按 after 走的
 */

class LinkedHashSetSource {
    public static void main(String[] args) {
        //分析一下LinkedHashSet的底层机制
        Set set = new LinkedHashSet();
        set.add(new String("AA"));
        set.add(456);
        set.add(456);
        set.add(new HashSetCustomer("Gifu", 1001));
        set.add(123);
        set.add("HSP");

        System.out.println("set=" + set);
        //老韩解读
        //1. LinkedHashSet 加入顺序和取出元素/数据的顺序一致
        //2. LinkedHashSet 底层维护的是一个LinkedHashMap(是HashMap的子类)
        //3. LinkedHashSet 底层结构 (数组table+双向链表)
        //4. 添加第一次时，直接将 数组table 扩容到 16 ,存放的结点类型是 LinkedHashMap$Entry
                                        //HashSet中存放的结点类型是 HashMap$Node
        //5. 数组是 HashMap$Node[] 存放的元素/数据是 LinkedHashMap$Entry类型
                            //父类数组存放类子类的元素，数组的多态，说明两者存在继承关系或者实现关系
        /*
                //继承关系是在 LinkedHashMap 的静态内部类 Entry 完成.
                static class Entry<K,V> extends HashMap.Node<K,V> {
                    Entry<K,V> before, after;
                    Entry(int hash, K key, V value, Node<K,V> next) {
                        super(hash, key, value, next);
                    }
                }

         */

    }
}
class HashSetCustomer {
    private String name;
    private int no;

    public HashSetCustomer(String name, int no) {
        this.name = name;
        this.no = no;
    }
}
/*课堂练习
 * Car 类(属性:name,price)，  如果 name 和 price 一样，
 * 则认为是相同元素，就不能添加
 */
class LinkedHashSetExercise {
    public static void main(String[] args) {

        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.add(new HashSetCar("奥拓", 1000));//OK
        linkedHashSet.add(new HashSetCar("奥迪", 300000));//OK
        linkedHashSet.add(new HashSetCar("法拉利", 10000000));//OK
        linkedHashSet.add(new HashSetCar("奥迪", 300000));//加入不了
        linkedHashSet.add(new HashSetCar("保时捷", 70000000));//OK
        linkedHashSet.add(new HashSetCar("奥迪", 300000));//加入不了

        System.out.println("linkedHashSet=" + linkedHashSet);

    }
}
class HashSetCar {
    private String name;
    private double price;

    public HashSetCar(String name, double price) {
        this.name = name;
        this.price = price;
    }


    @Override
    public String toString() {
        return "\nCar{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    //重写equals 方法 和 hashCode
    //当 name 和 price 相同时， 就返回相同的 hashCode 值, equals返回true

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashSetCar car = (HashSetCar) o;
        return Double.compare(car.price, price) == 0 &&
                Objects.equals(name, car.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}