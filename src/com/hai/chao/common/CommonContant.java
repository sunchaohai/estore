package com.hai.chao.common;

import com.hai.chao.utils.DbUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: estore
 * @description: 公共常量
 * @author: xiaohai
 * @create: 2018-08-31 11:21
 **/
public interface CommonContant {
    Logger LOGGER = LoggerFactory.getLogger(CommonContant.class);

    QueryRunner queryRunner = new QueryRunner(DbUtil.getDataSource());

    /**
     * 需要被记住的路径key，如果值存在，在登陆时不进入首页，进入该路径
     */

    String URLKEY = "remUrl";
}
