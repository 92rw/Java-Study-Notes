# Swing组件（一）

Swing是建立在AWT（Abstract Window Toolkit）之上的，提供了一套丰富的图形用户界面组件。与AWT相比，Swing提供了更多样化和功能更强大的组件，同时解决了AWT组件在不同平台间外观和行为可能不一致的问题。Swing组件是轻量级的，主要是因为它们是用Java完全实现的，而不是依赖于本地操作系统的GUI组件。

## 基本使用

swing包下的JFrame类继承了awt的Frame类，因此用法类似。

Swing在没有设定布局时，组件的坐标原点并不是窗口的左上角，而是窗口标题栏下方的左上角

```java
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("标题");//支持中文输入
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //设定窗口关闭操作，不需要设置监听器
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }
}
```

- 使用`JFrame.EXIT_ON_CLOSE`适合那些简单的应用，当用户关闭主窗口时，程序应该立即退出。
- 使用`WindowConstants.DO_NOTHING_ON_CLOSE`提供了更多的灵活性和控制，适合需要在应用程序关闭前执行特定操作的情况，比如保存数据、释放资源或者向用户确认是否真的要退出。

## 窗体控件

### 对awt的扩展

由于标题栏会占用面板空间，需要将窗体绘制的代码写在最后面

```java
    public static void main(String[] args) {
        JFrame frame = new JFrame("标题");

        //加入窗体组件：支持awt组件，但不建议
        Button button1 = new Button("awt按钮");//中文显示为乱码
        button1.setBounds(20, 50, 100, 50);
        JButton button2 = new JButton("swing按钮");
        button2.setBounds(120, 50, 100, 50);
        frame.add(button1);
        frame.add(button2);

        JMenuBar bar = new JMenuBar();    //创建菜单栏
        JMenu menu = new JMenu("文件");
        menu.add(new JCheckBoxMenuItem("选择"));//可勾选的菜单项
        JMenuItem menuClose = new JMenuItem("保存");
        menuClose.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        menuClose.addActionListener(e -> System.out.println("保存选项被选中"));
        menu.add(menuClose);
        bar.add(menu);

        //窗体布局的相关代码
        frame.setLayout(null);
        frame.setBounds(600, 300,400,400);
        frame.setJMenuBar(bar);    //为窗口设定刚刚定义好的菜单栏
        frame.setVisible(true);
    }
```

- **控件类**：
  - `JButton`：比AWT的`Button`提供了更多的定制化功能，如图标按钮等。
  - `JCheckBox`和`JRadioButton`：提供了复选框和单选按钮的Swing版本。
  - `JComboBox`：下拉列表框，AWT中使用`Choice`类，但Swing的`JComboBox`功能更丰富。
  - `JList`：提供了列表选择功能，可以自定义渲染器来改变列表项的显示方式。
  - `JMenu`、`JMenuBar`和`JMenuItem`：用于创建菜单栏和菜单项，比AWT的菜单组件更灵活。
  - `JTextField`、`JPasswordField`和`JTextArea`：文本输入相关组件，提供了比AWT更多的功能，如文本区域的滚动等。
  - `JLabel`：用于显示文本和图像的标签，AWT中没有直接对应的组件。
- **高级组件**：
  - `JTable`：用于显示和编辑二维数据的表格组件。
  - `JTree`：显示层次数据结构的树形组件。
  - `JSlider`：滑动条组件，用于从一定范围内选择值。
- **特殊功能组件**：
  - `JLayeredPane`和`JDesktopPane`：提供了分层窗格，允许组件在不同的层上显示，增加了UI的复杂性和灵活性。

### 新增组件

除了强化AWT提供的组件之外，还自行实现了各种各样新式的组件

* 进度条

```java
        JProgressBar bar = new JProgressBar();
        bar.setMaximum(100);
        bar.setValue(62);
        bar.setBounds(20,20,200,10);
        frame.add(bar);
```

在进度条中实时显示处理进度

