/*
抽象类最佳实践-模板设计模式
最佳实践
需求
1）有多个类，完成不同的任务iob
2）要求统计各自完成任务的时间
3）请编程实现TestTemplate.java

感情的自然流露
1.先用最容易想到的方法
2.分析问题，提出使用模板设计模式

总结归纳：使用了继承 但是  每个类 job的方法体 是不一样的 所以  将该方法抽象
 */
public class Studynote32_Prototype {
    public static void main(String[] args) {
        Proto01 t01 = new Proto01();
        t01.job();
        System.out.println("=====使用模板设计模式重构=====");
        Proto02 t02 = new Proto02();
        t02.caleTimes();
        Proto03 t03 = new Proto03();
        t03.caleTimes();
    }
}

class Proto01{
    public void job(){
        long start = System.currentTimeMillis();//任务开始时间
        long num = 0;
        for (int i = 0; i < 80000; i++) {
            num += i;
        }
        long end = System.currentTimeMillis();//任务结束时间
        System.out.println("和为 " + num + " 任务用时 " + (end-start) + " 毫秒");
    }
}
//可以将需要执行的运算代码块放到两个计算的方法之中，通过变化运算代码块实现复用
//其实就是把共同的代码提到父类做实现，不同的代码提到父类做抽象

/*
最佳实践
设计一个抽象类（Template），能完成如下功能：
1）编写方法caleTime()，可以计算某段代码的耗时时间
2）编写抽象方法 job()
3）编写一个子类Sub，继承抽象类Template，重写抽象方法。
4）编写一个测试类TestTemplate，看看是否好用。
 */
abstract class Template {//抽象类-模板设计模式

    public abstract void job();//抽象方法

    public void caleTimes() {//统计耗时多久

        long start = System.currentTimeMillis();
        job();//执行时动态绑定
        long end = System.currentTimeMillis();
        System.out.println(" 任务用时: " + (end - start) + " 毫秒");
    }
}

class Proto02 extends Template{
    public void job() {//实现父类的抽象方法
        long num = 0;
        for (int i = 0; i < 80000; i++) {
            num += i;
        }
        System.out.print("和为" + num);
    }
}

class Proto03 extends Template{
    public void job() {//实现父类的抽象方法
        long num = 1;
        for (int i = 1; i < 50; i++) {
            num *= i;
        }
        System.out.print("积为" + num);
    }
}
/*
具体体现在：job 方法连方法体都没有了，会让你意思到当前父类只是一个模板，而不是一个可运行的实例！
说一下我的理解：其实不用抽象只用继承也可以做到代码复用性高且简洁
但把父类的 job 声明为 abstract 以后，我们当然一方面在父类的 job 中就不需要写一些无意义的代码
另外一方面，abstract 也会强迫地提醒每个继承的子类必须去实现抽象方法

我感觉就是搞个抽象方法，然后这个抽象类中有一个·公共的实现方法，再搞个类继承这个抽象类，重写抽象方法，
再调用抽象方法中的实现方法
 */