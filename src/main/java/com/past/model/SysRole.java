package com.past.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysRole implements Serializable {

    private Integer id;//角色id

    private String name;//角色名称

    private Integer type;//角色的类型，1：管理员角色，2：其他

    private Integer status;//状态，1：正常，0：冻结

    private String remark;//备注

    private String operator;//操作者

    private Date operateTime;//最后一次更新的时间

    private String operateIp;//最后一次更新者的ip地址

    @Override
    public String toString() {
        return "SysRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", operator='" + operator + '\'' +
                ", operateTime=" + operateTime +
                ", operateIp='" + operateIp + '\'' +
                '}';
    }

}