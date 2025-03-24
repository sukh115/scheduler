package com.example.scheduler.controller;

import com.example.scheduler.dto.AuthorRequestDto;
import com.example.scheduler.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;
import com.example.scheduler.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 작성자 관련 HTTP 요청을 처리하는 컨트롤러
 * - 경로: /authors
 * - 기능: 생성, 조회, 수정, 삭제
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * 작성자를 등록합니다.
     *
     * @param dto 작성자 등록 요청 데이터 (이름, 이메일)
     * @return 등록된 작성자 정보 DTO
     */
    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody @Valid AuthorRequestDto dto) {
        return new ResponseEntity<>(authorService.saveAuthor(dto), HttpStatus.CREATED);
    }

    /**
     * 작성자 ID로 작성자를 조회합니다.
     *
     * @param authorId 조회할 작성자의 ID
     * @return 조회된 작성자 Entity
     */
    @GetMapping("/{authorId}")
    public ResponseEntity<Author> findByAuthorId(@PathVariable Long authorId) {
        return new ResponseEntity<>(authorService.findByAuthorId(authorId), HttpStatus.OK);
    }

    /**
     * 작성자 정보를 수정합니다.
     *
     * @param authorId 수정할 작성자의 ID
     * @param dto 수정할 작성자 데이터 (이름, 이메일)
     * @return 수정된 작성자 Entity
     */
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

    /**
     * 작성자를 삭제합니다.
     *
     * @param authorId 삭제할 작성자의 ID
     * @return HTTP 상태 코드 200 OK
     */
    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(
            @PathVariable Long authorId
    ) {
        authorService.deleteAuthor(authorId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
