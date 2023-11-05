package Exercise.Restaurant.service;

import Exercise.Restaurant.dao.MenuDAO;
import Exercise.Restaurant.domain.Menu;

import java.util.List;

public class MenuService {
    private MenuDAO menuDAO = new MenuDAO();

    //返回所有的菜品
    public List<Menu> list() {
        return menuDAO.queryMulti("SELECT * FROM menu", Menu.class);
    }
    //根据菜品编号获得价格
    public Menu getMenuById(int menuId) {
        return menuDAO.querySingle("SELECT * FROM menu WHERE id = ?", Menu.class, menuId);
    }
}
