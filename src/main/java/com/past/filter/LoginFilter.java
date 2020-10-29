package com.past.filter;

import com.past.common.RequestHolder;
import com.past.model.SysUser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录过滤器、判断用户是否登录
 * 拦截需要用户登录的请求，如果用户未登录，使其进入登录页面；如果已登录，取出登录用户的信息，存入ThreadLocal中
 */
@Slf4j
public class LoginFilter implements Filter {


    /**
     * 过滤器初始化
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    /**
     * 执行具体过滤业务
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;

        //从session中取出sysUser键多对应的对象
        SysUser sysUser = (SysUser) req.getSession().getAttribute("sysUser");

        if (sysUser == null){ //session中无登录用户信息，暂无用户登录-->重定向到登录页面
            resp.sendRedirect("/signin.jsp");
            return;
        }

        //session中有登录用户信息，用户已登录-->将登录用户的信息和请求存入ThreadLocal中
        RequestHolder.add(sysUser);
        RequestHolder.add(req);

        //调用过滤链，继续过滤
        filterChain.doFilter(servletRequest, servletResponse);
    }


    /**
     * 销毁过滤器
     */
    @Override
    public void destroy() {

    }


}
