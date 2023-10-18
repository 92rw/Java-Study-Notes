# JavaFX的多线程

JavaFX不允许主线程之外的线程更改/刷新UI，目的是保证线程安全，避免其他线程污染UI的数据

处理方式，利用内置的runLater方法，更新UI。相当于把任务放进队列，在Application线程空闲时执行



案例演示1：新建线程更新文本框

```java
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 500, 500);

        Label label = new Label("我是谁？");
        label.setLayoutX(200);
        label.setLayoutY(200);

        Button button = new Button("获取答案");
        button.setLayoutX(200);
        button.setLayoutY(250);
        button.setOnAction(event -> {
            Thread thread = new Thread(() -> {
                String newValue="皮卡丘";
                Platform.runLater(() -> {
                    label.setText(newValue);
                });
            });
            thread.start();
        });

        root.getChildren().addAll(label, button);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
```

案例演示2：新建线程获取图片并显示

```java
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ImageView imageView = new ImageView();
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);

        Label label = new Label("阿尼瓦灯塔照片");
        label.setLayoutX(340);
        label.setLayoutY(470);

        Button button = new Button("获取图片");
        button.setLayoutX(350);
        button.setLayoutY(500);

        button.setOnAction(event -> {
            Thread thread = new Thread(() -> {
                Image image = new Image("https://cn.bing.com/th?id=OHR.AnivaLighthouse_EN-US8147045989_UHD.jpg",true);
                image.progressProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        int progress = (int)(newValue.doubleValue() * 100);
                        label.setText("图片加载进度" + progress + "%");
                    }
                });
                imageView.setImage(image);
                imageView.setFitWidth(800);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
            });
            thread.setName("Aniva lighthouse");
            thread.start();
        });

        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(label, button,imageView);
        Scene scene = new Scene(root, 800, 550);


        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}

```

