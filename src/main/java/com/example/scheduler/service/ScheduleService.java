package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> findAllSchedule();

    Optional<ScheduleResponseDto> findScheduleById(Long id);

    ScheduleResponseDto updateSchedule(Long id, String title, String content, Timestamp updated_time);

}
