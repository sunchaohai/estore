package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
import com.hai.chao.domain.Good;
import com.hai.chao.domain.User;
import com.hai.chao.service.GoodService;
import com.hai.chao.service.impl.GoodServiceImpl;

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
 * @create: 2018-08-31 18:49
 **/
@WebServlet(name = "ServletGoodsList", urlPatterns = {"/servletGoodsList"})
public class ServletGoodsList extends HttpServlet implements CommonContant{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            //查询所有商品
            GoodService goodService = new GoodServiceImpl();
            List<Good> list = goodService.selectAllGoods();
            //放入request
            request.setAttribute("list",list);
            //转发到goods.jsp

            request.getRequestDispatcher("/goods.jsp").forward(request,response);

        }catch (Exception e){
            LOGGER.info("商品列表查询异常,----Exception----",e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
