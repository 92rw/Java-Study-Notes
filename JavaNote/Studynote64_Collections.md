# Collections工具类

Collections是一个操作Set、List和Map等集合的工具类，在 `java.util` 包下

1. ## 排序操作

   * `reverse(List list)`：反转顺序
   * `shuffle(List list)`：洗牌，将顺序打乱
   * `sort(List list)`：自然升序
   * `sort(List list, Comparator c)`：按照自定义的比较器排序
   * `swap(List list, int i, int j)`：将 i 和 j 位置的元素交换位置

2. ## 查找操作

   * `binarySearch(List list, Object key)`：二分查找法，前提是 List 已经排序过了
   * `max(Collection coll)`：返回最大元素
   * `max(Collection coll, Comparator comp)`：根据自定义比较器，返回最大元素
   * `min(Collection coll)`：返回最小元素
   * `min(Collection coll, Comparator comp)`：根据自定义比较器，返回最小元素
   * `fill(List list, Object obj)`：使用指定对象填充
   * `frequency(Collection c, Object o)`：返回指定对象出现的次数

3. ## 替换

   - copy(List dest,List src)：将src中的内容复制到dest中
   - replaceAll(List list, Object oldVal, Object newVal): 使用新值替换List对象的所有旧值
   - `addAll(Collection<? super T> c, T... elements)`，往集合中添加元素
   - `disjoint(Collection<?> c1, Collection<?> c2)`，判断两个集合是否没有交集

4. ## 同步控制

   ArrayList 和 HashMap 是线程不安全的，没法在多线程环境下使用，那 Collections 工具类中提供了多个 synchronizedXxx 方法，这些方法会返回一个同步的对象，从而解决多线程中访问集合时的安全问题。运行效率和Vector和Hashtable差不多，不如直接使用并发包下的 `CopyOnWriteArrayList`、`ConcurrentHashMap`。

      ```java
      static class SynchronizedList<E>
          extends SynchronizedCollection<E>
          implements List<E> {
          private static final long serialVersionUID = -7754090372962971524L;
      
          final List<E> list;
      
          SynchronizedList(List<E> list) {
              super(list); // 调用父类 SynchronizedCollection 的构造方法，传入 list
              this.list = list; // 初始化成员变量 list
          }
      
          // 获取指定索引处的元素
          public E get(int index) {
              synchronized (mutex) {return list.get(index);} // 加锁，调用 list 的 get 方法获取元素
          }
          
          // 在指定索引处插入指定元素
          public void add(int index, E element) {
              synchronized (mutex) {list.add(index, element);} // 加锁，调用 list 的 add 方法插入元素
          }
          
          // 移除指定索引处的元素
          public E remove(int index) {
              synchronized (mutex) {return list.remove(index);} // 加锁，调用 list 的 remove 方法移除元素
          }
      }
      
      ```

      

5. ## 不可变集合

   * `emptyXxx()`：制造一个空的不可变集合
   * `singletonXxx()`：制造一个只有一个元素的不可变集合
   * `unmodifiableXxx()`：为指定集合制作一个不可变集合



# 第三方工具类

目前比较主流的是`Spring`的`org.springframework.util`包下的 CollectionUtils 工具类，和`Apache`的`org.apache.commons.collections`包下的 CollectionUtils 工具类。

* 集合判空

  `isEmpty`方法可以轻松判断集合是否为空，`isNotEmpty`方法判断集合不为空。

* 两个集合之间操作

  union取并集，intersection取交集，disjunction取交集后的补集，subtract取差集



参考资料：

[Java Collections：专为集合框架而生的工具类 | 二哥的Java进阶之路 (javabetter.cn)](https://javabetter.cn/common-tool/collections.html)
