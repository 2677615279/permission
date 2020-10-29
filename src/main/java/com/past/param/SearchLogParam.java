package com.past.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 操作日志搜索参数类，传入的参数单独封装，然后校验及前置处理完，转换成实际的业务对象--SysLogWithBLOBs实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchLogParam implements Serializable {

    private Integer type; // 操作类型 LogType

    private String beforeSeg;//更新前的值

    private String afterSeg;//更新后的值

    private String operator;//操作者

    private String fromTime;//开始时间  yyyy-MM-dd HH:mm:ss

    private String toTime;//结束时间

}
