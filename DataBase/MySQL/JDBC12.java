package MySQL;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JDBC12 {

    //使用apache-DBUtils 工具类 + druid 完成对表的crud操作
    @Test
    public void testQueryMany() throws SQLException { //返回结果是多行的情况

        //1. 得到 连接 (druid)
        Connection connection = JDBC10.getConnection();
        //2. 使用 DBUtils 类和接口 , 先引入DBUtils 相关的jar(commons-dbutils-1.3), 加入到本Project
        //3. 创建 QueryRunner
        QueryRunner queryRunner = new QueryRunner();
        //4. 就可以执行相关的方法，返回ArrayList 结果集
        //String sql = "select * from actor where id >= ?";
        //   注意: sql 语句也可以查询部分列
        String sql = "select id, name from actor where id >= ?";
        // 解读
        //(1) query 方法就是执行sql 语句，得到resultset ---封装到 --> ArrayList 集合中
        //(2) 返回集合
        //(3) connection: 连接
        //(4) sql : 执行的sql语句
        //(5) new BeanListHandler<>(DBElementExample.class): 在将resultset -> DBElementExample 对象 -> 封装到 ArrayList
        //    底层使用反射机制 去获取DBElementExample 类的属性，然后进行封装
        // 实体类必须有无参构造器, 因为反射newInstance()需要调用缺省[默认]构造器;
        // 实体类必须有setter方法, 属性名可以和数据库里的字段不一样, 但setter方法的名字一定要一样. 比如数据库里有一个id字段, 反射时对应实体类里的setId()方法, 不一定非要有个id属性
        //(6) 1 就是给 sql 语句中的? 赋值，此处sql语句可以有多个问号，因为方法中此处是可变参数Object... params
        //(7) 底层得到的resultset ,会在query 关闭, 关闭PreparedStatment

        //这里的Actor类的每个属性名要跟查出来的列名一一对应，要不然会null
        //可变参数是为了迎合PreparedStatement进行语句校验防止SQL注入
        /**
         * 分析 queryRunner.query方法:
         * public <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
         *         PreparedStatement stmt = null;//定义PreparedStatement
         *         ResultSet rs = null;//接收返回的 ResultSet
         *         Object result = null;//返回ArrayList
         *
         *         try {
         *             stmt = this.prepareStatement(conn, sql);//创建PreparedStatement
         *             this.fillStatement(stmt, params);//对sql 进行 ? 赋值
         *             rs = this.wrap(stmt.executeQuery());//执行sql,返回resultset
         *             result = rsh.handle(rs);//返回的resultset --> arrayList[result] [使用到反射，对传入class对象处理]
         *         } catch (SQLException var33) {
         *             this.rethrow(var33, sql, params);
         *         } finally {
         *             try {
         *                 this.close(rs);//关闭resultset
         *             } finally {
         *                 this.close((Statement)stmt);//关闭preparedstatement对象
         *             }
         *         }
         *
         *         return result;
         *     }
         */
        List<DBElementExample> list =
                queryRunner.query(connection, sql, new BeanListHandler<>(DBElementExample.class), 1);
        System.out.println("输出集合的信息");
        for (DBElementExample actor : list) {
            System.out.print(actor);
        }


        //释放资源
        JDBC10.close(null, null, connection);

    }

    //演示 apache-dbutils + druid 完成 返回的结果是单行记录(单个对象)
    @Test
    public void testQuerySingle() throws SQLException {

        //1. 得到 连接 (druid)
        Connection connection = JDBC10.getConnection();
        //2. 使用 DBUtils 类和接口 , 先引入DBUtils 相关的jar , 加入到本Project
        //3. 创建 QueryRunner
        QueryRunner queryRunner = new QueryRunner();
        //4. 就可以执行相关的方法，返回单个对象
        String sql = "select * from actor where id = ?";
        // 老韩解读
        // 因为我们返回的单行记录<--->单个对象 , 使用的Hander 是 BeanHandler
        DBElementExample actor = queryRunner.query(connection, sql, new BeanHandler<>(DBElementExample.class), 10);
        System.out.println(actor);

        // 释放资源
        JDBC10.close(null, null, connection);

    }

    //演示apache-dbutils + druid 完成查询结果是单行单列-返回的就是object
    @Test
    public void testScalar() throws SQLException {

        //1. 得到 连接 (druid)
        Connection connection = JDBC10.getConnection();
        //2. 使用 DBUtils 类和接口 , 先引入DBUtils 相关的jar , 加入到本Project
        //3. 创建 QueryRunner
        QueryRunner queryRunner = new QueryRunner();

        //4. 就可以执行相关的方法，返回单行单列 , 返回的就是Object
        String sql = "select name from actor where id = ?";
        //老师解读： 因为返回的是一个对象, 使用的handler 就是 ScalarHandler
        Object obj = queryRunner.query(connection, sql, new ScalarHandler(), 4);
        System.out.println(obj);

        // 释放资源
        JDBC10.close(null, null, connection);
    }

    //演示apache-dbutils + druid 完成 dml (update, insert ,delete)
    @Test
    public void testDML() throws SQLException {

        //1. 得到 连接 (druid)
        Connection connection = JDBC10.getConnection();
        //2. 使用 DBUtils 类和接口 , 先引入DBUtils 相关的jar , 加入到本Project
        //3. 创建 QueryRunner
        QueryRunner queryRunner = new QueryRunner();

        //4. 这里组织sql 完成 update, insert delete
        //String sql = "update actor set name = ? where id = ?";
        //String sql = "insert into actor values(null, ?, ?, ?, ?)";
        String sql = "delete from actor where id = ?";

        //老韩解读
        //(1) 执行dml 操作是 queryRunner.update()
        //(2) 返回的值是受影响的行数 (affected: 受影响)
        //int affectedRow = queryRunner.update(connection, sql, "林青霞", "女", "1966-10-10", "116");
        int affectedRow = queryRunner.update(connection, sql, 1000 );
        System.out.println(affectedRow > 0 ? "执行成功" : "执行没有影响到表");

        // 释放资源
        JDBC10.close(null, null, connection);

    }
}

