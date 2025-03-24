package com.example.scheduler.repository;

import com.example.scheduler.dto.ScheduleAuthorDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import constant.column.AuthorColumns;
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

import static constant.column.ScheduleColumns.*;

@Repository
public class JdbcTemplateScheduleRespository implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRespository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 일정 저장 - SimpleJdbcInsert로 schedule 테이블에 저장 후 ID 반환
     */
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns(SCHEDULE_ID.getColumnName());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TITLE.getColumnName(), schedule.getTitle());
        parameters.put(CONTENT.getColumnName(), schedule.getContent());
        parameters.put(AUTHOR_ID.getColumnName(), schedule.getAuthorId());
        parameters.put(PASSWORD.getColumnName(), schedule.getPassword());
        parameters.put(CREATE_DATE.getColumnName(), schedule.getCreateDate());
        parameters.put(UPDATED_DATE.getColumnName(), schedule.getUpdatedDate());

        Number key = jdbcInsert.executeAndReturnKey(parameters);
        Long generatedId = key.longValue();

        return new ScheduleResponseDto(
                generatedId,
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUpdatedDate().toString(),
                schedule.getAuthorId()
        );
    }

    /**
     * 전체 일정 조회 - 작성자 이름 포함, 최신순 정렬
     */
    @Override
    public List<ScheduleAuthorDto> findAllSchedule() {
        return jdbcTemplate.query(
                "SELECT s." + TITLE.getColumnName() + ", s." + CONTENT.getColumnName() + ", s." + UPDATED_DATE.getColumnName() + " = a." + AuthorColumns.NAME.getColumnName() +
                        "FROM schedule s " +
                        "JOIN author a ON s." + AUTHOR_ID.getColumnName() + " = a. " + AuthorColumns.AUTHOR_ID.getColumnName() +
                        "ORDER BY s." + UPDATED_DATE.getColumnName() + " DESC",
                scheduleAuthorRowMapper()
        );
    }

    /**
     * 일정 ID로 단건 조회 (Entity 반환)
     */
    @Override
    public Optional<Schedule> findScheduleEntityById(Long scheduleId) {
        List<Schedule> result = jdbcTemplate.query(
                "SELECT * FROM schedule WHERE " + SCHEDULE_ID.getColumnName() + " = ?",
                scheduleRowMapperV2(),
                scheduleId
        );
        return result.stream().findAny();
    }

    /**
     * 작성자 ID로 최신 일정 1건 조회
     */
    @Override
    public Optional<ScheduleAuthorDto> findByAuthorId(Long authorId) {
        List<ScheduleAuthorDto> result = jdbcTemplate.query(
                "SELECT s." + TITLE.getColumnName() + ", s." + CONTENT.getColumnName() + ", s." + UPDATED_DATE.getColumnName() + " = a." + AuthorColumns.NAME.getColumnName() +
                        "FROM schedule s " +
                        "JOIN author a ON s." + AUTHOR_ID.getColumnName() + " = a. " + AuthorColumns.AUTHOR_ID.getColumnName() +
                        "WHERE s." + AUTHOR_ID.getColumnName() + " = ? " +
                        "ORDER BY s." + UPDATED_DATE.getColumnName() + " DESC",
                scheduleAuthorRowMapper(),
                authorId
        );
        return result.stream().findAny();
    }

    /**
     * 페이징 처리된 전체 일정 조회 (작성자 포함)
     */
    @Override
    public List<ScheduleAuthorDto> findAllPaged(int offset, int limit) {
        return jdbcTemplate.query(
                "SELECT s." + TITLE.getColumnName() + ", s." + CONTENT.getColumnName() + ", s." + UPDATED_DATE.getColumnName() + " = a." + AuthorColumns.NAME.getColumnName() +
                        "FROM schedule s " +
                        "JOIN author a ON s." + AUTHOR_ID.getColumnName() + " = a. " + AuthorColumns.AUTHOR_ID.getColumnName() +
                        "ORDER BY s." + UPDATED_DATE.getColumnName() + " DESC " +
                        "LIMIT ? OFFSET ?",
                scheduleAuthorRowMapper(),
                limit, offset
        );
    }

    /**
     * 일정 수정
     */
    @Override
    public int updatedSchedule(Long scheduleId, String title, String content, Timestamp updated_time, Long authorId) {
        return jdbcTemplate.update(
                "UPDATE schedule SET " +
                        TITLE.getColumnName() + " = ?, " +
                        CONTENT.getColumnName() + " = ?, " +
                        UPDATED_DATE.getColumnName() + " = ? " +
                        "WHERE " + SCHEDULE_ID.getColumnName() + " = ?",
                title, content, updated_time, scheduleId
        );
    }

    /**
     * 일정 ID로 조회 + 작성자 이름 포함 (예외 발생 가능)
     */
    @Override
    public ScheduleAuthorDto findScheduleByIdOrElseThrow(Long scheduleId) {
        return jdbcTemplate.query(
                        "SELECT s." + TITLE.getColumnName() + ", s." + CONTENT.getColumnName() + ", s." + UPDATED_DATE.getColumnName() + " = a." + AuthorColumns.NAME.getColumnName() +
                                "FROM schedule s " +
                                "JOIN author a ON s." + AUTHOR_ID.getColumnName() + " = a. " + AuthorColumns.AUTHOR_ID.getColumnName() +
                                "WHERE s." + SCHEDULE_ID.getColumnName() + " = ?",
                        scheduleAuthorRowMapper(),
                        scheduleId
                )
                .stream()
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + scheduleId));
    }

    /**
     * 일정 삭제
     */
    @Override
    public int deleteSchedule(Long scheduleId) {
        return jdbcTemplate.update(
                "DELETE FROM schedule WHERE " + SCHEDULE_ID.getColumnName() + " = ?",
                scheduleId
        );
    }

    /**
     * RowMapper: 일정 + 작성자 이름 DTO 매핑
     */
    private RowMapper<ScheduleAuthorDto> scheduleAuthorRowMapper() {
        return (rs, rowNum) -> {
            Timestamp timestamp = rs.getTimestamp(UPDATED_DATE.getColumnName());
            return new ScheduleAuthorDto(
                    rs.getString(TITLE.getColumnName()),
                    rs.getString(CONTENT.getColumnName()),
                    timestamp.toString(),
                    rs.getString(AuthorColumns.NAME.getColumnName())
            );
        };
    }

    /**
     * RowMapper: Schedule Entity 매핑
     */
    private RowMapper<Schedule> scheduleRowMapperV2() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong(SCHEDULE_ID.getColumnName()),
                rs.getString(TITLE.getColumnName()),
                rs.getString(CONTENT.getColumnName()),
                rs.getTimestamp(CREATE_DATE.getColumnName()),
                rs.getTimestamp(UPDATED_DATE.getColumnName()),
                rs.getLong(AUTHOR_ID.getColumnName()),
                rs.getString(PASSWORD.getColumnName())
        );
    }
}
