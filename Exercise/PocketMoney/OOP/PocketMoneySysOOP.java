package Exercise.PocketMoney.OOP;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

//使用面向对象编程重写零钱账户程序，将各个功能对应一个方法
//特点：功能模块化，易读性和后期维护性强
public class PocketMoneySysOOP {
    //定义相关变量
    boolean loop = true;
    Scanner scanner = new Scanner(System.in);
    String key = "";

    //2.完成零钱通明细（实现方法：数组（缺点：不能动态增长）/对象/字符串拼接）
    String details = "------------零钱账户明细------------";

    //3.完成收益入账（实现方法：定义新的变量）
    double money = 0;
    double balance = 0;
    //引入新包，快捷键Alt+Shift+Enter
    Date date = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");//用于日期时间的格式化

    //4.消费
    String note = ""; //用于记录消费内容

    //5.退出
    String choice = "";//记录用户选择

    //显示菜单
    public void mainMenu() {
        //直接使用循环结构的代码
        do{
            System.out.println("\n============零钱账户OOP版============");
            System.out.println("\t\t\t1 账户明细");
            System.out.println("\t\t\t2 收益入账");
            System.out.println("\t\t\t3 消   费");
            System.out.println("\t\t\t4 退   出");

            System.out.println("请选择(1-4)：");
            key = scanner.next();//用户可能输入其他内容


            //使用 switch 分支控制
            switch (key) {
                case "1" ://零钱通明细
                    this.detail();
                    break;
                case "2" ://收益入账
                    this.income();
                    break;
                case "3" ://消费
                    this.expense();
                    break;
                case "4" ://退出
                    this.exit();
                    break;
                default:
                    System.out.println("选择有误，请重新选择");

            }
        } while (loop);
    }

    //账户明细
    public void detail() {
        System.out.println(details);
    }

    //收益入账
    public void income() {
        System.out.println("请输入收益入账金额");
        money = scanner.nextDouble();
        //校验money的值范围
        //把不正确条件写在if里，可以有效减少括号里面的内容，这样以后需求改变了，也方便修改
        // 如果正确条件在括号里，找括号是真的头大
        if (money <= 0){
            System.out.println("收益入账的金额需要 大于 0");
            return;//使用return则不再执行后面的方法
        }
        balance += money;

        //拼接收益入账信息到details
        date = new Date();//获取当前日期
        details += "\n收益入账\t" + money + "\t" + sdf.format(date) + "\t" + balance;
    }

    //消费
    public void expense() {
        System.out.println("请输入收入消费金额");
        money = scanner.nextDouble();

        //校验money的值范围
        if (money <= 0 || money > balance){
            System.out.println("消费金额范围 应在 0 ~ " + balance);
            return;
        }
        balance -= money;
        System.out.println("消费说明：");
        note = scanner.next();
        //拼接收益入账信息到details
        date = new Date();//获取当前日期
        details += "\n"+ note + "\t-" + money + "\t" + sdf.format(date) + "\t" + balance;

    }

    //退出
    public void exit() {
        //用户输入4退出时，给出提示"你确定要退出吗？y/n”,否则循环输入指令，直到输入y或者n.

        //1) 定义变量 choice 接收用户输入
        //2) 使用while-break处理接收到的输入
        //3) 退出while 后，判断 choice 是y还是n，决定是否退出
        while (true) {
            System.out.println("您确定要退出吗? y/n");
            choice = scanner.next();
            if("y".equals(choice) || "n".equals(choice)){
                break;
            }
        }
        //一段代码完成一段功能，尽量不要混在一起
        if(choice.equals("y")){
            loop = false; //退出循环
        }
    }



}
