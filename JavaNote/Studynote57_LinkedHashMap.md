# LinkedHashMap

LinkedHashMap 继承了 HashMap

```java
public class LinkedHashMap<K,V>
    extends HashMap<K,V>
    implements Map<K,V>
{
    //...前略
    static class Entry<K,V> extends HashMap.Node<K,V> {
        Entry<K,V> before, after;
        Entry(int hash, K key, V value, Node<K,V> next) {
            super(hash, key, value, next);
        }
    }
    //后略...
}
```

为了提高查找效率，HashMap 在插入的时候对键做了一次哈希算法，这就导致插入元素顺序、遍历结果是无序的。LinkedHashMap 加入了 before 和 after 属性，用来维护当前元素的前一个元素和后一个元素的顺序。

迭代 LinkedHashMap 时不需要像 HashMap 那样遍历整个`table`，而只需要直接遍历`header`指向的双向链表即可，也就是说 LinkedHashMap 的迭代时间就只跟`entry`的个数相关，而跟`table`的大小无关。

## 源码解析

### 添加元素

LinkedHashMap 并未重写 HashMap 的 `put()` 方法，而是重写了 `put()` 方法需要调用的内部方法 `newNode()`。

```java
//HashMap 
Node<K,V> newNode(int hash, K key, V value, Node<K,V> next) {
    return new Node<>(hash, key, value, next);
}

//LinkedHashMap
HashMap.Node<K,V> newNode(int hash, K key, V value, HashMap.Node<K,V> e) {
    LinkedHashMap.Entry<K,V> p =
            new LinkedHashMap.Entry<>(hash, key, value, e);
    linkNodeLast(p);
    return p;
}
```

在 LinkedHashMap 中，链表中的节点顺序是按照插入顺序维护的。当使用 put() 方法向 LinkedHashMap 中添加键值对时，会将新节点插入到链表的尾部，并更新 before 和 after 属性，以保证链表的顺序关系——由 `linkNodeLast()` 方法来完成：

```java
//将指定节点插入到链表的尾部
private void linkNodeLast(LinkedHashMap.Entry<K,V> p) {
    LinkedHashMap.Entry<K,V> last = tail; // 获取链表的尾节点
    tail = p; // 将 p 设为尾节点
    if (last == null)
        head = p; // 如果链表为空，则将 p 设为头节点
    else {
        p.before = last; // 将 p 的前驱节点设为链表的尾节点
        last.after = p; // 将链表的尾节点的后继节点设为 p
    }
}
```

### 访问元素

LinkedHashMap 不仅能够维持插入顺序，还能够维持访问顺序。访问包括调用 `get()` 方法、`remove()` 方法和 `put()` 方法。

要维护访问顺序，需要我们在声明 LinkedHashMap 的时候指定三个参数。

```java
LinkedHashMap<String, String> map = new LinkedHashMap<>(16, .75f, true);
//构造器传入的参数分别是：initialCapacity 初始容量，loadFactor 负载因子，accessOrder 访问顺序
```

LinkedHashMap 通过下面三个方法维持访问顺序，在执行get，put，remove后会移动元素位置

```java
void afterNodeAccess(Node<K,V> p) { }
void afterNodeInsertion(boolean evict) { }
void afterNodeRemoval(Node<K,V> p) { }
```

当我们使用 `get()` 方法访问元素后，该元素会被放在链表最后一位，也就是最不经常访问的会留在头部。

```java
void afterNodeAccess(HashMap.Node<K,V> e) { // move node to last
    LinkedHashMap.Entry<K,V> last;
    if (accessOrder && (last = tail) != e) { // 如果按访问顺序排序，并且访问的节点不是尾节点
        LinkedHashMap.Entry<K,V> p = (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
        p.after = null; // 将要移动的节点的后继节点设为 null
        if (b == null)
            head = a; // 如果要移动的节点没有前驱节点，则将要移动的节点设为头节点
        else
            b.after = a; // 将要移动的节点的前驱节点的后继节点设为要移动的节点的后继节点
        if (a != null)
            a.before = b; // 如果要移动的节点有后继节点，则将要移动的节点的后继节点的前驱节点设为要移动的节点的前驱节点
        else
            last = b; // 如果要移动的节点没有后继节点，则将要移动的节点的前驱节点设为尾节点
        if (last == null)
            head = p; // 如果尾节点为空，则将要移动的节点设为头节点
        else {
            p.before = last; // 将要移动的节点的前驱节点设为尾节点
            last.after = p; // 将尾节点的后继节点设为要移动的节点
        }
        tail = p; // 将要移动的节点设为尾节点
        ++modCount; // 修改计数器
    }
}
```

在插入元素的时候，需要调用 `put()` 方法，该方法最后会调用 `afterNodeInsertion()` 方法，这个方法被 LinkedHashMap 重写了。

```java
/**
 * 在插入节点后，如果需要，可能会删除最早加入的元素
 *
 * @param evict 是否需要删除最早加入的元素
 */
void afterNodeInsertion(boolean evict) { // possibly remove eldest
    LinkedHashMap.Entry<K,V> first;
    if (evict && (first = head) != null && removeEldestEntry(first)) { // 如果需要删除最早加入的元素
        K key = first.key; // 获取要删除元素的键
        removeNode(hash(key), key, null, false, true); // 调用 removeNode() 方法删除元素
    }
}

```

`removeEldestEntry()` 方法会判断第一个元素是否超出了可容纳的最大范围，如果超出，那就会调用 `removeNode()` 方法对最不经常访问的那个元素进行删除。

### LRU缓存

可以使用 LinkedHashMap 来实现 LRU 缓存，LRU 是 Least Recently Used 的缩写，即最近最少使用，是一种常用的页面置换算法，选择最近最久未使用的页面予以淘汰。

```java
/** 一个固定大小的FIFO替换策略的缓存 */
class FIFOCache<K, V> extends LinkedHashMap<K, V>{
    private final int cacheSize;
    public FIFOCache(int cacheSize){
        this.cacheSize = cacheSize;
    }

    // 当Entry个数超过cacheSize时，删除最老的Entry
    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
       return size() > cacheSize;
    }
}
```

底层实现逻辑：[算法就像搭乐高：带你手撸 LRU 算法 | labuladong 的算法笔记](https://labuladong.online/algo/di-yi-zhan-da78c/shou-ba-sh-daeca/suan-fa-ji-8674e/)，对应的算法题：[LRU Cache - LeetCode](https://leetcode.com/problems/lru-cache/)





参考资料：

[JCFInternals/markdown/7-LinkedHashSet and LinkedHashMap.md at master · CarpenterLee/JCFInternals (github.com)](https://github.com/CarpenterLee/JCFInternals/blob/master/markdown/7-LinkedHashSet and LinkedHashMap.md)
