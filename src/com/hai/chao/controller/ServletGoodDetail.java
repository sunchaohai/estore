package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
import com.hai.chao.domain.Good;
import com.hai.chao.service.GoodService;
import com.hai.chao.service.impl.GoodServiceImpl;
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
 * @create: 2018-09-01 20:27
 **/
@WebServlet(name = "ServletGoodDetail", urlPatterns = {"/goodsDetail"})
public class ServletGoodDetail extends HttpServlet implements CommonContant{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            //获取参数，goodsId,校验
            String goodId = request.getParameter("gid");
            if(StringUtils.isEmpty(goodId)){
                request.setAttribute("msg","商品id不能为空");
                request.getRequestDispatcher("/goods.jsp").forward(request,response);
                return;
            }
            //调service,查询商品信息
            GoodService goodService = new GoodServiceImpl();
            Good good = goodService.selectGoodByPrimaryId(goodId);

            request.setAttribute("good",good);
            request.getRequestDispatcher("/goods_detail.jsp").forward(request,response);

        }catch (Exception e){
            LOGGER.error("查看商品详情接口异常,-----Exception------",e);
            response.sendRedirect(request.getContextPath());
            return;
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
