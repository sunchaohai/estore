package com.hai.chao.filter;

import com.hai.chao.common.CommonContant;
import com.hai.chao.domain.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: estore
 * @description: 权限过滤器
 * @author: xiaohai
 * @create: 2018-09-10 16:18
 **/
public class AuthColFilter implements Filter,CommonContant{
    /**
     * 创建两个集合用来存储 普通用户权限和管理员权限接口
     */
    private static List<String> adminList = new ArrayList<>();
    private static List<String> userList = new ArrayList<>();
    private  static final String ADMIN = "admin";
    private String noLoginPaths;

    @Override
    public void init(FilterConfig config) throws ServletException {
        String realPath = config.getServletContext().getRealPath("authfile/admin.txt");
        adminList = fileConvertToList(realPath);
        String realPath2 = config.getServletContext().getRealPath("authfile/user.txt");
        userList = fileConvertToList(realPath2);
        noLoginPaths = config.getInitParameter("noLoginPaths");
    }

    /**
     * 文件中权限数据存储到List中
     * @param realPath
     * @return
     */
    private  List<String> fileConvertToList(String realPath) {
        List<String> list = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(realPath))));
            String temp;
            while((temp=br.readLine())!=null){
                list.add(temp);
            }
        } catch (Exception e) {
            LOGGER.error("权限过滤器初始化方法获取缓存流失败,----Exception----",e);
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    LOGGER.error("权限过滤器初始化方法关闭资源异常,----Exception----",e);
                }
            }
        }

        return list;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("------------------+++=======three 三 3 c========+++--------------------------");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        uri = uri.substring(request.getContextPath().length());

        User user = (User) request.getSession().getAttribute("loginUser");
        if(user == null){
            //未登陆，进入登陆页面
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return;
        }
        //如果不是admin权限返回登陆页面
        if(!StringUtils.equalsIgnoreCase(ADMIN,user.getRole())){
            request.setAttribute("msg","您我权限访问"+uri+"资源，该资源只对admin角色开放!");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }else {
            //如果是：判断是否饮食此资源
            if(adminList.contains(uri)){
                filterChain.doFilter(request,response);
                return;
            }else {
                request.setAttribute("msg","您我权限访问"+uri+"资源，该资源只对admin角色开放!");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }
        }

    }

    @Override
    public void destroy() {

    }
}
