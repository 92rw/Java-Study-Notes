# 原地排序算法

最坏空间复杂度`O(1)`

### 选择排序

* i~n-1范围上，找到最小值并放在i位置，然后i+1~n-1范围上继续
* 最好时间复杂度`Ω(n^2)`，平均时间复杂度`Θ(n^2)`，最坏时间复杂度`O(n^2)`

```java
/* 选择排序 */
void selectionSort(int[] nums) {
    // 外循环：未排序区间为 [i, nums.length-1]
    for (int i = 0; i < nums.length - 1; i++) {
        // 内循环：找到未排序区间内的最小元素
        int minIndex = i;
        for (int j = i + 1; j < n; j++) {
            if (nums[j] < nums[minIndex])
                minIndex = j; // 记录最小元素的索引
        }
        // 将该最小元素与未排序区间的首个元素交换
        int temp = nums[i];
        nums[i] = nums[minIndex];
        nums[minIndex] = temp;
    }
}

```

参考资料：

[11.2  选择排序 - Hello 算法 (hello-algo.com)](https://www.hello-algo.com/chapter_sorting/selection_sort/#1121)



### 冒泡排序

* 0~i范围上，相邻位置较大的数滚下去，最大值最终来到i位置，然后0~i-1范围上继续
* 最好时间复杂度`Ω(n)`，平均时间复杂度`Θ(n^2)`，最坏时间复杂度`O(n^2)`

```java
/* 冒泡排序（标志优化） */
void bubbleSortWithFlag(int[] nums) {
    // 外循环：未排序区间为 [0, i]
    for (int i = nums.length - 1; i > 0; i--) {
        boolean flag = false; // 初始化标志位
        // 内循环：将未排序区间 [0, i] 中的最大元素交换至该区间的最右端
        for (int j = 0; j < i; j++) {
            if (nums[j] > nums[j + 1]) {
                // 交换 nums[j] 与 nums[j + 1]
                int tmp = nums[j];
                nums[j] = nums[j + 1];
                nums[j + 1] = tmp;
                flag = true; // 记录交换元素
            }
        }
        if (!flag)
            break; // 此轮冒泡未交换任何元素，直接跳出
    }
}

```

参考资料：[11.3  冒泡排序 - Hello 算法 (hello-algo.com)](https://www.hello-algo.com/chapter_sorting/bubble_sort/)



### 插入排序

* 0~i范围上已经有序，新来的数从右到左滑到不再小的位置插入，然后继续
* 最好时间复杂度`Ω(n)`，平均时间复杂度`Θ(n^2)`，最坏时间复杂度`O(n^2)`

```java
/* 插入排序 */
void insertionSort(int[] arr) {
    // 外循环：已排序元素数量为 1, 2, ..., n
    for (int i = 1; i < arr.length; i++) {
        // 内循环：将 arr[i] 插入到已排序部分的正确位置
        for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
            int tmp = arr[j];
            arr[j] = arr[j+1];
            arr[j+1] = tmp;
        }
    }
}

```

参考资料：

[插入排序 | 小菜学算法 (fasionchan.com)](https://fasionchan.com/algorithm/sort/insertion-sort/)

[11.4  插入排序 - Hello 算法 (hello-algo.com)](https://www.hello-algo.com/chapter_sorting/insertion_sort/)
