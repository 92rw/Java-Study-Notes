# 变量和数据类型

变量的声明

| 数据类型 | 变量名 | (赋值符号) | 值   | (结束符号) |
| -------- | ------ | ---------- | ---- | ---------- |
| int      | a      | =          | 50   | ;          |



## 基本数据类型

| 数据类型 | 表示含义 | 内存占用 | 默认值 | 最小值                     | 最大值                    |
| -------- | -------- | -------- | ------ | -------------------------- | ------------------------- |
| byte     | 整数     | 1        | 0      | -128（负2的7次方）         | 127（2的7次方-1）         |
| short    | 整数     | 2        | 0      | -32768（负2的15次方）      | 32767（2的15次方-1）      |
| int      | 整数     | 4        | 0      | -2147483648（负2的31次方） | 2147483647（2的31次方-1） |
| long     | 整数     | 8        | 0L     | 负2的63次方                | 2的63次方-1               |
| float    | 浮点数   | 4        | 0.0f   | 1.4E-45                    | 3.4028235E38              |
| double   | 浮点数   | 8        | 0.0    | 4.9E-324                   | 1.7976931348623157E308    |
| char     | 字符     | 2        | \u0000 | 0                          | 65535（2的16次方-1）      |
| boolean  | 布尔值   | 1        | false  | true                       | false                     |

