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
 * 权限点参数类，传入的参数单独封装，然后校验及前置处理完，转换成实际的业务对象--SysAclModule实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AclParam implements Serializable {

    private Integer id;//权限点id

    @NotBlank(message = "权限点名称不可以为空")
    @Length(min = 2, max = 20, message = "权限点名称长度需要在2-20个字之间")
    private String name;//权限名称

    @NotNull(message = "必须指定权限模块")
    private Integer aclModuleId;//权限所在的权限模块id

    @Length(min = 6, max = 100, message = "权限点URL长度需要在6-100个字符之间")
    private String url;//请求的url, 可以填正则表达式

    @NotNull(message = "必须指定权限点的类型")
    @Min(value = 1, message = "权限点类型不合法")
    @Max(value = 3, message = "权限点类型不合法")
    private Integer type;//类型，1：菜单，2：按钮，3：其他

    @NotNull(message = "必须指定权限点的状态")
    @Min(value = 0, message = "权限点状态不合法")
    @Max(value = 1, message = "权限点状态不合法")
    private Integer status;//状态，1：正常，0：冻结

    @NotNull(message = "必须指定权限点的展示顺序")
    private Integer seq; //权限在当前模块下的顺序，由小到大

    @Length(min = 0, max = 200, message = "权限点备注长度需要在200个字符以内")
    private String remark = "";//备注

}
