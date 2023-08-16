package Exercise.TankGame;

import java.util.Vector;

public class Tank {
    private int x;//坦克的横坐标
    private int y;//坦克的纵坐标
    private String direction = "Up"; //坦克炮管朝向
    private int speed = 1;//坦克移动速度
    boolean isLive = true;
    Vector<Shot> shots = new Vector<>();
    Shot shot; //定义一个设计对象，表示炮弹线程

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //坦克移动
    public void moveUp() { if (y >= 3) y -= speed; }

    public void moveDown() { if (y <= 687) y += speed; }

    public void moveLeft() { if (x >= 3) x -= speed; }

    public void moveRight() {if (x <= 937)x += speed; }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Shot tankShot(){

        switch (direction) {
            case "Up":
                return new Shot(x + 20, y, direction);
            case "Down":
                return new Shot(x + 20, y + 60, direction);

            case "Left":
                return new Shot(x, y + 20, direction);

            case "Right":
                return new Shot(x + 60, y + 20, direction);
            default:
                return null;
        }
    }

}
