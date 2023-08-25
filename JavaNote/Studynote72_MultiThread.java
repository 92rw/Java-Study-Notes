
//使用多线程，模拟三个窗口同时售票，票额100张

//方法1：继承Thread类
class SellTicket01Test{
    public static void main(String[] args) {
        //测试
        SellTicket01 window1 = new SellTicket01();
        SellTicket01 window2 = new SellTicket01();
        SellTicket01 window3 = new SellTicket01();

        //启动售票过程,会出现超卖现象
        window1.start();
        window2.start();
        window3.start();
    }
}
class SellTicket01 extends Thread{
    //因为需要创建3个对象，因此票额需要设置为静态属性
    private static int ticketAmount = 100;
    private static int ticketSold;

    @Override
    public void run() {
        while (true) {
            if(ticketAmount <= 0) {
                System.out.println(Thread.currentThread().getName() + "票额已售罄");
                break;
            }

            //休眠50毫秒，模拟售票过程
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(String.format("窗口 %s 售出一张票，合计已售出 %d 张，票库现有 %d 张",Thread.currentThread().getName(),(++ticketSold),(--ticketAmount)));
        }
    }
}

//方法2：实现Runnable接口
class SellTicket02Test{
    public static void main(String[] args) {
        SellTicket02 ticket = new SellTicket02();
        new Thread(ticket).start();
        new Thread(ticket).start();
        new Thread(ticket).start();
    }
}
class SellTicket02 implements Runnable{
    private int ticketAmount = 100;
    private int ticketSold;
    @Override
    public void run() {
        while (true) {
            if(ticketAmount <= 0) {
                System.out.println(Thread.currentThread().getName() + "票额已售罄");
                break;
            }

            //休眠50毫秒，模拟售票过程
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(String.format("窗口 %s 售出一张票，合计已售出 %d 张，票库现有 %d 张",Thread.currentThread().getName(),(++ticketSold),(--ticketAmount)));
        }
    }
}
/*
Synchronized
线程同步机制
1.在多线程编程，一些敏感数据不允许被多个线程同时访问，此时就使用同步访问技术，保证数据在任何同一时刻，最多有一个线程访问，以保证数据的完整性。
2.也可以这里理解：线程同步，即当有一个线程在对内存进行操作时，其他线程都不可以对这个内存地址进行操作，直到该线程完成操作，其他线程才能对该内存地址进行操作

同步具体方法-Synchronized
1.同步代码块
synchronized（对象）{//得到对象的锁，才能操作同步代码
//需要被同步代码;
}
2.synchronized还可以放在方法声明中，表示整个方法-为同步方法
public synchronized void m (String name){
//需要被同步的代码
}
//synchronized是串行化 即顺序型程序 不是并发型程序 就能实现排队效果

//一个线程在释放锁之后会立即再次加入拼抢。所以如果某个线程一直赢得竞争，看起来就只有一个线程在执行。解决方案是在同步块之外手动设置等待。


互斥锁
基本介绍
1.在Java语言中，引入了对象互锁的概念，来保证共享数据操作的完整性
2.每个对象都对应于一个可称为“互斥锁”的标记，这个标记用来保证在任一时刻，只能有一个线程访问该对象。
3.关键字synchronized来与对象的互斥锁联系。当某个对象用synchronized修饰时表明该对象在任一时刻只能由一个线程访问
4.同步的局限性：导致程序的执行效率要降低
5.同步方法（非静态的）的锁可以是this，也可以是其他对象（要求是同一个对象）
6.同步方法（静态的）的锁为当前类本身。

与对象锁对应的是类锁，这个可以用来锁住基于同一个类继承方式实现多线程的每个线程

注意事项和细节
1.同步方法如果没有使用static修饰：默认锁对象为this
2.如果方法使用static修饰，默认锁对象：当前类.class
3.代码块中的锁是加在对象上的，不是在具体代码中的
4.实现的落地步骤：
需要先分析上锁的代码
选择同步代码块或同步方法
要求多个线程的锁对象为同一个即可！


 */

