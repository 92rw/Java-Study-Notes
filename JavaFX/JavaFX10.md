# 将画布保存为图片

需要借助 javafx.embed.swing.SwingFXUtils 将图片转为缓冲流，javafx.stage.FileChooser 打开/保存文件

案例演示：

1. 在 SceneBuilder 中拖入 AnchorPane、MenuBar、Canvas

2. 给Canvas对象配置 fx:id 和鼠标拖拽、鼠标按下事件，给保存按钮配置事件，自动生成的fxml文件如下

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   
   <?import javafx.scene.canvas.Canvas?>
   <?import javafx.scene.control.Menu?>
   <?import javafx.scene.control.MenuBar?>
   <?import javafx.scene.control.MenuItem?>
   <?import javafx.scene.layout.AnchorPane?>
   
   <AnchorPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="500.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="sample.Demo">
      <children>
         <MenuBar layoutY="2.0" prefHeight="22.0" prefWidth="500.0">
           <menus>
             <Menu mnemonicParsing="false" text="File">
               <items>
                 <MenuItem mnemonicParsing="false" onAction="#saveImage" text="Save" />
                     <MenuItem mnemonicParsing="false" onAction="#exit" text="Close" />
               </items>
             </Menu>
             <Menu mnemonicParsing="false" text="Edit">
               <items>
                 <MenuItem mnemonicParsing="false" text="Delete" />
               </items>
             </Menu>
             <Menu mnemonicParsing="false" text="Help">
               <items>
                 <MenuItem mnemonicParsing="false" text="About" />
               </items>
             </Menu>
           </menus>
         </MenuBar>
         <Canvas fx:id="canvas" height="374.0" layoutY="27.0" onMouseDragged="#canvasOnMouseDragged" onMousePressed="#canvasOnMousePressed" width="500.0" />
      </children>
   </AnchorPane>
   ```

3. 根据SceneBuilder生成的模板，配置对应的Demo.java 控制器

   ```java
   public class Demo {
       @FXML
       private MenuItem saveMenu;
       @FXML
       private Canvas canvas;
   
       private double x,y;
   
   
       @FXML
       void canvasOnMouseDragged(MouseEvent event){
           double x2 = event.getX();
           double y2 = event.getY();
   
           canvas.getGraphicsContext2D().strokeLine(x,y,x2,y2);
           x = x2;
           y = y2;
       }
   
       @FXML
       void canvasOnMousePressed(MouseEvent event){
           x = event.getX();
           y = event.getY();
       }
   
       @FXML
       void saveImage(ActionEvent event){
           WritableImage image = canvas.snapshot(null, null);
           BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image,null);
   
           FileChooser fileChooser = new FileChooser();
           fileChooser.setTitle("Save Canvas Image");
           fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG",".png"));
           File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
   
           if (file != null) {
               try {
                   ImageIO.write(bufferedImage, "PNG", file);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
   
       @FXML
       void exit(ActionEvent event){
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
       }
   }
   ```

4. 主界面仍然仅需配置FXML

   ```java
   public class Main extends Application {
       public static void main(String[] args) {
           launch();
       }
   
       @Override
       public void start(Stage primaryStage) throws Exception {
           Pane load = FXMLLoader.load(getClass().getResource("demo.fxml"));
           Scene scene = new Scene(load);
           primaryStage.setScene(scene);
           primaryStage.show();
       }
   }
   ```

   

