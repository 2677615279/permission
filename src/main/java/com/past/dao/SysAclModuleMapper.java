package com.past.dao;

import com.past.model.SysAclModule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysAclModuleMapper {


    /**
     * 根据主键id删除权限模块
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);


    /**
     * 保存权限模块(所有的字段都会添加一遍即使没有值)
     * @param record
     * @return
     */
    int insert(SysAclModule record);


    /**
     * 保存权限模块,只给有值的字段赋值,会对传进来的值做非空判断
     * @param record
     * @return
     */
    int insertSelective(SysAclModule record);


    /**
     * 根据主键id查询权限模块
     * @param id
     * @return
     */
    SysAclModule selectByPrimaryKey(Integer id);


    /**
     * 更新权限模块 会对字段进行判断再更新(如果为Null就忽略更新)，如果你只想更新某一字段，可以用这个方法。
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysAclModule record);


    /**
     * 更新权限模块 对你注入的字段全部更新，如果有字段不更新，数据库的值就为null
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysAclModule record);


    /**
     * 获取所有权限模块
     * @return
     */
    List<SysAclModule> getAllAclModule();


    /**
     * 根据父id和其id name查询权限模块的数目
     * @param parentId
     * @param name
     * @param id
     * @return
     */
    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);


    /**
     * 根据层级获取子集
     * @param level
     * @return
     */
    List<SysAclModule> getChildAclModuleListByLevel(@Param("level") String level);


    /**
     * 批量更新层级
     * @param sysAclModuleList
     */
    void batchUpdateLevel(@Param("sysAclModuleList") List<SysAclModule> sysAclModuleList);


    /**
     * 根据父id查询该id所对权限模块的数目
     * @param aclModuleId
     * @return
     */
    int countByParentId(@Param("aclModuleId") int aclModuleId);


}