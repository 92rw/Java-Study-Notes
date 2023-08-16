package Exercise.TankGame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Scanner;

public class GameFrame extends JFrame {

    GamePanel gp = null;
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        GameFrame gf = new GameFrame();
    }
    public GameFrame() {
        String key;
        File file = new File(Recorder.getRecordFile());
        if(!file.exists()){
            key = "N";
            System.out.println("当前无游戏记录，自动开启新一局");
        }else {
            System.out.println("是否继续上局游戏(Y/N):");
            while (true) {
                key = scanner.next().toUpperCase();
                if (key.compareTo("Y") == 0 || key.compareTo("N") == 0) {
                    break;
                }
                System.out.println("输入有误,请重新输入");
            }
        }
        //设置进程
        gp = new GamePanel(key);
        new Thread(gp).start();
        //加载画面区域
        this.add(gp);
        this.setSize(1300,790);
        this.setVisible(true);//设置可见
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addKeyListener(gp);//监听键盘事件

        //关闭窗口时的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.saveRecord();
                System.exit(0);
            }
        });
    }
}
