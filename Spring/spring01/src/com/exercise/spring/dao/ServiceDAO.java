package com.exercise.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Float querypriceById(Integer id) {
        String sql = "SELECT price FROM goods WHERE goods_id = ?";
        Float price = jdbcTemplate.queryForObject(sql, float.class, id);
        return price;
    }

    public void updateBalance(Integer user_id, Float money) {
        String sql = "UPDATE user_account SET money = money - ? WHERE user_id = ?";
        jdbcTemplate.update(sql, money, user_id);
    }

    public void updateAmount(Integer goods_id, int amount) {
        String sql = "UPDATE goods_amount SET goods_num = goods_num - ? WHERE goods_id = ?";
        jdbcTemplate.update(sql, amount, goods_id);
    }
}
