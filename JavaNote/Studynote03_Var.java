//快捷键：Ctrl+/可以一键多行注释，选中后再次输入可取消
//变量=变量名+值+数据类型（三要素）
import java.lang.Math;
class Variation{
	public static void main(String[] args){
		
		//声明变量
		int a;
		a = 100;
		System.out.println(a);

		//变量必须先声明，后使用
		//变量在同一作用域内不得重名，其数据/值可在同一类型范围内变化

		int age = 20;
		double score = 88.9;		//小数
		char gender = '男';			//字符
		String name = "king";		//字符串
		//输出信息，快捷键
		System.out.println("人的信息如下：");
		System.out.println(age);
		System.out.println(score);
		System.out.println(gender);
		System.out.println(name);
	}
}


class Plus01{ //程序中+号的使用
	public static void main(String[] args) {

		//当左右两边都是数值型时，做加法运算
		System.out.println(100 + 3 + "hello"); //103hello

		//当左右两边有一方为字符串，则做拼接运算
		System.out.println("hello" + 100 + 3); //hello1003
	}
}

/*
数据类型

基本数据类型：
整数型 byte[1], short[2], int[4], long[8]	二进制影响其范围
浮点型 float[4], double[8]
字符型 char[2]
布尔型 boolean[1]

引用数据类型：
类 class 		字符串String属于此类型
接口 interface
数组 []

*/


//Java的整型常量（具体值）默认为int型，声明long型常量须后加'l'或'L'
//声明long型常量需后加"l"或"L"，以保证在我们声明的数据大小超过int范围时自动转换为long型数据，然后再赋值给long型变量，数据不溢出。
//bit:计算机中的最小存储单位，byte:计算机中基本存储单元, 1 byte = 8 bit

//浮点数 = 符号位 + 指数位 + 尾数位
//小数都是近似值，因为尾数部分可能丢失，造成精度损失
//浮点型常量默认为double型，声明float型常量时需后加'f'或"F"
//double型在赋值时可表示为“0.0d”的形式
//通常情况下，应该使用double型，因为它比float型更精确
/*
浮点型有两种表示形式
十进制形式：5.12		512.0f		.512（必须有小数点）
科学计数法：5.12e2[5.12*10的2次方 , 512.0]	5.12E-2[5.12*10的-2次方 , 0.0512]
*/

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


class Char01{ //字符类型
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

/*
字符类型的存储时，需要对应其码值，例如字符'a'
存储：'a'==>码值97==>十进制转二进制(110001)==>存储
读取：二进制(110001)==>转为十进制(97)==>'a'==>显示

ASCII(ASCII编码表一个字节表示，一个128个字符；实际上一个字节可表示256个字符，仅使用128个)
Unicode(固定大小的编码：使用两个字节来表示字符，字母和汉字统一都是占用两个字节，这样浪费空间；兼容ASCII）
utf-8(大小可变的编码：字母使用1个字节，汉字使用3个字节；互联网使用最广)
gbk(可以表示汉字，而且范围广，字母使用1个字节，汉字2个字节)
gb2312(可以表示汉字，gb2312 < gbk)
big5码(繁体中文，台湾，香港)

*/


class Boolean01{
	public static void main(String[] args){
		//演示判断成绩是否通过的案例
		//定义一个布尔变量
		boolean isPass = true;
		//不可以0或非0的整数替代true和false，和C语言不同
		if(isPass == true){
			System.out.println("考试通过，恭喜");
		} else {
			System.out.println("考试未通过，下次努力");
		}
	}
}

/*基本数据类型转换
自动类型转换：赋值和运算时，精度小的数据类型自动转换为精度大的
char-->int-->long-->float-->double
byte-->short-->int-->long-->float-->double

细节1：有多种类型的数据混合运算时，系统首先自动将所有数据转换成容量最大的那种数据类型，然后再进行计算
细节2：当我们把精度（容量）大的数据类型赋值给精度（容量）小的数据类型时，就会报错，反之就会进行自动类型转换。
细节3：(byte,short)和 char 之间不会相互自动转换，三者可转换为 int 型相互计算
细节4：boolean 不参与转换
细节5：自动提升原则：表达式结果的类型自动提升为操作数中最大的类型

*/

class AutoConvert{
	public static void main(String[] args){
		//演示自动转换
		int num = 'a'; // char -> int
		double d1 = 80; // int -> double
		//一些会出现错误的赋值
		float d2 = num + 1.1f; //小数默认double型，若不加"f"，将出现错误
		byte c1 = 10;
		short c2 = 15;
		int c3 = c2;			//变量赋值需判断类型：不可大改小；(byte,short)和char之间不能自动转换
		int c4 = c2 - c1; 		//short和byte相互计算后变成int型

		System.out.println(num);//97，结果为int型
		System.out.println(d1);//80.0，结果为double型
		System.out.println(d2);//98.1，结果为float型
		System.out.println(c3);//15，结果为int型
		System.out.println(c4);//5，结果为int型
	}
}


//强制类型转换：将容量大的数据类型转换为容量小的数据类型，可能造成精度降低或溢出。使用时加上强制转换符
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



class ForceConvert02{	//此处需确保String类型能转成有效的数据
	public static void main(String[] args){

		//基本类型转String：基本类型的值+""
		int n1= 101;
		float n2 = 1.2F;
		double n3 = 3.1;
		boolean n4 = true;
		String c1 = n1 + "";
		String c2 = n2 + "";
		String c3 = n3 + "";
		String c4 = n4 + "";
		System.out.println(c1+"\t"+c2+"\t"+c3+"\t"+c4);

		//String类型转基本数据类型：通过基本类型的包装类调用parseXX方法即可
		String a2 = "123";//String的内容需用双引号

		//使用 基本数据类型对应的包装类，得到基本数据类型
		int s1 = Integer.parseInt(a2);//字符串解析为十进制整数
		double s2 = Double.parseDouble(a2);
		float s3 = Float.parseFloat(a2);
		long s4 = Long.parseLong(a2);
		byte s5 = Byte.parseByte(a2);
		Short s6 = Short.parseShort(a2);
		System.out.println(s1+"\t"+s2+"\t"+s3+"\t"+s4+"\t"+s5+"\t"+s6);

		//布尔型转换
		boolean b = Boolean.parseBoolean("false");
		System.out.println(b);

		//字符串转成字符char -> 得到字符串的第一个字符
		System.out.println(a2.charAt(0));//得到字符串a2的第一个字符'1'
	}
}

class Plus02{ //运算符和变量的计算
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

//字符型的计算
//输出小写的a-z以及大写的Z-A
class Alphabet{
	public static void main(String[] args){
		for(char c1 = 'a'; c1 <= 'z'; c1++){
			System.out.print(c1 + " ");
		}
		System.out.println();
		for(char c2 = 'Z'; c2 >= 'A'; c2--){
			System.out.print(c2 + " ");
		}
		System.out.println();
	}
}

//求出1-1/2+1/3-1/4..1/100的和
class NumberSequence {
	public static void main(String[] args){
		double sum = 0.0;
			for(int i =1;i <= 100 ; i++){
				if (i % 2 != 0){
					sum += 1.0/i;//注意此处需为double型，否则会因整数相除无法得出小数
				}else{
					sum -= 1.0/i;
				}
			}
		System.out.println("sum = " +sum);
	}
}
