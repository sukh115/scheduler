package com.example.scheduler.service;

import com.example.scheduler.dto.AuthorRequestDto;
import com.example.scheduler.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;

public interface AuthorService {

    AuthorResponseDto saveAuthor(AuthorRequestDto dto);

    Author findByAuthorId(Long authorId);

    Author updateAuthor(Long authorId, String name, String email);

    void deleteAuthor(Long authorId);

}
