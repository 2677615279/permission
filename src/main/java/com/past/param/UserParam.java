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
 * 用户参数类，传入的参数单独封装，然后校验及前置处理完，转换成实际的业务对象--SysUser实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserParam implements Serializable {

    private Integer id;//用户id

    @NotBlank(message = "用户名不可以为空")
    @Length(min = 1, max = 20, message = "用户名长度需要在20个字以内")
    private String username;//用户名称

    @NotBlank(message = "手机号不可以为空")
    @Length(min = 1, max = 13, message = "手机号长度需要在13个字以内")
    private String telephone;//手机号

    @NotBlank(message = "邮箱不可以为空")
    @Length(min = 5, max = 50, message = "邮箱长度需要在50个字以内")
    private String mail;//邮箱

    @NotNull(message = "必须提供用户所在的部门")
    private Integer deptId;//用户所在部门的id

    @NotNull(message = "必指定用户的状态")
    @Min(value = 0, message = "用户状态不合法")
    @Max(value = 2, message = "用户状态不合法")
    private Integer status;//状态，1：正常，0：冻结，2：删除(不允许恢复正常)

    @Length(min = 0, max = 200, message = "备注长度需要在200个字以内")
    private String remark = "";//备注,不传则默认空字符串

}
