/*
运算符：一种特殊的符号，用以表示数据的运算、赋值和比较。
1.算术运算符
2.赋值运算符
3.关系运算符【比较运算符】
4.逻辑运算符
5.位运算符【需要二进制基础】
6.三元运算符
*/

/*
1.算术运算符：
加	+
减	-
乘	*
除	/
取余	%
自增	++
自减	--

*/

class ArithmeticOperator {
	public static void main(String[] args) {
		//除法运算符“/”
		System.out.println(10 / 4);//两个 int 计算后仍为整数型，因此输出2
		System.out.println(10.0 / 4);//double型得到2.5
		double d = 10 / 4;
		System.out.println(d);//输出得到2.0

		//取余“%”
		//计算公式为 a % b = a - a / b * b
		//取余得到数的正负号和被除数一致
		System.out.println(10 % 3); 	//1
		System.out.println(-10 % 3); 	//-1
		System.out.println(10 % -3); 	//1
		System.out.println(-10 % -3);	//-1

		//自增“++”
		int i = 10;
		//作为独立语句使用时，前++和后++都等价于 i = i + 1
		i++; //自增， => i = 11
		++i; //自增， => i = 12
		System.out.println("i = " + i); //12

		/*
		作为表达式使用
		前++：++i，先自增后赋值
		后++：i++，先赋值后自增
		*/
		int j = 8;
		int k = ++j; //等价 j = j + 1; k = j;
		System.out.println("j = " + j + " k = " + k); //j = 9, k = 9
		int l = k++; //等价 l = k, k = l + 1;
		System.out.println("k = " + k + " l = " + l); //k = 10, l = 9
	}
}

class ArithmeticOperatorExercise01 {
	public static void main(String[] args) {
		int i = 1;
		i = i++;//该规则使用临时变量，1) temp = i, 2) i = i + 1, 3) i = temp;
		System.out.println(i);//1

		int j = 1;
		j = ++j;//该规则使用临时变量，1) j = j + 1, 2) temp = j, 3) j = temp;
		System.out.println(j);//2
	}
}

class FahrtoCel {//华氏度转摄氏度
	public static void main(String[] args) {
		double fahrenheit = 234.5;
		//考虑数学公式和java语言的特性
		double calsius = 5.0 / 9 * (fahrenheit - 32); //计算结果为0.0，是因为前两个数都是int，需转换为double型
		System.out.println("华氏度 " + fahrenheit + " ℉ 对应的摄氏温度为 " + calsius " ℃");
	}
}





/*
2.关系运算符：
等于 	==
不等于	!=
小于 	<
大于 	>
小于等于	<=
大于等于	>=
检查是否是类的对象	instanceof

细节1：关系运算符结果都是boolean，仅可为true或false
细节2：区分关系运算符“==”和赋值“=”
*/

class RelationalOperator {
	public static void main(String[] args) {
		int a = 9;
		int b = 8;
		System.out.println(a > b);//true
		System.out.println(a >= b);//true
		System.out.println(a <= b);//false
		System.out.println(a < b);//false
		System.out.println(a == b);//false
		System.out.println(a != b);//true
		boolean flag = a > b;
		System.out.println("flag = " + flag);//true
	}
}

/*
3.逻辑运算符
连接多个关系表达式，最终结果为boolean

1.逻辑与“&”：两个同时为真，输出true，否则为false
2.短路与“&&”：两个同时为真，输出true，否则为false
3.逻辑或“|”：二者有一个为真，输出true，否则为false
4.短路或“||”：二者有一个为真，输出true，否则为false
5.取反“!a”：当a为true时输出false，a为false时输出true
6.逻辑异或“a^b”：当a和b不同时，输出true，否则为false
*/

