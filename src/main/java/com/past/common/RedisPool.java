package com.past.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * 配置redis，被spring管理
 */
@Component("redisPool")
@Slf4j
public class RedisPool {

    @Resource(name = "shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;


    /**
     * 使用连接池实例化一个ShardedJedis连接
     * @return
     */
    public ShardedJedis instance(){

        return shardedJedisPool.getResource();
    }


    /**
     * 安全关闭ShardedJedis
     * @param shardedJedis
     */
    public void safeClose(ShardedJedis shardedJedis){

        try {
            if (shardedJedis != null){
                shardedJedis.close();
            }
        } catch (Exception e){
            log.error("return redis resource exception", e);
        }
    }


}
