package com.hai.chao.service;

import com.hai.chao.domain.PCD;

import java.sql.SQLException;
import java.util.List;

public interface PCDService {
    List<PCD> selectProvinceByPid(int i) throws SQLException;
}
