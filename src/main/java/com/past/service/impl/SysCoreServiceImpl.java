package com.past.service.impl;

import com.google.common.collect.Lists;
import com.past.beans.CacheKeyConstants;
import com.past.common.RequestHolder;
import com.past.dao.SysAclMapper;
import com.past.dao.SysRoleAclMapper;
import com.past.dao.SysRoleUserMapper;
import com.past.model.SysAcl;
import com.past.model.SysUser;
import com.past.service.SysCacheService;
import com.past.service.SysCoreService;
import com.past.util.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色用户、角色权限 的业务
 */
@Service
public class SysCoreServiceImpl implements SysCoreService {

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    SysCacheService sysCacheService;


    /**
     * 获取当前已登录的用户分配的权限列表
     * @return
     */
    @Override
    public List<SysAcl> getCurrentUserAclList() {

        //获取当前登录用户的id
        Integer userId = RequestHolder.getCurrentUser().getId();
        //根据id获取当前用户分配的所有权限
        return getUserAclList(userId);
    }


    /**
     * 根据角色id获取角色已分配的权限点列表
     * @param roleId
     * @return
     */
    @Override
    public List<SysAcl> getRoleAclList(int roleId) {

        //根据角色获取此角色分配的所有权限点id --> 已分配则根据所有权限点id查询所有权限点对象信息
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)){
            return new ArrayList<>();
        }
        return sysAclMapper.getByIdList(aclIdList);
    }


    /**
     * 根据用户id获取用户已分配的权限点列表
     * @param userId
     * @return
     */
    @Override
    public List<SysAcl> getUserAclList(int userId) {

        //如果是超级管理员，查询获取所有权限点
        if (isSuperAdmin()){
            return sysAclMapper.getAll();
        }

        //如果不是超级管理员，获取当前用户分配的角色id集合
        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        //如果当前用户没有分配任何角色
        if (CollectionUtils.isEmpty(roleIdList)){
            return new ArrayList<>();
        }
        //如果当前用户已分配了角色-->获取其角色id列表所对应的权限id列表-->即获取当前用户所分配的权限id列表
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(aclIdList)){
            return new ArrayList<>();
        }
        return sysAclMapper.getByIdList(aclIdList); //根据权限点id集合，查询所对应的权限点集合
    }


    /**
     * 判断是否是超级管理员
     * @return
     */
    @Override
    public boolean isSuperAdmin() {

        // 这里是我自己定义了一个假的超级管理员规则，实际中要根据项目进行修改
        // 可以是配置文件获取，可以指定某个用户，也可以指定某个角色
        SysUser sysUser = RequestHolder.getCurrentUser();
        return sysUser.getMail().contains("admin");
    }


    /**
     * 是否具有访问该url的权限
     * @param url
     * @return
     */
    @Override
    public boolean hasUrlAcl(String url) {

        if (isSuperAdmin()){
            return true;
        }
        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(aclList)){
            return true;
        }

        List<SysAcl> userAclList = getCurrentUserAclListFromCache();//获取当前已登录的用户分配的权限列表
        //使用stream流 将需要校验的权限点的列表  抽取其id组成一个新集合
        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        boolean hasValidAcl = false;//是否具有 有效的权限点

        //规则：只要有一个权限点有权限，则认为该用户有访问权限
        for (SysAcl acl : aclList) {
            //判断用户是否具有某个权限点的访问权限
            if (acl == null || acl.getStatus() != 1){//权限点无效
                continue;
            }
            hasValidAcl = true;
            if (userAclIdSet.contains(acl.getId())){
                return true;
            }
        }

        //如果没有可以校验的权限点-->权限点都无效
        if (!hasValidAcl){
            return true;
        }

        return false;
    }


    /**
     * 从缓存中获取当前已登录的用户分配的权限列表
     * @return
     */
    @Override
    public List<SysAcl> getCurrentUserAclListFromCache() {

        int id = RequestHolder.getCurrentUser().getId();//获取当前登录用户的id
        String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS, String.valueOf(id));//从缓存中取数据
        if (StringUtils.isBlank(cacheValue)){ //当从缓存中获取不到该数据
            List<SysAcl> aclList = getCurrentUserAclList(); //查询数据库得到当前登录用户分配的所有权限
            if (CollectionUtils.isNotEmpty(aclList)){ //如果查询数据库得到当前登录用户分配的所有权限不为空，则将其写入缓存
                sysCacheService.saveCache(JsonMapper.objToString(aclList), 600, CacheKeyConstants.USER_ACLS, String.valueOf(id));
            }
            return aclList;
        }

        //如果缓存中有用户的权限数据、将其转换为List<SysAcl>形式返回
        return JsonMapper.stringToObj(cacheValue, new TypeReference<List<SysAcl>>() {});
    }


}
