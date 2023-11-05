package Exercise.Restaurant.view;

import Exercise.Restaurant.domain.*;
import Exercise.Restaurant.service.BillService;
import Exercise.Restaurant.service.DiningTableService;
import Exercise.Restaurant.service.EmployeeService;
import Exercise.Restaurant.service.MenuService;
import Exercise.Restaurant.utils.Utility;

import java.util.List;

public class MHLView {
    //控制是否退出菜单
    private boolean loop = true;
    //接收用户输入
    private String key = "";
    //和数据库相关的属性
    private EmployeeService employeeService = new EmployeeService();
    private DiningTableService diningTableService = new DiningTableService();
    private MenuService menuService = new MenuService();
    private BillService billService = new BillService();


    public static void main(String[] args) {
        //new MHLView().secondaryMenu();
        new MHLView().mainMenu();
    }


    //显示主菜单
    public void mainMenu() {
        while (loop) {
            System.out.println("==========满汉楼==========");
            System.out.println("\t\t 1 登录满汉楼");
            System.out.println("\t\t 2 退出满汉楼");
            System.out.println("请输入你的选择：");
            key = Utility.readString(1);
            switch (key) {
                case "1" :
                    System.out.println("请输入员工号：");
                    String empId = Utility.readString(50);
                    System.out.println("请输入密码：");
                    String pwd = Utility.readString(50);
                    //
                    Employee employee = employeeService.getEmployeeByIdAndPwd(empId, pwd);
                    if(employee != null) {
                        System.out.println("==="+ employee.getName() + "登录成功===");
                        //显示二级菜单
                        secondaryMenu();
                    } else {
                        System.out.println("==========登录失败==========");
                    }
                    break;
                case "2":
                    System.out.println("退出一级界面");
                    loop = false;
                    break;
                default:
                    System.out.println("输入有误。请重新输入");
            }
        }
        System.out.println("已退出满汉楼系统");
    }

    private void secondaryMenu(){
        while (loop) {
            System.out.println("==========满汉楼（二级菜单）==========");
            System.out.println("\t\t 1 显示餐桌状态");
            System.out.println("\t\t 2 预订餐桌");
            System.out.println("\t\t 3 显示所有菜品");
            System.out.println("\t\t 4 点餐服务");
            System.out.println("\t\t 5 查看账单");
            System.out.println("\t\t 6 结账");
            System.out.println("\t\t 9 退出满汉楼");
            System.out.println("请输入你的选择：");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    diningTableService.listDiningTable();
                    System.out.println("=====显示完毕=====");
                    break;
                case "2"://预订餐桌
                    orderDiningTable();
                    break;
                case "3"://显示所有菜品
                    listMenu();
                    break;
                case "4"://点餐服务
                    orderMenu();
                    break;
                case "5"://查看账单
                    listBill();
                    break;
                case "6"://结账
                    payBill();
                    break;
                case "9":
                    System.out.println("退出二级界面");
                    loop = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入");
            }
        }
    }

    private void orderDiningTable() {
        System.out.println("======预订餐桌======");
        System.out.println("请选择要预定的餐桌编号(-1退出)：");
        int orderId = Utility.readInt();
        if(orderId == -1) {
            System.out.println("===放弃预订餐桌===");
            return;
        }
        //执行方法，得到字符Y或N
        char key = Utility.readConfirmSelection();
        if(key == 'Y') {
            //根据orderId返回对应的DiningTable对象，如果为null表示餐桌不存在
            DiningTable tableById = diningTableService.getTableById(orderId);
            if(tableById == null) {
                System.out.println("===该餐桌不存在===");
                return;
            }
            //如果餐桌不为空，表示已被预定或正在就餐
            if(!("空".equals(tableById.getState()))) {
                System.out.println("===该餐桌已被预定===");
                return;
            }
            System.out.println("预订人姓名：");
            String orderName = Utility.readString(50);
            System.out.println("预订人电话：");
            String orderTel = Utility.readString(50);
            if (diningTableService.orderTable(orderId, orderName, orderTel)) {
                System.out.println("====餐桌预订成功====");
            } else {
                System.out.println("====餐桌预订失败====");
            }
        } else {
            System.out.println("===放弃预订餐桌===");
        }
    }

    private void listMenu() {
        List<Menu> list = menuService.list();
        System.out.println("\n菜品编号\t\t菜品名\t\t类别\t\t价格");
        for (Menu menu : list) {
            System.out.println(menu);
        }
        System.out.println("====显示完毕====");
    }

    private void orderMenu() {
        System.out.println("====点餐服务====");
        System.out.println("请输入点餐的桌号(-1退出)：");
        int orderTableId = Utility.readInt();
        if(orderTableId == -1) {
            System.out.println("===放弃点餐===");
            return;
        }
        System.out.println("请输入点餐的菜品号(-1退出)：");
        int orderMenuId = Utility.readInt();
        if(orderMenuId == -1) {
            System.out.println("===放弃点餐===");
            return;
        }
        System.out.println("请输入点餐的数量(-1退出)：");
        int orderNums = Utility.readInt();
        if(orderNums == -1) {
            System.out.println("===放弃点餐===");
            return;
        }

        //验证餐桌号是否存在
        DiningTable tableById = diningTableService.getTableById(orderTableId);
        if (tableById == null) {
            System.out.println("====餐桌号不存在====");
            return;
        }

        //验证菜品编号是否存在
        Menu menuById = menuService.getMenuById(orderMenuId);
        if (menuById == null) {
            System.out.println("====菜品号不存在====");
            return;
        }

        //点餐
        if (billService.orderMenu(orderMenuId,orderNums,orderTableId)) {
            System.out.println("======点菜成功======");
        } else {
            System.out.println("======点菜失败======");
        }
    }

    public void listBill() {
//        List<Bill> list = billService.list();
//        System.out.println(String.format("\n%s\t%s\t%s\t%s\t\t%s\t%s\t\t\t\t\t\t%s",
//                "编号","菜品号","菜品量","金额","桌号","日期","状态"));
//        for (Bill bill : list) {
//            System.out.println(bill);
//        }
        List<MultiTableBean> list = billService.list2();
        System.out.println(String.format("\n%s\t%s\t%s\t\t%s\t\t%s\t%s\t\t\t\t\t\t%s",
                "编号","菜品名","菜品量","金额","桌号","日期","状态"));
        for (MultiTableBean bill : list) {
            System.out.println(bill);
        }
        System.out.println("======显示完毕======");
    }

    public void payBill() {
        System.out.println("======结账服务======");
        System.out.println("请输入结账的桌号(-1退出)：");
        int tableId = Utility.readInt();
        if(tableId == -1) {
            System.out.println("===取消结账===");
            return;
        }
        //验证餐桌是否存在
        DiningTable tableById = diningTableService.getTableById(tableId);
        if (tableById == null) {
            System.out.println("====餐桌号不存在====");
            return;
        }
        //验证是否有账单未结账（此处也可以验证餐桌状态是否为“就餐中”）
        if (!billService.checkBillByTable(tableId)) {
            System.out.println("====该餐桌没有未结账单====");
            return;
        }
        System.out.println("请输入结账方式(现金/支付宝/微信)，回车表示退出：");
        String payMode = Utility.readString(20, "");//如果回车，返回空字符串
        if(payMode == "") {
            System.out.println("===取消结账===");
            return;
        }
        char key = Utility.readConfirmSelection();
        if(key == 'Y') {
            if (billService.payBill(tableId,payMode)) {
                System.out.println("========完成结账========");
            } else{
                System.out.println("======结账失败======");
            }
        } else {
            System.out.println("===取消结账===");
        }
    }
}
