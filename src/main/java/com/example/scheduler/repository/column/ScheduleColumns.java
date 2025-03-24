package com.example.scheduler.repository.column;

import lombok.Getter;

/**
 * 일정(schedule) 테이블의 컬럼명을 정의한 Enum
 */
@Getter
public enum ScheduleColumns {
    SCHEDULE_ID("schedule_id"),       // 일정 ID (PK)
    TITLE("title"),                   // 제목
    CONTENT("content"),               // 내용
    AUTHOR_ID("author_id"),           // 작성자 ID (FK)
    PASSWORD("password"),             // 비밀번호 (수정/삭제 시 검증용)
    CREATE_DATE("create_date"),       // 생성일시
    UPDATED_DATE("updated_date");     // 수정일시

    private final String columnName;

    ScheduleColumns(String columnName) {
        this.columnName = columnName;
    }
}
