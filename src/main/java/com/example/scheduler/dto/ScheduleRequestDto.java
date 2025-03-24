package com.example.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 일정 등록 및 수정 요청에 사용되는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {

    // 일정 제목
    @NotBlank(message = "제목을 적어주세요.")
    private String title;

    // 일정 내용
    @NotBlank(message = "내용을 적어주세요.")
    private String content;

    // 작성자 ID
    @NotNull(message = "작성자 ID가 없습니다.")
    private Long authorId;

    // 일정 수정/삭제용 비밀번호
    @NotNull(message = "비밀번호가 없습니다.")
    private String password;
}
