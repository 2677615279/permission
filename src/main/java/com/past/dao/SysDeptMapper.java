package com.past.dao;

import com.past.model.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysDeptMapper {


    /**
     * 根据主键id删除部门
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);


    /**
     * 保存部门(所有的字段都会添加一遍即使没有值)
     * @param record
     * @return
     */
    int insert(SysDept record);


    /**
     * 保存部门,只给有值的字段赋值,会对传进来的值做非空判断
     * @param record
     * @return
     */
    int insertSelective(SysDept record);


    /**
     * 根据主键id查询部门
     * @param id
     * @return
     */
    SysDept selectByPrimaryKey(Integer id);


    /**
     * 更新部门 会对字段进行判断再更新(如果为Null就忽略更新)，如果你只想更新某一字段，可以用这个方法。
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysDept record);


    /**
     * 更新部门 对你注入的字段全部更新，如果有字段不更新，数据库的值就为null
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysDept record);


    /**
     * 获取所有部门
     * @return
     */
    List<SysDept> getAllDept();


    /**
     * 根据层级获取子部门
     * @param level
     * @return
     */
    List<SysDept> getChildDeptListByLevel(@Param("level") String level);


    /**
     * 批量更新层级
     * @param sysDeptList
     */
    void batchUpdateLevel(@Param("sysDeptList") List<SysDept> sysDeptList);


    /**
     * 根据父id和其id name查询部门的数目
     * @param parentId
     * @param name
     * @param id
     * @return
     */
    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);


    /**
     * 根据父id查询该id所对部门的数目
     * @param deptId
     * @return
     */
    int countByParentId(@Param("deptId") int deptId);


}