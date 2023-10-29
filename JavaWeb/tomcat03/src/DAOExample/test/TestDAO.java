package DataBase.DAOExample.test;

import DataBase.DAOExample.dao.ActorDAO;
import DataBase.DAOExample.domain.DBElementExample;

import java.util.List;

public class TestDAO {
    //测试ActorDAO对数据库actor表的CRUD操作
    //查询出来都是null得 看下一、检查实体类是否有写无参构造 二、检查实体类是否有setter方法 三、实体类的属性名称是否与数据库的字段名称一致
    public void testActorDAO() {
        ActorDAO actorDAO = new ActorDAO();
        //1. 查询
        List<DBElementExample> actors = actorDAO.queryMulti("select * from actor where id >= ?", DBElementExample.class, 1);
        System.out.println("===查询结果===");
        for (DBElementExample actor : actors) {
            System.out.println(actor);
        }

        //2. 查询单行记录
        DBElementExample actor = actorDAO.querySingle("select * from actor where id = ?", DBElementExample.class, 6);
        System.out.println("====查询单行结果====");
        System.out.println(actor);

        //3. 查询单行单列
        Object o = actorDAO.queryScalar("select name from actor where id = ?", 6);
        System.out.println("====查询单行单列值===");
        System.out.println(o);

        //4. dml操作  insert ,update, delete
        int update = actorDAO.update("insert into actor values(null, ?, ?, ?, ?)", "张无忌", "男", "2000-11-11", "999");

        System.out.println(update > 0 ? "执行成功" : "执行没有影响表");




    }
}
