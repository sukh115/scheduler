package com.example.scheduler.repository;

import com.example.scheduler.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Override
    public Author findAuthorByIdOrElseThrow(Long authorId) {
        List<Author> result = jdbcTemplate.query("SELECT * FROM author WHERE author_id = ?", authorRowMapper, authorId);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + authorId));
    }

    @Override
    public Optional<Author> findEntityByAuthorId(Long authorId) {
        List<Author> result = jdbcTemplate.query("SELECT * FROM author WHERE author_id = ?", authorRowMapper, authorId);

        return result.stream().findAny();
    }

    @Override
    public Optional<Author> findByAuthorId(Long authorId) {
        List<Author> result = jdbcTemplate.query("SELECT * FROM author WHERE author_id = ?", authorRowMapper, authorId);
        return result.stream().findAny();
    }

    @Override
    public int updateAuthor(Long authorId, String name, String email, Timestamp updatedTime) {
        return jdbcTemplate.update(
                "UPDATE author SET name = ?, email = ?, updated_date = ? WHERE author_id = ?",
                name, email, updatedTime, authorId
        );
    }

    @Override
    public int deleteAuthor(Long authorId) {
        return jdbcTemplate.update("DELETE FROM author WHERE author_id = ?", authorId);
    }

    private final RowMapper<Author> authorRowMapper = (rs, rowNum) ->
            new Author(
                    rs.getLong("author_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getTimestamp("created_date"),
                    rs.getTimestamp("updated_date")
            );

    private final RowMapper<AuthorResponseDto> authorResponseDtoRowMapper = (rs, rowNum) ->
            new AuthorResponseDto(
                    rs.getLong("author_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getTimestamp("updated_date").toString()
            );


}
