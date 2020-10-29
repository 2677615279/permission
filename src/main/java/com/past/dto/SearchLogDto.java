package com.past.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 对操作日志对象的属性做了扩展
 */
@Getter
@Setter
public class SearchLogDto {

    private Integer type; // 操作类型 LogType

    private String beforeSeg;//更新前的值

    private String afterSeg;//更新后的值

    private String operator;//操作者

    private Date fromTime;//开始时间  yyyy-MM-dd HH:mm:ss

    private Date toTime;//结束时间

}
