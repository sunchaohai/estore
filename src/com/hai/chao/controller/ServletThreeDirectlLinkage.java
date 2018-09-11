package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
import com.hai.chao.domain.PCD;
import com.hai.chao.service.PCDService;
import com.hai.chao.service.impl.PCDServiceImpl;
import flexjson.JSONSerializer;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @program: estore
 * @description: ${description}
 * @author: xiaohai
 * @create: 2018-09-04 16:55
 **/
@WebServlet(name = "ServletThreeDirectlLinkage", urlPatterns = {"/servletThreeDirectlLinkage"})
public class ServletThreeDirectlLinkage extends HttpServlet implements CommonContant{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //接收参数：id
            String id = request.getParameter("id");
            PCDService pcdService = new PCDServiceImpl();
            if(StringUtils.isEmpty(id)){
                //查询所有pid=0的直辖市
                id = "0";
            }

            List<PCD> pcdList = pcdService.selectProvinceByPid(Integer.valueOf(id).intValue());

            //用flexJson把List转成json返回
            JSONSerializer serializer = new JSONSerializer();
            String serialize = serializer.serialize(pcdList);
            response.getWriter().write(serialize);

            //返回
//            request.setAttribute("pcdList",pcdList);
//            request.getRequestDispatcher("/orders_submit.jsp");
        }catch (Exception e){
            LOGGER.error("查询地区异常，----Exception----",e);
            response.sendRedirect(request.getContextPath()+"/500.jsp");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
