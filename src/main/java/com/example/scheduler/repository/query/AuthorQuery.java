package com.example.scheduler.repository.query;

import static com.example.scheduler.repository.column.AuthorColumns.*;

/**
 * 작성자(author) 관련 SQL 쿼리를 정의한 클래스
 * - JdbcTemplateAuthorRepository 사용
 */
public class AuthorQuery {

    // author_id 기준 단건 조회

    public static String findById() {
        return "SELECT * FROM author WHERE " + AUTHOR_ID.getColumnName() + " = ?";
    }

    // author_id 존재 여부 확인 (count)

    public static String existsById() {
        return "SELECT COUNT(*) FROM author WHERE " + AUTHOR_ID.getColumnName() + " = ?";
    }

    // author_id 기준 정보 수정

    public static String updateById() {
        return "UPDATE author SET " +
                NAME.getColumnName() + " = ?, " +
                EMAIL.getColumnName() + " = ?, " +
                UPDATED_DATE.getColumnName() + " = ? " +
                "WHERE " + AUTHOR_ID.getColumnName() + " = ?";
    }

    // author_id 기준 삭제

    public static String deleteById() {
        return "DELETE FROM author WHERE " + AUTHOR_ID.getColumnName() + " = ?";
    }
}

