package com.past.dto;

import com.past.model.SysAclModule;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 对权限模块的属性做了扩展，用来做权限模块树形 层级适配
 */
@Getter
@Setter
public class AclModuleLevelDto extends SysAclModule {

    //存储当前层级该权限模块的所有下一级模块(直接子模块)的集合，其每个子模块也有此属性，继续存储所有其子模块的子模块集合，从而形成一颗权限模块树
    private List<AclModuleLevelDto> aclModuleList = new ArrayList<>();

    //存储当前权限模块下的权限点，判断是否选中并是否有权限操作
    private List<AclDto> aclList = new ArrayList<>();


    /**
     * 适配，传入权限模块对象，copy成AclModuleLevelDto
     * @param sysAclModule
     * @return
     */
    public static AclModuleLevelDto adapt(SysAclModule sysAclModule){

        AclModuleLevelDto aclModuleLevelDto = new AclModuleLevelDto();
        BeanUtils.copyProperties(sysAclModule,aclModuleLevelDto); //对象之间属性的赋值，避免通过get、set方法一个一个属性的赋值
        return aclModuleLevelDto;
    }

    @Override
    public String toString() {
        return "AclModuleLevelDto{" +
                "aclModuleList=" + aclModuleList +
                ", aclList=" + aclList +
                '}';
    }

}
