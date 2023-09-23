package org.exercise.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtils {
    private static SqlSessionFactory sqlSessionFactory;
    //在静态代码块完成静态属性的初始化
    static {
        try {
            //指定配置文件（src/resource目录，在编译后即target/classes目录）
            String resource = "mybatis-config.xml";
            //此处使用的Resources类来自 org.apache.ibatis.io 包
            InputStream resourceAsStream = Resources.getResourceAsStream(resource);
            //这里获得的是 org.apache.ibatis.session.defaults.DefaultSqlSessionFactory 对象
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //编写方法，返回SqlSession对象
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
