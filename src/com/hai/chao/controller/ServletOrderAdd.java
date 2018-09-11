package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
import com.hai.chao.common.OrderStatusEnum;
import com.hai.chao.domain.Cart;
import com.hai.chao.domain.Order;
import com.hai.chao.domain.OrderItems;
import com.hai.chao.domain.User;
import com.hai.chao.service.OrderService;
import com.hai.chao.service.impl.OrderServiceImpl;
import com.hai.chao.utils.UUIDUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @program: estore
 * @description: ${description}
 * @author: xiaohai
 * @create: 2018-09-04 16:24
 **/
@WebServlet(name = "ServletOrderAdd", urlPatterns = {"/servletOrderAdd"})
public class ServletOrderAdd extends HttpServlet implements CommonContant{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            //校验登陆,获取参数
            User user = (User) request.getSession().getAttribute("loginUser");
            if(user == null){
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
            String ph = request.getParameter("province");
            String ch = request.getParameter("city");
            String dh = request.getParameter("district");
            String detailAddress = request.getParameter("detailAddress");
            String zipcode = request.getParameter("zipcode");
            String name = request.getParameter("name");
            String telephone = request.getParameter("telephone");
            String totalPrice = request.getParameter("totalPrice");

            //订单号
            String oid = UUIDUtils.getUUID();

            String address = ph + ch + dh + detailAddress;

            OrderService orderService = new OrderServiceImpl();
            Order order = new Order();
            order.setId(oid);
            order.setAddress(address);
            order.setTotalprice(Double.valueOf(totalPrice));
            order.setUid(user.getId());
            order.setCreatetime(new Date());
            order.setStatus(OrderStatusEnum.ORDER_S1.getCode());

            //获取购物车列表，保存到订单明细表中
            List<Cart> list = (List<Cart>) request.getSession().getAttribute("carts");
            List<OrderItems> oList = new ArrayList<>();
            for(Cart cart : list){
                OrderItems orderItems = new OrderItems();
                int gid = cart.getGid();
                int buynum = cart.getBuynum();
                orderItems.setGid(gid);
                orderItems.setBuynum(buynum);
                orderItems.setOid(oid);

                oList.add(orderItems);
            }
            order.setOiList(oList);

            //调用service保存
            int count = orderService.saveToOrder(order);
            if(count == 1){
                //调用查看全部订单的servlet查看结果
                response.sendRedirect(request.getContextPath()+"/orders.jsp");

            }else {
                LOGGER.error("提交订单失败");
                response.sendRedirect(request.getContextPath()+"/500.jsp");
            }

            //转发到order.jsp
        }catch (Exception e){
            LOGGER.error("提交订单异常啦，---Exception---",e);
            response.sendRedirect(request.getContextPath()+"/500.jsp");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
