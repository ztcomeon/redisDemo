/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: HelloController
 * Author:   Administrator
 * Date:     2019-06-27 11:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.redisDemo.controller;

import com.example.redisDemo.controller.model.ResponseCode;
import com.example.redisDemo.controller.model.ResponseModel;
import com.example.redisDemo.entity.UserEntity;
import com.example.redisDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2019-06-27
 * @since 1.0.0
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    //可以直接使用redisTemplate
    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/test")
    public String test2(String name, String age) {
        //这里相当于redis对String类型的set操作
        redisTemplate.opsForValue().set(name, age);
        //这里相当于redis对String类型的get操作
        return (String) redisTemplate.opsForValue().get(name);

    }

    @Autowired
    UserService userService;

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public ResponseModel create(@RequestBody UserEntity user) {
        try {
            UserEntity userEntity = userService.create(user);
            return new ResponseModel(new Date().getTime(), userEntity, ResponseCode._200, "");
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseModel findById(String id) {
        try {
            UserEntity userEntity = userService.findById(id);
            return new ResponseModel(new Date().getTime(), userEntity, ResponseCode._200, "");
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
    public ResponseModel deleteById(String id) {
        try {
            userService.deleteById(id);
            return this.buildHttpReslut();
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }

}