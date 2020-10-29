package com.past.controller;

import com.google.common.collect.Maps;
import com.past.beans.PageQuery;
import com.past.beans.PageResult;
import com.past.common.JsonData;
import com.past.dto.AclModuleLevelDto;
import com.past.model.SysRole;
import com.past.model.SysUser;
import com.past.param.UserParam;
import com.past.service.SysRoleService;
import com.past.service.SysTreeService;
import com.past.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 系统用户后台管理--保存更新删除...
 */
@Controller
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController {

    @Autowired
    SysUserService sysUserService;
    
    @Autowired
    SysTreeService sysTreeService;

    @Autowired
    SysRoleService sysRoleService;


    /**
     * 在校验属性前提下 保存用户
     * @param userParam
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam userParam){

        sysUserService.save(userParam);
        return JsonData.success();
    }


    /**
     * 在校验属性前提下 更新用户
     * @param userParam
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam userParam){

        sysUserService.update(userParam);
        return JsonData.success();
    }


    /**
     * 分页接口
     * @param deptId    部门id
     * @param pageQuery 分页参数
     * @return
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(int deptId, PageQuery pageQuery){

        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, pageQuery);
        return JsonData.success(result);
    }


    /**
     * 根据用户id，获取该用户的权限点信息
     * @param userId
     * @return
     */
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("userId") int userId){

        Map<String, Object> map = Maps.newHashMap();
        List<AclModuleLevelDto> userAclTree = sysTreeService.userAclTree(userId);//根据用户id获取分配给该用户的所有权限，形成树形结构
        List<SysRole> roleList = sysRoleService.getRoleListByUserId(userId);//根据用户id获取分配给该用户的所有角色
        map.put("acls", userAclTree);//用户已分配的权限
        map.put("roles", roleList);//用户已分配的角色
        return JsonData.success(map);
    }


    /**
     * 当用户无权限访问时  跳转的页面
     * @return
     */
    @RequestMapping("/noAuth.page")
    public ModelAndView noAuth(){

        return new ModelAndView("noAuth");
    }


}
