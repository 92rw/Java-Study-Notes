# Canvas开发游戏基本思路

响应用户操作：坐标变更、屏幕重绘 

javafx.animation.AnimationTimer：屏幕刷新都会调用此接口的handle() 方法

案例演示：监听键盘方向键，实现图片移动

```java
public class Main extends Application {
    public static final double WIDTH = 800, HEIGHT = 600;
    private Canvas canvas = new Canvas(WIDTH, HEIGHT);
    private GraphicsContext graphics = canvas.getGraphicsContext2D();
    private Image backgroundImage = new Image("/img/2.png");
    private Image selfImage = new Image("/img/Icon.png");
    private double x = 400, y = 300;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //paint();
        update();
        
        AnchorPane root = new AnchorPane(canvas);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case UP:
                    y -= 10;
                    break;
                case DOWN:
                    y += 10;
                    break;
                case LEFT:
                    x -= 10;
                    break;
                case RIGHT:
                    x += 10;
                    break;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void paint() {
        graphics.drawImage(backgroundImage,0,0);
        graphics.drawImage(selfImage,x,y);
    }

    private void update() {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                paint();
            }
        };
        animationTimer.start();
    }
}

```

## 游戏音效

javafx.scene.media.AudioClip 类，播放的音频文件不能暂停

demo.fxml 文件的配置如下

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.layout.AnchorPane?>

<AnchorPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="300.0" prefWidth="300.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="sample.Demo">
   <children>
      <Button fx:id="btn" layoutX="124.0" layoutY="138.0" mnemonicParsing="false" onAction="#clickBtn" text="听个响" />
   </children>
</AnchorPane>
```

Demo.java 文件配置如下

```java
public class Demo {

    @FXML
    private Button btn;

    @FXML
    void clickBtn(ActionEvent event) {
        AudioClip audioClip = new AudioClip(getClass().getResource("/audio/111.wav").toString());
        audioClip.setVolume(0.3);
        audioClip.play();
    }
}
```



