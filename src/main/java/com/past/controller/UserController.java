package com.past.controller;

import com.past.model.SysUser;
import com.past.service.SysUserService;
import com.past.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户日常管理--登录退出...
 */
@Controller
@Slf4j
public class UserController {

    @Autowired
    SysUserService sysUserService;


    /**
     * 用户登录
     * @param request
     * @param response
     */
    @RequestMapping("/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        SysUser sysUser = sysUserService.findByKeyword(username);//根据关键词(邮箱或手机号)查询用户
        String errorMsg = "";

        //登录成功欲跳转的链接，没有值即为"" ，登录成功跳转/admin/index.page该请求所跳页面; 有值则跳转?ret=所跟参数值的链接
        String ret = request.getParameter("ret"); //如  localhost:9999/sign.jsp?ret=/sys/dept/dept.page或localhost:9999/sign.jsp?ret=

        if (StringUtils.isBlank(username)){
            errorMsg = "用户名不可以为空";
        }
        else if (StringUtils.isBlank(password)){
            errorMsg = "密码不可以为空";
        }
        else if (sysUser == null){
            errorMsg = "查询不到指定的用户";
        }
        else if (!sysUser.getPassword().equals(MD5Util.encrypt(password))){
            errorMsg = "用户名或密码错误";
        }
        else if (sysUser.getStatus() != 1){
            errorMsg = "用户已被冻结，请联系管理员";
        }
        else {
            //login success...  登录成功，请求重定向指定的请求所跳转的页面
            request.getSession().setAttribute("sysUser", sysUser);//将登录成功的用户信息写入session作用域
            if (StringUtils.isNotBlank(ret)){
                response.sendRedirect(ret);
            }
            else {
                response.sendRedirect("/admin/index.page");
            }
            return; //登录成功，重定向页面，结束该方法，避免以下继续执行请求转发而抛出异常
        }

        //login fail...  登录失败，把错误信息写到页面，请求转发到signin.jsp
        request.setAttribute("errorMsg", errorMsg);
        request.setAttribute("username", username);
        if (StringUtils.isNotBlank(ret)){
            request.setAttribute("ret", ret);
        }
        String path = "/signin.jsp";
        //请求转发
        request.getRequestDispatcher(path).forward(request, response);
    }


    /**
     * 用户注销
     * @param request
     * @param response
     */
    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception{

        request.getSession().invalidate();//注销session  清空session中的所有值
        String path = "/signin.jsp";
        //请求重定向
        response.sendRedirect(path);
    }


}
