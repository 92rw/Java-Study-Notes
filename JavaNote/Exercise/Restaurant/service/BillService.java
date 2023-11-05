package Exercise.Restaurant.service;

import Exercise.Restaurant.dao.BillDAO;
import Exercise.Restaurant.dao.MultiTableDAO;
import Exercise.Restaurant.domain.Bill;
import Exercise.Restaurant.domain.MultiTableBean;

import java.util.List;
import java.util.UUID;

public class BillService {
    private BillDAO billDAO = new BillDAO();
    private MenuService menuService = new MenuService();
    private DiningTableService diningTableService = new DiningTableService();
    private MultiTableDAO multiTableDAO = new MultiTableDAO();

    //编写点餐的方法，如果成功，则返回true
    //1.生成账单，2.更新对应餐桌的状态
    public boolean orderMenu(int menuId, int nums, int diningTableId) {
        //生成账单号，使用UUID避免重复
        String billID = UUID.randomUUID().toString();

        //将账单生成到bill表
        int update = billDAO.update("INSERT INTO bill VALUES(null,?,?,?,?,?,now(),'未结账')",
                billID, menuId, nums, menuService.getMenuById(menuId).getPrice() * nums, diningTableId);
        if (update == 0) return false;

        //更新对应餐桌的状态
        return diningTableService.updateState(diningTableId,"就餐中");
    }

    //返回所有的账单，供主界面调用（主界面不再调用此方法）
//    public List<Bill> list() {
//        return billDAO.queryMulti("SELECT * FROM bill", Bill.class);//这个账单不显示菜品名称
//    }
    //利用新建的domain和DAO，在账单中显示菜品名
    public List<MultiTableBean> list2() {
        return multiTableDAO.queryMulti("SELECT bill.*,name FROM bill, menu WHERE menuId=menu.id", MultiTableBean .class);
    }


    //查看某个餐桌是否有未结账的账单
    public boolean checkBillByTable(int tableId){
        //select sum(money) from bill where diningTableId = ? and state = '未结账'  如果有未结账直接把金额报出来了
        //SELECT * FROM bill WHERE diningTableId=? AND state = '未结账' LIMIT 0, 1  只显示一条记录，付款时需要依次结清
        Bill bill = billDAO.querySingle("SELECT * FROM bill WHERE diningTableId=? AND state = '未结账' LIMIT 0, 1",
                Bill.class, tableId);
        return bill != null;
    }

    //完成结账（判断条件可二选一：①餐桌状态“就餐中”②餐桌存在且存在未结账的账单）
    public boolean payBill(int tableId, String payMode) {
        //修改Bill表
        int update = billDAO.update("UPDATE bill SET state=? WHERE diningTableId=? AND state='未结账'", payMode, tableId);
        if (update == 0) return false;
        //修改diningTable表
        //注意：不要直接在此处操作，而应该调用DiningTableService的方法
        if (!diningTableService.setFree(tableId, "空")) return false;
        return true;
    }
}
