# SceneBuilder

以拖拽方式完成 FXML 布局，下载地址：[Scene Builder | JavaFX中文官方网站 (openjfx.cn)](https://openjfx.cn/scene-builder/)

在 IEDA 项目内置 SceneBuilder 的方法：File -> Settings -> Languages & Frameworks -> JavaFX

此时可在fxml文件的页面右键->Open in SceneBuilder



## 画面初始化

通过Controller里面的initialize方法，可以配置初始化时候显示的内容。这样内容更新不需要依赖其他的事件触发

initialize方法的加载时机：布局内的控件加载完成后，场景加载前（无法访问Scene，提示空指针错误）



案例演示：添加一个表格

1. 新建一个 Empty 的项目

2. 从左侧的Containers中拖入一个AnchorPane，从Controls拖入一个TableView，通过右侧Properties设置为布局的大小

3. 在右侧Code分别给各表格配置 fx:id 以便控制器读取

4. 通过View -> Show Sample Controller Skeletor 直接生成Controller代码

5. 直接保存即可得到FXML布局文件，可拷贝到项目工程目录下

6. 新建一个JavaBean类 Person，设置私有属性

7. 给fxml配置 fx:controller 属性为控制器类的全路径，再编写控制器类

   ```java
   public class TableController {
       @FXML
       private TableView<Object> table;
   
       @FXML
       private TableColumn<Person,String> name;
   
       @FXML
       private TableColumn<Person,Integer> age;
   
       public void initialize() {
           ObservableList<Object> celldate = FXCollections.observableArrayList();
           name.setCellValueFactory(new PropertyValueFactory<Person,String>("name"));
           age.setCellValueFactory(new PropertyValueFactory<Person,Integer>("age"));
   
           celldate.add(new Person("秦始皇",50));
           celldate.add(new Person("汉武帝",70));
           celldate.add(new Person("唐太宗",53));
           celldate.add(new Person("宋太祖",50));
   
           table.setItems(celldate);
       }
   }
   
   ```
   
8. 此时主程序的 start() 方法仅需4行代码即可运行窗口

   ```java
   Pane load = FXMLLoader.load(getClass().getResource("demo.fxml"));
   Scene scene = new Scene(load);
   primaryStage.setScene(scene);
   primaryStage.show();
   ```

   

说明：

* 需要给 FXML 配置 `fx:controller` 属性为控制器类的全路径
* 控制器类需要给各元素指定具体的泛型



## 在主线程中操作控制器

通过fxml配置的组件，坐标值已固定，无法随窗口大小变化而调整位置。控制器中也不会自动调用绑定逻辑。只能在主线程中获取控制器的引用

案例演示：创建一个随窗口变化位置的圆

1. 利用SceneBuilder在fxml文件绘制图案，设置控制器

2. 在控制器中，将圆设置为私有变量以便封装，对外提供改变控件属性绑定的方法

   ```java
   public class Demo {
       @FXML
       Circle ci;
   
       public void circleLocationBind(Scene scene) {
           ci.centerXProperty().bind(scene.widthProperty().divide(2));
           ci.centerYProperty().bind(scene.heightProperty().divide(2));
       }
   }
   ```

3. 在主方法中，获取控制器完成
