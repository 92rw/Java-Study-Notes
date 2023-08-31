# abstract修饰符

## 基本介绍

abstract只能修饰类和方法，不能修饰属性和其它

当父类的一些方法不能确定时，可以用abstract关键字来修饰该方法，这个方法就是抽象方法。当一个类中存在抽象方法时，需要将该类声明为abstract类（抽象类）。

一般来说，抽象类会被继承，有其子类来实现抽象方法

抽象类的价值更多作用是在于设计，是设计者设计好后，让子类继承并实现抽象类

```java
访问修饰符 abstract 类名{
    访问修饰符 abstract 返回类型 抽象方法名(参数列表); //没有方法体
}
```



使用细节

* 抽象类不能被实例化（但是有默认构造器，供子类继承时调用，抽象类就是为子类服务的。）
* 抽象类不一定要包含abstract方法。也就是说，抽象类可以没有abstract方法
* 一旦类包含了abstract方法,则这个类必须声明为abstract
* 抽象类可以有任意成员【因为抽象类还是类】，比如：非抽象方法、构造器、静态属性等等
* 如果一个类继承了抽象类，则它必须实现抽象类的所有抽象方法，除非它自己也声明为abstract类
* 抽象方法不能使用private、final和static来修饰，因为这些关键字都是和重写相违背的。
  * private子类不能直接访问；final本意是不希望子类重写；
  * static修饰的静态方法有具体的实现，能通过类名直接调用，而abstract只有方法声明没有具体的实现（无方法体），需要在子类或实现类中去编写完整的方法处理逻辑后才能使用
  * 方法不能用private但是属性可以，因为这些属性可以用构造方法和get来获取
  * 被private,final和static修饰的方法都是静态绑定机制的，也就是说，它们属于该类独有的，静态绑定机制对静态方法来说，就是用一个父类引用符号去调用子类的静态方法时，调用的是子类的方法



## 案例演示

使用接口算税，要求如下（已做简化）：

* 工资收入：超过5000不超过10000的部分税率为10%，超过10000的部分税率为20%
* 稿费收入：税率是20%
* 测试实例的工资收入7500元，稿费收入12000元

代码实现过程如下：

1. 定义接口Income

   ```java
   abstract class Income {
   	abstract double getTax();	
   }
   ```

2. 工资计算类

   ```java
   class SalaryIncome extends Income{
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
   class RoyaltyIncome extends Income{
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

   
