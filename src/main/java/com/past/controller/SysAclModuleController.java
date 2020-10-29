package com.past.controller;

import com.past.common.JsonData;
import com.past.dto.AclModuleLevelDto;
import com.past.param.AclModuleParam;
import com.past.service.SysAclModuleService;
import com.past.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Autowired
    SysAclModuleService sysAclModuleService;

    @Autowired
    SysTreeService sysTreeService;


    /**
     * 在校验属性前提下 保存权限模块
     * @param aclModuleParam
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam aclModuleParam){

        sysAclModuleService.save(aclModuleParam);
        return JsonData.success();
    }


    /**
     * 在校验属性前提下 更新权限模块
     * @param aclModuleParam
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam aclModuleParam){

        sysAclModuleService.update(aclModuleParam);
        return JsonData.success();
    }


    /**
     * 获取权限模块层级树
     * @return
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){

        List<AclModuleLevelDto> dtoList = sysTreeService.aclModuleTree();
        return JsonData.success(dtoList);
    }


    /**
     * 跳转权限模块管理页面
     * @return
     */
    @RequestMapping("/acl.page")
    public ModelAndView page() {

        return new ModelAndView("acl");
    }


    /**
     * 根据模块id，删除该权限模块
     * @param id
     * @return
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData deleteAclModule(@RequestParam("id") int id){

        sysAclModuleService.delete(id);
        return JsonData.success();
    }


}
