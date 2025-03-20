package com.example.scheduler.service;

import com.example.scheduler.dto.AuthorRequestDto;
import com.example.scheduler.dto.AuthorResponseDto;
import com.example.scheduler.entity.Author;
import com.example.scheduler.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;

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

    @Override
    public Author findByAuthorId(Long authorId) {
        Author author = authorRepository.findAuthorByIdOrElseThrow(authorId);

        return new Author(author);
    }


    @Override
    public Author updateAuthor(Long authorId, String name, String email) {
        //  현재 시간 설정
        Timestamp updatedTime = new Timestamp(System.currentTimeMillis());

        // 작성자가 존재하는지 확인
        Author author = authorRepository.findEntityByAuthorId(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 작성자입니다."));

        // ️유효성 검사
        if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이름과 이메일을 입력해주세요.");
        }

        // 수정 수행
        int updatedRow = authorRepository.updateAuthor(authorId, name, email, updatedTime);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "작성자 수정 실패");
        }

        // 5️수정된 작성자 정보 반환
        return authorRepository.findAuthorByIdOrElseThrow(authorId);
    }


    @Override
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findEntityByAuthorId(authorId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        int deleteRow = authorRepository.deleteAuthor(authorId);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + authorId);
        }
    }
}
