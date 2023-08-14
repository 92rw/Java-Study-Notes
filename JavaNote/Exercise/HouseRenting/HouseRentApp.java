package Exercise.HouseRenting;

import Exercise.HouseRenting.view.HouseView;

public class HouseRentApp {
    public static void main(String[] args) {
        //创建匿名HouseView对象，执行菜单界面
        new HouseView().mainMenu();
        System.out.println("==已退出房屋出租系统==");
    }
}
