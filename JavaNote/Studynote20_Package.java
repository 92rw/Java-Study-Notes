/*
●包的三大作用
1.区分相同名字的类
2.当类很多时，可以很好的管理类[看Java API文档]
3.控制访问范围

●包的基本语法：
package 包名

●包的本质分析（原理）
实际上就是创建不同的文件夹/目录来保存类文件

●包的命名
命名规则：只能包含数字、字母、下划线、小圆点，但不能用数字开头，不能是关键字或保留字
demo.class.exec1    ×错误：不能出现关键字
demo.12a            ×错误：不能以数字开头
demo.ab12.oa        √正确

命名规范：
一般是小写字母+小圆点一般是
com.公司名.项目名.业务模块名

举例：
com.sina.crm.user   //用户模块
com.sina.crm.order  //订单模块
com.sina.crm.utils  //工具类

●常用的包
一个包下，包含很多的类，java中常用的包有：
java.lang.* //lang包是基本包，默认引入，不需要再引入。
java.util.* //util包，系统提供的工具包，工具类，使用Scanner
java.net.*  //网络包，网络开发
java.awt.*  //是做java的界面开发，GUI

●引入包的主要目的是使用包下面的类，比如
 import java.util.Scanner   //只是引入一个类Scanner
 imporat java.util.*    //表示将java.util包全部引入
 建议：只导入需要的类，不适用*导入

●注意事项和使用细节
1.package的作用是声明当前类所在的包，需要放在类（或文件）的最上面，一个类中最多只有一句package
2.import指令位置放在package的下面，在类定义前面，可以有多句且没有顺序要求。
 */

import java.util.Arrays;

public class Studynote20_Package {
    public static void main(String[] args) {
        //使用系统提供的类完成Array排序
        int[] arr = {-1, 20, 2, 13, 3};
        //对其从小到大进行排序
        //传统方法：自己编写（冒泡）
        //系统提供相应的类，可以方便完成
        System.out.println("===Arrays.sort()排序后的array===");
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }

        MyTools mt = new MyTools();
        mt.bubble(arr);
        System.out.println("\n===冒泡排序后的array===");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
    }
}

//创建一个类MyTools,编写一个方法，可以完成对int数组冒泡排序的功能
//要求从大到小
class MyTools{
    public void bubble(int[] arr){
        //冒泡排序
        for(int i = 0; i < arr.length-1; i++){
            for (int j = 0; j < arr.length -1 -i; j++) {
                if(arr[j] < arr[j+1]){
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }
        }
    }
}

