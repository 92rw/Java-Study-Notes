import java.util.HashSet;
import java.util.Objects;

public class Studynote55_HashSet {
    public static void main(String[] args) {
        //HashSet源码解读
        HashSet hashSet = new HashSet();
        hashSet.add("java");//到此位置，第1次add分析完毕.
        hashSet.add("php");//到此位置，第2次add分析完毕
        hashSet.add("java");
        System.out.println("set=" + hashSet);

        /*
        HashSet 的源码解读:添加元素底层是如何实现（对比hash()+equals()）
        1. 执行 HashSet()
            public HashSet() {
                map = new HashMap<>();
            }
        2. 执行 add()
           public boolean add(E e) {//e = "java"
                return map.put(e, PRESENT)==null;//(static) PRESENT = new Object();//HashMap里面put要传入键值对，hashset只有值，所以需要占位
           }

         3.执行 put() , 该方法会执行 hash(key) 得到key对应的hash值 算法h = key.hashCode()) ^ (h >>> 16)
         //这个位运算>>>16(无符号右移16位)可深究的东西很多，即解决正负数问题，又能解决高位相同，低位不同的的问题
         //对h和h >>> 16进行按位异或运算，得到一个新的整数值。这样可以使得h低16位和高16位都参与到最终的hash值计算中，提高了hash值分布均匀性
         //当PRESENT为null时，使用remove和add方法时返回的布尔值都会是true，结果是无效的

             public V put(K key, V value) {//key = "java" value = PRESENT（共享的静态属性）
                return putVal(hash(key), key, value, false, true);
            }
         4.执行 putVal
         //源码作者是Doug Lea，中文名为道格·利。现担任纽约州立大学Oswego分校教师；

         final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
                Node<K,V>[] tab; Node<K,V> p; int n, i; //定义了辅助变量
                //table 就是 HashMap 的一个数组，类型是 Node[]
                //if 语句表示如果当前table 是null, 或者 大小=0，则第一次扩容到16个空间

                //源码中 resize() 方法第一次扩容的过程使用位运算，原因是位运算更快
                //乘法比左移效率低，而且如果除数B满足2的n次方 可以用按位与来替代取模运算
                //方法中 newThr 赋给 threshold 临界值，用了四分之三空间就开始准备扩容

                if ((tab = table) == null || (n = tab.length) == 0) //注意这里null放短路或前面先判断可以防止异常
                    n = (tab = resize()).length;    //此代码执行后，n = 16，短路或||前面的tab == 空为false所以会进行后边的把tab.length赋给n并判断是否为空的操作

                //
                //(1)根据key，得到hash 去计算该key应该存放到table表的哪个索引位置，并把这个位置的对象，赋给 p
                //&是按位与, 就是把数组的大小(n-1) 和存放的hash值求按位与运算，hash 是前面通过 hash (key) 算出来的存放对象的哈希值
                //n 的值 为 数组长度 , n-1 为最大下标, 通过&的计算规则可知，在位运算中，大于（n-1）的位计运算结果全部会是 0
                //所以 (n-1) & hash 的值 大于等于 0, 小于等于 n-1 的值，也就是 0<=i<=(n+1)
                //这样就通过 hash 和 tab 的长度综合算出一个 tab 的下标来决定 key 存放的位置

                //(2)判断p 是否为null，此处的辅助变量 p 就是一个Node对象，定义的属性为Node<K,V>
                //(2.1) 如果p 为null, 表示还没有存放元素, 就创建一个Node (key="java",value=PRESENT)
                //      这里把hash存进去是为了与后来元素的hash值进行比较，null的意思还没有下一个指向，就是说还没有下一个节点
                //(2.2) 就放在该位置 tab[i] = newNode(hash, key, value, null)

                if ((p = tab[i = (n - 1) & hash]) == null)  //此处的计算相当于取余，%。为了高效率才用位运算&代替
                                                            //我们知道被除数越大，余数就越大。所以开始和size运算得到的索引才比较小。因为他相当于余数
                    tab[i] = newNode(hash, key, value, null);   //这里传入的第三个参数说明传入的value是共享的 PRESENT
                else {
                    Node<K,V> e; K k;   //一个开发技巧提示： 在需要局部变量(辅助变量)时候，再创建：不进来就不占内存，并且作用域不同

                    /*
                    下面这段代码之所以先比较hash是为了提高性能。这也解释了为什么equals需要重写hashcode，就是避免两个key明明相等但因为没有重写hashcode导致
                    hash值不相等，从而来不及进行后面的判断。这样导致的后果就是hashset中会出想key相等的重复元素，破坏了hashset的特性
                    加入元素时把hash值对当前数组长度取余了，所以这可能hash值不一样，但是取余后结果一样，所以要比较一次

                    按照这个逻辑，我们就可以研究比较hash的作用是什么，首先我们了解一个逻辑式即：key相等->hash值相同（需要我们自己重写hashcode实现）
                    而如果他满足hash相等的条件后，我们此时无法确定key值是否相等，因为hash相等只是一个必要条件，接着判断key值相等就行，这样就极大的提高性能

         */
        //如果当前索引位置对应的链表的第一个元素和准备添加的key的hash值一样
        //并且满足 下面两个条件之一:
        //(1) 准备加入的key 和 p 指向的Node 结点的 key 是同一个对象（p.Key就是Node中存放元素在内存中一个地址，和当前元素的地址值作比较，看看是不是同一个对象）
        //(2)  p 指向的Node 结点的 key 的equals() 和准备加入的key比较后相同就不能加入。地址一样的话，内容值就必然相同，但是地址值不一样可能内容值一样
        //== 和 equals  判断了值、引用地址、内容  这好几重。以确保不同数据类型的元素，来到这里判断时都不会出错
        //k是目前索引存放的对象的key  而key是我们当前想加入进去的key
        /*
                    if (p.hash == hash &&
                        ((k = p.key) == key || (key != null && key.equals(k))))
                        e = p;
                    //e是等于p 是没有动  p是指索引对应的那个对象  是将原来有的对象赋给e  所以不是覆盖 是将原来那个放回去

                    //再判断 p 是不是一颗红黑树：如果是一颗红黑树，就调用 putTreeVal , 来进行添加
                    else if (p instanceof TreeNode)
                        e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
                    else {//如果table对应索引位置，已经是一个链表, 就使用for循环比较：这里for没有给终止条件，要么没找到相同，要么找到相同，才会结束for
                          //(1) 依次和该链表的每一个元素比较后，都不相同, 则加入到该链表的最后
                          //    注意在把元素添加到链表后，立即判断 该链表是否已经达到8个结点：因为 binCount 从零开始，当大于等于 TREEIFY_THRESHOLD（数值为8）
                          //    , 就调用 treeifyBin() 对当前这个链表进行树化(转成红黑树)。
                          //这里的p开始是链表头节点，e是p.next就是第2个节点，此时对应的binCount为0。for 语句中 ++binCount 所以会实现先+再运算。 e=p.next先运行再到第二个if语句
                          //    注意，在转成红黑树时，要进行判断, 判断条件(MIN_TREEIFY_CAPACITY数值为64)
                          //    if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
                          //            resize();
                          //    如果上面条件成立，先table扩容.
                          //    只有上面条件不成立时，才转成红黑树
                          //(2) 依次和该链表的每一个元素比较过程中，如果有相同情况,就直接break

                        for (int binCount = 0; ; ++binCount) {  //1.该for循环（死循环）中 第一个if判断插入元素所在位置的单链表中p表示的节点后还有无节点 若无 则插入 若有 则执行第二个if语句
                            if ((e = p.next) == null) {         //把p.next（可以看成链接其他对象的线）赋给e，看他是不是空（有没有），没有就加到后边。注意此时已经在上面代码完成了链表第一个元素的比较
                                p.next = newNode(hash, key, value, null);
                                if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                                    treeifyBin(tab, hash);
                                break;
                            }
                            if (e.hash == hash &&               //2.第二个if语句为判断p节点直接后继节点是否和要插入元素相同 若相同则break退出循环 若不同 则执行e=p
                                ((k = e.key) == key || (key != null && key.equals(k))))
                                break;
                            p = e;                              //3.e=p意味让p此时表示该节点的原来的后继节点 而后循环比较 直到p表示的节点后无节点插入元素或p表示节点与要插入元素相同
                        }
                    }
                    if (e != null) { // existing mapping for key    //如果返回的value不为空
                        V oldValue = e.value;
                        if (!onlyIfAbsent || oldValue == null)
                            e.value = value;
                        afterNodeAccess(e);
                        return oldValue;
                    }
                }
                ++modCount;
                    //size 就是我们每加入一个结点Node(k,v,h,next), 不管加在哪个位置都会 size++
                    //afterNodeInsertion 方法此处没有具体实现，属于采用模板设计模式，让子类重写该方法
                if (++size > threshold) //threshold表示临界值
                    resize();//扩容
                afterNodeInsertion(evict);
                return null;    //这个返回值给add() 方法，用于判断添加元素是否成功
            }
         */
    }
}

