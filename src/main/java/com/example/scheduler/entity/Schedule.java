package com.example.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String title;
    private String content;
    private Timestamp createDate;
    private Timestamp updatedDate;
    private String userName;
    private String password;


    public Schedule(String title, String content, String userName, String password) {
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.password = password;
        this.createDate = new Timestamp(System.currentTimeMillis());
        this.updatedDate = new Timestamp(System.currentTimeMillis());
    }

    public Schedule(Long id, String title, String content, Timestamp updateDate, String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.updatedDate = updateDate;
        this.userName = userName;
    }

}
