package com.example.scheduler.repository;

import com.example.scheduler.dto.response.ScheduleAuthorDto;
import com.example.scheduler.dto.response.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

// Schedule(일정) 관련 Repository 인터페이스
public interface ScheduleRepository {

    // 일정 저장
    ScheduleResponseDto saveSchedule(Schedule schedule);

    // 전체 일정 목록 조회 (작성자 이름 포함)
    List<ScheduleAuthorDto> findAllSchedule();

    // 일정 ID로 엔티티 조회
    Optional<Schedule> findScheduleEntityById(Long scheduleId);

    // 작성자 ID로 일정 1건 조회 (작성자 이름 포함)
    List<ScheduleAuthorDto> findAllByAuthorId(Long authorId);

    // 전체 일정 페이징 목록 조회 (작성자 포함)
    List<ScheduleAuthorDto> findAllPaged(int offset, int limit);

    // 일정 수정
    int updateSchedule(Long scheduleId, String title, String content, Timestamp updated_time);

    // 일정 ID로 일정 조회 (작성자 포함) - 없으면 예외 발생
    ScheduleAuthorDto findScheduleByIdOrElseThrow(Long scheduleId);

    // 일정 삭제
    int deleteSchedule(Long scheduleId);
}

