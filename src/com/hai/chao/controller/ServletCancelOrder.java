package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
import com.hai.chao.service.OrderService;
import com.hai.chao.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: estore
 * @description: 取消订单
 * @author: xiaohai
 * @create: 2018-09-08 14:59
 **/
@WebServlet(name = "ServletCancelOrder", urlPatterns = {"/cancelOrder"})
public class ServletCancelOrder extends HttpServlet implements CommonContant{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderServiceImpl();
        try{
            //校验登陆，获取参数
            String oid = request.getParameter("oid");

            //调用service取消订单
            orderService.cancleOrder(oid);

            //查询所有订单，
            request.getRequestDispatcher("/servletOrderList").forward(request,response);
        }catch (Exception e){
            LOGGER.error(" 取消订单异常啦,----Exception----",e);
            response.sendRedirect(request.getContextPath()+"/500.jsp");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
