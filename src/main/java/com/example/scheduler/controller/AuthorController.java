package com.example.scheduler.controller;

import com.example.scheduler.dto.AuthorRequestDto;
import com.example.scheduler.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;
import com.example.scheduler.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody @Valid AuthorRequestDto dto) {
        return new ResponseEntity<>(authorService.saveAuthor(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> findByAuthorId(@PathVariable Long authorId) {
        return new ResponseEntity<>(authorService.findByAuthorId(authorId), HttpStatus.OK);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable Long authorId,
            @RequestBody @Valid AuthorRequestDto dto
    ) {
        return new ResponseEntity<>(
                authorService.updateAuthor(authorId, dto.getName(), dto.getEmail()),
                HttpStatus.OK
        );
    }


    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(
            @PathVariable Long authorId
    ) {
        authorService.deleteAuthor(authorId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
