package com.example.scheduler.repository;

import com.example.scheduler.dto.ScheduleAuthorDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.query.ScheduleQuery;
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

import static com.example.scheduler.constant.column.ScheduleColumns.*;
import static com.example.scheduler.query.ScheduleQuery.*;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 일정 저장
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
     * 전체 일정 조회
     */
    @Override
    public List<ScheduleAuthorDto> findAllSchedule() {
        return jdbcTemplate.query(
                findAllWithAuthor(),
                scheduleAuthorRowMapper()
        );
    }

    /**
     * 일정 ID로 단건 조회 (Entity)
     */
    @Override
    public Optional<Schedule> findScheduleEntityById(Long scheduleId) {
        List<Schedule> result = jdbcTemplate.query(
                findEntityById(),
                scheduleRowMapperV2(),
                scheduleId
        );
        return result.stream().findAny();
    }

    /**
     * 작성자 ID로 최신 일정 조회
     */
    @Override
    public Optional<ScheduleAuthorDto> findByAuthorId(Long authorId) {
        List<ScheduleAuthorDto> result = jdbcTemplate.query(
                ScheduleQuery.findByAuthorId(),
                scheduleAuthorRowMapper(),
                authorId
        );
        return result.stream().findAny();
    }

    /**
     * 전체 일정 페이징 조회
     */
    @Override
    public List<ScheduleAuthorDto> findAllPaged(int offset, int limit) {
        return jdbcTemplate.query(
                ScheduleQuery.findAllPaged(),
                scheduleAuthorRowMapper(),
                limit, offset
        );
    }

    /**
     * 일정 수정
     */
    @Override
    public int updatedSchedule(Long scheduleId, String title, String content, Timestamp updatedTime, Long authorId) {
        return jdbcTemplate.update(
                updateById(),
                title, content, updatedTime, scheduleId
        );
    }

    /**
     * 일정 ID로 조회 + 작성자 이름 포함
     */
    @Override
    public ScheduleAuthorDto findScheduleByIdOrElseThrow(Long scheduleId) {
        return jdbcTemplate.query(
                        findByIdWithAuthor(),
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
                deleteById(),
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
                    rs.getString("author_name") // 별칭으로 가져옴
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
