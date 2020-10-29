package com.past.dao;

import com.past.model.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysRoleMapper {


    /**
     * 根据主键id删除角色
     * @param roleId
     * @return
     */
    int deleteByPrimaryKey(Integer roleId);


    /**
     * 保存角色(所有的字段都会添加一遍即使没有值)
     * @param record
     * @return
     */
    int insert(SysRole record);


    /**
     * 保存角色,只给有值的字段赋值,会对传进来的值做非空判断
     * @param record
     * @return
     */
    int insertSelective(SysRole record);


    /**
     * 根据主键id查询角色
     * @param roleId
     * @return
     */
    SysRole selectByPrimaryKey(Integer roleId);


    /**
     * 更新角色 会对字段进行判断再更新(如果为Null就忽略更新)，如果你只想更新某一字段，可以用这个方法。
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysRole record);


    /**
     * 更新角色 对你注入的字段全部更新，如果有字段不更新，数据库的值就为null
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysRole record);


    /**
     * 查询获取所有角色
     * @return
     */
    List<SysRole> getAll();


    /**
     * 根据名称查询 当前角色数目
     * @param name
     * @param id
     * @return
     */
    int countByName(@Param("name") String name, @Param("id") Integer id);


    /**
     * 根据id集合，获取角色列表集合
     * @param idList
     * @return
     */
    List<SysRole> getByIdList(@Param("idList") List<Integer> idList);
    

}