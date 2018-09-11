package com.hai.chao.service;

import com.hai.chao.domain.Order;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface OrderService {

    int saveToOrder(Order order) throws SQLException, PropertyVetoException, IOException;

    List<Order> selectOrdersByUid(int uid) throws SQLException;

    Order selectOrderDetailByPrimary(String oid) throws SQLException;

    void cancleOrder(String oid);

    List<Order> selectAllNoPaymentOrders() throws SQLException;

    void updateOrderStatusByPrimary(Order order) throws SQLException;

    void scanOrderList() throws SQLException;

}
