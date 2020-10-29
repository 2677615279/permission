package com.past.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限点实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class SysAcl implements Serializable {

    private Integer id;//权限id

    private String code;//权限码

    private String name;//权限名称

    private Integer aclModuleId;//权限所在的权限模块id

    private String url;//请求的url, 可以填正则表达式

    private Integer type;//类型，1：菜单，2：按钮，3：其他

    private Integer status;//状态，1：正常，0：冻结

    private Integer seq;//权限在当前模块下的顺序，由小到大

    private String remark;//备注

    private String operator;//操作者

    private Date operateTime;//最后一次更新时间

    private String operateIp;//最后一个更新者的ip地址


    @Override
    public String toString() {
        return "SysAcl{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", aclModuleId=" + aclModuleId +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", seq=" + seq +
                ", remark='" + remark + '\'' +
                ", operator='" + operator + '\'' +
                ", operateTime=" + operateTime +
                ", operateIp='" + operateIp + '\'' +
                '}';
    }

}