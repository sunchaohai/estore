package com.hai.chao.service.impl;

import com.hai.chao.dao.GoodDao;
import com.hai.chao.dao.impl.GoodDaoImpl;
import com.hai.chao.domain.Good;
import com.hai.chao.service.GoodService;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: estore
 * @description: 商品相关操作service
 * @author: xiaohai
 * @create: 2018-08-31 18:50
 **/
public class GoodServiceImpl implements GoodService {
    @Override
    public List<Good> selectAllGoods() throws SQLException {
        GoodDao goodDao = new GoodDaoImpl();
        return goodDao.selectAllGoods();
    }

    @Override
    public Good selectGoodByPrimaryId(String goodId) throws SQLException {
        GoodDao goodDao = new GoodDaoImpl();

        return goodDao.selectGoodByPrimaryId(goodId);
    }

    @Override
    public void saveGood(Good good) throws SQLException {
        GoodDao dao = new GoodDaoImpl();

        dao.saveGood(good);
    }
}
