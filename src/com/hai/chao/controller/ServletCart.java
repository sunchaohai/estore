package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
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

/**
 * @program: estore
 * @description: ${description}
 * @author: xiaohai
 * @create: 2018-09-01 22:46
 **/
@WebServlet(name = "ServletCart", urlPatterns = {"/addToCard"})
public class ServletCart extends HttpServlet implements CommonContant {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取gid，校验
            String gid = request.getParameter("gid");
            if(StringUtils.isEmpty(gid)){
                request.setAttribute("msg","商品id不能为空");
                request.getRequestDispatcher("/goods_detail.jsp").forward(request,response);
            }

            //获取当前用户id,调service方法处理
            User loginUser = (User) request.getSession().getAttribute("loginUser");
            CartService cartService = new CartServiceImpl();
            int count = cartService.addToCard(loginUser.getId(),Integer.valueOf(gid));
            if(count == 1){
                response.sendRedirect(request.getContextPath()+"/buyorcart.jsp");
            }else{
                response.sendRedirect(request.getContextPath());
            }
        }catch (Exception e){
            LOGGER.error("加入购物车接口异常,------Exception------",e);
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
