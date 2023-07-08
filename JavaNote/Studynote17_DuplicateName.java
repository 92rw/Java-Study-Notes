
/*
方法重载Overload
●基本介绍
jva中允许同一个类中，多个同名方法的存在，但要求形参列表不一致！
t比如：System.out.println();
out是PrintStream类型

●重载的好处
1)减轻了起名的麻烦
2)减轻了记名的麻烦

●注意事项和使用细节
1)方法名：必须相同
2)形参列表：必须不同(参数类型或个数或顺序，至少有一样不同，参数名无要求)->错误提示：方法的重复定义
3)返回类型：无要求
*/




/*
案例：在Methods类，定义三个重载方法max(),第一个方法，返回两个int值中的最大值，
第二个方法，返回两个double值中的最大值，第三个方法，返回三个double值中的最大值，
并分别调用三个方法，
*/
class Overload {
	public static void main(String[] args){
		Method01 test = new Method01();
		System.out.println(test.max(3,5,8));//此处运行最后一段重载max()，原因是输入的值为int
	}
}

class Method01 {

	//下面的三个方法构成了重载，是合法的

	//返回两个int值中的最大值
	public int max(int n1, int n2) {
		System.out.println("max(int n1, int n2)被调用");
			return n1 > n2 ? n1 : n2;//简洁的语法表达：三元运算符
	}

	//返回两个double值中的最大值
	public double max(double n1, double n2) {
		System.out.println("max(double n1, double n2)被调用");
		if(n1 > n2) return n1;
		else return n2;
	}


	//返回三个double值中的最大值
	public double max(double n1, double n2, double n3) {
		System.out.println("max(double n1, double n2, double n3)被调用");
		double max1 = n1 > n2 ? n1 : n2;
		return max1 > n3 ? max1 : n3;
	}


	//由于java先编译再运行，因此编译时会选择更合适的进行比较
		public double max(double n1, double n2, int n3) {
		System.out.println("max(double n1, double n2, int n3)被调用");
		double max1 = n1 > n2 ? n1 : n2;
		return max1 > n3 ? max1 : n3;
	}
}

/*
可变参数Variable Parameter

●基本概念
java允许将同一个类中多个同名同功能但参数个数不同的方法，封装成一个方法。

●基本语法
访问修饰符 返回类型 方法名（数据类型...形参名）{
}

●注意事项
1)可变参数的实参可以为0个或任意多个。
2)可变参数的实参可以为数组。
3)可变参数的本质就是数组。
4)可变参改可以和普通类型的参数一起放在形参列表，但必须保证可变参数在最后
5)一个形参列表中只能出现一个可变参数

*/

//案例：多个整数求和
class VarParameter{
	public static void main(String[] args){
		Method02 test = new Method02();
		int sum = test.sum(1,5,10,10,5,1);
		System.out.println("和为" + sum);

		//细节：可变参数的实参可以是数组
		System.out.println("====演示数组====");
		int[] arr = {1,4,6,4,1};
		int arraySum = test.sum(arr);
		System.out.println("和为" + arraySum);

		//细节：可变参数的实参可以是数组
		System.out.println("====演示成绩计算====");
		System.out.println(test.showScore("saiko",65,78));
	}
}


class Method02 {
	//int...表示接收多个可变参数int
	//使用可变参数时，可以当做数组来使用
	public int sum(int... nums){
		System.out.println("参数的长度为" + nums.length);
		int res = 0;
		for(int i = 0; i < nums.length; i++){
			res += nums[i];
		}
		return res;
	}

	//细节：可变参改可以和普通类型的参数一起放在形参列表，但必须保证可变参数在最后
	public String showScore(String name, double... scores){
		double totalScore = 0;
		for(int i = 0; i < scores.length; i++){
			totalScore += scores[i];
		}
		return name + " 有 " + scores.length + " 门课的成绩为 " + totalScore; 
	}
}
