package com.past.common;

import com.past.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Http请求前后监听工具--HttpInterceptor
 */
@Slf4j  //日志处理
public class HttpInterceptor implements HandlerInterceptor {

    private static final String START_TIME = "requestStartTime"; //记录请求开始时间


    /**
     * 在请求url之前执行
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURL().toString();
        Map parameterMap = httpServletRequest.getParameterMap();
        log.info("request start. url:{}, params:{}", url, JsonMapper.objToString(parameterMap));//打印日志 使用占位符 参数敏感信息有待过滤
        Long start = System.currentTimeMillis();
        httpServletRequest.setAttribute(START_TIME,start); //将请求开始时间存入Request作用域
        return true;
    }


    /**
     * 只在请求正常结束之后，执行  遇到异常不执行
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        String url = httpServletRequest.getRequestURL().toString();
        Map parameterMap = httpServletRequest.getParameterMap();
        Long start = (Long) httpServletRequest.getAttribute(START_TIME);
        Long end = System.currentTimeMillis();
        log.info("request finished. url:{}, params:{}, cost:{}", url, JsonMapper.objToString(parameterMap), end - start);//打印日志 使用占位符 参数敏感信息有待过滤
        removeThreadLocalInfo();
    }


    /**
     * 在请求正常结束或异常结束之后，都会执行   遇到异常也执行
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        String url = httpServletRequest.getRequestURL().toString();
        Map parameterMap = httpServletRequest.getParameterMap();
        Long start = (Long) httpServletRequest.getAttribute(START_TIME);
        Long end = System.currentTimeMillis();
        log.info("request completion. url:{}, params:{}, cost:{}", url, JsonMapper.objToString(parameterMap), end - start);//打印日志 使用占位符 参数敏感信息有待过滤
        removeThreadLocalInfo();
    }


    /**
     * 在请求结束之后 从ThreadLocal移除登录过的SysUser对象，移除请求HttpServletRequest对象
     */
    public void removeThreadLocalInfo(){
        RequestHolder.remove();
    }

}