* Java的整型常量（具体值）默认为int型，声明long型常量须后加'l'或'L'，以保证在我们声明的数据大小超过int范围时自动转换为long型数据，然后再赋值给long型变量，数据不溢出。
* bit:计算机中的最小存储单位，byte:计算机中基本存储单元, 1 byte = 8 bit
* 不可以0或非0的整数替代true和false，和C语言不同
* 即使表示布尔量仅需 1 位（0 或 1），它在内存中通常被存储为 1 字节。这是因为现代计算机 CPU 通常将 1 字节作为最小寻址内存单元。在boolean数组中，以更省内存的形式表示
* [同样是占32个坑，凭啥你float就比int的范围更大？ - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/84453627)
* [IEEE-754浮点数那些坑：0.1加0.2不等于0.3！ | 随笔 (fasionchan.com)](https://fasionchan.com/posts/ieee754-traps/)



### 浮点数

浮点数 = 符号位 + 指数位 + 尾数位

* 小数都是近似值，因为尾数部分可能丢失，造成精度损失
* 浮点型常量默认为double型，声明float型常量时需后加'f'或"F"
* double型在赋值时可表示为“0.0d”的形式
* 通常情况下，应该使用double型，因为它比float型更精确

浮点数的表示形式

| 十进制形式           | 科学计数法                        |
| -------------------- | --------------------------------- |
| 5.12                 | 5.12e2[5.12*10的2次方 , 512.0]    |
| 512.0f               | 5.12E-2[5.12*10的-2次方 , 0.0512] |
| .512（必须有小数点） |                                   |

```java
class FloatDetail{
   public static void main(String[] args){
      double num1 = 2.7;
      double num2 = 8.1 / 3;
      double num3 = 2.7;

      //浮点数使用陷阱：2.7 和 8.1/3 比较
      System.out.println("num1的值为" + num1);
      System.out.println("num2的值为" + num2);
      System.out.println("num3的值为" + num3);

      //运算结果是小数时，进行判断要小心：应该以两个数差值的绝对值，在某个精度范围内判断
      if(Math.abs(num1 - num2) < 0.00001){
         System.out.println("num1和num2差值非常小，到达规定精度，认为相等");
      }
      System.out.println(Math.abs(num1 - num2));

      //细节：如果是直接查询得到的小数或直接赋值，可直接判断相等
      if(num1 == num3){
         System.out.println("num1和num3相等");
      }
   }
}
```

说明：

* 浮点数通过二进制科学计数法表示，存在精度误差。例如十进制的`0.1`换算成二进制是一个无限循环小数，内存中存储的是近似值

  ```java
  double x = 1.0 / 10;      //0.1
  double y = 1 - 9.0 / 10;  //0.09999999999999998
  ```

  可以通过 `double r = Math.abs(x - y);` 获取绝对值再判断r是否小于某个很小的数判定两书比较结果

### 字符类型

字符类型的存储时，需要对应其码值，例如字符'a'

* 存储：'a'==>码值97==>十进制转二进制(110001)==>存储
* 读取：二进制(110001)==>转为十进制(97)==>'a'==>显示

编码格式：

* ASCII(ASCII编码表一个字节表示，一个128个字符；实际上一个字节可表示256个字符，仅使用128个)
* Unicode(固定大小的编码：使用两个字节来表示字符，字母和汉字统一都是占用两个字节，这样浪费空间；兼容ASCII）
* utf-8(大小可变的编码：字母使用1个字节，汉字使用3个字节；互联网使用最广)
* gbk(可以表示汉字，而且范围广，字母使用1个字节，汉字2个字节)
* gb2312(可以表示汉字，gb2312 < gbk)
* big5码(繁体中文，台湾，香港)

```java
class CharDetail{ //字符类型
   public static void main(String[] args){
      char c1 = 'a'; //字符常量只能用单引号括起来
      char c2 = '\t';//使用转义符的特殊字符型常量
      char c3 = '韩';
      char c4 = 97;//说明：unicode码为97的那个字符
      char c5 = 'b' +1; //赋值为b的unicode码+1后，对应的那个字符
      //在java中，char的本质是一个整数，在输出时，是unicode码对应的字符
      //char是一个可运算的整数，输出对应数字时可以"(int)字符"

      //快捷键Ctrl+Shift+D可快速复制粘贴光标所在行的命令
      System.out.println(c1+"的 unicode 对应数字为 "+ (int)c1);
      System.out.println(c2);
      System.out.println(c3);
      System.out.println('a' + 10); //输出的结果是计算后的数字
      System.out.println(c4);//查看Unicode对应编码http://tool.chinaz.com/Tools/Unicode.aspx
      System.out.println((int)(c5));//99
      System.out.println(c5);//c
   }
}
```

### 基本数据类型转换

自动类型转换：赋值和运算时，精度小的数据类型自动转换为精度大的

* char-->int-->long-->float-->double
* byte-->short-->int-->long-->float-->double

使用细节

1. 有多种类型的数据混合运算时，系统首先自动将所有数据转换成容量最大的那种数据类型，然后再进行计算
2. 当我们把精度（容量）大的数据类型赋值给精度（容量）小的数据类型时，就会报错，反之就会进行自动类型转换
3. (byte,short)和 char 之间不会相互自动转换，三者可转换为 int 型相互计算
4. boolean 不参与转换
5. 自动提升原则：表达式结果的类型自动提升为操作数中最大的类型

```java
class AutoConvert{
   public static void main(String[] args){
      //演示自动转换
      int num = 'a'; // char -> int
      double d1 = 80; // int -> double
      //一些会出现错误的赋值
      float d2 = num + 1.1f; //小数默认double型，若不加"f"，将出现错误
      byte c1 = 10;
      short c2 = 15;
      int c3 = c2;         //变量赋值需判断类型：不可大改小；(byte,short)和char之间不能自动转换
      int c4 = c2 - c1;     //short和byte相互计算后变成int型

      System.out.println(num);//97，结果为int型
      System.out.println(d1);//80.0，结果为double型
      System.out.println(d2);//98.1，结果为float型
      System.out.println(c3);//15，结果为int型
      System.out.println(c4);//5，结果为int型
   }
}
```

强制类型转换：将容量大的数据类型转换为容量小的数据类型，可能造成精度降低或溢出。使用时加上强制转换符

```java
class ForceConvert01{
      public static void main(String[] args){
      //演示强制类型转换
      int n1 = (int)1.9;
      System.out.println("n1 = " + n1);//精度损失，丢掉小数点后面的尾数

      int n2 = 2000;
      byte b1 = (byte)n2;
      System.out.println(b1);//数据溢出，超过byte数据范围

      //强转符号仅对于最近的操作数有效，若涉及计算需使用小括号提升优先级
      //int x = (int)10*3.5+6*1.5; //编译错误：double ->int
      int x = (int)(10*3.5+6*1.5);
      System.out.println(x); //double的44.0强制转为int的44

      //char类型可以保存int的常量值，但不能保存int的变量值，需要强转
      int m = 100;
      //char c2 = m; //错误，
      char c2 = (char)m;
      System.out.println(c2);//输出100对应的字符
   }
}
```

基本类型转String：基本类型的值+""；String类型转基本数据类型：通过基本类型的包装类调用parseXX方法即可



## var关键字

类型名称过长时，可以通过var关键字定义。类似JavaScript



参考资料：

[3.2  基本数据类型 - Hello 算法 (hello-algo.com)](https://www.hello-algo.com/chapter_data_structure/basic_data_types/)
