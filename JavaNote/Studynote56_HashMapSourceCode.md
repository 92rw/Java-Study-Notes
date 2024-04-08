# HashMap源码分析

### 加入元素

通过新建HashMap，并在put方法中下断点，其具体执行流程：

1. 执行构造器 `new HashMap()`

   * 初始化加载因子 loadfactor = 0.75
   * HashMap$Node[] table = null

2. 执行put 调用 hash方法，计算 key的 hash值 `(h = key.hashCode()) ^ (h >>> 16)`

   ```java
   public V put(K key, V value) {
       return putVal(hash(key), key, value, false, true);
   }
   ```

3. 调用putVal方法

   ```java
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
              boolean evict) {
           Node<K,V>[] tab; Node<K,V> p; int n, i;//辅助变量
           //如果底层的table 数组为null, 或者 length =0 , 就扩容到16
           if ((tab = table) == null || (n = tab.length) == 0)
               n = (tab = resize()).length;
           //取出hash值对应的table的索引位置的Node, 如果为null, 就直接把加入的k-v, 创建成一个 Node ,加入该位置即可
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
   ```

HashMap 的初始大小是 16，在添加第一个元素的时候，需要通过键的哈希码在大小为 16 的数组中确定一个位置（索引）

hash 方法的原理是，先获取 key 对象的 hashCode 值，然后将其高位与低位进行异或操作，得到一个新的哈希值。为什么要进行异或操作呢？因为对于 hashCode 的高位和低位，它们的分布是比较均匀的，如果只是简单地将它们加起来或者进行位运算，容易出现哈希冲突，而异或操作可以避免这个问题。

然后将新的哈希值取模（mod），得到一个实际的存储位置。这个取模操作的目的是将哈希值映射到桶（Bucket）的索引上，桶是 HashMap 中的一个数组，每个桶中会存储着一个链表（或者红黑树），装载哈希值相同的键值对（没有相同哈希值的话就只存储一个键值对）。

### 扩容

```java
    final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;//保存原始的哈希表数组
        int oldCap = (oldTab == null) ? 0 : oldTab.length;//获取原始哈希表的容量
        int oldThr = threshold;//获取原始哈希表的阈值
        int newCap, newThr = 0;//新哈希表的容量和阈值
        if (oldCap > 0) {// 原哈希表不为空
            if (oldCap >= MAXIMUM_CAPACITY) {//MAXIMUM_CAPACITY 为最大容量，2 的 30 次方 = 1<<30
                threshold = Integer.MAX_VALUE;// 容量调整为 Integer 的最大值 0x7fffffff（十六进制）=2 的 31 次方-1
                return oldTab;//不再进行扩容，直接返回原哈希表
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold翻倍得到新容量
        }
        else if (oldThr > 0) // initial capacity was placed in threshold原哈希表为空但有阈值的情况
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults原哈希表和阈值都为空
            newCap = DEFAULT_INITIAL_CAPACITY;//新容量默认值16
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);//新阈值设为新容量乘以负载因子
        }
        if (newThr == 0) {//第一次进入时为true
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;//将新阈值设为 newThr
        @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];//创建新的哈希表数组
        table = newTab;//更新 table 引用为新的哈希表数组 newTab
        if (oldTab != null) {// 对oldTab中所有元素进行rehash。由于每次扩容是2次幂的扩展(指数组长度/桶数量扩为原来2倍)，所以，元素的位置要么是在原位置，要么是在原位置再移动2次幂的位置
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {//数组j位置的元素不为空，需要该位置上的所有元素进行rehash
                    oldTab[j] = null;//将旧数组中该位置的元素置为 null，以便垃圾回收
                    if (e.next == null)// 桶中只有一个元素（无冲突），则直接rehash
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)//树结构
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);//分成两个链表
                    else { // preserve order桶中是链表结构（JDK1.7中旧链表迁移新链表的时候，用的是头插法，如果在新表的数组索引位置相同，则链表元素会倒置；但是JDK1.8不会倒置，用的是双指针）
                        Node<K,V> loHead = null, loTail = null;//low位链表，其桶位置不变，head和tail分别代表首尾指针
                        Node<K,V> hiHead = null, hiTail = null;//high位链表，其桶位于追加后的新数组中
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {//将旧链表拆分成两条新的链表，计算新链表在扩容后的数组中的新下标
                                if (loTail == null)
                                    loHead = e;// 将该元素作为低位链表的头结点
                                else
                                    loTail.next = e;// 如果低位链表已经有结点，将该元素加入低位链表的尾部
                                loTail = e;// 更新低位链表的尾结点
                            }
                            else {// 如果该元素在高位链表中
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        //是0的话索引没变，是1的话索引变成“原索引+oldCap”
                        if (loTail != null) {
                            loTail.next = null;// 将低位链表的尾结点指向 null，以便垃圾回收
                            newTab[j] = loHead;// 将低位链表作为新数组对应位置的元素
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

```

