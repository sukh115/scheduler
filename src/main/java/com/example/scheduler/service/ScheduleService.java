package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleAuthorDto;
import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleAuthorDto> findAllSchedule();

    Optional<ScheduleAuthorDto> findByAuthorId(Long authorId);

    ScheduleAuthorDto updateSchedule(Long scheduleId, String title, String content, Long authorId, String password);

    void deleteSchedule(Long scheduleId, String password);

    List<ScheduleAuthorDto> findAllSchedulePaged(int page, int size);

}
