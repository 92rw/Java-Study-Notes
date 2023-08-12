import java.util.Hashtable;
import java.util.Properties;

/*
HashTable的基本介绍
继承关系图
                       Map
                       ⇡   ↖
 Dictionary  ----------|    SortedMap（接口）
     ↑      |          |          ⇡
    Hashtable     HashMap     TreeMap
        ↑             ↑
    Properties  LinkedHashMap

1）存放的元素是键值对：即K-V
2）hashtable的键和值都不能为null，否则会抛出NullPointerException
3）hashTable使用方法基本上和HashMap一样
4）hashTable是线程安全的（synchronize），hashMap是线程不安全的

Hashtable采用取模的方式来计算数组下标，同时数组的长度尽量为素数或者奇数，这样的目的是为了减少Hash碰撞，计算出来的数组下标更加分散，让元素均匀的分布在数组各个位置。

Hashtable 和 HashMap对比
            版本    线程安全（同步）    效率      允许null键null值
HashMap     1.2       不安全           高             可以
Hashtable   1.0        安全           较低           不可以


 */
public class Studynote61_Hashtable {
    public static void main(String[] args) {
        Hashtable table = new Hashtable();//ok
        table.put("john", 100); //ok
        //table.put(null, 100); //异常 NullPointerException
        //table.put("john", null);//异常 NullPointerException
        table.put("lucy", 100);//ok
        table.put("lic", 100);//ok
        table.put("lic", 88);//替换
        table.put("hello1", 1);
        table.put("hello2", 1);
        table.put("hello3", 1);
        table.put("hello4", 1);
        table.put("hello5", 1);
        table.put("hello6", 1);
        System.out.println(table);

        //简单说明一下Hashtable的底层
        //1. 底层有数组 Hashtable$Entry[] 初始化大小为 11
                //Hashtable 有个 private 的内部类Entry 实现了Map.Entry(Map里的Entry接口)，所以Node和HashTable$Entry更像一种并列关系
                //源码走的是protected Entry(int hash, K key, V value, Entry<K,V> next)，与Node无关
        //2. 临界值 threshold 8 = 11 * 0.75
        //3. 扩容: 按照自己的扩容机制来进行即可.
        //4. 执行 方法 addEntry(hash, key, value, index); 添加K-V 封装到Entry
        //5. 当 if (count >= threshold) 满足时，就进行扩容
        //5. 按照 int newCapacity = (oldCapacity << 1) + 1; 的大小扩容.

    }
}

/*
Map接口实现类-Properties
基本介绍
1.Properties类继承自Hashtable类并且实现了Map接口，也是使用一种键值对的形式来保存数据
2.使用特点和Hashtable类似
3.Properties还可以用于从xxx.properties文件中，加载数据到Properties类对象并进行读取和修改
说明：工作后xxx.properties文件通常作为配置文件，这个知识点在IO流举例，可以参考文章
 https://www.cnblogs.com/xudong-bupt/p/3758136.html
 */

class Properties_ {
    public static void main(String[] args) {

        //1. Properties 继承  Hashtable
        //2. 可以通过 k-v 存放数据，当然key 和 value 不能为 null
        //增加
        Properties properties = new Properties();
        //properties.put(null, "abc");//抛出 空指针异常
        //properties.put("abc", null); //抛出 空指针异常
        properties.put("john", 100);//k-v
        properties.put("lucy", 100);
        properties.put("lic", 100);
        properties.put("lic", 88);//如果有相同的key ， value被替换

        System.out.println("properties=" + properties);

        //通过k 获取对应值
        System.out.println(properties.get("lic"));//88

        //删除
        //方法名都一样的，所以说方法的命名很重要呢，方便记忆，且利用动态绑定机制又可以很灵活
        properties.remove("lic");
        System.out.println("properties=" + properties);

        //修改
        properties.put("john", "约翰");
        System.out.println("properties=" + properties);

    }
}