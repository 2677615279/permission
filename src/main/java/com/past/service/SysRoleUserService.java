package com.past.service;

import com.past.model.SysUser;

import java.util.List;

public interface SysRoleUserService {


    /**
     * 根据角色id获取角色分配的所有用户
     * @param roleId
     * @return
     */
    List<SysUser> getUserListByRoleId(int roleId);


    /**
     * 根据角色id改变该角色下的  角色用户关系(新增、更新、删除)  并保存
     * @param roleId
     * @param userIdList
     */
    void changeRoleUsers(int roleId, List<Integer> userIdList);


    /**
     * 更新角色用户：删除旧用户，增加新用户
     * @param roleId
     * @param userIdList
     */
    void updateRoleUsers(int roleId, List<Integer> userIdList);


    /**
     * 保存角色用户关联关系的操作日志
     * @param roleId
     * @param before
     * @param after
     */
    void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after);


}
