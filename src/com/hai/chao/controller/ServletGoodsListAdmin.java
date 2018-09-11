package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
import com.hai.chao.domain.Good;
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
 * @create: 2018-09-09 15:09
 **/
@WebServlet(name = "ServletGoodsListAdmin", urlPatterns = {"/servletGoodsListAdmin"})
public class ServletGoodsListAdmin extends HttpServlet implements CommonContant{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //校验登陆
            //查询数据
            GoodService goodService = new GoodServiceImpl();
            List<Good> goods = goodService.selectAllGoods();

            //转发到前台
            request.setAttribute("goods",goods);
            request.getRequestDispatcher("/goods_admin.jsp").forward(request,response);
        }catch (Exception e){
            LOGGER.error("后台管理查询商品列表异常,---Exception---",e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
