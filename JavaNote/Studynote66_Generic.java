import java.util.*;

public class Studynote66_Generic {
    public static void main(String[] args) {

        //注意，特别强调： E具体的数据类型在定义GenericPerson对象的时候指定,即在编译期间，就确定E是什么类型
        GenericPerson<String> person1 = new GenericPerson<String>("测试样例");
        person1.show(); //String

        /*
            你可以这样理解，上面的GenericPerson类
            class GenericPerson {
                String s ;//E表示 s的数据类型, 该数据类型在定义GenericPerson对象的时候指定,即在编译期间，就确定E是什么类型

                public GenericPerson(String s) {//E也可以是参数类型
                    this.s = s;
                }

                public String f() {//返回类型使用E
                    return s;
                }
            }
         */

        GenericPerson<Integer> person2 = new GenericPerson<Integer>(100);
        person2.show();//Integer

        /*
            class GenericPerson {
                Integer s ;//E表示 s的数据类型, 该数据类型在定义GenericPerson对象的时候指定,即在编译期间，就确定E是什么类型

                public GenericPerson(Integer s) {//E也可以是参数类型
                    this.s = s;
                }

                public Integer f() {//返回类型使用E
                    return s;
                }
            }
         */
    }
}

//泛型的作用是：可以在类声明时通过一个标识表示类中某个属性的类型，或者是某个方法的返回值的类型，或者是参数类型

class GenericPerson<E> {
    E s ;//E表示 s的数据类型, 该数据类型在定义GenericPerson对象的时候指定,即在编译期间，就确定E是什么类型

    public GenericPerson(E s) {//参数类型使用E
        this.s = s;
    }

    public E f() {//返回类型使用E
        return s;
    }

    public void show() {
        System.out.println(s.getClass());//显示s的运行类型
    }
}

/**泛型练习1
 * 创建  3个学生对象
 * 放入到HashSet中学生对象, 使用.
 * 放入到  HashMap中，要求 Key 是 String name, Value 就是 学生对象
 * 使用两种方式遍历
 */

class GenericExercise1 {
    public static void main(String[] args) {
        //使用泛型方式给HashSet 放入3个学生对象
        HashSet<GenericStudent> students = new HashSet<GenericStudent>();//等号左边指定了泛型类型，等号右边可以不写
        GenericStudent s1 = new GenericStudent("jack", 18);
        GenericStudent s2 = new GenericStudent("tom", 28);
        GenericStudent s3 = new GenericStudent("mary", 19);


        students.add(s1);
        students.add(s2);
        students.add(s3);

        //遍历
        for (GenericStudent student : students) {
            System.out.println(student);
        }

        //使用泛型方式给HashMap 放入3个学生对象
        //K -> String V->Student
        HashMap<String, GenericStudent> hm = new HashMap<String, GenericStudent>();
        /*
            public class HashMap<K,V>  {}
         */
        hm.put(s1.getName(),s1);
        hm.put(s2.getName(),s2);
        hm.put(s3.getName(),s3);

        //迭代器 EntrySet
        /*
        public Set<Map.Entry<K,V>> entrySet() {
            Set<Map.Entry<K,V>> es;
            return (es = entrySet) == null ? (entrySet = new EntrySet()) : es;
        }
         */
        Set<Map.Entry<String, GenericStudent>> entries = hm.entrySet();
        /*
            public final Iterator<Map.Entry<K,V>> iterator() {
                return new EntryIterator();
            }
         */

        //编译器只能检查编译类型  运行了才知道运行类型是什么  所以传的是编译类型指定的泛型
        //没有泛型的时候，我们传入和接收都得是Object，有了就可以直接声明类型了
        //因为你接受的编译类型是Entry，Entry里有getKey和getValue这两种方法，
        Iterator<Map.Entry<String, GenericStudent>> iterator = entries.iterator();
        System.out.println("==============================");
        while (iterator.hasNext()) {
            Map.Entry<String, GenericStudent> next =  iterator.next();
            System.out.println(next.getKey() + "-" + next.getValue());

        }

    }
}

class GenericStudent {
    //代码改进：若为了防止加入集合的元素重复，可以重写一下equals和hashcode()方法
    private String name;
    private int age;

