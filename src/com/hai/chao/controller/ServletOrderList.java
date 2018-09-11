package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
import com.hai.chao.domain.Order;
import com.hai.chao.domain.User;
import com.hai.chao.service.OrderService;
import com.hai.chao.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @program: estore
 * @description: ${description}
 * @author: xiaohai
 * @create: 2018-09-07 22:15
 **/
@WebServlet(name = "ServletOrderList", urlPatterns = {"/servletOrderList"})
public class ServletOrderList extends HttpServlet implements CommonContant {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderServiceImpl();
        try {
            //校验登陆,猎取uid
            User user = (User) request.getSession().getAttribute("loginUser");
            int uid = user.getId();

            //调用service查询数据
            List<Order> orderList = orderService.selectOrdersByUid(uid);

            //转发到order.js页面
            request.setAttribute("orderList",orderList);
            request.getRequestDispatcher("/orders.jsp").forward(request,response);

            return;
        }catch (Exception e){
            LOGGER.error("查询订单列表异常啦，---Exception---",e);
            response.sendRedirect(request.getContextPath()+"/500.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
