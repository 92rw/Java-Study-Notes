import java.util.Comparator;
import java.util.TreeSet;

public class Studynote62_TreeSet {
    public static void main(String[] args) {

        //用tree类型的集合大多数都是想自定义排序，无参构造器即使默认排序可能也不是你想要的，还是得用比较器，就当作无参是无序的行了
        //1. 当我们使用无参构造器，创建TreeSet时，仍然是无序的
        //2. 老师希望添加的元素，按照字符串大小来排序
        //3. 使用TreeSet 提供的一个构造器，可以传入一个比较器(匿名内部类)
        //   并指定排序规则
        //4. 简单看看源码
        /*
        因为 TreeSet 和 TreeMap 在自身没有比较器的时候，存入任何对象都需要将该对象强转为 Comparable对象，
        然后通过这个 Comparable对象 调用 compareTO() 比较，而如果自定义一个类但没有实现这个接口，会抛出异常的。
        (没有按照插入顺序)，这里是无参构造器调用String的compareTo方法，按照ASCII码进行自动排序：英文符号>数字>大写字母>小写字母>中文>中文符号;
         */
        /*
        1. 构造器把传入的比较器对象，赋给了 TreeSet的底层的 TreeMap的属性this.comparator

         public TreeMap(Comparator<? super K> comparator) {
                this.comparator = comparator;
            }
         2. 在 调用 treeSet.add("tom"), 在底层会执行到

             if (cpr != null) {//cpr 就是我们的匿名内部类(对象)，说明如果传入的对象所在类不支持Comparable接口，则无法添加此对象，抛出异常
                do {
                    parent = t;
                    //动态绑定到我们的匿名内部类(对象)compare
                    cmp = cpr.compare(key, t.key);
                    if (cmp < 0)
                        t = t.left;
                    else if (cmp > 0)
                        t = t.right;
                    else //如果相等，即返回0,这个Key就没有加入
                        return t.setValue(value);
                } while (t != null);
            }
         */
            //使用默认构造器，即从小到大排列
//        TreeSet treeSet = new TreeSet();
        TreeSet treeSet = new TreeSet(new Comparator() {//添加元素是根据匿名内部类重写的比较器定义来判断能否加入成功的
            @Override
            public int compare(Object o1, Object o2) {


                //下面 调用String的 compareTo方法进行从大到小
                //return ((String) o2).compareTo((String) o1);

                //如果要求加入的元素，按照长度大小排序
                //return ((String) o1).length() - ((String) o2).length();//这里的相同长度的字符串无法添加，因为只比较存储长度，跟字符串本身的内容无关了。
                //return ((String) o1).length() >= ((String) o2).length() ? -1 : 1;//这样能排序且能添加，但无法剔除重复项
                String s1 = (String) o1;
                String s2 = (String) o2;
                int result = s2.length() - s1.length();
                return result == 0 ? s2.compareTo(s1) : result;//这样既比较长度，又比较内容
            }
        });
        //添加数据.
        treeSet.add("jack");
        treeSet.add("tom");//3
        treeSet.add("sp");
        treeSet.add("a");
        treeSet.add("abc");//3


        System.out.println("treeSet=" + treeSet);
        /*
        比较HashSet和TreeSet如何实现去重
        （1）HashSet的去重机制：hashCode()+equals()，底层先通过存入对象，进行运算得到一个hash值，hash值与数组长度取模得到就是索引位置，
        如果发现table索引所在的位置，没有数据，就直接存放。如果有数据，就进行equals比较[遍历比较]，如果比较后，不相同，就加入，否则就不加入，

        (2）TreeSet的去重机制：如果你传入了一个Comparator匿名对象，就使用实现的compare去重，如果方法返回0,就认为是相同的元素/数据，
        就不添加，如果你没有传入一个Comparator匿名对象，则以你添的对象实现的compareable接口的compareTo去重
         */
    }
}

