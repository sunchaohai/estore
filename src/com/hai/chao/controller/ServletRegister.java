package com.hai.chao.controller;

import com.hai.chao.common.CommonContant;
import com.hai.chao.domain.User;
import com.hai.chao.service.UserService;
import com.hai.chao.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: estore
 * @description: 用户注册
 * @author: xiaohai
 * @create: 2018-08-29 19:12
 **/
@WebServlet(name = "ServletRegister", urlPatterns = {"/servletRegister"})
public class ServletRegister extends HttpServlet implements CommonContant {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            //获取参数并校验
            boolean paramFlag = validateParam(request, response);
            if(!paramFlag){
                request.getRequestDispatcher("/register.jsp").forward(request,response);
                return;
            }

            //校验验证码
            boolean flag = checkUser(request,request.getParameter("captcha"));
            if(!flag){
                request.setAttribute("msg","验证码不正确");
                request.getRequestDispatcher("/register.jsp").forward(request,response);
                return;
            }


            //封装到user对象中
            User user = new User();
            BeanUtils.populate(user,request.getParameterMap());

            //调用service方法保存用户
            UserService userService = new UserServiceImpl();
            int count = userService.saveUser(user);
            if(count == -2){
                request.setAttribute("msg","用户已经存在");
                request.getRequestDispatcher("/register.jsp").forward(request,response);
                return;
            }else if(count == 1){
                //保存成功
                response.sendRedirect(request.getContextPath()+"/login.jsp");
                return;
            }else{
                request.setAttribute("msg","服务器忙，请稍后再试");
                request.getRequestDispatcher("/register.jsp").forward(request,response);
                return;
            }

        }catch (Exception e){
            LOGGER.error("注册接口异常了,------Exception------",e);
        }

        //返回
    }

    /**
     * 校验参数 true:通过校验，false:未通过
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private boolean validateParam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean flag = true;
        String username = request.getParameter("username");
        if(StringUtils.isEmpty(username)){
            request.setAttribute("msg","用户名不能为空");
            flag = false;
        }
        String nickname = request.getParameter("nickname");
        if(StringUtils.isEmpty(nickname)){
            request.setAttribute("msg","用昵称不能为空");
            flag = false;
        }
        String password = request.getParameter("password");
        if(StringUtils.isEmpty(password)){
            request.setAttribute("msg","用户密码不能为空");
            flag = false;
        }
        String confirm_password = request.getParameter("confirm_password");
        if(StringUtils.isEmpty(confirm_password)){
            request.setAttribute("msg","确认不能为空");
            flag = false;
        }
        String captcha = request.getParameter("captcha");
        if(StringUtils.isEmpty(captcha)){
            request.setAttribute("msg","验证码不能为空");
            flag = false;
        }

        return flag;

    }

    /**
    * @Description: 校验验证码是否正确，true正确 false不正确
    * @Param: [request:请求request, captcha:验证码]
    * @return: boolean
    * @Author: xiaohai
    * @Date: 2018/8/31
    */
    private boolean checkUser(HttpServletRequest request, String captcha) {
        String code = (String) request.getSession().getAttribute("code");
        if(StringUtils.equals(code,captcha)){
            return true;
        }

        return false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
