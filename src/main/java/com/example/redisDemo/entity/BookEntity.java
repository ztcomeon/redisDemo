package com.example.redisDemo.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * BookEntity entity.
 *
 * @author Evan
 * @date 2019/4
 */
@Entity
@Table(name = "redis_book")
public class BookEntity extends UuidEntity{

    private String title;
    private String author;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
