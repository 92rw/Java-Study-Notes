# 工厂模式

工厂模式本身是为了屏蔽构建对象的细节，以实现面向接口的目的，以达到无感知切换产品的目的。

到了抽象工厂，升级成了连工厂都是多实现的。

不同的工厂里有对同一个产品的不同实现。当我们想要切换使用的产品的时候，直接切换工厂就行了，而不用在普通工厂里去修改代码。把工厂都抽象出来，是真正实现了面向接口编程。符合了开闭原则。



案例演示：

铁路机车可以牵引列车

```java
public abstract class Locomotive {
    public String type;
    public abstract void pullTrain();
}
```

不同机车有不同的运用方式

```java
public class HXD3 extends Locomotive {
    public HXD3() {
        this.type = "HXD3型";
    }
    @Override
    public void pullTrain() {
        System.out.println(type + "机车担当交路");
    }
}

public class HXD3C extends Locomotive {
    public HXD3C() {
        this.type = "HXD3C型";
    }
    @Override
    public void pullTrain() {
        System.out.println(type + "机车担当交路");
    }
}

public class HXN5B extends Locomotive {
    public HXN5B() {
        this.type = "HXN5B型";
    }
    @Override
    public void pullTrain() {
        System.out.println(type + "机车担当交路");
    }
}
```

一般方法：

当班司机通过新建Operation对象，调用Operation.getLocomotive() 方法获得本务机车担当任务

```java
public class Operation{
    public Locomotive getLocomotive() {
        Locomotive onDutyLoco = new HXD3C();
        return onDutyLoco;
    }
}
```

此时的主方法如下

```java
    public static void main(String[] args) {
        Operation operation = new Operation();
        operation.getLocomotive();
    }
```







## 简单工厂模式

又称作静态工厂方法模式（Static Factory Method），不属于23种GOF设计模式之一

案例演示：

新建“机务段”类，根据提供的机车类型，返回机车对象

```java
public class LocomotiveDepot {
    public static Locomotive offer(Class<? extends Locomotive> clazz) {
        if (clazz != null) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("该机车不存在");
            }
        }
        return null;
    }
}
```

* 要求：所有对象的构造方法必须保持一致
* 特点：提供静态方法

Operation 类根据提供的机车名，创建Class对象，调用 LocomotiveDepot 类的静态方法

```java
public class Operation{
    public Locomotive getLocomotive(String type) {
        Class classOfType = Class.forName(type);
        Locomotive onDutyLoco = LocomotiveDepot.offer(classOfType);
        return onDutyLoco;
    }
}
```

* 优点：使用端不需要创建实例，耦合性低
* 缺点：违反了开闭原则

此时的主方法如下

```java
    public static void main(String[] args) {
        Operation operation = new Operation();
        operation.getLocomotive("HXD3");
    }
```

原理说明：[秒懂设计模式之简单工厂模式（Simple Factory Pattern） - ShuSheng007](https://shusheng007.top/2020/02/16/simple-factory-pattern/)



### 在Integer类的使用

在创建 Integer 类实例时，执行 valueOf(int i) 方法，如果数字 i 在-128~127之间，则从内部类 IntegerCache 获取实例，而非创建新对象

参考资料：[包装类Integer的equal方法与“==”运算符 比较 - 三只蛋黄派 - 博客园 (cnblogs.com)](https://www.cnblogs.com/threeAgePie/p/15766211.html)



## 工厂方法模式（Factory Method）

案例演示：

规范机车操作流程，在运用时不允许执行其他操作。

将Operation类改为抽象类，控制 getLocomotive 方法在执行时不得改变，子类通过重写 get() 方法实现驾驶不同机车

```java
public abstract class Operation{
    public final Locomotive getLocomotive(String type) {
        Locomotive onDutyLoco = get(type);
        return onDutyLoco;
    }
    public abstract Locomotive get(String type);
}
```



```java
public class DieselOperation extends Operation {   
    @Override
    public Locomotive get(String type) {
       LocomotiveDepot locomotiveDepot;
       if ("HXN5B".equals(type)) {
            locomotive = new HXN5B();
        } else {
            throw new RuntimeException("找不到该类型内燃机车");
        }
        return locomotive;
    }
}
public class ElectricOperation extends Operation {
    @Override
    public Locomotive get(String type) {
        Locomotive locomotive;
        if ("HXD3".equals(type)) {
            locomotive = new HXD3();
        } else if ("HXD3C".equals(type)) {
            locomotive = new HXD3C();
        } else {
            throw new RuntimeException("找不到该类型电力机车");
        }
        return locomotive;
    }
}
```

此时的主方法如下

```java
    public static void main(String[] args) {
        Operation operation = new ElectricOperation();
        operation.getLocomotive("HXD3");
    }
```



在Java中的应用：

* 实现Collection接口则必须实现Iterable接口，该接口只有一个iterator()方法，返回一个Iterator类型的对象



参考资料：

[秒懂设计模式之工厂方法模式（Factory Method Pattern） - ShuSheng007](https://shusheng007.top/2020/02/16/factory-method-pattern/)





## 抽象工厂模式（Abstract Factory）

[java抽象工厂模式（Abstract Factory）-创建型_nogos的博客-CSDN博客](https://blog.csdn.net/sunxianghuang/article/details/51811126)

[秒懂设计模式之抽象工厂模式（Abstract Factory Pattern） - ShuSheng007](https://shusheng007.top/2020/02/16/abstract-factory-pattern/)



参考资料

[工厂模式——猫粮公司的演进 | 蝉沐风 (chanmufeng.com)](https://www.chanmufeng.com/posts/basic/design-pattern/工厂模式.html)