    public GenericStudent(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

/**泛型练习2
 * 定义 GenericEmployee 类
 * 1) 该类包含：private成员变量name,sal,birthday，其中 birthday 为 GenericBirthday 类的对象；
 * 2) 为每一个属性定义 getter, setter 方法；
 * 3) 重写 toString 方法输出 name, sal, birthday
 * 4) GenericBirthday 类包含: private成员变量month,day,year；并为每一个属性定义 getter, setter 方法；
 * 5) 创建该类的 3 个对象，并把这些对象放入 ArrayList 集合中（ArrayList 需使用泛型来定义），对集合中的元素进行排序，并遍历输出：
 *
 * 排序方式： 调用ArrayList 的 sort 方法 ,
 * 传入 Comparator对象[使用泛型]，先按照name排序，如果name相同，则按生日日期的先后排序。【即：定制排序】

 */

class GenericExercise2 {
    public static void main(String[] args) {

        ArrayList<GenericEmployee> employees = new ArrayList<>();
        employees.add(new GenericEmployee("tom", 20000, new GenericBirthday(1980,12,11)));
        employees.add(new GenericEmployee("jack", 12000, new GenericBirthday(2001,12,12)));
        employees.add(new GenericEmployee("tom", 50000, new GenericBirthday(1980,12,10)));

        System.out.println("employees=" + employees);


        employees.sort(new Comparator<GenericEmployee>() {
            @Override
            public int compare(GenericEmployee emp1, GenericEmployee emp2) {
                //先按照name排序，如果name相同，则按生日日期的先后排序。【即：定制排序】
                /*先对传入的参数进行验证
                (1) 这里使用 instanceof 是无法剔除子类的情况的，因为 Employee子类 instanceof Employee 也是返回 true
                (2) 这里 instanceof 唯一的作用是，排除 o1 和 o2 为 null 的情况，因为 null instanceof Employee 为 false
                此处也可以写成 != null
                 */
                if(!(emp1 instanceof GenericEmployee && emp2 instanceof GenericEmployee)) {
                    System.out.println("类型不正确..");
                    return 0;
                }
                //比较name
                int i = emp1.getName().compareTo(emp2.getName());
                if(i != 0) {
                    return i;
                }

                //下面是对birthday的比较，因此，我们最好把这个比较，放在 Birthday 类完成
                //如果不进行封装，此处需要依次比较年月日的差值，在比较结果不为零时返回数据
                //将Birthday 类实现 Comparable接口，把比较的代码封装在 compareTo 方法后，将来可维护性和复用性，就大大增强.
                return emp1.getBirthday().compareTo(emp2.getBirthday());
            }
        });

        System.out.println("==对雇员进行排序==");
        System.out.println(employees);

    }
}

class GenericBirthday implements Comparable<GenericBirthday>{//实现接口时指定泛型，好处是不需要向下转型
    private int year;
    private int month;
    private int day;

    public GenericBirthday(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "MyDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    @Override
    public int compareTo(GenericBirthday o) { //把对year-month-day比较放在这里

        int yearMinus = year - o.getYear();
        if(yearMinus != 0) {
            return  yearMinus;
        }
        //如果year相同，就比较month
        int monthMinus = month - o.getMonth();
        if(monthMinus != 0) {
            return monthMinus;
        }
        //如果year 和 month
        return day - o.getDay();
    }
}

class GenericEmployee {
    private String name;
    private double sal;
    private GenericBirthday birthday;

    public GenericEmployee(String name, double sal, GenericBirthday birthday) {
        this.name = name;
        this.sal = sal;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenericBirthday getBirthday() {
        return birthday;
    }

    public void setBirthday(GenericBirthday birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "\nEmployee{" +
                "name='" + name + '\'' +
                ", sal=" + sal +
                ", birthday=" + birthday +
                '}';
    }
}


/**
 *  泛型接口使用的说明
 *  1. 接口中，静态成员也不能使用泛型
 *  2. 泛型接口的类型, 在继承接口或者实现接口时确定
 *  3. 没有指定类型，默认为Object
 *
 *  接口的属性的修饰符只能是public static final，方法是public的，抽象方法可以省略关键字，实现了接口的实例对象可以直接使用接口的默认方法。
 */

//在继承接口 指定泛型接口的类型
interface IA extends IUsb<String, Double> {

}
//当我们去实现IA接口时，因为IA在继承IUsu 接口时，指定了U 为String R为Double
//，在实现IUsu接口的方法时，使用String替换U, 是Double替换R
class AA implements IA {

    @Override
    public Double get(String s) {
        return null;
    }
    @Override
    public void hi(Double aDouble) {

    }
    @Override
    public void run(Double r1, Double r2, String u1, String u2) {

    }
}

//实现接口时，直接指定泛型接口的类型
//给U 指定Integer 给 R 指定了 Float
//所以，当我们实现IUsb方法时，会使用Integer替换U, 使用Float替换R
class BB implements IUsb<Integer, Float> {

    @Override
    public Float get(Integer integer) {
        return null;
    }

    @Override
    public void hi(Float aFloat) {

    }

    @Override
    public void run(Float r1, Float r2, Integer u1, Integer u2) {

    }
}
//没有指定类型，默认为Object
//建议直接写成 IUsb<Object,Object>
class CC implements IUsb { //等价 class CC implements IUsb<Object,Object> {
    @Override
    public Object get(Object o) {
        return null;
    }
    @Override
    public void hi(Object o) {
    }
    @Override
    public void run(Object r1, Object r2, Object u1, Object u2) {

    }

}

interface IUsb<U, R> {

    int n = 10;
    //U name; 不能这样使用

    //普通方法中，可以使用接口泛型
    R get(U u);

    void hi(R r);

    void run(R r1, R r2, U u1, U u2);

    //在jdk8 中，可以在接口中，使用默认方法, 也是可以使用泛型
    default R method(U u) {
        return null;
    }
}
