package com.example.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Author {
    private Long authorId;
    private String name;
    private String email;
    private Timestamp createdDate;
    private Timestamp updatedDate;

    public Author(String name, String email) {
        this.name = name;
        this.email = email;
        this.createdDate = new Timestamp(System.currentTimeMillis());
        this.updatedDate = new Timestamp(System.currentTimeMillis());
    }
}
