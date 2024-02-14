# AWT编程（一）

在 Java 正式推出的时候，它还包含一个用于基本 GUI 程序设计的类库Abstract Window Toolkit（抽象窗口工具包，简称AWT）用于桌面应用程序的开发，这套工具包依附于操作系统提供的UI，具体样式会根据不同操作系统提供的界面元素进行展示。

## 基本框架

在程序中新建一个窗口

```java
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame("Hello World");          //构造器可以传入标题
        frame.setTitle("我是标题");          //设置标题，可覆盖构造器设置的标题

        frame.setBackground(Color.CYAN);   //设置背景颜色，也可以new Color
        frame.setResizable(false);          //窗口大小是否固定
        frame.setAlwaysOnTop(true);         //窗口是否始终展示在最前面
		frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));//将光标设置成十字形

        frame.setVisible(true);             //展示窗口
    }
}
```

窗体本身是单独的线程，因此使用`Thread.sleep(10000);` 并不会导致窗体卡顿，程序会创建不同的队列处理相关任务

### 定位坐标的相关原理

在Java坐标系中，第一个是x坐标，表示当前位置为水平方向，距离坐标原点x个像素；第二个是y坐标，表示当前位置为垂直方向，距离坐标原点y个像素。

计算机在屏幕上显示的内容都是由屏幕上的每一个像素组成的，显示器的分辨率是800×600，表示计算机屏幕上的每一行由800个点组成，共有600行，整个计算机屏幕共有480000个像素。还有一个参数叫点距，引入以后像素就能转换成长度了

**注意：AWT窗体计算坐标定位时，是从包含窗体标题栏的左上角开始的**

```java
        frame.setSize(500,300);		//设置窗口大小，单位为像素点
        frame.setLocation(700,400); //不设置初始值时，默认出现在左上角
```

保持窗口居中的相关方法

```java
//方法1
    frame.setLocationRelativeTo(null);

//方法2
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	//通过（屏幕尺寸-窗体尺寸）/ 2 计算偏移值
    int x = (screenSize.width - frame.getWidth()) / 2;
    int y = (screenSize.height - frame.getHeight()) / 2;
    frame.setLocation(x, y);
```

可以使用一行代码同时完成 setLocation 方法的 (x, y) 参数和 setSize 方法的 (width, height) 参数设置

```java
frame.setBounds(700,400,500,300);//传入的参数为(x,y, width, height)
```

## 监听器

监听器会监听窗口中发生的一些事件，当对应的事件发生时调用监听器进行处理，从而我们能够在发生对应事件时进行对应处理

当前程序无法在点击窗体的关闭按钮时自动关闭，只能在控制台中停止运行，通过加入窗体的监听器可以实现关闭功能

```java
//如果向该方法传入WindowListener对象，会要求实现所有的方法，可以通过“适配器模式”传入WindowAdapter对象，只实现所需要的功能
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //frame.dispose();//第一种写法，程序在之后会调用windowClosed方法
                System.exit(0);//第二种写法
            }
        });
```

监听窗体的WindowListener接口可以设置的事件如下

```java
public interface WindowListener extends EventListener {
    public void windowOpened(WindowEvent e);//当窗口的可见性首次变成true时会被调用
    public void windowClosing(WindowEvent e);//点击窗体关闭按钮时调用
    public void windowClosed(WindowEvent e);//窗体成功关闭后调用
    public void windowIconified(WindowEvent e);//窗口最小化时被调用
    public void windowDeiconified(WindowEvent e);//窗口从最小化状态变成普通状态时调用
    public void windowActivated(WindowEvent e);//当窗口变成活跃状态时被调用
    public void windowDeactivated(WindowEvent e);//当窗口变成不活跃状态时被调用
}
```

addKeyListener和addMouseListener方法可以监听键盘和鼠标的操作

```java
public interface KeyListener extends EventListener {
    public void keyTyped(KeyEvent e);   //有字符输入
    public void keyPressed(KeyEvent e);   //有按键按下
    public void keyReleased(KeyEvent e);   //有按键松开
}
```

具体的代码实现如下

```java
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.print("键盘按下：" + e.getKeyChar());   //可以通过KeyEvent对象来获取当前事件输入的对应字符
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("鼠标点击：（" + e.getX() + "," + e.getY() + "）使用的部件：" + e.getButton());
            }
        });
```

通过e.getButton() 方法可以获取鼠标是使用哪个键点击的，我们的鼠标一般情况下有三个按键：

- BUTTON1 - 鼠标左键
- BUTTON2 - 鼠标中键
- BUTTON3 - 鼠标右键

## 窗体组件

由于默认情况下会采用BorderLayout作为布局，首先通过```frame.setLayout(null);``` 方法将布局置空

当窗体组件使用绝对坐标定义时，不会随窗体尺寸变化而自动调整坐标

### 标签

新建标签

```java
//添加标签只需要创建一个Label对象即可
		Label label = new Label("TextLabel");   
        //必须设定标签的位置和大小，否则无法展示出来。组件的位置是以整个窗口的左上角为原点开始计算的。
        label.setBounds(20, 50, 100, 20);
        frame.add(label);    //使用add方法添加组件到窗口中
```

获取当前系统的字体

```java
    Font[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    Arrays.stream(allFonts).forEach(System.out::println);
```

设置字体

使用setFont方法，一般对单个组件使用。如果对Frame对象使用，会作用于整个窗体。

```java
label.setFont(new Font("Times New Roman", Font.BOLD, 15));   //Font构造方法需要字体名称、字体样式（加粗、斜体）、字号
```

设置字体颜色

```java
label.setBackground(Color.BLACK);    //setBackground设定背景颜色，注意背景填充就是我们之前设定的大小
label.setForeground(Color.WHITE);    //setForeground是设定字体颜色
```

