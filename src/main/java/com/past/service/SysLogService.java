package com.past.service;

import com.past.beans.PageQuery;
import com.past.beans.PageResult;
import com.past.model.*;
import com.past.param.SearchLogParam;

public interface SysLogService {


    /**
     * 保存部门的操作日志
     * @param before
     * @param after
     */
    void saveDeptLog(SysDept before, SysDept after);


    /**
     * 保存用户的操作日志
     * @param before
     * @param after
     */
    void saveUserLog(SysUser before, SysUser after);


    /**
     * 保存权限模块的操作日志
     * @param before
     * @param after
     */
    void saveAclModuleLog(SysAclModule before, SysAclModule after);


    /**
     * 保存权限点的操作日志
     * @param before
     * @param after
     */
    void saveAclLog(SysAcl before, SysAcl after);


    /**
     * 保存角色的操作日志
     * @param before
     * @param after
     */
    void saveRoleLog(SysRole before, SysRole after);


    /**
     * 根据分页参数和查询参数，获取操作日志的分页结果
     * @param param
     * @param page
     * @return
     */
    PageResult<SysLogWithBLOBs> searchPageList(SearchLogParam param, PageQuery page);


    /**
     * 根据id 还原该数据的操作
     * @param id
     */
    void recover(int id);


}
