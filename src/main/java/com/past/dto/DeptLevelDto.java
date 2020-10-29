package com.past.dto;

import com.past.model.SysDept;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 对部门对象的属性做了扩展，用来做部门树形 层级适配
 */
@Getter
@Setter
public class DeptLevelDto extends SysDept {

    //存储当前层级该部门的所有下一级部门(直接子部门)的集合，其每个子部门对象也有此属性，继续存储所有其子部门的子部门集合，从而形成一颗部门树
    private List<DeptLevelDto> deptList = new ArrayList<>();


    /**
     * 适配，传入部门对象，copy成DeptLevelDto
     * @param sysDept
     * @return
     */
    public static DeptLevelDto adapt(SysDept sysDept){

        DeptLevelDto deptLevelDto = new DeptLevelDto();
        BeanUtils.copyProperties(sysDept,deptLevelDto); //对象之间属性的赋值，避免通过get、set方法一个一个属性的赋值
        return deptLevelDto;
    }

    @Override
    public String toString() {
        return "DeptLevelDto{" +
                "deptList=" + deptList +
                '}';
    }

}
