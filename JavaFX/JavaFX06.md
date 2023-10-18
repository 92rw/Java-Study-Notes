# FXML

功能：在开发桌面文件时，使用XML标签的形式配置布局

官方文档：[Introduction to FXML | JavaFX 20 (openjfx.io)](https://openjfx.io/javadoc/20/javafx.fxml/javafx/fxml/doc-files/introduction_to_fxml.html)

鼠标右键->New->FXML File，新建demo.fxml文件

需要配置controller、id、激活事件，以便控制器调用。标签对应构造属性，基本类型直接赋值，引用类型需要写子类

```xml
<AnchorPane xmlns="http://javafx.com/javafx"
            xmlns:fx="http://javafx.com/fxml"
            fx:controller="sample.Demo"
            prefHeight="400.0" prefWidth="600.0">

    <children>
        <Label fx:id="text" text="Hello World" layoutX="150" layoutY="200">
            <font>
                <Font size="20"/>
            </font>
        </Label>
        <Button fx:id="button" text="向上移动" layoutX="200" layoutY="250" onAction="#onClick"/>
    </children>
</AnchorPane>
```

编写对应的Controller类，属性、激活事件名称需要和FXML配置一致

```java
public class Demo {
    @FXML
    Label text;

    @FXML
    Button button;

    public void onClick() {
        text.setLayoutY(text.getLayoutY() - 10);
    }
}
```

此时在画布类可以直接调用，仅需控制画布尺寸，实现视图、构造器、Java代码的分离

```java
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane load = FXMLLoader.load(getClass().getResource("demo.fxml"));

        Scene scene = new Scene(load, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
```



上述操作可以通过SceneBuilder可视化操作，具体流程：

1. 新建一个 Empty 的项目
2. 从左侧的Containers中拖入一个AnchorPane，从Controls中拖入一个Button
3. 在右侧Properties可以内部属性，Layout修改布局样式，Code可以设置事件监听
4. 通过View -> Show Sample Controller Skeletor 直接生成Controller代码
5. 直接保存即可得到FXML布局文件，拷贝到项目工程目录下，需要配置 fx:controller 属性
6. 在控制器中配置触发事件的处理逻辑
