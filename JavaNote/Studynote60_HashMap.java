import java.util.*;

public class Studynote60_HashMap {
}
/*
1）(k,v)是一个Node实现了Map.Entry<K,V>，查看HashMap的源码可以看到
2）jdk7.0的hashmap底层实现[数组+链表]，jdk8.0底层[数组+链表+红黑树]

>扩容机制(和HashSet相同)
1)HashMap底层维护了Node类型的数组table，默认为null
2)当创建对象时，将加载因子(loadfactor)初始化为0.75
3）当添加key-val时，通过key的哈希值得到在table的索引。然后判断该索引处是否有元素
如果没有元素直接添加。如果该素引处有元素，继续判断该元素（Node）的key是否和准备加入的
key是否相等，如果相等，则直接替换val；如果不相等需要判断是树结构还是链表结构，做出
相应处理。如果添加时发现容量不够，则需要扩容。
4)第1次添加，则需要扩容table容量为16，临界值(threshold)为12（16*0.75）
5）以后再扩容，则需要扩容table容量为原来的2倍(32)，临界值为原来的2倍(24),依次类推
6)在 Java8 中,如果一条链表的元素个数超过 TREEIFY THRESHOLD(默认是 8）,并且 table 的长度 >= MIN_TREEIFY_CAPACITY(默认64),就会进行树化(红黑树)
 */


class HashMapSource1 {
    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.put("java", 10);//ok
        map.put("php", 10);//ok
        map.put("java", 20);//替换value

        System.out.println("map=" + map);//

        /*解读HashMap的源码+图解
        1. 执行构造器 new HashMap()
           初始化加载因子 loadfactor = 0.75
           HashMap$Node[] table = null
        2. 执行put 调用 hash方法，计算 key的 hash值 (h = key.hashCode()) ^ (h >>> 16)
            public V put(K key, V value) {//K = "java" value = 10
                return putVal(hash(key), key, value, false, true);
            }
         3. 执行 putVal
         final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
                Node<K,V>[] tab; Node<K,V> p; int n, i;//辅助变量
                //如果底层的table 数组为null, 或者 length =0 , 就扩容到16
                if ((tab = table) == null || (n = tab.length) == 0)
                    n = (tab = resize()).length;
                //取出hash值对应的table的索引位置的Node, 如果为null, 就直接把加入的k-v
                //, 创建成一个 Node ,加入该位置即可
                if ((p = tab[i = (n - 1) & hash]) == null)
                    tab[i] = newNode(hash, key, value, null);
                else {
                    Node<K,V> e; K k;//辅助变量
                        // 如果table的索引位置的key的hash相同和新的key的hash值相同，并满足
                        //(table现有索引位置的结点的key和准备添加的key是同一个对象  || 两个 key对比后的 equals()返回为 true)
                        //就认为不能加入新的k-v
                    if (p.hash == hash &&   //不同哈希值也可能会计算出相同的索引，因此在相同索引的位置会再次判断哈希值是否相等
                        ((k = p.key) == key || (key != null && key.equals(k))))
                        e = p;
                    else if (p instanceof TreeNode)//如果当前的table的已有的Node 是红黑树，就按照红黑树的方式处理
                        e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
                    else {
                        //如果找到的结点，后面是链表，就循环比较
                        for (int binCount = 0; ; ++binCount) {//死循环
                            if ((e = p.next) == null) {     //这里e = p.next，可以和p配合做循环指针，结束之后一定会值null，又会为后续要不要替换value做了支撑条件
                            //如果遍历整个链表没有相同值,就加到该链表的最后
                                p.next = newNode(hash, key, value, null);
                            //加入后，判断当前链表的个数，是否已经到8个，到8个，后就调用 treeifyBin 方法进行红黑树的转换
                                if (binCount >= TREEIFY_THRESHOLD - 1) // binCount=0时，链表添加第2个元素，binCount=7时，链表添加第9个元素，进如树化判断的代码
                                    treeifyBin(tab, hash);
                                break;
                            }
                            if (e.hash == hash && //如果在循环比较过程中，发现有相同,就break,就只是替换value
                                ((k = e.key) == key || (key != null && key.equals(k))))
                                break;
                            p = e;
                        }
                    }
                    if (e != null) { // existing mapping for key
                        V oldValue = e.value;
                        if (!onlyIfAbsent || oldValue == null)  //onlyIfAbsent=true表示是否以删除旧键值对来插入新键值对，false表示不删除旧键值对，只是替换value
                            e.value = value; //替换，key对应value
                        afterNodeAccess(e);
                        return oldValue;
                    }
                }
                ++modCount;//每增加一个Node ,就size++
                if (++size > threshold)     //如size > 临界值[12-24-48]，就扩容
                    resize();               //resize()方法会用新的长度重新计算下标。把旧链表移动到新位置，因为n发生了变化。从而保证(n-1)&hash结果值的统一性。
                afterNodeInsertion(evict);
                return null;
            }

              5. 关于树化(转成红黑树)
              //如果table 为null ,或者大小还没有到 64，暂时不树化，而是进行扩容.
              //否则才会真正的树化 -> 剪枝
              final void treeifyBin(Node<K,V>[] tab, int hash) {
                int n, index; Node<K,V> e;
                if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
                    resize();
            }
         */

    }
}


