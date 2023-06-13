/*
1.顺序控制

2.分支控制if else switch：
如果if{}只有一条语句，可以不用{}
多分支可以没有else，如果所有条件表达式都不成立，则没有执行入口
如果有else，如果所有条件表达式都不成立，则执行else代码块

3.循环控制for while dowhile 多重循环
4.break
5.continue
6.return
*/
import java.util.Scanner;
class LeapYear{	//判断一个年份是否是闰年
	public static void main(String[] args){
		Scanner myScanner = new Scanner(System.in);
		
		//闰年的条件是符合下面二者之一：
		//(1)年份能被4整除，但不能被100整除；(2)能被400整除
		System.out.println("请输入年份");
		int year = myScanner.nextInt();
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			System.out.println(year + "年是闰年");}
		else {System.out.println(year + "年不是闰年");}
		System.out.println("程序继续...");
		}
}

//多分支的判断
class If01{
	public static void main(String[] args){
		boolean b = true;
		if (b = false) //此处对b赋值为false，if也将是false
			System.out.println("a");
		else if(b)		
			System.out.println("b");
		else if(!b)		//正确，输出此条
			System.out.println("c");
		else
			System.out.println("d");
		}
}

/*swich分支

switch(表达式){
	case 常量1:
		语句块1;
		break;
	...
	default:
		default语句块;
		break;
}

1.switch关键字，表标swtich分支结构
2.表达式对应一个值
3.case常量1：当表达式的值等于常量1，就执行语句块1
4.break:表示退出swtich（穿透：执行完后如果没有break，将继续执行下一段语句块）
5.如果和case常量1匹配，就执行语句块1，如果没有匹配，就继续匹配case常量2
6.如果一个都没有匹配上，执行default


*/

import java.util.Scanner;
class SwitchExercise{ //小写转大写
	public static void main(String[] args){
		Scanner myScanner = new Scanner(System.in);
		System.out.println("请输入一个字符(a-g)");
		char c1 = myScanner.next().charAt(0);//把字符串转换成字符
		switch(c1){//在java中，只要有值返回，都可看作一个表达式
			case 97 ://char和int可自动转换
				System.out.println("A");
				break;
			case 'b' :
				System.out.println("B");
				break;
			case 'b' + 1://可以是常量或表达式
				System.out.println("C");
				break;
			case 'd' :
				System.out.println("D");
				break;
			case 'e' :
				System.out.println("E");
				break;
			case 'f' :
				System.out.println("F");
				break;
			case 'g' :
				System.out.println("G");
				break;
			default:
				System.out.println("输入字符不正确，无法匹配");
			
		}
		System.out.println("退出了switch，继续执行程序");
	}
}

/*switch注意事项
细节1.表达式数据类型，应和case后的常量类型一致，或者是可以自动转成可以相互比较的类型，比如输入的是字符，而常量是int
细节2.switch（表达式）中表达式的返回值必须是：(byte,short,int,char,enum[枚举],String)
细节3.case子句中的值必须是常量或表达式，不能是变量
细节4.default子句是可选的，当设有匹配的case时，执行default
细节5.break语句用来在执行完一个case分支后使程序跳出switch语句块；
如果没有写break,程序会顺序执行到switch结尾，除非遇到break
*/

//根据指定的月份，判断所属的季节，使用穿透
import java.util.Scanner;
class Season{
	public static void main(String[] args){
		Scanner myScanner = new Scanner(System.in);

		//3,4,5春季6,7,8夏季9,10,11秋季12,1,2冬季
		System.out.println("请输入月份（1-12）");
		int month = myScanner.nextInt();
		if(month>=1 && month <= 12){
			switch((int)(month/3)){
				case 1:
					System.out.println("这是春季");
					break;
				case 2:
					System.out.println("这是夏季");
					break;
				case 3:
					System.out.println("这是秋季");
				case 4://统一输出，直接穿透，可不写内容
				default:
					System.out.println("这是冬季");
				}
		}
		else{
			System.out.println("月份应在1-12之间");
		}
	}
}

/*
switch和if的比较
1.如果判断的具体数值不多，而且符合byte、short、int、char,enum,String这6种类型。虽然两个语句都可以使用，建议使用swtich语句。
2.其他情况：对区间判断，对结果为boolean类型判断，使用if,if的使用范围更广
*/

/*
for循环控制
for(循环变量初始化;循环条件;循环变量迭代){
	循环操作（语句）;
	}

说明：
1.for关键字，表示循环控制
2.fo有四要素：(1)循环变量初始化(2)循环条件(3)循环操作(4)循环变量迭代
3.循环操作，这里可以有多条语句，也就是我们要循环执行的代码
4.如果循环操作（语句）只有一条语句，可以省略0，建议不要省略
*/

