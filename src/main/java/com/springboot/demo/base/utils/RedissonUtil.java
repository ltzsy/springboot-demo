package com.springboot.demo.base.utils;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: redisson工具类 <br>
 * @author: 小花卷的Dad <br>
 * @create: 2023/8/24 <br>
 */
@Component
public class RedissonUtil {

    private static RedissonClient redissonClient;

    /**
     * 锁默认释放时间
     */
    private static final long default_lease_time = 5L;

    @Autowired
    public void setRedissonClient(RedissonClient redissonClient) {
        RedissonUtil.redissonClient = redissonClient;
    }

    /**
     * key是否存在
     * @param key
     * @return
     */
    public static boolean isExists(String key){
        return redissonClient.getBucket(key).isExists();
    }

    /**
     * 获取生命周期
     * @param key
     * @return
     */
    public static long getExpireTime(String key){
        return redissonClient.getBucket(key).remainTimeToLive();
    }

    /**
     * 设置生命周期
     * @param key
     * @param time(毫秒)
     * @return
     */
    public static boolean setExpireTime(String key, Long expire){
        return redissonClient.getBucket(key).expire(Duration.ofMillis(expire));
    }

    public static boolean delete(String key){
        if(!isExists(key)){
            return true;
        }
        return redissonClient.getBucket(key).delete();
    }

    /**
     * 保存字符串
     * @param key
     * @param value
     */
    public static void setStr(String key, String value){
        RBucket<String> rBucket = redissonClient.getBucket(key);
        rBucket.set(value);
    }

    /**
     * 保存字符串
     * @param key
     * @param value
     * @param expire
     */
    public static void setStr(String key, String value, Long expire){
        RBucket<String> rBucket = redissonClient.getBucket(key);
        rBucket.set(value, Duration.ofMillis(expire));
    }

    /**
     * 查询字符串
     * @param key
     * @return
     */
    public static String getStr(String key){
        if(isExists(key)){
            return null;
        }
        RBucket<String> rBucket = redissonClient.getBucket(key);
        return rBucket.get();
    }

    /**
     * 保存对象
     * @param key
     * @param value
     * @param <T>
     */
    public static <T> void setObject(String key, T value){
        RBucket<T> rBucket = redissonClient.getBucket(key);
        rBucket.set(value);
    }

    /**
     * 保存对象
     * @param key
     * @param value
     * @param expire
     * @param <T>
     */
    public static <T> void setObject(String key, T value, Long expire){
        RBucket<T> rBucket = redissonClient.getBucket(key);
        rBucket.set(value, Duration.ofMillis(expire));
    }

    /**
     * 查询对象
     * @param key
     * @return
     */
    public static <T> T getObject(String key){
        RBucket<T> rBucket = redissonClient.getBucket(key);
        return rBucket.get();
    }

    /**
     * map.get
     * @param key
     * @param mapKey
     * @param <T>
     * @return
     */
    public static <T> T mapGet(String key, String mapKey){
        if(!isExists(key)){
            return null;
        }
        Map<String, T> rMap = redissonClient.getMap(key);
        return rMap.get(mapKey);
    }

    /**
     * 查询map
     * @param key
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> mapGetAll(String key){
        RMap<String, T> rMap = redissonClient.getMap(key);
        return rMap.readAllMap();
    }

    /**
     * map.put
     * @param key
     * @param mapKey
     * @param mapValue
     * @param <T>
     */
    public static <T> void mapPut(String key, String mapKey,T mapValue){
        RMap<String, T> rMap = redissonClient.getMap(key);
        rMap.put(mapKey, mapValue);
    }

    /**
     * map.putAll
     * @param key
     * @param map
     * @param <T>
     */
    public static <T> void mapPutAll(String key, Map<String, T> map){
        RMap<String, T> rMap = redissonClient.getMap(key);
        rMap.putAll(map);
    }

    /**
     * map.contains
     * @param key
     * @param mapKey
     * @return
     */
    public static boolean mapContains(String key, String mapKey){
        if(!isExists(key)){
            return false;
        }
        Map<String, Object> rMap = redissonClient.getMap(key);
        return rMap.containsKey(mapKey);
    }

    /**
     * list.get
     * @param key
     * @param listIndex
     * @param <T>
     * @return
     */
    public static <T> T listGet(String key, int listIndex){
        if(!isExists(key)){
            return null;
        }
        if(listIndex < 0){
            return null;
        }
        RList<T> rList = redissonClient.getList(key);
        if(rList.size()-1 < listIndex){
            return null;
        }
        return rList.get(listIndex);
    }

    /**
     * list.getAll
     * @param key
     * @param <T>
     * @return
     */
    public static <T> List<T> listGetAll(String key){
        RList<T> rList = redissonClient.getList(key);
        return rList.readAll();
    }

    /**
     * list.add
     * @param key
     * @param addValue
     * @param <T>
     */
    public static <T> void listAdd(String key, T addValue){
        RList<T> rList = redissonClient.getList(key);
        rList.add(addValue);
    }

    /**
     * list.add
     * @param key
     * @param addList
     * @param <T>
     */
    public static <T> void listAddAll(String key, List<T> addList){
        RList<T> rList = redissonClient.getList(key);
        rList.addAll(addList);
    }

    /**
     * list.set
     * @param key
     * @param listIndex
     * @param setValue
     * @param <T>
     */
    public static <T> void listSet(String key, int listIndex, T setValue){
        RList<T> rList = redissonClient.getList(key);
        if(rList.size()-1 < listIndex){
            return;
        }
        rList.set(listIndex, setValue);
    }

    /**
     * 获取锁
     * @param lockName
     * @return
     */
    public static RLock getLock(String lockName){
        RLock rLock = redissonClient.getLock(lockName);
        if(rLock.isLocked()){
            return null;
        }
        rLock.lock(default_lease_time, TimeUnit.SECONDS);
        return rLock;
    }


    /**
     * 获取锁
     * @param lockName
     * @param waitTime 阻塞时间（秒）
     * @return
     */
    public static RLock getLock(String lockName, long waitTime){
        RLock rLock = redissonClient.getLock(lockName);
        try {
            boolean resp = rLock.tryLock(waitTime, default_lease_time,TimeUnit.SECONDS);
            if(resp){
                return rLock;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 释放锁
     * @param lock
     */
    public static void unlock(RLock lock){
        if(ObjectUtils.isEmpty(lock)){
            return;
        }
        if(!lock.isLocked()){
            return;
        }
        lock.unlock();
    }
}
