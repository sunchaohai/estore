package com.hai.chao.dao;

import com.hai.chao.domain.Good;

import java.sql.SQLException;
import java.util.List;

public interface GoodDao {
    List<Good> selectAllGoods() throws SQLException;

    Good selectGoodByPrimaryId(String goodId) throws SQLException;

    void saveGood(Good good) throws SQLException;
}
