package com.example.scheduler.service;

import com.example.scheduler.dto.AuthorRequestDto;
import com.example.scheduler.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;
import com.example.scheduler.exception.CustomException;
import com.example.scheduler.exception.exceptionCode.ExceptionCode;
import com.example.scheduler.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorResponseDto saveAuthor(AuthorRequestDto dto) {
        Author author = new Author(dto.getName(), dto.getEmail());
        return authorRepository.saveAuthor(author);
    }

    @Override
    public Author findByAuthorId(Long authorId) {
        Author author = authorRepository.findAuthorByIdOrElseThrow(authorId);
        return new Author(author);
    }


    @Override
    public Author updateAuthor(Long authorId, String name, String email) {
        Author author = authorRepository.findByAuthorId(authorId)
                .orElseThrow(() -> new CustomException(ExceptionCode.AUTHOR_NOT_FOUND));

        author.update(name, email, new Timestamp(System.currentTimeMillis())); // ✅ 여기!

        int updatedRow = authorRepository.updateAuthor(authorId, author.getName(), author.getEmail(), author.getUpdatedDate());
        if (updatedRow == 0) {
            throw new CustomException(ExceptionCode.AUTHOR_UPDATE_FAILED);
        }

        return authorRepository.findAuthorByIdOrElseThrow(authorId);
    }



    @Override
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findByAuthorId(authorId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        int deleted = authorRepository.deleteAuthor(authorId);
        if (deleted == 0) {
            throw new CustomException(ExceptionCode.AUTHOR_DELETE_FAILED);
        }
    }
}
