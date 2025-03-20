package com.example.scheduler.repository;

import com.example.scheduler.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;

public interface AuthorRepository {

    AuthorResponseDto saveAuthor(Author author);
    boolean existsByAuthorId(Long authorId);

}
