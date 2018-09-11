package com.hai.chao.service;

import com.hai.chao.domain.User;

import java.sql.SQLException;

public interface UserService {
    int saveUser(User user);

    User selectUserByUsername(String username) throws SQLException;

    User selectUserByUsernameAndPassword(String usrename, String password) throws SQLException;
}
