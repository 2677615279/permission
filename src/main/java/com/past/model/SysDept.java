package com.past.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysDept implements Serializable {

    private Integer id;//部门id

    private String name;//部门名称

    private Integer parentId;//上级部门的id

    private String level;//部门层级

    private Integer seq;//部门在当前层级下的顺序，由小到大

    private String remark;//备注

    private String operator;//操作者

    private Date operateTime;//最后一次操作时间

    private String operateIp;//最后一次更新操作者的ip地址

    @Override
    public String toString() {
        return "SysDept{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", level='" + level + '\'' +
                ", seq=" + seq +
                ", remark='" + remark + '\'' +
                ", operator='" + operator + '\'' +
                ", operateTime=" + operateTime +
                ", operateIp='" + operateIp + '\'' +
                '}';
    }

}