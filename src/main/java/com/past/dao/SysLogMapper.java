package com.past.dao;

import com.past.beans.PageQuery;
import com.past.dto.SearchLogDto;
import com.past.model.SysLog;
import com.past.model.SysLogWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysLogMapper {


    /**
     * 根据主键id删除日志
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);


    /**
     * 保存日志(所有的字段都会添加一遍即使没有值)
     * @param record
     * @return
     */
    int insert(SysLogWithBLOBs record);


    /**
     * 保存日志,只给有值的字段赋值,会对传进来的值做非空判断
     * @param record
     * @return
     */
    int insertSelective(SysLogWithBLOBs record);


    /**
     * 根据主键id查询日志
     * @param id
     * @return
     */
    SysLogWithBLOBs selectByPrimaryKey(Integer id);


    /**
     * 更新日志 会对字段进行判断再更新(如果为Null就忽略更新)，如果你只想更新某一字段，可以用这个方法。
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysLogWithBLOBs record);


    /**
     * 更新日志 会对字段进行判断再更新(如果为Null就忽略更新)，如果你只想更新某一字段，可以用这个方法。
     * @param record
     * @return
     */
    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);


    /**
     * 更新日志 对你注入的字段全部更新，如果有字段不更新，数据库的值就为null
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysLog record);


    /**
     * 根据查询参数对象 查询符合条件的日志数目
     * @param dto
     * @return
     */
    int countBySearchDto(@Param("dto") SearchLogDto dto);


    /**
     * 根据查询参数对象 获取分页列表
     * @param dto
     * @param page
     * @return
     */
    List<SysLogWithBLOBs> getPageListBySearchDto(@Param("dto") SearchLogDto dto, @Param("page") PageQuery page);


}