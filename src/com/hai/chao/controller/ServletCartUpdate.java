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
 * @create: 2018-09-04 14:56
 **/
@WebServlet(name = "ServletCartUpdate", urlPatterns = {"/updateCartNum"})
public class ServletCartUpdate extends HttpServlet implements CommonContant{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try{
           //获取参数，校验
           User loginUser = (User) request.getSession().getAttribute("loginUser");
           if(loginUser == null){
               //回首页
               response.sendRedirect(request.getContextPath());
           }
           String gid = request.getParameter("gid");
           String buynum = request.getParameter("buynum");
           if(StringUtils.isAnyEmpty(gid,buynum)){
               request.setAttribute("msg","商品id和数量不能为空");
               request.getRequestDispatcher("/cart.jsp").forward(request,response);
           }
           int uid = loginUser.getId();

           //调用service保存
           CartService cartService = new CartServiceImpl();
           int count = cartService.updateCartNumByUidAndGid(uid,Integer.valueOf(gid).intValue(),Integer.valueOf(buynum).intValue());
           //转发servletCartList
           if(count == 1){
               request.getRequestDispatcher("/servletCartList").forward(request,response);
           }else {
               response.sendRedirect(request.getContextPath()+"/500.jsp");
           }
       }catch (Exception e){
           LOGGER.error("修改购物车商品数量失败,---Exception---",e);
           response.sendRedirect(request.getContextPath()+"/500.jsp");
       }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
