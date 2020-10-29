package com.past.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 权限模块参数类，传入的参数单独封装，然后校验及前置处理完，转换成实际的业务对象--SysAclModule实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AclModuleParam implements Serializable {

    private Integer id;//权限模块id

    @NotBlank(message = "权限模块名称不可以为空")
    @Length(min = 2, max = 20, message = "权限模块名称长度需要在2-20个字之间")
    private String name;//权限模块名称

    private Integer parentId = 0;//上级权限模块的id

    @NotNull(message = "权限模块展示顺序不可以为空")
    private Integer seq;//权限模块在当前层级下的顺序，由小到大

    @NotNull(message = "权限模块状态不可以为空")
    @Min(value = 0, message = "权限模块状态不合法")
    @Max(value = 1, message = "权限模块状态不合法")
    private Integer status;//状态 1：正常，0：冻结

    @Length(max = 200, message = "权限模块备注需要在200个字之间")
    private String remark = "";//备注

}
