package com.hai.chao.service.impl;

import com.hai.chao.dao.PCDDao;
import com.hai.chao.dao.impl.PCDDaoImpl;
import com.hai.chao.domain.PCD;
import com.hai.chao.service.PCDService;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: estore
 * @description: 省市区操作Service
 * @author: xiaohai
 * @create: 2018-09-04 17:07
 **/
public class PCDServiceImpl implements PCDService {
    /**
     * 根据pid查询所有数据
     * @param id
     * @return
     */
    @Override
    public List<PCD> selectProvinceByPid(int id) throws SQLException {
        PCDDao pcdDao = new PCDDaoImpl();
        return pcdDao.selectProvinceByPid(id);
    }
}
