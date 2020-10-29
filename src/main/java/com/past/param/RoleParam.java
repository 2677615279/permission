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
 * 角色模块参数类，传入的参数单独封装，然后校验及前置处理完，转换成实际的业务对象--SysRole实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleParam implements Serializable {

    private Integer id;//角色id

    @NotBlank(message = "角色名称不可以为空")
    @Length(min = 2, max = 20, message = "角色名称长度需要在2-20个字之间")
    private String name;//角色名称

    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 2, message = "角色类型不合法")
    private Integer type = 1;//角色类型，1：管理员角色，2：其他

    @NotNull(message = "角色状态不可以为空")
    @Min(value = 0, message = "角色状态不合法")
    @Max(value = 1, message = "角色状态不合法")
    private Integer status;//状态，1：正常，0：冻结

    @Length(min = 0, max = 200, message = "角色备注长度需要在200个字符以内")
    private String remark = ""; //备注

}
