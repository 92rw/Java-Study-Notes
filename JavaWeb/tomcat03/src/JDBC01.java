package DataBase;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 演示对数据库进行DML操作
 */
public class JDBC01 {
    public static void main(String[] args) throws SQLException {

        //前置工作： 在项目下创建一个文件夹比如 libs
        // 将 mysql.jar 拷贝到该目录下，点击 add to project ..加入到项目中

        //1. 注册驱动
        Driver driver = new Driver(); //创建driver对象

        //2. 得到连接
        // 说明：如果是MySQL8版本，需要com.mysql.cj.jdbc.Driver
        //(1) jdbc:mysql:// 规定好表示协议，通过jdbc的方式连接mysql
        //(2) localhost:3306 mysql所在的主机（可以是ip地址）和监听端口
        //(3) hsp_db02 连接到mysql DBMS 的哪个数据库
        //(4) mysql的连接本质就是前面学过的socket连接
        //如果有使用高版本MySQL的小伙伴，url后需要加上?serverTimezone=UTC&useSSL=false
        String url = "jdbc:mysql://localhost:3306/hsp_db02";
        //将 用户名和密码 封装到Properties 对象
        Properties properties = new Properties();
        //说明 user 和 password 是规定好，后面的值根据实际情况写
        properties.setProperty("user", "root");// 用户
        properties.setProperty("password", "hsp"); //此处密码仅做演示，以实际密码为准
        Connection connect = driver.connect(url, properties);

    /*首先在MySQL中执行创建表的语句：
    actor（--演员表
CREATE TABLE company (id INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(32) NOT NULL DEFAULT '',
state_owned CHAR（1）NOT NULL DEFAULT '否', establish_date DATETIME, contact_number VARCHAR(13));
     */
        //3. 执行sql
        //String sql = "insert into company values(null, '中国铁路总公司', '是', '2013-03-14', '12306')";
        //String sql = "update company set name= '国铁集团' where id = 1";
        String sql = "delete from company where id = 1";
        //statement 用于执行静态SQL语句并返回其生成的结果的对象
        Statement statement = connect.createStatement();
        int rows = statement.executeUpdate(sql); // 如果是 dml 语句，返回的就是影响行数

        System.out.println(rows > 0 ? "成功" : "失败");

        //4. 关闭连接资源
        statement.close();
        connect.close();

    }
}

