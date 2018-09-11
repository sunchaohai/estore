package com.hai.chao.dao;

import com.hai.chao.domain.User;

import java.sql.SQLException;

/**
 * @program: estore
 * @description: 操作数据库层
 * @author: xiaohai
 * @create: 2018-08-31 11:39
 **/
public interface UserDao {


    int saveUse(User user) throws SQLException;

    User selectUserByUsername(String username) throws SQLException;

    User selectUserByUsernameAndPassword(String username, String password) throws SQLException;
}
