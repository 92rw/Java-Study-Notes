# Vector

Vector效率低，仅在需要线程安全的时候使用

1. Vector类的定义说明
   `public class Vector<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable`
2. Vector底层也是一个对象数组，`protected Object[] elementData;`
3. Vector是线程同步的，即线程安全，Vector类的操作方法带有synchronized
4. 在开发中，需要线程同步安全时，考虑使用Vector（支持线程同步和互斥）

|           | 底层结构         | 版本   | 线程安全（同步）效率 | 扩容倍数                                                     |
| --------- | ---------------- | ------ | -------------------- | ------------------------------------------------------------ |
| ArrayList | 可变数组Object[] | jdk1.2 | 不安全，效率高       | 如果有参构造1.5倍;如果是无参1.第一次10；2.从第二次开始1.5倍扩 |
| Vector    | 可变数组Object[] | jdk1.0 | 安全，效率不高       | 如果是无参，默认10，满后按2倍扩容;如果指定大小，则每次直接按2倍 |



## 数据结构：栈

Stack 是 Vector 的一个子类，本质上也是由动态数组实现的，实现了先进后出的功能

特有方法：

* public synchronized int search(Object o)：返回元素相对栈顶的偏移量
* public boolean empty()：返回是否为空
* public synchronized E peek()：返回栈顶元素
* public synchronized E pop()：返回并移除栈顶的元素
* public E push(E item)：将`element`压入堆栈，同时也返回`element`



## 源码解读

```java
import java.util.Vector;

public class Studynote52_Vector {
    public static void main(String[] args) {
        //无参构造器
        //有参数的构造
        Vector vector = new Vector(8);
        for (int i = 0; i < 10; i++) {
            vector.add(i);
        }
        vector.add(100);
        System.out.println("vector=" + vector);
        //解读源码
        //1. 新建 Vector 类的对象
        /*1）无参构造器
            public Vector() {
                this(10);
            }
         2）一个参数的有参构造器
            public Vector(int initialCapacity) {
                this(initialCapacity, 0);
            }
         3）两个参数的有参构造器：第二个参数指定数组每次扩容数量
             public Vector(int initialCapacity, int capacityIncrement) {
                super();
                if (initialCapacity < 0)
                    throw new IllegalArgumentException("Illegal Capacity: "+
                                                       initialCapacity);
                this.elementData = new Object[initialCapacity];
                this.capacityIncrement = capacityIncrement;
            }
            */
        /*数组元素增加
         2. vector.add(i)
         //jdk11增加了数组中的真实元素个数，直接判断就可以知道要不要扩容，不要这个函数了
         2.1  //下面这个方法就添加数据到vector集合
            public synchronized boolean add(E e) {
                modCount++;
                ensureCapacityHelper(elementCount + 1);
                elementData[elementCount++] = e;
                return true;
            }
          2.2  //确定是否需要扩容 扩容条件 ： minCapacity - elementData.length>0
            private void ensureCapacityHelper(int minCapacity) {
                // overflow-conscious code
                if (minCapacity - elementData.length > 0)
                    grow(minCapacity);
            }
          2.3 //如果 需要的数组大小 不够用，就扩容 , 扩容的算法
              //newCapacity = oldCapacity + ((capacityIncrement > 0) ?
              //                             capacityIncrement : oldCapacity);
              //就是扩容两倍.
            private void grow(int minCapacity) {
                // overflow-conscious code
                int oldCapacity = elementData.length;
                int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                                                 capacityIncrement : oldCapacity);
                if (newCapacity - minCapacity < 0)
                    newCapacity = minCapacity;
                if (newCapacity - MAX_ARRAY_SIZE > 0)
                    newCapacity = hugeCapacity(minCapacity);
                elementData = Arrays.copyOf(elementData, newCapacity);
            }
         */

    }
}
```

