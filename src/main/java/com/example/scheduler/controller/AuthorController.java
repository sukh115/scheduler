package com.example.scheduler.controller;

import com.example.scheduler.dto.AuthorRequestDto;
import com.example.scheduler.dto.AuthorResponseDto;
import com.example.scheduler.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody AuthorRequestDto dto) {
        return new ResponseEntity<>(authorService.saveAuthor(dto), HttpStatus.CREATED);
    }
}
