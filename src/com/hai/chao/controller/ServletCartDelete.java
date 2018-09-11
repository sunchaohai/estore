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
 * @create: 2018-09-04 16:03
 **/
@WebServlet(name = "ServletCartDelete", urlPatterns = {"/servletCartDelete"})
public class ServletCartDelete extends HttpServlet implements CommonContant{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try {
           //校验登陆,获取uid,gid
           User user = (User) request.getSession().getAttribute("loginUser");
           if(user == null){
               response.sendRedirect(request.getContextPath());
           }
           int uid = user.getId();
           String gid = request.getParameter("gid");
           if(StringUtils.isEmpty(gid)){
               request.setAttribute("msg","参数gid不能为空");
           }
           //调用service删除

           CartService cartService = new CartServiceImpl();
           int count = cartService.deleteCartByUidAndGid(uid,Integer.valueOf(gid).intValue());

           //转发到cartList
           if(count == 1){
               request.getRequestDispatcher("/servletCartList").forward(request,response);
           }else{
               response.sendRedirect(request.getContextPath()+"/500.jsp");
           }
       }catch (Exception e){
           LOGGER.error("删除购物车异常,---Exception--",e);
           response.sendRedirect(request.getContextPath()+"/500.jsp");
       }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
