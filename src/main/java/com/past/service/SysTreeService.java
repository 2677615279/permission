package com.past.service;

import com.google.common.collect.Multimap;
import com.past.dto.AclDto;
import com.past.dto.AclModuleLevelDto;
import com.past.dto.DeptLevelDto;

import java.util.List;

/**
 * 层级树接口
 */
public interface SysTreeService {


    /**
     * 查询数据库全部部门 适配成DeptLevelDto对象 存入一个集合  生成递归部门树
     * @return
     */
    List<DeptLevelDto> deptTree();


    /**
     * 对部门排序，返回根部门下的所有部门  组成一颗部门树
     * @param deptLevelList 参与组成部门树的 存储DeptLevelDto对象的 集合
     * @return
     */
    List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList);


    /**
     * 递归排列部门
     * @param currentLevelList 存储当前层级下的所有DeptLevelDto对象的集合
     * @param level  当前层级
     * @param levelDeptMap  存储<level,该level下的DeptLevelDto部门对象>的map集合
     */
    void transformDeptTree(List<DeptLevelDto> currentLevelList, String level, Multimap<String,DeptLevelDto> levelDeptMap);


    /**
     * 查询数据库全部权限模块 适配成AclModuleLevelDto对象 存入一个集合  生成递归权限模块树
     * @return
     */
    List<AclModuleLevelDto> aclModuleTree();


    /**
     * 对权限模块排序，返回根模块下的所有子集  组成一颗权限模块树
     * @param aclModuleLevelList 参与组成权限模块树的 存储AclModuleLevelDto对象的 集合
     * @return
     */
    List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> aclModuleLevelList);


    /**
     * 递归排列权限模块
     * @param currentLevelList 存储当前层级下的所有AclModuleLevelDto对象的集合
     * @param level  当前层级
     * @param levelAclModuleMap  存储<level,该level下的AclModuleLevelDto部门对象>的map集合
     */
    void transformAclModuleTree(List<AclModuleLevelDto> currentLevelList, String level, Multimap<String,AclModuleLevelDto> levelAclModuleMap);


    /**
     * 角色权限树：取出权限模块和权限点，组成一棵树，根据角色id，将权限挂到权限模块上，判断其是否被选中是否可操作，即为权限添加标记
     * @param roleId
     * @return
     */
    List<AclModuleLevelDto> roleTree(int roleId);


    /**
     * 将被标记好的权限集合 转换成 当前角色下的 权限模块树和每个模块下的权限树
     * @param aclDtoList
     * @return
     */
    List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList);


    /**
     * 将权限点有顺序的绑定到权限模块树上
     * @param aclModuleLevelList
     * @param moduleIdAclMap
     */
    void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList, Multimap<Integer, AclDto> moduleIdAclMap);


    /**
     * 根据用户id，获取用户权限树
     * @param userId
     * @return
     */
    List<AclModuleLevelDto> userAclTree(int userId);


}
