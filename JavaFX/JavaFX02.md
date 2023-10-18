# 编写Hello, World 窗口

JavaFX界面结构

![界面结构](https://fxdocs.github.io/docs/html5/images/scenegraph/scene_graph.jpg)



案例演示：在窗口中编写Hello, world

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Label label = new Label("Hello World");
        BorderPane borderPane = new BorderPane(label);
        Scene scene = new Scene(borderPane, 300, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX窗口");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

```

代码构造

1. 继承Application类，重写start()方法
2. 在main()方法中，通过launch()方法展现窗口
   * 可以通过传入外部类的Class对象，打开多个窗口
3. launch()方法在生命周期内会调用 init()，start()，stop()方法

参考文章：[JavaFX—Application - DirWangK - 博客园 (cnblogs.com)](https://www.cnblogs.com/DirWang/articles/11263615.html)



案例演示：点击按钮，访问外部链接

```java
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Button button = new Button("Github");

        BorderPane borderPane = new BorderPane(button);

        button.setOnAction(event -> {
            getHostServices().showDocument("www.github.com");
        });
        Scene scene = new Scene(borderPane, 300, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX窗口");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

```



## JavaFX的生命周期

Application类有三个方法：init，start（需重写），end

当被launch() 方法调用时，首先init()方法创建JavaFX-Launcher线程，然后start()方法创建JavaFX Application Thread线程（UI线程），关闭时执行stop()方法
