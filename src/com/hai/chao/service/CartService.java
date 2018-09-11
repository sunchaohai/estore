package com.hai.chao.service;

import com.hai.chao.domain.Cart;

import java.sql.SQLException;
import java.util.List;

public interface CartService {
    int addToCard(int id, Integer gid) throws SQLException;

    List<Cart> selectCartListByUid(int uid) throws SQLException;

    int updateCartNumByUidAndGid(int uid, int gid,int buynum) throws SQLException;

    int deleteCartByUidAndGid(int uid, int gid) throws SQLException;
}

