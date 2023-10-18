# 接口

## Node 接口

javafx.scene.Node，所有可见控件都继承该接口



通用属性

| 坐标属性             | 定义内容    | 样式属性  | 定义内容                    |
| -------------------- | ----------- | --------- | --------------------------- |
| layoutX              | 左上角X坐标 | style     | 设置样式：在css语法前加-fx- |
| layoutY              | 左上角Y坐标 | alignment | 在样式中的对齐方式          |
| prefWidth            | 背景宽度    | visible   | 可见性                      |
| prefHeight           | 背景高度    | blendMode | 是否混色                    |
| rotate               | 旋转角度    | opacity   | 透明度（范围0-1）           |
| translateX           | 横向平移    | parent    | 父节点                      |
| translateY           | 纵向平移    | scene     | 所在场景                    |
| scaleX/scaleY/scaleZ | 三维坐标    | id        |                             |

有关CSS定义的使用，详见官方文档：[JavaFX CSS Reference Guide (openjfx.io)](https://openjfx.io/javadoc/20/javafx.graphics/javafx/scene/doc-files/cssref.html)

事件监听

| User Action                 | Source Objec | Even Tye Fired | Event Registration Method                     |
| --------------------------- | ------------ | -------------- | --------------------------------------------- |
| Click a button              | Button       | ActionEvent    | setOnAction(EventHandler\<ActionEvent>)       |
| Press Enter in a text field | TextFfeld    | ActionEvent    | setOnAction(EventHandler\<ActionEvent>)       |
| Check or uneheek            | RadioButton  | ActionEvent    | setOnAction(EventHandler\<ActionEvent>)       |
| Check or uneheek            | CheckBox     | ActionEvent    | setOnAction(EventHandler\<ActionEvent>)       |
| Select a new item           | ComboBox     | ActionEvent    | setOnAction(EventHandler\<ActionEvent>)       |
| Mouse pressed               | Node，Scene  | MouseEvent     | SetOnMousePressed(EventHandler\<MouseEvent>)  |
| Mouse released              |              |                | SetOnMouseReleased(EventHandler\<MouseEvent>) |
| Mouse clicked               |              |                | SetOnMouseClicked(EventHandler\<MouseEvent>)  |
| Mouse entered               |              |                | setOnMouseEntered(EventHandler\<MouseEvent>)  |
| Mouse exited                |              |                | setOnMouseExited(EventHandler\<MouseEvent>)   |
| Mouse moved                 |              |                | SetOnMouseMoved(EventHandler\<MouseEvent>)    |
| Moue dragged                |              |                | setOnMouseDragged(EventHandler\<MouseEvent>)  |
| Key pressed                 | Node，Scene  | KeyEvent       | setOnKeyPressed(EventHandler\<KeyEvent>)      |
| Key released                |              |                | setonkeyReleased(EventHandler\<KeyEvent>)     |
| Key typed                   |              |                | SetOnKeyTyped(EventHandler\<KeyEvent>)        |



## Property\<T> 接口

javafx.beans.property，属性绑定、属性监听器通过该接口设置

| Type    | Method                                         | Description      |
| :------ | :--------------------------------------------- | ---------------- |
| void    | bind(ObservableValue<?  extends T> observable) | 创建单项绑定     |
| void    | bindBidirectional(Property\<T>  other)         | 创建双向绑定     |
| boolean | isBound()                                      | 检查是否存在绑定 |
| void    | unbind()                                       | 删除单向绑定     |
| void    | unbindBidirectional(Property\<T>  other)       | 删除双向绑定     |

```java
//画一个始终显示在窗体中心的圆
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 500, 500);

        Circle circle = new Circle();
        circle.setCenterX(250);
        circle.setCenterY(250);
        circle.setRadius(100);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(3);
        circle.setFill(Color.WHITE);

        //设置坐标随窗体改变
        circle.centerXProperty().bind(scene.widthProperty().divide(2));
        circle.centerYProperty().bind(scene.heightProperty().divide(2));

        //设置监听器
        circle.centerXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("检测到X轴中心点改变。原值：" + oldValue + "，现值：" + newValue);
            }
        });
        root.getChildren().addAll(circle);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

案例演示：用鼠标点击按钮、键盘向下键控制标签移动

```java
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 500, 500);

        Label label = new Label("点击键盘向下移动");
        label.setLayoutX(200);
        label.setLayoutY(200);

        Button button = new Button("点击按钮向上移动");
        button.setLayoutX(300);
        button.setLayoutY(200);
        root.getChildren().addAll(label, button);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                label.setLayoutY(label.getLayoutY() - 10);
            }
        });

        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            if (code.equals(KeyCode.DOWN)) {
                label.setLayoutY(label.getLayoutY() + 10);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

```

案例演示：支持拖拽并在文本框中显示文件路径

```java
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 500, 500);

        TextArea textArea = new TextArea();
        textArea.setLayoutX(10);
        textArea.setLayoutY(100);

        textArea.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
        });

        textArea.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                String path = dragboard.getFiles().get(0).getAbsolutePath();
                textArea.setText(path);
            }
        });
        root.getChildren().add(textArea);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
```

