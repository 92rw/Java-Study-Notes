# 多媒体文件交互

## 影音播放

* Media，MediaPlayer 和 MediaView（视频播放）类，位于 javafx.scene.media 包下
* 进度条美化，可使用第三方库 [JFoenix](https://github.com/sshahine/JFoenix) 提供的 JFXSlider
* 导入的图片均来自阿里图标库，设置尺寸为32*32

demo.fxml 文件的配置如下

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Slider?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.media.MediaView?>

<AnchorPane fx:id="root" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="600.0" prefWidth="800.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="sample.Demo">
   <children>
      <MediaView fx:id="mediaView" fitHeight="450.0" fitWidth="800.0" onDragDropped="#dragDropped" onDragOver="#dragOver" />
      <Slider fx:id="progressBar" layoutY="490.0" prefHeight="14.0" prefWidth="800.0" />
      <Slider fx:id="volumeBar" layoutX="628.0" layoutY="539.0" />
      <ImageView layoutX="581.0" layoutY="530.0">
         <image>
            <Image url="@../img/Vol.png" />
         </image>
      </ImageView>
      <ImageView fx:id="play" layoutX="60.0" layoutY="530.0">
         <image>
            <Image url="@../img/Play.png" />
         </image>
      </ImageView>
      <ImageView fx:id="stop" layoutX="192.0" layoutY="530.0">
         <image>
            <Image url="@../img/Stop.png" />
         </image>
      </ImageView>
      <ImageView fx:id="pause" layoutX="127.0" layoutY="530.0">
         <image>
            <Image url="@../img/pause.png" />
         </image>
      </ImageView>
   </children>
</AnchorPane>

```

Demo.java 文件配置如下

```java
package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.File;

public class Demo {
    private boolean bind = true;

    @FXML
    private AnchorPane root;

    @FXML
    private MediaView mediaView;

    @FXML
    private Slider progressBar;

    @FXML
    private Slider volumeBar;

    @FXML
    private ImageView pause;

    @FXML
    private ImageView play;

    @FXML
    private ImageView stop;

    @FXML
    void dragDropped(DragEvent event) {

        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasFiles()) {
            File file = dragboard.getFiles().get(0);
            Media media = new Media(file.toURI().toString());

            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() -> {
                mediaPlayer.setAutoPlay(true);

                pause.setOnMouseClicked(event1 -> {
                    mediaPlayer.pause();
                });

                play.setOnMouseClicked(event1 -> {
                    mediaPlayer.play();
                });

                stop.setOnMouseClicked(event1 -> {
                    mediaPlayer.stop();
                });

                volumeBar.setMax(1);
                volumeBar.setLabelFormatter(new StringConverter<Double>() {
                    @Override
                    public String toString(Double object) {
                        double dv = object;
                        int v = (int) (dv * 100);
                        return Integer.toString(v);
                    }

                    @Override
                    public Double fromString(String string) {
                        return null;
                    }
                });
                volumeBar.valueProperty().bindBidirectional(mediaPlayer.volumeProperty());

                mediaView.setMediaPlayer(mediaPlayer);

                progressBar.setMin(mediaPlayer.getStartTime().toSeconds());
                progressBar.setMax(mediaPlayer.getTotalDuration().toSeconds());

                progressBar.setOnMousePressed(event1 -> bind = false);
                progressBar.setOnMouseReleased(event1 -> {
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                    bind = true;
                });
                progressBar.setLabelFormatter(new StringConverter<Double>() {
                    @Override
                    public String toString(Double object) {
                        // TODO：将时间格式转换为时分秒
                        return null;
                    }

                    @Override
                    public Double fromString(String string) {
                        return null;
                    }
                });

                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                        if (bind) {
                            progressBar.setValue(newValue.toSeconds());
                        }
                    }
                });
            });
        }
    }

    @FXML
    void dragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
    }

}

```



