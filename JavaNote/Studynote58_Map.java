import java.util.*;

public class Studynote58_Map {
    public static void main(String[] args) {
        //类似 Python 的字典
        //Map 接口实现类的特点, 使用实现类HashMap
        //1. Map 与 Collection 并列存在。用于保存具有映射关系的数据:Key-Value(双列元素)
        //2. Map 中的 key 和  value 可以是任何引用类型的数据，会封装到HashMap$Node 对象中
        //3. Map 中的 key 不允许重复，原因和HashSet 一样，前面分析过源码.
        //4. Map 中的 value 可以重复
        //5. Map 的 key 可以为 null, value 也可以为bnull ，注意 key 为 null,
        //   只能有一个，value 为null ,可以多个
        //6. 常用 String 类作为 Map 的 key
        //7. key 和 value 之间存在单向一对一关系，即通过指定的 key 总能找到对应的 value
        Map map = new HashMap();
        map.put("no1", "狂徒张三");//k-v
        map.put("no2", "浪人李四");//k-v
        map.put("no1", "侠盗王五");//替换机制（通过底层代码e = p 实现，只是HashSet的value固定）
        map.put("no3", "侠盗王五");//k-v
        map.put(null, null); //
        map.put(null, "abc"); //null作为key只能出现一次
        map.put("no4", null); //k-v
        map.put("no5", null); //k-v
        map.put(1, "河东赵六");//key可以是不是String类
        map.put(new Object(), "江南孙七");//新建 Object 类作为 key 传入
        // 通过get 方法，传入 key ,会返回对应的value
        // 因为key不能重复, 故一定存在一对一, 只是value可能会出现相同而已
        System.out.println(map.get("no2"));//浪人李四

        System.out.println("map=" + map);
    }
}


/*
Map接口的特点
8）Map存放数据的key-value示意图，一对k-v是放在一个HashMap$Node中的，
在执行putVal() 新建对象时，执行了 Node<K,V> newNode(int hash, K key, V value, Node<K,V> next) 方法
由因为Node<K,V>实现了Entry接口，有些书上也说一对k-v就是一个Entry

然后 Entry<K,V>是一个数据类型， EntrySet是一个集合，Entry就会作为一个数据 放进 这个集合里面
在源码Entry<K,V>中， k 就是 set； v 就是 collection
值储存在Node节点里，Set存放的是指向key的地址, collection指向的是存放value的地址
因为map是没有实现迭代器接口的！！！所以才需要这种方式来进行遍历map的操作
 */

class MapSourceCode {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("no1", "韩顺平");//k-v
        map.put("no2", "张无忌");//k-v
        map.put(new MapInterfaceCar(), new MapInterfacePerson());//k-v

        //源码解读
        //1. k-v 最后是 HashMap$Node node = newNode(hash, key, value, null)
        //2. k-v 为了方便程序员的遍历，还会 创建 EntrySet 集合 ，该集合存放的元素的类型 Entry, 而一个Entry
        //   对象就有k,v EntrySet<Entry<K,V>> ，即： transient Set<Map.Entry<K,V>> entrySet;
        //   其实就是将Node<K,V>向上转型成Entry（数据类型）之后存入EntrySet（集合）
        //3. entrySet 中， 定义的类型是 Map.Entry ，但是实际上存放的还是 HashMap$Node
        //   这时因为 static class Node<K,V> implements Map.Entry<K,V>
        //4. 当把 HashMap$Node 对象 存放到 entrySet 就方便我们的遍历, 因为 Map.Entry 提供了重要方法
        //   K getKey(); V getValue();

        Set set = map.entrySet();//调用entrySet方法把map集合转换为set集合，set集合里存放的元素的类型为Entry（其实是Map.Entry），因为Node实现了Map.Entry接口，所以说运行类型是Node,

        System.out.println(set.getClass());// HashMap$EntrySet

