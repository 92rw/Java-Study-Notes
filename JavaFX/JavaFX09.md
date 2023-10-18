# Canvas控件

javafx.scene.canvas.Canvas

可在画布上绘制线段、图形，插入文本、图片。自身不包含绘制的方法，需要利用getGraphicsContext2D()

| 返回类型        | 方法名                  | 说明     |
| --------------- | ----------------------- | -------- |
| GraphicsContext | getGraphicsContext2D()  | 画笔对象 |
| double          | getHeight()             |          |
| double          | getWidth()              |          |
| DoubleProperty  | heightProperty()        |          |
| DoubleProperty  | widthProperty()         |          |
| void            | setHeight(double value) |          |

javafx.scene.canvas.GraphicsContext

用于绘制图案。fill开头的带填充，stroke开头的不带填充

Canvas 类和 GraphicsContext 共同使用，可开发绘图类的软件或2D游戏

案例演示：绘制坦克图形

```java
public class Main extends Application {
    public static final double WIDTH = 400, HEIGHT = 400;
    private Canvas canvas = new Canvas(WIDTH, HEIGHT);
    private GraphicsContext graphics = canvas.getGraphicsContext2D();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        int x = 180, y = 150;
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);

        graphics.setStroke(Color.GOLD);//描边颜色
        graphics.setFill(Color.CYAN);//填充颜色
        graphics.setLineWidth(1.5);//描边粗细

        //在画布上绘制坦克图形，参数来自坦克大战游戏
        graphics.fillRect(x, y,10,60);//左侧履带
        graphics.fillRect(x + 30, y,10,60);//右侧履带
        graphics.fillRect(x + 10, y + 10 ,20,40);//中部炮台
        graphics.fillOval(x + 10, y + 20 ,20,20);//炮塔
        graphics.fillRect(x + 18, y - 2, 4, 4);//炮口
        graphics.strokeLine(x + 20 , y + 30,x + 20, y);//炮膛
        for (int i = 2; i < 60; i += 4) {
            graphics.strokeLine(x + 1, y + i, x + 9, y + i);
            graphics.strokeLine(x + 31, y + i, x + 39, y + i);
        }

        AnchorPane root = new AnchorPane(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

```

案例演示：绘图板

```java
public class Main extends Application {
    public static final double WIDTH = 800, HEIGHT = 600;
    private Canvas canvas = new Canvas(WIDTH, HEIGHT);
    private GraphicsContext graphics = canvas.getGraphicsContext2D();
    private double x,y;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);

        canvas.setOnMousePressed(event -> {
            x = event.getX();
            y = event.getY();
        });

        canvas.setOnMouseDragged(event -> {
            double x2 = event.getX();
            double y2 = event.getY();

            graphics.strokeLine(x,y,x2,y2);
            x = x2;
            y = y2;
        });

        AnchorPane root = new AnchorPane(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}

```

案例演示：绘制矩形

```java
public class Main extends Application {
    public static final double WIDTH = 800, HEIGHT = 600;
    private Canvas canvas = new Canvas(WIDTH, HEIGHT);
    private GraphicsContext graphics = canvas.getGraphicsContext2D();
    private double x,y;
    private WritableImage record = null;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);

        canvas.setOnMousePressed(event -> {
            x = event.getX();
            y = event.getY();
        });

        canvas.setOnMouseDragged(event -> {
            double startX = x;
            double startY = y;
            double endX = event.getX();
            double endY = event.getY();

            if (endX < startX){
                startX = endX;
                endX = x;
            }
            if (endY < startY){
                startY = endY;
                endY = y;
            }
            double width = endX - startX;
            double height = endY - startY;

            //清除画布内其他矩形，绘制之前保存过的图形
            graphics.clearRect(0, 0, WIDTH, HEIGHT);
            graphics.drawImage(record, 0,0);

            graphics.strokeRect(startX,startY,width,height);
        });

        canvas.setOnMouseReleased(event -> {
            record = canvas.snapshot(null,null);
        });

        AnchorPane root = new AnchorPane(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
```





