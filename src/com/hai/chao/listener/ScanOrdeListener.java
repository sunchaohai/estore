package com.hai.chao.listener;

import com.hai.chao.common.CommonContant;
import com.hai.chao.domain.Order;
import com.hai.chao.service.OrderService;
import com.hai.chao.service.impl.OrderServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @program: estore
 * @description: 定单扫描监听器
 * @author: xiaohai
 * @create: 2018-09-10 19:12
 *
 * 如果订单创建两小时，还未付款，则设置为订单过期
 **/
public class ScanOrdeListener implements ServletContextListener,CommonContant {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                OrderService orderService = new OrderServiceImpl();
                //查询所有未待付款订单
                try {
                    orderService.scanOrderList();
                } catch (SQLException e) {
                    LOGGER.error("订单扫描监听器异常啦，-----Exception----",e);
                }

            }
        },0,1000*60*60*2);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
