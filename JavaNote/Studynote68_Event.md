# java事件处理机制

基本说明
java事件处理是采取“委派事件模型”。当事件发生时,产生事件的对象，会把此“信息”传递给事件的监听者处理，
这里所说的"信息“实际上就是java.awt.event事件类库里某个类所创建的对象，把它称为“事件的对象”。

1.事件源：事件源是一个产生事件的对象，比如按钮，窗口等。
2.事件：事件就是承载事件源状态改变时的对象，比如当键盘事件、鼠标事件、窗口事件等等，会生成一个事件对象，
该对象保存着当前事件很多信息，比如KeyEvent对象有被按下键的Code值。java.awt.event 包和 javax.swing.event 包中定义了各种事件类型
3.

| 事件类                                                       | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ActionEvent                                                  | 通常在按下按钮，或双击一个列表项或选中茶个菜单时发生。       |
| AdjustmentEvent                                              | 当操作一个滚动条时发生。                                     |
| ComponentEvent                                               | 当一个组件隐藏，移动，改变大小时发生                         |
| ContainerEvent                                               | 当一个组件从容器中加入或者删除时发生                         |
| xxxxxxxxxx // 待测试函数public int readFromFile(InputStream inputStream) {    // ...}​// 测试代码public void test_readFromFile() {    byte[] testData = new byte[512];    // ... 构建测试数据, 填入 testData 数组 ...    InputStream in = new ByteIntputStream(testData);    int res = readFromFile(in);    // ... assert ... 判断返回值是否符合预期 ...}java | 当一个组件获得或是失去焦点时发生。                           |
| ItemEvent                                                    | 当一个复选框或是列表项被选中时，当一个选择框或选择菜单被选中。 |
| KeyEvent                                                     | 当从键盘的按键被按下，松开时发生。                           |
| MouseEvent                                                   | 当鼠标被拖动，移动，点击，按下..                             |
| TextEvent                                                    | 当文本区和文本域的文本发生改变时发生。                       |
| WindowEvent                                                  | 当一个窗口激活，关闭，失效，恢复，最小化.                    |





4.事件监听器接口：
（1）当事件源产生一个事件，可以传送给事件监听者处理
（2）事件监听者实际上就是一个类，该类实现了某个事件监听器接口比如前面我们案例中的 EventPanel 就是一个类，
它实现了KeyListener 接口，它就可以作为一个事件监听者，对接受到的事件进行处理
（3）事件监听器接口有多种，不同的事件监听器接口可以监听不同的事件，一个类可以实现多个监听接口
（4）这些接口在 java.awt.event 包和 javax.swing.event 包中定义列出常用的事件监听器接口，可查看jdk文档