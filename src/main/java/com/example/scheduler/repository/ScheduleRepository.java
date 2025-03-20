package com.example.scheduler.repository;

import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedule();

    Optional<ScheduleResponseDto> findScheduleById(Long id);
    Optional<Schedule> findScheduleEntityById(Long id);


    int updatedSchedule(Long id, String title, String content, Timestamp updated_time, String user_name);

    ScheduleResponseDto findScheduleByIdOrElseTheow(Long id);

    int deleteSchedule(Long id);

}
