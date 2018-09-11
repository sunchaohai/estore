package com.hai.chao.filter;

import com.hai.chao.domain.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: estore
 * @description: 用户是否登陆过滤器
 * @author: xiaohai
 * @create: 2018-08-31 19:24
 **/
public class LoginConfirmFilter implements Filter{
    private String noLoginPaths;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        noLoginPaths = filterConfig.getInitParameter("noLoginPaths");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("------------------+++=======two 二 2 b========+++--------------------------");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        if(StringUtils.isNotEmpty(noLoginPaths)){
            String[] paths = noLoginPaths.split(";");
            for(String path: paths){
                if(StringUtils.isEmpty(path)) {
                    continue;
                }

                //首页和指定接口不经过是否登陆过滤器
                if(uri.indexOf(path) != -1
                        || StringUtils.equalsIgnoreCase("/estore/",uri)){
                    filterChain.doFilter(request,response);
                    return;
                }
            }
        }

        //判断是否登陆
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if(loginUser == null){
            //未登陆，进入登陆页面
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }else {
            filterChain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
