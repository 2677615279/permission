package com.past.controller;

import com.google.common.collect.Maps;
import com.past.beans.PageQuery;
import com.past.beans.PageResult;
import com.past.common.JsonData;
import com.past.model.SysAcl;
import com.past.model.SysRole;
import com.past.model.SysUser;
import com.past.param.AclParam;
import com.past.service.SysAclService;
import com.past.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {

    @Autowired
    SysAclService sysAclService;

    @Autowired
    SysRoleService sysRoleService;


    /**
     * 在校验属性前提下 保存权限点
     * @param aclParam
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAcl(AclParam aclParam){

        sysAclService.save(aclParam);
        return JsonData.success();
    }


    /**
     * 在校验属性前提下 更新权限点
     * @param aclParam
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAcl(AclParam aclParam){

        sysAclService.update(aclParam);
        return JsonData.success();
    }


    /**
     * 根据权限模块id和分页参数信息，获取该模块下的权限点列表
     * @param aclModuleId 权限模块id
     * @param pageQuery 分页参数
     * @return
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery){

        PageResult<SysAcl> result = sysAclService.getPageByAclModuleId(aclModuleId, pageQuery);
        return JsonData.success(result);
    }


    /**
     * 根据权限点id，获取对应的权限点信息
     * @param aclId
     * @return
     */
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("aclId") int aclId){

        Map<String, Object> map = Maps.newHashMap();
        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
        List<SysUser> userList = sysRoleService.getUserListByRoleList(roleList);
        map.put("users", userList);//权限已分配的用户
        map.put("roles", roleList);//权限已分配的角色
        return JsonData.success(map);
    }


}
