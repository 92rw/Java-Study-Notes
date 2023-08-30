# main方法

解释main方法的形式 ： public static void main(String[] args))

* main()方法由java虚拟机调用，和main()方法不在同一个类
* java虚拟机需要调用类的main()方法，所以该方法的访问权限必须是public
* java虚拟机在执行main()方法时不必创建对象，所以该方法必须是static
* 该方法接收String类型的数组参数，该数组中保存执行java命令时传递给所运行的类的参数
* java程序运行时，根据参数传入顺序执行

>特别提示：
>1）在main()方法中，我们可以直接调用main方法所在类的静态方法或静态属性。
>
>2）但是，不能直接访问该类中的非静态成员，必须创建该类的一个实例对象后，才能通过这个对象去访问类中的非静态成员

```java
class Main {
    private static String name = "静态属性 name";

    private int n1 = 10000; //非静态变量/属性无法被访问

    private static void hi(){
        System.out.println("静态方法 hi()");
    }

    private void hello(){
        System.out.println("非静态方法 hello()");
    }

    public static void main(String[] args) {
        //1.静态方法 main() 可以直接访问本类的静态成员
        System.out.println(name);
        hi();

        //2.静态方法 main() 不能直接访问本类的非静态成员
        //System.out.println(n1);
        //hello();

        //3.静态方法 main() 要访问非静态成员，需要先创建对象再调用
        Main main1 = new Main();
        System.out.println("非静态属性 n1 = " + main1.n1);
        main1.hello();
    }
}
```

IDEA中，向main方法传参的方法：选中上方Edit Configurations， 在Program arguments 中传入参数
