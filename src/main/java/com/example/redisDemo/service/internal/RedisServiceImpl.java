package com.example.redisDemo.service.internal;

import com.example.redisDemo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020-03-23
 * @since 1.0.0
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public boolean set(String prefix, String key, Object object) {
        boolean result = false;
        try {
            String realKey = prefix + key;
            ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
            operations.set(realKey, object);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean set(String prefix, String key, Object object, int second, boolean isRandomSeconds) {
        boolean result = false;
        try {
            String realKey = prefix + key;
            ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
            if (isRandomSeconds) {
                int randomSeconds = (int) (300 * Math.random() + 60);
                second += randomSeconds;
            }
            if (second <= 0) {
                operations.set(realKey, object);
            } else {
                operations.set(realKey, object, second, TimeUnit.SECONDS);
            }
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean exists(String prefix, String key) {
        String realKey = prefix + key;
        return redisTemplate.hasKey(realKey);
    }

    @Override
    public Object get(String prefix, String key) {
        String realKey = prefix + key;
        Object result = null;
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        result = operations.get(realKey);
        return result;
    }

    @Override
    public boolean delete(String prefix, String key) {
        boolean result = false;
        String realKey = prefix + key;
        try {
            if (exists(prefix, key)) {
                result = redisTemplate.delete(realKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void lPush(String prefix, String key, Object object) {
        String realKey = prefix + key;
        ListOperations<Object, Object> operations = redisTemplate.opsForList();
        Long aLong = operations.leftPush(realKey, object);
        System.out.println(aLong);
    }

    @Override
    public List<Object> lRange(String prefix, String key, long start, long end) {
        String realKey = prefix + key;
        ListOperations<Object, Object> operations = redisTemplate.opsForList();
        List<Object> objects = operations.range(realKey, start, end);
        return objects;
    }
}