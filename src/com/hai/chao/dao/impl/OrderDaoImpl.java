package com.hai.chao.dao.impl;

import com.hai.chao.common.CommonContant;
import com.hai.chao.dao.OrderDao;
import com.hai.chao.domain.Order;
import com.hai.chao.domain.OrderItems;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @program: estore
 * @description: 订单相关操作dao实现类
 * @author: xiaohai
 * @create: 2018-09-07 14:32
 **/
public class OrderDaoImpl implements OrderDao,CommonContant{

    @Override
    public int saveToOrder(Connection conn, Order order) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "insert into orders values(?,?,?,?,?,?)";

        return queryRunner.update(conn,sql,order.getId(),order.getUid(),order.getTotalprice(),order.getAddress(),order.getStatus(),order.getCreatetime());
    }

    @Override
    public List<Order> selectOrdersByUid(int uid) throws SQLException {
        String sql = "select * from orders where uid = ?";
        return queryRunner.query(sql,uid,new BeanListHandler<>(Order.class));
    }

    @Override
    public int saveOrderItem(Connection conn, List<OrderItems> orderItems) throws SQLException {
        if(orderItems == null || orderItems.size() == 0){
            return 0;
        }
        QueryRunner queryRunner = new QueryRunner();
        String sql = "insert into orderitems values(?,?,?)";
        for(OrderItems items : orderItems){
            queryRunner.update(conn,sql,items.getOid(),items.getGid(),items.getBuynum());
        }

        return 1;
    }
    @Override
    public Order selectOrderByPrimary(String oid) throws SQLException {
        String sql = "select * from orders where id = ?";

        return queryRunner.query(sql,new BeanHandler<>(Order.class),oid);
    }

    @Override
    public List<OrderItems> selectOrderDetailByPrimary(String oid) throws SQLException {
        String sql = "select * from orderitems where oid = ?";

        return queryRunner.query(sql,new BeanListHandler<>(OrderItems.class),oid);
    }

    @Override
    public void deleteOrderItemsByOrderId(String oid) throws SQLException {
        String sql = "delete from orderitems where oid = ?";

        queryRunner.update(sql,oid);
    }

    @Override
    public void deleteOrderByPrimary(String oid) throws SQLException {
        String sql = "delete from orders where id = ?";

        queryRunner.update(sql,oid);
    }

    @Override
    public List<Order> selectAllNoPaymentOrders() throws SQLException {
        String sql = "select * from orders where status = ?";
        return queryRunner.query(sql,new BeanListHandler<>(Order.class),1);
    }

    @Override
    public void updateOrderStatusByPrimary(Order order) throws SQLException {
        String sql = "update orders set status = ? where id = ?";

        queryRunner.update(sql,order.getStatus(),order.getId());
    }

}
