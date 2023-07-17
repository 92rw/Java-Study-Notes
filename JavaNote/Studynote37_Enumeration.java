
/*
1）枚举对应英文（enumeration，简写enum）
2）枚举是一组常量的集合
3）可以这里理解：枚举属于一种特殊的类，里面只包含一组有限的特定的对象。

1）自定义类实现枚举
2）使用enum关键字实现枚举
 */

//演示自定义枚举实现
class Season1 {//类
    private String name;
    private String desc;//描述

    //定义了四个对象（对象名大写：常量命名规范），使用 final + static 共同修饰，实现底层优化
    //final修饰对象（引用）只是保证指向不变，但不能保证对象本身不被修改
    public static final Season1 SPRING = new Season1("春天","温暖");
    public static final Season1 SUMMER = new Season1("夏天","炎热");
    public static final Season1 AUTUMN = new Season1("秋天","凉爽");
    public static final Season1 WINTER = new Season1("冬天","寒冷");

    //1.将构造器私有化，防止直接 new
    //2.去掉 setXXX 方法，防止属性被修改
    //3.在 Season 内部创建固定的对象

    private Season1(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }


    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "Season{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}

//使用 enum 实现枚举
enum Season2 {
    // 1.使用关键字 enum 替代 class
    // 2.常量名(实参列表)
    // 3.如果有多个常量，使用 , (逗号)间隔
    // 4.如果使用enum来实现枚举，要求将定义常量对象，写在最前面

    SPRING("春天","温暖"), SUMMER("夏天","炎热"), AUTUMN("秋天","凉爽"), WINTER("冬天","寒冷");

