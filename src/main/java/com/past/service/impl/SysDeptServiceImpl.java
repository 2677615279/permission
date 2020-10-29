package com.past.service.impl;

import com.google.common.base.Preconditions;
import com.past.common.RequestHolder;
import com.past.dao.SysDeptMapper;
import com.past.dao.SysUserMapper;
import com.past.exception.ParamException;
import com.past.model.SysDept;
import com.past.param.DeptParam;
import com.past.service.SysDeptService;
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
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    SysDeptMapper sysDeptMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysLogService sysLogService;


    /**
     * 校验参数、保存部门
     * @param deptParam
     */
    @Override
    @Transactional
    public void save(DeptParam deptParam) {

        //校验对象参数 是否满足注解约束  不满足约束，抛出ParamException
        BeanValidator.check(deptParam);
        //满足约束，不抛出异常，再判断部门是否已存在，不存在时开始组装部门类
        if (checkExist(deptParam.getParentId(),deptParam.getName(),deptParam.getId())){
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        //校验成功，满足约束，封装成实际的业务对象，调用持久层接口完成保存
        SysDept sysDept = SysDept.builder()
                .name(deptParam.getName())
                .parentId(deptParam.getParentId())
                .seq(deptParam.getSeq())
                .remark(deptParam.getRemark())
                .build();
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()) , deptParam.getParentId()));
        sysDept.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysDept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())); //获取公网ipv6地址 IPv6：0:0:0:0:0:0:0:1
        sysDept.setOperateIp(IpUtil.getLocalIPForCMD()); //通过cmd获取内网ipv4地址
//        sysDept.setOperateIp(IpUtil.getV4IP()); //获取公网ipv4地址
        sysDept.setOperateTime(new Date());

        //TODO：sendEmail

        sysDeptMapper.insertSelective(sysDept);
        sysLogService.saveDeptLog(null, sysDept);
    }


    /**
     * 校验参数、更新部门
     * @param deptParam
     */
    @Override
    @Transactional
    public void update(DeptParam deptParam) {

        //校验对象参数 是否满足注解约束  不满足约束，抛出ParamException
        BeanValidator.check(deptParam);
        //满足约束，不抛出异常，再判断部门是否已存在，不存在时开始组装部门类
        if (checkExist(deptParam.getParentId(),deptParam.getName(),deptParam.getId())){
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        //获取待更新的部门
        SysDept before = sysDeptMapper.selectByPrimaryKey(deptParam.getId());
        //使用google.guava工具包校验待更新的部门是否为空
        Preconditions.checkNotNull(before,"待更新的部门不存在");//不存在部门，抛出异常

        //校验成功，满足约束，封装成实际的业务对象，调用持久层接口完成更新
        SysDept after = SysDept.builder()
                .id(deptParam.getId())
                .name(deptParam.getName())
                .parentId(deptParam.getParentId())
                .seq(deptParam.getSeq())
                .remark(deptParam.getRemark())
                .build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
//        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())); //获取公网ipv6地址 IPv6：0:0:0:0:0:0:0:1
        after.setOperateIp(IpUtil.getLocalIPForCMD()); //通过cmd获取内网ipv4地址
//        after.setOperateIp(IpUtil.getV4IP()); //获取公网ipv4地址
        after.setOperateTime(new Date());

        //更新当前部门和当前部门的所有子集
        updateWithChild(before, after);
        sysLogService.saveDeptLog(before, after);
    }


    /**
     * 更新当前部门和当前部门的子部门
     * @param before 更新前的部门
     * @param after 更新后的部门
     */
    @Override
    @Transactional
    public void updateWithChild(SysDept before, SysDept after) {

        /*
        模拟场景  技术部为部门树根节点、一级子节点有后端和前端、二级子节点有python和java(是后端的直接子节点)
        更新前
        id  name   parent_id    level
        1  技术部    0            0
        2  后端      1            0.1
        3  前端      1            0.1
        4  python    2            0.1.2
        5  java      2            0.1.2

        更新id为2的后端、将其作为前端的直接子节点-->后端和后端的子节点python java都要更新
        更新后
        id  name   parent_id    level
        1  技术部    0            0
        2  后端      3            0.1.3
        3  前端      1            0.1
        4  python    2            0.1.3.2
        5  java      2            0.1.3.2
        */

        String newLevelPrefix = after.getLevel();//更新后的层级--0.1.3           其直接子节点0.1.3.2
        String oldLevelPrefix = before.getLevel();//更新前的层级--0.1            其直接子节点0.1.2

        //如果更新前后层级前缀不相等，需要更新子部门。否则不更新子部门
        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            //获取更新前其下一级部门准确的层级全称  --0.1.2
            String curLevel = before.getLevel() + "." + before.getId();
            //获取其下一级部门及下一级部门的子部门及子部门的子集(获取到底)  获取层级为0.1.2*  如0.1.2、0.1.2...层级的部门
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(curLevel + "%");
            //判断其部门的所有子部门(获取到底)，不为空则遍历该集合，重新赋值level
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();//获取其所有子部门的原层级  --0.1.2
                    if (level.equals(curLevel) || level.indexOf(curLevel + ".") == 0) {
                        // getChildAclModuleListByLevel可能会取出多余的内容，因此需要加个判断
                        // 比如0.1* 可能取出0.1、0.1.3、0.11、0.11.3，而期望取出  0.1、0.1.3， 因此呢需要判断等于0.1或者以0.1.为前缀才满足条件
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());//'0.1.3'+'0.1.2'.substring(3)='0.1.3.2'
                        // level = newLevelPrefix + "." + dept.getParentId();//'0.1.3' + '.' + '2'='0.1.3.2'
                        dept.setLevel(level);
                    }
                }
                //重新赋值level后  执行批量更新、更新该部门的所有子集
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        //调用持久层接口，更新完所有子集后，更新自身
        sysDeptMapper.updateByPrimaryKeySelective(after);
    }



    /**
     * 验证部门是否在该父级下已存在  新增时deptId传空值；更新时deptId传实际部门id，排除当前部门
     * @param parentId
     * @param deptName
     * @param deptId
     * @return
     */
    @Override
    public boolean checkExist(Integer parentId, String deptName, Integer deptId) {

        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }


    /**
     * 根据部门id查询数据库，获取部门对象的level属性值
     * @param deptId
     * @return
     */
    @Override
    public String getLevel(Integer deptId){

        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (sysDept == null){
            return null;
        }
        return sysDept.getLevel();
    }


    /**
     * 根据部门id删除该部门
     * @param deptId
     */
    @Override
    @Transactional
    public void delete(int deptId) {

        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        Preconditions.checkNotNull(dept, "待删除的部门不存在，无法删除");
        if (sysDeptMapper.countByParentId(dept.getId()) > 0){
            throw new ParamException("当前部门下面有子部门，无法删除");
        }
        if (sysUserMapper.countByDeptId(dept.getId()) > 0){
            throw new ParamException("当前部门下面有用户，无法删除");
        }
        sysDeptMapper.deleteByPrimaryKey(deptId);
    }


}
