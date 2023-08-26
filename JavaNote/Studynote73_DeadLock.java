import java.util.Scanner;

/*
线程死锁：多个线程都占用了对方的锁资源，但不肯相让，导致了死锁，在编程是一定要避免死锁的发生
 */
public class Studynote73_DeadLock {
    public static void main(String[] args) {
        //模拟死锁现象
        DeadLockDemo A = new DeadLockDemo(true);
        A.setName("A线程");
        DeadLockDemo B = new DeadLockDemo(false);
        B.setName("B线程");
        A.start();
        B.start();
    }
}


//线程
class DeadLockDemo extends Thread {
    static Object o1 = new Object();// 保证多线程，共享一个对象,这里使用static
    static Object o2 = new Object();
    boolean flag;

    public DeadLockDemo(boolean flag) {//构造器
        this.flag = flag;
    }

    @Override
    public void run() {

        //下面业务逻辑的分析
        //1. 如果flag 为 T, 线程A 就会先得到/持有 o1 对象锁, 然后尝试去获取 o2 对象锁
        //2. 如果线程A 得不到 o2 对象锁，就会Blocked
        //3. 如果flag 为 F, 线程B 就会先得到/持有 o2 对象锁, 然后尝试去获取 o1 对象锁
        //4. 如果线程B 得不到 o1 对象锁，就会Blocked
        if (flag) {
            synchronized (o1) {//对象互斥锁, 下面就是同步代码
                System.out.println(Thread.currentThread().getName() + " 进入1");
                synchronized (o2) { // 这里获得li对象的监视权
                    System.out.println(Thread.currentThread().getName() + " 进入2");
                }

            }
        } else {
            synchronized (o2) {
                System.out.println(Thread.currentThread().getName() + " 进入3");
                synchronized (o1) { // 这里获得li对象的监视权
                    System.out.println(Thread.currentThread().getName() + " 进入4");
                }
            }
        }
    }
}

/*
下面操作会释放锁
1.当前线程的同步方法、同步代码块执行结束
案例：上厕所，完事出来
2.当前线程在同步代码块、同步方法中遇到break、return。
案例：没有正常的完事，经理叫他修改bug，不得已出来
3.当前线程在同步代码块、同步方法中出现了未处理的Error或Exception，导致异常结束
案例：没有正常的完事，发现忘带纸，不得已出来
4.当前线程在同步代码块、同步方法中执行了线程对象的wait()方法，当前线程暂停，并释放锁。
案例：没有正常完事，觉得需要酝酿下，所以出来等会再进去

下面操作不会释放锁
1．线程执行同步代码块或同步方法时，程序调用Thread.sleep()、Thread.yield()方法暂停当前线程的执行，不会释放锁
案例：上厕所，太困了，在坑位上咪了一会
2.线程执行同步代码块时，其他线程调用了该线程的suspend()方法将该线程挂起，该线程不会释放锁
提示：应尽量避免使用suspend()和resume()来控制线程，此方法不再推荐使用
 */

/*
多线程练习
（1）在main方法中启动两个线程
（2）第1个线程循环随机打印100以内的整数
（3）直到第2个线程从键盘读取了Q"命令
 */
class MultiThreadExercise01{
    public static void main(String[] args) {
        PrintRandomInt thread1 = new PrintRandomInt();
        CatchStopKey thread2 = new CatchStopKey(thread1);
        thread1.start();
        thread2.start();
    }
}
//第一个线程
class PrintRandomInt extends Thread{
    private boolean loop = true;

    @Override
    public void run() {
        while (loop) {
            System.out.println((int)(100 * Math.random() + 1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("A线程退出");
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}

class CatchStopKey extends Thread{
    private PrintRandomInt a;
    private Scanner scanner = new Scanner(System.in);
    //在构造器中直接传入上一个类的对象，让两个线程对象共同拥有loop变量
    public CatchStopKey(PrintRandomInt a) {
        this.a = a;
    }

    @Override
    public void run() {
        while (true) {
            //接收到用户输入
            System.out.println("请输入指令，若输入(Q)表示退出");
            char key = scanner.next().toUpperCase().charAt(0);
            if (key == 'Q') {
                //以通知的方式结束守护进程
                a.setLoop(false);
                System.out.println("B线程退出");
                break;
            }
        }
    }
}

/*
（1）有2个用户分别从同一个卡上取钱（总额：10000）
（2）每次都取1000，当余额不足时，就不能取款了
（3）不能出现超取现象=》线程同步问题

解题思路：注意互斥锁需要是同一个对象(最好是类对象)
用while无限循环，用if判断，synchronize锁if，锁外用休眠就好了

说明：涉及到多个线程共享资源，建议采用Runnable接口。但此次加入余额判断的语句，因此通过Thread实现
 */

class MultiThreadExercise02{
    public static void main(String[] args) {
        WithdrawClient withdrawClient1 = new WithdrawClient();
        WithdrawClient withdrawClient2 = new WithdrawClient();
        //设定客户名字
        withdrawClient1.setName("A");
        withdrawClient2.setName("B");
        //执行线程
        withdrawClient1.start();
        withdrawClient2.start();
    }
}

class WithdrawClient extends Thread{
    static int Balance = 10000;
    static int Count;
    int WithdrawMoney;
    //此处设置的是非公平锁
    //可以把这个同步方法改成同步代码块，作用范围是WithdrawClient.class
    static synchronized void withdraw(int varymoney){
        Balance -= varymoney;

        System.out.print(String.format("第%d次取款由用户%s发起，取款金额%d，卡内余额%d，",
                (++Count),Thread.currentThread().getName(),varymoney,Balance));

    }

    @Override
    public void run() {
        while(Balance >= 0){

            int varymoney = (int)(Math.random() * 1000 + 1);
            if (Balance - varymoney >= 0) {
                WithdrawMoney += varymoney;
                withdraw(varymoney);
                /*同步代码块的方式如下
                synchronized (WithdrawClient.class) {
                    Balance -= varymoney;

                    System.out.print(String.format("第%d次取款由用户%s发起，取款金额%d，卡内余额%d，",
                            (++Count),Thread.currentThread().getName(),varymoney,Balance));

                }
                 */
            } else {
                System.out.println(String.format("用户%s计划取款%d，大于卡内余额%d，共取出现金%d并退出",
                        Thread.currentThread().getName(),varymoney,Balance,WithdrawMoney));
                break;
            }
            System.out.println("已取出现金" + WithdrawMoney);//该变量无法通过静态方法调用

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
