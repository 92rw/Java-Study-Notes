# 二分查找

利用遍历实现的顺序查找，到达第n个元素需要遍历n次。时间复杂度为`O(n)`，效率较低

二分查找：有序数组，基于分治策略，每轮缩小一半范围，只要log2的n次

* 时间复杂度`O(logn)`，空间复杂度`O(1)`

* 计算过程中，通过左右边界的索引，得到中间位置的的值。为防止序号相加的结果超过int类型取值范围，不需要直接相加

  ```java
  int mid = left + (right - left) / 2;
  int mid = left + (right - left) >> 1;
  ```

* 需要跳跃式访问数据，因此在链表中执行效率低

* 常数时间需要进行的步骤较多，因此在数据量小时运行时间长于线性查找

### 基本用法

结束方式和区间选定：目标在中点右侧是，区间左侧时mid + 1

* 左闭右闭区间：初始右边界为nums.length - 1，while循环的条件是left <= right；目标在中点左侧时，区间右侧是mid -1
* 左闭右开区间：初始右边界为nums.length，while循环的条件是left < right，目标在中点左侧时，区间右侧是mid
* 总结：while循环的终止条件是搜索区间为空

代码演示：左闭右开区间法，查找数组中是否存在某个元素

```java
	public static boolean exist(int[] arr, int num) {
		int left = 0, right = arr.length;
		while (l <= r) {
			m = l + (r - l) / 2;
			if (arr[m] == num) {
				return true;
			} else if (arr[m] > num) {
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return false;
	}
```



### 搜索边界位置

根据 [10.3  二分查找边界 - Hello 算法 (hello-algo.com)](https://www.hello-algo.com/chapter_searching/binary_search_edge/#1031)，当查找数组中重复元素的边界时，可以在已经得到 `arr[mid] == target` 时，继续向该方向二分查找。除此之外，还可以：

* 转化为查找相邻数的边界，再通过加减索引值
* 转化为查找该数加减某个浮点数后的索引值

代码演示：[Find First and Last Position of Element in Sorted Array - LeetCode](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

说明：

* 搜索边界的原理，是找到目标数后不直接返回，而是继续缩小区间进行收缩。左闭右闭区间在循环结束时left == right
* 当待查找数在数组中不存在时，除了可以用下面方法中的布尔值判断，还可以通过下面的方式
  * 判断数组是否越界（left - 1 < 0 || left - 1 >= nums.length）
  * 判断 nums[left - 1] == target

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        return new int[] {findLeft(nums, target), findRight(nums, target)};
    }
    
	//左闭右开区间，寻找最左位置
    public static int findLeft(int[] arr, int num) {
        int l = 0, r = arr.length;
        boolean exist = false;
        while (l < r) {
            int m = l + ((r - l) >> 1);
            if (arr[m] > num) {
                r = m;
            } else if (arr[m] < num){
                l = m + 1;
            } else {
                exist = true;
                r = m;
            }
        }
        return exist ? l : -1;
    }

    //左闭右闭区间，寻找最右位置
    public static int findRight(int[] arr, int num) {
        int l = 0, r = arr.length - 1;
        boolean exist = false;
        while (l <= r) {
            int m = l + ((r - l) >> 1);
            if (arr[m] > num) {
                r = m - 1;
            } else if (arr[m] < num){
                l = m + 1;
            } else {
                exist = true;
                l = m + 1;
            }
        }
        return exist ? r : -1;
    }
}
```



### 在无序数组中的应用

[力扣第162题：寻找峰值](https://leetcode.cn/problems/find-peak-element/description/)，要求使用时间复杂度为 O(log n)的算法，否则超时

```java
class Solution {
    public int findPeakElement(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;
        if (nums[0] > nums[1]) return 0;
        if (nums[n-1] > nums[n-2]) return n-1;
        int left = 1, right = n - 2, mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums[mid -1] > nums[mid]) {
                right = mid -1;
            } else if (nums[mid +1] > nums[mid]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
```

[1011. 在 D 天内送达包裹的能力 - 力扣（LeetCode）](https://leetcode.cn/problems/capacity-to-ship-packages-within-d-days/solutions/744326/gong-shui-san-xie-li-yong-er-duan-xing-z-95zj/)





参考资料：

[我写了首诗，让你闭着眼睛也能写对二分搜索 | labuladong 的算法笔记](https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-48c1d/wo-xie-le--9c7a4/)

[算法讲解006【入门】二分搜索_哔哩哔哩_bilibili](https://www.bilibili.com/av359276770)
