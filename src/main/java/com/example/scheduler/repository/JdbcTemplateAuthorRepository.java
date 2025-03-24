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

import static com.example.scheduler.constant.column.AuthorColumns.*;
import static com.example.scheduler.query.AuthorQuery.*;

/**
 * 작성자 데이터를 JDBC 기반으로 관리하는 Repository 구현체
 */
@Repository
public class JdbcTemplateAuthorRepository implements AuthorRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateAuthorRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 작성자 저장 - author 테이블에 데이터를 저장하고 ID 반환
     */
    @Override
    public AuthorResponseDto saveAuthor(Author author) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("author").usingGeneratedKeyColumns(AUTHOR_ID.getColumnName());

        Map<String, Object> parameter = new HashMap<>();
        parameter.put(NAME.getColumnName(), author.getName());
        parameter.put(EMAIL.getColumnName(), author.getEmail());
        parameter.put(CREATED_DATE.getColumnName(), author.getCreatedDate());
        parameter.put(UPDATED_DATE.getColumnName(), author.getUpdatedDate());

        Number key = jdbcInsert.executeAndReturnKey(parameter);
        Long generatedId = key.longValue();

        return new AuthorResponseDto(
                generatedId,
                author.getName(),
                author.getEmail(),
                author.getUpdatedDate().toString()
        );
    }

    /**
     * 작성자 존재 여부 확인
     * - 조건: author_id
     * - 반환: 존재하면 true, 없으면 false
     */
    @Override
    public boolean existsByAuthorId(Long authorId) {
        Integer count = jdbcTemplate.queryForObject(
                existsById(),
                Integer.class,
                authorId
        );
        return count != null && count > 0;
    }

    /**
     * 작성자 단건 조회 (예외 발생)
     * - 조건: author_id
     * - 없을 경우 NOT_FOUND 예외 발생
     */
    @Override
    public Author findAuthorByIdOrElseThrow(Long authorId) {
        List<Author> result = jdbcTemplate.query(
                findById(),
                authorRowMapper,
                authorId
        );
        return result.stream().findAny().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + authorId)
        );
    }

    /**
     * 작성자 단건 조회 (Optional 반환)
     * - 조건: author_id
     */
    @Override
    public Optional<Author> findByAuthorId(Long authorId) {
        List<Author> result = jdbcTemplate.query(
                findById(),
                authorRowMapper,
                authorId
        );
        return result.stream().findAny();
    }

    /**
     * 작성자 정보 수정
     * - name, email, updated_date 갱신
     * - 조건: author_id
     */
    @Override
    public int updateAuthor(Long authorId, String name, String email, Timestamp updatedTime) {
        return jdbcTemplate.update(
                updateById(),
                name, email, updatedTime, authorId
        );
    }

    /**
     * 작성자 삭제
     * - 조건: author_id
     */
    @Override
    public int deleteAuthor(Long authorId) {
        return jdbcTemplate.update(
                deleteById(),
                authorId
        );
    }

    /**
     * RowMapper: Author 엔티티 매핑
     * - 전체 필드 포함
     */
    private final RowMapper<Author> authorRowMapper = (rs, rowNum) ->
            new Author(
                    rs.getLong(AUTHOR_ID.getColumnName()),
                    rs.getString(NAME.getColumnName()),
                    rs.getString(EMAIL.getColumnName()),
                    rs.getTimestamp(CREATED_DATE.getColumnName()),
                    rs.getTimestamp(UPDATED_DATE.getColumnName())
            );

    /**
     * RowMapper: Author DTO 매핑
     * - 일부 필드만 사용 (응답용)
     */
    private final RowMapper<AuthorResponseDto> authorResponseDtoRowMapper = (rs, rowNum) ->
            new AuthorResponseDto(
                    rs.getLong(AUTHOR_ID.getColumnName()),
                    rs.getString(NAME.getColumnName()),
                    rs.getString(EMAIL.getColumnName()),
                    rs.getTimestamp(UPDATED_DATE.getColumnName()).toString()
            );
}
