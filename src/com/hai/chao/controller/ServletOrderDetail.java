package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
import com.hai.chao.domain.Order;
import com.hai.chao.service.OrderService;
import com.hai.chao.service.impl.OrderServiceImpl;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: estore
 * @description: ${description}
 * @author: xiaohai
 * @create: 2018-09-08 11:53
 **/
@WebServlet(name = "ServletOrderDetail", urlPatterns = {"/servletOrderDetail"})
public class ServletOrderDetail extends HttpServlet implements CommonContant {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try{
           //校验登陆，过滤器已做，获取参数oid
           String oid = request.getParameter("oid");
           if(StringUtils.isEmpty(oid)){
               response.sendRedirect(request.getContextPath());
           }

           //根据oid查询数据
           OrderService orderService = new OrderServiceImpl();
           Order order = orderService.selectOrderDetailByPrimary(oid);

           //转发到orders_detail.jsp页面
           request.setAttribute("order",order);
           request.getRequestDispatcher("/orders_detail.jsp").forward(request,response);
       }catch (Exception e){
           LOGGER.error("查看订单详情异常，----Exception---",e);
           response.sendRedirect(request.getContextPath()+"/500.jsp");
       }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
