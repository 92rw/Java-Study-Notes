# Map接口

| 特性     | TreeMap        | HashMap        | LinkedHashMap    |
| -------- | -------------- | -------------- | ---------------- |
| 排序     | 支持           | 不支持         | 不支持           |
| 插入顺序 | 不保证         | 不保证         | 保证             |
| 查找效率 | O(log n)       | O(1)           | O(1)             |
| 空间占用 | 通常较大       | 通常较小       | 通常较大         |
| 适用场景 | 需要排序的场景 | 无需排序的场景 | 需要保持插入顺序 |

添加元素：put()

```java
V put(K key, V value);
```

删除元素

```java
V remove(Object key);

default boolean remove(Object key, Object value) {
    Object curValue = get(key);
    if (!Objects.equals(curValue, value) ||
        (curValue == null && !containsKey(key))) {
        return false;
    }
    remove(key);
    return true;
}
```

修改元素：调用put方法，因为键唯一所以直接替换原来键值对

遍历方法：[Java中的KeySet() vs values() vs entrySet()方法 (techiedelight.com)](https://www.techiedelight.com/zh/keyset-values-entryset-method-java/)



```java
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import lombok.Data;
/**
 * 使用HashMap添加3个员工对象，要求
 * 键：员工id
 * 值：员工对象
 *
 * 并遍历显示工资>18000的员工(遍历方式最少两种)
 * 员工类：姓名、工资、员工id
 */
public class MapExercise {
    public static void main(String[] args) {

        Map hashMap = new HashMap();
        //添加对象
        hashMap.put(1, new Employee("jack", 300000, 1));
        hashMap.put(2, new Employee("tom", 21000, 2));
        hashMap.put(3, new Employee("milan", 12000, 3));


        //遍历2种方式
        //并遍历显示工资>18000的员工(遍历方式最少两种)
        //1. 使用keySet  -> 增强for
        Set keySet = hashMap.keySet();
        System.out.println("====第一种遍历方式====");
        for (Object key : keySet) {
            //如果担心获取到的value类型不是 Employee，可以在此处加入 instance 判断
            Employee emp = (Employee) hashMap.get(key);
            if(emp.getSal() >18000) {
                System.out.println(emp);
            }
        }

        //2. 使用EntrySet -> 迭代器
        //   从编译类型看是Object->Entry是向下转型。从运行类型看，是从Node->Entry，是向上转型
        //   慢慢品，越品越有味道.
        Set entrySet = hashMap.entrySet();
        System.out.println("======迭代器======");
        Iterator iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry =  (Map.Entry)iterator.next();
            //通过entry 取得key 和 value，这里的entry运行类型是HashMap$Node
            //泛型架构却不使用泛型就是这样，关键数据的编译类型都被默认Object，所以要频繁向下转型
            Employee emp = (Employee) entry.getValue();
            if(emp.getSal() > 18000) {
                System.out.println(emp);
            }
        }

    }
}

@Data
class Employee {
    private String name;
    private double sal;
    private int id;
}
```