```java
JProgressBar bar = new JProgressBar();   //进度条显示文件拷贝进度
bar.setMaximum(1000);
bar.setBounds(20, 50, 300, 10);

JButton button = new JButton("点击开始");   //点击按钮开始拷贝文件
button.setBounds(20, 100, 100, 30);
button.addActionListener(e -> new Thread(() -> {
    //注意，不能直接在这个线程里面处理，因为这个线程是负责图形界面的，得单独创建一个线程处理，否则图形界面会卡死
    File file = new File("in.iso");
    try(FileInputStream in = new FileInputStream(file);
        FileOutputStream out = new FileOutputStream("out.iso")){
        long size = file.length(), current = 0;
        int len;
        byte[] bytes = new byte[1024];
        while ((len = in.read(bytes)) > 0) {
            current += len;
            int finalCurrent = (int) current;  // 需要是effectively final，用于lambda表达式内
            SwingUtilities.invokeLater(() -> {
                bar.setValue((int) (bar.getMaximum() * (double)finalCurrent / size));  // 在EDT中更新进度条
                bar.repaint();  // 确保在EDT中重新绘制组件
            });

            out.write(bytes, 0, len);
        }
    } catch (IOException exception) {
        exception.printStackTrace();
    }
}).start());

frame.add(bar);
frame.add(button);
```

当你在非EDT线程中更新GUI组件时（如进度条），应当使用`SwingUtilities.invokeLater`或`SwingWorker`来确保这些更新在EDT上执行，因为Swing组件的状态通常不是线程安全的。直接从另一个线程修改GUI组件可能会导致不可预测的行为或错误。



* 可切换状态的按钮

```java
        JToggleButton toggleButton = new JToggleButton("切换按钮");   //开关按钮有两个状态，一个是开一个是关，可通过setSelected设置默认状态
        toggleButton.setBounds(20, 50, 100, 30);
        toggleButton.addActionListener(e -> {
            if (toggleButton.isSelected()) {
                toggleButton.setText("已选中");
                System.out.println("Button is selected.");
            } else {
                toggleButton.setText("单击选中");
                System.out.println("Button is not selected.");
            }
        });
        frame.add(toggleButton);
```

* 颜色选择器：可使用getColor方法获取到当前选中的颜色

```java
    public static void main(String[] args) {
        JFrame frame = new JFrame("颜色选择器");
        JColorChooser chooser = new JColorChooser();
        chooser.setBounds(0, 0, 600, 300);
        frame.add(chooser);

        //窗体布局的相关代码
        frame.setLayout(null);
        frame.setBounds(600, 300,600,350);//需考虑到标题栏也占用了高度
        frame.setVisible(true);
    }
```

* 文件选择器

```java
        JFileChooser chooser = new JFileChooser();
        chooser.setBounds(0, 0, 500, 300);
        frame.add(chooser);
```

* 工具提示

```java
JButton button = new JButton("我是按钮");
button.setBounds(50, 50, 100, 30);
button.setToolTipText("这个是按钮的工具提示");
```

`setToolTipText`方法是`JComponent`就带有的，因此任何组件都可以设置这样的工具提示，是不是感觉很高级？

### 高级组件

树形组件

```java
JTree tree = new JTree();
tree.setBounds(0, 0, 200, 200);
frame.add(tree);

```

利用树形组件显示文件树

```java
public class DirectoryTree  {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Directory Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        //以当前工程项目的src目录为例
        File file = new File("src");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(file.getName());
        addNodes(root, file);

        JTree tree = new JTree(root);
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode != null) {
                // 获取与节点关联的File对象
                File selectedFile = (File) selectedNode.getUserObject();
                // 现在你可以使用File对象了，比如获取文件路径
                System.out.println("Selected File Path: " + selectedFile.getAbsolutePath());
            }
        });

        JScrollPane scrollPane = new JScrollPane(tree); // 使用滚动面板显示树
        frame.add(scrollPane);

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



## 参考资料

[柏码 - 让每一行代码都闪耀智慧的光芒！ (itbaima.cn)](https://www.itbaima.cn/document)