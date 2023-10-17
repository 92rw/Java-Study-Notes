# 接口

## 基本介绍

接口就是给出一些没有实现的方法，封装到一起，到某个类要使用的时候，在根据具体情况把这些方法写出来。语法：

```java
interface 接口名{
    属性(public static final);
    抽象方法(public abstract);
    default 默认方法;
}

class 实现类 implements 接口名{
    自己属性;
    自己方法;
    必须实现的接口的抽象方法;
    //默认方法可以不重写
}
```

使用细节

* 接口不能被实例化
* 接口中所有的方法是 public 方法，接口中抽象方法，可以不用 abstract 修饰
* 一个普通类实现接口，就必须将该接口的所有方法都实现（创建子类时快捷键 Alt+Enter 快速重写）
* 抽象类实现接口，可以不用实现接口的方法
* 一个类同时可以实现多个接口
* 接口中属性的访问形式：接口名.属性名
* 一个接口不能继承其它的类，但是可以继承多个别的接口
* 接口的修饰符只能是 public 和默认，这点和类的修饰符是一样的
* 方法重载时，可以自动识别接口类型从而调用对应的方法



### 接口的多态特性

实现接口 VS 继承抽象类

|            | abstract class               | interface                        |
| :--------- | :--------------------------- | -------------------------------- |
| 继承       | 只能extends一个class         | 可以implements多个interface      |
| 字段       | 可以定义实例字段             | 不能定义实例字段                 |
| 抽象方法   | 可以定义抽象方法             | 可以定义抽象方法                 |
| 非抽象方法 | 可以定义非抽象方法           | 可以定义default方法              |
| 应用场景   | 高复用性和可维护性，减少冗余 | 定义参数、结果的规范，让子类实现 |

一个类不可以从多个父类中继承同名的方法，但是可以实现多个接口中的同名方法



## 案例演示

使用接口算税，要求如下（已做简化）：

* 工资收入：超过5000不超过10000的部分税率为10%，超过10000的部分税率为20%
* 稿费收入：税率是20%
* 测试实例的工资收入7500元，稿费收入12000元

代码实现过程如下：

1. 定义接口Income

   ```java
   interface Income {
   	double getTax();	
   }
   ```

2. 工资计算类

   ```java
   class SalaryIncome implements Income{
   	private double salary;
   	public SalaryIncome(double salary) {
   		this.salary = salary;
   	}
   	@Override
   	public double getTax() {
   		if(salary <= 5000) {
   			return 0;
   		}
   		else if(salary >5000 && salary <=10000) {
   			return (salary-5000)*0.1;
   		}
   		else {
   			return (salary-10000)*0.2 + 5000*0.1;
   		}
   	}
   }
   ```

3. 稿费计算类

   ```java
   class RoyaltyIncome implements Income{
   	private double royalty;
   	public RoyaltyIncome(double royalty) {
   		this.royalty = royalty;
   	}
   	@Override
   	public double getTax() {
   		return royalty*0.2;
   	}
   }
   ```

4. 主方法

   ```java
   public class Main {
   	public static void main(String[] args) {
   		Income[] incomes = new Income[] { 
   				new SalaryIncome(7500d), 
   				new RoyaltyIncome(12000d) };
   
   		System.out.println(totalTax(incomes));
   	}
   	
   	public static double totalTax(Income...incomes) {
   		double total = 0;
   		for(Income n : incomes) {
   			total += n.getTax();
   		}
   		return total;
   	}
   }
   ```

   

## 默认方法和静态方法

Java 8 中，你可以为接口添加静态方法和默认方法

* 静态方法：使用 static 关键字修饰。可以通过接口直接调用静态方法，并执行其方法体。
* 默认方法：使用 default 关键字修饰。可以通过实现类的实例对象来调用。

说明：

* 类优先原则：如果子类(或实现类)继承的父类和实现的接口中都声明了同名同参数的默认方法，那么子类在没有重写此方法的情况下，默认调用的是父类中的同名同参数的方法。

* 接口冲突（程序二义性）：如果实现类实现了多个接口，而这多个接口中定义了同名同参数的默认方法，那么在实现类没有重写此方法的情况下，报错。此时可重写该方法并，通过`接口名.super.方法` 访问父类的代码即可

* 实现类可以不实现`default`方法。`default`方法和抽象类的普通方法是有所不同的。因为`interface`没有字段，`default`方法无法访问字段，而抽象类的普通方法可以访问实例字段。

* 接口的静态方法直接和接口本身绑定，也不需要被类给实现，因此不存在接口多继承或者类多实现上的程序二义性而冲突。

  

参考文章：

[JDK8新特性第二篇：四大函数式接口【Function/Consumer/Supplier/Perdicate】、接口的扩展方法【default/static】 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/531651771)
