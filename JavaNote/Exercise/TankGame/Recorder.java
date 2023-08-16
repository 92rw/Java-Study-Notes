package Exercise.TankGame;

import java.io.*;
import java.util.Vector;

public class Recorder {//用于记录相关信息，和文件交互

    //定义变量，记录我方击毁敌人坦克数
    private static int destoryedEnemyTank = 0;
    private static int playerTankNum = 2;
    //定义IO对象
    //private static 是为了能在本类中直接调用，不可被其他类直接实例化。而且不需要新建对象就能调用方法
    //再加上 做游戏得一启动就要加载显示的内容
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "src\\Exercise\\TankGame\\TankGameRecord.txt";
    //定义Vector<EnemyTank>，指向 GamePanel类中的集合类
    private static Vector<EnemyTank> enemyTanks = null;
    ///定义Vector<Node>，保存坦克信息
    private static Vector<Node> nodes = new Vector<>();

    public static String getRecordFile() {//返回记录文件的存储位置
        return recordFile;
    }

    public static int getDestoryedEnemyTank() {
        return destoryedEnemyTank;
    }

    public static int getPlayerTankNum() {
        return playerTankNum;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //当敌方坦克被击毁，则记录的数据++
    public static void addDestoryedEnemyTank() {
        Recorder.destoryedEnemyTank++;
    }
    public static void minusPlayerTank() {
        Recorder.playerTankNum--;
    }

    //当游戏退出时，保存击毁敌方坦克数、敌方坦克坐标和方向
    public static void saveRecord() {
        if (enemyTanks.size() != 0) {//如果敌人数量不为零则创建文件并保存数据，否则删除文件
            try {
                bw = new BufferedWriter(new FileWriter(recordFile));
                bw.write(destoryedEnemyTank + "\r\n");//bw.write写入int类型会乱码，需要将其转成字符串+""
                //bw.newLine();//用这个方法增加新行可能会乱码

                //遍历敌人坦克的Vector，根据情况保存
                for (int i = 0; i < enemyTanks.size(); i++) { //遍历敌人
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank.isLive) {//保险起见，再次判断
                        String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirection();
                        bw.write(record + "\r\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            File file = new File(recordFile);
            file.delete();
        }
    }
    //读取文件中记录的数据，该方法在继续上局游戏时调用
    public static Vector<Node> readRecord() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            destoryedEnemyTank = Integer.parseInt(br.readLine());
            //循环读取
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] tankInfo = line.split(" ");
                for (int i = 0; i < tankInfo.length; i++) {
                    System.out.println(tankInfo[i]);
                }
                Node node = new Node(Integer.parseInt(tankInfo[0]),
                        Integer.parseInt(tankInfo[1]), tankInfo[2]);
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nodes;
    }
}
