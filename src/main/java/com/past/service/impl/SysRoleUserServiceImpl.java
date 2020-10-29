package com.past.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.past.beans.LogType;
import com.past.common.RequestHolder;
import com.past.dao.SysLogMapper;
import com.past.dao.SysRoleUserMapper;
import com.past.dao.SysUserMapper;
import com.past.model.SysLogWithBLOBs;
import com.past.model.SysRoleUser;
import com.past.model.SysUser;
import com.past.service.SysRoleUserService;
import com.past.util.IpUtil;
import com.past.util.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {

    @Autowired
    SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysLogMapper sysLogMapper;

    /**
     * 根据角色id获取角色分配的所有用户
     * @param roleId
     * @return
     */
    @Override
    public List<SysUser> getUserListByRoleId(int roleId) {

        //根据角色id取出角色分配的所有用户的id
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)){
            return Lists.newArrayList();
        }
        //如果不为空，则根据用户id集合取出所有的用户对象，存入一个集合
        return sysUserMapper.getByIdList(userIdList);
    }


    /**
     * 根据角色id改变该角色下的  角色用户关系(新增、更新、删除)  并保存
     * @param roleId
     * @param userIdList
     */
    @Override
    @Transactional
    public void changeRoleUsers(int roleId, List<Integer> userIdList) {

        //根据roleId取出当前角色之前分配的用户id集合
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        //判断originUserIdList和传入的userIdList是否相同，相同则不需要处理
        if (originUserIdList.size() == userIdList.size()){
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(userIdSet);
            if (CollectionUtils.isEmpty(originUserIdSet)){
                return;
            }
        }
        //更新操作：删除旧用户，增加新用户
        updateRoleUsers(roleId, userIdList);
        saveRoleUserLog(roleId, originUserIdList, userIdList);
    }


    /**
     * 更新角色用户：删除旧用户，增加新用户
     * @param roleId
     * @param userIdList
     */
    @Override
    @Transactional
    public void updateRoleUsers(int roleId, List<Integer> userIdList){

        //根据角色id，删除此角色分配的旧用户
        sysRoleUserMapper.deleteByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)){
            return;
        }
        List<SysRoleUser> roleUserList = Lists.newArrayList();
        for (Integer userId : userIdList) {
            SysRoleUser sysRoleUser = SysRoleUser.builder()
                    .roleId(roleId)
                    .userId(userId)
                    .operator(RequestHolder.getCurrentUser().getUsername())
                    .operateIp(IpUtil.getLocalIPForCMD())
                    .operateTime(new Date())
                    .build();
            roleUserList.add(sysRoleUser);
        }
        //为此角色，批量新增新的角色用户关联关系
        sysRoleUserMapper.batchInsert(roleUserList);
    }


    /**
     * 保存角色用户关联关系的操作日志
     * @param roleId
     * @param before
     * @param after
     */
    @Override
    @Transactional
    public void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {

        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_USER);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.objToString(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.objToString(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getLocalIPForCMD());
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);

        sysLogMapper.insertSelective(sysLog);
    }


}
