package Exercise.Restaurant.service;

import Exercise.Restaurant.dao.DiningTableDAO;
import Exercise.Restaurant.domain.DiningTable;

import java.util.List;

public class DiningTableService {//业务层
    //定义一个DiningTableDAO属性
    private DiningTableDAO diningTableDAO = new DiningTableDAO();

    //返回所有餐桌的信息
    public List<DiningTable> list() {
        return diningTableDAO.queryMulti("SELECT id, state FROM diningTable", DiningTable.class);
    }

    //显示餐桌状态
    public void listDiningTable() {
        List<DiningTable> list = list();
        System.out.println("\n餐桌编号\t\t餐桌状态");
        for (DiningTable diningTable : list) {
            System.out.println(diningTable);
        }
    }

    //根据id，查询对应餐桌对象：如果返回null表示餐桌不存在
    //因为id是主键不能重复说，因此可通过id进行查找
    public DiningTable getTableById(int id) {
        return diningTableDAO.querySingle("SELECT * FROM diningTable WHERE id = ?", DiningTable.class, id);
    }

    //如果餐桌可以预定，调用方法更新其状态（包括预订人的名字和电话）
    public boolean orderTable(int id, String orderName, String orderTel) {
        int update = diningTableDAO.update("UPDATE diningTable SET state='已预订', orderName=?, orderTel=? WHERE id=?", orderName, orderTel, id);
        return update > 0;
    }
    //下订单后，更新餐桌状态
    public boolean updateState(int id, String state) {
        int update = diningTableDAO.update("UPDATE diningTable SET state=? WHERE id=?", state, id);
        return update > 0;
    }

    //将指定的餐桌设置为空闲状态
    public boolean setFree(int id, String state) {
        int update = diningTableDAO.update("UPDATE diningTable SET state=?,orderName='', orderTel='' WHERE id=?", state, id);
        return update > 0;
    }
}
