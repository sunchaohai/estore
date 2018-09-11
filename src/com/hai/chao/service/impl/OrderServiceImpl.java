package com.hai.chao.service.impl;

import com.hai.chao.common.CommonContant;
import com.hai.chao.dao.CartDao;
import com.hai.chao.dao.OrderDao;
import com.hai.chao.dao.impl.CartDaoImpl;
import com.hai.chao.dao.impl.OrderDaoImpl;
import com.hai.chao.domain.Good;
import com.hai.chao.domain.Order;
import com.hai.chao.domain.OrderItems;
import com.hai.chao.service.GoodService;
import com.hai.chao.service.OrderService;
import com.hai.chao.utils.DbUtil;
import org.apache.commons.collections.CollectionUtils;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @program: estore
 * @description: 订单相关操作service实现类
 * @author: xiaohai
 * @create: 2018-09-07 14:12
 **/
public class OrderServiceImpl implements OrderService,CommonContant {


    @Override
    public int saveToOrder(Order order) {
        Connection conn = null;
        OrderDao orderDao = new OrderDaoImpl();
        CartDao cartDao = new CartDaoImpl();
        try {
            //启动事物
            conn = DbUtil.getConnection();

            //开启事物
            conn.setAutoCommit(false);

            //添加订单
            int count = orderDao.saveToOrder(conn, order);
            //保存订单详情
            orderDao.saveOrderItem(conn, order.getOiList());

            //清空购物车
            cartDao.clear(conn, order.getUid());

            //提交事物
            conn.commit();
            return count;
        } catch (Exception e) {
            LOGGER.error("保存订单异常,----OrderServiceImpl saveToOrder()--Exception---", e);
            //异常回滚
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error("回滚异常,----OrderServiceImpl saveToOrder()--Exception---", e);
            }

            return 0;
        }

    }

    @Override
    public List<Order> selectOrdersByUid(int uid) throws SQLException {
        OrderDao orderDao = new OrderDaoImpl();

        return orderDao.selectOrdersByUid(uid);
    }

    @Override
    public Order selectOrderDetailByPrimary(String oid) throws SQLException {
        OrderDao orderDao = new OrderDaoImpl();
        //先查询订单表
        Order order = orderDao.selectOrderByPrimary(oid);

        //再查询订单明细，结果整合到order中返回
        List<OrderItems> list = orderDao.selectOrderDetailByPrimary(oid);
        if(CollectionUtils.isNotEmpty(list)){
            GoodService goodService = new GoodServiceImpl();
            for(OrderItems items : list){
                int gid = items.getGid();
                if(gid == 0 && Integer.valueOf(gid) == null){
                    continue;
                }
                Good good = goodService.selectGoodByPrimaryId(gid+"");
                items.setGood(good);
            }
        }

        order.setOiList(list);

        return order;
    }

    @Override
    public void cancleOrder(String oid) {
        Connection conn = null;
        OrderDao orderDao = new OrderDaoImpl();
        try{
            conn = DbUtil.getConnection();
            conn.setAutoCommit(false);

            //删除订单详情
            orderDao.deleteOrderItemsByOrderId(oid);

            //删除订单
            orderDao.deleteOrderByPrimary(oid);

            //提交
            conn.commit();
        }catch (SQLException e){
            //异常回滚
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error(" 取消订单OrderServiceImpl cancleOrder()异常啦,----Exception----",e);
            }
        } catch (PropertyVetoException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error(" 取消订单OrderServiceImpl cancleOrder()异常啦,----Exception----",e);
            }
        } catch (IOException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error(" 取消订单OrderServiceImpl cancleOrder()异常啦,----Exception----",e);
            }
        }


    }

    @Override
    public List<Order> selectAllNoPaymentOrders() throws SQLException {
        OrderDao orderDao = new OrderDaoImpl();

        return orderDao.selectAllNoPaymentOrders();
    }

    @Override
    public void updateOrderStatusByPrimary(Order order) throws SQLException {
        OrderDao orderDao = new OrderDaoImpl();

        orderDao.updateOrderStatusByPrimary(order);
    }

    @Override
    public void scanOrderList() throws SQLException {
        List<Order> orders = this.selectAllNoPaymentOrders();
        if(orders != null && orders.size() > 0){
            for(Order order : orders){

                if(order.getCreatetime().getTime()< System.currentTimeMillis()-2*60*60*1000){
                    order.setStatus(3);
                    this.updateOrderStatusByPrimary(order);
                }
            }
        }
    }
}
