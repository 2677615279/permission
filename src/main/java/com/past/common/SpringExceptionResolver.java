package com.past.common;

import com.past.exception.ParamException;
import com.past.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 全局异常处理类
 */
@Slf4j  //日志处理
public class SpringExceptionResolver implements HandlerExceptionResolver , Serializable {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        String url = httpServletRequest.getRequestURL().toString();
        ModelAndView modelAndView;
        String defaultMsg = "System Error";

        //对数据请求和页面请求分别做异常处理 .json  .page
        if (url.endsWith(".json")){ //是数据请求
            //如果是我们自定义的异常
            if (e instanceof PermissionException || e instanceof ParamException){
                JsonData result = JsonData.fail(e.getMessage());
                modelAndView = new ModelAndView("jsonView",result.toMap());
            } else { //如果不是自定义的异常
                log.error("unknow json exception , url:" + url , e); //日志输出
                JsonData result = JsonData.fail(defaultMsg);
                modelAndView = new ModelAndView("jsonView",result.toMap());
            }
        } else if (url.endsWith(".page")){ //是页面请求
            log.error("unknow page exception , url:" + url , e); //日志输出
            JsonData result = JsonData.fail(defaultMsg);
            modelAndView = new ModelAndView("exception",result.toMap());
        } else {
            log.error("unknow exception , url:" + url , e); //日志输出
            JsonData result = JsonData.fail(defaultMsg);
            modelAndView = new ModelAndView("jsonView",result.toMap());
        }

        return modelAndView;
    }

}
