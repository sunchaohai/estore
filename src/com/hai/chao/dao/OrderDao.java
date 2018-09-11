package com.hai.chao.dao;

import com.hai.chao.domain.Order;
import com.hai.chao.domain.OrderItems;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    int saveOrderItem(Connection conn, List<OrderItems> orderItems) throws SQLException;

    int saveToOrder(Connection conn, Order order) throws SQLException;

    List<Order> selectOrdersByUid(int uid) throws SQLException;

    Order selectOrderByPrimary(String oid) throws SQLException;

    List<OrderItems> selectOrderDetailByPrimary(String oid) throws SQLException;

    void deleteOrderItemsByOrderId(String oid) throws SQLException;

    void deleteOrderByPrimary(String oid) throws SQLException;

    List<Order> selectAllNoPaymentOrders() throws SQLException;

    void updateOrderStatusByPrimary(Order order) throws SQLException;
}
