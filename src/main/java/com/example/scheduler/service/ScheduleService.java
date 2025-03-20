package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleAuthorDto;
import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleAuthorDto> findAllSchedule();

    Optional<ScheduleAuthorDto> findByAuthorId(Long authorId);

    ScheduleAuthorDto updateSchedule(Long scheduleId, String title, String content, Long authorId, Timestamp updated_time, String password);

    void deleteSchedule(Long scheduleId, String password);
}
