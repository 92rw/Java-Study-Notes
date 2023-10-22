# 装饰器模式

装饰器类，用于增强原始类的功能。装饰器类和原始类的区别在于，装饰器类是对原始类的功能增强，不能独立使用。





```java

//代码演示：装饰器模式中，装饰类和被装饰类（约定俗成，而非必须）继承自同一个父类。
//装饰器模式可以在不改变原有类的前提下给对象增加功能
//语法和代理模式确实很相似，但代理专注于调用原功能，装饰专注于扩展原功能。
//注意：这里的重点不是父类子类的多态关系，是类中属性为一个类（这是一种类的组合关系--与继承相对应）

abstract class Reader1_ { //抽象类

    public void readFile() {}
    public void readString() {}

    //在Reader_ 抽象类，使用read方法统一管理.
    //后面在调用时，利于对象动态绑定机制， 绑定到对应的实现子类即可.
    //public abstract void read();
    //父类两个空方法就是为了避免去调用子类特有方法进行向下转型而写的
}

//创建两个节点流
class StringReader1_ extends Reader1_ {
    public void readString() {
        System.out.println("读取字符串..");
    }
}
class FileReader1_ extends Reader1_ {

    public void readFile() {
        System.out.println("对文件进行读取...");
    }
}

//做成处理流/包装流
class BufferedReader1_ extends Reader1_{

    private Reader1_ reader_; //属性是 Reader_类型

    //接收Reader_ 子类对象
    public BufferedReader1_(Reader1_ reader_) {
        this.reader_ = reader_;
    }

    public void readFile() { //封装一层
        reader_.readFile();
    }

    //让方法更加灵活， 多次读取文件, 或者加缓冲byte[] ....
    public void readFiles(int num) {
        for(int i = 0; i < num; i++) {
            reader_.readFile();
        }
    }

    //扩展 readString, 批量处理字符串数据
    public void readStrings(int num) {
        for(int i = 0; i <num; i++) {
            reader_.readString();
        }
    }
}

class Test_ {
    public static void main(String[] args) {

        //多态+动态绑定：要读写文件，就传文件的构造器，读写字符串，就传字符串的构造器
        BufferedReader1_ bufferedReader_ = new BufferedReader1_(new FileReader1_());
        bufferedReader_.readFiles(10);
        //bufferedReader_.readFile();

        //这次希望通过 BufferedReader_ 多次读取字符串
        BufferedReader1_ bufferedReader_2 = new BufferedReader1_(new StringReader1_());
        bufferedReader_2.readStrings(5);
    }
}

```

