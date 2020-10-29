package com.past.service;

import com.past.beans.CacheKeyConstants;

public interface SysCacheService {


    /**
     * 将数据保存到redis缓存
     * @param toSavedValue 欲保存到缓存的数据
     * @param timeoutSeconds 超时时间
     * @param prefix 缓存前缀
     */
    void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix);


    /**
     * 适配redis api  将数据保存到redis缓存
     * @param toSavedValue 欲保存到缓存的数据
     * @param timeoutSeconds 超时时间
     * @param prefix 缓存前缀
     * @param keys 其他
     */
    void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix, String... keys);


    /**
     * 生成正常对应的要缓存的权限点的名称
     * @param prefix
     * @param keys
     * @return
     */
    String generateCacheKey(CacheKeyConstants prefix, String... keys);


    /**
     * 根据key从缓存中获取数据
     * @return
     */
    String getFromCache(CacheKeyConstants prefix, String... keys);


}
