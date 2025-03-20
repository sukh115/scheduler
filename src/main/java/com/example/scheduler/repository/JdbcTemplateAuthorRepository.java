package com.example.scheduler.repository;

import com.example.scheduler.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;
import com.example.scheduler.service.AuthorServiceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcTemplateAuthorRepository implements AuthorRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateAuthorRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @Override
    public AuthorResponseDto saveAuthor(Author author) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("author").usingGeneratedKeyColumns("author_id");

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("name", author.getName());
        parameter.put("email", author.getEmail());
        parameter.put("created_date", author.getCreatedDate());
        parameter.put("updated_date", author.getUpdatedDate());

        Number key = jdbcInsert.executeAndReturnKey(parameter);
        Long generatedId = key.longValue();

        return new AuthorResponseDto(
                generatedId,
                author.getName(),
                author.getEmail(),
                author.getUpdatedDate().toString()
        );
    }

    @Override
    public boolean existsByAuthorId(Long authorId) {
        String sql = "SELECT COUNT(*) FROM author WHERE author_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, authorId);
        return count != null && count > 0;
    }

}
