# AWT编程（二）

## 窗体加入面板

很多应用程序并不只是由单一的布局组成的，而是多种布局相互嵌套的结果，而一个窗体只能设置一种布局。此时可以使用其他容器类：面板。

面板是最简单的容器类，它跟窗口一样，可以提供一个空间，同样可以随意添加组件到面板中，只不过面板本身也是一个组件，所以说面板是可以放到其他容器中的容器。面板本身也是容器，所以说也可以单独设置面板内部的布局，通过添加不同面板，可以实现展示效果：

```java
public class FranceFlag {
    public static void main(String[] args) {
        Frame frame = new Frame("France Flag");

        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);             //展示窗口
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        GridLayout layout = new GridLayout();   //窗体设置为网格布局
        layout.setColumns(3);     //设置列数为3
        frame.setLayout(layout);

        Panel left = new Panel();
        left.setBackground(new Color(0,85,164));   //添加一个背景颜色方便区分
        frame.add(left);

        Panel middle = new Panel();
        middle.setBackground(Color.WHITE);
        frame.add(middle);

        Panel right = new Panel();
        right.setBackground(new Color(239,65,53));
        frame.add(right);
    }
}

```

加入窗体的面板本身也可以设置布局

```java
        GridLayout layout = new GridLayout();   //先设置整个窗口的布局
        layout.setRows(2);     //设置行数为2
        frame.setLayout(layout);

        Panel top = new Panel();
        top.setBackground(new Color(0,87,183));
        top.setLayout(new FlowLayout());    //面板默认会采用FlowLayout，所以说这里指不指定都一样
        for (int i = 0; i < 5; i++)    //面板就像窗口一样，可以设定布局和添加组件
            top.add(new Button("FlowLayout " + i));

        Panel bottom = new Panel();
        bottom.setBackground(new Color(255,215,0));
        bottom.setLayout(new GridLayout());   //下半部分采用网格布局
        for (int i = 0; i < 5; i++)
            bottom.add(new Button("GridLayout " + i));

        frame.add(top);
        frame.add(bottom);
```



## 滚动面板

窗口大小可能并不能完全显示内部的内容，比如出现了一张很大的图片。此时就会出现滚动条来让我们进行拖拽，这样就可以向下滑动查看没有完全展示出来的内容了。

AWT也为我们提供了滚动面板组件，滚动面板也是一个容器，但是我们无法修改它的布局，它只能容纳单个组件，比如展示一个图片、或者是列表等，我们也可以将其与Panel配合使用

```java
        ScrollPane scrollPane = new ScrollPane();   //创建滚动面板
        frame.add(scrollPane);

        GridLayout layout = new GridLayout();    //创建滚动面板内部的要展示的面板
        layout.setRows(20);
        Panel panel = new Panel();
        panel.setLayout(layout);
        for (int i = 0; i < 20; i++) panel.add(new Button("Button" + i));   //为面板添加按钮
        scrollPane.add(panel);
```

上方代码中，按钮大小采用的是自动生成的大小，可以在for循环中为组件设定一个建议的大小

```java
for (int i = 0; i < 20; i++) {
    Button button = new Button("Button" + i);
    button.setPreferredSize(new Dimension(100, 50));   //设置首选大小
    panel.add(button);
}
```

滚动面板的最佳搭档就是List列表

```java
        ScrollPane scrollPane = new ScrollPane();   //创建滚动面板
        frame.add(scrollPane);

        List list = new List();   //注意是awt包下的List
        list.add("East");
        list.add("West");
        list.add("South");
        list.add("North");
        list.setMultipleMode(true);   //是否开启多选模式，按Shift可以按范围选中
        scrollPane.add(list);

        list.addItemListener(System.out::println);
```



## 菜单栏

编写AWT程序也可以添加不同类型的菜单，只需要为窗口设定一个菜单栏

```java
        MenuBar bar = new MenuBar();    //创建菜单栏
        Menu menu = new Menu("File");
        menu.add(new CheckboxMenuItem("Select"));//可勾选的菜单项
        MenuItem menuClose = new MenuItem("Close");
        menu.add(menuClose);
        bar.add(menu);
        frame.setMenuBar(bar);    //为窗口设定刚刚定义好的菜单栏
```

菜单栏也可以设置鼠标和键盘监听器

```java
        //设置鼠标监听器
        menuClose.addActionListener(e -> System.exit(0));
        //设定键盘快捷键
        menuClose.setShortcut(new MenuShortcut('F'));   //MenuShortcut就是指定快捷键组合，默认情况下是Ctrl+指定按键
        //menuClose.setShortcut(new MenuShortcut('A', true));   //第二个参数指定为true表示需要Ctrl+Shift+指定按键
```



## 弹出菜单

弹出一个浮在窗口之上的，并且可以进行选择的菜单，这个就是弹出菜单。

