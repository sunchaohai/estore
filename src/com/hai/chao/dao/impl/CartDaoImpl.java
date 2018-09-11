package com.hai.chao.dao.impl;

import com.hai.chao.common.CommonContant;
import com.hai.chao.dao.CartDao;
import com.hai.chao.domain.Cart;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @program: estore
 * @description: 购物车相关操作dao
 * @author: xiaohai
 * @create: 2018-09-01 23:04
 **/
public class CartDaoImpl implements CartDao,CommonContant {

    @Override
    public Cart selectCartByUidAndGid(int uid, Integer gid) throws SQLException {
        String sql = "select * from cart where uid = ? and gid = ?";

        Cart cart = queryRunner.query(sql, new BeanHandler<>(Cart.class), Integer.valueOf(uid), gid);
        return cart;
    }

    @Override
    public int insert(Cart cart) throws SQLException {
        String sql = "insert into cart() values(?,?,?)";

        return queryRunner.update(sql, cart.getUid(), cart.getGid(), cart.getBuynum());
    }

    @Override
    public int updateBuynumByPrimary(Cart cart) throws SQLException {
        String sql = "update cart set buynum = ? where uid = ? and gid = ?";

        return queryRunner.update(sql, cart.getBuynum(), cart.getUid(), cart.getGid());
    }

    @Override
    public List<Cart> selectCartListByUid(int uid) throws SQLException {
        String sql = "select * from cart where uid = ?";

        List<Cart> cart = queryRunner.query(sql, new BeanListHandler<>(Cart.class), uid);
        return cart;
    }

    @Override
    public int updateCartNumByUidAndGid(int uid, int gid,int buynum) throws SQLException {
        String sql = "update cart set buynum = ? where uid = ? and gid = ?";

        return queryRunner.update(sql, buynum, uid, gid);
    }

    @Override
    public int deleteCartByUidAndGid(int uid, int gid) throws SQLException {
        String sql = "delete from cart where uid = ? and gid = ?";

        return queryRunner.update(sql,uid,gid);
    }

    @Override
    public void clear(Connection conn, int uid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();

        String sql = "delete from cart where uid = ?";

        queryRunner.update(conn,sql,uid);
    }
}
