package com.example.scheduler.service;

import com.example.scheduler.dto.AuthorRequestDto;
import com.example.scheduler.dto.AuthorResponseDto;

public interface AuthorService {

    AuthorResponseDto saveAuthor(AuthorRequestDto dto);

}
