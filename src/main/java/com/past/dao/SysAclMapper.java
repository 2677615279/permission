package com.past.dao;

import com.past.beans.PageQuery;
import com.past.model.SysAcl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysAclMapper {


    /**
     * 根据主键id删除权限
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);


    /**
     * 保存权限(所有的字段都会添加一遍即使没有值)
     * @param record
     * @return
     */
    int insert(SysAcl record);


    /**
     * 保存权限,只给有值的字段赋值,会对传进来的值做非空判断
     * @param record
     * @return
     */
    int insertSelective(SysAcl record);


    /**
     * 根据主键id查询权限
     * @param id
     * @return
     */
    SysAcl selectByPrimaryKey(Integer id);


    /**
     * 更新权限 会对字段进行判断再更新(如果为Null就忽略更新)，如果你只想更新某一字段，可以用这个方法。
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysAcl record);


    /**
     * 更新权限 对你注入的字段全部更新，如果有字段不更新，数据库的值就为null
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysAcl record);


    /**
     * 根据当前权限点id和name以及所处权限模块id 查询权限点数目
     * @param aclModuleId
     * @param name
     * @param id
     * @return
     */
    int countByNameAndAclModuleId(@Param("aclModuleId") Integer aclModuleId, @Param("name") String name, @Param("id") Integer id);


    /**
     * 根据分页参数和权限模块id，查询该权限模块下所有权限点的实际分页结果
     * @param aclModuleId
     * @param page
     * @return
     */
    List<SysAcl> getPageByAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("page") PageQuery page);


    /**
     * 根据权限模块id，查询其下所有权限点的数目
     * @param aclModuleId
     * @return
     */
    int countByAclModuleId(@Param("aclModuleId") int aclModuleId);


    /**
     * 查询获取所有的权限点信息 返回一个集合
     * @return
     */
    List<SysAcl> getAll();


    /**
     * 根据权限点id集合，查询所对应的权限点集合
     * @param idList
     * @return
     */
    List<SysAcl> getByIdList(@Param("idList") List<Integer> idList);


    /**
     * 根据url获取所有权限点
     * @param url
     * @return
     */
    List<SysAcl> getByUrl(@Param("url") String url);


}