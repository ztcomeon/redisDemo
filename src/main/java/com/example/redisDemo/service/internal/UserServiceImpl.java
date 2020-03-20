/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: UserServiceImpl
 * Author:   Administrator
 * Date:     2020-03-05 17:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.redisDemo.service.internal;


import com.example.redisDemo.entity.UserEntity;
import com.example.redisDemo.repository.UserRepository;
import com.example.redisDemo.service.UserService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020-03-05
 * @since 1.0.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
//    @CachePut(cacheNames = "user", condition = "#userEntity!=null", key = "#result.id")
    public UserEntity create(UserEntity userEntity){
            Validate.notNull(userEntity, "新增对象不能为空");
        return userRepository.save(userEntity);
}

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> users = userRepository.findAll();
        return users;
    }

    @Override
//    @Cacheable(cacheNames = "user")
    public UserEntity findById(String id) {
//        List<UserEntity> users = (List<UserEntity>) userRepository.findById(id).get();
        //getOne返回的是代理对象
//        UserEntity userEntity = userRepository.getOne(id);
        UserEntity userEntity = userRepository.findById(id).orElse(null);

        return userEntity;
    }

    @Override
    public List<UserEntity> findByName(String name) {
        List<UserEntity> users = userRepository.findByUserName(name);
        return users;
    }

    @Transactional
    @Override
    public UserEntity modifyUser(UserEntity userEntity) {
        //修改用户信息
        Validate.notNull(userEntity, "修改用户时，用户信息不能为空");
        Validate.notBlank(userEntity.getId(), "修改用户时，用户信息不能为空");
        UserEntity oldUser = this.findById(userEntity.getId());
        Validate.notNull(oldUser, "无法获取到该用户");
        oldUser.setUserName(userEntity.getUserName());
        oldUser.setPassword(userEntity.getPassword());
        oldUser.setUserAge(userEntity.getUserAge());
        return userRepository.saveAndFlush(oldUser);
    }

    @Transactional
    @Override
//    @CacheEvict(cacheNames = "user", beforeInvocation = true, key = "#id")
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}