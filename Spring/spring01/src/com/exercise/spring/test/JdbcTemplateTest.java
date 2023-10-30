package com.exercise.spring.test;

import com.exercise.spring.aop.xml.Calculate;
import com.exercise.spring.bean.Train;
import com.exercise.spring.dao.TrainDAO;
import com.exercise.spring.service.DeclarativeService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTemplateTest {
    @Test
    public void testDataSourceByJdbcTemplate() throws SQLException {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        DataSource dataSource = ioc.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        System.out.println("获取到connection：" +connection);
        connection.close();
    }

    @Test
    public void addDataByJdbcTemplate() throws SQLException{
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        JdbcTemplate jdbcTemplate = ioc.getBean(JdbcTemplate.class);

        //添加方式1：利用execute方法
        String sql1 = "INSERT INTO train VALUES(2539, '北京', '青岛')";
        jdbcTemplate.execute(sql1);
        System.out.println("字段1添加成功");

        //添加方式2：利用update犯法
        String sql2 = "INSERT INTO train VALUES(?, ?, ?)";
        int affected = jdbcTemplate.update(sql2, 8416, "青岛", "青岛西");
        System.out.println("字段2添加完成，受影响的记录数：" + affected);
    }

    @Test
    public void updateDataByJdbcTemplate() throws SQLException{
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        JdbcTemplate jdbcTemplate = ioc.getBean(JdbcTemplate.class);

        String sql = "UPDATE train SET terminal = ? WHERE id = ?";
        int affected = jdbcTemplate.update(sql, "莒县", 8416);
        System.out.println("字段修改完成，受影响的记录数：" + affected);
    }

    @Test
    public void addBatchDataByJdbcTemplate() throws SQLException{
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        JdbcTemplate jdbcTemplate = ioc.getBean(JdbcTemplate.class);

        //准备参数
        String sql = "INSERT INTO train VALUES(?, ?, ?)";
        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{5022, "青岛", "曹县"});
        batchArgs.add(new Object[]{5026, "青岛", "菏泽"});
        //调用方法
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        //返回结果是一个数组，每个元素对应上面的sql语句对表的影响记录数
        for (int anInt : ints) {
            System.out.println("受影响的记录数：" + anInt);
        }
        System.out.println("批量添加成功");
    }

    @Test
    public void queryMultiDataByJdbcTemplate() throws SQLException{
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        JdbcTemplate jdbcTemplate = ioc.getBean(JdbcTemplate.class);

        String sql = "SELECT id trainNo, origin, terminal FROM train WHERE id > ?";
        //使用RowMapper接口，封装返回的数据。
        //要求：1.查询的表字段需要和对象字段保持一致 2.传入class对象才能创建反射
        BeanPropertyRowMapper<Train> rowMapper = new BeanPropertyRowMapper<>(Train.class);

        //准备参数
        List<Train> trainList = jdbcTemplate.query(sql, rowMapper, 5000);
        for (Train train : trainList) {
            System.out.println(train);
        }
    }

    @Test
    public void queryDataByJdbcTemplate() throws SQLException{
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        JdbcTemplate jdbcTemplate = ioc.getBean(JdbcTemplate.class);

        String sql = "SELECT id trainNo, origin, terminal FROM train WHERE id = 2539";
        //使用RowMapper接口，封装返回的数据。
        //要求：1.查询的表字段需要和对象字段保持一致 2.传入class对象才能创建反射
        BeanPropertyRowMapper<Train> rowMapper = new BeanPropertyRowMapper<>(Train.class);

        //准备参数
        Train train = jdbcTemplate.queryForObject(sql, rowMapper);
        System.out.println(train);
    }

    @Test
    public void queryScalerJdbcTemplate() throws SQLException{
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        JdbcTemplate jdbcTemplate = ioc.getBean(JdbcTemplate.class);

        String sql = "SELECT id trainNo FROM train WHERE terminal = '莒县'";
        Integer integer = jdbcTemplate.queryForObject(sql, int.class);
        System.out.println("返回的车次：" + integer);
    }

    @Test
    public void addDataByNamedParameterJdbcTemplate() throws SQLException{
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = ioc.getBean(NamedParameterJdbcTemplate.class);

        //具名参数：要求按照规定的名字来设置参数，更加明确表的结构
        String sql = "INSERT INTO train VALUES(:id, :departure, :destination)";
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("id",8411);
        paraMap.put("departure","高密");
        paraMap.put("destination","蓝村");
        int affected = namedParameterJdbcTemplate.update(sql, paraMap);
        System.out.println("受影响行数：" + affected);
    }

    @Test
    public void addObjectByNamedParameterJdbcTemplate() throws SQLException{
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = ioc.getBean(NamedParameterJdbcTemplate.class);

        String sql = "INSERT INTO train VALUES(:trainNo, :origin, :terminal)";
        //传入具体的参数对象
        Train train = new Train(7102, "黄山", "南京");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(train);

        int affected = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        System.out.println("受影响行数：" + affected);
    }

    @Test
    public void trainDAOsave() throws SQLException{
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        TrainDAO trainDAO = ioc.getBean(TrainDAO.class);
        Train train = new Train(6063, "宝鸡", "广元");

        trainDAO.save(train);
        System.out.println("保存成功");
    }

    @Test
    public void buyGoods() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        DeclarativeService service = ioc.getBean(DeclarativeService.class);
        service.buyGoods(1,1,4);
    }
}
