package com.hai.chao.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DbUtils工具类
 */
public class DbUtil {
    /**
     * 实例化连接池
     */
    private static final ComboPooledDataSource CPDS = new ComboPooledDataSource("hai");

    public static ComboPooledDataSource getDataSource(){
        return CPDS;
    }
   /** 
   * @Description: 获得连接 
   * @Param: [] 
   * @return: java.sql.Connection 
   * @Author: xiaohai
   * @Date: 2018/8/16 
   */ 
    public static Connection getConnection() throws SQLException, PropertyVetoException, IOException {
        return CPDS.getConnection();
    }

    /** 
    * @Description: 关闭资源
    * @Param: [conn, st, rs] 
    * @return: void 
    * @Author: xiaohai
    * @Date: 2018/8/16 
    */ 
    public static void close(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        close(conn, st);

    }

    public static void close(Connection conn, Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