class HashSetIncrement {//演示哈希数组扩容机制
    public static void main(String[] args) {
        /*
        HashSet底层是HashMap, 第一次添加时，table 数组扩容到 16，临界值(threshold)是 16*加载因子(loadFactor)是0.75 = 12
        如果table 数组使用到了临界值 12,就会扩容到 16 * 2 = 32,新的临界值就是 32*0.75 = 24, 依次类推
         */
        HashSet hashSet = new HashSet();
        //下面这段代码用于演示数组 size 和 threshold 扩容机制
//        for(int i = 1; i <= 100; i++) {
//            hashSet.add(i);//1,2,3,4,5...100
//        }
        /*
        在Java8中, 如果一条链表的元素个数到达 TREEIFY_THRESHOLD(默认是 8 )，
        并且table的大小 >= MIN_TREEIFY_CAPACITY(默认64),就会进行树化(红黑树),否则仍然采用数组扩容机制

         */
        //下面这段代码用于演示树化机制，将数据加在同一条链表上
//        for(int i = 1; i <= 12; i++) {
//            hashSet.add(new A(i));//
//        }


        /*
            当我们向hashset增加一个元素，-> Node -> 加入table , 就算是增加了一个size++
            主要是为了防止单条链表上元素过多，所以会进行扩容，加快树化的进度
            总结：结点个数 = table索引上的个数 + 链表上的个数
            数组下标计算通过 (n-1) & hash，这个值不可能超过n
         */

        for(int i = 1; i <= 7; i++) {//在table的某一条链表上添加了 7个A对象
            hashSet.add(new HashSetA(i));//
        }

        for(int i = 1; i <= 7; i++) {//在table的另外一条链表上添加了 7个B对象
            hashSet.add(new HashSetB(i));//
        }

    }
}

