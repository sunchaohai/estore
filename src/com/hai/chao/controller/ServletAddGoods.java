package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
import com.hai.chao.domain.Good;
import com.hai.chao.service.GoodService;
import com.hai.chao.service.impl.GoodServiceImpl;
import com.hai.chao.utils.DirUtil;
import com.hai.chao.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: estore
 * @description: ${description}
 * @author: xiaohai
 * @create: 2018-09-09 13:04
 **/
@WebServlet(name = "ServletAddGoods", urlPatterns = {"/addGoodServlet"})
public class ServletAddGoods extends HttpServlet implements CommonContant{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //校验登陆，获取参数
        GoodService goodService = new GoodServiceImpl();
        Good good = new Good();
        try {
            //创建disk并绑定ServletFileUpload
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            //解析request对象
            List<FileItem> fileItems = upload.parseRequest(request);
            Map<String,Object> map = new HashMap<>();
            for (FileItem fileItem : fileItems) {
                if(fileItem.isFormField()){
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString("utf-8");

                    map.put(name,value);
                }else {
                    String fieldName = fileItem.getName();
                    //防止姓名重复
                    fieldName = UUIDUtils.getUUID()+fieldName;
                    String dir = DirUtil.getDir(fieldName);
                    File fileDir = new File("/Users/sch/mydocument/mywork/gitworkspace/estore/web/upload",dir);

                    if(!fileDir.exists()){
                        fileDir.mkdirs();
                    }
                    good.setImgurl("/upload"+dir+"/"+fieldName);
                    fileItem.write(new File(fileDir,fieldName));
                }
            }
            BeanUtils.populate(good,map);

            //调用service保存商品
            goodService.saveGood(good);

            //转发至商品列表
            request.getRequestDispatcher("/servletGoodsListAdmin").forward(request,response);
        }catch (Exception e){
            LOGGER.error("添加商品异常，----Exception----",e);
            response.sendRedirect(request.getContextPath()+"/500.jsp");
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
