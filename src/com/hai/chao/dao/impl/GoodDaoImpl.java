package com.hai.chao.dao.impl;

import com.hai.chao.common.CommonContant;
import com.hai.chao.dao.GoodDao;
import com.hai.chao.domain.Good;
import com.hai.chao.utils.DbUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: estore
 * @description: 商品Dao
 * @author: xiaohai
 * @create: 2018-09-01 20:35
 **/
public class GoodDaoImpl implements GoodDao,CommonContant {
    @Override
    public List<Good> selectAllGoods() throws SQLException {
        String sql = "select * from goods";

        List<Good> list = queryRunner.query(sql, new BeanListHandler<>(Good.class));

        return list;
    }

    @Override
    public Good selectGoodByPrimaryId(String goodId) throws SQLException {
        String sql = "select * from goods where id = ?";

        Good good = queryRunner.query(sql, new BeanHandler<>(Good.class), goodId);

        return good;
    }

    @Override
    public void saveGood(Good good) throws SQLException {
        String sql = "insert into goods values(null,?,?,?,?,?,?,?)";

        queryRunner.update(sql,good.getName(),good.getMarketprice(),
                good.getEstoreprice(),good.getCategory(),good.getNum(),good.getImgurl(),good.getDescription());
    }
}
