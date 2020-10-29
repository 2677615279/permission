package com.past.service.impl;

import com.google.common.base.Joiner;
import com.past.beans.CacheKeyConstants;
import com.past.common.RedisPool;
import com.past.service.SysCacheService;
import com.past.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;

@Service
@Slf4j
public class SysCacheServiceImpl implements SysCacheService {

    @Resource(name = "redisPool")
    RedisPool redisPool;


    /**
     * 将数据保存到redis缓存   --缓存系统中权限，无需传入其他信息
     * @param toSavedValue 欲保存到缓存的数据
     * @param timeoutSeconds 超时时间
     * @param prefix 缓存前缀
     */
    @Override
    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix) {

        saveCache(toSavedValue, timeoutSeconds, prefix, (String[]) null);
    }


    /**
     * 适配redis api  将数据保存到redis缓存  --缓存用户中权限，需要传入用户相关信息
     * @param toSavedValue 欲保存到缓存的数据
     * @param timeoutSeconds 超时时间
     * @param prefix 缓存前缀
     * @param keys 用户相关的信息
     */
    @Override
    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix, String... keys) {

        //如果没有欲保存到缓存的数据，直接结束
        if (toSavedValue == null){
            return;
        }
        ShardedJedis shardedJedis = null;
        //获取redis连接
        try {
            String cacheKey = generateCacheKey(prefix, keys);
            shardedJedis = redisPool.instance();
            shardedJedis.setex(cacheKey, timeoutSeconds, toSavedValue);
        } catch (Exception e){
            log.error("save cache exception, prefix:{}, keys:{}", prefix.name(), JsonMapper.objToString(keys), e);
        } finally {
            redisPool.safeClose(shardedJedis);
        }
    }


    /**
     * 生成正常对应的要缓存的权限点的名称  如果key特别复杂 可以传入多个String值拼接
     * @param prefix
     * @param keys
     * @return
     */
    @Override
    public String generateCacheKey(CacheKeyConstants prefix, String... keys) {

        String key = prefix.name();
        if (keys != null && keys.length > 0){
            key += "_" + Joiner.on("_").join(keys);
        }
        return key;
    }


    /**
     * 根据key从缓存中获取数据
     * @return
     */
    @Override
    public String getFromCache(CacheKeyConstants prefix, String... keys) {

        ShardedJedis shardedJedis = null;
        String cacheKey = generateCacheKey(prefix, keys);
        try {
            shardedJedis = redisPool.instance();
            return shardedJedis.get(cacheKey);
        } catch (Exception e){
            log.error("get from cache exception, prefix:{}, keys:{}", prefix.name(), JsonMapper.objToString(keys), e);
            return null;
        } finally {
            redisPool.safeClose(shardedJedis);
        }
    }


}
