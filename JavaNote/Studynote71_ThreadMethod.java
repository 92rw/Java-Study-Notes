
class ThreadExample{
    public static void main(String[] args) throws InterruptedException {
        //创建 CatMeow 对象，当做线程使用
        CatMeow cat = new CatMeow();
        cat.start(); //启动 cat 对象的线程 Thread-0，最终会执行 cat 的 run() 方法
        /*运行原理
        start() 方法的源代码中调用 start0() 方法，实现多线程的效果
        start0() 是native 修饰的本地方法，底层是c/c++实现。由 JNI（Java Native Interface）调用底层的操作系统方法来启动新的线程
        start0() 方法执行后，该线程不一定马上执行，只是线程变成可运行状态，具体执行时间由CPU统一调度
         */

        //cat.run();
        //直接调用普通方法相当于串行化执行，没有真正启动线程，方法的调用就会新开一个栈空间，执行完毕后才继续运行下方代码

        System.out.println("主线程" + Thread.currentThread().getName() + "继续执行");
        //在 main 线程中启动子线程 Thread-0，主线程不会阻塞，会继续执行

        //因为是一个进程，在一个CPU内执行，所以不是并行
        //java 采用的是抢占式调度，谁抢到时间片谁就执行。
        for (int i = 0; i < 10; i++) {
            System.out.println("主线程i = " + i);
            Thread.sleep(1000);
        }

        //在Terminal界面使用 jconsole 可以看到，要所有的线程都结束了,进程才结束,然后退出程序
        //一个进程结束了，并不意味着它其中的线程结束，一个线程结束也不意味着它其中的子线程结束
    }
}

//任务要求：编写程序，开启一个线程。该线程每隔一秒在控制台输出“喵喵，我是小猫咪”。输出8次后结束进程
class CatMeow extends Thread{
    int times = 0;//记录输出次数
    @Override
    public void run() {//重写run方法，写上自己的业务逻辑

        while (true) {
            //该线程每隔1秒。在控制台输出 “喵喵, 我是小猫咪”
            System.out.println("喵喵, 我是小猫咪" + (++times) + " 线程名=" + Thread.currentThread().getName());
            //让该线程休眠1秒，使用快捷键 Ctrl+Alt+T 快速写try-catch方法，避免程序中断的异常
            //这里try-catch是保证该线程在sleep时还是能感知响应，能够响应中断，不会睡死
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(times == 8) {
                break;//当times 到80, 退出while, 这时线程也就退出..
            }
        }
    }
}
//1. 当一个类继承了 Thread 类， 该类就可以当做线程使用
//2. 我们会重写 run方法，写上自己的业务代码
//3. Thread 类实现了 Runnable 接口的 run() 方法
/*
    @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }
 */
//=====================================================================================================
/*
线程应用案例2-实现Runnable接口
>说明
1.java是单继承的，在某些情况下一个类可能已经继承了某个父类，这时在用继承 Thread 类方法来创建线程显然不可能了。
2.java设计者们提供了另外一个方式创建线程，就是通过实现 Runnable 接口来创建线程

方法是创建一个Thread对象，传入待创建进程的类对象，之后使用这个Thread对象去调用start即可调用run(),底层是通过调用Thread对象的run方法调用了带创建进程的类对象的run方法。
 */
class RunnableExample {
    public static void main(String[] args) {
        DogBark dog = new DogBark();
        //dog.start(); //不能创建线程，因为start()方法是由Thread类实现的
        //向 Thread 类传入实现Runnable接口的dog对象，让thread对象调用run方法
        //底层使用了设计模式[代理模式]，
        Thread thread = new Thread(dog);
        thread.start();
    }
}


