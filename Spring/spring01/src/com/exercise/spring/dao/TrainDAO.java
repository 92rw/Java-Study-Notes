package com.exercise.spring.dao;

import com.exercise.spring.bean.Train;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class TrainDAO {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void save(Train train) {
        String sql = "INSERT INTO train VALUES(?,?,?)";
        int affected = jdbcTemplate.update(sql, train.getTrainNo(), train.getOrigin(), train.getTerminal());
        System.out.println("数据保存成功，受影响行数" + affected);
    }
}
