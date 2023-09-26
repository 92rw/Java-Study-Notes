package com.javaweb.ajax.dao;

import com.javaweb.ajax.entity.User;

//继承BasicDAO并指定泛型User
//这时可以使用BasicDAO中的所有方法
public class UserDAO extends BasicDAO<User> {
}
