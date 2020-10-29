package com.past.common;

import com.past.model.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用ThreadLocal控制请求和登录请求的对象
 */
public class RequestHolder {

    private static final ThreadLocal<SysUser> USER_HOLDER = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletRequest> REQUEST_HOLDER = new ThreadLocal<>();


    /**
     * 向ThreadLocal中添加登录过的对象  SysUser
     * @param sysUser
     */
    public static void add(SysUser sysUser){

        USER_HOLDER.set(sysUser);
    }


    /**
     * 向ThreadLocal中添加当前请求对象 httpServletRequest
     * @param httpServletRequest
     */
    public static void add(HttpServletRequest httpServletRequest){

        REQUEST_HOLDER.set(httpServletRequest);
    }


    /**
     * 从ThreadLocal中取出登录过的对象  SysUser
     * @return
     */
    public static SysUser getCurrentUser(){

        return USER_HOLDER.get();
    }


    /**
     * 从ThreadLocal中取出请求的对象  HttpServletRequest
     * @return
     */
    public static HttpServletRequest getCurrentRequest(){

        return REQUEST_HOLDER.get();
    }


    /**
     * 每个进程结束后，从ThreadLocal移除登录过的SysUser对象，移除请求HttpServletRequest对象
     */
    public static void remove(){

        USER_HOLDER.remove();
        REQUEST_HOLDER.remove();
    }


}
