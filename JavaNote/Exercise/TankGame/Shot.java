package Exercise.TankGame;

public class Shot implements Runnable{
    int x;
    int y;
    String direction;
    int speed = 8;
    boolean isRunning = true; //监视炮弹是否还在移动

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Shot(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public void run() {
        while (true) {

            //炮弹设置休眠，否则出现即到终点
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向改变坐标
            switch (direction) {
                case "Up":
                    y -= speed;
                    break;
                case "Down":
                    y += speed;
                    break;
                case "Left":
                    x -= speed;
                    break;
                case "Right":
                    x += speed;
                    break;
            }

            //测试代码，判断炮弹坐标，测试完成后注销
            //System.out.println(String.format("炮弹坐标为(%d,%d)",x,y));

            //炮弹销毁条件：超出画框，或是碰撞到物体
            if(!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isRunning)) {
                System.out.println("炮弹线程" + Thread.currentThread().getName() + "退出");
                isRunning = false;
                break;
            }
        }
    }
}
