import java.lang.Math;
import java.util.Scanner;

//输出1~100之间的不能被5整除的数，每5个一行
class Nonefive{
	public static void main(String[] args){
		int count = 0; //统计输出的个数
		for(int i =1; i<=100; i++){
			if(i % 5 != 0){
				System.out.print(i+"\t");
				count++;
				
			//每满5个数字，输出一个换行
			if(count % 5 ==0){
				System.out.println();
				}
			}
		}
	}
}

/*
打印空心金字塔
    *		//第1层 1个* 4个空格
   * *		//第2层
  *   *		//第3层
 *     *	//第4层
*********	//第5层

*/


class PrintPyramid{//打印空心金字塔

	public static void main(String[] args){
		int layer = 5;
		for(int i =1; i <= layer; i++){//i表示层数
			//在输出*前，需要输出对应空格，数量为=总层数-当前层数
			for(int k = 1; k <= layer-i; k++){
				System.out.print(" ");
			}
			//控制每层打印的*数
			//每行的第一个，最后一个，以及最后一行全是*
			for(int j = 1;j <= 2* i - 1;j++){
				//
				if (j == 1 || j ==2 * i - 1 || i ==layer){
					System.out.print("*");
				} else{
				System.out.print(" ");}
			}
			System.out.println();
		}
	}
}

class PrintDiamond{//打印菱形
	public static void main(String[] args){
		int layer = 4;
		for(int i =1; i <= layer; i++){//i表示层数
			//在输出*前，需要输出对应空格，数量为=总层数-当前层数
			for(int k = 1; k <= layer-i; k++){
				System.out.print(" ");
			}
			//控制每层打印的*数
			//每行的第一个，最后一个，以及最后一行全是*
			for(int j = 1;j <= 2* i - 1;j++){
				//
				if (j == 1 || j ==2 * i - 1){//空心金字塔最下面一层中间用空格填充
					System.out.print("*");
				} else{
				System.out.print(" ");}
			}
			System.out.println();
		}
		//打印菱形下半部分
		for(int m = 1; m <= layer-1; m++){
			//输出空格
			for(int n = 1; n <= m ; n++){
				System.out.print(" ");
			}
			for(int o = 2*(layer-m)-1;o >= 1; o--){
				if(o ==1|o ==2*(layer-m)-1){System.out.print("*");}
				else{System.out.print(" ");}
			}
			//for int(k = 1,k < 
		System.out.println();
		}
	}
}


//实现登录验证，有3次机会，如果用户名为”丁真”，密码”666”提示登录成功

//思路分析
//1.创建Scanner对象接收用户输入
//2.定义String name;String passwd;保存用户名和密码
//3.定义一般变量int chance记录还有几次登录机会
//4.最多循环3次[登录3次]，如果满足条件就提前退出
class BreakExercise{
	public static void main(String[] args){
		Scanner myScanner = new Scanner(System.in);
		String name = "";
		String passwd = "";
		int chance = 3;//登陆一次，减少一次
		
		for(int i = 1; i<=3; i++){
			System.out.println("请输入用户名");
			name = myScanner.next();
			System.out.println("请输入密码");
			passwd = myScanner.next();
			//比较输入的名字和密码是否正确
			
			//比较字符串的内容时，使用equals方法，可用下面两种输入方式
			//推荐采用第二种方法，可以避免空指针
			if(name.equals("丁真") && "666".equals(passwd)){
				System.out.println("登陆成功");
				break;
			}

			//输入信息错误，减少一次登录机会
			chance--;
			System.out.println("登录信息错误，您还剩"+ chance+"次机会");
		}
		System.out.println("代码执行完毕");
	}
}




//问题：随机生成1~100的一个数，直到生成了97，计算一共用了几次？

//方法一：使用do-while循环
class RandomNumber01{
	public static void main(String[] args){
		int i = 0;
		boolean b = true;
		do{
			int a = (int)(Math.random()*100) + 1;
			i++;
			System.out.println(a);//可不显示
			if(a ==97){
				b=false;
			}
		}while(b);
		System.out.println("总共花费次数"+ i);
	}
}




/*
编程实现如下功能
某人有100,000元，每经过一次路口，需要交费，规则如下：
1)当现金>50000时，每次交5%
2)当现金<=50000时，每次交1000
编程计算该人可以经过多少次路口，要求：使用while break方式完成
*/
//方法1
class TollStation{
	public static void main(String[] args){
		double money = 100000;
		int count = 0;
		while((int)(money / 1000) != 0){
			if(money >50000){
				money *=0.95;
				count++;
				//System.out.println("i = " +count+"剩余金额："+ money);
			}else {money -= 1000;
			count++;
			//System.out.println("i = " +count+"剩余金额："+ money);
			}
		}
		System.out.println("总共经过次数为：" + count+" ，此时剩余金额为："+money);
	}
}

//方法二：while(true),判断else if(money>=1000)，在else时break

//用两种方法求1+(1+2)+(1+2+3)+(1+2+3+4)+…+(1+2+3+.+100)的结果

//方法1
class LoopSum01{
	public static void main(String[] args){
		int sum1 = 0;
		int sum2 = 0;
		for(int i =1;i<=100;i++){
			sum1 += i;
			sum2 += sum1;
		}
		System.out.println("sum = " +sum2);
	}
}

//方法2
class LoopSum02{
	public static void main(String[] args){
		int sum = 0;
		for(int i = 1;i <= 100;i++){
			for(int j = 1;j <= i; j++){//内层对1~i进行循环
			sum += j;
			}
		}
		System.out.println("sum =" +sum);
	}
}
