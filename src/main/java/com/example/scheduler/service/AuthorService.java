package com.example.scheduler.service;

import com.example.scheduler.dto.AuthorRequestDto;
import com.example.scheduler.dto.AuthorResponseDto;
import org.springframework.stereotype.Service;

public interface AuthorService {

    AuthorResponseDto saveAuthor(AuthorRequestDto dto);

}
