import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Studynote50_List {
}
/*
List接口基本介绍：Collection接口的子接口
1）List集合类中元素有序（即添加顺序和取出顺序一致）、且可重复
2）List集合中的每个元素都有其对应的顺序素引，即支持素引。
3）List容器中的元素都对应一个整数型的序号记载其在容器中的位置，可以根据序号存取容器中的元素
4）主要的实现类包括 ArrayList，Vector，LinkedList

常用方法
 * 1.add:添加单个元素
 * 2.remove:删除指定元素
 * 3.contains:查找元素是否存在
 * 4.size:获取元素个数
 * 5.isEmpty:判断是否为空
 * 6.clear:清空
 * 7.addAll:添加多个元素
 * 8.containsAll:查找多个元素是否都存在
 * 9.removeAll：删除多个元素               arrayList.removeAll(arrayList); //相当于清空
 */
class List_ {
    public static void main(String[] args) {
        //1. List集合类中元素有序(即添加顺序和取出顺序一致)、且可重复
        List list = new ArrayList();
        list.add("jack");
        list.add("tom");
        list.add("mary");
        list.add("smith");
        list.add("tom");//可以重复
        System.out.println("list=" + list);
        //2. List集合中的每个元素都有其对应的顺序索引，即支持索引
        //   本质是数组，索引是从0开始的
        System.out.println(list.get(3));//hsp
    }
}

class ListMethod {
    public static void main(String[] args) {
        //因为要用父类的方法就需要创建父类对象,但是接口不能实例化,所以只能调用子类对象（子类再向上转型）来使用父类的方法
        List list = new ArrayList();
        list.add("狐狸精");
        list.add("老鼠精");
        //void add(int index, Object ele):在index位置插入ele元素
        //在index = 1的位置插入一个对象
        list.add(1, "蝎子精");
        System.out.println("list=" + list);

        //boolean addAll(int index, Collection eles):从index位置开始将eles中的所有元素添加进来
        List list2 = new ArrayList();
        list2.add("蜘蛛精");
        list2.add("白骨精");
        list.addAll(1, list2);//原有元素向后移动
        System.out.println("list=" + list);

        //Object get(int index):获取指定index位置的元素
        //注意这里返回的是 Object 类，在使用时需要向下转型

        //int indexOf(Object obj):返回obj在集合中首次出现的位置
        System.out.println(list.indexOf("白骨精"));//2

        //int lastIndexOf(Object obj):返回obj在当前集合中末次出现的位置
        list.add("白骨精");
        System.out.println("list=" + list);
        System.out.println(list.lastIndexOf("白骨精"));

        //Object remove(int index):移除指定index位置的元素，并返回此元素
        list.remove(0);
        System.out.println("list=" + list);

        //Object set(int index, Object ele):设置指定index位置的元素为ele , 相当于是替换.
        //如果数值大于索引值，会出现 IndexOutOfBoundsException 异常
        list.set(1, "女儿国王");
        System.out.println("list=" + list);

        //List subList(int fromIndex, int toIndex):返回从fromIndex到toIndex位置的子集合
        // 注意返回的子集合 fromIndex <= subList < toIndex（前闭后开区间）
        List returnlist = list.subList(0, 2);
        System.out.println("returnlist=" + returnlist);

    }
}

/*
添加10个以上的元素(比如String "hello" )，在2号位插入一个元素"韩顺平教育"，
获得第5个元素，删除第6个元素，修改第7个元素，在使用迭代器遍历集合，
要求:使用List的实现类ArrayList完成。
 */
class ListExercise1 {
    public static void main(String[] args) {

        List list = new ArrayList();
        for (int i = 0; i < 12; i++) {
            list.add("hello" + i);
        }
        System.out.println("list=" + list);

        //在2号位插入一个元素"韩顺平教育"
        list.add(1, "韩顺平教育");
        System.out.println("list=" + list);

        //获得第5个元素
        System.out.println("第五个元素=" + list.get(4));

        //删除第6个元素
        list.remove(5);
        System.out.println("list=" + list);

        //修改第7个元素
        list.set(6, "三国演义");
        System.out.println("list=" + list);

        //在使用迭代器遍历集合
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object obj =  iterator.next();
            System.out.println("obj=" + obj);

        }
    }
}

class ListTraverse{
    //演示遍历 List 接口的三种方式
    //写游戏的时候还是得用普通for，因为遍历时元素被删会出异常
    public static void main(String[] args) {
        List list = new ArrayList();
        //List list = new Vector();
        //List list = new LinkedList();
        list.add("狐狸精");
        list.add("老鼠精");
        list.add("蜘蛛精");
        list.add("白骨精");
        
        //方式1：使用iterator（快捷键itit）
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object next =  iterator.next();
            System.out.println("iterator = " + next);
        }

        //方式2：增强for循环（快捷键iter）
        for (Object o : list) {
            System.out.println("o = " + o);
        }

        //方式3：使用普通for
        for (int i = 0; i < list.size(); i++) {
            System.out.println("对象 = " + list.get(i));
        }
    }
}
class ListBook {
    private String name;
    private String author;
    private double price;

    public ListBook(String name, String author, double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "名称：" + name + "\t\t价格:" + price + "\t\t作者：" + author;
    }
}
/*
练习2
使用Lis的实现类添加三本图书，并遍历，打印如下效果
名称：XX     价格：xx       作者： xX
名称：XX     价格：xx       作者： xX
名称：XX     价格：xx       作者： xX

要求
1）按价格排序，从低到高（使用冒泡法）
2）要求使用ArrayList、LinkedList和Vector三种集合实现
 */

class ListExercise02 {

    public static void main(String[] args) {

        //List list = new ArrayList();
        List list = new LinkedList();
        //List list = new Vector();
        list.add(new ListBook("红楼梦", "曹雪芹", 100));
        list.add(new ListBook("西游记", "吴承恩", 10));
        list.add(new ListBook("水浒传", "施耐庵", 19));
        list.add(new ListBook("三国", "罗贯中", 80));
        //list.add(new Book("西游记", "吴承恩", 10));

        for (Object o : list) {
            System.out.println(o);
        }

        //冒泡排序
        sort(list);

        System.out.println("==排序后==");

        for (Object o : list) {
            System.out.println(o);
        }

    }

    //静态方法：在主方法调用得用static修饰,因为main是static,同在一个类里静态方法才能调用
    //价格要求是从小到大
    public static void sort(List list) {

        int listSize = list.size();
        for (int i = 0; i < listSize - 1; i++) {
            for (int j = 0; j < listSize - 1 - i; j++) {
                //取出对象Book
                //List 取出的元素都是Object类型，而getPrice是ListBook特有方法，只能向下转型才能调用
                ListBook book1 = (ListBook) list.get(j);
                ListBook book2 = (ListBook) list.get(j + 1);
                if (book1.getPrice() > book2.getPrice()) {//交换
                    //不需要建立 temp 对象也能完成交换
                    //get方法并没有返回新对象，返回的是指向该位置对象的引用，但由于创建了新的Listbook
                    // 类型的引用指向该对象，所有相当于已经有了中间变量
                    list.set(j, book2);
                    list.set(j + 1, book1);
                }
            }
        }

    }
}