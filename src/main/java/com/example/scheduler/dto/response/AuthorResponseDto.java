package com.example.scheduler.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 작성자 등록/조회/수정 이후 응답으로 전달되는 DTO
 */
@Getter
@NoArgsConstructor
public class AuthorResponseDto {
    // 작성자 ID
    private Long id;

    // 작성자 이름
    private String name;

    // 작성자 이메일
    private String email;

    // 마지막 수정 일시
    private String updatedDate;

    public AuthorResponseDto(Long id, String name, String email, String updatedDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.updatedDate = updatedDate;
    }
}
