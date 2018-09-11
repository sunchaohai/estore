package com.hai.chao.dao;

import com.hai.chao.domain.Cart;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CartDao {
    Cart selectCartByUidAndGid(int uid, Integer gid) throws SQLException;

    int insert(Cart cart) throws SQLException;

    int updateBuynumByPrimary(Cart cart) throws SQLException;

    List<Cart> selectCartListByUid(int uid) throws SQLException;

    int updateCartNumByUidAndGid(int uid, int gid,int buynum) throws SQLException;

    int deleteCartByUidAndGid(int uid, int gid) throws SQLException;

    void clear(Connection conn, int uid) throws SQLException;
}
