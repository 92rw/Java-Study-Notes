import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Studynote44_Arrays {
}
/*
·Arrays类常见方法应用案例
Arrays里面包含了一系列静态方法，用于管理或操作数组（比如排序和搜索）
1）toString返回数组的字符串形式
2）sort排序（自然排序和定制排序）
3）binarySearch通过二分搜索法进行查找，要求必须排好序
4）copyOf数组元素的复制
5）fill数组元素的填充
6）equals比较两个数组元素内容是否完全一致
7）asList将一组值，转换成list
 */

class ArraysMethod1{
    public static void main(String[] args) {
        Integer[] ints = {1, 20, 90};
        //取得数组元素的一般方法：遍历数组，缺点：步骤较繁琐
//        for (int i = 0; i < ints.length; i++) {
//            System.out.println(i);
//        }
        //此处直接使用Arrays.toString() 方法，输入的形参为Object类，生成一段开头"["，结尾"]"的字符串
        System.out.println(Arrays.toString(ints));//[1, 20, 90]

        //2）sort排序（自然排序和定制排序）
        Integer arr[]= {1, -1, 7, 0, 89};
        System.out.println("=====数组排序前=====");
        System.out.println(Arrays.toString(arr));//直接输出arr只能得到hashcode值
        //排序时可以使用冒泡排序，也可以直接使用Arrays提供的sort()方法
        //因为数组是引用类型，排序后会影响到实参
        System.out.println("=====数组默认排序=====");
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        //利用接口 Comparator 重写方法实现定制排序
        //传入两个参数：①待排序数组②实现了 compare() 方法的的匿名内部类
        System.out.println("=====数组定制排序=====");
        Arrays.sort(arr, new Comparator() {
            @Override//查看底层源码，此处调用的是 TimSort 类 binarySort 方法，基于归并和插入，比较次数和空间需求远小于其他排序算法
            //底层代码在 binarySort 方法通过匿名内部类排序。正数，>>>逻辑右移1位= >>算术右移1位=十进制除2
            //可简记：o1是数组的中位数，o2 是数组的第一位数，而compare的底层采用二叉树排序
            //当输出的值小于 0 ==> compare底层采用从小到大的排序
            public int compare(Object o1, Object o2) {//泛型的默认值是Object
                Integer i1 = (Integer) o1;
                Integer i2 = (Integer) o2;
                return i2 - i1;//return数值大于0，从大到小排，return数值小于0，从小到大排。
            }
        });
        System.out.println(Arrays.toString(arr));

        //3）binarySearch通过二分搜索法进行查找，要求数组只能是升序
        //如果找不到该元素，则返回 -（本应该在数组的索引+1）
        //可以用二分查找的返回值来判断插入数字在一串数字中排第几个位置
        int index=Arrays.binarySearch(arr,3);
        System.out.println("index = " + index);
    }

}

