# 二维数组

编程思想

* 测试时使用边界值
* 利用index赋值-1，判断数组中是否存在特殊边界条件

```java
//使用方式1：动态初始化
类型[][] 数组名 = new 类型[大小][大小];

//使用方式2：静态初始化
类型[][] 数组名 = {{值1,值2,...},{值1,值2,...},...,{值1,值2,...}}
```



## 案例演示

要求：用二维数组输出如下图形
0 0 0 0 0 0
0 0 1 0 0 0
0 2 0 3 0 0
0 0 0 0 0 0
说明：二维数组的每个元素都是一维数组，如果需要得到每个一维数组的值，需要再次遍历

```java
/*

*/
class TwoDimensionalArray{
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
```



要求：使用二维数组打印10行杨辉三角
1
1  1
1  2  1
1  3  3  1
1  4  6  4  1
1  5  10 10 5  1
...

特点：
1.第一行有1个元素，第n行有n个元素
2.每一行的第一个元素和最后一个元素都是1
3.从第三行开始，非第一个元素和最后一个元素，其值`arr[i][j] = arr[i-1][j-1]+arr[i-1][j]`

```java
/*
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
```



## 算法

数组反转

[Rotate Image - LeetCode](https://leetcode.com/problems/rotate-image/description/)：利用对称轴和反转，实现数组元素位置旋转

```java
class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][n- 1 - j];
                matrix[i][n- 1 - j] = tmp;
            }
        }
    }
}
```

遍历

[Spiral Matrix - LeetCode](https://leetcode.com/problems/spiral-matrix/description/) 矩阵的螺旋遍历，同样的方法可以用于 [Spiral Matrix II - LeetCode](https://leetcode.com/problems/spiral-matrix-ii/)

```java
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        int row = matrix.length, column = matrix[0].length;
        int up = 0, left = 0, down = row-1, right = column-1;
        List<Integer> res = new LinkedList<>();
        while (res.size() < row * column) {
            if(up <= down) {
                for(int i = left; i <= right; i++) {
                    res.add(matrix[up][i]);
                }
                up++;
            }
            if(right >= left) {
                for(int i = up; i <= down; i++) {
                    res.add(matrix[i][right]);
                }
                right--;
            }
            if(down>= up) {
                for(int i = right; i >= left; i--) {
                    res.add(matrix[down][i]);
                }
                down--;
            }
            if(left <= right) {
                for(int i = down; i >= up; i--) {
                    res.add(matrix[i][left]);
                }
                left++;
            }
        }
        return res;
    }
}
```



参考资料：[二维数组的花式遍历技巧 | labuladong 的算法笔记](https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-48c1d/er-wei-shu-150fb/)
