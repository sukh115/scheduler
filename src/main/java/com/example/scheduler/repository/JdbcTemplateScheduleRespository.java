package com.example.scheduler.repository;

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
        parameters.put("user_name", schedule.getUserName());
        parameters.put("password", schedule.getPassword());
        parameters.put("create_date", schedule.getCreateDate());  // 추가
        parameters.put("updated_date", schedule.getUpdatedDate()); // 추가


        Number key = jdbcInsert.executeAndReturnKey(parameters);
        Long generatedId = key.longValue();

        return new ScheduleResponseDto(
                generatedId,
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUpdatedDate().toString(),
                schedule.getUserName()
        );
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule() {
        return jdbcTemplate.query("SELECT * FROM schedule", scheduleRowMapper());
    }

    @Override
    public Optional<ScheduleResponseDto> findScheduleById(Long id) {
        List<ScheduleResponseDto> result = jdbcTemplate.query("SELECT * FROM schedule WHERE schedule_id = ?", scheduleRowMapper(), id);

        return result.stream().findAny();
    }

    @Override
    public Optional<Schedule> findScheduleEntityById(Long id) {
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE schedule_id = ?", scheduleRowMapperV2(), id);

        return result.stream().findAny();
    }

    @Override
    public int updatedSchedule(Long id, String title, String content, Timestamp updated_time, String user_name) {
        return jdbcTemplate.update(
                "UPDATE schedule SET title = ?, content = ?, updated_date = ? WHERE schedule_id = ?",
                title, content, updated_time, id  //
        );
    }


    @Override
    public ScheduleResponseDto findScheduleByIdOrElseThrow(Long id) {
        List<ScheduleResponseDto> result = jdbcTemplate.query("SELECT * FROM schedule WHERE schedule_id = ?", scheduleRowMapper(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE schedule_id = ?", id);
    }


    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return (rs, rowNum) -> {
            Timestamp timestamp = rs.getTimestamp("updated_date");
            String formattedDate = timestamp.toLocalDateTime().toLocalDate().toString(); // ✅ YYYY-MM-DD 변환

            return new ScheduleResponseDto(
                    rs.getLong("schedule_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    formattedDate, // ✅ 변환된 값 적용
                    rs.getString("user_name")
            );
        };
    }


    private RowMapper<Schedule> scheduleRowMapperV2() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("schedule_id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getTimestamp("create_date"),
                rs.getTimestamp("updated_date"),
                rs.getString("user_name"),
                rs.getString("password")
        );
    }
}