package MySQL;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**

 * 这是一个工具类，完成 mysql的连接和关闭资源
 */
public class JDBC06 {
    //定义相关的属性(4个), 因为只需要一份，因此，我们做成static
    private static String user; //用户名
    private static String password; //密码
    private static String url; //url
    private static String driver; //驱动名

    //在static代码块去初始化
    static {
        //现在是在一个类中，不是在方法中，不能直接在类名旁写throws 异常
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src\\mysql.properties"));
            //读取相关的属性值
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            driver = properties.getProperty("driver");
        } catch (IOException e) {
            //在实际开发中，我们可以这样处理
            //1. 将编译异常转成 运行异常
            //2. 调用者，可以选择捕获该异常，也可以选择默认处理该异常，比较方便（不需要try-catch）
            //3.运行时异常可以不处理，因为有默认的处理。编译时异常必须处理，要么捕获要么抛出。
            // 这里抛出运行时异常给调用该工具类的方法，方法就可以不用捕获异常了
            //这是因为JDBCUtils时被调用者 如果在这里输出编译异常信息 那么调用者获得的异常信息就少了 如果用运行时异常 调用者想看就捕捉这个异常 不想看就抛出（就是异常信息的决定权交给了调用者）
            throw new RuntimeException(e);

        }
    }

    //连接数据库, 返回Connection
    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            //1. 将编译异常转成 运行异常
            //2. 调用者，可以选择捕获该异常，也可以选择默认处理该异常，比较方便.
            throw new RuntimeException(e);
        }
    }

    //关闭相关资源
    //其他方法：直接用它们父类AutoCloseable...asc 采用增强for 循环关闭。
    // 凡是close()方法的都实现了AutoCloseble接口，所以可以只用一个方法和一个可变参数实现。
    /*
        1. ResultSet 结果集
        2. Statement 或者 PreparedStatement（直接关闭父接口Statement，利用向下转型）
        3. Connection
        4. 如果需要关闭资源，就传入对象，否则传入 null
     */
    public static void close(ResultSet set, Statement statement, Connection connection) {

        //判断是否为null
        try {
            if (set != null) {
                set.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            //将编译异常转成运行异常抛出
            throw new RuntimeException(e);
        }

    }

}
