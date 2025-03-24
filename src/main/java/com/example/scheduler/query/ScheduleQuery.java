package com.example.scheduler.query;

import com.example.scheduler.constant.column.AuthorColumns;

import static com.example.scheduler.constant.column.ScheduleColumns.*;

public class ScheduleQuery {

    public static String findAllWithAuthor() {
        return "SELECT s." + TITLE.getColumnName() + ", " +
                "s." + CONTENT.getColumnName() + ", " +
                "s." + UPDATED_DATE.getColumnName() + ", " +
                "a." + AuthorColumns.NAME.getColumnName() + " " +
                "FROM schedule s " +
                "JOIN author a ON s." + AUTHOR_ID.getColumnName() + " = a." + AUTHOR_ID.getColumnName() + " " +
                "ORDER BY s." + UPDATED_DATE.getColumnName() + " DESC";
    }

    public static String findByAuthorId() {
        return findAllWithAuthor() + " WHERE s." + AUTHOR_ID.getColumnName() + " = ?";
    }

    public static String findAllPaged() {
        return findAllWithAuthor() + " LIMIT ? OFFSET ?";
    }

    public static String findEntityById() {
        return "SELECT * FROM schedule WHERE " + SCHEDULE_ID.getColumnName() + " = ?";
    }

    public static String findByIdWithAuthor() {
        return findAllWithAuthor() + " WHERE s." + SCHEDULE_ID.getColumnName() + " = ?";
    }

    public static String updateById() {
        return "UPDATE schedule SET " +
                TITLE.getColumnName() + " = ?, " +
                CONTENT.getColumnName() + " = ?, " +
                UPDATED_DATE.getColumnName() + " = ? " +
                "WHERE " + SCHEDULE_ID.getColumnName() + " = ?";
    }

    public static String deleteById() {
        return "DELETE FROM schedule WHERE " + SCHEDULE_ID.getColumnName() + " = ?";
    }
}
