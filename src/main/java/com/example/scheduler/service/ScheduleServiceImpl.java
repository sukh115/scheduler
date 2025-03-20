package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleAuthorDto;
import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.repository.AuthorRepository;
import com.example.scheduler.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, AuthorRepository authorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.authorRepository = authorRepository;
    }


    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        if (!authorRepository.existsByAuthorId(dto.getAuthorId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 작성자입니다.");
        }

        Schedule schedule = new Schedule(dto.getTitle(), dto.getContent(), dto.getAuthorId(), dto.getPassword());
        return scheduleRepository.saveSchedule(schedule);
    }



    @Override
    public List<ScheduleAuthorDto> findAllSchedule() {
        return scheduleRepository.findAllSchedule();
    }

    @Override
    public Optional<ScheduleAuthorDto> findByAuthorId(Long authorId) {
        return scheduleRepository.findByAuthorId(authorId);
    }

    @Override
    public ScheduleAuthorDto updateSchedule(Long scheduleId, String title, String content, Long authorId , Timestamp updated_time, String password) {
        Schedule schedule = scheduleRepository.findScheduleEntityById(scheduleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        if (title == null || content == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목과 내용을 적어주세요.");
        }

        Timestamp updatedTime = new Timestamp(System.currentTimeMillis());

        int updatedRow = scheduleRepository.updatedSchedule(scheduleId, title, content, updatedTime, authorId);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정 수정 실패");
        }

        return scheduleRepository.findScheduleByIdOrElseThrow(scheduleId);
    }

    @Override
    public void deleteSchedule(Long scheduleId, String password) {
        Schedule schedule = scheduleRepository.findScheduleEntityById(scheduleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        int deleteRow = scheduleRepository.deleteSchedule(scheduleId);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + scheduleId);
        }
    }

}
