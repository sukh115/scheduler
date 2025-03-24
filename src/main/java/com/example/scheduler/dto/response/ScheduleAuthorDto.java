package com.example.scheduler.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일정 정보 + 작성자 이름을 함께 담는 DTO
 * - 조회용 응답에 사용
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleAuthorDto {
    // 일정 제목
    private String title;

    // 일정 내용
    private String content;

    // 수정일시 (문자열 형태)
    private String updatedDate;

    // 작성자 이름
    private String Name;
}
