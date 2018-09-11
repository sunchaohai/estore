package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
import com.hai.chao.domain.Cart;
import com.hai.chao.domain.User;
import com.hai.chao.service.CartService;
import com.hai.chao.service.impl.CartServiceImpl;
import org.apache.commons.lang3.StringUtils;

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
 * @create: 2018-09-04 11:51
 **/
@WebServlet(name = "ServletCartList", urlPatterns = {"/servletCartList"})
public class ServletCartList extends HttpServlet implements CommonContant {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            //校验登陆:拦截器已做

            //获取参数,校验,获取uid
            User loginUser = (User) request.getSession().getAttribute("loginUser");
            if(loginUser == null){
                //回首页
                response.sendRedirect(request.getContextPath());
            }
            int uid = loginUser.getId();
            //调用service查询购物车表list
            CartService cartService = new CartServiceImpl();
            List<Cart> carts = cartService.selectCartListByUid(uid);
            //返回
            request.getSession().setAttribute("carts",carts);
            request.getRequestDispatcher("/cart.jsp").forward(request,response);
        }catch (Exception e){
            LOGGER.error("查询购物车列表异常,----Excepton-----",e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
