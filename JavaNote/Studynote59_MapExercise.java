import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
/**
 * 使用HashMap添加3个员工对象，要求
 * 键：员工id
 * 值：员工对象
 *
 * 并遍历显示工资>18000的员工(遍历方式最少两种)
 * 员工类：姓名、工资、员工id
 */
public class Studynote59_MapExercise {
    public static void main(String[] args) {

        Map hashMap = new HashMap();
        //添加对象
        hashMap.put(1, new HashMapEmployee("jack", 300000, 1));
        hashMap.put(2, new HashMapEmployee("tom", 21000, 2));
        hashMap.put(3, new HashMapEmployee("milan", 12000, 3));


        //遍历2种方式
        //并遍历显示工资>18000的员工(遍历方式最少两种)
        //1. 使用keySet  -> 增强for
        Set keySet = hashMap.keySet();
        System.out.println("====第一种遍历方式====");
        for (Object key : keySet) {
            //如果担心获取到的value类型不是 HashMapEmployee，可以在此处加入 instance 判断
            HashMapEmployee emp = (HashMapEmployee) hashMap.get(key);
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
            HashMapEmployee emp = (HashMapEmployee) entry.getValue();
            if(emp.getSal() > 18000) {
                System.out.println(emp);
            }
        }

    }
}

class HashMapEmployee {
    private String name;
    private double sal;
    private int id;

    public HashMapEmployee(String name, double sal, int id) {
        this.name = name;
        this.sal = sal;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", sal=" + sal +
                ", id=" + id +
                '}';
    }
}