//方法3：使用synchronized 的方法并实现Runnable接口

class SellTicket03Test{
    public static void main(String[] args) {
        SellTicket03 ticket = new SellTicket03();
        new Thread(ticket).start();
        new Thread(ticket).start();
        new Thread(ticket).start();
    }
}
class SellTicket03 implements Runnable{
    private int ticketAmount = 100;
    private int ticketSold;
    private boolean loop = true;
    @Override
    public void run() {
        while (loop) {
           sell();
        }
    }

    //一个对象对应着一个互斥锁，这里目的是任意时刻只让一个线程访问，所以哪个对象当锁都行，能锁住就可以，不是必须当前对象，
    //只要是同一个对象，同一把锁就行。在同一时刻只能有一个线程执行sell方法，可以用两种思路：
    private /*synchronized*/ void sell() { //思路1：设置为synchronized的同步方法，
        synchronized (this) {//思路2：同步代码块，互斥锁在this对象上
            //synchronized修饰的是一个代码块，括号内的参数this表示当前对象，因此该代码块也称为对象锁。同一时间只有一个线程可以获得该对象的锁，其他线程需要等待。
            if(ticketAmount <= 0) {
                System.out.println(Thread.currentThread().getName() + "票额已售罄");
                loop = false;
                return;
            }

            //休眠50毫秒，模拟售票过程
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(String.format("窗口 %s 售出一张票，合计已售出 %d 张，票库现有 %d 张",Thread.currentThread().getName(),(++ticketSold),(--ticketAmount)));
        }
    }
}

//方法4：使用synchronized 的方法并继承Thread类
//synchronized是对象锁，锁住对象，不同实例的锁互不影响，因此需要给sell方法设置static，
//为的就是只创建一块相同内存，再让三个线程共同处理，否则创建的对象各自会调用各自的方法，这样同步了也没有用
//用继承的，重点在于sell要加static，因为Thread是三个对象，那么sell也是属于三个不同对象的方法，加锁就不管用了，必须设置成静态，让它作为属于类的方法，这样不管几个对象调，调的都是同一个
//由于sell方法调用了loop、ticketAmount等属性，导致这些属性也需要设置为静态变量
class SellTicket04Test{
    public static void main(String[] args) {
        //测试
        SellTicket04 window1 = new SellTicket04();
        SellTicket04 window2 = new SellTicket04();
        SellTicket04 window3 = new SellTicket04();

        //启动售票过程,会出现超卖现象
        window1.start();
        window2.start();
        window3.start();
    }
}
class SellTicket04 extends Thread{
    //因为需要创建3个对象，因此票额需要设置为静态属性
    private static int ticketAmount = 100;
    private static int ticketSold;
    private static boolean loop = true;

    private synchronized static void sell(){
        if(ticketAmount <= 0) {
            System.out.println(Thread.currentThread().getName() + "票额已售罄");
            loop = false;
            return;
        }

        //休眠50毫秒，模拟售票过程
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("窗口 %s 售出一张票，合计已售出 %d 张，票库现有 %d 张",Thread.currentThread().getName(),(++ticketSold),(--ticketAmount)));

    }
    @Override
    public void run() {
        while (loop) {
          sell();
        }
    }
}

//静态变量优先于对象存在的，类被加载到内存中时，被static修饰的变量也会随着类的加载而加载到内存中
//静态方法使用的时候尚未实例化对象，this针对的是对象，所以只能用类来当锁，不能用this做锁
//同步方法和同步代码块的锁是加在 ShowSynchronized.class上的
class ShowSynchronized{
    public synchronized static void m1(){}//同步方法
    public static void m2(){
        synchronized (ShowSynchronized.class){//同步代码块
            System.out.println("m2");
        }
    }

}

