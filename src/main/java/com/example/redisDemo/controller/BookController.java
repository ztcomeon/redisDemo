/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: BookController
 * Author:   Administrator
 * Date:     2020-03-20 17:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.redisDemo.controller;

import com.example.redisDemo.controller.model.ResponseCode;
import com.example.redisDemo.controller.model.ResponseModel;
import com.example.redisDemo.entity.BookEntity;
import com.example.redisDemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020-03-20
 * @since 1.0.0
 */
@RestController
@RequestMapping("/book")
public class BookController extends BaseController {

    @Autowired
    BookService bookService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseModel create(@RequestBody BookEntity bookEntity) {
        try {
            BookEntity entity = bookService.create(bookEntity);
            return new ResponseModel(new Date().getTime(), entity, ResponseCode._200, "");
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseModel findById(String id) {
        try {
            BookEntity entity = bookService.findById(id);
            return new ResponseModel(new Date().getTime(), entity, ResponseCode._200, "");
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseModel findAll() {
        try {
            List<BookEntity> entitys = bookService.findAll();
            return new ResponseModel(new Date().getTime(), entitys, ResponseCode._200, "");
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ResponseModel update(@RequestBody BookEntity bookEntity) {
        try {
            BookEntity entity = bookService.update(bookEntity);
            return new ResponseModel(new Date().getTime(), entity, ResponseCode._200, "");
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }

    @RequestMapping(value = "/deleteById", method = {RequestMethod.GET})
    public ResponseModel deleteById(String id) {
        try {
            bookService.deleteById(id);
           return this.buildHttpReslut();
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }
}