reeNode 既是一个红黑树结构，也是一个双链表结构。

判断节点 `e instanceof TreeNode` 为 true，则调用 HashMap.TreeNode#split 方法对树进行拆分，而**拆分主要用的是 TreeNode 的链表属性**。

```java
final void split(HashMap<K,V> map, Node<K,V>[] tab, int index, int bit) {
    TreeNode<K,V> b = this;
    // Relink into lo and hi lists, preserving order
    TreeNode<K,V> loHead = null, loTail = null;
    TreeNode<K,V> hiHead = null, hiTail = null;
    int lc = 0, hc = 0; // 用于决定红黑树是否要转回链表
    for (TreeNode<K,V> e = b, next; e != null; e = next) { // 对节点e进行遍历（首先明确：TreeNode既是一个红黑树结构，也是一个双链表结构）
        next = (TreeNode<K,V>)e.next;
        e.next = null; // 把e的下一个节点赋值给next后，断开e与e.next节点
        if ((e.hash & bit) == 0) { // 原索引
            if ((e.prev = loTail) == null)
                loHead = e;
            else
                loTail.next = e;
            loTail = e;
            ++lc;
        }
        else { // 原索引 + oldCap
            if ((e.prev = hiTail) == null)
                hiHead = e;
            else
                hiTail.next = e;
            hiTail = e;
            ++hc;
        }
    }

    if (loHead != null) {
        if (lc <= UNTREEIFY_THRESHOLD)
            tab[index] = loHead.untreeify(map); // 转为链结构
        else {
            tab[index] = loHead;
            if (hiHead != null) // (else is already treeified)
                loHead.treeify(tab); // 转换成树结构
        }
    }
    if (hiHead != null) {
        if (hc <= UNTREEIFY_THRESHOLD)
            tab[index + bit] = hiHead.untreeify(map);
        else {
            tab[index + bit] = hiHead;
            if (loHead != null)
                hiHead.treeify(tab);
        }
    }
}
```

HashMap 的内部实现是通过一个数组和链表或红黑树的组合来实现的。当我们往 HashMap 中不断添加元素时，HashMap 会自动进行扩容操作（条件是元素数量达到负载因子（load factor）乘以数组长度时），以保证其存储的元素数量不会超出其容量限制。下面是 HashMap 的扩容机制：

1、在进行扩容操作时，HashMap 会先将数组的长度扩大一倍，然后将原来的元素重新散列（到新的数组中。由于元素的散列位置是通过 key 的 hashcode 和数组长度取模得到的，因此在数组长度扩大后，元素的散列位置也会发生一些改变。

2、在重新散列元素时，如果一个元素的散列位置发生了改变，那么它需要被移动到新的位置。如果新的位置上已经有元素了，那么这个元素就会被添加到链表的末尾，如果链表的长度超过了阈值（8个），那么它将会被转换成红黑树。

### 树化

如果table 为null ,或者大小还没有到 64，暂时不树化，而是进行扩容。无法扩容时才会真正的树化 -> 剪枝

```java
  final void treeifyBin(Node<K,V>[] tab, int hash) {
    int n, index; Node<K,V> e;
    if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
        resize();
}
```

演示树化机制的代码：在循环语句中下断点，查看每次循环过程中容器的组成

```java
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
```



# HashMap源码中的位运算

## 取模计算数组索引

在 HashMap 添加或修改元素的put方法中，会调用hash(key) 方法

```java
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}

```

这个方法会计算key的哈希值

```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}

```