        for (Object obj : set) {

            System.out.println(obj.getClass()); //HashMap$Node
            //为了从 HashMap$Node 取出k-v，需要做一个向下转型
            Map.Entry entry = (Map.Entry) obj;
            System.out.println(entry.getKey() + "-" + entry.getValue() );

            /*
            这里增强for循环可以用Entry来接受，因为Entryset里面存放的就是一个个entry，这里用Object来接收了所以要向下转型
            getkey，getvalue不是子类特有的方法，使用Entry接口方法时动态绑定到Node重写实现的方法，Node是Entry接口的实现类，接口类型指向实现类实例体现多态
            这里是不能用迭代器来进行遍历 之前的Collection集合里有iterator()所以可以通过迭代器来进行遍历 这里是讲到Map了与Collection并列存在 所以这里也有一个遍历方法

             */
        }
        System.out.println("=====通过setEntry迭代得到key和values=======");
        Set set1 = map.keySet();//key的编译类型
        System.out.println(set1.getClass());
        Collection values = map.values();//values的编译类型
        System.out.println(values.getClass());

    }
}
        /*

       Node（结点）相当于是自定义的一种数据类型，它实现了Map类里的一个静态内部Entry接口。而EntrySet是用来存放Entry的具体对象的（从名字就可以看出来），而因为Node实现了Entry，
       所以它可以存放进EntrySet，用以方便遍历。entrySet() 方法用把node（entry）给set化，，可以让Map类的对象调用，从而得到一个Set类对象就可以迭代了。
        Set类实现了Collection类，Collection类实现了Iterable类，就可用到Itrator迭代器
         */


        /*
        Node<k, v> (向上)转成 Entry<k, v>，而Entry<k, v>存放在EntrySet的set(放k)和collection(放v)中

        EntrySet理解成应该set集合存放Entry类型 entrySet方法是为了得到这个集合


        下断点可以发现，这里调用了HashSet的entryset方法，并且new了一个EntrySet对象，但是向上去找它的构造方法，发现是空的，set是一个没有内容空对象
        但是你直接打印出set却有值，这是因为调用了toString方法，EntrySet继承AbstractSet类，间接实现了AbstractCollection接口

        entrySet()获取entrySet,entrySet是一个Set<Entry<key,value>接口，里面存放的是Entry对象，Entry对象里面存放的是key的引用和value的引用

        Set里面存放的是Entry<K,V>,但是Entry<K,V>是一个接口类型，如果你要通过它来调用数据，必须要实例化，Node正好实现了这个接口，这是接口的多态
        实际上存放的是Node对象，Node是个默认修饰的内部类，没法取到；而Node实现了Entry接口，所以只能用Map.Entry来接收
        就是不想让你直接操作Map里的Node,给你几个集合接口，将你通常想用的方法写在接口里，用的时候调接口，而不是让你直接去操作Node

        set对象它的底层toString方法调用的iterator返回的方法是nextNode方法，这个方法的返回值类型就是Node类型
        set的编译类型是Set，运行类型是HshMap$EntrySet，增强for循环有一步骤是用了set.iterator方法，这方法在EntrySet中重写了，该方法最后返回的就是Node对象

        (1) 因为 EntrySet 内部实际不保存任何东西，而是通过迭代器返回 table 里面的结点
        (2) 而 table 中的结点的类型 Node 又实现了 Map.Entry 接口，所以运行类型是 Node，编译类型是 Entry
         */


        /*
        关于Entry的实际地址是怎么拿到的？
        首先增强for循环会调用Entryset重写的iterator方法，然后发现它会初始化next = t[index++]，而这里的t =table指向真正的哈希表
        它会从tab[0]索引一直往下找，直到发现不为空的元素然后，返回这个迭代器对象
        然后增强for底层调用next方法，而根据动态绑定entryset类中没有next方法向上找，
        找到EntryIterator中的next，发现调用nextNode，nextNode没有，向上找到HashIterator.nextNode
        观察代码发现，next的规律就是table从上往下找(从0往后)，发现有链表就遍历到链表尾，然后接着往下找，


        关于为什么需要EntrySet这个内部类，我的理解：
        Map和Collection是互不相关的两类集合，而Collection实现了Iterable接口，可以很方便使用增强for作为联通Collection和Map接口的桥梁，既能存储键值对，又能使用迭代器，岂不美哉

        Node继承了Entry所以可以用Entry来指向
        调用entrySet()方法后会创建Set型EntrySet，此时并没有指向任何地方
        当需要进行遍历是，EntrySet中的迭代器才会指向Map中的第一个结点
        然后不断调用迭代器中重写的next()方法不断的向下遍历
        也就是说不管在哪，只要能够重写next()逻辑，就能够利用迭代器进行遍历

        同理指向key和value的集合也是这样
        keySet -> 第一个结点的key，table[i].key，并重写next逻辑
        values -> 第一个结点的value，第一个非空table[i].value，并重写next逻辑

        我说一下自己的理解：比如一个学生(数据)有学号(k)，姓名(v)，他的前后桌 等基本信息（Node）。
        一般老师会把基本信息(Node)做成 电子名片(Entry)的“形式”(实际还是Node)。
        然后把电子名片(Entry)通过entrySet()方法，记录在学生教育系统(EntrySet)中，
        为什么要这么做？？1.因为学生教育系统(Set接口类型)有遍历电子名片的功能(迭代器)。
        2.电子名片(Entry”类型”)有getKey()和getValue()功能就可以方便查询学生的学号(k)和姓名(v)了。

        如果直接遍历table数组，那么在存在哈希冲突的情况下，需要遍历每个table中的链表或红黑树，这样效率会比较低下。
         */

class MapInterfaceCar {}

class MapInterfacePerson{}
/*
Map接口和常用方法
继承关系图
                  Map
                 ⇡   ↖
    -------------|    SortedMap（接口）
    |            |          ⇡
Hashtable     HashMap     TreeMap
    ↑             ↑
Properties  LinkedHashMap


Map接口常用方法

1) put:添加
2）remove：根据键删除映射关系
3）get：根据键获取值
4)size:获取元素个数
5）isEmpty:判断个数是否为0
6)clear:清除
7)containsKey:查找键是否存在
 */

