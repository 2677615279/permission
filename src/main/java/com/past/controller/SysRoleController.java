package com.past.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.past.common.JsonData;
import com.past.dto.AclModuleLevelDto;
import com.past.model.SysRole;
import com.past.model.SysUser;
import com.past.param.RoleParam;
import com.past.service.*;
import com.past.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sys/role")
@Slf4j
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysTreeService sysTreeService;

    @Autowired
    SysRoleAclService sysRoleAclService;

    @Autowired
    SysRoleUserService sysRoleUserService;

    @Autowired
    SysUserService sysUserService;


    /**
     * 在校验属性前提下 保存角色
     * @param roleParam
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveRole(RoleParam roleParam){

        sysRoleService.save(roleParam);
        return JsonData.success();
    }


    /**
     * 在校验属性前提下 更新角色
     * @param roleParam
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateRole(RoleParam roleParam){

        sysRoleService.update(roleParam);
        return JsonData.success();
    }


    /**
     * 获取角色列表
     * @return
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData list(){

        List<SysRole> roleList = sysRoleService.getAll();
        return JsonData.success(roleList);
    }


    /**
     * 跳转role.jsp页面
     * @return
     */
    @RequestMapping("/role.page")
    public ModelAndView page(){

        return new ModelAndView("role");
    }


    /**
     * 根据角色id获取角色权限树
     * @param roleId
     * @return
     */
    @RequestMapping("/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") int roleId){

        List<AclModuleLevelDto> list = sysTreeService.roleTree(roleId);
        return JsonData.success(list);
    }


    /**
     * 改变角色权限关联关系(新增、删除、更新)并保存
     * @param roleId
     * @param aclIds
     * @return
     */
    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds){

        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }


    /**
     * 根据角色id，获取该角色已分配的用户信息
     * @param roleId
     * @return
     */
    @RequestMapping("/users.json")
    @ResponseBody
    public JsonData users(@RequestParam("roleId") int roleId){

        List<SysUser> selectedUserList = sysRoleUserService.getUserListByRoleId(roleId);//根据角色id获取当前角色已分配的所有用户
        List<SysUser> allUserList = sysUserService.getAll();//查询数据库获取所有的用户
        List<SysUser> unselectedUserList = Lists.newArrayList();//初始化一个集合存储当前角色未分配的所有用户

        //使用stream流 生成一个存储当前角色已分配的所有用户id的集合
        Set<Integer> selectedUserIdSet = selectedUserList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());
        //遍历查询数据库所得的全部用户
        allUserList.forEach(sysUser -> {
            //如果状态为1(正常)且已分配的用户id集合中不包含此用户的id
            if (sysUser.getStatus() == 1 && !selectedUserIdSet.contains(sysUser.getId())){
                unselectedUserList.add(sysUser);//将其存入存储当前角色未分配的所有用户的集合中
            }
        });
        // 当用户状态不为1时，即非正常状态，在已分配的用户中执行过滤操作
        // selectedUserList = selectedUserList.stream().filter(sysUser -> sysUser.getStatus() !=1 ).collect(Collectors.toList());

        Map<String, List<SysUser>> map = Maps.newHashMap();
        map.put("selected", selectedUserList);
        map.put("unselected", unselectedUserList);
        return JsonData.success(map);
    }


    /**
     * 改变角色用户关联关系(新增、删除、更新)并保存
     * @param roleId
     * @param userIds
     * @return
     */
    @RequestMapping("/changeUsers.json")
    @ResponseBody
    public JsonData changeUsers(@RequestParam("roleId") int roleId, @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds){

        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
        sysRoleUserService.changeRoleUsers(roleId, userIdList);
        return JsonData.success();
    }


}