class DogBark implements Runnable {
    int count = 0;
    @Override
    public void run() {
        while (count != 10) {//下方代码++count，因此输出10次后停止。这种写法仅做演示，开发中不常用
            System.out.println("小狗汪汪叫" + (++count) + " 线程名" + Thread.currentThread().getName());
            //休眠一秒
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//线程代理类的工作机理：模拟极简的Thread类

class ThreadProxy implements Runnable{
    private Runnable target;//接口的多态：不能new，但是类型可以指向一个对象

    @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }

    public ThreadProxy(Runnable target) {
        this.target = target;
    }

    public void start() {
        start0();//这个方法真正实现多线程
    }

    public void start0() {
        run();
    }
}

class AnimalCall{}
class LionRoar extends AnimalCall implements Runnable{
    private int count;
    @Override
    public void run() {
        while (true) {
            System.out.println("狮子咆哮" + (++count) + " 线程名" + Thread.currentThread().getName());
            //休眠一秒
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //输出10次后停止
            if(count == 10) break;
        }
    }
}
class ThreadProxyExample{
    public static void main(String[] args) {
        LionRoar lionRoar = new LionRoar();
        ThreadProxy lion = new ThreadProxy(lionRoar);
        lion.start();
    }
}

/*
线程终止
基本说明
1.当线程完成任务后，会自动退出
2.还可以通过使用变量来控制run方法退出的方式停止线程，即[通知方式]
 */
class ThreadExit {
    public static void main(String[] args) throws InterruptedException {
        LoopThread t1 = new LoopThread();
        t1.start();

        //如果希望main线程去控制t1 线程的终止, 必须可以修改 loop
        //让t1 退出run方法，从而终止 t1线程 -> 通知方式

        //让主线程休眠 10 秒，再通知 t1线程退出
        System.out.println("main线程休眠10s...");
        Thread.sleep(10 * 1000);
        t1.setLoop(false);
    }
}

class LoopThread extends Thread {
    private int count = 0;
    //设置一个控制变量
    private boolean loop = true;
    @Override
    public void run() {
        while (loop) {

            try {
                Thread.sleep(50);// 让当前线程休眠50ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T 运行中...." + (++count));
        }

    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}



/*
常用方法第一组
1.setName       //设置线程名称，使之与参数 name 相同
2.getName       //返回该线程的名称
3.start         //使该线程开始执行Java虚拟机底层调用该线程的 start0方法
4.run           //调用线程对象 run 方法；
5.setPriority   //更改线程的优先级
6.getPriority   //获取线程的优先级
7.sleep         //在指定的毫秒数内让当前正在执行的线程休眠（暂停执行）
8.interrupt     //中断线程

注意事项：
1.start底层会创建新的线程，调用run，run是一个简单的方法调用，不会启动新线程
2.线程优先级的范围：MAX_PRIORITY=10，MIN_PRIORITY=1，NORM_PRIORITY=5
3.interrupt，中断线程，但并没有真正的结束线程。所以一般用于中断正在休眠的线程
4.sleep:线程的静态方法，使当前线程休眠
 */
class ThreadMethod01 {
    public static void main(String[] args) throws InterruptedException {
        //测试相关的方法
        CustomThread01 t = new CustomThread01();
        t.setName("方法一");
        t.setPriority(Thread.MIN_PRIORITY);//1
        t.start();//启动子线程


        //主线程打印5个hi ,然后就中断 子线程的休眠
        for(int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            System.out.println("hi " + i);
        }
        //线程的优先级越低，意味着被调度的概率越小，并不是说最后执行，只是改变调度的概率
        System.out.println(t.getName() + " 线程的优先级 =" + t.getPriority());//1

        t.interrupt();//当执行到这里，就会中断 t线程的休眠.



    }
}

class CustomThread01 extends Thread { //自定义的线程类
    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < 20; i++) {
                //Thread.currentThread().getName() 获取当前线程的名称
                System.out.println(String.format("%s 线程第 %d 次加载", Thread.currentThread().getName(), i));
            }
            try {
                System.out.println(Thread.currentThread().getName() + " 休眠中~~~");
                Thread.sleep(20000);//20秒
            } catch (InterruptedException e) {
                //当该线程执行到一个interrupt 方法时，就会catch 一个 异常, 可以加入自己的业务代码
                //InterruptedException 是捕获到一个中断异常（不是进程终止）
                System.out.println(Thread.currentThread().getName() + "被 interrupt了");
            }
        }
    }
}

/*
常用方法第二组（线程协调）
1.yield：线程的礼让。让出cpu，让其他线程执行，但礼让的时间不确定，所以也不一定礼让成功（由CPU决定）
2.join：线程的插队。插队的线程一旦插队成功，则肯定先执行完插入的线程所有的任务
案例：main线程创建一个子线程，每隔1s输出hello.输出20次，主线程每隔1秒，输出hi，输出20次.要求：两个线
程同时执行，当主线程输出5次后，就让子线程运行完，主线程再继续
 */

class ThreadMethod02 {
    public static void main(String[] args) throws InterruptedException {

        CustomThread02 t2 = new CustomThread02();
        t2.start();

        for(int i = 1; i <= 20; i++) {
            Thread.sleep(1000);
            System.out.println("货列进入第 " + i  + " 个区间");
            if(i == 5) {
                System.out.println("货列进入车站让行");
                //join, 线程插队
                //t2.join();// 这里相当于让t2 线程先执行完毕

                Thread.yield();//礼让，不一定成功..
                System.out.println("让行完毕，货列启动");
            }
        }
        System.out.println("货列运行完成");
    }
}

class CustomThread02 extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 20; i++) {
            try {
                Thread.sleep(1000);//休眠1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("客车经过第 " + i +  " 个区间");
        }
        System.out.println("客车运行完成");
    }
}
/*
用户线程和守护线程
1.用户线程：也叫工作线程，当线程的任务执行完或以通知的方式结束
2.守护线程：一般是为工作线程服务的，当所有的用户线程结束，守护线程自动结束
3.常见的守护线程：垃圾回收机制
设置为守护线程的方法：setDaemon(boolean)
 */

