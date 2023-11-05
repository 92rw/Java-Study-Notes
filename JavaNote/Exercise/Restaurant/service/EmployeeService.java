package Exercise.Restaurant.service;

import Exercise.Restaurant.dao.EmployeeDAO;
import Exercise.Restaurant.domain.Employee;

/**
 * 调用EmployeeDAO对象，完成对数据库的操作
 */
public class EmployeeService {
    //定义EmployeeDAO属性
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    //根据empId和pwd，到数据库查询Employee对象；如果查询不到则返回null
    public Employee getEmployeeByIdAndPwd(String empId, String pwd) {
        Employee employee = employeeDAO.querySingle(
       "SELECT * FROM employee WHERE empId = ? AND pwd = md5(?)", Employee.class, empId, pwd);
        return employee;
    }
}
