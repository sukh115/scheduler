package com.example.scheduler.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthorResponseDto {
    private Long id;
    private String name;
    private String email;
    private String updatedDate;

    public AuthorResponseDto(Long id, String name, String email, String updatedDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.updatedDate = updatedDate;
    }
}
