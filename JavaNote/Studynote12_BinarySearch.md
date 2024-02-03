# 二分查找

顺序查找：到达第n个元素需要遍历n次。每次移动的步长有限，时间复杂度为`O(n)`，效率较低

二分查找：利用有序数组的特点，每轮缩小一半范围，实现时间复杂度`O(logn)`，空间复杂度`O(1)`

* 基本格式

  ```java
  int left = -1, right = N;
  while (left + 1 != right) {
      int mid = left + (right - left) / 2;//防止超过整数最大值
      if (m满足判定条件) {
          left = mid;
      } else {
          right = mid;
      }
  return left或right;
  /*
  说明：left是“蓝色区间右边界”，right是“红色区间左边界”；这种方式保证mid始终在[0,N)范围内，更新指针时不需要加减1
  */
  ```

* 需要跳跃式访问数据，因此在链表中执行效率低

* 常数时间需要进行的步骤较多，因此在数据量小时运行时间长于线性查找

* 基于分治策略，只要log2的n次



案例演示：对数组①②③⑤⑤⑤⑧⑨进行处理

|                        | 区间划分   | 循环条件 | 返回值 |      |
| ---------------------- | ---------- | -------- | ------ | ---- |
| 找到第一个"≥5"的元素   | ①②③(❺)❺❺❽❾ | ＜5      | right  |      |
| 找到最后一个"<5"的元素 | ①②(⓷)❺❺❺❽❾ | ＜5      | left   |      |
| 第一个">5"的元素       | ①②③⑤⑤⑤(❽)❾ | ≤5       | right  |      |
| 找到最后一个"≤5"的元素 | ①②③⑤⑤(⓹)❽❾ | ≤5       | left   |      |



### 搜索边界位置

根据 [10.3  二分查找边界 - Hello 算法 (hello-algo.com)](https://www.hello-algo.com/chapter_searching/binary_search_edge/#1031)，当查找数组中重复元素的边界时，可以在已经得到 `arr[mid] == target` 时，继续向该方向二分查找。除此之外，还可以：

* 转化为查找相邻数的边界，再通过加减索引值
* 转化为查找该数加减某个浮点数后的索引值

代码演示：[Find First and Last Position of Element in Sorted Array - LeetCode](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

说明：

* 当待查找数在数组中不存在时，除了可以用下面方法中的布尔值判断，还可以通过下面的方式
  * 判断数组是否越界（left - 1 < 0 || left - 1 >= nums.length）
  * 判断 nums[left - 1] == target

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        return new int[] {findLeft(nums, target), findRight(nums, target)};
    }
    

    public static int findLeft(int[] arr, int num) {
        int l = -1, r = arr.length;
        while (l + 1 != r) {
            int m = l + ((r - l) >> 1);
            if (arr[m] < num) {
                l = m;
            } else {
                r = m;
            }
        }
        //判定不越界而且该数在数组中
        if (r == arr.length) return -1;
        if (arr[r] != num) r = -1;
        return r;
    }

    public static int findRight(int[] arr, int num) {
        int l = -1, r = arr.length;
        while (l + 1 != r) {
            int m = l + ((r - l) >> 1);
            if (arr[m] <= num) {
                l = m;
            } else {
                r = m;
            }
        }
        //判定不越界而且该数在数组中
        if (l == -1 || arr[l] != num) l = -1;
        return l;
    }
}
```



### 在无序数组中的应用

[力扣第162题：寻找峰值](https://leetcode.cn/problems/find-peak-element/description/)，要求使用时间复杂度为 O(log n)的算法，否则超时

```java
class Solution {
    public int findPeakElement(int[] nums) {
        int left = -1, right = nums.length, mid = 0;
        while (left + 1 != right) {
            mid = (left + right) / 2;
            if (nums[mid -1] > nums[mid]) {
                right = mid -1;
            } else if (nums[mid +1] > nums[mid]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }
}
```

[1011. 在 D 天内送达包裹的能力 - 力扣（LeetCode）](https://leetcode.cn/problems/capacity-to-ship-packages-within-d-days/solutions/744326/gong-shui-san-xie-li-yong-er-duan-xing-z-95zj/)





参考资料：

[我写了首诗，让你闭着眼睛也能写对二分搜索 | labuladong 的算法笔记](https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-48c1d/wo-xie-le--9c7a4/)

[二分搜索怎么用？我又总结了套路 | labuladong 的算法笔记](https://labuladong.online/algo/di-yi-zhan-da78c/shou-ba-sh-48c1d/er-fen-sou-ae51e/)

[算法讲解006【入门】二分搜索_哔哩哔哩_bilibili](https://www.bilibili.com/av359276770)

[二分查找为什么总是写错？_哔哩哔哩_bilibili](https://www.bilibili.com/av841423368)
