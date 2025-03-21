package com.example.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {

    @NotBlank(message = "제목을 적어주세요.")
    private String title;

    @NotBlank(message = "내용을 적어주세요.")
    private String content;

    @NotNull(message = "작성자 ID가 없습니다.")
    private Long authorId;

    @NotNull(message = "비밀번호가 없습니다.")
    private String password;
}
