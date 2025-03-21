package com.example.scheduler.controller;

import com.example.scheduler.dto.ScheduleAuthorDto;
import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ScheduleAuthorDto> findAllSchedule() {
        return scheduleService.findAllSchedule();
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<Optional<ScheduleAuthorDto>> findByAuthorId(@PathVariable Long authorId) {
        return new ResponseEntity<>(scheduleService.findByAuthorId(authorId), HttpStatus.OK);
    }


    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleAuthorDto> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequestDto dto
    ) {
        return new ResponseEntity<>(
                scheduleService.updateSchedule(scheduleId, dto.getTitle(), dto.getContent(), dto.getAuthorId(), dto.getPassword()),
                HttpStatus.OK
        );
    }


    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequestDto dto
    ) {
        scheduleService.deleteSchedule(scheduleId, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/paged")
    public List<ScheduleAuthorDto> findAllSchedulePaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return scheduleService.findAllSchedulePaged(page, size);
    }


}
