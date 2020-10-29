package com.past.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.past.beans.LogType;
import com.past.common.RequestHolder;
import com.past.dao.SysLogMapper;
import com.past.dao.SysRoleAclMapper;
import com.past.model.SysLogWithBLOBs;
import com.past.model.SysRoleAcl;
import com.past.service.SysRoleAclService;
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
public class SysRoleAclServiceImpl implements SysRoleAclService {

    @Autowired
    SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    SysLogMapper sysLogMapper;


    /**
     * 改变角色权限关系(新增、删除、更新)
     * @param roleId
     * @param aclIdList
     */
    @Override
    @Transactional
    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {

        //根据roleId取出当前角色之前分配的权限id集合
        List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        //判断originAclIdList和传入的aclIdList是否相同，相同则不需要处理
        if (originAclIdList.size() == aclIdList.size()){
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)){
                return;
            }
        }
        //更新操作：删除旧权限，增加新权限
        updateRoleAcls(roleId, aclIdList);
        saveRoleAclLog(roleId, originAclIdList, aclIdList);
    }


    /**
     * 更新角色权限：删除旧权限，增加新权限
     * @param roleId
     * @param aclIdList
     */
    @Override
    @Transactional
    public void updateRoleAcls(int roleId, List<Integer> aclIdList) {

        //根据角色id，删除此角色分配的旧权限
        sysRoleAclMapper.deleteByRoleId(roleId);
        if (CollectionUtils.isEmpty(aclIdList)){
            return;
        }
        List<SysRoleAcl> roleAclList = Lists.newArrayList();
        for (Integer aclId : aclIdList) {
            SysRoleAcl sysRoleAcl = SysRoleAcl.builder()
                    .roleId(roleId)
                    .aclId(aclId)
                    .operator(RequestHolder.getCurrentUser().getUsername())
                    .operateIp(IpUtil.getLocalIPForCMD())
                    .operateTime(new Date())
                    .build();
            roleAclList.add(sysRoleAcl);
        }
        //为此角色，批量新增新权限
        sysRoleAclMapper.batchInsert(roleAclList);
    }


    /**
     * 保存角色权限关联关系的操作日志
     * @param roleId
     * @param before
     * @param after
     */
    @Override
    @Transactional
    public void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {

        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_ACL);
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
