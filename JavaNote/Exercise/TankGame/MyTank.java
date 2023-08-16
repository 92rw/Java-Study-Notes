package Exercise.TankGame;

public class MyTank extends Tank {

    public MyTank(int x, int y) {
        super(x, y);
    }

    //射击
    public void shotEnemy(){
        //创建 Shot 对象，并根据当前坦克方向设置其初始参数
        if (shots.size() == 5) {
            return;
        }

        shot = super.tankShot();
        //炮弹类继承Runnable接口
        new Thread(shot).start();
        //把新创建的shot放进shots
        shots.add(shot);
    }
}

