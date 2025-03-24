package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleAuthorDto;
import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Author;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.exception.CustomException;
import com.example.scheduler.exception.exceptionCode.ExceptionCode;
import com.example.scheduler.repository.AuthorRepository;
import com.example.scheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * 일정 관련 비즈니스 로직을 처리하는 서비스 구현 클래스
 * - 작성자 유효성, 비밀번호 검증, 예외 처리 포함
 * - CRUD 및 페이징 기능 제공
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, AuthorRepository authorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.authorRepository = authorRepository;
    }

    // 일정 저장
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        // 작성자 존재 확인
        Author author = authorRepository.findByAuthorId(dto.getAuthorId())
                .orElseThrow(() -> new CustomException(ExceptionCode.AUTHOR_NOT_FOUND));

        author.validateExistence();

        // 일정 도메인 생성
        Schedule schedule = new Schedule(dto.getTitle(), dto.getContent(), dto.getAuthorId(), dto.getPassword());
        return scheduleRepository.saveSchedule(schedule);
    }

    // 전체 일정 조회
    @Override
    public List<ScheduleAuthorDto> findAllSchedule() {
        return scheduleRepository.findAllSchedule();
    }

    // 작성자 ID로 최신 일정 1건 조회
    @Override
    public Optional<ScheduleAuthorDto> findByAuthorId(Long authorId) {
        return scheduleRepository.findByAuthorId(authorId);
    }

    /**
     * 일정 수정 처리
     * - 일정 존재 확인
     * - 작성자 ID 및 비밀번호 검증
     * - 도메인 상태 변경 후 저장
     */
    @Override
    public ScheduleAuthorDto updateSchedule(Long scheduleId, String title, String content, Long authorId, String password) {
        Timestamp updatedTime = new Timestamp(System.currentTimeMillis());

        // 일정 조회 및 존재 확인
        Schedule schedule = scheduleRepository.findScheduleEntityById(scheduleId)
                .orElseThrow(() -> new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND));

        // 작성자 및 비밀번호 검증
        schedule.validateAuthor(authorId);
        schedule.validatePassword(password);

        // 도메인 상태 갱신
        schedule.update(title, content, updatedTime);

        // DB에 반영
        int updatedRow = scheduleRepository.updatedSchedule(scheduleId, title, content, updatedTime, authorId);
        if (updatedRow == 0) {
            throw new CustomException(ExceptionCode.SCHEDULE_UPDATE_FAILED);
        }

        return scheduleRepository.findScheduleByIdOrElseThrow(scheduleId);
    }

    // 일정 삭제
    @Override
    public void deleteSchedule(Long scheduleId, String password) {
        // 일정 존재 여부 확인
        Schedule schedule = scheduleRepository.findScheduleEntityById(scheduleId)
                .orElseThrow(() -> new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND));

        // 비밀번호 검증
        schedule.validatePassword(password);

        // 삭제 실행
        int deletedRow = scheduleRepository.deleteSchedule(scheduleId);
        if (deletedRow == 0) {
            throw new CustomException(ExceptionCode.SCHEDULE_DELETE_FAILED);
        }
    }

    // 페이징된 일정 조회
    @Override
    public List<ScheduleAuthorDto> findAllSchedulePaged(int page, int size) {
        int offset = page * size;
        return scheduleRepository.findAllPaged(offset, size);
    }
}