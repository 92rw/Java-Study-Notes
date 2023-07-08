/*
断点调试
        一个实际需求
        1.在开发中，新手程序员在查找错误时，这时老程序员就会温餐提示，可以用断点调试
        一步一步的看源码执行的过程，从而发现错误所在.
        2.重要提示：在断点调试过程中，是运行状态，是以对象的运行类型来执行的

        断点调试介绍
        1.断点调试是指在程序的某一行设置一个断点breakpoint，调试时，程序运行到这一行就会停住，
        然后你可以一步一步往下调试，调试过程中可以看各个变量当前的值，出错的话，调
        试到出错的代码行即显示错误，停下。进行分析从而找到这个Bug
        2.断点调试是程序员必须掌握的技能

        3.断点调试也能帮助我们查看java底层源代码的执行过程，提高程序员的Java水平

        断点调试快捷键
        F7（跳入）F8(跳过）shift+F8（跳出）F9（resume,执行到下一个断点）
        F7：跳入方法内
        F8：逐行执行代码
        1、想要点进方法里面就按F7； 2、想要跳转到下一行就按F8； 3、想要跳到下一个断点的位置就按F9。
        shift+F8:跳出方法

        本来就是用于找bug的，就是能输出，但结果不对，debug可以直观的看变量的变化过程，方便很多
 */

import java.util.Arrays;

class Debug01 {
    public static void main(String[] args) {
        int[] arr = {1, 10, -1};
        //利用快捷键 F8 逐行运行找错
        for (int i = 0; i <= arr.length; i++) {//因为没有arr[3]，所以会报错
            System.out.println(arr[i]);
        }
        System.out.println("退出for循环");
    }
}

class Debug02{
    public static void main(String[] args) {
        //debug源码
        int arr[] = {8, -1, 199, 70, 10};
        //利用快捷键 F7 快速进入方法体内，了解运行机制
        //如果无法成功进入方法体，可force step into（快捷键 Alt+Shift+F7）
        //或者通过 Settings -> Build,Execution,Deployment -> Debugger -> Stepping
        //将 Do not step into the classes的 java.*和 javax.* 取消勾选
        Arrays.sort(arr);
        //可以用快捷键 Shift+F8 返回上一个方法
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i] + "\t");
        }
    }
}

//断点可以在debug过程中，动态的下断点
