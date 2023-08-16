package Exercise.TankGame;

public class Node {//记录坦克的信息
    //根据jdk版本，自定义的Node可能会和系统类重名，如果Node显示报错就改成myNode
    private int x;
    private int y;
    private String direction;

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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Node(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}
