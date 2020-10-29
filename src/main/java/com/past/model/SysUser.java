package com.past.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysUser implements Serializable {

	private Integer id;//用户id

	private String username;//用户名称

	private String telephone;//手机号

	private String mail;//邮箱

	private String password;//加密后的密码

	private Integer deptId;//用户所在部门的id

	private Integer status;//状态，1：正常，0：冻结，2：删除(不允许恢复正常)

	private String remark;//备注

	private String operator;//操作者

	private Date operateTime;//最后一次更新时间

	private String operateIp;//最后一次更新者的ip地址

	@Override
	public String toString() {
		return "SysUser{" +
				"id=" + id +
				", username='" + username + '\'' +
				", telephone='" + telephone + '\'' +
				", mail='" + mail + '\'' +
				", password='" + password + '\'' +
				", deptId=" + deptId +
				", status=" + status +
				", remark='" + remark + '\'' +
				", operator='" + operator + '\'' +
				", operateTime=" + operateTime +
				", operateIp='" + operateIp + '\'' +
				'}';
	}

}

