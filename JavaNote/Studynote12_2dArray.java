import java.lang.Math;
//编程思想：测试时使用边界值
//编程思想：利用index赋值-1，判断数组中是否存在特殊边界条件


/*
二维数组TwoDimensionalArray
1.从定义上看int[][]
2.相当于一维数组的每个元素都是一维数组


使用方式：动态初始化
语法：类型[][] 数组名 = new 类型[大小][大小]

使用方式：静态初始化
语法：类型[][] 数组名 = {{值1,值2,...},{值1,值2,...},...,{值1,值2,...}}

*/


/*
要求：用二维数组输出如下图形
0 0 0 0 0 0
0 0 1 0 0 0
0 2 0 3 0 0
0 0 0 0 0 0

说明：二维数组的每个元素都是一维数组，如果需要得到每个一维数组的值，需要再次遍历
*/
public class TwoDimensionalArray{
	public static void main(String[] args){
		int[][] arr = {{0, 0, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0}, {0, 2, 0, 3, 0, 0}, {0, 0, 0, 0, 0, 0}};
		for(int i =0;i < arr.length; i++){

			//arr[i].length得到二维数组每个组成元素的长度
			for(int j = 0; j< arr[i].length; j++){
				System.out.print(arr[i][j] +" ");
			}
			System.out.println();
		}
	}
}

/*需求：动态创建下面二维数组，并输出
	j=0	j=1	j=2
i=0	1
i=1	2	2
i=2	3	3	3

说明：
1.有三个一维数组，每个一维数组的元素不一样
2.如果直接开辟3*3的空间，将浪费内存
*/
class TriangleArray{
	public static void main(String[] args){
		
		int[][] arr = new int[3][];	//只确定一维数组的个数，没有给一维数组开空间
		
		for(int i = 0; i< arr.length; i++){//遍历arr每个一维数组
			//给每个一维数组开空间
			//如果没有给一维数组new，那么arr[i]就是null
			arr[i] = new int [i + 1];
			for(int j = 0; j < arr[i].length; j++){
				arr[i][j] = i + 1;
				}
		}
		
		//遍历arr输出每个数组
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){				
				System.out.print(arr[i][j] +" ");
			}
			System.out.println();
		}
	}
}

/*使用二维数组打印10行杨辉三角
1
1	1
1	2	1
1	3	3	1
1	4	6	4	1
1	5	10	10	5	1
...

特点：
1.第一行有1个元素，第n行有n个元素
2.每一行的第一个元素和最后一个元素都是1
3.从第三行开始，非第一个元素和最后一个元素，其值arr[i][j] = arr[i-1][j-1]+arr[i-1][j]
*/

class YangHui{
	public static void main(String[] args){
		int[][] yangHui = new int[10][];
		for(int i = 0; i < yangHui.length; i++){
			yangHui[i] = new int[i+1];//给每个一维数组（行）开空间
			for(int j = 0; j < yangHui[i].length; j++){
				if(j == 0 | j == i){ //此处i = yangHui.length - 1
					yangHui[i][j] = 1;
				}else{
					yangHui[i][j] = yangHui[i-1][j-1]+yangHui[i-1][j];
				}
			}
		}
		for(int i = 0; i < yangHui.length; i++){
			for(int j = 0; j < yangHui[i].length; j++){				
				System.out.print(yangHui[i][j] +"\t");
			}
			System.out.println();
		}
	}
}


//已知有个升序的数组，要求插入一个元素，该数组顺序依然是升序
//比如：[10,12,45,90],添加数字23后，数组为[10,12,23,45,90]
class InsertNum{
	public static void main(String[] args){
		int[] arr = {10,12,45,90};
		int index = -1;//通过赋值-1方便罗列特殊情况
		int insertNum = 23;
		// 遍历数组，如果发现insertNum<= arr[i]，说明i就是要插入的位置
		//使用index保留index=i
		//如果遍历完后没有发现insertNum<= arr[i]，说明index=arr.length
		//即：添加到arr的最后
		
		for(int i = 0; i < arr.length; i++){
			if(arr[i] >= insertNum){
					index = i;
					break;//
				}
			}
		//判断index的值
		if(index == -1){//说明没有找到位置
			index = arr.length;
		}
		//扩容
		int[] arrNew = new int[arr.length + 1];
		
		//将arr的元素拷贝到新数组，并且跳过index的位置
		//循环变量i用于控制新数组下标，j用于遍历旧数组
		for(int i = 0, j = 0; i < arrNew.length; i++){
			if(i != index){//说明可以把arr的元素拷贝到新数组
				arrNew[i] = arr[j];
				j++;
				}else{//这个位置就是要插入的数
					arrNew[i] = insertNum;
				}
			}
		
		//让arr指向arrNew，原来的数组被销毁
		arr = arrNew;

		for(int i = 0; i < arr.length; i++){
			System.out.print(arr[i] +" ");
		
		}
	}
}

//随机生成10个整数(1~100的范围)保存到数组，并倒序打印以及求平均值、求最大值和最大值的下标、并查找里面是否有8

class RandomArray{
	public static void main(String[] args){
		int[] arr = new int[10];
		
		//生成随机整数
		for(int i = 0; i < arr.length; i++){
			arr[i] = (int)(Math.random()*100) + 1;
		}
		System.out.println("====arr的元素情况====");
		for(int i = 0; i < arr.length; i++){
			System.out.print(arr[i] +" ");
		}
		System.out.println();
		
		//倒序打印≠逆序打印
		System.out.println("====arr的元素情况(倒序)====");
		for(int i = arr.length - 1; i >= 0; i--){
			System.out.print(arr[i] +" ");
		}
		System.out.println();
		
		//求平均值、最大值和最大值下标
		double sum = arr[0];
		int max = arr[0];
		int maxIndex = 0;

		for(int i = 1; i < arr.length; i++){
			
			sum += arr[i];//累积和
			
			if( max < arr[i]){//说明max不是最大值
				max = arr[i];
				maxIndex = i;
			}
			
			
		}
		System.out.println("平均值为" + sum);
		System.out.println("最大值为" + max + "最大值下标为" + maxIndex);
		
		//查找数字8是否在数组中
		int findNum = 8;
		int index = -1;
		for(int i = 0; i < arr.length; i++){
			if(arr[i] == findNum){
				index =1;
				System.out.println("找到数" + findNum + " 下标=" + i);
				break;//若需找到所有的8下标，可以新建一个数组，通过扩容的方式记录
			}
		}
		if(index == -1){
			System.out.println("没有找到数" + findNum);
		}
	}
}
