# 运算符

运算符：一种特殊的符号，用以表示数据的运算、赋值和比较。

在Java的计算表达式中，运算优先级从高到低依次是：

- `()` `{}`
- 单目运算符`!` `~` `++` `--`
- 算数运算符`*` `/` `%` 高于`+` `-`
- 位移运算符`<<` `>>` `>>>`
- 比较运算符
- 逻辑运算符：`&` 高于 `|`
- 三元运算符
- 赋值运算符`+=` `-=` `*=` `/=`

只需要加括号就可以保证运算的优先级正确

## 算术运算符

| 含义 | 加   | 减   | 乘   | 除   | 取余 | 自增 | 自减 |
| ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| 符号 | +    | -    | *    | /    | %    | ++   | --   |

说明：

* 运算结果只会是参与运算中表示范围更大的类型，不会改变其原有类型。

  * 两个int相除无法得到浮点数，只能得到整数部分的int型

    ```java
    double d1 = 10 / 4;   //2.0
    double d2 = 10.0 / 4; //2.5
    ```

  * 当计算得出的数超过基本数据类型最大值时，会造成溢出。例如计算int类型的`2147483640 + 15`时，底层进行的二进制加法如下，得到 `-2147483641`。此时可通过转为long类型相加解决问题

    ```
      0111 1111 1111 1111 1111 1111 1111 1000
    + 0000 0000 0000 0000 0000 0000 0000 1111
    -----------------------------------------
      1000 0000 0000 0000 0000 0000 0000 0111
    ```

* `++`写在前面和后面计算结果是不同的，`++n`表示先加1再引用n，`n++`表示先引用n再加1。
* 按位运算符也属于算数运算

* 整数运算在除数为`0`时会报错，而浮点数运算在除数为`0`时，不会报错，但会返回几个特殊值
  - `NaN`表示Not a Number
  - `Infinity`表示无穷大
  - `-Infinity`表示负无穷大



```java
class FahrtoCel {//华氏度转摄氏度
	public static void main(String[] args) {
		double fahrenheit = 234.5;
		//考虑数学公式和java语言的特性
		double calsius = 5.0 / 9 * (fahrenheit - 32); //计算结果为0.0，是因为前两个数都是int，需转换为double型
		System.out.println("华氏度 " + fahrenheit + " ℉ 对应的摄氏温度为 " + calsius +  " ℃");
	}
}

```

对于char类型的字符，也可以通过ASCII码值或UTF-16数值，转换成int类型实现算数运算 [Ransom Note - LeetCode --- 赎金票据 - LeetCode](https://leetcode.com/problems/ransom-note/solutions/1671552/1ms-100-easy-explanation-java-solution/?envType=study-plan-v2&envId=top-interview-150)

## 关系运算符

| 含义 | 等于 | 不等于 | 小于 | 等于 | 小于等于 | 大于等于 | 检查是否是类对象 |
| ---- | ---- | ------ | ---- | ---- | -------- | -------- | ---------------- |
| 符号 | ==   | !=     | <    | =    | <=       | >=       | instanceof       |

* 关系运算符结果都是boolean，仅可为true或false
* 区分关系运算符“==”和赋值“=”



## 逻辑运算符

连接多个关系表达式，最终结果为boolean型

| 含义     | 逻辑与       | 短路与       | 逻辑或         | 短路或         | 取反     | 逻辑异或 |
| -------- | ------------ | ------------ | -------------- | -------------- | -------- | -------- |
| 符号     | &            | &&           | \|             | \|\|           | !a       | a^b      |
| true条件 | 两者同时为真 | 两者同时为真 | 两者有一个为真 | 两者有一个为真 | a为false | a和b不同 |

* 短路运算在第一个条件不满足时直接退出，不判断后面的条件，效率高；逻辑运算会判断所有条件

```java
class LogicOperatorExercise{
	public static void main(String[] args) {
        boolean x = true;
        boolean y = false;
        short z = 46;
        if ((z++==46)&&(y=true)) z++;	//z判断为真且自增一次；y赋值为真，因此if成立，z变为48
        if((x=false)||(++z==49)) z++;	//x赋值为假，短路或继续执行；z自增一次，if成立，z变为50
        System.out.println("z =" + z);	//输出结果为50
	}
}
```



## 赋值运算符

赋值运算符左边只能是变量，右边可以是变量、表达式、常量值

复合赋值运算符：a += 3; 等价于a = a + 3;

```java
class AssignPlus01{
   public static void main(String[] args) {

      //当左右两边都是数值型时，做加法运算
      System.out.println(100 + 3 + "hello"); //103hello

      //当左右两边有一方为字符串，则做拼接运算
      System.out.println("hello" + 100 + 3); //hello1003
   }
}

class AssignPlus02{ //运算符和变量的计算
	public static void main(String[] args){
		//两个字符串相加
		String name1 = "双喜临门";
		String name2 = "年年有余";
		System.out.println(name1 + "t" + //可以换行输出
			name2);

		//两个字符相加，得到一个整数
		char c1 = '男';
		char c2 = '女';
		System.out.println(c1 + c2); //得到两个字符码值的整数

		//保存两个价格
		float price1 = 123.56f;
		double price2 =100.11;
		System.out.println(price1 + price2);//得到double型的数字，但是表示为带有尾数的近似值
		}
}
```

复合赋值运算会默认强制类型转换

```java
class AssignOperator {
	public static void main(String[] args) {
		int n1 = 10;
		n1 += 4;
		System.out.println(n1);//14
		n1 /= 3;
		System.out.println(n1);//4	

		//复合赋值运算会默认强制类型转换
		byte b = 125;
		b += 2;    //等价于 b = (byte)(b + 2);
		System.out.println(b);    //到达byte类型最大值127
		b++;    //等价于 b = (byte)(b + 1);
		System.out.println(b);    //超出byte类型最大值，输出为-128
	}
}
```



## 三元运算符

```java
//基本语法
条件表达式 ? 表达式1 : 表达式2
//1.如果条件表达式为 true , 运算后的结果是表达式 1 ;
//2.如果条件表达式为 false , 运算后的结果是表达式 2 ;
```

表达式1和表达式2要为可以赋给接收变量的类型（或可以自动转换/或在代码中强制转换）

```java
class TernaryOperator {
	public static void main(String[] args) {
		int n1 = 55;
		int n2 = 33;
		int n3 = 123;
		//思路：1.先得到n1和n2中最大数，保存到max1；2.得到max1和n3中最大数，保存到max2
		int max1 = n1 >= n2 ? n1: n2;
		int max2 = max1 >= n3 ? max1 :n3;
		System.out.println(max2);

		//方法2：用一条语句实现
		int max = (n1>n2?n1:n2)>n3?(n1>n2?n1:n2):n3;
		System.out.println("最大数=" + max);
	}
}
```

