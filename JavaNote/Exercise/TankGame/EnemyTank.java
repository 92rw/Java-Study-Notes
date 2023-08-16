package Exercise.TankGame;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
    private String newDirection;

    Vector<EnemyTank> enemyTanks = new Vector<>();


    public EnemyTank(int x, int y) {
        super(x, y);
    }

    //构造方法，得到 GamePanel 类的成员 Vector<EnemyTank>
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }
    //编写方法，判断是否和其他坦克重叠或碰撞
    //方法1：利用if语句分别判断
    //1. 如果敌人坦克是上/下 x的范围 [enemyTank.getX(), enemyTank.getX() + 40]
    //                     y的范围 [enemyTank.getY(), enemyTank.getY() + 60]
    //2. 如果敌人坦克是右/左  x的范围 [enemyTank.getX(), enemyTank.getX() + 60]
    //                     y的范围 [enemyTank.getY(), enemyTank.getY() + 40]
    //当前坦克 左上角的坐标 [this.getX(), this.getY()]     右上角的坐标 [this.getX() + 40, this.getY()]
    //        左下角的坐标 [this.getX(), this.getY() + 60]右下角的坐标 [this.getX() + 60, this.getY() + 40]
    //方法2：利用Rectangle碰撞检测：
    /*二者顶点坐标就是get(x)和get(y)
      const isTrue = r1x + r1w >= r2x && // 矩形 2 左边界
                 r1x <= r2x + r2w && // 矩形 2 右边界
                 r1y + r1h >= r2y && // 矩形 2 上边界
                 r1y <= r2y + r2h; // 矩形 2 下边界
*/


    public boolean isCollision(){
        int r1x, r1y, r2x, r2y, r1w , r1h, r2w, r2h;
        //判断当前坦克方向
        switch (this.getDirection()){

            case "Up":
            case "Down":
                r1x = this.getX();
                r1y = this.getY();
                r1w = 40;
                r1h = 60;
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank anotherTank= enemyTanks.get(i);
                    //不和自己比较
                    if (this != anotherTank) {
                        r2x = anotherTank.getX();
                        r2y = anotherTank.getY();
                        //敌方坦克上下移动
                        if (anotherTank.getDirection() == "Up" || anotherTank.getDirection() == "Down") {
                            r2w = 40;
                            r2h = 60;
                            if ((r1x + r1w >= r2x && r1x <= r2x + r2w && r1y + r1h >= r2y && r1y <= r2y + r2h)) return true;
                        }
                        if (anotherTank.getDirection() == "Left" || anotherTank.getDirection() == "Right") {
                            r2w = 60;
                            r2h = 40;
                            if ((r1x + r1w >= r2x && r1x <= r2x + r2w && r1y + r1h >= r2y && r1y <= r2y + r2h)) return true;
                        }
                    }
                }
                break;
            case "Left":
            case "Right":
                r1x = this.getX();
                r1y = this.getY();
                r1w = 60;
                r1h = 40;
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank anotherTank= enemyTanks.get(i);
                    //不和自己比较
                    if (this != anotherTank) {
                        r2x = anotherTank.getX();
                        r2y = anotherTank.getY();
                        //敌方坦克上下移动
                        if (anotherTank.getDirection() == "Up" || anotherTank.getDirection() == "Down") {
                            r2w = 40;
                            r2h = 60;
                            if ((r1x + r1w >= r2x && r1x <= r2x + r2w && r1y + r1h >= r2y && r1y <= r2y + r2h)) return true;
                        }
                        if (anotherTank.getDirection() == "Left" || anotherTank.getDirection() == "Right") {
                            r2w = 60;
                            r2h = 40;
                            if ((r1x + r1w >= r2x && r1x <= r2x + r2w && r1y + r1h >= r2y && r1y <= r2y + r2h)) return true;
                        }
                    }
                }
                break;
            }
        return false;

    }



    @Override
    public void run() {
        while (true) {
            int EnemyMaxShot = 1; //此处设置为每个敌人坦克炮弹只能同时拥有1颗运行中的炮弹
            if (isLive && shots.size() < EnemyMaxShot) {
                for (int i = shots.size(); i < EnemyMaxShot; i++) {
                    shot = super.tankShot();
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //放入run里是因为可以一直判断敌人炮弹是X否已全部销毁
                    new Thread(shot).start();
                    shots.add(shot);
                }
            }

            //根据当前方向开始移动
            int random = (int)(Math.random()*60 + 1 );//设置随机移动距离1~60
            switch (getDirection()) {
                case "Up":
                    for (int i = 0; i < random; i++) {
                        if (getY() > 0 && !isCollision()){
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case "Down":
                    for (int i = 0; i < random; i++) {
                        if (getY() + 60 < 750 && !isCollision()) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case "Left":
                    for (int i = 0; i < random; i++) {
                        if (getX() > 0 && !isCollision()) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case "Right":
                    for (int i = 0; i < random; i++) {
                        if (getX() + 60 < 1000 && !isCollision()) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
            }



            //设置新方向
            switch ((int)(Math.random() * 4)) {
                case 0:
                    newDirection = "Up";
                    break;
                case 1:
                    newDirection = "Down";
                    break;
                case 2:
                    newDirection = "Left";
                    break;
                case 3:
                    newDirection = "Right";
                    break;
            }
            setDirection(newDirection);
            //线程退出
            if (!isLive) break;
        }
    }
}
