/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: RedisService
 * Author:   Administrator
 * Date:     2020-03-23 10:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.redisDemo.service;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020-03-23
 * @since 1.0.0
 */
public interface RedisService {


    /**
     * @param prefix 前缀
     * @param key    主键
     * @param object 对象
     * @Author ztx
     * @Description //写入缓存
     **/
    boolean set(String prefix, String key, Object object);

    /**
     * @param prefix          前缀
     * @param key             主键
     * @param object          写入对象
     * @param second          过期时间
     * @param isRandomSeconds 过期时间是否添加随机值
     * @Author ztx
     * @Description //写入缓存并设置过期时间
     **/
    boolean set(String prefix, String key, Object object, int second, boolean isRandomSeconds);

    /**
     * @param prefix 前缀
     * @param key    主键
     * @Author ztx
     * @Description //判断缓存是否存在
     **/
    boolean exists(String prefix, String key);

    /***
     * @param prefix
     * @param key
     * @Author ztx
     * @Description //获取缓存对象
     **/
    Object get(String prefix, String key);

    /***
     * @param prefix
     * @param key
     * @Author ztx
     * @Description //删除缓存对象
     **/
    boolean delete(String prefix, String key);

    /**
     * @param prefix
     * @param key
     * @param object
     * @Author ztx
     * @Description //列表添加
     **/
    void lPush(String prefix, String key, Object object);

    /**
     * @param prefix
     * @param key
     * @Author ztx
     * @Description //列表删除
     **/
    List<Object> lRange(String prefix, String key, long start, long end);

    /**
     * @param prefix
     * @param key
     * @param object1
     * @param object2
     * @Author ztx
     * @Description //哈希添加
     **/
    boolean hashSet(String prefix, String key, Object object1, Object object2);

    /***
     * @param prefix
     * @param key
     * @param map
     * @Author ztx
     * @Description //hash添加
     **/
    boolean hashSetAll(String prefix, String key, Map<String, Object> map);

    /**
     * @param prefix
     * @param key
     * @Author ztx
     * @Description //集合添加
     **/
    Object hashGet(String prefix, String key);

    /**
     * @param prefix
     * @param key
     * @param object
     * @Author ztx
     * @Description //TODO
     **/
    boolean setAdd(String prefix, String key, Object object);

    /**
     * @param prefix
     * @param key
     * @Author ztx
     * @Description //集合获取
     **/
    Object setGet(String prefix, String key);

    /**
     * @param prefix
     * @param key
     * @param object
     * @Author ztx
     * @Description //有序集合添加
     **/
    boolean zsetAdd(String prefix, String key, Object object, double score);

    /**
     * @param prefix
     * @param key
     * @Author ztx
     * @Description //有序集合获取
     **/
    Object zsetRangeByScore(String prefix, String key,double scoure, double scoure1);
}