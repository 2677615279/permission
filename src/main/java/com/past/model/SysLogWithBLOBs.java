package com.past.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库中这类字段是text属性，使用mybatis-generator自动生成后 会以该属性组成一个子类继承于该表其他字段生成的实体父类
 */
public class SysLogWithBLOBs extends SysLog implements Serializable {

    private String oldValue;//旧值

    private String newValue;//新值

    public SysLogWithBLOBs() {
        super();
    }

    public SysLogWithBLOBs(String oldValue, String newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public SysLogWithBLOBs(String oldValue, String newValue, Integer id, Integer type, Integer targetId, String operator, Date operateTime, String operateIp, Integer status){
        super(id,type,targetId,operator,operateTime,operateIp,status);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue == null ? null : oldValue.trim();
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue == null ? null : newValue.trim();
    }

    @Override
    public String toString() {
        return "SysLogWithBLOBs{" +
                "oldValue='" + oldValue + '\'' +
                ", newValue='" + newValue + '\'' +
                '}';
    }

}