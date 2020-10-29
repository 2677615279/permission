package com.past.dao;

import com.past.model.SysRoleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysRoleUserMapper {


    /**
     * 根据主键id删除角色用户关联关系
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);


    /**
     * 保存角色用户关联关系(所有的字段都会添加一遍即使没有值)
     * @param record
     * @return
     */
    int insert(SysRoleUser record);


    /**
     * 保存角色用户关联关系,只给有值的字段赋值,会对传进来的值做非空判断
     * @param record
     * @return
     */
    int insertSelective(SysRoleUser record);


    /**
     * 根据主键id查询角色用户关联关系
     * @param id
     * @return
     */
    SysRoleUser selectByPrimaryKey(Integer id);


    /**
     * 更新角色用户关联关系 会对字段进行判断再更新(如果为Null就忽略更新)，如果你只想更新某一字段，可以用这个方法。
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysRoleUser record);


    /**
     * 更新角色用户关联关系 对你注入的字段全部更新，如果有字段不更新，数据库的值就为null
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysRoleUser record);


    /**
     * 根据用户id，取出用户分配的角色id列表
     * @param userId
     * @return
     */
    List<Integer> getRoleIdListByUserId(@Param("userId") int userId);


    /**
     * 根据角色id，取出角色分配的用户id列表
     * @param roleId
     * @return
     */
    List<Integer> getUserIdListByRoleId(@Param("roleId") int roleId);


    /**
     * 根据角色id 删除所有分配此角色的用户
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId") int roleId);


    /**
     * 批量新增新的角色用户关联关系
     * @param roleUserList
     */
    void batchInsert(@Param("roleUserList") List<SysRoleUser> roleUserList);


    /**
     * 根据角色id列表，取出每个角色分配的用户id列表
     * @param roleIdList
     * @return
     */
    List<Integer> getUserIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);
    

}