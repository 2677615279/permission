package com.past.service.impl;

import com.google.common.base.Preconditions;
import com.past.beans.PageQuery;
import com.past.beans.PageResult;
import com.past.common.RequestHolder;
import com.past.dao.SysAclMapper;
import com.past.exception.ParamException;
import com.past.model.SysAcl;
import com.past.param.AclParam;
import com.past.service.SysAclService;
import com.past.service.SysLogService;
import com.past.util.BeanValidator;
import com.past.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SysAclServiceImpl implements SysAclService {

    @Autowired
    SysAclMapper sysAclMapper;

    @Autowired
    SysLogService sysLogService;


    /**
     * 校验参数、保存权限点
     * @param aclParam
     */
    @Override
    @Transactional
    public void save(AclParam aclParam) {

        //校验对象参数 是否满足注解约束  不满足约束，抛出ParamException
        BeanValidator.check(aclParam);
        //满足约束，不抛出异常，再判断权限点是否已存在，不存在时开始组装权限点
        if (checkExist(aclParam.getAclModuleId(), aclParam.getName(), aclParam.getId())){
            throw new ParamException("当前权限模块下已存在相同名称的权限点");
        }

        //校验成功，满足约束，封装成实际的业务对象，调用持久层接口完成保存
        SysAcl sysAcl = SysAcl.builder()
                .name(aclParam.getName())
                .aclModuleId(aclParam.getAclModuleId())
                .url(aclParam.getUrl())
                .type(aclParam.getType())
                .status(aclParam.getStatus())
                .seq(aclParam.getSeq())
                .remark(aclParam.getRemark())
                .build();
        sysAcl.setCode(generateCode());
        sysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysAcl.setOperateIp(IpUtil.getV4IP());//设置外网ipv4地址
        sysAcl.setOperateIp(IpUtil.getLocalIPForCMD()); //获取内网ipv4地址
        sysAcl.setOperateTime(new Date());

        //TODO：sendEmail

        sysAclMapper.insertSelective(sysAcl);
        sysLogService.saveAclLog(null, sysAcl);
    }


    /**
     * 校验参数、更新权限点
     * @param aclParam
     */
    @Override
    @Transactional
    public void update(AclParam aclParam) {

        //校验对象参数 是否满足注解约束  不满足约束，抛出ParamException
        BeanValidator.check(aclParam);
        //满足约束，不抛出异常，再判断权限点是否已存在，不存在时开始组装权限点
        if (checkExist(aclParam.getAclModuleId(), aclParam.getName(), aclParam.getId())){
            throw new ParamException("当前权限模块下已存在相同名称的权限点");
        }

        //查出待更新的权限、判断是否查到：查不到抛出异常，进入全局异常处理类；查得到，组装更新后的业务对象
        SysAcl before = sysAclMapper.selectByPrimaryKey(aclParam.getId());
        //使用google.guava工具包校验待更新的权限点是否为空
        Preconditions.checkNotNull(before, "待更新的权限点不存在");

        SysAcl after = SysAcl.builder()
                .name(aclParam.getName())
                .aclModuleId(aclParam.getAclModuleId())
                .url(aclParam.getUrl())
                .type(aclParam.getType())
                .status(aclParam.getStatus())
                .seq(aclParam.getSeq())
                .remark(aclParam.getRemark())
                .id(aclParam.getId())
                .build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
//        after.setOperateIp(IpUtil.getV4IP());//设置外网ipv4地址
        after.setOperateIp(IpUtil.getLocalIPForCMD()); //获取内网ipv4地址
        after.setOperateTime(new Date());

        sysAclMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveAclLog(before, after);
    }


    /**
     * 验证权限点是否在该权限模块下已存在，存在返回true 抛出异常；不存在返回false，继续执行业务
     * @param aclModuleId
     * @param aclName
     * @param aclId
     * @return
     */
    @Override
    public boolean checkExist(Integer aclModuleId, String aclName, Integer aclId) {

        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, aclName, aclId) > 0;
    }


    /**
     * 生成权限码
     * @return
     */
    @Override
    public String generateCode(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date()) + "_" + (int)(Math.random() * 100);
    }


    /**
     * 根据权限模块id和分页参数 获取当前模块下的权限点分页列表
     * @param aclModuleId
     * @param pageQuery
     * @return
     */
    @Override
    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery pageQuery) {

        BeanValidator.check(pageQuery);
        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count > 0){
            List<SysAcl> list = sysAclMapper.getPageByAclModuleId(aclModuleId, pageQuery);
            return PageResult.<SysAcl>builder().total(count).data(list).build();
        }
        else {
            return PageResult.<SysAcl>builder().build();
        }
    }


}
