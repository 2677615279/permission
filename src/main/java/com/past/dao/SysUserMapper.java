package com.past.dao;

import com.past.beans.PageQuery;
import com.past.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysUserMapper {

    
    /**
     * 根据主键id删除用户
     * @param userId
     * @return
     */
    int deleteByPrimaryKey(Integer userId);

    
    /**
     * 保存用户(所有的字段都会添加一遍即使没有值)
     * @param record
     * @return
     */
    int insert(SysUser record);


    /**
     * 保存用户,只给有值的字段赋值,会对传进来的值做非空判断
     * @param record
     * @return
     */
    int insertSelective(SysUser record);


    /**
     * 根据主键id查询用户
     * @param userId
     * @return
     */
    SysUser selectByPrimaryKey(Integer userId);


    /**
     * 更新用户 会对字段进行判断再更新(如果为Null就忽略更新)，如果你只想更新某一字段，可以用这个方法。
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysUser record);


    /**
     * 更新用户 对你注入的字段全部更新，如果有字段不更新，数据库的值就为null
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysUser record);


    /**
     * 根据关键词(邮箱或手机号)查询用户
     * @param keyword
     * @return
     */
    SysUser findByKeyword(@Param("keyword") String keyword);


    /**
     * 根据id和邮箱查询的记录数
     * @param mail
     * @param id
     * @return
     */
    int countByMail(@Param("mail") String mail, @Param("id") Integer id);


    /**
     * 根据id和手机号查询的记录数
     * @param telephone
     * @param id
     * @return
     */
    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);


    /**
     * 根据部门id查询该部门下的用户数目
     * @param deptId
     * @return
     */
    int countByDeptId(@Param("deptId") int deptId);


    /**
     * 根据分页参数和部门id，查询该部门下所有用户的实际分页结果
     * @param deptId
     * @param page
     * @return
     */
    List<SysUser> getPageByDeptId(@Param("deptId") int deptId, @Param("page") PageQuery page);


    /**
     * 根据用户id列表取出对应的用户对象，存入一个集合
     * @param idList
     * @return
     */
    List<SysUser> getByIdList(@Param("idList") List<Integer> idList);


    /**
     * 查询所有用户信息
     * @return
     */
    List<SysUser> getAll();
    

}