package com.example.scheduler.query;

import static com.example.scheduler.constant.column.AuthorColumns.*;

public class AuthorQuery {

    public static String findById() {
        return "SELECT * FROM author WHERE " + AUTHOR_ID.getColumnName() + " = ?";
    }

    public static String existsById() {
        return "SELECT COUNT(*) FROM author WHERE " + AUTHOR_ID.getColumnName() + " = ?";
    }

    public static String updateById() {
        return "UPDATE author SET " +
                NAME.getColumnName() + " = ?, " +
                EMAIL.getColumnName() + " = ?, " +
                UPDATED_DATE.getColumnName() + " = ? " +
                "WHERE " + AUTHOR_ID.getColumnName() + " = ?";
    }

    public static String deleteById() {
        return "DELETE FROM author WHERE " + AUTHOR_ID.getColumnName() + " = ?";
    }
}

