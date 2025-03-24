package com.example.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 작성자 등록 및 수정 요청에 사용되는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequestDto {

    // 작성자 이름
    @NotBlank(message = "이름을 적어주세요")
    private String name;

    // 작성자 이메일
    @NotBlank(message = "이에일을 적어주세요")
    private String email;
}