```java
        PopupMenu popupMenu = new PopupMenu();    //创建弹出菜单
        popupMenu.add(new MenuItem("Option 1"));   //每一个选项依然是使用MenuItem
        popupMenu.add(menuClose);//一个菜单项可以在多个菜单中使用
        frame.add(popupMenu);    //注意，弹出菜单也要作为组件加入到窗口中（但是默认情况下不显示）

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {  //监听鼠标右键
                    popupMenu.show(frame, e.getX(), e.getY());   //要展示弹出菜单，我们只需要调用show方法即可
                    //注意，第一个参数必须是弹出菜单所加入的窗口或是窗口中的任意一个组件
                    //后面的坐标就是相对于这个窗口或是组件的原点（左上角）这个位置进行弹出
                    //我们这里写的就是相对于当前窗口的左上角，鼠标点击位置的x、y位置弹出窗口
                }
            }
        });
```



## 弹出对话框

可以使用AWT为我们提供的对话框，比如我们现在希望在关闭窗口时询问我们是否真的要关闭：调用WindowListener

```java
        //第一个参数是父窗口或是父对话框（对话框也可以由对话框唤起）
        //最后一个参数是当对话框展示时，是否让父窗口（对话框）无法点击
        Dialog dialog = new Dialog(frame, "Caution", true);
        //设置对话框的大小和位置
        dialog.setSize(200, 80);
        dialog.setLocationRelativeTo(null);

        //关闭窗体时，弹出对话框
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(true);
            }
        });
        //关闭对话框时，隐藏对话框
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.dispose();
                //dialog.setVisible(false);//另一种写法
            }
        });
```

可以向对话框中增加按钮

```java
Dialog dialog = new Dialog(frame, "Comfirm Exit", true);
dialog.setResizable(false);
dialog.add(new Label("Confirm to exit?"), BorderLayout.NORTH);   //对话框默认采用的是边界布局
dialog.add(new Button("No"), BorderLayout.WEST);
dialog.add(new Button("Yes"), BorderLayout.EAST);
dialog.setSize(200, 80);

```



## 文件对话框

根据操作系统提供的文件选择器，直接获取到对应的文件

```java
        FileDialog dialog = new FileDialog(frame, "请选择一个文件", FileDialog.LOAD);  //选择文件对话框类型，可以是加载文件或是保存文件
        MenuItem menuOpen = new MenuItem("Open");
        menu.add(menuOpen);
        menuOpen.addActionListener(e -> {
            dialog.setVisible(true);   //注意，需要将对话框展示出来之后，才能进行选择
            //选择完成之后getDirectory和getFile方法就可以返回结果了，否则会阻塞
            System.out.println("选择的文件为："+dialog.getDirectory() + dialog.getFile());
        });

```



## 自定义组件

除了使用官方提供的这些组件之外，我们也可以自己创建一些组件来使用，

要自己编写一个组件，需要完成下面的步骤：

- 必须继承自Component类，表示这是一个AWT组件。
- 重写`paint`方法，这个方法就是组件的绘制方法，最终绘制出来的结果就是展示出来的结果了。

在使用时，先向该组件设置边框，然后在主窗口调用`add()`方法添加该组件即可。

```java
ExampleComponent example = new ExampleComponent();
example.setBounds(20,50,200,200);
frame.add(example);
```



### 绘制形状

paint方法传入一个Graphics对象，实际上这个对象就是我们用于绘制图形的工具

