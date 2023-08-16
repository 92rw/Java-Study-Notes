package Exercise.TankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

//坦克大战的绘图区域

//实现 KeyListener 接口，用于监听键盘事件
//实现 Runnable 接口，当做线程使用，以便重绘炮弹
public class GamePanel extends JPanel implements KeyListener,Runnable {
    //定义己方坦克
    Vector<MyTank> players = new Vector<>();
    //定义敌方坦克，放入 Vector 集合
    Vector<EnemyTank> enemies = new Vector<>();

    //定义Vector<Node>，用于恢复敌人坦克的坐标和方向信息
    Vector<Node> nodes = new Vector<>();

    //定义爆炸效果
    //当炮弹击中坦克时，加入一个Explosion对象到explode
    Vector<Explosion> explode = new Vector<>();

    int enemySize = 8;
    MyTank playerTank = null;

    //定义三张爆炸图片，用于显示效果、
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public GamePanel(String key) {//构造器

        //初始化己方坦克

        playerTank = new MyTank(450,500);
        playerTank.setSpeed(15);
        players.add(playerTank);


        //遍历初始化敌方坦克
        switch (key){
            case "Y":

                nodes = Recorder.readRecord();
                //遍历初始化敌方坦克
                for (int i = 0; i < nodes.size(); i++) {//这里不能用size()，刚创建的Vector集合的size() == 0;
                    Node node = nodes.get(i);
                    //创建敌方坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    enemyTank.setDirection(node.getDirection());//设置初始朝向

                    enemies.add(enemyTank);
                    //判断是否碰撞
                    enemyTank.setEnemyTanks(enemies);

                    //启动线程
                    new Thread(enemyTank).start();
                    //将记录敌人坦克数的Vector集合类赋给 Record类中的enemyTanks对象
                    Recorder.setEnemyTanks(enemies);
/*
                    //发射炮弹
                    Shot shot = null;
                    if (node.getDirection() == "Up") {
                        shot = new Shot(node.getX() + 20, node.getY(), node.getDirection());
                    } else if (node.getDirection() == "Down") {
                        shot = new Shot(node.getX() + 20, node.getY() + 60, node.getDirection());
                    } else if (node.getDirection() == "Left") {
                        shot = new Shot(node.getX(), node.getY() + 20, node.getDirection());
                    } else if (node.getDirection() == "Right") {
                        shot = new Shot(node.getX() + 60, node.getY() + 20, node.getDirection());
                    }
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();

                    //坦克加入到集合中
                    enemies.add(enemyTank);*/
                }
                break;
            case "N"://开始新游戏
                //Recorder.setDestoryedEnemyTank(0);//不设置零的话，将读取存档文件中的数据

                for (int i = 0; i < enemySize; i++) {//这里不能用size()，刚创建的Vector集合的size() == 0;
                    //创建敌方坦克
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    //将坦克集合传入EnemyTank类
                    enemyTank.setEnemyTanks(enemies);

                    enemyTank.setDirection("Down");//设置初始朝向
                    enemies.add(enemyTank);//坦克加入到集合中
                    //启动线程
                    new Thread(enemyTank).start();

                    //发射炮弹
                    Shot enemyShot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, "Down");
                    enemyTank.shots.add(enemyShot);
                    new Thread(enemyShot).start();



                }
                break;

            default:
                System.out.println("你的输入有误");
        }

        //实现爆炸效果的三个文件，将其放在"out\production\项目名"目录下，否则报空指针异常
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));

        Recorder.setEnemyTanks(enemies);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);

        showInfo(g);//显示玩家击毁坦克的信息
        //绘制己方坦克
        for (int i = 0; i < players.size(); i++) {
            MyTank playerTank = players.get(i);
            drawTank(playerTank.getX(), playerTank.getY(), g, playerTank.getDirection(), 1);//判断条件：(playerTank != null && playerTank.isLive)

            //绘制己方炮弹（多发炮弹）
            for (int j = 0; j < playerTank.shots.size(); j++) {
                Shot shot = playerTank.shots.get(j);
                if (/*shot != null && playerTank.isLive &&*/ shot.isRunning ) { //判断条件：存在运行中的炮弹
                    g.fill3DRect(shot.x - 1, shot.y - 1, 3, 3, false);
                } else { //如果该shot对象已经无效，从集合中删除
                    playerTank.shots.remove(shot);
                }
            }
        }
        //绘制己方炮弹（单发炮弹）
        //if里面的那个条件不按照这个顺序，将会出现白框和空指针异常
