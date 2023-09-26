package com.javaweb.ajax.service;

import com.javaweb.ajax.dao.UserDAO;
import com.javaweb.ajax.entity.User;

/**
 * 提供业务方法，比如 getUserByName 方法，根据用户名返回对应的User对象
 */
public class UserService {

    //引入UserDAO属性，方便操作数据库
    //因为这里userDAO不是静态属性，因此调用该属性的方法都不能是静态方法
    private UserDAO userDAO = new UserDAO();

    public User getUserByName(String username) {
        User user = userDAO.querySingle("SELECT * FROM `user` WHERE username = ?", User.class, username);
        return user;
    }
}
