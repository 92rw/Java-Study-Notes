# 数组

数组可以存放多个同一类型的数据。

数组也是一种数据类型，是引用类型。

* 平均时间复杂度：插入Θ(n)，删除Θ(n)，访问Θ(1)，查找Θ(n)
* 最差时间复杂度：插入O(n)，删除O(n)，访问O(1)，查找O(n)

* 最差空间复杂度：O(n)

* 数组的引用（使用/访问，获取数组元素）可以通过 `数组名[index/下标/索引]` 实现，下标从0开始编号。元素内存地址 = 数组内存地址 + 元素长度 × 元素索引
* 可以用 `数组名.length`来表示其大小/长度



## 创建数组

（1） 第一种声明方法
```java
//数据类型[] 数组名 = new 数据类型[大小];
double[] scores = new double[5];
//数据类型 数组名[] = new 数据类型[大小];
```

（2）第二种声明方法
```java
//第一步：声明数组，此时数组是null
//第二步：分配存储空间，可以存放数据
double scores[];
scores = new double[5]
```

（3）第三种声明方法：静态初始化
```java
//数据类型 数组名[] = {元素值1,元素值2,...}
char[] charArray = {'H', 'e', 'l', 'l', 'o'};
```


注意事项：

1. 数组是多个相同类型数据的组合，实现对这些数据的统一管理
2. 数组中的元素可以是任何数据类型，包括基本类型和引用类型，但是不能混用。
3. 数组创建后，如果没有赋值，有默认值：基本数据类型是默认值，其他类型是null
4. 使用数组的步骤：①声明数组并开辟空间，②给数组各个元素赋值，③使用数组
5. 数组的下标是从0开始的，必须在指定范围内(0~数组长度减1)使用，否则报：下标越界异常，比如int[] arr = new int[5];则有效下标为0-4
6. 数组属引用类型，数组型数据是对象(Object)



## 访问和修改元素

数组是引用类型，在使用索引访问数组元素时，如果索引超出范围，程序会抛出异常

```java
/* 随机访问元素 */
int randomAccess(int[] nums) {
    // 在区间 [0, nums.length) 中随机抽取一个数字
    int randomIndex = ThreadLocalRandom.current().nextInt(0, nums.length);
    // 获取并返回随机元素
    int randomNum = nums[randomIndex];
    return randomNum;
}
```

数组赋值assign的机制

* 基本数据类型赋值，这个值就是具体的数据，而且相互不影响。值传递=值拷贝（存储在栈）；
* 数组在默认情况下是引用传递（地址拷贝），赋的值是地址（存储在堆），赋值方式是引用赋值。数组间会相互影响
  

案例：创建一个char类型的26个元素的数组，分别放置'A'-'Z'。使用for循环访问所有元素并打印出来。

```java
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
```



## 数组扩容

要求：实现动态的给数组添加元素效果，实现对数组扩容。

* 原始数组使用静态分配int[] arr={1,2,3}
* 增加的元素，直接放在数组的最后arr={1,2,3,4}
* 用户可以通过如下方法来决定是否继续添加，添加成功，是否继续？y/n

思路分析

1. 定义初始数组int[] arr = {1,2,3} 下标0-2
2. 定义一个新的数组int[] arrNew = new int[arr.length+1];
3. 遍历 arr数组，依次将 arr 的元素拷贝到 arrNew 数组
4. 将4赋给arrNew[arrNew..length-1]=4;把4赋给arrNewj最后一个元素
5. 让arr指向arrNew;arr=arrNew.;那么原来arr数组就被销毁
6. 创建一个Scanner可以接受用户输入
7. 因为用户退出时间不确定，使用do-while+break来控制

代码实现

```java
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
```



由于数组不支持动态扩容，需要建立新索引并完成指向。这道题可以使用二分查找的方法降低时间复杂度

```java
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
```



参考资料：

[4.1  数组 - Hello 算法 (hello-algo.com)](https://www.hello-algo.com/chapter_array_and_linkedlist/array/#413)

[双指针技巧秒杀七道数组题目 | labuladong 的算法笔记](https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-48c1d/shuang-zhi-fa4bd/)
