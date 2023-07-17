
/*
递归

简单的说：递归就是方法自己调用自己，每次调用时传入不同的变量，递归有助于编程者解决复杂问题，同时可以让代码变得简洁



●递归能解决什么问题？
1.各种数学问如：8皇后问题，汉诺塔，阶乘问题，迷宫问题，球和篮子的问题
2.各种算法中也会使用到递归，比如快排，归并排序，二分查找，分治算法等
3.将用栈解决的问题->递归代码比较简洁

每次递归都会进入完整的方法，方法走完才回到被调用的地方，所以一定会执行输出语句

方法执行完毕后就会返回，返回到调用方法的地方，然后继续执行后面的代码，直到main方法执行完毕，整个程序才退出
可以把栈看成一个羽毛球桶，方法为羽毛球，想想放和用的时候

递归重要规则
1.执行一个方法时，就创建一个新的受保护的独立空间（栈空间）
2.方法的局部变量是独立的，不会相互影响，比如n变量
3.如果方法中使用的是引用类型变量（比如数组，对象），就会共享该引用类型的数据，
4.递归必须向退出递归的条件逼近，否则就是无限递归，出现StackOverflowError：
5.当一个方法执行完毕，或者遇到return,就会返回，遵守谁调用，就将结果返回给谁，同时当方法执行完毕或者返回时，该方法也就执行完毕。
*/
public class Studynote14_Recursion {
	public static void main(String[] args){
		RecursionDemo test = new RecursionDemo();
		test.test(4);
		int res1 = test.factorial(3);//阶乘
		System.out.println("res = " + res1);

		RecursionMath exercise = new RecursionMath();
		int n = -1;
		int res2 = exercise.Fibonacci(n);
		if(res2 != -1){
			System.out.println("当n="+ n + "时对应的斐波那契数为" + res2);
		} else {
			System.out.println("此数无对应的斐波那契数");
		}
		int day = 5;
		int peachNum = exercise.peach(day);
		if(peachNum != -1){
			System.out.println("第"+ day + "天有" + peachNum + "个桃子");
		} 
	}
}

class RecursionDemo{
	//运行和打印方向相反
	public void test(int n) {
		if(n > 2) {
			test(n -1);
		}
	System.out.println("n="+n);
	}

	//阶乘
	public int factorial(int n) {
		if (n == 1) {
			return 1;
		} else {
			return factorial(n - 1) * n;
		}
	}
}

class RecursionMath{

	//使用递归的方式求出斐波那契数1,1,2,3,5,8,13...给你一个整数n,求出它的值是多
	//思路分析：当n=1斐波那契数是1，当n=2斐波那契数是1，n>=3斐波那契数是前两个数的和
	public int Fibonacci(int n){
		if(n >= 1) {
			if(n == 1 || n == 2) {
				return 1;
			}else{
				return Fibonacci(n-1) + Fibonacci(n-2);
			}
		} else{
			System.out.print("要求输入>=1的整数 ");
			return -1;//必须有return输出
		}
	}


	/*
	猴子吃桃子问题：有一堆桃子，猴子第一天吃了其中的一半，并再多吃了一个！以后
	每天猴子都吃其中的一半，然后再多吃一个。当到第10天时，想再吃时（即还没吃）
	发现只有1个桃子了。问题：最初共多少个桃子？

	思路分析：逆推
	第n-1天的桃子树= (第n天的桃子数+1)*2
	*/
	public int peach(int day) {
		if (day == 10) {
			return 1;
		} else if (day >= 1 && day <= 9){
			return (peach(day+1) +1) * 2;
		} else{
			System.out.println("day应在1-10之间");
			return -1;
		}
	}
}

