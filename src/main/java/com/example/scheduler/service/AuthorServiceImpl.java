package com.example.scheduler.service;

import com.example.scheduler.dto.request.AuthorRequestDto;
import com.example.scheduler.dto.response.AuthorResponseDto;
import com.example.scheduler.entity.Author;
import com.example.scheduler.exception.CustomException;
import com.example.scheduler.exception.exceptionCode.ExceptionCode;
import com.example.scheduler.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;

/**
 * 작성자 관련 비즈니스 로직을 처리하는 서비스 구현 클래스
 * - 등록, 조회, 수정, 삭제 기능 포함
 * - 예외 상황에 대한 검증 및 처리 수행
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // 작성자 저장
    @Override
    public AuthorResponseDto saveAuthor(AuthorRequestDto dto) {
        Author author = new Author(dto.getName(), dto.getEmail());
        return authorRepository.saveAuthor(author);
    }

    // 작성자 단건 조회
    @Override
    public Author findByAuthorId(Long authorId) {
        Author author = authorRepository.findAuthorByIdOrElseThrow(authorId);
        return new Author(author);
    }

    /**
     * 작성자 정보 수정 처리
     * - 존재 여부 검증
     * - 수정 시각 갱신
     * - 수정 실패 시 예외 발생
     */
    @Override
    public Author updateAuthor(Long authorId, String name, String email) {
        // 존재하는 작성자인지 확인
        Author author = authorRepository.findByAuthorId(authorId)
                .orElseThrow(() -> new CustomException(ExceptionCode.AUTHOR_NOT_FOUND));

        // 도메인 로직으로 상태 변경
        author.update(name, email, new Timestamp(System.currentTimeMillis())); // ✅ 여기!

        // DB 수정
        int updatedRow = authorRepository.updateAuthor(authorId, author.getName(), author.getEmail(), author.getUpdatedDate());
        if (updatedRow == 0) {
            throw new CustomException(ExceptionCode.AUTHOR_UPDATE_FAILED);
        }

        return authorRepository.findAuthorByIdOrElseThrow(authorId);
    }


    // 작성자 삭제
    @Override
    public void deleteAuthor(Long authorId) {
        // 존재 여부 확인
        Author author = authorRepository.findByAuthorId(authorId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // DB 삭제 요청
        int deleted = authorRepository.deleteAuthor(authorId);
        if (deleted == 0) {
            throw new CustomException(ExceptionCode.AUTHOR_DELETE_FAILED);
        }
    }
}
