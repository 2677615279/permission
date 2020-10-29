package com.past.controller;

import com.past.beans.PageQuery;
import com.past.beans.PageResult;
import com.past.common.JsonData;
import com.past.model.SysLogWithBLOBs;
import com.past.param.SearchLogParam;
import com.past.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sys/log")
@Slf4j
public class SysLogController {

    @Autowired
    SysLogService sysLogService;


    /**
     * 跳转操作日志页面
     * @return
     */
    @RequestMapping("/log.page")
    public ModelAndView page(){

        return new ModelAndView("log");
    }


    /**
     * 根据查询条件返回分页接口
     * @param searchLogParam
     * @param pageQuery
     * @return
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData searchPage(SearchLogParam searchLogParam, PageQuery pageQuery){

        PageResult<SysLogWithBLOBs> result = sysLogService.searchPageList(searchLogParam, pageQuery);
        return JsonData.success(result);
    }


    /**
     * 根据操作日志id还原接口
     * @param id
     * @return
     */
    @RequestMapping("/recover.json")
    @ResponseBody
    public JsonData recover(@RequestParam("id") int id){

        sysLogService.recover(id);
        return JsonData.success();
    }


}
