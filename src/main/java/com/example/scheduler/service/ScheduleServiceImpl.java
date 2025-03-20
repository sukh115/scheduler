package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
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

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getTitle(), dto.getContent(), dto.getUserName(), dto.getPassword());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule() {
        return scheduleRepository.findAllSchedule();
    }

    @Override
    public Optional<ScheduleResponseDto> findScheduleById(Long id) {
        return scheduleRepository.findScheduleById(id);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, String title, String content, String user_name , Timestamp updated_time,String password) {
        Schedule schedule = scheduleRepository.findScheduleEntityById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        if (title == null || content == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목과 내용을 적어주세요.");
        }

        String updatedUserName = schedule.getUserName();
        Timestamp updatedTime = new Timestamp(System.currentTimeMillis());

        int updatedRow = scheduleRepository.updatedSchedule(id, title, content, updatedTime, updatedUserName);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정 수정 실패");
        }

        return scheduleRepository.findScheduleByIdOrElseThrow(id);
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findScheduleEntityById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        int deleteRow = scheduleRepository.deleteSchedule(id);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }

}
