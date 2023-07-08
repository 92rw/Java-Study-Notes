/*
进制

对于整数，有四种表示方式
1.二进制：0,1，满2进1。以0b或0B开头。
2.十进制：0-9，满10进1。
3.八进制：0-7，满8进1.以数字0开头表示。
4.十六进制：0-9及A(10)-F(15),满16进1.以0x或0X开头表示。此处的A-F不区分大小写。

*/
class Numeral{
	public static void main(String[] args){
		int n1 = 0b1010;	//二进制BIN
		int n2 = 1010;		//十进制DEC
		int n3 = 01010;		//八进制OCT
		int n4 = 0x10101;	//十六进制HEX
		System.out.println("n1=" + n1);
		System.out.println("n2=" + n2);
		System.out.println("n3=" + n3);
		System.out.println("n4=" + n4);
		}
}

//进制转换
//转十进制（DEC）
//二进制（BIN）转十进制：从最低位（右边）开始，将每个位上的数提取出来，乘以2的（位数-1）次方并求和。例：0b1011 = 1*1+1*2+0*4+1*8=11
//八进制（OCT）转十进制：从最低位（右边）开始，将每个位上的数提取出来，乘以8的（位数-1）次方并求和。例：0234=4*1+3*8+2*64=156
//十六进制（HEX）转十进制：从最低位（右边）开始，将每个位上的数提取出来，乘以16的（位数-1）次方并求和。例：0x23A=10*1+3*16+2*256=570

//十进制转二进制：将该数不断除以2，直到商为0，然后将每步得到的余数倒过来，就是对应的二进制。例如34:0b00100010（一个字节有8位，需要用两个0填充高位）
//十进制转八进制：将该数不断除以8，直到商为0，然后将每步得到的余数倒过来，就是对应的八进制。例如131:0203
//十进制转十六进制：将该数不断除以16，直到商为0，然后将每步得到的余数倒过来，就是对应的十六进制。例如237:0xED

//二进制转八进制：从低位开始，将二进制每三位一组，转成对应的八进制。例如0b11010101：0325
//二进制转十六进制：从低位开始，将二进制每四位一组，转成对应的十六进制。例如0b11010101：0xD5

//八进制转二进制：将八进制数每1位，转成对应的3位二进制数。例如0237=0b10011111（最左边的0可省略）
//十六进制转二进制：将十六进制数每1位，转成对应的3位二进制数。例如0x23B=0b001000111011

/*
原码、反码、补码
反码的英文叫radix-minus-one，也就是基础减一
计算机使用补码运算的原因：将正数和负数统一起来

1.二进制的最高位是符号位：0表示正数，1表示负数(口诀：0->0 1->-)
2.正数的原码，反码，补码都一样（三码合一）
3.负数的反码=它的原码符号位不变，其它位取反(0->1,1->0)
4.负数的补码=它的反码+1，负数的反码=负数的补码-1
5.0的反码，补码都是0
6.java没有无符号数，换言之，java中的数都是有符号的
7.在计算机运算的时候，都是以补码的方式来运算的。
8.当我们看运算结果的时候，要看他的原码


*/

/*
按位与“&”：两位全1，结果为1，否则为0
按位或“|”：有1则1，否则为0
按位异或“^”:不同为1，相同为0
按位取反“~”：相反数
*/

