# Swing组件（二）

## 容器类

**容器类**：

- `JPanel`：比AWT的`Panel`提供了更复杂的布局管理和绘画功能。
- `JScrollPane`：提供了滚动视图的容器，这在AWT中没有直接对应物。
- `JSplitPane`：允许用户通过拖动分隔条来重新分配两个组件的空间。
- `JTabbedPane`：提供了选项卡功能，用户可以在不同的面板间切换。
- `JToolBar`：提供了一个可以停靠的工具栏容器。

选项卡

JTabbedPane跟我们之前认识的Panel很像，相当于也是将我们的组件装进了内部，但是它可以同时装很多个，并且支持自由切换，类似选项卡的操作

```java
        JTabbedPane pane = new JTabbedPane();
        pane.setBounds(0, 0, 500, 300);
        pane.addTab("橙色面板", new JPanel(){{setBackground(Color.ORANGE);}});
        pane.addTab("粉色面板", new JPanel(){{setBackground(Color.PINK);}});
        frame.add(pane);
```

分隔面板

JSplitPane将一块完整的面板分割为两个部分，这样，我们就可以分别在左右两边进行操作了，而且中间的分割线是可以拖动的，实际上我们的IDEA也是这样的：

```java
        JSplitPane pane = new JSplitPane();
        pane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);  //设定为横向分割
//横向分割之后，我们需要指定左右两边的组件
        pane.setLeftComponent(new JPanel(){{setBackground(Color.ORANGE);}});
        pane.setRightComponent(new JPanel(){{setBackground(Color.PINK);}});
        frame.add(pane);
```

### 简易文件浏览器

根据上面的内容创建一个简单的文件浏览器，其中使用`JSplitPane`来分割显示文件树（`JTree`）和一个文本编辑区域（`JTextArea`）。当在`JTree`中选择文件时，通过树选择监听器（`TreeSelectionListener`），选中的文件内容将被读取并显示在`JTextArea`中。

```java
public class FileExplorer {
    public static void main(String[] args) {
        JFrame frame = new JFrame("文件浏览器");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setAlwaysOnTop(true);

        JTextArea area = new JTextArea();   //右边就是我们需要编辑的文本域

        File file = new File("src");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(file.getName());
        addNodes(root, file);

        JTree tree = new JTree(root);
        tree.addTreeSelectionListener(e -> {   //点击文件之后，我们需要变换编辑窗口中的文本内容，这里加个监听器
            area.setText("");   //先清空
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode != null) {
                // 获取与节点关联的File对象
                File selectedFile = (File) selectedNode.getUserObject();
                try (FileReader reader = new FileReader(selectedFile)) {
                    char[] chars = new char[128];   //直接开始读取内容
                    int len;
                    StringBuilder content = new StringBuilder();
                    while ((len = reader.read(chars)) > 0) {
                        content.append(chars, 0, len);
                    }
                    area.setText(content.toString());//将文件内容写入到文本域
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        JSplitPane pane = new JSplitPane();
        pane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);  //设定为横向分割
//横向分割之后，我们需要指定左右两边的组件
        pane.setLeftComponent(new JScrollPane(tree));   //文件树和编辑区域都套一个滚动面板，因为有可能会撑得很大
        pane.setRightComponent(new JScrollPane(area));

        frame.add(pane);
        frame.setVisible(true);
    }

    private static void addNodes(DefaultMutableTreeNode node, File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                // 直接将File对象作为用户对象
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(f) {
                    @Override
                    public String toString() {
                        // 只返回文件或目录的名称，而不是完整路径
                        return f.getName();
                    }
                };
                node.add(childNode);
                if (f.isDirectory()) {
                    addNodes(childNode, f); // 递归添加节点
                }
            }
        }
    }
}
```

## 窗体组件

**窗体组件**：

- `JFrame`：相比于AWT的`Frame`，`JFrame`提供了更多的功能，如设置图标、关闭操作等。
- `JDialog`：相比于AWT的`Dialog`，`JDialog`允许更灵活的对话框功能，包括模态和非模态对话框等。
- `JApplet`：用于创建可以嵌入网页的小应用程序（注意：随着Java插件的淘汰，这部分用途已经大大减少）。

在用户关闭窗体时的提示框

```java
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //int value = JOptionPane.showConfirmDialog(frame, "你真的要退出吗？");
                int value = JOptionPane.showConfirmDialog(frame, "你真的要退出吗？", "退出程序", JOptionPane.YES_NO_OPTION);
                if(value == JOptionPane.OK_OPTION)    //返回值就是用户的选择结果，也是预置好的，这里判断如果是OK那么就退出
                    System.exit(0);
            }
        });
```

还有其他类型的窗体

```java
//消息提示框
JOptionPane.showMessageDialog(frame, "我是简单的提示消息！");

//获取用户输入信息
String str = JOptionPane.showInputDialog("毕业后的你，将何去何从呢？");
System.out.println(str);
```



## 皮肤组件

组件UI也是可以换皮肤的，官方名称叫做LookAndFeel，Swing官方为我们提供了很多套皮肤，这些皮肤都是可以跨平台的，当然也有某些平台专属的限定皮肤：

- MetalLookAndFeel - 官方默认皮肤
- WindowsLookAndFeel - Windows操作系统限定皮肤，其他平台无法使用
- MotifLookAndFeel - 官方皮肤
- NimbusLookAndFeel - 官方皮肤
- AquaLookAndFeel - MacOS操作系统限定皮肤，其他平台无法使用

可以在Main方法添加静态代码块，完成全局换皮肤效果

```java
    static {
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
```

Swing组件的绘制并不是由组件本身编写的，而是在各个UI实现类中编写的，所以说要修改组件样式只需要更换皮肤即可。

除了全局设定皮肤之外，我们也可以单独对某些组件设定皮肤，每个组件都有自己的`getUI`方法，这个方法就是获取当前组件使用的UI样式的，还可以对某个指定组件进行UI样式修改

```java
public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("样式测试");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setLayout(null);
        frame.setAlwaysOnTop(true);

        JLabel label = new JLabel("你好");//右边就是我们需要编辑的文本域
        label.setForeground(Color.red);
        label.setFont(new Font("FangSong", Font.BOLD, 25));
        label.setBounds(40,40,100,20);
        frame.add(label);

        JCheckBox checkBox = new JCheckBox("好得很");
        checkBox.setBounds(40,80,100,20);
        frame.add(checkBox);

        JButton button = new JButton("定制按钮");
        button.setBounds(40,120,100,20);
        button.setUI(new TestJButtonUI());

        frame.add(button);

        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    static class TestJButtonUI extends BasicButtonUI {
        @Override
        public void paint(Graphics g, JComponent c) {
            JButton button = (JButton) c;
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, button.getWidth(), button.getHeight());

            //让按钮中的文字居中显示
            FontMetrics fm = g.getFontMetrics();
            int stringWidth = fm.stringWidth(button.getText());//获取文字像素宽度
            int stringAscent = fm.getAscent();//节气基线以上字形（字符的实际部分）的最大高度
            int xCoordinate = (button.getWidth() - stringWidth) / 2;
            int yCoordinate = (button.getHeight() + stringAscent) / 2;

            g.setColor(button.getModel().isPressed() ? Color.GRAY : Color.WHITE); // Change text color based on button state
            g.drawString(button.getText(), xCoordinate, yCoordinate);
        }
    }
}
```



## 窗体和画板的组合

```java
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

```



## 参考资料

[柏码 - 让每一行代码都闪耀智慧的光芒！ (itbaima.cn)](https://www.itbaima.cn/document)