class MultiplicationTable{	//生成九九乘法表
	public static void main(String[] args){
		for(int i =1; i<10;i++){
			for(int j = 1;j <=i;j++){
				System.out.print(i +"*"+ j + "=" +(i*j) +"\t");
			}
			System.out.println();//自动换行
		}
	}
}
/*
注意事项
1.循环条件需得到布尔值
2.for循环的初始化和变量迭代可以写到其他地方(用于扩展变量的作用域)，但是两边的分号不能省略

for(;;) System.out.println("ok~");//表示一个无限循环，使用Ctrl+C方可退出

3.循环初始值可以有多条初始化语句，但要求类型一样，中间用逗号隔开；
循环变量迭代可以用多条变量迭代语句，中间用逗号隔开



*/

class ForExerciese{//打印1~180之间所有是9的倍数的整数，统计个数及总和

	public static void main(String[] args){
		int sum = 0;
		int count = 0;
		int start = 1;
		int end = 100;
		int t = 9;//被除数
		for (int i = start; i <= end; i++){
			if (i % t == 0){
				sum += i;
				count++;
				System.out.println(i);
			}
		}
		System.out.println(t+"的倍数共有 " + count +" 个，总和为 " + sum);
	}
}

/*
编程思想
1.化繁为简：将复杂需求拆解为简单需求，逐步完成
(1)完成输出1-100的值
(2)在输出的过程中，进行过滤，只输出9的倍数 i % 9 == 0
(3)统计个数 定义一个变量 int count=0; 当条件满足时 count++;
(4)总和，定义一个变量 int sum=0; 当条件满足时累积 sum+=i;

2.先死后活：先考虑固定的值，然后转成可以灵活变化的值
(1)为了适应更好的需求，把范围的开始的值和结束的值，做出变量
(2)还可以更进一步 9 倍数也做成变量 int t=9;
*/

/*
while循环

while(循环条件){
	循环体(语句);
	循环变量迭代;
}

注意事项
1.循环条件为返回布尔值的表达式
2.while循环线判断再执行语句

*/

/*
do while循环

do{
	循环体(语句);
	循环变量迭代;
}while(循环条件);

说明：
1.do while是关键字
1.也有循环四要素，只是位置不一样
2.先执行，再判断，也就是说，至少会执行一次
3.最后有一个分号；
4.while和do.while区别举例：要账
*/

class DoWhile{ //统计1-200间能被5整除但不能被3整除的个数
	public static void main(String[] args){
		int i = 1;
		int count = 0;
		do {
			if(i%5 == 0&& i%3 !=0) count++;
			i++;
		}while(i<=200);
		System.out.println("1-200间能被5整除但不能被3整除的个数为 "+ count);
	}
}

/*
跳转控制语句break

注意事项：
1.break语句出现在多层嵌套的语句块中时，可以通过标签指明要终止的是哪一层语句块
2.标签的使用
(1)break语句可以指定退出哪层
(2)label1是标签，由程序员指定。
(3)break后指定到哪个label就退出到哪里
(4)在实际的开发中，尽量不要使用标签。----->可读性变差
(5)如果没有指定break,默认退出最近的循环体


跳转控制语句continue
注意事项
1)continue语句用于结束本次循环，继续执行下一次循环。
2)continuej语句出现在多层嵌套的循环语句体中时，可以通过标签指明要跳过的是哪一层循环，和break规则一样

break结束指定方法，continue执行下一次循环,return退出所有方法

跳转控制语句return
说明：表示跳出所在的方法
注意：如果return写在main方法，退出程序。
*/

class Break01{
	public static void main(String[] args){
		label1:
		for(int j = 0; j < 4; j++){//外层for
		label2:
			for(int i = 0; i < 10; i++){//内层for
				if(i==2){
					break;//等价于break label2;
					//break label1;//
				}
			System.out.println("i = " + i);
			}
		}	
	}
}

class Break02{
	public static void main(String[] args){
		int num = 1;
		while(num < 10){
			System.out.println(num);
			if(num > 5){
				break;//先输出再break，因此会输出数字7
			}
			num += 2);
		}
	}
}

class Continue01{
	public static void main(String[] args){
		int i = 1;
		while(i <= 4){
			i++;
			if(i ==2){
				continue;
			}
			System.out.println("i = " + i );//输出3,4,5
		}
	}
}


class Continue02{
	public static void main(String[] args){
		label1:
		for(int j = 0; j < 4; j++){//外层for
		label2:
			for(int i = 0; i < 10; i++){//内层for
				if(i==2){
					continue;//等价于continue label2;
					//continue label1;//
				}
			System.out.println("i = " + i);
			}
		}	
	}
}

class Return01{
	public static void main(String[] args){
		for(int i = 1; i <= 5; i++){
			if(i==3){
				System.out.println("i = " + i);
				return;//
				//break;
				//continue;
			}
			System.out.println("Hello World!");
		}
		System.out.println("go on...");
	}
}
