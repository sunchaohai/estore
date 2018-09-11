package com.hai.chao.dao.impl;

import com.hai.chao.dao.UserDao;
import com.hai.chao.domain.User;
import com.hai.chao.utils.DbUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @program: estore
 * @description: UserDao实现类，用户相关操作
 * @author: xiaohai
 * @create: 2018-08-31 12:26
 **/
public class UserDaoImpl implements UserDao {
    @Override
    public int saveUse(User user) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DbUtil.getDataSource());

        String sql = "insert into user() values(null,?,?,?,?)";
        int updateCount = queryRunner.update(sql, user.getNickname(), user.getUsername(), user.getPassword(), user.getRole());

        return updateCount;
    }

    @Override
    public User selectUserByUsername(String username) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DbUtil.getDataSource());

        String sql = "select * from user where username = ?";
        BeanHandler<User> rsh = new BeanHandler<>(User.class);
        User user = queryRunner.query(sql, rsh,username);


        return user;
    }

    @Override
    public User selectUserByUsernameAndPassword(String username, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(DbUtil.getDataSource());

        String sql = "select * from user where username = ? and password = ?";
        BeanHandler<User> rsh = new BeanHandler<>(User.class);
        User user = queryRunner.query(sql, rsh,username,password);

        return user;
    }
}
