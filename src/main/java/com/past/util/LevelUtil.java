package com.past.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 计算层级的工具类
 */
public class LevelUtil {

    public final static String SEPARATOR = ".";//层级分隔符

    public final static String ROOT = "0";//层级根 最高层级


    /**
     * 计算部门层级
     * 0  0.1  0.1.2  0.1.3
     * @param parentLevel 上级部门层级
     * @param parentId 上级部门id
     * @return
     */
    public static String calculateLevel(String parentLevel, Integer parentId){
        //如果上级部门层级为空，即是首层
        if (StringUtils.isBlank(parentLevel)){
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }

}
