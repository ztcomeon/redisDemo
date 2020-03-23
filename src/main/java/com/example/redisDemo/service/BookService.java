/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: BookService
 * Author:   Administrator
 * Date:     2020-03-19 16:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.redisDemo.service;


import com.example.redisDemo.entity.BookEntity;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020-03-19
 * @since 1.0.0
 */
public interface BookService {

    List<BookEntity> findAll();

    BookEntity create(BookEntity bookEntity);

    BookEntity update(BookEntity bookEntity);

    void deleteById(String id);

    BookEntity findById(String id);


    void lPush(BookEntity bookEntity);

    List<Object> lPop(String key, long start, long end);
}