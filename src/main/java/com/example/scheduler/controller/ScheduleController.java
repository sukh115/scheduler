package com.example.scheduler.controller;

import com.example.scheduler.dto.ScheduleAuthorDto;
import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * 일정 관련 HTTP 요청을 처리하는 컨트롤러
 * - 경로: /schedules
 * - 기능: 일정 생성, 전체 조회, 작성자별 조회, 수정, 삭제, 페이징 조회
 */
@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 일정을 생성합니다.
     *
     * @param dto 일정 생성 요청 데이터 (제목, 내용, 작성자 ID, 비밀번호)
     * @return 생성된 일정의 응답 DTO
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    /**
     * 전체 일정 목록을 조회합니다.
     * (작성자 이름 포함)
     *
     * @return 일정 + 작성자 정보를 담은 리스트
     */
    @GetMapping
    public List<ScheduleAuthorDto> findAllSchedule() {
        return scheduleService.findAllSchedule();
    }

    /**
     * 작성자 ID로 가장 최신 일정 1건을 조회합니다.
     *
     * @param authorId 작성자 ID
     * @return Optional로 감싼 일정 + 작성자 DTO
     */
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<ScheduleAuthorDto>> findByAuthorId(@PathVariable Long authorId) {
        return new ResponseEntity<>(scheduleService.findAllByAuthorId(authorId), HttpStatus.OK);
    }

    /**
     * 일정을 수정합니다.
     *
     * @param scheduleId 수정할 일정 ID
     * @param dto        일정 수정 요청 데이터 (제목, 내용, 작성자 ID, 비밀번호)
     * @return 수정된 일정 + 작성자 DTO
     */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleAuthorDto> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody @Valid ScheduleRequestDto dto
    ) {
        return new ResponseEntity<>(
                scheduleService.updateSchedule(scheduleId, dto.getTitle(), dto.getContent(), dto.getAuthorId(), dto.getPassword()),
                HttpStatus.OK
        );
    }

    /**
     * 일정을 삭제합니다.
     *
     * @param scheduleId 삭제할 일정 ID
     * @param dto        일정 삭제 요청 데이터 (비밀번호)
     * @return HTTP 상태 코드 200 OK
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequestDto dto
    ) {
        scheduleService.deleteSchedule(scheduleId, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 전체 일정 목록을 페이지네이션하여 조회합니다.
     *
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 페이징된 일정 + 작성자 정보 리스트
     */
    @GetMapping("/paged")
    public List<ScheduleAuthorDto> findAllSchedulePaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return scheduleService.findAllSchedulePaged(page, size);
    }


}
