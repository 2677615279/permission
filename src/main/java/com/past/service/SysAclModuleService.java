package com.past.service;

import com.past.model.SysAclModule;
import com.past.param.AclModuleParam;

public interface SysAclModuleService {


    /**
     * 校验参数、保存权限模块
     * @param aclModuleParam
     */
    void save(AclModuleParam aclModuleParam);


    /**
     * 校验参数、更新权限模块
     * @param aclModuleParam
     */
    void update(AclModuleParam aclModuleParam);


    /**
     * 验证权限模块是否在该父级下已存在，存在返回true 抛出异常；不存在返回false，继续执行业务
     * @param parentId
     * @param aclModuleName
     * @param aclModuleId
     * @return
     */
    boolean checkExist(Integer parentId, String aclModuleName, Integer aclModuleId);


    /**
     * 根据权限模块id查询数据库，获取权限模块对象的level属性值
     * @param aclModuleId
     * @return
     */
    String getLevel(Integer aclModuleId);


    /**
     * 更新当前权限模块和当前权限模块的子模块(更新到底)
     * @param before 更新前的权限模块
     * @param after 更新后的权限模块
     */
    void updateWithChild(SysAclModule before, SysAclModule after);


    /**
     * 根据模块id删除该权限模块
     * @param aclModuleId
     */
    void delete(int aclModuleId);


}
