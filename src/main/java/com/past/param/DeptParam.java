package com.past.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 部门参数类，传入的参数单独封装，然后校验及前置处理完，转换成实际的业务对象--SysDept实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptParam implements Serializable {

    private Integer id;//部门id

    @NotBlank(message = "部门名称不可以为空")
    @Length(max = 15,min = 2,message = "部门名称长度必须在2-15个字之间")
    private String name;//部门名称

    private Integer parentId = 0;//上级部门的id 默认0

    @NotNull(message = "展示顺序不可以为空")
    private Integer seq;//部门在当前层级下的顺序，由小到大

    @Length(max = 150,message = "备注的长度需要在150个字之内")
    private String remark = "";//备注

}
