//数组可以存放多个同一类型的数据。数组也是一种数据类型，是引用类型。
//即：数组就是一组数据



//{}表示数组的值/元素，依次表示数组的第几个元素


//数组的引用（使用/访问，获取数组元素）可以通过 数组名[index/下标/索引] 实现，下标从0开始编号
//通过for可以循环访问数组的元素/值

//可以用 数组名.length来表示其大小/长度

//（1） 第一种声明方法
//数据类型[] 数组名 = new 数据类型[大小];
//数据类型 数组名[] = new 数据类型[大小];
import java.util.Scanner;
class Array01{
	public static void main(String[] args){
		Scanner myScanner = new Scanner(System.in);

		double[] scores = new double[5];
		//double[] 表示是double类型的数组，数组名

		for(int i = 0; i < scores.length; i++){
			System.out.println("请输入第"+ (i+1) + "个元素的值");
			scores[i] = myScanner.nextDouble();
		}

		//输出，遍历数组
		System.out.println("====已输入的数据如下:====");
		for(int i = 0; i < scores.length; i++){
			System.out.println("第" + (i+1) + "个元素的值=" + scores[i]);

		}
	}
}


/*（2）第二种声明方法
第一步：声明数组，此时数组是null
第二步：分配存储空间，可以存放数据

double scores[];
scores = new double[5]


（3）第三种声明方法：静态初始化
数据类型 数组名[] = {元素值,元素值,...};
*/

/*
注意事项：
1.数组是多个相同类型数据的组合，实现对这些数据的统一管理
2.数组中的元素可以是任何数据类型，包括基本类型和引用类型，但是不能混用。
3.数组创建后，如果没有赋值，有默认值
int 0,short 0,byte 0,long 0,
float 0.0,double 0.0,
char \u0000,
boolean false,
String null
4.使用数组的步骤1.声明数组并开辟空间 2给数组各个元素赋值 3使用数组
5.数组的下标是从0开始的。
6.数组下标必须在指定范围内(0~数组长度减一)使用，否则报：下标越界异常，比如int[] arr = new int[5];则有效下标为0-4
7.数组属引用类型，数组型数据是对象(object)


*/

/*数组赋值assign的机制
基本数据类型赋值，这个值就是具体的数据，而且相互不影响。值传递=值拷贝（存储在栈）；
数组在默认情况下是引用传递（地址拷贝），赋的值是地址（存储在堆），赋值方式是引用赋值。数组间会相互影响
*/


//创建一个char类型的26个元素的数组，分别放置'A'-'Z'。
//使用for循环访问所有元素并打印出来。
class Array02{
	public static void main(String[] args){
		char chars[];
		chars = new char[26];
		for(int i = 0; i < chars.length; i++){//循环26次
			chars[i] = (char)('A' + i);//由于'A'+i是int型，需要强制转换
		}
		for(int i = 0; i < chars.length; i++){
			System.out.print(chars[i] + " ");
		}
	}
}

//数组翻转：对arr[] = {11,22,33,44,55,66}进行数据翻转
//方法一：新建数组，改变指针（占用更多内存）
class ArrayCopy{
	public static void main(String[] args){
		int[] arr = {11,22,33,44,55,66};
		
		//先创建新的数组arr2，开辟新的数据空间
		//数据空间大小和原有的相同
		int[] arr2 = new int[arr.length];
		
		//遍历 arr，把每个元素拷贝到arr2对应的位置；
		for(int i = 0; i < arr.length; i++){
			arr2[arr.length - i - 1] = arr[i];
		}

		//改变数组指向
		arr = arr2;

		//输出新的arr内容
		for(int i = 0; i < arr.length; i++){
			System.out.print(arr[i] + " ");
		}
	}
}

//数组翻转：对arr[] = {11,22,33,44,55,66}进行数据翻转
//方法二：利用临时变量进行内部赋值
class ArrayReverse{
	public static void main(String[] args){
		int[] arr = {11,22,33,44,55,66,77};

		//对于奇数和偶数个元素，通用此公式
		for(int i = 0; i < arr.length/2; i++){
		int temp = arr[arr.length-i-1];
		arr[arr.length - i-1] = arr[i];
		arr[i] = temp;
		}
		
		//输出数组的内容
		for(int i = 0; i < arr.length; i++){
			System.out.print(arr[i] + " ");
		}
	}
}


/*
数组添加
要求：实现动态的给数组添加元素效果，实现对数组扩容。
1)原始数组使用静态分配int[] arr={1,2,3}
2)增加的元素，直接放在数组的最后arr={1,2,3,4}
3)用户可以通过如下方法来决定是否继续添加，添加成功，是否继续？y/n

思路分析
1.定义初始数组int[] arr = {1,2,3} //下标0-2
2.定义一个新的数组int[] arrNew = new int[arr.length+1];
3.遍历 arr数组，依次将 arr 的元素拷贝到 arrNew 数组
4,将4赋给arrNew[arrNew..length-1]=4;把4赋给arrNewj最后一个元素
5.让arr指向arrNew;arr=arrNew.;那么原来arr数组就被销毁
6.创建一个Scanner可以接受用户输入
7.因为用户退出时间不确定，使用do-while+break来控制
*/
import java.util.Scanner;
class Arrayadd{
	public static void main(String[] args){
		Scanner myScanner = new Scanner(System.in);
		int[] arr = {1,2,3};
		
		do{
		int arrNew[] = new int[arr.length+1];
		//遍历数组，拷贝元素
		for(int i = 0; i< arr.length; i++){
			arrNew[i] = arr[i];
		}
		//从用户处获取添加的元素
		System.out.println("请输入需要添加的元素");
		int addNum = myScanner.nextInt();
		
		//增加新元素
		arrNew[arr.length] = addNum;
		arr = arrNew;
		//输出arr
		System.out.println("====arr扩容后的情况为========");
		for(int i = 0; i < arrNew.length; i++){
			System.out.print(arrNew[i] + " ");
			}
		System.out.println();
		
		//问用户是否继续
		System.out.println("是否继续添加");
		char key = myScanner.next().charAt(0);
		if(key == 'n'){ //如果输入n，运行结束
			break;
			}
		}while(true);
		System.out.println("程序运行结束");
	}
}
