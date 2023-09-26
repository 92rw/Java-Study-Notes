package com.javaweb.ajax.dao;

import com.javaweb.ajax.util.JdbcByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BasicDAO<T> {
    //因为接收数据库的对象可能是任意类型，因此类中定义泛型
    //因为类中的方法都在执行完毕后关闭了数据库连接，因此在TestDAO中不需要再写相关语句，只需要查询即可
    private QueryRunner qr = new QueryRunner();

    //开发通用的DML方法，针对任意的表
    public int update(String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = JdbcByDruid.getConnection();
            int update = qr.update(connection,sql,parameters);
            return update;
        } catch (SQLException e) {
            //只要没有其他对象调用该方法，就不用转成运行异常，直接捕获或抛出即可
            throw new RuntimeException(e);//将编译异常转为运行异常
        } finally {
            JdbcByDruid.close(null,null,connection);
        }
    }

    /**
     *返回多个对象（查询结果是多行），针对任意表
     * @param sql SQL语句，可以有?
     * @param clazz 通过反射创建一个类的Class对象，和数据库的表中列元素对应
     * @param parameters 传入?的具体值，可以是多个
     * @return 根据查询到的结果，返回对应的ArrayList集合
     */
    public List<T> queryMulti(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;
        try {
            connection = JdbcByDruid.getConnection();
            //设计模式：处理器模式
            return qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            //只要没有其他对象调用该方法，就不用转成运行异常，直接捕获或抛出即可
            throw new RuntimeException(e);//将编译异常转为运行异常
        } finally {
            JdbcByDruid.close(null, null, connection);
        }
    }

    //查询单行结果的通用方法
    public T querySingle(String sql, Class<T> clazz, Object... parameters){
        Connection connection = null;
        try {
            connection = JdbcByDruid.getConnection();
            //设计模式：处理器模式
            return qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            //只要没有其他对象调用该方法，就不用转成运行异常，直接捕获或抛出即可
            throw new RuntimeException(e);//将编译异常转为运行异常
        } finally {
            JdbcByDruid.close(null, null, connection);
        }
    }

    //查询单行单列结果
    public Object queryScalar(String sql, Object... parameters){
        Connection connection = null;
        try {
            connection = JdbcByDruid.getConnection();
            //设计模式：处理器模式
            return qr.query(connection, sql, new ScalarHandler(), parameters);
        } catch (SQLException e) {
            //只要没有其他对象调用该方法，就不用转成运行异常，直接捕获或抛出即可
            throw new RuntimeException(e);//将编译异常转为运行异常
        } finally {
            JdbcByDruid.close(null, null, connection);
        }
    }
}
