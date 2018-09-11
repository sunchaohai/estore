package com.hai.chao.service.impl;

import com.hai.chao.common.CommonContant;
import com.hai.chao.dao.UserDao;
import com.hai.chao.dao.impl.UserDaoImpl;
import com.hai.chao.domain.User;
import com.hai.chao.service.UserService;
import com.hai.chao.utils.MD5Utils;

import java.sql.SQLException;

/**
 * @program: estore
 * @description: 用来处理有关用户操作的具体逻辑
 * @author: xiaohai
 * @create: 2018-08-31 11:34
 **/
public class UserServiceImpl implements UserService,CommonContant {

   /**
    * 保存用户
    * -2：用户已存在
    * -1：保存异常
    * 1：保存成功
    * @Param: [user]
    * @return: int
    */
    @Override
    public int saveUser(User user) {
        try{
            //根据用户名查询用户是否存在
            User u = this.selectUserByUsername(user.getUsername());
            if(u!=null){
                return -2;
            }
            UserDao userDao = new UserDaoImpl();

            //对密码进行md5加密
            String password = MD5Utils.getMD5(user.getPassword());
            user.setPassword(password);
            user.setRole("管理员");

            return userDao.saveUse(user);
        }catch (Exception e){
            LOGGER.error("保存用户异常,-----Exception------",e);
            return -1;
        }
    }

    @Override
    public User selectUserByUsername(String username) throws SQLException {
        UserDao userDao = new UserDaoImpl();
        return userDao.selectUserByUsername(username);
    }

    @Override
    public User selectUserByUsernameAndPassword(String username, String password) throws SQLException {
        UserDao userDao = new UserDaoImpl();
        return userDao.selectUserByUsernameAndPassword(username,password);
    }
}
