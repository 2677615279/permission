package com.past.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.past.dao.SysAclMapper;
import com.past.dao.SysAclModuleMapper;
import com.past.dao.SysDeptMapper;
import com.past.dto.AclDto;
import com.past.dto.AclModuleLevelDto;
import com.past.dto.DeptLevelDto;
import com.past.model.SysAcl;
import com.past.model.SysAclModule;
import com.past.model.SysDept;
import com.past.service.SysCoreService;
import com.past.service.SysTreeService;
import com.past.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysTreeServiceImpl implements SysTreeService {

    @Autowired
    SysDeptMapper sysDeptMapper;

    @Autowired
    SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    SysCoreService sysCoreService;

    @Autowired
    SysAclMapper sysAclMapper;


    /**
     * 查询数据库全部部门 适配成DeptLevelDto对象 存入一个集合  生成递归部门树
     * @return
     */
    @Override
    public List<DeptLevelDto> deptTree() {

        List<SysDept> deptList = sysDeptMapper.getAllDept();
        List<DeptLevelDto> dtoList = new ArrayList<>();
        deptList.forEach(dept->{
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        });
        return deptListToTree(dtoList);
    }


    /**
     * 对部门排序，返回根部门和其下的所有部门  组成一颗部门树
     * @param deptLevelList 参与组成部门树的 存储DeptLevelDto对象的 集合
     * @return
     */
    @Override
    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList){

        if (CollectionUtils.isEmpty(deptLevelList)){
            return new ArrayList<>();
        }
        //key :level   value: 相同level下的部门  level->[dept1,dept2...]
        Multimap<String,DeptLevelDto> levelDeptMap = ArrayListMultimap.create();

        //从根到底的部门集合
        List<DeptLevelDto> rootList = new ArrayList<>();

        for (DeptLevelDto dto : deptLevelList) {
            levelDeptMap.put(dto.getLevel(),dto);
            //如果当前部门的顶级部门
            if (LevelUtil.ROOT.equals(dto.getLevel())){
                rootList.add(dto);
            }
        }

        //排序rootList 按照seq从小到大排序
        rootList.sort(deptSeqComparator);
        //对排序好的rootList下的子部门集合 进行递归排序  递归生成树
        transformDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);

        //返回一颗从根到底的部门树
        return rootList;
    }


    /**
     * 递归排列部门
     * @param currentLevelList 存储当前层级下的所有DeptLevelDto对象及其所有子集的集合
     * @param level  当前层级
     * @param levelDeptMap  存储<level,该level下的DeptLevelDto部门对象>的map集合
     */
    @Override
    public void transformDeptTree(List<DeptLevelDto> currentLevelList, String level, Multimap<String,DeptLevelDto> levelDeptMap){

        for (int i = 0; i < currentLevelList.size() ; i++) {
            //遍历该层的每个元素
            DeptLevelDto dto = currentLevelList.get(i);
            //计算该集合当前所处层级的下一级的level
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            //通过下一级level得到下一级level的部门集合
            List<DeptLevelDto> nextLevelList  = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            //如果下一级子部门集合不为空
            if (CollectionUtils.isNotEmpty(nextLevelList )){
                //排序
                nextLevelList .sort(deptSeqComparator);
                //设置下一级部门
                dto.setDeptList(nextLevelList);
                //进入到下一层继续递归
                transformDeptTree(nextLevelList, nextLevel, levelDeptMap);
            }
        }
    }


    /**
     * 查询数据库全部权限模块 适配成AclModuleLevelDto对象 存入一个集合  生成递归权限模块树
     * @return
     */
    @Override
    public List<AclModuleLevelDto> aclModuleTree() {

        List<SysAclModule> aclModuleList = sysAclModuleMapper.getAllAclModule();
        List<AclModuleLevelDto> dtoList = new ArrayList<>();
        aclModuleList.forEach(aclModule->{
            AclModuleLevelDto dto = AclModuleLevelDto.adapt(aclModule);
            dtoList.add(dto);
        });
        return aclModuleListToTree(dtoList);
    }


    /**
     * 对权限模块排序，返回根模块下的所有子集  组成一颗权限模块树
     * @param aclModuleLevelList 参与组成权限模块树的 存储AclModuleLevelDto对象的 集合
     * @return
     */
    @Override
    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> aclModuleLevelList) {

        if (CollectionUtils.isEmpty(aclModuleLevelList)){
            return new ArrayList<>();
        }
        //key :level   value: 相同level下的权限模块  level->[aclModule1,aclModule2...]
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();

        //从根到底的权限模块集合
        List<AclModuleLevelDto> rootList = new ArrayList<>();
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            levelAclModuleMap.put(dto.getLevel(), dto);
            //如果当前权限模块的顶级模块
            if (LevelUtil.ROOT.equals(dto.getLevel())){
                rootList.add(dto);
            }
        }

        //排序rootList 按照seq从小到大排序
        rootList.sort(aclModuleSeqComparator);
        //对排序好的rootList下的子模块集合 进行递归排序  递归生成树
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);

        //返回一颗从根到底的权限模块树
        return rootList;
    }


    /**
     * 递归排列权限模块
     * @param currentLevelList 存储当前层级下的所有AclModuleLevelDto对象的集合
     * @param level  当前层级
     * @param levelAclModuleMap  存储<level,该level下的AclModuleLevelDto部门对象>的map集合
     */
    @Override
    public void transformAclModuleTree(List<AclModuleLevelDto> currentLevelList, String level, Multimap<String, AclModuleLevelDto> levelAclModuleMap) {

        for (int i = 0; i < currentLevelList.size(); i++) {
            //遍历该层的每个元素
            AclModuleLevelDto dto = currentLevelList.get(i);
            //计算该集合当前所处层级的下一级level
            String nextLevel = LevelUtil.calculateLevel(level ,dto.getId());
            //通过下一级level得到下一级level的权限模块集合
            List<AclModuleLevelDto> nextLevelList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            //如果下一级子模块集合不为空
            if (CollectionUtils.isNotEmpty(nextLevelList)){
                //排序
                nextLevelList.sort(aclModuleSeqComparator);
                //设置其下一层的直接子集
                dto.setAclModuleList(nextLevelList);
                //递归下一层
                transformAclModuleTree(nextLevelList, nextLevel, levelAclModuleMap);
            }
        }
    }


    /**
     * 角色权限树：取出权限模块和权限点，组成一棵树，根据角色id，将权限挂到权限模块上，判断其是否被选中是否可操作，即为权限添加标记
     * @param roleId
     * @return
     */
    @Override
    public List<AclModuleLevelDto> roleTree(int roleId) {

        //1.当前用户已分配的权限点
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        //2.当前角色分配的权限点
        List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);
        //3.当前系统所有的权限点
        List<AclDto> aclDtoList = Lists.newArrayList();

        //当前用户已分配的权限点id集合
        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        //当前角色已分配的权限点id集合
        Set<Integer> roleAclIdSet = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());

        List<SysAcl> allAclList = sysAclMapper.getAll(); //查询数据库获取表中所有的权限点
        //遍历数据库查询结果，为权限点添加标记，再将其存入当前系统所有的权限点集合aclDtoList中
        allAclList.forEach(sysAcl -> {
            AclDto dto = AclDto.adapt(sysAcl);
            if (userAclIdSet.contains(sysAcl.getId())){
                dto.setHasAcl(true);
            }
            if (roleAclIdSet.contains(sysAcl.getId())){
                dto.setChecked(true);
            }
            aclDtoList.add(dto);
        });

        return aclListToTree(aclDtoList); //将被标记好的权限集合 转换成 当前角色下的 权限模块树和每个模块下的权限树
    }


    /**
     * 将被标记好的权限集合 转换成 当前角色下的 权限模块树和每个模块下的权限树
     * @param aclDtoList
     * @return
     */
    @Override
    public List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList) {

        if (CollectionUtils.isEmpty(aclDtoList)){
            return Lists.newArrayList();
        }

        List<AclModuleLevelDto> aclModuleLevelList = aclModuleTree();//获取权限模块树
        //map 每一个权限模块下的可用的权限点列表
        Multimap<Integer, AclDto> moduleIdAclMap = ArrayListMultimap.create();
        aclDtoList.forEach(acl -> {
            //如果该权限状态为可用
            if (acl.getStatus() == 1){
                moduleIdAclMap.put(acl.getAclModuleId(), acl); //将有效的权限点存储在权限模块id下
            }
        });

        //将权限点有顺序的绑定到权限模块树上
        bindAclsWithOrder(aclModuleLevelList, moduleIdAclMap);
        return aclModuleLevelList;//返回绑定好的权限模块树、每个模块下都绑定着可用的权限点
    }


    /**
     * 将权限点有顺序的绑定到权限模块树上
     * @param aclModuleLevelList
     * @param moduleIdAclMap
     */
    @Override
    public void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList, Multimap<Integer, AclDto> moduleIdAclMap) {

        if (CollectionUtils.isEmpty(aclModuleLevelList)){
            return;
        }
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            List<AclDto> aclDtoList = (List<AclDto>) moduleIdAclMap.get(dto.getId()); //根据权限模块id选出存储的所有有效的权限点集合
            if (CollectionUtils.isNotEmpty(aclDtoList)){
                aclDtoList.sort(aclSeqComparator);//对该模块下的权限做升序排序
                dto.setAclList(aclDtoList);//将排序好的权限存入当前权限模块
            }
            bindAclsWithOrder(dto.getAclModuleList(), moduleIdAclMap);
        }
    }


    /**
     * 根据用户id，获取用户权限树
     * @param userId
     * @return
     */
    @Override
    public List<AclModuleLevelDto> userAclTree(int userId) {

        List<SysAcl> userAclList = sysCoreService.getUserAclList(userId);//根据用户id，获取该用户分配的所有权限点
        List<AclDto> aclDtoList = Lists.newArrayList();
        for (SysAcl acl : userAclList) {
            AclDto dto = AclDto.adapt(acl);
            dto.setChecked(true);
            dto.setHasAcl(true);
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }


    /**
     * 部门在当前层级下的排序器  从小到大排序
     */
    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };


    /**
     * 权限模块在当前层级下的排序器  从小到大排序
     */
    public Comparator<AclModuleLevelDto> aclModuleSeqComparator = new Comparator<AclModuleLevelDto>() {
        @Override
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };


    /**
     * 权限点在当前权限模块下的排序器  从小到大排序
     */
    public Comparator<AclDto> aclSeqComparator = new Comparator<AclDto>() {
        @Override
        public int compare(AclDto o1, AclDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };


}
