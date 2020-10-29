package com.past.service;

import com.past.model.SysRole;
import com.past.model.SysUser;
import com.past.param.RoleParam;

import java.util.List;

public interface SysRoleService {


    /**
     * 校验参数、保存角色
     * @param roleParam
     */
    void save(RoleParam roleParam);


    /**
     * 校验参数、更新权角色
     * @param roleParam
     */
    void update(RoleParam roleParam);


    /**
     * 验证角色名称是否已存在，存在返回true 抛出异常；不存在返回false，继续执行业务
     * @param name
     * @param id
     * @return
     */
    boolean checkExist(String name, Integer id);


    /**
     * 查询获取所有角色
     * @return
     */
    List<SysRole> getAll();


    /**
     * 根据用户id获取分配该用户的所有角色
     * @param userId
     * @return
     */
    List<SysRole> getRoleListByUserId(int userId);


    /**
     * 根据权限id获取分配该权限的所有角色
     * @param aclId
     * @return
     */
    List<SysRole> getRoleListByAclId(int aclId);


    /**
     * 根据角色列表 查询每个角色所分配的用户列表
     * @param roleList
     * @return
     */
    List<SysUser> getUserListByRoleList(List<SysRole> roleList);


}
