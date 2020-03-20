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
        redisTemplate.opsForValue().set(entity.getId(), entity);
        redisTemplate.expire("book", 300, TimeUnit.SECONDS);
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
        bookRepository.deleteById(id);
    }

    @Override
    public BookEntity findById(String id) {
        redisTemplate.opsForValue().get(id);
        redisTemplate.expire("book", 30, TimeUnit.SECONDS);
        BookEntity entity = bookRepository.findById(id).orElse(null);

        return entity;
    }
}