class HashSetB {
    private int n;

    public HashSetB(int n) {
        this.n = n;
    }
    @Override
    public int hashCode() {
        return 200;
    }
}

class HashSetA {
    private int n;

    public HashSetA(int n) {
        this.n = n;
    }
    @Override//这种情况下，两个A实例进行equals比较时 ，因为比较的是hashCode，所以自然不同
    //hash值决定了你在table中的位置，而老韩重写了equals 保证第一次传入的key 的hash值为100  往后添加的那几个元素的hash也为100
    public int hashCode() {
        return 100;
    }
}
/*
hash方法中如果你没有特地Override的话，该hash方法会默认调用Object类中的hashCode算法。
如果你在类中自己重写了hashCode方法的话，那到了算hashCode的时候会特地调用你之前Override之后的
HashCode方法!!值得一提的是，Object方法中的HashCode方法，也就是真真正正的算出hash码的方法是native
修饰的,其核心是C++写的，你无法继续追下去了
 */

/*
代码分析题：已知：Person类按照id和name重写了hashCode和equals方法，问下面代码输出什么？

分析源码：添加元素的源码中在比较时是先比hash值，再地址key == p.key，最后equals
 */

class HashSetExercise {
    public static void main(String[] args) {
        HashSet set = new HashSet();//ok
        HashSetPerson p1 = new HashSetPerson(1001,"AA");//ok
        HashSetPerson p2 = new HashSetPerson(1002,"BB");//ok
        set.add(p1);                                              //ok
        set.add(p2);                                              //ok
        p1.name = "CC";
        //remove移除的是根据传入的对象的hash值来移除的，按修改之后的p1解析不到之前p1的位置
        set.remove(p1);                                           //删除失败，虽然存储位置没有变，但由于重写了hashCode，remove无法再找到之前的存储位置
        System.out.println(set);                                  //2个元素
        set.add(new HashSetPerson(1001,"CC"));
        System.out.println(set);                                  //3个元素

        //此处add会加在p1后面，因为p1的id和name与之一样计算的hash也一样，比较发现但key不同（不是同一个对象），然后挂在p1后的链表
        set.add(new HashSetPerson(1001,"AA"));
        System.out.println(set);                                  //4个元素

    }
}

class HashSetPerson {
    public String name;
    public int id;

    public HashSetPerson(int id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashSetPerson person = (HashSetPerson) o;
        return id == person.id &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
