import java.util.Iterator;
import java.util.LinkedList;
//一定要注意引用和对象的区别，有new的才是对象，没new的是引用（地址、指针），看不明白就多看几遍，别着急
public class Studynote53_LinkedList {
}
/*
LinkedList的全面说明
//LinkedList的作者是Josh Bloch，谷歌Java 架构师写的
1）LinkedList底层实现了双向链表和双端队列特点
2）可以添加任意元素（元素可以重复），包括null
3）线程不安全，没有实现同步

LinkedList的底层操作机制
1) LinkedList 底层维护了一个双向链表
2）LinkedList 中维护了两个属性 first 和 last 分别指向首节点和尾节点
3）每个节点（Node对象），里面又维护了 prev、next、item 三个属性，其中通过 prev 指向前一个，
通过 next 指向后一个节点。最终实现双向链表
4）LinkedList 元素的添加和删除，不是通过数组完成的（只需要prev和next改变指向），相对来说效率较高。

ArrayList和LinkedList的比较
            底层结构      增删的效率     改查的效率
ArrayList   可变数组    较低，数组扩容      较高
LinkedList  双向链表    较高，链表追加      较低

如何选择ArrayList和LinkedList：
1）如果我们改查的操作多，选择ArrayList
2）如果我们增删的操作多，选择LinkedList
3）一般来说，在程序中，80%-90%都是查询，因此大部分情况下会选ArrayList
4）在一个项目中，根据业务灵活选择，也可能这样，一个模块使用的是ArrayList另外一个模块是LinkedList，根据业务来选择
//1.链表主要用于频繁修改的业务。2.链表不方便定位查找数据需要遍历，而数组有下表可以快速定位。

 */
//从链表是对象存储，把多个对象通过属性指向连接起来成一个集合。不同于之前讲的数组存储
//改指针指向对于计算机而言非常容易，但是数组就需要时刻考虑容量，扩容，效率太低，套了很多层
//数组加链表就是每个数组元素里面除了基本数据以外，还有一个以上对象引用，指向下一个空间。

//定义一个Node 类，Node 对象 表示双向链表的一个结点
class LinkedlistNode {
    public Object item; //真正存放数据
    public LinkedlistNode next; //指向后一个结点
    public LinkedlistNode pre; //指向前一个结点
    public LinkedlistNode(Object name) {
        this.item = name;
    }
    public String toString() {
        return "Node name=" + item;
    }
}


class LinkedList01 {
    public static void main(String[] args) {
        //模拟一个简单的双向链表
        //其实要只要理解在集合中，存放的是对象，通过对象来访问类的成员就可以了
        //因为next和last相当于c语言的指针，指向的是链表中的某个结点对象，
        // 而在java里面没有指针是引用，引用类型是Node类型
        LinkedlistNode jack = new LinkedlistNode("jack");
        LinkedlistNode tom = new LinkedlistNode("tom");
        LinkedlistNode hsp = new LinkedlistNode("老韩");

        //连接三个结点，形成双向链表
        //next就是在Node类中定义的一个Node类型的成员变量，可以存储一个Node类对象
        //jack -> tom -> hsp
        jack.next = tom;
        tom.next = hsp;
        //hsp -> tom -> jack
        hsp.pre = tom;
        tom.pre = jack;

        LinkedlistNode first = jack;//让first引用指向jack,就是双向链表的首结点
        LinkedlistNode last = hsp; //让last引用指向hsp,就是双向链表的尾结点


        //演示，从头到尾进行遍历
        System.out.println("===从头到尾进行遍历===");
        while (true) {
            if(first == null) {
                break;
            }
            //输出first 信息
            System.out.println(first);
            first = first.next;
        }

        //演示，从尾到头的遍历
        System.out.println("====从尾到头的遍历====");
        while (true) {
            if(last == null) {
                break;
            }
            //输出last 信息
            System.out.println(last);
            last = last.pre;
        }

        //演示链表的添加对象/数据过程
        //要求，是在 tom --------- 老韩直接，插入一个对象 smith

        //1. 先创建一个 Node 结点，name 就是 smith
        LinkedlistNode smith = new LinkedlistNode("smith");
        //将 smith 加入双向链表
        smith.next = hsp;
        smith.pre = tom;
        //改变原有链表的经由
        hsp.pre = smith;
        tom.next = smith;

        //让first 再次指向jack
        first = jack;//让first引用指向jack,就是双向链表的头结点

        System.out.println("===从头到尾进行遍历===");
        while (true) {
            if(first == null) {
                break;
            }
            //输出first 信息
            System.out.println(first);
            first = first.next;
        }

        last = hsp; //让last 重新指向最后一个结点
        //演示，从尾到头的遍历
        System.out.println("====从尾到头的遍历====");
        while (true) {
            if(last == null) {
                break;
            }
            //输出last 信息
            System.out.println(last);
            last = last.pre;
        }
    }
}

