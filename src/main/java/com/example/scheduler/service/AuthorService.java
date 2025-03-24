package com.example.scheduler.service;

import com.example.scheduler.dto.AuthorRequestDto;
import com.example.scheduler.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;

/**
 * 작성자 관련 비즈니스 로직을 정의한 서비스 인터페이스
 */
public interface AuthorService {

    AuthorResponseDto saveAuthor(AuthorRequestDto dto);             // 작성자 등록

    Author findByAuthorId(Long authorId);                           // 작성자 단건 조회

    Author updateAuthor(Long authorId, String name, String email);  // 작성자 정보 수정

    void deleteAuthor(Long authorId);                               // 작성자 삭제

}
