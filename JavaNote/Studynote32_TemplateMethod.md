# 模板方法模式

抽象类体现的就是一种模板模式的设计，抽象类作为多个子类的通用模板，子类在抽象类的基础上进行扩展、改造，但子类总体上会保留抽象类的行为方式。

> 当功能内部一部分实现是确定的，一部分实现是不确定的。这时可以把不确定的部分暴露出去，让子类去实现。

模板方法的核心思想是：父类定义骨架，子类实现某些细节。



Java标准库也有很多模板方法的应用。在集合类中，`AbstractList`和`AbstractQueuedSynchronizer`都定义了很多通用操作，子类只需要实现某些必要方法。



最佳实践
设计一个抽象类（Template），能完成如下功能：

* 编写方法caleTime()，可以计算某段代码的耗时时间
* 编写抽象方法 job()
* 编写一个子类Sub，继承抽象类Template，重写抽象方法。
* 编写一个测试类TestTemplate，看看是否好用。

代码实现：

1. 编写抽象类

   ```java
   abstract class Template {//抽象类-模板设计模式
       
       public abstract void job();//抽象方法
       
       final void caleTimes() {//模板方法：不能被子类重写
           long start = System.currentTimeMillis();
           job();//执行时动态绑定
           long end = System.currentTimeMillis();
           System.out.println(" 任务用时: " + (end - start) + " 毫秒");
       }
   }
   ```

2. 编写实现类

   ```java
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
   ```

3. 测试类

   ```java
   class TestTemplate{
   	public static void main(String[] args) {
           Proto02 t02 = new Proto02();
           t02.caleTimes();
           Proto03 t03 = new Proto03();
           t03.caleTimes();
       }
   }
   ```

   

说明：

* 为了防止子类重写父类的骨架方法，可以在父类中对骨架方法使用`final`
* 对于需要子类实现的抽象方法，一般声明为`protected`，使得这些方法对外部客户端不可见。



参考资料

[秒懂设计模式之模板方法模式（Template Method Pattern） - ShuSheng007](https://shusheng007.top/2020/02/16/template-method-pattern/)
