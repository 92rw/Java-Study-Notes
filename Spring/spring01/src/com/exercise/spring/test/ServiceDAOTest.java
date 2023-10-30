package com.exercise.spring.test;

import com.exercise.spring.dao.ServiceDAO;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class ServiceDAOTest {
    @Test
    public void testDataSourceByJdbcTemplate() throws SQLException {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("JdbcTemplate_ioc.xml");
        ServiceDAO serviceDAO = ioc.getBean(ServiceDAO.class);
        Float price = serviceDAO.querypriceById(1);
        System.out.println("获取到 price：" +price);
    }
}