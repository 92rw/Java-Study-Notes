package Exercise.TankGame;

public class Explosion {
    int x, y; //炮弹的坐标
    int life = 9; //炮弹的生命周期
    boolean isLive = true; //是否还存活

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //减少生命值
    public void lifeDown() { //配合出现图片的爆炸效果
        if(life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }
}
