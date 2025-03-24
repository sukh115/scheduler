package com.example.scheduler.entity;

import com.example.scheduler.exception.CustomException;
import com.example.scheduler.exception.exceptionCode.ExceptionCode;
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

    public void update(String title, String content, Timestamp updatedTime) {
        this.title = title;
        this.content = content;
        this.updatedDate = updatedTime;
    }


    // 작성자 일치 유효성 검사
    public void validateAuthor(Long authorId) {
        if (!this.authorId.equals(authorId)) {
            throw new CustomException(ExceptionCode.UNAUTHORIZED_AUTHOR);
        }
    }

    // 비밀번호 일치 유효성 검사
    public void validatePassword(String password) {
        if (this.password.equals(password)) {
            throw new CustomException(ExceptionCode.PASSWORD_MISMATCH);
        }
    }


}