class BitOperation01{	//位运算
	public static void main(String[] args){
		int a = 2 & 3;		//按位与：两位全1，结果为1，否则为0
		//基本原理：原码以二进制表示，int为4个字节，正数原码和补码相同，因此通过计算得到：
		//2的原码/补码00000000 00000000 00000000 00000010
		//3的原码/补码00000000 00000000 00000000 00000011
		//通过补码计算得到按位与:00000000 00000000 00000000 00000010（运算后得到的补码）
		//运算后的补码是正数，补码和原码相同
		//转成十进制，得到2
		
		int b = 5 | 4;		//按位或：有1则1，否则为0
		//5的原码/补码 		00000000 00000000 00000000 00000101
		//4的原码/补码 		00000000 00000000 00000000 00000100
		//运算得到原码/补码	00000000 00000000 00000000 00000101
		//转成十进制，得到5

		int c = -3 ^ 3;		//按位异或：不同为1，相同为0
		//-3的原码 10000000 00000000 00000000 00000011
		//负数的反码=它的原码符号位不变，其它位取反
		//-3的反码 11111111 11111111 11111111 11111100
		//负数的补码=它的反码+1
		//-3的补码 11111111 11111111 11111111 11111101
		//3的补码  00000000 00000000 00000000 00000011
		//运算后补码11111111 11111111 11111111 11111110
		//负数的反码=负数的补码-1
		//运算后反码11111111 11111111 11111111 11111101
		//负数的原码=符号位不变，其他取反
		//运算后原码10000000 00000000 00000000 00000010
		//转十进制得到-2

		int d = ~ -2;		//按位取反
		//负数的原码第1位是1，其余和原码相同
		//-2的原码 10000000 00000000 00000000 00000010
		//(符号不变，其余取反)
		//-2的反码 11111111 11111111 11111111 11111101
		//（反码+1）
		//-2的补码 11111111 11111111 11111111 11111110
		//按位取反 00000000 00000000 00000000 00000001
		//运算后得到的补码是正数，对应的原码转换十进制为1

		int e = ~ 5;		//按位取反
		//得到5的原码，即补码		00000000 00000000 00000000 00000101
		//按位取反运算得到补码	11111111 11111111 11111111 11111010
		//（补码-1）
		//运算后的反码为			11111111 11111111 11111111 11111001
		//运算后的原码为			10000000 00000000 00000000 00000110
		//运算后原码转十进制为-6

		System.out.println("a=" + a);//2
		System.out.println("b=" + b);//5
		System.out.println("c=" + c);//-2
		System.out.println("d=" + d);//1
		System.out.println("e=" + e);//-6
		}
}

/*
算术右移“>>”：低位溢出，符号位不变，用符号位填补溢出的高位
算数左移“<<”：符号位不变，低位补0
逻辑右移“>>>”：低位溢出，高位补0
注意：没有“<<<”符号
*/

class BitOperation02{	//位运算
	public static void main(String[] args){
		int a = 1 >> 2;	//1算数右移2位，相当于运行两遍除以2
		//1的数字为0000000 00000000 00000000 00000001
		//最右边两位溢出并补0，得到的补码转换为十进制为0
		int b = -1 >> 2;
		//-1的原码	10000000 00000000 00000000 00000001
		//转反码		11111111 11111111 11111111 11111110
		//转补码+1	11111111 11111111 11111111 11111111
		//位运算得到 11111111 11111111 11111111 11111111
		//转补码-1	11111111 11111111 11111111 11111110
		//转反码		10000000 00000000 00000000 00000001
		//转十进制-1
		int c = 1 << 2;		//1算数左移两位，相当于运行两遍乘2
		//低位向左移动得到补码0000000 00000000 00000000 00000100
		//补码转换为十进制得到4
		int d = -1 << 2;
		//-1的原码	10000000 00000000 00000000 00000001
		//转反码		11111111 11111111 11111111 11111110
		//转补码+1	11111111 11111111 11111111 11111111
		//位运算得到 11111111 11111111 11111111 11111100
		//转补码-1	11111111 11111111 11111111 11111011
		//转反码		10000000 00000000 00000000 00000100
		//转十进制为4
		int e = 3 >>> 2;		//无符号右移
		//低位溢出，高位补0，转十进制得到0
		System.out.println("a=" + a);//0
		System.out.println("b=" + b);//-1
		System.out.println("c=" + c);//4
		System.out.println("d=" + d);//-1
		System.out.println("e=" + e);//0
		}
}