class HashMapSource2 {
    public static void main(String[] args) {
        //演示树化的机制：添加到9个元素的时候table表开始扩容，到11个开始树化（满足单链表8个Node结点+table扩容至64）
        HashMap hashMap1 = new HashMap();
        for(int i = 1; i <= 12; i++) {
            hashMap1.put(new HashMapNum(i), "hello");//扩容后链表位置是否位移，根本原因在于底层判断机制：(e.hash & oldCap) == 0，满足条件则在原来位置的屁股后面直接加
        }
        System.out.println("hashMap1=" + hashMap1);

        //验证 table 的扩容：此时因为各条链表小于8，所以会按照0.75*table来判定是否扩容
        //0 -> 16(12) -> 32(24) -> 64(64*0.75=48)-> 128 (96) ->...
        //在随机哈希代码下，桶中的节点频率遵循 泊松分布，文中给出了桶长度k的频率表。 由频率表可以看出，桶的长度超过8的概率非常非常小。所以作者应该是根据 概率统计而选择了8作为阀值。
        HashMap hashMap2 = new HashMap();
        for(int i = 1; i <= 12; i++) {
            hashMap2.put(i, "hello");
        }
        hashMap2.put("aaa", "bbb");

        System.out.println("hashMap2=" + hashMap2);//12个 k-v

        //

    }
}

class HashMapNum  {
    private int num;
    public HashMapNum(int num) {
        this.num = num;
    }

    //所有的HashMapNum对象的hashCode都是100
    @Override
    public int hashCode() {
        return 100;
    }

    @Override
    public String toString() {
        return "\nA{" +
                "num=" + num +
                '}';
    }
}

/**
 * 按要求完成下列任务
 * 1）使用HashMap类实例化一个Map类型的对象m，键（String）和值（int）分别用于存储员工的姓名和工资，
 * 存入数据如下：	jack—650元；tom—1200元；smith——2900元；
 * 2）将jack的工资更改为2600元
 * 3）为所有员工工资加薪100元；
 * 4）遍历集合中所有的员工
 * 5）遍历集合中所有的工资
 */

class HashMapExercise {
    public static void main(String[] args) {

        Map m = new HashMap();
        m.put("jack", 650);//int->Integer
        m.put("tom", 1200);//int->Integer
        m.put("smith", 2900);//int->Integer
        System.out.println(m);

        m.put("jack", 2600);//替换，更新
        System.out.println(m);

        //为所有员工工资加薪100元；
        //keySet
        Set keySet = m.keySet();
        for (Object key : keySet) {
            //更新
            m.put(key, (Integer)m.get(key) + 100);//get(key) 得到的值编译类型是 Object，需要向下转型
        }
        System.out.println(m);

        System.out.println("=============遍历=============");
        //遍历 EntrySet
        Set entrySet = m.entrySet();
        //迭代器
        Iterator iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry =  (Map.Entry)iterator.next();
            System.out.println(entry.getKey() + "-" + entry.getValue());

        }

        System.out.println("====遍历所有的工资====");
        Collection values = m.values();
        for (Object value : values) {
            System.out.println("工资=" + value);
        }

    }
}