```java
public abstract class Graphics {
   	//移动画笔原点到指定坐标，默认是(0,0)
    public abstract void translate(int x, int y);
    //设定画笔颜色
    public abstract void setColor(Color c);
    //设置为普通绘画模式
    public abstract void setPaintMode();
    //交替颜色模式，比较高级，小伙伴自行了解
    public abstract void setXORMode(Color c1);
    //设置字体，绘制文本内容时就按照这个字体来绘制
    public abstract void setFont(Font font);

    //设置裁剪区域，一旦设置裁剪区域，那么裁剪区域以外的地方即使绘制，也不会生效，绘制
  	//只会在裁剪区域内生效（有点像图层蒙版？）
    public abstract void setClip(int x, int y, int width, int height);
    //设定自定义形状的裁剪区域
    public abstract void setClip(Shape clip);

    //拷贝指定区域的内容到另一个位置
    public abstract void copyArea(int x, int y, int width, int height,
                                  int dx, int dy);
    //绘制直线：始点和结束点的坐标
    public abstract void drawLine(int x1, int y1, int x2, int y2);
    //填充矩形区域：左上角坐标和长宽数值
    public abstract void fillRect(int x, int y, int width, int height);
    //绘制矩形边框
    public void drawRect(int x, int y, int width, int height);
	//绘制圆角矩形边框
    public abstract void drawRoundRect(int x, int y, int width, int height,
                                       int arcWidth, int arcHeight);
    //填充圆角矩形区域
    public abstract void fillRoundRect(int x, int y, int width, int height,
                                       int arcWidth, int arcHeight);
    //绘制3D矩形边框（其实就是加了个深色和浅色边框，有一个视觉效果罢了）
    public void draw3DRect(int x, int y, int width, int height,
                           boolean raised);
    //填充3D矩形区域（同上）
    public void fill3DRect(int x, int y, int width, int height,
                           boolean raised);
    //绘制椭圆形边框：java中画圆都是按照内切圆来画的，前两个值为图形的x，y坐标，后两个值为宽度和高度。宽度=高度则是圆形
    public abstract void drawOval(int x, int y, int width, int height);
    //填充椭圆形区域
    public abstract void fillOval(int x, int y, int width, int height);
    //绘制弧线边框
    public abstract void drawArc(int x, int y, int width, int height,
                                 int startAngle, int arcAngle);
	//填充扇形区域
    public abstract void fillArc(int x, int y, int width, int height,
                                 int startAngle, int arcAngle);
    //绘制折线（需要提供多个坐标）
    public abstract void drawPolyline(int xPoints[], int yPoints[],
                                      int nPoints);
	//绘制多边形边框
    public abstract void drawPolygon(int xPoints[], int yPoints[],
                                     int nPoints);
    //填充多边形区域
    public abstract void fillPolygon(int xPoints[], int yPoints[],
                                     int nPoints);
    //绘制文本（xy坐标是字符的左下角定位点）
    public abstract void drawString(String str, int x, int y);
   	//绘制图片（绘制大小为图片原本大小）
    public abstract boolean drawImage(Image img, int x, int y,
                                      ImageObserver observer);
   	//绘制按自定义大小缩放后的图片
    public abstract boolean drawImage(Image img, int x, int y,
                                      int width, int height,
                                      ImageObserver observer);
    //绘制图片时如果是透明部分则采用背景颜色填充
    public abstract boolean drawImage(Image img, int x, int y,
                                      Color bgcolor,
                                      ImageObserver observer);
    //绘制按自定义大小缩放后带背景颜色的图片
    public abstract boolean drawImage(Image img, int x, int y,
                                      int width, int height,
                                      Color bgcolor,
                                      ImageObserver observer);
    //对原本的图片按照起始坐标和尺寸进行裁剪后，再以给定大小绘制到给定位置
    public abstract boolean drawImage(Image img,
                                      int dx1, int dy1, int dx2, int dy2,
                                      int sx1, int sy1, int sx2, int sy2,
                                      ImageObserver observer);
    //对原本的图片按照起始坐标和尺寸进行裁剪后，再以给定大小绘制到给定位置的带背景颜色的图片
    public abstract boolean drawImage(Image img,
                                      int dx1, int dy1, int dx2, int dy2,
                                      int sx1, int sy1, int sx2, int sy2,
                                      Color bgcolor,
                                      ImageObserver observer);
}
```

例如在黑色背景下绘制圆角矩形红色边框，可以重写为

```java
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.RED);    //画笔改成红色
        //在中间画个圆角矩形边框
        g.drawRoundRect(getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2, 30, 30);
    }
```



### 图片加载

官方没有提供图片组件，我们可以自己编写一个图片组件用于在窗口中展示我们的图片。

```java
public class ImageView extends Component {
    private final Image image;   
    public ImageView(String filename) throws IOException {
        File file = new File(filename);
        image = ImageIO.read(file);   //我们可以使用ImageIO类来快速将图片文件读取为Image对象
    }

    @Override
    public void paint(Graphics g) {
      	//绘制图片需要提供Image对象
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
```

设置自定义程序图标

程序默认是Java图标，可以使用ImageIO读取图片后作为程序图标（需抛出IO异常）。在MacOS下需要用另一种写法

```java
Image image = ImageIO.read(new File("test.png"));
frame.setIconImage(image);//Windows系统
//Application.getApplication().setDockIconImage(image);//MacOS
```



### 窗口修饰

通过将窗口设定为非修饰状态，可以去掉系统为我们提供的窗口边框，并修改显示出来的样式

```java
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame("Hello World"){    //使用匿名内部类（或者自己写个子类也行）
            @Override
            public void paint(Graphics g) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), 28);   //先绘制标题栏
                g.setColor(Color.BLACK);
                g.drawString(getTitle(), getWidth() / 2, 20);   //绘制标题名称
                super.paint(g);   //原本的绘制别覆盖了，该怎么做还要怎么做
            }
        };

        //编写监听器，实现标题栏拖动效果
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            int oldX, oldY;
            public void mouseDragged(MouseEvent e) {   //鼠标拖动时如果是标题栏，就将窗口位置修改
                if(e.getY() <= 28)
                    frame.setLocation(e.getXOnScreen() - oldX, e.getYOnScreen() - oldY);
            }

            public void mouseMoved(MouseEvent e) {   //记录上一次的鼠标位置
                oldX = e.getX();
                oldY = e.getY();
            }
        });


        frame.setBounds(500,500,500,400);
        //取消窗口修饰，并设置形状
        frame.setUndecorated(true);
        frame.setShape(new RoundRectangle2D.Double(0,0, frame.getWidth(), frame.getHeight(), 30,30));

        frame.setVisible(true);             //展示窗口
    }
}
```



## 参考资料

[柏码 - 让每一行代码都闪耀智慧的光芒！ (itbaima.cn)](https://www.itbaima.cn/document)