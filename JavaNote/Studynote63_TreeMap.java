import java.util.Comparator;
import java.util.TreeMap;

public class Studynote63_TreeMap {
    public static void main(String[] args) {

        //使用默认的构造器，创建TreeMap, 是无序的(也没有排序)
        /*
            按照传入的 k(String) 的大小进行排序
         */
        //TreeMap treeMap = new TreeMap();
        TreeMap treeMap = new TreeMap(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                /*
                假设你传入的是String类型的数据，那它就会默认调用String类的compareTo方法
                而String的compareTo方法要求你传入的参数也是String类型的数据
                所以如果你传入的不是同类型的数据才会抛出classCastException
                 */

                //按照传入的 k(String) 的大小进行排序
                //按照K(String) 的长度大小排序
                //return ((String) o2).compareTo((String) o1);
                return ((String) o2).length() - ((String) o1).length();
            }
        });
        treeMap.put("jackie", "杰克");
        treeMap.put("tom", "汤姆");
        treeMap.put("kristina", "克里斯蒂娜");
        treeMap.put("smith", "斯密斯");
        treeMap.put("Mayuri", "真由里");//key无法加入，value替换

        System.out.println("treemap=" + treeMap);

        /*

            老韩解读源码：
            1. 构造器. 把传入的实现了 Comparator接口的匿名内部类(对象)，传给给TreeMap的comparator
             public TreeMap(Comparator<? super K> comparator) {
                this.comparator = comparator;
            }
            2. 调用put方法
            2.1 第一次添加, 把k-v 封装到 Entry对象，放入root
            Entry<K,V> t = root;
            if (t == null) {
                compare(key, key); // type (and possibly null) check
                                   //不接收 compare 方法返回的结果，传入两个相同的 key 参数，作用是检测其是否为空
                                   //这里设计者的意思是默认不能为null，但留了条路给使用者，只需要在比较器里面设置一下null的情况，就可以添加null了
                root = new Entry<>(key, value, null);
                size = 1;
                modCount++;
                return null;
            }

            遍历方式使用的是二叉树的中序遍历，entrySet使用的是二叉树的中序遍历，先左子树，根节点，右子树。
            红黑树就是二叉排序树的优化的优化，左边放比根节点小的，右边放比根节点大的，递归定义下去
            插入的时候通过比较方法判断应该插入父节点的左子树还是右子树  然后输出的时候通过中序遍历就能顺序输出了

            2.2 以后添加
            Comparator<? super K> cpr = comparator;
            if (cpr != null) {
                do { //遍历所有的key , 给当前key找到适当位置
                    parent = t;
                    cmp = cpr.compare(key, t.key);//动态绑定到我们的匿名内部类的compare
                    if (cmp < 0)
                        t = t.left;
                    else if (cmp > 0)
                        t = t.right;
                    else  //如果遍历过程中，发现准备添加Key 和当前已有的Key 相等，就不添加
                        return t.setValue(value);
                } while (t != null);
            }
         */
        /*

        put方法if/else走两种分支，一种是Comparable接口的compareTo方法，一种Comparator接口的compare方法
        然后分析一下作者为什么这样设计，首先TreeMap中的元素肯定要支持排序，
        假如不实现Compareable那就必须添加，那必须传入比较器，简而言之，得二选一。因此匿名内部类的两种适用情形：
        1、元素的key支持Comparable但自然排序不满足调用者要求，需要自定义；
        2、自定义的类没有实现comparable接口，不设置比较器会抛出 classCastException
         */

    }
}
