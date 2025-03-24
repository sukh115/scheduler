package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleAuthorDto;
import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);           // 일정 저장

    List<ScheduleAuthorDto> findAllSchedule();                          // 전체 일정 조회

    Optional<ScheduleAuthorDto> findByAuthorId(Long authorId);          // 작성자 ID로 최신 일정 1건 조회

    ScheduleAuthorDto updateSchedule(Long scheduleId, String title, String content, Long authorId, String password); // 일정 수정

    void deleteSchedule(Long scheduleId, String password);              // 일정 삭제

    List<ScheduleAuthorDto> findAllSchedulePaged(int page, int size);   // 페이징

}
