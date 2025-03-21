package com.example.scheduler.repository;

import com.example.scheduler.dto.ScheduleAuthorDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
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
public class JdbcTemplateScheduleRespository implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRespository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("schedule_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", schedule.getTitle());
        parameters.put("content", schedule.getContent());
        parameters.put("author_id", schedule.getAuthorId());
        parameters.put("password", schedule.getPassword());
        parameters.put("create_date", schedule.getCreateDate());  // 추가
        parameters.put("updated_date", schedule.getUpdatedDate()); // 추가


        Number key = jdbcInsert.executeAndReturnKey(parameters);
        Long generatedId = key.longValue();

        return new ScheduleResponseDto(generatedId, schedule.getTitle(), schedule.getContent(), schedule.getUpdatedDate().toString(), schedule.getAuthorId());
    }

    @Override
    public List<ScheduleAuthorDto> findAllSchedule() {
        return jdbcTemplate.query("SELECT s.title, s.content, s.updated_date, a.name " + "FROM schedule s " + "JOIN author a ON s.author_id = a.author_id " + // 반드시 JOIN 필요
                "ORDER BY s.updated_date DESC", scheduleAuthorRowMapper());
    }


    @Override
    public Optional<Schedule> findScheduleEntityById(Long scheduleId) {
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE schedule_id = ?", scheduleRowMapperV2(), scheduleId);

        return result.stream().findAny();
    }

    @Override
    public Optional<ScheduleAuthorDto> findByAuthorId(Long authorId) {
        List<ScheduleAuthorDto> result = jdbcTemplate.query("SELECT s.title, s.content, s.updated_date, a.name " + "FROM schedule s " + "JOIN author a ON s.author_id = a.author_id " + "WHERE s.author_id = ? " + "ORDER BY s.updated_date DESC", scheduleAuthorRowMapper(), authorId);
        return result.stream().findAny();
    }


    @Override
    public int updatedSchedule(Long scheduleId, String title, String content, Timestamp updated_time, Long authorId) {
        return jdbcTemplate.update("UPDATE schedule SET title = ?, content = ?, updated_date = ? WHERE schedule_id = ?", title, content, updated_time, scheduleId  //
        );
    }


    @Override
    public ScheduleAuthorDto findScheduleByIdOrElseThrow(Long scheduleId) {
        List<ScheduleAuthorDto> result = jdbcTemplate.query("SELECT s.title, s.content, s.updated_date, a.name" + "FROM schedule s " + "JOIN author a ON s.author_id = a.author_id " + "WHERE s.schedule_id = ?", scheduleAuthorRowMapper(), scheduleId);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + scheduleId));
    }

    @Override
    public int deleteSchedule(Long scheduleId) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE schedule_id = ?", scheduleId);
    }


    private RowMapper<ScheduleAuthorDto> scheduleAuthorRowMapper() {
        return (rs, rowNum) -> {
            Timestamp timestamp = rs.getTimestamp("updated_date");
            String formattedDate = timestamp.toLocalDateTime().toLocalDate().toString();
            return new ScheduleAuthorDto(rs.getString("title"), rs.getString("content"), rs.getTimestamp("updated_date").toString(), // 날짜 변환
                    rs.getString("name") // 작성자 이름
            );
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return (rs, rowNum) -> new Schedule(rs.getLong("schedule_id"), rs.getString("title"), rs.getString("content"), rs.getTimestamp("create_date"), rs.getTimestamp("updated_date"), rs.getLong("author_id"), rs.getString("password"));
    }
}