### 按钮

```java
        Button button = new Button("Button"); 
        button.setLocation(20, 100);
        button.setSize(100, 20);
        frame.add(button);
```

给按钮添加监听器，实现点击效果（由于ActionListener接口只有一个actionPerformed方法，可以使用Lambda表达式）

```java
button.addActionListener(e -> System.out.println("监听到 " + new Date(e.getWhen()) + " 按钮被点击"));
```

### 文本框

单行文本框

```java
        TextField field = new TextField();
        field.setBounds(20, 70, 100, 25);
        frame.add(field);
```

文本框可以显示成密码效果，也可以获取输入内容

```java
field.setEchoChar('*');   //setEchoChar设定展示字符，无论我们输入的是什么，最终展示出来的都是我们指定的字符
button.addActionListener(e -> System.out.println("文本框中的输入内容：" + field.getText()));
```

文本域

支持多行显示，同样可以通过getText方法获取输入的文本

```java
        TextArea textArea = new TextArea();
        textArea.setBounds(250,60,200,100);
        frame.add(textArea);
```



### 勾选框

checkbox可以使用getState方法获取到当前选中状态

```java
        Checkbox checkbox = new Checkbox("Selected");
        checkbox.setBounds(140, 70, 100, 30);   //这个大小并不是勾选框的大小，具体的勾选框大小要根据操作系统决定，跟Label一样，是展示的空间大小
        frame.add(checkbox);
```

单选框

```java
        CheckboxGroup group = new CheckboxGroup();   //创建单选框组

        Checkbox c1 = new Checkbox("One");
        c1.setBounds(20, 120, 50, 30);
        frame.add(c1);

        Checkbox c2 = new Checkbox("Two");
        c2.setBounds(100, 120, 50, 30);
        frame.add(c2);

        c1.setCheckboxGroup(group);    //多个单选框都可以添加到单选框组中
        c2.setCheckboxGroup(group);
```

可以通过 `group.getSelectedCheckbox().getLabel()` 方法得到当前选中的选框

## 窗体布局

布局可以根据自己的一些性质，对容器（这里可以是我们的窗口）内部的组件自动进行调整，包括组件的位置、组件的大小等。

约束只有在当前容器为对应布局时才可以使用。

设置布局后，组件的大小由布局效果决定

### 边界布局 BorderLayout

默认情况下，我们的窗口采用的是边界布局（BorderLayout），这种布局方式支持将组件放置到五个区域：

```java
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame("Layout Test");

        frame.setBounds(700,400,500,300);
        frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));//将光标设置成十字形

        frame.setVisible(true);             //展示窗口
        BorderLayout borderLayout = new BorderLayout();
        frame.setLayout(borderLayout);   //使用边界布局
        frame.add(new Button("1st Button"), BorderLayout.WEST);  //在添加组件时，可以在后面加入约束
        frame.add(new Button("2nd Button"), BorderLayout.EAST);
        frame.add(new Button("3rd Button"), BorderLayout.SOUTH);
        frame.add(new Button("4th Button"), BorderLayout.NORTH);
        frame.add(new Button("5th Button"), BorderLayout.CENTER);
    }
}
```

可以看到，分别在东、南、西、北、中心位置都可以添加组件，组件的大小会被自动调整，并且随着我们的窗口大小变化，组件的大小也会跟着自动调整，是不是感觉挺方便的？边界布局的性质：

- BorderLayout布局的容器某个位置的某个组件会直接充满整个区域。
- 如果在某个位置重复添加组件，只有最后一个添加的组件可见。
- 缺少某个位置的组件时，其他位置的组件会延伸到该位置。
- 默认情况下添加到CENTER

我们还可以调整组件之间的间距：

```java
        borderLayout.setHgap(50);   //Hgap是横向间距
        borderLayout.setVgap(50);   //Vgap是纵向间距
```



### 流式布局 FlowLayout

流式布局实际上就是按顺序排列的一种布局：

```java
frame.setLayout(new FlowLayout());   //采用流式布局
frame.add(new Button("1st Button"));
frame.add(new Button("2nd Button"));
frame.add(new Button("3rd Button"));
```

采用流式布局后，按钮会根据内容大小，自动调整为对应的大小，并且他们之间是有间距的。当我们对窗口大小进行调整时，流式布局也会进行自动调整。

```java
//在设定流式布局时指定对齐模式：
frame.setLayout(new FlowLayout(FlowLayout.RIGHT));   //指定为右对齐
```

我们同样可以使用Hgap和Vgap来设置组件之间的间距



### 卡片布局 CardLayout

将卡片作为一个容器中的每个组件，每次只能看到最顶上的这张卡片，但是我们可以将下层的卡片切到顶上来

```java
        CardLayout layout = new CardLayout();
        frame.setLayout(layout);
        frame.add(new Label("Card1"));
        frame.add(new Label("Card2"));
        try {
            while (true) {
                Thread.sleep(3000);
                layout.next(frame);    //我们需要使用CardLayout对象来进行切换
            }
        } catch (InterruptedException e) {
            System.out.println("发生异常");
        }
```

上面的代码可以让添加的标签每三秒就会变化一次，可以利用卡片布局来做一个类似跑马灯的效果



### 网格布局GridLayout

以矩形网格的形式对组件进行管理，默认情况下会生成一行按格子划分的相等区域

```java
        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(3);//手动指定行数
        frame.setLayout(gridLayout);
        for (int i = 0; i < 10; i++) frame.add(new Button("Button " + i));
```



### 网格包GridBagLayout

按照网格进行划分，但是一个组件可以同时占据多个网格、实现类似小键盘的效果



## 参考资料

[柏码 - 让每一行代码都闪耀智慧的光芒！ (itbaima.cn)](https://www.itbaima.cn/document)