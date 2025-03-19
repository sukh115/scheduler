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
    private Timestamp updateDate;
    private String userName;
    private String password;


    public Schedule(String title, String content, String userName, String password) {
        this.title = title;
        this.content = content;
        this.createDate = new Timestamp(System.currentTimeMillis()); // 현재 시간
        this.updateDate = new Timestamp(System.currentTimeMillis());
        this.userName = userName;
        this.password = password;
    }

}
