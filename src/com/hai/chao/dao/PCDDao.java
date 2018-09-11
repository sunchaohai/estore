package com.hai.chao.dao;

import com.hai.chao.domain.PCD;

import java.sql.SQLException;
import java.util.List;

public interface PCDDao {
    List<PCD> selectProvinceByPid(int id) throws SQLException;
}
