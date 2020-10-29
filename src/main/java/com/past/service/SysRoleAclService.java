package com.past.service;

import java.util.List;

public interface SysRoleAclService {


    /**
     * 根据角色id  改变角色权限关系(新增、更新、删除)  并保存
     * @param roleId
     * @param aclIdList
     */
    void changeRoleAcls(Integer roleId, List<Integer> aclIdList);


    /**
     * 更新角色权限：删除旧权限，增加新权限
     * @param roleId
     * @param aclIdList
     */
    void updateRoleAcls(int roleId, List<Integer> aclIdList);


    /**
     * 保存角色权限关联关系的操作日志
     * @param roleId
     * @param before
     * @param after
     */
    void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after);


}