//        if (playerTank.shot != null && playerTank.isLive && playerTank.shot.isRunning) { //判断条件：存在运行中的炮弹
//            g.fill3DRect(playerTank.shot.x, playerTank.shot.y, 1, 1, false);
//        }

        //绘制敌方坦克，遍历 Vector
        for (int i = 0; i < enemies.size(); i++) {
            EnemyTank enemyTank = enemies.get(i);
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(),0);
                //绘制炮弹：此处不能使用增强for循环
                //迭代器或增强for循环无法动态删除 enemyTank.enemyShots 集合中的元素，
                // 用普通for则不会抛出 ConcurrentModificationException 异常
                //for (Shot shot :enemyTank.enemyShots) {
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if(shot != null && shot.isRunning) {
                        g.fill3DRect(shot.x - 1, shot.y - 1, 3, 3, false);
                    } else {
                        //从Vector 中移除该炮弹
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }

        //如果explosion集合中有对象，就绘制爆炸效果
        //打掉一个坦克，会循环9次是因为：MyPanel使用了线程，在run方法里一直在重绘，
        // 每次重绘都会调用paint方法，直到life=0，bomb从集合里删除，这个for循环就不再被调用了

        for (int i = 0; i < explode.size(); i++) {
            Explosion explosion = explode.get(i);
            //爆炸的图片初始化后，被第一次使用就会导致paint的调用
            //第一个爆炸没显示出来的可以在MyPanel的paint方法里取出bomb的前面，休眠一下，如Thread.sleep(50);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (explosion.life > 6) {
                g.drawImage(image1, explosion.x, explosion.y, 60, 60, this);
            } else if (explosion.life > 3) {
                g.drawImage(image2, explosion.x, explosion.y, 60, 60, this);
            } else {
                g.drawImage(image3, explosion.x, explosion.y, 60, 60, this);
            }
            explosion.lifeDown();

            if(explosion.life == 0) {//完成后移除
                explode.remove(explosion);
            }
        }



    }

    //编写方法，绘制坦克：快速注解变量类型的快捷键是"/**"，按回车

    /**
     *
     * @param x 坦克左上角x坐标
     * @param y 坦克左上角y坐标
     * @param g 画笔
     * @param direction 坦克方向（上下左右）
     * @param type 坦克类型
     */
    public void drawTank(int x, int y, Graphics g, String direction, int type){
        //给不同坦克类型设置不同颜色
        switch (type) {
            case 0: //敌方坦克
                g.setColor(Color.cyan);//青色
                break;
            case 1: //我方坦克
                g.setColor(Color.yellow);//黄色
                break;
        }

        //根据坦克方向绘制图形，direction 限定了坦克朝向
        switch (direction){
            case "Up":
                g.fill3DRect(x, y,10,60, false);                   //左侧履带
                g.fill3DRect(x + 30, y,10,60, false);           //右侧履带
                g.fill3DRect(x + 10, y + 10 ,20,40, false);  //中部炮台
                g.fillOval(x + 10, y + 20 ,20,20);                  //炮塔
                g.fill3DRect(x + 18, y - 2, 4, 4, false);    //炮口
                g.drawLine(x + 20 , y + 30,x + 20, y);                     //炮膛
                for (int i = 2; i < 60; i += 4) {
                    g.drawLine(x + 1, y + i, x + 9, y + i);
                    g.drawLine(x + 31, y + i, x + 39, y + i);
                }
                break;
            case "Down":
                g.fill3DRect(x, y,10,60, false);                   //左侧履带
                g.fill3DRect(x + 30, y,10,60, false);           //右侧履带
                g.fill3DRect(x + 10, y + 10 ,20,40, false);  //中部炮台
                g.fillOval(x + 10, y + 20 ,20,20);                  //炮塔
                g.fill3DRect(x + 18, y + 58, 4, 4, false);
                g.drawLine(x + 20 , y + 30,x + 20, y + 60);            //炮管
                for (int i = 2; i < 60; i += 4) {
                    g.drawLine(x + 1, y + i, x + 9, y + i);
                    g.drawLine(x + 31, y + i, x + 39, y + i);
                }
                break;
            case "Left":
                g.fill3DRect(x, y,60,10, false);                   //上侧履带
                g.fill3DRect(x, y + 30,60,10, false);           //下侧履带
                g.fill3DRect(x + 10, y + 10 ,40,20, false);  //中部炮台
                g.fillOval(x + 20, y + 10 ,20,20);                  //炮塔
                g.fill3DRect(x - 2, y + 18, 4, 4, false);
                g.drawLine(x + 30, y + 20, x, y + 20);
                for (int i = 2; i < 60; i += 4) {
                    g.drawLine(x + i, y + 1, x + i, y + 9);
                    g.drawLine(x + i, y + 31, x + i, y + 39);

                }
                break;
            case "Right":
                g.fill3DRect(x, y,60,10, false);                   //上侧履带
                g.fill3DRect(x, y + 30,60,10, false);           //下侧履带
                g.fill3DRect(x + 10, y + 10 ,40,20, false);  //中部炮台
                g.fillOval(x + 20, y + 10 ,20,20);                  //炮塔
                g.fill3DRect(x + 58, y + 18, 4, 4, false);
                g.drawLine(x + 30 , y + 20,x + 60, y + 20);             //炮管
                for (int i = 2; i < 60; i += 4) {
                    g.drawLine(x + i, y + 1, x + i, y + 9);
                    g.drawLine(x + i, y + 31, x + i, y + 39);
                }
                break;
            default:
                System.out.println("方向数据缺失");
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    //处理WSAD键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("用户按下 " + e.getKeyChar());//测试代码

        for (MyTank hero : players) {
            if (e.getKeyCode() == KeyEvent.VK_W) {//上
                hero.moveUp();
                hero.setDirection("Up");
            } else if (e.getKeyCode() == KeyEvent.VK_D) {//右
                hero.setDirection("Right");
                hero.moveRight();
            } else if (e.getKeyCode() == KeyEvent.VK_S) {//下
                hero.setDirection("Down");
                hero.moveDown();
            } else if (e.getKeyCode() == KeyEvent.VK_A) {//左
                hero.setDirection("Left");
                hero.moveLeft();
            }
            if (e.getKeyCode() == KeyEvent.VK_J) {//按J发射子弹
                hero.shotEnemy();
            }

/*      char keypressed = Character.toLowerCase(e.getKeyChar());
            switch (keypressed) {
                case 'w':
                    playerTank.setDirection("Up");
                    playerTank.moveUp();
                    break;
                case 's':
                    playerTank.setDirection("Down");
                    playerTank.moveDown();
                    break;
                case 'a':
                    playerTank.setDirection("Left");
                    playerTank.moveLeft();
                    break;
                case 'd':
                    playerTank.setDirection("Right");
                    playerTank.moveRight();
                    break;
                case 'j':
                    //System.out.println("用户按下J键，发射炮弹");
                    //注意：shot 对应的线程消亡，对象未销毁，因此是两个判断条件
                    //发射一颗炮弹
    //                if (playerTank.shot == null || !playerTank.shot.isRunning) playerTank.playerTankShot();
                    playerTank.shotEnemy();
                }
                this.repaint()//刷新画布
                    */

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {

        while (true) {
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < enemies.size(); i++) {
                EnemyTank enemyTank = enemies.get(i);
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    for (int k = 0; k < players.size(); k++) {
                        MyTank playerTank = players.get(k);
                        hitTank(shot, playerTank);
                    }
                }
                for (int j = 0; j < players.size(); j++) {
                    MyTank myTank = players.get(j);
                    for (int k = 0; k < myTank.shots.size(); k++) {
                        Shot shot = myTank.shots.get(k);
                        hitTank(shot,enemyTank);
                    }
                }
            }
        }
    }
/*
    //编写方法判断我方炮弹是否命中
    //如果不判断地方坦克的isLive状态，那么就算已经消失，炮弹也无法穿透
    //如果坦克不消失，是因为没有让坦克生命周期结束 绘制也没有结束

    //当同时发射多颗炮弹时，需要传入集合在方法中进行判断
    public void hitEnemyTank() {
        //判断敌方坦克是否被击中
        for (int j = 0; j < playerTank.shots.size(); j++) {
            Shot shot = playerTank.shots.get(j);
            //判断炮弹是否还在运行
            if (shot != null && shot.isRunning) {//玩家的炮弹处在运行状态
                //for (EnemyTank enemyTank :enemyTanks) {//当执行的语句中存在集合元素删减，不能用增强for循环
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot, enemyTank);
                }
            }
        }
            下面这段代码仅适用于单炮弹的情况
            if (playerTank.shot != null && playerTank.shot.isRunning) {//玩家的炮弹处在运行状态
                //for (EnemyTank enemyTank :enemyTanks) {//当执行的语句中存在集合元素删减，不能用增强for循环
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(playerTank.shot, enemyTank);
                }



    }
    //编写方法，判断敌方炮弹是否击中我方坦克
    public void hitPlayerTank() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.enemyShots.size(); j++) {
                Shot shot = enemyTank.enemyShots.get(j);
                if (shot != null && playerTank.isLive && shot.isRunning) {
                    hitTank(shot, playerTank);
                }
            }
        }
    }
*/
    //无论是哪方坦克，都调用这个方法判断是否被击中
    public void hitTank(Shot s, Tank tank) {
        switch (tank.getDirection()) {
            case "Up":
            case "Down":
                if (tank.isLive && s != null && s.x > tank.getX() && s.x < tank.getX() + 40
                && s.y > tank.getY() && s.y < tank.getY() + 60) {
                    s.isRunning = false;
                    tank.isLive = false;
                    if (tank instanceof EnemyTank) {
                        //将敌方坦克从集合中拿掉，否则后续炮弹无法穿透
                        enemies.remove(tank);
                        Recorder.addDestoryedEnemyTank();
                    }
                    if (tank instanceof MyTank) {
                        players.remove(tank);
                        Recorder.minusPlayerTank();
                        if (Recorder.getPlayerTankNum() > 0) {
                            playerTank = new MyTank(500, 500);
                            playerTank.setSpeed(15);
                            players.add(playerTank);
                        }
                    }
                    //创建一个explode对象
                    Explosion explosion = new Explosion(tank.getX(), tank.getY());
                    explode.add(explosion);
                    //在完成绘图之后加载音乐文件
                    new AePlayWave("src\\Exercise\\TankGame\\111.wav").start();
                }
                break;
            case "Left":
            case "Right":
                if (tank.isLive && s != null && s.x > tank.getX() && s.x < tank.getX() + 60 /* && tank.isLive == true */
                        && s.y > tank.getY() && s.y < tank.getY() + 40) {
                    s.isRunning = false;
                    tank.isLive = false;
                    if (tank instanceof EnemyTank) {
                        enemies.remove(tank);
                        Recorder.addDestoryedEnemyTank();
                    }
                    if (tank instanceof MyTank) {
                        players.remove(tank);
                        Recorder.minusPlayerTank();
                        if (Recorder.getPlayerTankNum() > 0) {
                            playerTank = new MyTank(500, 500);
                            playerTank.setSpeed(15);
                            players.add(playerTank);
                        }
                    }
                    Explosion explosion = new Explosion(tank.getX(), tank.getY());
                    explode.add(explosion);
                    //在完成绘图之后加载音乐文件
                    new AePlayWave("src\\Exercise\\TankGame\\111.wav").start();
                }
                break;
        }
    }

    public void showInfo(Graphics g){//记录击毁敌方坦克的信息
        //画出玩家的总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("累计击毁敌方坦克", 1020, 30);
        g.drawString("当前玩家坦克生命", 1020, 200);
        g.drawString(Recorder.getDestoryedEnemyTank() + "",1080,100);
        g.drawString(Recorder.getPlayerTankNum() + "",1080,270);
        //这个方法修改了画笔颜色，因此后续代码需要改画笔颜色
        drawTank(1020, 60, g, "Up", 0);
        drawTank(1020, 230, g, "Up", 1);


        g.setColor(Color.red);
        if (enemies.size() == 0) {//胜利信息
            Font font1 = new Font("宋体", Font.BOLD, 80);
            g.setFont(font1);
            g.drawString("YOU WIN", 350, 350);
        }

        if (players.size() == 0) {//失败信息
            Font font2 = new Font("宋体", Font.BOLD, 80);
            g.setFont(font2);
            g.drawString("Game Over", 350, 350);
        }

    }
}
