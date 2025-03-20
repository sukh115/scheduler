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
    public ScheduleAuthorDto updateSchedule(Long scheduleId, String title, String content, Long authorId, String password) {
        // 1️⃣ 현재 시간 설정
        Timestamp updatedTime = new Timestamp(System.currentTimeMillis());

        // 2️⃣ 일정이 존재하는지 확인
        Schedule schedule = scheduleRepository.findScheduleEntityById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다."));

        // 3️⃣ 작성자가 일치하는지 확인
        if (!schedule.getAuthorId().equals(authorId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성자만 일정을 수정할 수 있습니다.");
        }

        // 4️⃣ 비밀번호 검증
        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 5️⃣ 제목과 내용이 비어 있으면 예외 발생
        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목과 내용을 입력해주세요.");
        }

        // 6️⃣ 수정 수행
        int updatedRow = scheduleRepository.updatedSchedule(scheduleId, title, content, updatedTime, authorId);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정 수정 실패");
        }

        // 7️⃣ 수정된 일정 반환 (작성자 이름 포함)
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
