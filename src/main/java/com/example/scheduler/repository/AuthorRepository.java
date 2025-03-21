package com.example.scheduler.repository;

import com.example.scheduler.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;

import java.sql.Timestamp;
import java.util.Optional;

public interface AuthorRepository {

    AuthorResponseDto saveAuthor(Author author);

    boolean existsByAuthorId(Long authorId);

    Author findAuthorByIdOrElseThrow(Long authorId);

    Optional<Author> findEntityByAuthorId(Long authorId);

    Optional<Author> findByAuthorId(Long authorId);

    int updateAuthor(Long authorId, String name, String email, Timestamp updatedTime);

    int deleteAuthor(Long authorId);

}
