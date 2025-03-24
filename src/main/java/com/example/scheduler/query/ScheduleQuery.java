package com.example.scheduler.query;

import com.example.scheduler.constant.column.AuthorColumns;

import static com.example.scheduler.constant.column.ScheduleColumns.*;

/**
 * 일정(schedule) 관련 SQL 쿼리를 정의한 클래스
 * - 작성자(author) 테이블과의 조인 포함
 * - JdbcTemplateScheduleRepository에서 사용
 */
public class ScheduleQuery {

    // 전체 일정 조회 + 작성자 이름 포함 (최신순 정렬)
    public static String findAllWithAuthor() {
        return "SELECT s." + TITLE.getColumnName() + ", " +
                "s." + CONTENT.getColumnName() + ", " +
                "s." + UPDATED_DATE.getColumnName() + ", " +
                "a." + AuthorColumns.NAME.getColumnName() + " " +
                "FROM schedule s " +
                "JOIN author a ON s." + AUTHOR_ID.getColumnName() + " = a." + AUTHOR_ID.getColumnName() + " " +
                "ORDER BY s." + UPDATED_DATE.getColumnName() + " DESC";
    }

    // 특정 작성자의 일정만 조회
    public static String findByAuthorId() {
        return findAllWithAuthor() + " WHERE s." + AUTHOR_ID.getColumnName() + " = ?";
    }

    // 전체 일정 페이징 조회
    public static String findAllPaged() {
        return findAllWithAuthor() + " LIMIT ? OFFSET ?";
    }

    // schedule_id로 일정 단건 조회 (Entity 반환용)
    public static String findEntityById() {
        return "SELECT * FROM schedule WHERE " + SCHEDULE_ID.getColumnName() + " = ?";
    }

    // 일정 ID 기준으로 작성자 포함 조회
    public static String findByIdWithAuthor() {
        return findAllWithAuthor() + " WHERE s." + SCHEDULE_ID.getColumnName() + " = ?";
    }

    // 일정 수정
    public static String updateById() {
        return "UPDATE schedule SET " +
                TITLE.getColumnName() + " = ?, " +
                CONTENT.getColumnName() + " = ?, " +
                UPDATED_DATE.getColumnName() + " = ? " +
                "WHERE " + SCHEDULE_ID.getColumnName() + " = ?";
    }

    // 일정 삭제
    public static String deleteById() {
        return "DELETE FROM schedule WHERE " + SCHEDULE_ID.getColumnName() + " = ?";
    }
}
