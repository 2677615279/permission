package com.past.dao;

import com.past.model.SysRoleAcl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysRoleAclMapper {


    /**
     * 根据主键id删除角色权限关联关系
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);


    /**
     * 保存角色权限关联关系(所有的字段都会添加一遍即使没有值)
     * @param record
     * @return
     */
    int insert(SysRoleAcl record);


    /**
     * 保存角色权限关联关系,只给有值的字段赋值,会对传进来的值做非空判断
     * @param record
     * @return
     */
    int insertSelective(SysRoleAcl record);


    /**
     * 根据主键id查询角色权限关联关系
     * @param id
     * @return
     */
    SysRoleAcl selectByPrimaryKey(Integer id);


    /**
     * 更新角色权限关联关系 会对字段进行判断再更新(如果为Null就忽略更新)，如果你只想更新某一字段，可以用这个方法。
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysRoleAcl record);


    /**
     * 更新角色权限关联关系 对你注入的字段全部更新，如果有字段不更新，数据库的值就为null
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysRoleAcl record);


    /**
     * 根据角色id列表，获取所对应的权限点id列表
     * @param roleIdList
     * @return
     */
    List<Integer> getAclIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);


    /**
     * 根据角色id 删除该角色的权限
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId") int roleId);


    /**
     * 批量新增新的角色权限关联关系
     * @param roleAclList
     */
    void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);


    /**
     * 根据权限id 获取分配该权限的所有角色id
     * @param aclId
     * @return
     */
    List<Integer>getRoleIdListByAclId(@Param("aclId") int aclId);


}