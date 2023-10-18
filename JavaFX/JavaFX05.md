# 颜色、字体、图片

## Color类

javafx.scene.paint.Color

```java
Color.web(#f66a08);
Color.BLUE;
Color.rgb(11,45,14)
```



## Font类

javafx.scene.text

```java
//如果使用电脑中没有的字体，在工程目录导入文件并使用loadFont方法加载
new Font(10);
Font.font("微软雅黑",FontWeight.BOLD,12);
```

## Image类

javafx.scene.image.Image


支持的格式：BMP，GIF，JPEG，PNG

```java
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 500, 500);

        ImageView imageView = new ImageView();
        Image image = new Image("/img/1.png");
        imageView.setImage(image);

        root.getChildren().add(imageView);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

```

