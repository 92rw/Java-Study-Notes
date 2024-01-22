# 线程数据共享和安全

## ThreadLocal介绍

1. ThreadLocal的作用，可以实现①在同一个线程②数据共享，从而解决多线程数据安全问题
2. ThreadLocal可以给当前线程关联一个数据（普通变量、对象、数组）set 方法
3. ThreadLocal可以像 Map 一样存取数据，key 为当前线程，get 方法
4. 每一个 ThreadLoca 对象，只能为当前线程关联一个数据，如果要为当前线程关联多个数据，就需要使用多个
   ThreadLocal对象实例
5. 每个 ThreadLocal 对象实例定义的时候，一般为 static 类型
6. ThreadLocal 中保存数据，在线程销毁后，会自动释放



## 特点

1、get方法不需要指定参数名，因为只能放入/取出一个对象



set方法源码解读

```java
public void set(T value) {
    //1. 获取当前线程, 关联到当前线程!
    Thread t = Thread.currentThread();
    //2. 通过线程对象, 获取到ThreadLocalMap
    //   ThreadLocalMap 是静态内部类，类型 ThreadLocal.ThreadLocalMap
    ThreadLocalMap map = getMap(t);
    //3. 如果map不为null, 将数据(dog,pig..) 放入map
    //   key:threadLocal value:存放的数据
    //   从这个源码我们已然看出一个threadlocal只能关联一个数据，再次调用set方法, 就会替换
    //4. 如果map为null, 就创建一个和当前线程关联的ThreadLocalMap, 并且该数据放入
    if (map != null) {
        map.set(this, value);
    } else {
        createMap(t, value);
    }
}
```

每一个调用ThreadLocal的线程，都维护了该类型静态内部类ThreadLocalMap类型的threadLocals属性，该属性的table表中以key-value形式存放了Entry类型的数据ThreadLocal<?> k, Object v



get方法源码解读

```java
public T get() {
    //1. 先得到当前的线程对象
    Thread t = Thread.currentThread();
    //2.通过线程获取到对应的ThreadLocalMap
    ThreadLocalMap map = getMap(t);
    if (map != null) {
         //3. 如果map不为空, 根据当前的 threadlocal对象，得到对应的Entry
        ThreadLocalMap.Entry e = map.getEntry(this);
        //4. 如果e 不为null
        if (e != null) {
            @SuppressWarnings("unchecked")
            //返回当前threadlocal关联的数据value
            T result = (T)e.value;
            return result;
        }
    }
    return setInitialValue();
}
```

说明：ThreadLocal还有很多细节，涉及到弱引用等，暂不深入
