package com.past.service;

import com.past.beans.PageQuery;
import com.past.beans.PageResult;
import com.past.model.SysUser;
import com.past.param.UserParam;

import java.util.List;

public interface SysUserService {


    /**
     * 校验参数、保存用户
     * @param userParam
     */
    void save(UserParam userParam);


    /**
     * 校验参数、更新用户
     * @param userParam
     */
    void update(UserParam userParam);


    /**
     * 判断该用户的邮箱是否存在，存在返回true 抛出异常；不存在返回false，继续执行业务
     * @param mail
     * @param userId
     * @return
     */
    boolean checkMailExist(String mail, Integer userId);


    /**
     * 判断该用户的手机号是否存在，存在返回true 抛出异常；不存在返回false，继续执行业务
     * @param telephone
     * @param userId
     * @return
     */
    boolean checkTelephoneExist(String telephone, Integer userId);


    /**
     * 根据关键词(邮箱或手机号)查询用户
     * @param keyword
     * @return
     */
    SysUser findByKeyword(String keyword);


    /**
     * 根据部门id和分页参数 获取分页结果
     * @param deptId
     * @param pageQuery
     * @return
     */
    PageResult<SysUser> getPageByDeptId(int deptId, PageQuery pageQuery);


    /**
     * 获取所有用户
     * @return
     */
    List<SysUser> getAll();


}
