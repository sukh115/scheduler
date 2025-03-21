package com.example.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long scheduleId;
    private String title;
    private String content;
    private Timestamp createDate;
    private Timestamp updatedDate;
    private Long authorId;
    private String password;


    public Schedule(String title, String content, Long authorId, String password) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.password = password;
        this.createDate = new Timestamp(System.currentTimeMillis());
        this.updatedDate = new Timestamp(System.currentTimeMillis());
    }

}
