package com.past.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限操作日志实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysLog implements Serializable {

    private Integer id;//日志id

    private Integer type;//权限更新的类型，1：部门，2：用户，3：权限模块，4：权限，5：角色，6：角色用户关系，7：角色权限关系

    private Integer targetId;//基于type后指定的主键id，比如部门、用户、权限、角色等表的主键(用户角色关联表和角色权限关联表的角色id、对角色做调整)

    private String operator;//操作者

    private Date operateTime;//最后一次更新的时间

    private String operateIp;//最后一次更新者的ip地址

    private Integer status;//当前是否复原过，0：没有，1：复原过

    @Override
    public String toString() {
        return "SysLog{" +
                "id=" + id +
                ", type=" + type +
                ", targetId=" + targetId +
                ", operator='" + operator + '\'' +
                ", operateTime=" + operateTime +
                ", operateIp='" + operateIp + '\'' +
                ", status=" + status +
                '}';
    }

}