class LinkedListMethod {
    public static void main(String[] args) {

        LinkedList linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        System.out.println("linkedList=" + linkedList);

        //演示一个删除结点的
        linkedList.remove(); // 这里默认调用 removeFirst() 删除的是第一个结点
        //linkedList.remove(2);

        System.out.println("linkedList=" + linkedList);

        //修改某个结点对象
        linkedList.set(1, 999);
        System.out.println("linkedList=" + linkedList);

        //得到某个结点对象
        //get(1) 是得到双向链表的第二个对象
        Object o = linkedList.get(1);
        System.out.println(o);//999

        //遍历：因为 LinkedList 实现了 List 接口，利用迭代器遍历
        System.out.println("===LinkeList遍历迭代器====");
        Iterator iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            Object next =  iterator.next();
            System.out.println("next=" + next);

        }

        System.out.println("===LinkeList遍历增强for====");
        for (Object o1 : linkedList) {
            System.out.println("o1=" + o1);
        }
        System.out.println("===LinkeList遍历普通for====");
        for (int i = 0; i < linkedList.size(); i++) {
            System.out.println(linkedList.get(i));
        }


        //老韩源码阅读.
        /* 1. LinkedList linkedList = new LinkedList();
              public LinkedList() {}
           //这时 linkeList 的属性 first = null  last = null

           2. 执行 添加
               public boolean add(E e) {
                    linkLast(e);
                    return true;
                }
            3.将新的结点，加入到双向链表的最后
             void linkLast(E e) {
                final Node<E> l = last;
                final Node<E> newNode = new Node<>(l, e, null);
                last = newNode;
                if (l == null)
                    first = newNode;
                else
                    l.next = newNode;
                size++;
                modCount++;
            }
            因为是双端队列，所以先用l 把左边的引用（last）保存下来，然后创建新结点，
            然后让当前结点的last指向新节点，接着判断是不是第一个结点，进行相应的引用处理
            last,初始为null，每一轮添加走完last就会指向上一个，然后将其给新进来的这个的l位置，也就是pre
            因为是在尾部插入，所以需要找到尾节点对象，放在创建的新对象的prev指向的对象
         */

        /*
          老韩读源码 linkedList.remove(); // 这里默认删除的是第一个结点
          1. 执行 removeFirst
            public E remove() {
                return removeFirst();
            }
         2. 执行
            public E removeFirst() {
                final Node<E> f = first;
                if (f == null)
                    throw new NoSuchElementException();
                return unlinkFirst(f);
            }
          3. 执行 unlinkFirst, 将 f 指向的双向链表的第一个结点拿掉
            private E unlinkFirst(Node<E> f) {
                // assert f == first && f != null;
                final E element = f.item;
                final Node<E> next = f.next;
                f.item = null;
                f.next = null; // 没有引用的对象会被当做垃圾对象，GC要回收
                first = next;
                if (next == null)
                    last = null;
                else
                    next.prev = null;
                size--;
                modCount++;
                return element;//返回第一个链表中原有的数据，也可以不使用
            }
            //f的引用是在上一个方法中声明的，当方法结束时这个f的生命周期就结束了，
            这个时候被删除的对象就没有任何引用，就会被回收
         */
    }
}
