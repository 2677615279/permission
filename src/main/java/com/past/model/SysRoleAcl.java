package com.past.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限关联实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysRoleAcl implements Serializable {

    private Integer id;

    private Integer roleId;//角色id

    private Integer aclId;//权限id

    private String operator;//操作者

    private Date operateTime;//最后一次更新的时间

    private String operateIp;//最后一次更新者的ip

    @Override
    public String toString() {
        return "SysRoleAcl{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", aclId=" + aclId +
                ", operator='" + operator + '\'' +
                ", operateTime=" + operateTime +
                ", operateIp='" + operateIp + '\'' +
                '}';
    }

}