package com.example.scheduler.service;

import com.example.scheduler.dto.AuthorRequestDto;
import com.example.scheduler.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;
import com.example.scheduler.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorResponseDto saveAuthor(AuthorRequestDto dto) {
        Author author = new Author(dto.getName(), dto.getEmail());
        return authorRepository.saveAuthor(author);
    }
}
