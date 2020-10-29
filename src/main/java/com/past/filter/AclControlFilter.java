package com.past.filter;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.past.common.ApplicationContextHelper;
import com.past.common.JsonData;
import com.past.common.RequestHolder;
import com.past.model.SysUser;
import com.past.service.SysCoreService;
import com.past.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限拦截过滤
 */
@Slf4j
public class AclControlFilter implements Filter {

    private Set<String> exclusionUrlSet = Sets.newConcurrentHashSet();//存储白名单url的集合

    private final static String NO_AUTH_URL = "/sys/user/noAuth.page";//定义一个无权限时，页面请求访问的url(保证该url必须在白名单中)

    /**
     * 过滤器初始化、读取web.xml文件，解析出需要过滤的白名单的url
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String exclusionUrls = filterConfig.getInitParameter("exclusionUrls");//白名单的url
        List<String> exclusionUrlList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(exclusionUrls);
        exclusionUrlSet = Sets.newConcurrentHashSet(exclusionUrlList);
        exclusionUrlSet.add(NO_AUTH_URL);
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
        String servletPath = req.getServletPath();//获取请求url
        Map parameterMap = req.getParameterMap();//获取请求参数

        //白名单的处理逻辑
        if (exclusionUrlSet.contains(servletPath)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //获取登录的用户
        SysUser sysUser = RequestHolder.getCurrentUser();
        //未登录  执行无权限操作
        if (sysUser == null){
            log.info("someone visit {}, but doesn't login, parameter:{}", servletPath, JsonMapper.objToString(parameterMap));
            noAuth(req, resp);
            return;
        }

        SysCoreService sysCoreService = ApplicationContextHelper.popBean(SysCoreService.class);
        //没有访问该url的权限  执行无权限操作
        if (sysCoreService != null && !sysCoreService.hasUrlAcl(servletPath)){
            log.info("{} visit {}, but doesn't login, parameter:{}", JsonMapper.objToString(sysUser), servletPath, JsonMapper.objToString(parameterMap));
            noAuth(req, resp);
            return;
        }

        //调用过滤链，继续过滤
        filterChain.doFilter(servletRequest, servletResponse);
    }


    /**
     * 销毁过滤器
     */
    @Override
    public void destroy() {

    }


    /**
     * 无权限操作
     */
    public void noAuth(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String servletPath = req.getServletPath();//获取请求url

        //如果是json请求
        if (servletPath.endsWith(".json")){
            JsonData jsonData = JsonData.fail("没有访问权限，如需要访问，请联系管理员");
            resp.setHeader("Content-Type", "application/json");
            resp.getWriter().print(JsonMapper.objToString(jsonData));
        }
        //如果是其他请求 如页面请求
        else {
            clientRedirect(NO_AUTH_URL, resp);
        }
    }


    /**
     * 无权限操作时  页面请求时的跳转逻辑
     * @param url
     */
    public void clientRedirect(String url, HttpServletResponse resp) throws IOException {

        resp.setHeader("Content-Type", "text/html");
        resp.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                + "<title>跳转中...</title>\n" + "</head>\n" + "<body>\n" + "跳转中，请稍候...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                + "window.location.href='" + url + "?ret='+encodeURIComponent(window.location.href);\n" + "//]]></script>\n" + "</body>\n" + "</html>\n");
    }


}
