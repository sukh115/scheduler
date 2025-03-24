package com.example.scheduler.repository;

import com.example.scheduler.dto.response.AuthorResponseDto;
import com.example.scheduler.entity.Author;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * 작성자(author) 테이블의 컬럼명을 정의한 Enum
 */
public interface AuthorRepository {

    // 작성자 저장
    AuthorResponseDto saveAuthor(Author author);

    // 작성자 존재 여부 확인
    boolean existsByAuthorId(Long authorId);

    // 작성자 ID로 조회 (없으면 예외)
    Author findAuthorByIdOrElseThrow(Long authorId);

    // 작성자 ID로 Optional 조회
    Optional<Author> findByAuthorId(Long authorId);

    // 작성자 정보 수정
    int updateAuthor(Long authorId, String name, String email, Timestamp updatedTime);

    // 작성자 삭제
    int deleteAuthor(Long authorId);

}