    private String name;
    private String desc;//描述
    private Season2(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }


    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "Season{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}

/*
enum 关键字注意事项
1.当我们使用 enum 关键字开发一个枚举类时，默认会继承 Enum 类，对象都使用 final + static 共同修饰
（使用反编译工具 javap ，可查询到这个类是 java.lang.Enum 的子类）
2.传统的public static final Season2 SPRING = new Season2("春天","温暖"); 简化成 SPRING("春天","温暖"), 这里必须知道，它调用的是哪个构造器
3.如果使用无参构造器创建枚举对象，则实参列表和小括号都可以省略
4.当有多个枚举对象时，使用，间隔，最后有一个分号结尾
5.枚举对象必须放在枚举类的行首

使用细节
1）使用 enum 关键字后，就不能再继承其它类了，因为 enum 会隐式继承 Enum，而 Java 是单继承机制。
2）枚举类和普通类一样，可以实现接口，如下形式。
enum 类名 implements 接口1，接口2{}

 */
enum Gender2{
    BOY, GIRL;
}

public class Studynote37_Enumeration {
    public static void main(String[] args) {
        Gender2 boy = Gender2.BOY;
        Gender2 boy2 = Gender2.BOY;
        System.out.println(boy);//因为 Gendere2 没有 toString() 方法，因此调用父类 Enum 的 toString() 方法，根据源码会得到声明常量的名字
        System.out.println(boy == boy2);

    }
}

/*
enum 常用方法一览表

方法名                                 详细描述
values              返回当前枚举类中所有的常量
valueOf             传递枚举类型的 Class 对象和枚举常量名称给静态方法 valueOf，会得到与参数匹配的枚举常量。
toString            得到当前枚举常量的名称。可以通过重写这个方法来使得到的结果更易读。
equals              在枚举类型中可以直接使用“==来比较两个枚举常量是否相等。 Enum 提供的这个 equals() 方法，
                    也是直接使用“=="实现的。它的存在是为了在Set、List和Map中使用。注意，equals是不可变的。
hashCode            Enum实现了 hashCode() 来和 equals() 保持一致。它也是不可变的。
getDeclaringClass   得到枚举常量所属枚举类型的 Class 对象。可以用它来判断两个枚举常量是否属于同一个枚举类型。
name                得到当前枚举常量的名称，在子类中不能重写。建议优先使用 toString()。
ordinal             得到当前枚举常量的位置号，默认从0开始。
compareTo           枚举类型实现了 Comparable 接口，比较位置号大小
clone               枚举类型不能被Clone。为了防止子类实现克隆方法，Enum 实现了一个仅抛出 CloneNotSupportedException 异常的不变 Clone()
 */
class EnumMethod{
    public static void main(String[] args) {
        Season2 autumn = Season2.AUTUMN;
        System.out.println(autumn);//方法中重写过 toString()，因此输出的是重写后的方法
        System.out.println(autumn.name());//输出枚举对象名称
        System.out.println(autumn.ordinal());//当前枚举常量的次序，从0开始编号

        //从反编译看到，有一个 values() 方法，返回的是 Season2[] 数组
        //这个数组含有定义的所有枚举对象
        System.out.println("===遍历取出枚举对象(增强for)===");
        Season2[] values = Season2.values();
        for (Season2 season : values) { //增强for循环
            System.out.println(season);
        }

        //valueOf()将字符串转换成枚举对象，要求字符串必须为已有的常量名，否则报异常
        Season2 autumn1 = Season2.valueOf("AUTUMN");
        //执行流程：
        //1.根据输入的 "AUTUMN" 到 Season2 枚举对象去查找
        //2.如果找到了就返回，没有找到就报错
        System.out.println("autumn1 = " + autumn1);
        System.out.println(autumn == autumn1);

        //compareTo：比较枚举对象位置号大小，返回的是两个值的差(int)
        System.out.println(Season2.WINTER.compareTo(Season2.SUMMER));
    }
}

class enrichedFor{//演示增强for循环
    public static void main(String[] args) {
        int[] nums = {1, 2, 9};
        System.out.println("===普通for循环===");
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
        //增强for循环，本质是迭代器遍历
        System.out.println("===增强for循环===");
        for (int i : nums) {
            //执行流程是 依次从nums数组中取出数据，赋给i
            System.out.println("i = " + i);
        }
    }
}

/*
枚举类练习
1.创建一个Color枚举类，有 RED, BLUE, BLACK, YELLOW, GREEN 这个五个枚举值/对象
2.枚举类包含三个属性 redValue, greenValue，blueValue。创建构造方法，参数包括这三个属性
3.每个枚举值都要给这三个属性赋值，三个属性对应的值分别是
4.red:255,0,0 blue:0,0,255 black:0,0,0 yellow:255,255,0 green:0,255,0
5.定义接口，里面有方法 show() ，要求Color实现该接口
6.show()方法中显示三属性的值
7.将枚举对象在switch语句中匹配使用
 */

enum Color implements ShowColor{
    RED(255,0,0), BLUE(0,0,255), BLACK(0,0,0), YELLOW(255,255,0), GREEN(0,255,0);
    private int redValue;
    private int greenValue;
    private int blueValue;
    //枚举的构造器，默认是私有的
    Color(int redValue, int greenValue, int blueValue) {
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }

    @Override
    public void show() {
        System.out.println("红绿蓝三色值分别为 " + redValue + ", " + greenValue + ", " + blueValue);
    }
}
interface ShowColor{
    public void show();//此处 public 可省略
}
class EnumExercise{
    public static void main(String[] args) {
        //演示枚举值的 switch 使用
        Color green = Color.GREEN;
        green.show();
        //switch的括号中，放入枚举对象；每个 case 中写入枚举类中定义的对象
        switch (green){
            /*此处 switch 语句也可以接收用户输入来匹配
        String color = input.nextLine();
        switch (Color.valueOf(color))
*/
            case YELLOW:
                System.out.println("匹配到黄色");
                break;
            case BLACK:
                System.out.println("匹配到黑色");
                break;
            default:
                System.out.println("未匹配到颜色");
        }
    }
}
