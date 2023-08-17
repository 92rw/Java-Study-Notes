import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*

Collections工具类
Collections工具类介绍
1)Collections是一个操作Set、List和Map等集合的工具类
2）Collections中提供了一系列静态的方法对集合元素进行排序、查询和修改等操作

排序操作：（均为static方法）
1)reverse(List)：反转List中元素的顺序
2）shuffle（List)：对List集合元素进行随机排序
3)sort(List)：根据元素的自然顺序对指定List集合元素按升序排序
4)sort(List，Comparator)：根据指定的 Comparator产生的顺序对 List集合元素进行
排序
5)swap(List，int，int)：将指定 list 集合中的i处元素和j处元素进行交换

查找、替换
1）Object max(Collection)：根据元素的自然顺序，返回给定集合中的最大元素
2）Object max(Collection, Comparator): 根据 Comparator 指定的顺序
返回给定集合中的最大元素
3）Object min(Collection)
4）Object min(Collection, Comparator)
5）int frequency(Collection，Object)：返回指定集合中指定元素的出现次数
6）void copy(List dest,List src)：将src中的内容复制到dest中
7）boolean replaceAll(List list, Object oldVal, Object newVal): 使用
新值替换List对象的所有旧值
 */


public class Studynote64_Collections {
    public static void main(String[] args) {

        //创建ArrayList 集合，用于测试.
        List list = new ArrayList();
        list.add("tom");
        list.add("smith");
        list.add("king");
        list.add("milan");
        list.add("tom");


        //reverse(List)：反转 List 中元素的顺序
        Collections.reverse(list);
        System.out.println("list=" + list);

        //shuffle(List)：对 List 集合元素进行随机排序
        //shuffle这个单词就是洗牌的意思
//        for (int i = 0; i < 5; i++) {
//            Collections.shuffle(list);
//            System.out.println("list=" + list);
//        }

        //sort(List)：根据元素的自然顺序对指定 List 集合元素按升序排序
        Collections.sort(list);
        System.out.println("自然排序后");
        System.out.println("list=" + list);

        //sort(List，Comparator)：根据指定的 Comparator 产生的顺序对 List 集合元素进行排序
        //我们希望按照 字符串的长度大小排序
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                //可以加入校验代码，用 instanceof 判断是不是字符串，或是抛出异常
                return ((String) o2).length() - ((String) o1).length();
            }
        });
        System.out.println("字符串长度大小排序=" + list);
        //swap(List，int， int)：将指定 list 集合中的 i 处元素和 j 处元素进行交换

        //如果此处索引越界，会抛出IndexOutOfBoundsException
        Collections.swap(list, 0, 1);
        System.out.println("交换后的情况");
        System.out.println("list=" + list);

        //Object max(Collection)：根据元素的自然顺序，返回给定集合中的最大元素
        System.out.println("自然顺序最大元素=" + Collections.max(list));
        //Object max(Collection，Comparator)：根据 Comparator 指定的顺序，返回给定集合中的最大元素
        //比如，我们要返回长度最大的元素
        Object maxObject = Collections.max(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((String)o1).length() - ((String)o2).length();
            }
        });
        System.out.println("长度最大的元素=" + maxObject);


        //Object min(Collection)
        //Object min(Collection，Comparator)
        //上面的两个方法，参考max即可

        //int frequency(Collection，Object)：返回指定集合中指定元素的出现次数
        System.out.println("tom出现的次数=" + Collections.frequency(list, "tom"));

        //void copy(List dest,List src)：将src中的内容复制到dest中
        //注意目标集合的元素数不能少于原集合
        ArrayList dest = new ArrayList();
        //为了完成一个完整拷贝，我们需要先给dest 赋值，大小和list.size()一样，否则会抛出IndexOutOfBoundsException
        for(int i = 0; i < list.size(); i++) {  //size是集合元素个数，不是底层数组的长度
            dest.add("");//不能使用list的长度来定义dest的空间，这个方法里面比较的是size，
                        // 不加东西进去集合size是不会变的，空间是你定义的大小，但是size等于0
        }
        //拷贝
        Collections.copy(dest, list);//看源码可以知道复制的时候从第一个元素开始依次覆盖dest的元素
        System.out.println("dest=" + dest);//肯定不能直接定义dest的容量啊 定义的又不是size的大小！而copy比较的是size的大小来判断是否越界

        //boolean replaceAll(List list，Object oldVal，Object newVal)：使用新值替换 List 对象的所有旧值
        //如果list中，有tom 就替换成 汤姆
        Collections.replaceAll(list, "tom", "汤姆");
        System.out.println("list替换后=" + list);


    }
}
