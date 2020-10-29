package com.past.service;

import com.past.beans.PageQuery;
import com.past.beans.PageResult;
import com.past.model.SysAcl;
import com.past.param.AclParam;

public interface SysAclService {


    /**
     * 校验参数、保存权限点
     * @param aclParam
     */
    void save(AclParam aclParam);


    /**
     * 校验参数、更新权限点
     * @param aclParam
     */
    void update(AclParam aclParam);


    /**
     * 验证权限点是否在该权限模块下已存在，存在返回true 抛出异常；不存在返回false，继续执行业务
     * @param aclModuleId
     * @param aclName
     * @param aclId
     * @return
     */
    boolean checkExist(Integer aclModuleId, String aclName, Integer aclId);


    /**
     * 生成权限码
     * @return
     */
    String generateCode();


    /**
     * 根据权限模块id和分页参数 获取当前模块下的权限点分页列表
     * @param aclModuleId
     * @param pageQuery
     * @return
     */
    PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery pageQuery);


}
