package com.exercise.spring.service;

import com.exercise.spring.dao.ServiceDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.annotation.Repeatable;

@Service
public class DeclarativeService {
    @Resource
    private ServiceDAO serviceDAO;

    public void buyGoods(int userId, int goodsId, int amount) {
        System.out.printf("用户id：%d，商品编号：%d，购买数量：%d",userId,goodsId,amount);
        Float price = serviceDAO.querypriceById(goodsId);
        serviceDAO.updateBalance(userId, price * amount);
        serviceDAO.updateAmount(goodsId, amount);
        System.out.println("数据更新成功，完成扣款" + price*amount + "元");
    }

    @Transactional(isolation = Isolation.DEFAULT)
    public void buyGoodsByTx(int userId, int goodsId, int amount) {
        System.out.printf("用户id：%d，商品编号：%d，购买数量：%d",userId,goodsId,amount);
        Float price = serviceDAO.querypriceById(goodsId);
        serviceDAO.updateBalance(userId, price * amount);
        serviceDAO.updateAmount(goodsId, amount);
        System.out.println("数据更新成功，完成扣款" + price*amount + "元");
    }
}