class ArrayBubbleSort{
    //用于演示 Arrays 类 public static <T> void sort(T[] a, Comparator<? super T> c) 方法的含义
    public static void main(String[] args) {
        int[] arr = {1, -1, 8, 0, 20};
        System.out.println("==排序前的数组为==");
        System.out.println(Arrays.toString(arr));
        bubble2(arr, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Integer i1 = (Integer) o1;
                Integer i2 = (Integer) o2;
                return i1 - i2;
                //i1-i2结果大于0则说明前一位的值大于后一位，则交换位置，是正向排列，
                // 同理如果返回值是小于0则不执行交换代码
            }
        });
        System.out.println("==排序后的数组为==");
        System.out.println(Arrays.toString(arr));
    }
    //普通冒泡排序的方法，直接在主方法调用bubble1(arr);可实现
    public static void bubble1(int[] ints){
        int temp = 0;
        for (int i = 0; i < ints.length - 1; i++) {
            for (int j = 0; j < ints.length - i - 1; j++) {
                //按从小到大排序
                if(ints[j] > ints[j + 1]){
                    temp = ints[j];
                    ints[j] = ints[j + 1];
                    ints[j + 1] = temp;
                }
            }
        }
    }
    //结合冒泡的定制排序
    //compare给出了两种选择，即arr[j] - arr[j+1]的差与零比较,
    // 还是arr[j+1] -arr[j]的差与零比较，分别对应升序排序和降序排序，由你重写compare 时决定
    public static void bubble2(int[] arr, Comparator c){
        int temp = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                //排序条件由 compare 方法返回的数值决定
                if(c.compare(arr[j], arr[j + 1]) > 0){
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}

class ArraysMethod2{
    public static void main(String[] args) {
        Integer[] arr = {1, -1, 8, 0, 20};

        //4）copyOf(原数组, 拷贝的元素数量) 数组元素的复制
        //该方法底层使用的是 System.arraycopy()
        //按照原来存储的数据填充默认值，实现数组拷贝
        //如果直接 newArr = arr的话哈素值一样，代表只是指向而不是复制
        //如果拷贝长度 >arr.length，在新数组后面增加 null
        //如果拷贝长度<0 就抛出异常NegativeArraySizeException
        Integer[] newArr = Arrays.copyOf(arr,arr.length + 1);
        System.out.println(Arrays.toString(newArr));

        //5）fill数组元素的填充
        //用于替换数组原来的元素，可以用于初始化数组
        Integer[] num =new Integer[]{9,3,2};
        Arrays.fill(num,99);
        System.out.println(Arrays.toString(num));

        //6）equals比较两个数组元素内容是否完全一致
        Integer[] arr2 = {1, -1, 8, 0, 20};
        boolean equals = Arrays.equals(arr,arr2);
        System.out.println(equals);

        //7)asList将一组数值，转换成List集合再返回
        //返回的 asList 编译类型为 List(接口)，运行类型 java.util.Arrays$ArrayList（静态内部类）
        List<Integer> asList= Arrays.asList(2,3,4,5,6,1);
        System.out.println("asList= " + asList);
        System.out.println("asList的运行类型= " + asList.getClass());
    }
}

/*
案例：自定义ArraysBook类，里面包含name和price，按price排序（从大到小）。创建4本书并调用代码比较信息
 */

class ArraysBook{
    private String name;
    private double price;

    public ArraysBook() {}//由于排序需要创建临时变量，因此使用无参构造器

    public ArraysBook(String name, double price) {
        this.name = name;
        this.price = price;
    }
    //有了Arrays.sort() 方法的匿名内部类之后，这段方法可以不使用
    public static ArraysBook[] sortPrice(ArraysBook[] books,Comparator c){
        ArraysBook temp = new ArraysBook();
        for (int i = 0; i < books.length - 1; i++) {
            for (int j = 0; j < books.length -1 -i; j++) {
                if(c.compare(books[j].price,books[j+1].price) > 0){
                    temp = books[j];
                    books[j] = books[j+1];
                    books[j+1] =temp;
                }
            }
        }
        return books;
    }

    @Override
    public String toString() {
        return "ArraysBook{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
class ArraysExercise{
    public static void main(String[] args) {
        ArraysBook[] books = new ArraysBook[4];
        books[0] = new ArraysBook("西厢记",100);
        books[1] = new ArraysBook("杜十娘怒沉百宝箱",90);
        books[2] = new ArraysBook("钻山豹和座山雕",5);
        books[3] = new ArraysBook("醒世恒言",300);
        for (int i = 0; i < books.length; i++) {
            System.out.println(books[i]);
        }

        //方法1：利用新构造的函数
        ArraysBook.sortPrice(books, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                double d1 = (Double) o1;
                double d2 = (Double) o2;
                double priceVal = d2 - d1;
                if (priceVal > 0) {
                    return -1;
                } else if (priceVal < 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        System.out.println("====排序后====");
        for (int i = 0; i < books.length; i++) {
            System.out.println(books[i]);
        }

        //方法2（推荐）：利用Arrays.sort()方法
        //注意：根据对象的属性给数组排序，跟前面讲的Integer数组排序用的就不是一个方法
        Arrays.sort(books, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {//类型转换异常的用类型接收即可
                ArraysBook book1 = (ArraysBook) o1;
                ArraysBook book2 = (ArraysBook) o2;
                //使用(int)Math.ceil(o1.getPrice()-o2.getPrice())强转，会造成精度丢失。
                //如果差值为-0.5，向上取整会变为0，从小于0直接变为等于0.如果判断条件是当x<0执行操作时，就会产生错误结果
                return book1.getPrice() < book2.getPrice() ? 1: -1;
                //需要三元构造器输出三种结果，可以用return (x < y) ? -1 : ((x == y) ? 0 : 1);
            }
        });

        //对书名长度进行比较
        Arrays.sort(books, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                ArraysBook book1 = (ArraysBook) o1;
                ArraysBook book2 = (ArraysBook) o2;
                //更改 book1 和 book2 的前后顺序可实现从大到小/从小到大的变化
                return book1.getName().length() - book2.getName().length();
            }
        });
    }
}