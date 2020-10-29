package com.past.service;

import com.past.model.SysAcl;

import java.util.List;

public interface SysCoreService {


    /**
     * 获取当前已登录的用户分配的权限列表
     * @return
     */
    List<SysAcl> getCurrentUserAclList();


    /**
     * 根据角色id获取角色已分配的权限点列表
     * @param roleId
     * @return
     */
    List<SysAcl> getRoleAclList(int roleId);


    /**
     * 根据用户id获取用户已分配的权限点列表
     * @param userId
     * @return
     */
    List<SysAcl> getUserAclList(int userId);


    /**
     * 判断是否是超级管理员
     * @return
     */
    boolean isSuperAdmin();


    /**
     * 是否具有访问该url的权限
     * @param url
     * @return
     */
    boolean hasUrlAcl(String url);


    /**
     * 获取当前已登录的用户分配的权限列表
     * @return
     */
    List<SysAcl> getCurrentUserAclListFromCache();


}
