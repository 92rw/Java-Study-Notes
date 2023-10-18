# 窗口和场景

## Stage窗口

javafx.stage.Stage



通过set方法可以改变的属性

| 属性      | 类型   | 属性      | 类型    | 说明         |
| --------- | ------ | --------- | ------- | ------------ |
| MinWidth  | double | Iconified | boolean | 最小化       |
| MinHeight | double | Maximized | boolean | 最大化       |
| MaxWidth  | double | Resizable | boolean | 是否尺寸可变 |
| MaxHeight | double |           |         |              |



| 重要方法                        | 方法说明       | 备注                                                         |
| ------------------------------- | -------------- | ------------------------------------------------------------ |
| setTitle(String value)          | 设置标题       |                                                              |
| getIcons().add(Image img)       | 设置图标       |                                                              |
| initStyle(StageStyle style)     | 窗口样式       | DECORATED默认，UNDECORATED无标题栏，TRANSPARENT，UTILITY没有最小化最大化 |
| initModality(Modality modality) | 其他窗口可用性 | NONE窗口相互不影响，WINDOW_MODAL只禁入其他窗口，APPLICATION_MODAL所有其他窗口禁入 |

代码演示：不同按钮新建的不同窗口，可用性不一样

```java
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Button button0 = new Button("窗口0");
        Button button1 = new Button("窗口1");
        button0.setLayoutX(200);
        button0.setLayoutY(200);
        button1.setLayoutX(200);
        button1.setLayoutY(250);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(button0, button1);

        button0.setOnAction(event -> {
            Stage stage = new Stage();
            stage.setHeight(200);
            stage.setWidth(300);
            stage.initOwner(primaryStage);//设置副窗口
            stage.initModality(Modality.WINDOW_MODAL);//设置可用性
            stage.show();
        });

        button1.setOnAction(event -> {
            Stage stage = new Stage();
            stage.setHeight(200);
            stage.setWidth(300);
            stage.show();
        });
        Scene scene = new Scene(anchorPane, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX窗口");
        primaryStage.getIcons().add(new Image("/img/1.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

代码演示：设置关闭事件，点击确定后才能退出

```java
Platform.setImplicitExit(false);//取消操作系统默认退出的动作
primaryStage.setOnCloseRequest(event -> {
    event.consume();
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("退出程序");
    alert.setHeaderText(null);//用于显示一些补充信息
    alert.setContentText("您是否要退出程序？");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        //primaryStage.close() 执行后，只是关闭窗口，进程不结束
        Platform.exit();
    }
});
```



## Scene场景



```java
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Button button0 = new Button("点击切换场景");
        button0.setLayoutX(200);
        button0.setLayoutY(200);

        AnchorPane root0 = new AnchorPane();
        root0.getChildren().addAll(button0);
        Scene scene0 = new Scene(root0, 500, 500);

        Label label = new Label("你好，JavaFX");
        label.setLayoutX(200);
        label.setLayoutY(200);
        Button button1 = new Button("返回原场景");
        button1.setLayoutX(200);
        button1.setLayoutY(250);

        AnchorPane root1 = new AnchorPane();
        root1.getChildren().addAll(label,button1);
        Scene scene1 = new Scene(root1, 500, 500);
        scene1.setCursor(new ImageCursor(new Image("/img/1.png")));//切换光标图案

        button0.setOnAction(event -> {
            primaryStage.setScene(scene1);
        });

        button1.setOnAction(event -> {
            primaryStage.setScene(scene0);
        });

        primaryStage.setScene(scene0);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

