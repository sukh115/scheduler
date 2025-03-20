package com.example.scheduler.repository;

import com.example.scheduler.dto.ScheduleAuthorDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleAuthorDto> findAllSchedule();

    Optional<Schedule> findScheduleEntityById(Long scheduleId);
    Optional<ScheduleAuthorDto> findByAuthorId(Long authorId);

    int updatedSchedule(Long scheduleId, String title, String content, Timestamp updated_time, Long authorId);

    ScheduleAuthorDto findScheduleByIdOrElseThrow(Long authorId);

    int deleteSchedule(Long scheduleId);

}
