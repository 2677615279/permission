package com.past.beans;

import lombok.Getter;

/**
 * 缓存枚举类
 */
@Getter
public enum CacheKeyConstants {

    /**
     * 缓存中的系统权限
     */
    SYSTEM_ACLS,

    /**
     * 缓存中的用户权限
     */
    USER_ACLS;

}
