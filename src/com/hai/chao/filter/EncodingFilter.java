package com.hai.chao.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @program: estore
 * @description: 中文乱码过滤器
 * @author: xiaohai
 * @create: 2018-08-30 10:24
 **/
public class EncodingFilter implements Filter{
    private static final Logger LOGGER = LoggerFactory.getLogger(EncodingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String fileterName = filterConfig.getInitParameter("filter-name");
        try {
            System.out.println(new String(fileterName.getBytes("gbk"),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LOGGER.info(fileterName+"......init方法......");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("------------------+++=======one 一 1 a========+++--------------------------");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setContentType("text/html;charset=utf-8");
        HttpServletRequest myRequest = new MyRequest(request);

        filterChain.doFilter(myRequest,response);
    }

    @Override
    public void destroy() {
        LOGGER.info("中文乱码过滤器，destroy方法......");
    }
}
