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
        Author author = authorRepository.findByAuthorId(dto.getAuthorId())
                .orElseThrow(() -> new CustomException(ExceptionCode.AUTHOR_NOT_FOUND));

        author.validateExistence();

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
    public ScheduleAuthorDto updateSchedule(Long scheduleId, String title, String content, Long authorId, String password) {
        Timestamp updatedTime = new Timestamp(System.currentTimeMillis());

        Schedule schedule = scheduleRepository.findScheduleEntityById(scheduleId)
                .orElseThrow(() -> new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND));

        schedule.validateAuthor(authorId);
        schedule.validatePassword(password);

        schedule.update(title, content, updatedTime);

        int updatedRow = scheduleRepository.updatedSchedule(scheduleId, title, content, updatedTime, authorId);
        if (updatedRow == 0) {
            throw new CustomException(ExceptionCode.SCHEDULE_UPDATE_FAILED);
        }

        return scheduleRepository.findScheduleByIdOrElseThrow(scheduleId);
    }

    @Override
    public void deleteSchedule(Long scheduleId, String password) {
        Schedule schedule = scheduleRepository.findScheduleEntityById(scheduleId)
                .orElseThrow(() -> new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND));

        schedule.validatePassword(password);
        int deletedRow = scheduleRepository.deleteSchedule(scheduleId);
        if (deletedRow == 0) {
            throw new CustomException(ExceptionCode.SCHEDULE_DELETE_FAILED);
        }
    }

    @Override
    public List<ScheduleAuthorDto> findAllSchedulePaged(int page, int size) {
        int offset = page * size;
        return scheduleRepository.findAllPaged(offset, size);
    }
}