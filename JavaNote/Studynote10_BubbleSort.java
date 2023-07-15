/*
排序

排序是将一群数据，依指定的顺序进行排列的过程。
排序的分类：
1.内部排序：
指将需要处理的所有数据都加载到内部存储器中进行排序。包括（交换式排序法、选择式排序法和插入式排序法）：
2.外部排序法：
数据量过大，无法全部加载到内存中，需要借助外部存储进行排序。包括（合并排序法和直接合并排序法）。


冒泡排序(Bubble Sorting)的基本思想是：通过对待排序序列从后向前（从下标较大的元素开始），
依次比较相邻元素的值，若发现逆序则交换，使值较大的元素逐渐从前移向后部，就象水底下的气泡一样逐渐向上冒。
*/

/*举例说明：将24, 69, 80, 57, 13使用冒泡排序法排列
方法：两两比较相邻数，前面的数较大则和后面的数交换顺序
----------------------------------
第1轮排序：将最大数放在最后
第一次比较{24(指针), 69(指针), 80, 57, 13}
第二次比较{24, 69(指针), 80(指针), 57, 13}
第三次比较{24, 69, 57(指针), 80(指针), 13}
第四次比较{24, 69, 57, 13(指针), 80(指针)}

第2轮排序：将次大数放在倒数第二
第一次比较{24(指针), 69(指针), 57, 13, 80}
第二次比较{24, 57(指针), 69(指针), 13, 80}
第三次比较{24, 57, 13(指针), 69(指针), 80}

第3轮排序：将第三数放在倒数第三
第一次比较{24(指针), 57(指针), 13, 69, 80}
第二次比较{24, 13(指针), 57(指针), 69, 80}

第4轮排序：将第四数放在倒数第四
第一次比较{13(指针), 24(指针), 57, 69, 80}
---------------------------------
总结：
1、共有5个元素
2.进行4轮排序（外层循环）
3、每轮排序确定一个数的位置
4、前面的数大于后面的数，则相互交换
5、每轮比较在减少
*/

class BubbleSortAnalyse{
	public static void main(String[] args){
		int arr[] = {24, 69, 80, 57, 13};
		int temp = 0;//用于辅助交换的变量
		
		//第一轮比较
		for(int i = 0; i < 4; i++){
			if(arr[i] > arr[i + 1]){
				temp = arr[i];
				arr[i] = arr[i + 1];
				arr[i + 1] = temp;
				}
			}
		
		System.out.println("======第一轮比较结果======");
		for(int j = 0; j < arr.length; j++){
		System.out.print(arr[j]+" ");
		}
		System.out.println();		
		//第二轮比较
		for(int i = 0; i < 3; i++){
			if(arr[i] > arr[i + 1]){
				temp = arr[i];
				arr[i] = arr[i + 1];
				arr[i + 1] = temp;
				}
			}
		
		System.out.println("======第二轮比较结果======");
		for(int j = 0; j < arr.length; j++){
		System.out.print(arr[j]+" ");
		}
		System.out.println();
		//第三轮比较
		for(int i = 0; i < 2; i++){
			if(arr[i] > arr[i + 1]){
				temp = arr[i];
				arr[i] = arr[i + 1];
				arr[i + 1] = temp;
				}
			}
		
		System.out.println("======第三轮比较结果======");
		for(int j = 0; j < arr.length; j++){
		System.out.print(arr[j]+" ");
		}
		System.out.println();
		//第四轮比较
		for(int i = 0; i < 1; i++){
			if(arr[i] > arr[i + 1]){
				temp = arr[i];
				arr[i] = arr[i + 1];
				arr[i + 1] = temp;
				}
			}
		
		System.out.println("======第四轮比较结果======");
		for(int j = 0; j < arr.length; j++){
		System.out.print(arr[j]+" ");
		}
		System.out.println();

	}
}


//将上述排序通过外层循环包裹起来
class BubbleSort{
	public static void main(String[] args){
		int arr[] = {24, 69, 80, 57, 13};
		int temp = 0;
		//外层循环次数 = 数组中的元素数- 1
		for(int j = 0; j< arr.length-1; j++){		
			for(int i = 0; i < arr.length-1-j; i++){
				if(arr[i] > arr[i + 1]){
					temp = arr[i];
					arr[i] = arr[i + 1];
					arr[i + 1] = temp;
				}
			}
			//输出时使用换行符调整格式
			System.out.println("\n======第"+(j+1)+"轮比较结果======");
			for(int i = 0; i < arr.length; i++){
			System.out.print(arr[i]+"\t");
			}
		}
		System.out.println();		
	}
}

//继续优化的思路：若是数组本身已按顺序排列，可以提前结束循环
