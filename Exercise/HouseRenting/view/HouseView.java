package Exercise.HouseRenting.view;

import Exercise.HouseRenting.domain.House;
import Exercise.HouseRenting.service.HouseService;
import Exercise.HouseRenting.utils.Utility;
/*
- HouseView.java <=>类（界面）
1）显示主菜单
2）接收用户输入
3）调用其他类(HouseService类)完成对房屋信息的各种操作
代码实现分析：
编写 mainMenu() 方法显示主菜单
编写 listHouses() 方法调用 HouseService 的房屋列表
编写 addHouses() 方法接收用户输入
编写 delHouses() 方法接收用户输入id
编写 findHouse() 方法接收用户输入id
编写 updateHouse() 方法接收用户输入id
 */
public class HouseView {

    private boolean loop = true;//控制显示菜单
    private char key = ' ';//接收用户选择
    private HouseService houseService = new HouseService(5);//设置数组大小

    //显示主菜单
    public void mainMenu() {
        do{
            System.out.println("\n==============房屋租赁系统==============");
            System.out.println("\t\t\t1 新 增 房 源");
            System.out.println("\t\t\t2 查 找 房 屋");
            System.out.println("\t\t\t3 删 除 房 屋");
            System.out.println("\t\t\t4 修改房屋信息");
            System.out.println("\t\t\t5 显示房屋列表");
            System.out.println("\t\t\t6 退       出");
            System.out.println("请输入你的选择(1-6)");
            key = Utility.readChar();
            switch (key) {
                case '1':
                    addHouse();
                    break;
                case '2':
                    findHouse();
                    break;
                case '3':
                    delHouse();
                    break;
                case '4':
                    updateHouse();
                    break;
                case '5':
                    listHouses();
                    break;
                case '6':
                    exit();
                    break;
            }
        } while(loop);
    }

    //新增房屋
    public void addHouse() {
        System.out.println("\n-----------------添加房屋-----------------");
        System.out.print("姓名： ");
        String name = Utility.readString(10);//限制名字长度为10
        System.out.print("电话： ");
        int phoneNum = Utility.readInt();
        System.out.print("地址： ");
        String address = Utility.readString(16);
        System.out.print("房租： ");
        int monthRent = Utility.readInt();
        System.out.print("状态： ");
        String state = Utility.readString(3);
        //创建一个新的House对象，注意id是系统分配的，用户不能输入
        House newHouse = new House(0,name,address,phoneNum,monthRent,state);
        if(houseService.add(newHouse)) {
            System.out.println("-------------添加房屋成功-------------");
            } else System.out.println("-------------添加房屋失败-------------");
        }

    //删除房源
    public void delHouse() {
        System.out.println("\n---------------删除房屋---------------");
        System.out.println("请输入待删除房屋的编号(-1退出)：");
        int delId = Utility.readInt();
        if(delId == -1) {
            System.out.println("-------------放弃删除房屋-------------");
            return;
        }
        //System.out.println("请确认输入是否删除(Y/N)，小心选择：");
        char choice = Utility.readConfirmSelection();
        if(choice == 'Y') {
            if(houseService.del(delId)) {
                System.out.println("---------------删除房屋成功---------------");
            }
        } else System.out.println("---------------删除房屋失败---------------");

    }

    //修改房屋信息
    public void updateHouse() {
        System.out.println("---------------修改房屋信息---------------");
        System.out.println("请输入待修改房屋的编号(-1退出)：");
        int updateNum = Utility.readInt();
        if(updateNum == -1) {
            System.out.println("-------------放弃修改信息-------------");
            return;
        }
        //根据输入的id查找对象
        House house = houseService.find(updateNum);//返回的是引用类型，即HouseService中的那个数组元素

        if(house == null) {
            System.out.println("--------此编号不存在，无法修改--------");
            return;
        }

        System.out.print("姓名（" + house.getName()+ "）：");
        String name = Utility.readString(10,"");//如果用户直接回车，表示不修改该信息
        if(!"".equals(name)) {//如果输入为空，则执行下面的代码
            house.setName(name);
        }
        System.out.print("电话（" + house.getPhoneNum()+ "）：");
        int phone = Utility.readInt(-1);
        if(phone != -1){//如果输入为空，则执行下面的代码
            house.setPhoneNum(phone);
        }
        System.out.print("地址（" + house.getAddress()+ "）：");
        String address = Utility.readString(15,house.getAddress());//利用默认值省掉判断流程
        house.setAddress(address);
        System.out.print("租金（" + house.getMonthRent()+ "）：");
        int rent = Utility.readInt(-1);
        if(rent != -1){
            house.setMonthRent(rent);//int 值可以直接赋给double
        }
        System.out.print("状态（" + house.getState()+ "）：");
        String state = Utility.readString(3,"");
        if(!state.equals("")){
            house.setState(state);
        }
        System.out.println("---------------修改信息成功---------------");
    }




    //查询房屋信息
    public void findHouse() {
        System.out.println("\n---------------查找房屋---------------");
        System.out.println("请输入要查找房屋的编号(-1改为按地址查询)：");
        int inputId = Utility.readInt();
        if(inputId == -1){
            this.findAddress();
            return;
        }
        House house = houseService.find(inputId);
        if(house != null){
            System.out.println("编号\t房主\t\t地址\t\t电话\t月租\t状态（未出租/已出租）");
            System.out.println(house);
        }else System.out.println("---------------房屋不存在---------------");
    }
    public void findAddress(){
        System.out.println("请输入房屋地址信息");
        String str = Utility.readString(10);
        House house = houseService.findStr(str);
        if(house != null){
            System.out.println("编号\t房主\t\t地址\t\t电话\t月租\t状态（未出租/已出租）");
            System.out.println(house);
        }else System.out.println("---------------房屋不存在---------------");
    }

    //显示房屋列表
    public void listHouses() {
        System.out.println("\n-----------------房屋列表-----------------");
        System.out.println("编号\t房主\t\t地址\t\t电话\t月租\t状态（未出租/已出租）");
        House[] houses = houseService.list();//得到所有房屋信息
        for (int i = 0; i < houses.length; i++) {
            if(houses[i] == null){//如果房屋列表为空，没有必要显示
                break;
            }
            System.out.println(houses[i]);
        }
        System.out.println("-------------房屋列表显示完毕-------------");;
    }

    //使用Utility提供的方法，完成确认
    public void exit() {
        char c = Utility.readConfirmSelection();
        if (c == 'Y') {
            System.out.println("已退出房屋租赁系统");
            loop = false;
        }
    }
}
