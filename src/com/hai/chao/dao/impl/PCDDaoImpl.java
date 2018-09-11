package com.hai.chao.dao.impl;

import com.hai.chao.common.CommonContant;
import com.hai.chao.dao.PCDDao;
import com.hai.chao.domain.PCD;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: estore
 * @description: 省市区相关操作Dao
 * @author: xiaohai
 * @create: 2018-09-04 17:17
 **/
public class PCDDaoImpl implements PCDDao,CommonContant {
    @Override
    public List<PCD> selectProvinceByPid(int id) throws SQLException {
        String sql = "SELECT * from province_city_district where pid = ?";


        return queryRunner.query(sql,new BeanListHandler<>(PCD.class),id);
    }
}
