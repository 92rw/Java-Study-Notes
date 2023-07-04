/*
访问修饰符modifier

●基本介绍
java提供四种访问控制修饰符号，用于控制方法和属性（成员变量）的访问权限（范围）：
访问级别    访问控制修饰符  本类  本包  子类  不同包
  公开         public      √    √     √     √
 受保护       protected    √    √     √     ×
  默认        没有修饰符    √    √     ×     ×
  私有         private     √    ×     ×     ×
●使用的注意事项
1修饰符可以用来修饰类中的属性，成员方法以及类
2)只有默认的和public才能修饰类！，并且遵循上述访问权限的特点。
3)在子类中的访问权限涉及继承
4)成员方法的访问规则和属性完全一样。
 */
//不同类之间调用private属性或者方法可以通过定义public/protected来访问


/*
●封装(encapsulation)
封装(encapsulation)就是把抽象出的数据[属性]和对数据的操作[方法]封装在一起，
数据被保护在内部，程序的其它部分只有通过被授权的操作[方法]，才能对数据进行操作。

封装的理解和好处
1)隐藏实现细节 方法(连接数据库)  <--调用(传入参数)
2)可以对数据进行验证，保证安全合理
将对象私有化，就是只能通过该类本身的方法去访问其内部成员变量

●封装的实现步骤(三步)
1)将属性进行私有化【不能直接修改属性】
2)提供一个公共(public)的 set 方法，用于对属性判断并赋值
public void setXxx（类型 参数名）{ //Xxx表示某个属性
    //加入数据验证的业务逻辑
    属性=参数名：
    }
3)提供一个公共(public)的get方法，用于获取属性的值
public XX getXxx（）{//权限判断
    return Xx;
    }
 */
public class Studynote21_Encapsulation {
    public static void main(String[] args) {
        //如果要使用快捷键Alr+R快速运行代码块，需要先配置主类
        //第一次点击鼠标运行程序，后面可使用快捷键
        OOPPerson person = new OOPPerson();
        person.setName("AzumaSeren");
        person.setAge(3000);
        person.setSalary(30000);
        System.out.println(person.info());
        System.out.println(person.getSalary());

        //开发方式二：将构造方法和setXxx相结合
        //直接使用构造器指定结果
        OOPPerson nanami = new OOPPerson("nanami", 4000, 12450);
        System.out.println("====nanami的信息====");
        System.out.println(nanami.info());

    }
    
}

//无参构造器写出来是要说明两种方法都能用，一个是new一个对象，调用set，另一个是直接传参
//如果不声明无参构造器，默认的构造器会被有参构造器覆盖掉

/*
运行一段程序：不能随便查看人的年龄，工资等隐私，并对设置的年龄进行合理的验证。年龄合理就设置，否则
给默认年龄，必须在1-120，年龄，工资不能直接查看，name的长度在2-6字符之间

 */
class OOPPerson{
    public String name;
    private int age;    //私有化
    private double salary;


    //键盘Alt+Insert -> Constructor -> 无参构造器
    public OOPPerson() {
    }

    //键盘Alt+Insert -> Constructor -> 新建三个属性的构造器
    //构造器是public可以在任意文件夹下的类调用，同时构造器又能打开它本来类中的所有属性代码 因此就绕过了private
    public OOPPerson(String name, int age, double salary) {
/*
        这段代码没有执行setXxx的判断语句，可将set方法写在构造器中
        this.name = name;
        this.age = age;
        this.salary = salary;*/
        setName(name);
        setAge(age);
        setSalary(salary);
    }
//自己写setXxx和getXxx太慢，使用快捷键
    //键盘Alt+Insert -> Getter and Setter -> 选中需要的代码
    //构造器的作用是为对象初始化属性（只能用一次），set方法是修改对象属性

    //get是用来返回值获取信息的
    //不用get方法直接返回是为了避免输入非法字符时不好return的情况
    public String getName() {
        return name;
    }

    public void setName(String name) {
        //加入对数据的校验，相当于增加了业务逻辑
        if(name.length() >= 2 && name.length() <= 6) {
            this.name = name;
        } else{
            System.out.println("名字长度不对，需在(2-6)之间，赋值默认姓名");
            this.name = "佚名";
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age >= 1 && age <= 120) {//如果在合理范围
            this.age = age;
        }else{
            System.out.println("年龄不对，需在1~120之间，赋值默认年龄18");
            this.age = 18;//设置默认年龄
        }
    }

    public double getSalary() {
        //可以在这里增加对当前对象的权限
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    //写一个方法，返回属性信息
    public String info(){
        return "信息为 name= "+ name + " age = "+ age + " salary = " + salary;
    }
}


