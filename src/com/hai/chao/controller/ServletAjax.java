package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
import com.hai.chao.domain.User;
import com.hai.chao.service.UserService;
import com.hai.chao.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: estore
 * @description: 查询用户名是否已经存在
 * @author: xiaohai
 * @create: 2018-08-29 22:42
 **/
@WebServlet(name = "ServletAjax", urlPatterns = {"/servletAjax"})
public class ServletAjax extends HttpServlet implements CommonContant {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try {
           PrintWriter writer = response.getWriter();
           //获取参数，校验
           String username = request.getParameter("username");
           if(StringUtils.isEmpty(username)){
               writer.write("-1");
               return;
           }

           //调service方法查询
           UserService userService = new UserServiceImpl();
           User user = userService.selectUserByUsername(username);
           if(user == null){
               writer.write("0");
           }else {
               writer.write("-2");
           }

           //返回
       }catch (Exception e){
           LOGGER.error("根据用户名查询用户异常,------Exception-----",e);
           response.getWriter().write("-4");
       }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