class LogicOperator {
	public static void main(String[] args) {

		int a = 4;
		int b = 9;

		//短路与：若第一个条件false，后面条件不再执行，效率高
		if (a < 1 && ++b < 50){
			System.out.println("短路与成立");
		}
		System.out.println("短路与 a = " + a + " b = " + b);//未执行++b

		//逻辑与：会判断所有条件，执行判断语句
		if (a < 1 & ++b < 50){
			System.out.println("逻辑与成立");
		}
		System.out.println("逻辑与 a = " + a + " b = " + b);//执行++b

		//短路或：第一个条件true，则不判断第二个条件，效率高
		if (a > 1 || ++b > 4) {
			System.out.println("短路或成立");
		}
		System.out.println("短路或 a = " + a + " b = " + b);//未执行++b
		
		//逻辑或：会判断所有条件
		if (a > 1 | ++b > 4) {
			System.out.println("逻辑或成立");
		}
		System.out.println("逻辑或 a = " + a + " b = " + b);//执行++b

		//取反Inverse
		System.out.println(60 > 20);	//true
		System.out.println(!(60 > 20));	//false

		//逻辑异或a^b
		boolean c = (10 > 1) ^ (3 < 5);	//两个都为真
		System.out.println(c);			//false
	}

}

class LogicOperatorExercise01 {
	public static void main(String[] args) {

		//后++先判断自增，前++先自增再判断
		int x1 = 5, y1 = 5;		
		if(x1++ == 6 & ++y1 == 6) { //逻辑与，前false后true，整体为false
			x1 = 11;
		}
		System.out.println("x1 = " + x1 + " y1 = " + y1);	//6,6

		int x2 = 5, y2 = 5;
		if(x2++ == 6 && ++y2 == 6) { //短路与，前false后不判断
			x2 = 11;
		}
		System.out.println("x2 = " + x2 + " y2 = " + y2);	//6,5

		int x3 = 5, y3 = 5;
		if(x3++ == 5 | ++y3 == 5) { //逻辑或，前true后false，整体为true
			x3 = 11;
		}
		System.out.println("x3 = " + x3 + " y3 = " + y3);	//11,6

		int x4 = 5, y4 = 5;
		if(x4++ == 5 || ++y4 == 5) { //短路或，前true后不执行
			x4 = 11;
		}
		System.out.println("x4 = " + x4 + " y4 = " + y4);	//11,5
	}
}

class LogicOperatorExercise02{
	public static void main(String[] args) {

	boolean x = true;
	boolean y = false;
	short z = 46;
	if ((z++==46)&&(y=true)) z++;	//z判断为真且自增一次；y赋值为真，因此if成立，z变为48
	if((x=false)||(++z==49)) z++;	//x赋值为假，短路或继续执行；z自增一次，if成立，z变为50
	System.out.println("z =" + z);	//输出结果为50
	}
}


/*3.赋值运算符：
赋值运算符左边只能是变量，右边可以是变量、表达式、常量值
复合赋值运算符：a += 3; 等价于a = a + 3;
-------------
复合赋值运算会默认强制类型转换
*/

class AssignOperator {
	public static void main(String[] args) {
		int n1 = 10;
		n1 += 4;
		System.out.println(n1);//14
		n1 /= 3;
		System.out.println(n1);//4	

		//复合赋值运算会默认强制类型转换
		byte b = 125;
		b += 2;	//等价于 b = (byte)(b + 2); 
		System.out.println(b);	//到达byte类型最大值127
		b++;	//等价于 b = (byte)(b + 1); 
		System.out.println(b);	//超出byte类型最大值，输出为-128
}

/*
4.三元运算符：
基本语法 条件表达式 ? 表达式1 : 表达式2
1.如果条件表达式为 true , 运算后的结果是表达式 1 ;
2.如果条件表达式为 false , 运算后的结果是表达式 2 ;

表达式1和表达式2要为可以赋给接收变量的类型（或可以自动转换/或在代码中强制转换）
*/
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


/*
运算符优先级
只有单目运算符、赋值运算符从右向左运算
1）(), {}, int a=5,b=3;
2）单目运算：++, --,!（取反）
3）算术运算符
4）位移运算符
5）比较运算符
6）逻辑运算符
7）三元运算符
8）赋值运算符

*/