class MapMethod {
    public static void main(String[] args) {
        //演示map接口常用方法

        Map map = new HashMap();
        map.put("邓超", new MapInterfacePerson());//OK
        map.put("邓超", "孙俪");//替换
        map.put("王宝强", "马蓉");//OK
        map.put("宋喆", "马蓉");//OK
        map.put("刘令博", null);//OK
        map.put(null, "刘亦菲");//OK
        map.put("鹿晗", "关晓彤");//OK

        System.out.println("map=" + map);

//        remove:根据键删除映射关系
        map.remove(null);
        System.out.println("map=" + map);
//        get：根据键获取值
        Object val = map.get("鹿晗");
        System.out.println("val=" + val);
//        size:获取元素个数
        System.out.println("k-v=" + map.size());
//        isEmpty:判断个数是否为0
        System.out.println(map.isEmpty());//F
//        clear:清除k-v
        //map.clear();
        System.out.println("map=" + map);
//        containsKey:查找键是否存在
        System.out.println("结果=" + map.containsKey("刘令博"));//T

    }
}
/*
Map接口遍历方法
>Map遍历的示意图图（比List，和Set复杂点，但是基本原理一样）

>Map遍历方式案例演示
1）containsKey:查找键是否存在
2）keySet：获取所有的键
3）entrySet:获取所有关系K-V
4）values:获取所有的值
 */

class MapTraverse {
    public static void main(String[] args) {

        Map map = new HashMap();
        map.put("邓超", "孙俪");
        map.put("王宝强", "马蓉");
        map.put("宋喆", "马蓉");
        map.put("刘令博", null);
        map.put(null, "刘亦菲");
        map.put("鹿晗", "关晓彤");

        //第一组: 先取出 所有的Key , 通过Key 取出对应的Value
        Set keyset = map.keySet();
        //(1) 增强for
        System.out.println("-----第一种方式-------");
        for (Object key : keyset) {
            System.out.println(key + "-" + map.get(key));
        }
        //(2) 迭代器
        System.out.println("----第二种方式--------");
        Iterator iterator1 = keyset.iterator();
        while (iterator1.hasNext()) {
            Object key =  iterator1.next();
            System.out.println(key + "-" + map.get(key));
        }

        //第二组: 把所有的values取出
        Collection values = map.values();
        //这里可以使用所有的Collections使用的遍历方法，因为没有下标索引，无法使用普通for
        //(1) 增强for
        System.out.println("---取出所有的value 增强for----");
        for (Object value : values) {
            System.out.println(value);
        }
        //(2) 迭代器
        System.out.println("---取出所有的value 迭代器----");
        Iterator iterator2 = values.iterator();
        while (iterator2.hasNext()) {
            Object value =  iterator2.next();
            System.out.println(value);
        }

        //第三组: 通过EntrySet 来获取 k-v
        Set entrySet = map.entrySet();// EntrySet<Map.Entry<K,V>>
        //(1) 增强for
        System.out.println("----使用EntrySet 的 for增强(第3种)----");
        for (Object entry : entrySet) {
            Map.Entry m = (Map.Entry) entry;//将编译类型由 Object 类转成 Map.Entry，以便调用 getKey 和 getValue 等方法
            System.out.println(m.getKey() + "-" + m.getValue());
        }
        //(2) 迭代器
        System.out.println("----使用EntrySet 的 迭代器(第4种)----");
        Iterator iterator3 = entrySet.iterator();
        while (iterator3.hasNext()) {
            Object entry =  iterator3.next();
            //System.out.println(next.getClass());//HashMap$Node -实现-> Map.Entry (getKey,getValue)
            //向下转型 Map.Entry
            Map.Entry m = (Map.Entry) entry;
            System.out.println(m.getKey() + "-" + m.getValue());
        }
    }
}
//说明：Entry是接口Map的内部接口，接口中的接口默认 public static，所以Map.Entry可以直接访问；Node是静态内部类，是默认访问修饰符，包外访问不了
//当前代码中，行数最少的是keySet()+增强for循环；Map接口里的forEach方法是最简单的（用lambda表达式）

/*
Map接口实现类-HashMap
·HashMap小结
1）Map接口的常用实现类：HashMap、Hashtable和Properties。
2）HashMap是Map接口使用频率最高的实现类。
3】HashMap是以key-val对的方式来存储数据(HashMap$Node类型)
4）key不能重复，但是值可以重复，允许使用null键和null值
5）如果添加相同的key，则会覆盖原来的key-val，等同于修改.（key不会替换，val会替换）
6）与HashSet一样，不保证映射的顺序，因为底层是以hash表的方式来存储的(jdk8 的 hashMap 底层为数组+链表+红黑树)
7）HashMap没有实现同步，因此是线程不安全的（方法没有synchronize修饰的同步互斥）
 */