class ThreadMethod03 {
    public static void main(String[] args) throws InterruptedException {
        MyDaemonThread myDaemonThread = new MyDaemonThread();
        //如果我们希望当main线程结束后，子线程自动结束
        //只需将子线程设为守护线程即可
        myDaemonThread.setDaemon(true);
        myDaemonThread.start();

        for( int i = 1; i <= 10; i++) {//main线程
            System.out.println("宝强在辛苦的工作...");
            Thread.sleep(1000);
        }
    }
}

class MyDaemonThread extends Thread {
    public void run() {
        for (; ; ) {//无限循环
            try {
                Thread.sleep(1000);//休眠1000毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("马蓉和宋喆快乐聊天，哈哈哈~~~");
        }
    }
}

/*
线程的生命周期
JDK中用Thread.State枚举表示了线程的几种状态

public static enum Thread.state extends Enum<Thread.State>

线程状态。线程可以处于以下状态之一
NEW（新建）：尚未启动的线程处于此状态
new -> NEW状态 -> start()方法 ->RUNNABLE状态

RUNNABLE（可运行）：在Java虚拟机中执行的线程处于此状态
重点：因为Runnable受线程调度器的影响，所以可以细分为Ready就绪(线程被挂起/Thread.yeild)和Running运行(线程被调度器选中运行)两种状态

BLOCKED（阻塞）：被阻塞等待监视器锁定的线程处于此状态。
RUNNABLE状态 -> 等待进入同步代码块的锁 -> BLOCKED状态 -> 获得锁 -> RUNNABLE状态

WAITING（等待）：正在等待另一个线程执行特定动作的线程处于此状态，
RUNNABLE状态 -> o.wait()/t.join()/LockSupport.park() -> WAITING状态 -> o.notify()/o.notifyAll()/LockSupport.unpark() -> RUNNABLE状态

TIMED WAITING（超时）：正在等待另一个线程执行动作达到指定等待时间的线程处于此状态
RUNNABLE状态 -> Thread.sleep(time)/o.wait(time)/t.join(time)/LockSupport.parkNanos()/LockSupport.parkUntil() -> TIMEDWAITING状态 -> 时间结束 -> RUNNABLE状态

TERMINATED（终止）：已退出的线程处于此状态
 */

class ThreadStateShows {
    public static void main(String[] args) throws InterruptedException {
        CustomThread03 t = new CustomThread03();//此时状态为NEW
        System.out.println(t.getName() + " 状态 " + t.getState());
        t.start();//状态为RUNNABLE

        while (Thread.State.TERMINATED != t.getState()) {
            System.out.println(t.getName() + " 状态 " + t.getState());
            Thread.sleep(500);
        }

        System.out.println(t.getName() + " 状态 " + t.getState());//此时状态为TERMINATED

    }
}

class CustomThread03 extends Thread {
    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < 10; i++) {
                System.out.println("hi " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }
}

