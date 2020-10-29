package com.past.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限模块实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysAclModule implements Serializable {

    private Integer id;//权限模块id

    private String name;//权限模块名称

    private Integer parentId;//上级权限模块的id

    private String level;//权限模块层级

    private Integer seq;//权限模块在当前层级下的顺序，由小到大

    private Integer status;//状态 1：正常，0：冻结，2删除

    private String remark;//备注

    private String operator;//操作者

    private Date operateTime;//最后一次操作时间

    private String operateIp;//最后一次更新操作者的ip地址

    @Override
    public String toString() {
        return "SysAclModule{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", level='" + level + '\'' +
                ", seq=" + seq +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", operator='" + operator + '\'' +
                ", operateTime=" + operateTime +
                ", operateIp='" + operateIp + '\'' +
                '}';
    }

}