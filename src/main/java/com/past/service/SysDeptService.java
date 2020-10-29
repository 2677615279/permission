package com.past.service;

import com.past.model.SysDept;
import com.past.param.DeptParam;

public interface SysDeptService {


    /**
     * 校验参数、保存部门
     * @param deptParam
     */
    void save(DeptParam deptParam);


    /**
     * 校验参数、更新部门
     * @param deptParam
     */
    void update(DeptParam deptParam);


    /**
     * 验证部门是否在该父级下已存在，存在返回true 抛出异常；不存在返回false，继续执行业务
     * @param parentId
     * @param deptName
     * @param deptId
     * @return
     */
    boolean checkExist(Integer parentId, String deptName, Integer deptId);


    /**
     * 根据部门id查询数据库，获取部门对象的level属性值
     * @param deptId
     * @return
     */
    String getLevel(Integer deptId);


    /**
     * 更新当前部门和当前部门的子部门(更新到底)
     * @param before 更新前的部门
     * @param after 更新后的部门
     */
    void updateWithChild(SysDept before, SysDept after);


    /**
     * 根据部门id删除该部门
     * @param deptId
     */
    void delete(int deptId);


}
