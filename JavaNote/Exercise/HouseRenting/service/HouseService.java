package Exercise.HouseRenting.service;

import Exercise.HouseRenting.domain.House;//需要用快捷键 Alt+Enter 调用 House 类

/*
- HouseService.java <=>类（业务层）
1）响应 HouseView 的调用
2）完成房屋信息 CRUD(create,read,update,delete)
代码实现分析：
定义 House[] 数组保存房间对象
编写 list() 方法返回房屋信息
编写 add(House newHouse) 方法把新对象加入数组，返回 boolean
编写 del(int delId) 方法删除房屋信息，返回 boolean
编写 find(int delId) 方法返回 House 对象，否则返回 null
 */
public class HouseService {

    private House[] houses; //保存 House 对象
    private int countHouse = 1; //记录已录入房源数
    private int idCounter = 1; //记录当前id数

    public HouseService(int size) {
        houses = new House[size]; //创建对象时指定数组大小
        //为了方便测试，此处初始化一个对象
        houses[0] = new House(1,"jace", "Gunma",114514,1919, "未出租");

    }
    //list 方法，返回数组信息
    public House[] list() {
        return houses;
    }

    public boolean add(House newHouse) {
        //判断是否可以继续添加对象（此处暂不考虑数组扩容问题）
        if(countHouse == houses.length) {
            System.out.println("数组已满，不能添加");
            return false;
        }
        //把 newHouse 对象加入到数组后，房源数自增
        houses[countHouse++] = newHouse;
        //房屋编号需要更新，先自增再赋值
        //因为这里的标号相当于主键，是唯一且自增的！！
        newHouse.setId(++idCounter);
        return true;
    }

    public boolean del(int delId){
        //找到 delId 对应的下标，注意和房屋编号不是一个概念
        int delindex = -1;
        for (int i = 0; i < countHouse; i++) {
            if(delId == houses[i].getId()){
                delindex = i;//使用 index 记录要删除的房屋在数组中的下标
            }
        }
        if(delindex == -1){//未找到此房屋编号
            System.out.println("未找到此房屋编号");
            return false;
        }
        //将index之后的覆盖到前一位，数组最后一位置空
        for (int i = delindex; i < houses.length-1; i++) {
            houses[i] = houses[i + 1];//如果被删除的是最后一位，那么这段代码就不会被执行到
        }
        houses[--countHouse] = null;//既删除最后一位，又让房源数减一
        return true;
    }

    public House find(int findId) {
        for (int i = 0; i < countHouse; i++) {
            if(findId == houses[i].getId()){
                return houses[i];
            }
        }
        return null;
    }

    public House findStr(String findStr) {
        for (int i = 0; i < countHouse; i++) {
            if(findStr.equals(houses[i].getAddress())){
                return houses[i];
            }
        }
        return null;
    }
}
