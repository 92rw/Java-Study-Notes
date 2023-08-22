import javax.swing.*;
import java.awt.*;
import java.awt.Color;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/*
坐标原点位于左上角，以像素为单位。
在Java坐标系中，第一个是x坐标，表示当前位置为水平方向，距离坐标原点x个像素；第二个是y坐标，表示当前位置为垂直方向，距离坐标原点y个像素

计算机在屏幕上显示的内容都是由屏幕上的每一个像素组成的。例如，计算机显示器的分辨率是800×600，表示计算机屏幕上的每一行由800个点组成，共有600行
整个计算机屏幕共有480000个像素。像素是一个密度单位，而厘米是长度单位，两者无法比较

还有一个参数叫点距，引入以后像素就能转换成长度了

Java绘图技术
绘图原理
Component类提供了两个和绘图相关最重要的方法
1. paint(Graphics g)绘制组件的外观
2.repaint()刷新组件的外观。

当组件第一次在屏幕显示的时候,程序会自动的调用paint()方法来绘制组件
在以下情况paint()将会被调用：
1.窗口最小化,再最大化
2.窗口的大小发生变化
3.repaint方法被调用
 */

class DrawCircle extends JFrame { //JFrame对应窗口,可以理解成是一个画框

    //定义一个面板
    private DrawPanel mp = null;

    public static void main(String[] args) {
        new DrawCircle();
        System.out.println("退出程序~");
    }

    public DrawCircle() {//构造器
        //这个this是因为继承了Jframe,并且JFrame里面的属性是public，所以这个this用的是父类定义过的属性
        //这里也体现出引入this的方便之处小括号里的是方法的局部变量，this 的是类的属性（this可以精准定位到属性，不会被局部变量影响）

        //初始化面板
        mp = new DrawPanel();
        //把面板放入到窗口(画框)
        this.add(mp);
        //设置窗口的大小
        this.setSize(400, 300);
        //当点击窗口的小×，程序完全退出.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);//可以显示
    }
}

class DrawPanel extends JPanel { //JPanel 可以理解成一个画板
    //初始化一个Graphics的实例化对象

    @Override
    public void paint(Graphics g) {//绘图方法，Graphics g 把 g 理解成一支画笔
        super.paint(g);//调用父类的方法完成初始化.
        System.out.println("paint 方法被调用了~");
        //画出一个圆形.
        //g.drawOval(10, 10, 100, 100);
        //应该这么说 java中画圆都是按照内切圆来画的，前两个值为图形的x，y坐标，后两个值为宽度和高度。宽度=高度则是圆形

        //参考jdk帮助文档，演示绘制不同的图形..
        //画直线 drawLine(int x1,int y1,int x2,int y2)
        //g.drawLine(10, 10, 100, 100);//起始点和结束点的坐标
        //画矩形边框 drawRect(int x, int y, int width, int height)
        //g.drawRect(10, 10, 100, 100);//左上角坐标和长宽数值
        //画椭圆边框 drawOval(int x, int y, int width, int height)
        //填充矩形 fillRect(int x, int y, int width, int height)
        //设置画笔的颜色
//        g.setColor(Color.blue);
//        g.fillRect(10, 10, 100, 100);

        //填充椭圆 fillOval(int x, int y, int width, int height)
//        g.setColor(Color.red);
//        g.fillOval(10, 10, 100, 100);

        //画图片 drawImage(Image img, int x, int y, ..)
        //1. 获取图片资源, /bg.png 表示在该项目的根目录去获取 bg.png 图片资源
//        Image image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bg.png"));
//        g.drawImage(image, 10, 10, 175, 221, this);
        //画字符串 drawString(String str, int x, int y)//写字
        //给画笔设置颜色和字体
        g.setColor(Color.red);
        g.setFont(new Font("隶书", Font.CENTER_BASELINE, 60));
        //这里设置的 50， 100， 是 "北京你好"左下角
        g.drawString("青岛北站", 50, 100);
        //设置画笔的字体 setFont(Font font)
        //设置画笔的颜色 setColor(Color c)


    }
}

 
//绘制黑色小球，可通过按键移动
class drawBall extends JFrame{
    EventPanel mp = null;
    //其实也可以在main内创建两个对象实例，这种放在构造器的写法，是你先new了一个，然后在主方法内new一个
    public static void main(String[] args) {
        new drawBall();
    }
    public drawBall() {
        mp = new EventPanel();
        this.add(mp);
        this.setSize(400,300);
        this.addKeyListener(mp);//监听键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

//继承 JPanel 类的画布，实现 KeyListener 接口监听键盘事件
//对于实现接口的类，需要快速重写方法，可以使用快捷键 Ctrl + I
class EventPanel extends JPanel implements KeyListener {

    //为了让小球移动，将其左上角坐标(x,y)设置成变量
    int x = 10;
    int y = 10;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillOval(x, y,20,20);
    }

    //触发条件：有字符输入
    @Override
    public void keyTyped(KeyEvent e) {

    }
    //触发条件：有按键按下
    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println((char)e.getKeyCode() + "被按下");//如果不转成char字符，则输出的是数字编号


        //根据用户按键处理小球移动
        //Java 会给每个按键分配一个 ASCII 码值
        //如果需要WSAD键操作，使用getKeyChar方法也行
        switch (e.getKeyCode()){
            case KeyEvent.VK_DOWN:
                y+=5;
                break;
            case KeyEvent.VK_UP:
                y-=5;
                break;
            case KeyEvent.VK_LEFT:
                x-=5;
                break;
            case KeyEvent.VK_RIGHT:
                x+=5;
                break;
        }

        //paint方法不仅随着窗口移动以及随着每次窗口的操作都会刷新 所以 小球会移动
        this.repaint();
    }
    //触发条件：有按键松开
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
