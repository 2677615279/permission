package com.past.service.impl;

import com.google.common.base.Preconditions;
import com.past.beans.PageQuery;
import com.past.beans.PageResult;
import com.past.common.RequestHolder;
import com.past.dao.SysUserMapper;
import com.past.exception.ParamException;
import com.past.model.SysUser;
import com.past.param.UserParam;
import com.past.service.SysLogService;
import com.past.service.SysUserService;
import com.past.util.BeanValidator;
import com.past.util.IpUtil;
import com.past.util.MD5Util;
import com.past.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysLogService sysLogService;


    /**
     * 校验参数、保存用户
     * @param userParam
     */
    @Override
    @Transactional
    public void save(UserParam userParam) {

        //校验对象参数 是否满足注解约束  不满足约束，抛出ParamException
        BeanValidator.check(userParam);
        //满足约束，继续执行业务，判断邮箱和手机号是否被已存在且被占用
        if (checkTelephoneExist(userParam.getTelephone(), userParam.getId())){
            throw new ParamException("手机号已存在且被他人占用，请更换手机号");
        }
        if (checkMailExist(userParam.getMail(), userParam.getId())){
            throw new ParamException("邮箱已存在且被他人占用，请更换邮箱");
        }

        //校验成功，满足约束，封装成实际的业务对象，调用持久层接口完成保存
        String password = PasswordUtil.randomPassword();
        password = "12345678";
        String encryptedPassword = MD5Util.encrypt(password);//加密后的密码
        SysUser sysUser = SysUser.builder()
                .username(userParam.getUsername())
                .telephone(userParam.getTelephone())
                .mail(userParam.getMail())
                .deptId(userParam.getDeptId())
                .status(userParam.getStatus())
                .remark(userParam.getRemark())
                .password(encryptedPassword)
                .build();
        sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())); //获取公网ipv6地址 IPv6：0:0:0:0:0:0:0:1
        sysUser.setOperateIp(IpUtil.getLocalIPForCMD()); //通过cmd获取内网ipv4地址
//        sysUser.setOperateIp(IpUtil.getV4IP()); //获取公网ipv4地址
        sysUser.setOperateTime(new Date());

        //TODO：sendEmail

        sysUserMapper.insertSelective(sysUser);
        sysLogService.saveUserLog(null, sysUser);
    }


    /**
     * 校验参数、更新用户
     * @param userParam
     */
    @Override
    @Transactional
    public void update(UserParam userParam){

        //校验对象参数 是否满足注解约束  不满足约束，抛出ParamException
        BeanValidator.check(userParam);
        //满足约束，继续执行业务，判断邮箱和手机号是否被已存在且被占用
        if (checkTelephoneExist(userParam.getTelephone(), userParam.getId())){
            throw new ParamException("手机号已存在且被他人占用，请更换手机号");
        }
        if (checkMailExist(userParam.getMail(), userParam.getId())){
            throw new ParamException("邮箱已存在且被他人占用，请更换邮箱");
        }

        //获取待更新的用户
        SysUser before = sysUserMapper.selectByPrimaryKey(userParam.getId());
        //使用google.guava工具包校验待更新的用户是否为空
        Preconditions.checkNotNull(before,"待更新的用户不存在");//不存在用户，抛出异常

        //校验成功，满足约束，封装成实际的业务对象，调用持久层接口完成更新、不更新原用户的密码
        SysUser after = SysUser.builder()
                .id(userParam.getId())
                .username(userParam.getUsername())
                .telephone(userParam.getTelephone())
                .mail(userParam.getMail())
                .deptId(userParam.getDeptId())
                .status(userParam.getStatus())
                .remark(userParam.getRemark())
                .build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
//        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())); //获取公网ipv6地址 IPv6：0:0:0:0:0:0:0:1
        after.setOperateIp(IpUtil.getLocalIPForCMD()); //通过cmd获取内网ipv4地址
//        after.setOperateIp(IpUtil.getV4IP()); //获取公网ipv4地址
        after.setOperateTime(new Date());

        sysUserMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveUserLog(before, after);
    }

    /**
     * 判断该用户的邮箱是否存在，存在返回true 抛出异常；不存在返回false，继续执行业务
     * @param mail
     * @param userId
     * @return
     */
    @Override
    public boolean checkMailExist(String mail, Integer userId) {

        return sysUserMapper.countByMail(mail, userId) > 0;
    }


    /**
     * 判断该用户的手机号是否存在，存在返回true 抛出异常；不存在返回false，继续执行业务
     * @param telephone
     * @param userId
     * @return
     */
    @Override
    public boolean checkTelephoneExist(String telephone, Integer userId) {

        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }


    /**
     * 根据关键词(邮箱或手机号)查询用户
     * @param keyword
     * @return
     */
    @Override
    public SysUser findByKeyword(String keyword) {

        return sysUserMapper.findByKeyword(keyword);
    }


    /**
     * 根据部门id和分页参数 获取分页结果
     * @param deptId
     * @param pageQuery
     * @return
     */
    @Override
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery pageQuery) {

        BeanValidator.check(pageQuery);
        int count = sysUserMapper.countByDeptId(deptId);
        if (count > 0){
            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, pageQuery);
            return PageResult.<SysUser>builder().total(count).data(list).build();
        }
        else {
            return PageResult.<SysUser>builder().build();
        }
    }


    /**
     * 获取所有用户
     * @return
     */
    @Override
    public List<SysUser> getAll() {

        return sysUserMapper.getAll();
    }


}
