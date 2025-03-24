package com.example.scheduler.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 일정 등록 이후 응답으로 전달되는 DTO
 */
@Getter
@NoArgsConstructor
public class ScheduleResponseDto {
    // 일정 ID
    private Long id;

    // 일정 제목
    private String title;

    // 일정 내용
    private String content;

    // 수정 일시
    private String updatedDate;

    // 작성자 ID
    private Long authorId;

    public ScheduleResponseDto(Long id, String title, String content, String updateDate, Long authorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.updatedDate = updateDate;
        this.authorId = authorId;
    }
}
