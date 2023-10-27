# 链表和指针

数值的传递

* 基本数据类型（int、long、byte、short、char、float、double、boolean）和String，传递的是值（值拷贝），形参的任何改变不影响实参！
* 引用类型传递的是地址（传递也是值,但是值是地址），可以通过形参影响实参

在方法执行中，对于传入的形参

* 如果方法体改变了形参的指向，在在没有返回值的情况下，传入的形参不会发生变化
* 如果方法体改变了形参的成员变量，在在没有返回值的情况下，传入的形参会发生变化

## 链表

|          | 数组                                 | 链表                                                         |
| -------- | ------------------------------------ | ------------------------------------------------------------ |
| 存储方式 | 连续内存空间                         | 分散内存空间                                                 |
| 容量扩展 | 长度不可变                           | 可灵活扩展                                                   |
| 内存效率 | 占用内存少、浪费部分空间             | 占用内存大                                                   |
| 访问元素 | `O(1)`，利用下标实现随机访问         | `O(n)`，需要遍历至索引                                       |
| 添加元素 | `O(n)`，将索引值及之后的元素向后移动 | `O(1)`，将`新节点.next = 下一个节点`，再将`原节点.next = 新节点` |
| 删除元素 | `O(n)`，将索引值之后的元素向前移动   | `O(1)`，`前面节点.next = 前面节点.next.next`                 |
| 查找元素 | `O(n)`，遍历至目标节点               | `O(n)`，遍历至目标节点                                       |

在链表的遍历过程中，需要注意修改成员变量、和修改实例指向的区别

```java
// 单链表节点
public static class ListNode {
	public int val;
	public ListNode next;

	public ListNode(int val) {
		this.val = val;
	}

	public ListNode(int val, ListNode next) {
		this.val = val;
		this.next = next;
	}
}
```

单向链表：包含值和指向下一节点的引用两项数据

环形链表：单向链表的尾节点指向头节点（即首尾相接）

双向链表：包含指向上一个节点的指针



## 递归

简单的说：递归就是方法自己调用自己，每次调用时传入不同的变量，递归有助于编程者解决复杂问题，同时可以让代码变得简洁



●递归能解决什么问题？

1. 各种数学问如：8皇后问题，汉诺塔，阶乘问题，迷宫问题，球和篮子的问题
2. 各种算法中也会使用到递归，比如快排，归并排序，二分查找，分治算法等
3. 将用栈解决的问题，转化为递归代码比较简洁

每次递归都会进入完整的方法，方法走完才回到被调用的地方，所以一定会执行输出语句

方法执行完毕后就会返回，返回到调用方法的地方，然后继续执行后面的代码，直到main方法执行完毕，整个程序才退出


递归重要规则

1. 执行一个方法时，就创建一个新的受保护的独立空间（栈空间）
2. 方法的局部变量是独立的，不会相互影响，比如n变量
3. 如果方法中使用的是引用类型变量（比如数组，对象），就会共享该引用类型的数据，
4. 递归必须向退出递归的条件逼近，否则就是无限递归，出现StackOverflowError;
5. 当一个方法执行完毕，或者遇到return,就会返回，遵守谁调用，就将结果返回给谁，同时当方法执行完毕或者返回时，该方法也就执行完毕。

代码实现

1. 使用递归的方式求出斐波那契数1,1,2,3,5,8,13……给你一个整数n，求出第n个斐波那契数列的值是多少

```java
//思路分析：当n=1斐波那契数是1，当n=2斐波那契数是1，n>=3斐波那契数是前两个数的和
public int Fibonacci(int n){
	if(n >= 1) {
		if(n == 1 || n == 2) {
			return 1;
		}else{
			return Fibonacci(n-1) + Fibonacci(n-2);
		}
	} else{
		System.out.print("要求输入>=1的整数 ");
		return -1;//必须有return输出
	}
}
```



## 链表反转

参考资料：[递归魔法：反转单链表 | labuladong 的算法笔记](https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-8f30d/di-gui-mo--10b77/)

练习题：[Reverse Linked List - LeetCode](https://leetcode.com/problems/reverse-linked-list/description/)

递归实现

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        //终止条件
        if (head == null || head.next == null) {
            return head;
        }

        ListNode res = reverseList(head.next);
        head.next.next = head;//函数返回时，让当前节点下一个节点的next指针，指向当前节点
        head.next = null;//当前节点的next指针，指向null
        return res;
    }
}
```

利用哨兵节点

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode next = null;
        while(head != null) {
            next = head.next;
            head.next = pre;//当前节点的next指针指向前一个节点
            pre = head;
            head = next;
        } 
        return pre;
    }
}
```



