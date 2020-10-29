package com.past.service.impl;

import com.google.common.base.Preconditions;
import com.past.common.RequestHolder;
import com.past.dao.SysAclMapper;
import com.past.dao.SysAclModuleMapper;
import com.past.exception.ParamException;
import com.past.model.SysAclModule;
import com.past.param.AclModuleParam;
import com.past.service.SysAclModuleService;
import com.past.service.SysLogService;
import com.past.util.BeanValidator;
import com.past.util.IpUtil;
import com.past.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {

    @Autowired
    SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    SysAclMapper sysAclMapper;

    @Autowired
    SysLogService sysLogService;


    /**
     * 校验参数、保存权限模块
     * @param aclModuleParam
     */
    @Override
    @Transactional
    public void save(AclModuleParam aclModuleParam) {

        //校验对象参数 是否满足注解约束  不满足约束，抛出ParamException
        BeanValidator.check(aclModuleParam);
        //满足约束，不抛出异常，再判断权限模块是否已存在，不存在时开始组装权限模块类
        if (checkExist(aclModuleParam.getParentId(),aclModuleParam.getName(),aclModuleParam.getId())){
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }

        //校验成功，满足约束，封装成实际的业务对象，调用持久层接口完成保存
        SysAclModule sysAclModule = SysAclModule.builder()
                .name(aclModuleParam.getName())
                .parentId(aclModuleParam.getParentId())
                .seq(aclModuleParam.getSeq())
                .status(aclModuleParam.getStatus())
                .remark(aclModuleParam.getRemark())
                .build();
        sysAclModule.setLevel(LevelUtil.calculateLevel(getLevel(aclModuleParam.getParentId()) , aclModuleParam.getParentId()));
        sysAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysAclModule.setOperateIp(IpUtil.getV4IP()); //获取公网ipv4地址
        sysAclModule.setOperateIp(IpUtil.getLocalIPForCMD()); //获取内网ipv4地址
        sysAclModule.setOperateTime(new Date());

        //TODO：sendEmail

        sysAclModuleMapper.insertSelective(sysAclModule);
        sysLogService.saveAclModuleLog(null, sysAclModule);
    }


    /**
     * 校验参数、更新权限模块
     * @param aclModuleParam
     */
    @Override
    @Transactional
    public void update(AclModuleParam aclModuleParam) {

        //校验对象参数 是否满足注解约束  不满足约束，抛出ParamException
        BeanValidator.check(aclModuleParam);
        //满足约束，不抛出异常，再判断权限模块是否已存在，不存在时开始组装权限模块
        if (checkExist(aclModuleParam.getParentId(),aclModuleParam.getName(),aclModuleParam.getId())){
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }
        //获取待更新的权限模块
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(aclModuleParam.getId());
        //使用google.guava工具包校验待更新的权限模块是否为空
        Preconditions.checkNotNull(before,"待更新的权限模块不存在");//不存在权限模块，抛出异常

        //校验成功，满足约束，封装成实际的业务对象，调用持久层接口完成更新
        SysAclModule after = SysAclModule.builder()
                .name(aclModuleParam.getName())
                .parentId(aclModuleParam.getParentId())
                .seq(aclModuleParam.getSeq())
                .status(aclModuleParam.getStatus())
                .remark(aclModuleParam.getRemark())
                .id(aclModuleParam.getId())
                .build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(aclModuleParam.getParentId()), aclModuleParam.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
//        after.setOperateIp(IpUtil.getV4IP()); //获取公网ipv4地址
        after.setOperateIp(IpUtil.getLocalIPForCMD()); //获取内网ipv4地址
        after.setOperateTime(new Date());

        //更新当前权限模块和当前权限模块的所有子集
        updateWithChild(before, after);
        sysLogService.saveAclModuleLog(before, after);
    }


    /**
     * 更新当前权限模块和当前权限模块的子模块(更新到底)
     * @param before 更新前的权限模块
     * @param after 更新后的权限模块
     */
    @Override
    @Transactional
    public void updateWithChild(SysAclModule before, SysAclModule after) {

        String newLevelPrefix = after.getLevel();//更新后的层级--0.1.3           其直接子节点0.1.3.2
        String oldLevelPrefix = before.getLevel();//更新前的层级--0.1            其直接子节点0.1.2

        //如果更新前后层级前缀不相等，需要更新子节点。否则不更新子节点
        if (!newLevelPrefix.equals(oldLevelPrefix)){
            //获取更新前其下一级权限模块准确的层级全称  --0.1.2
            String curLevel = before.getLevel() + "." + before.getId();
            //获取其下一级模块及其所有子集(获取到底)  获取层级为0.1.2*  如0.1.2、0.1.2...层级的模块
            List<SysAclModule> aclModuleList = sysAclModuleMapper.getChildAclModuleListByLevel(curLevel + "%");
            //判断子集是否为空(获取到底)，不为空则遍历该集合，重新赋值level
            if (CollectionUtils.isNotEmpty(aclModuleList)) {
                for (SysAclModule aclModule : aclModuleList) {
                    String level = aclModule.getLevel();//获取其所有子部门的原层级  --0.1.2
                    if (level.equals(curLevel) || level.indexOf(curLevel + ".") == 0) {
                        // getChildAclModuleListByLevel可能会取出多余的内容，因此需要加个判断
                        // 比如0.1* 可能取出0.1、0.1.3、0.11、0.11.3，而期望取出  0.1、0.1.3， 因此呢需要判断等于0.1或者以0.1.为前缀才满足条件
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());//'0.1.3'+'0.1.2'.substring(3)='0.1.3.2'
                        // level = newLevelPrefix + "." + aclModule.getParentId();//'0.1.3' + '.' + '2'='0.1.3.2'
                        aclModule.setLevel(level);
                    }
                }
                //重新赋值level后  执行批量更新、更新该模块的所有子集
                sysAclModuleMapper.batchUpdateLevel(aclModuleList);
            }
        }
        //调用持久层接口，更新完所有子集后，更新自身
        sysAclModuleMapper.updateByPrimaryKeySelective(after);
    }


    /**
     * 验证权限模块是否在该父级下已存在，存在返回true 抛出异常；不存在返回false，继续执行业务
     * @param parentId
     * @param aclModuleName
     * @param aclModuleId
     * @return
     */
    @Override
    public boolean checkExist(Integer parentId, String aclModuleName, Integer aclModuleId) {

        return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, aclModuleId) > 0;
    }


    /**
     * 根据权限模块id查询数据库，获取权限模块对象的level属性值
     * @param aclModuleId
     * @return
     */
    @Override
    public String getLevel(Integer aclModuleId) {

        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (sysAclModule == null){
            return null;
        }
        return sysAclModule.getLevel();
    }


    /**
     * 根据模块id删除该权限模块
     * @param aclModuleId
     */
    @Override
    @Transactional
    public void delete(int aclModuleId) {

        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        Preconditions.checkNotNull(aclModule, "待删除的权限模块不存在，无法删除");
        if (sysAclModuleMapper.countByParentId(aclModule.getId()) > 0){
            throw new ParamException("当前权限模块下面有子模块，无法删除");
        }
        if (sysAclMapper.countByAclModuleId(aclModule.getId()) > 0){
            throw new ParamException("当前权限模块下面有权限点，无法删除");
        }
        sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
    }


}