- 参数 key：需要计算哈希码的键值。
- `key == null ? 0 : (h = key.hashCode()) ^ (h >>> 16)`：这是一个三目运算符，如果键值为null，则哈希码为0（依旧是说如果键为 null，则存放在第一个位置）；否则，通过调用`hashCode()`方法获取键的哈希码，并将其与右移16位的哈希码进行异或运算。
- `^` 位运算符：异或运算符，将两个数的二进制位进行比较，如果相同则为0，不同则为1。
- `h >>> 16`：将哈希码向右移动16位，相当于将原来的哈希码分成了两个16位的部分。
- 这个操作的目的在于让高位的信息也参与到哈希码的计算中，以增加哈希码的随机性。最终返回的是经过异或运算后得到的哈希码值，可以减少哈希冲突、提高数据分散性

调用putVal方法时，也会

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
    // 数组
    HashMap.Node<K,V>[] tab; 
    // 元素
    HashMap.Node<K,V> p; 

    // n 为数组的长度 i 为下标
    int n, i;
    // 数组为空的时候
    if ((tab = table) == null || (n = tab.length) == 0)
        // 第一次扩容后的数组长度
        n = (tab = resize()).length;
    // 计算节点的插入位置，如果该位置为空，则新建一个节点插入
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
}

```

从 HashMap 中 get 的时候会调用 `getNode` 方法，也需要计算hash值：

```java
final Node<K,V> getNode(int hash, Object key) {
    // 获取当前的数组和长度，以及当前节点链表的第一个节点（根据索引直接从数组中找）
    Node<K,V>[] tab; 
    Node<K,V> first, e; 
    int n; 
    K k;
    if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
        // 如果第一个节点就是要查找的节点，则直接返回
        if (first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k))))
            return first;
        // 如果第一个节点不是要查找的节点，则遍历节点链表查找
        if ((e = first.next) != null) {
            do {
                if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    // 如果节点链表中没有找到对应的节点，则返回 null
    return null;
}

```

取模运算（（“Modulo Operation”））和取余运算（（“Remainder Operation ”））是两种不同的运算方式，在数学或算术中，这两者都被用来描述当一个数除以另一个数后所得的剩余部分。在 Java 中，通常使用 % 运算符来表示取余（尽管实际操作可能是取模），用 `Math.floorMod()` 来表示取模。

不同之处：

- 取余操作丢弃商的小数部分，结果的符号与被除数的符号相同。比如 `-7 % 3 = -1`。
- 取模操作在执行除法时，它会向下取整到最接近的整数。结果的符号与除数的符号相同。比如 `Math.floorMod(-7, 3) = 2`

取模运算 `(n - 1) & hash` 就是把键的哈希码经过 `hash()` 方法计算后，再和（数组长度-1）做了一个“与”运算。采用这种方式的原因是当b是2的幂时`a % b = a & (b-1)`，可以简化运算，而 HashMap 的空间容量都是2的幂。

**取模运算是为了计算数组的下标**。

- put 的时候计算下标，把键值对放到对应的桶上。
- get 的时候通过下标，把键值对从对应的桶上取出来。

ConcurrentHashMap 中的 hash 算法进行了取绝对值操作，避免了 e.hash 为负数的情况。
java.util.concurrent.ConcurrentHashMap#spread

```java
static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash
static final int spread(int h) {
    return (h ^ (h >>> 16)) & HASH_BITS;
}
```

如上，HASH_BITS 的作用是使 hash 值为正数，hash 值为负数在 ConcurrentHashMap 有特殊的含义

## 计算扩容后新索引值

扩容前索引值为 `(n - 1) & hash` ，扩容后的容量为 `2 * oldCap` ，对于扩容后的节点 e，计算 e.hash & oldCap。
**当 e.hash & oldCap == 0，则节点在新数组中的索引值与旧索引值相同。**

**当 e.hash & oldCap != 0，则节点在新数组中的索引值为旧索引值+旧数组容量。**



参考资料：

[java - 阅读 JDK 源码：HashMap 扩容总结及图解 - Sumkor's Blog - SegmentFault 思否](https://segmentfault.com/a/1190000039302830)

[HashMap在处理key时的两个优化 - 知乎 (zhihu.com)](https://www.zhihu.com/question/52726115/answer/2515210291)

参考资料：

[Java7/8 中的 HashMap 和 ConcurrentHashMap 全解析_Javadoop](https://www.javadoop.com/post/hashmap)

[java - 阅读 JDK 源码：ConcurrentHashMap 扩容总结 (发现源码的BUG！) - Sumkor's Blog - SegmentFault 思否](https://segmentfault.com/a/1190000039315702)

[《Java源码分析》：HashMap_java源码学习-CSDN博客](https://blog.csdn.net/u010412719/article/details/51980632)
