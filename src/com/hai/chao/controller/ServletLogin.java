package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
import com.hai.chao.domain.User;
import com.hai.chao.service.UserService;
import com.hai.chao.service.impl.UserServiceImpl;
import com.hai.chao.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @program: estore
 * @description: ${description}
 * @author: xiaohai
 * @create: 2018-08-31 17:10
 **/
@WebServlet(name = "ServletLogin", urlPatterns = {"/servletLogin"})
public class ServletLogin extends HttpServlet implements CommonContant {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取用户名和密码，校验
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (StringUtils.isAnyEmpty(username, password)) {
                request.setAttribute("msg", "用户名和密码均不能为空");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
            //Md5加密password
            password = MD5Utils.getMD5(password);

            //根据用户名和加密后密码去数据查询用户，如果存在进入首页，不存在给出提示
            UserService userService = new UserServiceImpl();
            User u = userService.selectUserByUsernameAndPassword(username, password);
            if (u == null) {
                request.setAttribute("msg", "用户名或密码不正确。");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("loginUser", u);
                //判断是否勾选记住用户名
                String remember = request.getParameter("remember");
                if (StringUtils.equals("on", remember)) {
                    //勾选
                    Cookie cookie = new Cookie("username", username);
                    cookie.setPath("/");
                    cookie.setMaxAge(100800);
                    response.addCookie(cookie);

                } else {
                    Cookie cookie = new Cookie("username", username);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }


                //重定向到首页,进入主页之前判断是否有记住的路径，如果有进入这个路径:在拦截器中判断有bug，
                String referer = (String) request.getSession().getAttribute("remUrl");
                if (StringUtils.isNotEmpty(referer)) {
                    response.sendRedirect(referer);
                } else {
                    response.sendRedirect(request.getContextPath());
                }
            }

        } catch (Exception e) {
            LOGGER.error("登陆异常，-----Exception------", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
