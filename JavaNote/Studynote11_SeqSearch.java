/*
在java中，我们常用的查找有两种：
1.顺序查找
2.二分查找[二分法，我们放在算法讲解]

案例演示：
1.有一个数列：孙行者、行者孙、者行孙猜数游戏：从键盘
中任意输入一个名称，判断数列中是否包含此名称【顺序查找】要求：如果找
到了，就提示找到，并给出下标值。
2.请对一个有序数组进行二分查找{1,8,10,89,1000,1234}，输入一个数看
看该数组是否存在此数，并且求出下标，如果没有就提示”没有这个数”。



思路分析
1,定义一个字符串数组
2,接收用户输入，遍历数组，逐一比较，如果有，则提示信息，并退出


*/
import java.util.Scanner;
public class SequenceSearch{
	public static void main(String[] args){
		String[] names = {"孙行者", "行者孙", "者行孙"};
		//接收用户输入
		Scanner myScanner = new Scanner(System.in);
		System.out.println("请输入名字");
		String findName = myScanner.next();

		int index = -1; //用于判断下面的代码块是否执行

		//遍历数组，逐一比较，如果有，则提示信息，并退出
		for(int i = 0; i< names.length; i++){
			//字符串比较实用equals，如果要找到名字就是当前元素
			if(findName.equals(names[i])){
				System.out.println("恭喜你找到 " + findName);
				System.out.println("下标为 " + i);
				//把i保存到index
				index++;
				break;
				}
			}

		//定义没有找到时的输出内容	
		if(index == -1){
			System.out.println("Sorry, 没有找到 " + findName);
		}
	}
}
