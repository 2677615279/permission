package com.past.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.past.common.RequestHolder;
import com.past.dao.SysRoleAclMapper;
import com.past.dao.SysRoleMapper;
import com.past.dao.SysRoleUserMapper;
import com.past.dao.SysUserMapper;
import com.past.exception.ParamException;
import com.past.model.SysRole;
import com.past.model.SysUser;
import com.past.param.RoleParam;
import com.past.service.SysLogService;
import com.past.service.SysRoleService;
import com.past.util.BeanValidator;
import com.past.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysLogService sysLogService;


    /**
     * 校验参数、保存角色
     * @param roleParam
     */
    @Override
    @Transactional
    public void save(RoleParam roleParam) {

        //校验对象参数 是否满足注解约束  不满足约束，抛出ParamException
        BeanValidator.check(roleParam);
        //满足约束，不抛出异常，再判断角色是否已存在，不存在时开始组装角色类
        if (checkExist(roleParam.getName(), roleParam.getId())){
            throw new ParamException("角色名称已经存在");
        }

        //校验成功，满足约束，封装成实际的业务对象，调用持久层接口完成保存
        SysRole sysRole = SysRole.builder()
                .name(roleParam.getName())
                .type(roleParam.getType())
                .status(roleParam.getStatus())
                .remark(roleParam.getRemark())
                .build();
        sysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysRole.setOperateIp(IpUtil.getLocalIPForCMD());
        sysRole.setOperateTime(new Date());

        //TODO:

        sysRoleMapper.insertSelective(sysRole);
        sysLogService.saveRoleLog(null, sysRole);
    }


    /**
     * 校验参数、更新权角色
     * @param roleParam
     */
    @Override
    @Transactional
    public void update(RoleParam roleParam) {

        //校验对象参数 是否满足注解约束  不满足约束，抛出ParamException
        BeanValidator.check(roleParam);
        //满足约束，不抛出异常，再判断角色是否已存在，不存在时开始组装角色类
        if (checkExist(roleParam.getName(), roleParam.getId())){
            throw new ParamException("角色名称已经存在");
        }

        //获取待更新的角色
        SysRole before = sysRoleMapper.selectByPrimaryKey(roleParam.getId());
        //使用google.guava工具包校验待更新的角色是否为空
        Preconditions.checkNotNull(before, "待更新的角色不存在");

        //校验成功，满足约束，封装成实际的业务对象，调用持久层接口完成保存
        SysRole after = SysRole.builder()
                .name(roleParam.getName())
                .type(roleParam.getType())
                .status(roleParam.getStatus())
                .remark(roleParam.getRemark())
                .id(roleParam.getId())
                .build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getLocalIPForCMD());
        after.setOperateTime(new Date());

        sysRoleMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveRoleLog(before, after);
    }


    /**
     * 验证角色名称是否已存在，存在返回true 抛出异常；不存在返回false，继续执行业务
     * @param name
     * @param id
     * @return
     */
    @Override
    public boolean checkExist(String name, Integer id) {

        return sysRoleMapper.countByName(name, id) > 0;
    }


    /**
     * 查询获取所有角色
     * @return
     */
    @Override
    public List<SysRole> getAll() {

        return sysRoleMapper.getAll();
    }


    /**
     * 根据用户id获取分配该用户的所有角色
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> getRoleListByUserId(int userId){

        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)){
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIdList);
    }


    /**
     * 根据权限id获取分配该权限的所有角色
     * @param aclId
     * @return
     */
    @Override
    public List<SysRole> getRoleListByAclId(int aclId) {

        List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)){
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIdList);
    }


    /**
     * 根据角色列表 查询每个角色所分配的用户列表
     * @param roleList
     * @return
     */
    @Override
    public List<SysUser> getUserListByRoleList(List<SysRole> roleList) {

        if (CollectionUtils.isEmpty(roleList)){
            return Lists.newArrayList();
        }
        //使用stream流，取出角色集合中每个角色的id，组成一个新集合
        List<Integer> roleIdList = roleList.stream().map(roleItem -> roleItem.getId()).collect(Collectors.toList());
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(userIdList)){//如果这些角色都没有分配用户
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }


}