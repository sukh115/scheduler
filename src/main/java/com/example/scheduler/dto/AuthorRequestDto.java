package com.example.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequestDto {

    @NotBlank(message = "이름을 적어주세요")
    private String name;

    @NotBlank(message = "이에일을 적어주세요")
    private String email;
}
