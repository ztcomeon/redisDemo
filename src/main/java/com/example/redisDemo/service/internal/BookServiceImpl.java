/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: BookServiceImpl
 * Author:   Administrator
 * Date:     2020-03-19 16:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.redisDemo.service.internal;

import com.example.redisDemo.entity.BookEntity;
import com.example.redisDemo.repository.BookRepository;
import com.example.redisDemo.service.BookService;
import com.example.redisDemo.service.RedisService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020-03-19
 * @since 1.0.0
 */
@Service("BookService")
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisService redisService;

    //使用：能够自动分层
    private final String KEY = "bookCache_:";

    private final String LIST_KEY = "list:";

    @Override
    public List<BookEntity> findAll() {
        List<BookEntity> entities = bookRepository.findAll();
//        redisTemplate.opsForValue().set("users",entities);
        return entities;
    }

    @Transactional
    @Override
    public BookEntity create(BookEntity bookEntity) {
        Validate.notNull(bookEntity, "新增对象不能为空");
        Validate.notBlank(bookEntity.getTitle(), "标题不能为空");
        BookEntity entity = bookRepository.save(bookEntity);
        redisService.set(KEY, entity.getId(), entity);
        return entity;
    }

    @Transactional
    @Override
    public BookEntity update(BookEntity bookEntity) {
        Validate.notNull(bookEntity, "更新对象不能为空");

        BookEntity oldBook = bookRepository.findById(bookEntity.getId()).orElse(null);
        Validate.notNull(oldBook, "未查询到更新对象");

        return bookRepository.saveAndFlush(bookEntity);
    }

    @Transactional
    @Override
    public void deleteById(String id) {

        boolean delete = redisService.delete(KEY, id);
        System.out.println(delete);

        try {
            BookEntity oldBook = bookRepository.findById(id).orElse(null);
            if (oldBook != null) {
                bookRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public BookEntity findById(String id) {

        //先从redis中取
        BookEntity bookEntity = (BookEntity) redisService.get(KEY, id);

        if (bookEntity == null) {
            //redis中没有在从db中取
            BookEntity bookEntityDB = bookRepository.findById(id).orElse(null);
            if (bookEntityDB != null) {
                //db非空的情况下刷新redis
                redisService.set(KEY, bookEntityDB.getId(), bookEntityDB);
                return bookEntityDB;
            }
        }
        return bookEntity;
    }

    @Override
    public void lPush(BookEntity bookEntity) {
        BookEntity entity = bookRepository.save(bookEntity);
        //list的命名规则需要查一下
        String key = LIST_KEY + "book";
        redisService.lPush(KEY, key, entity);
    }

    @Override
    public List<Object> lPop(String key, long start, long end) {
        String key2 = LIST_KEY + key;
        List<Object> objects = redisService.lRange(KEY, key2, start, end);
        return objects;
    }
}