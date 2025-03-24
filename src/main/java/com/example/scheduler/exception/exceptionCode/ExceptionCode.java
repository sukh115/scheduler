package com.example.scheduler.exception.exceptionCode;

import lombok.Getter;

/**
 * 도메인 및 공통 예외 상황에 대한 코드 정의 Enum
 * - 상태 코드, 에러 코드 문자열, 메시지를 포함
 */
@Getter
public enum ExceptionCode {
    // Author 관련
    AUTHOR_NOT_FOUND(404, "AUTHOR_001", "작성자를 찾을 수 없습니다."),
    AUTHOR_INVALID_INPUT(400, "AUTHOR_002", "이름과 이메일을 입력해주세요."),
    AUTHOR_UPDATE_FAILED(404, "AUTHOR_003", "작성자 수정에 실패했습니다."),
    AUTHOR_DELETE_FAILED(404, "AUTHOR_004", "작성자 삭제에 실패했습니다."),
    AUTHOR_EMAIL_DUPLICATED(409, "AUTHOR_005", "이미 사용 중인 이메일입니다."),

    // Schedule 관련
    SCHEDULE_NOT_FOUND(404, "SCHEDULE_001", "존재하지 않는 일정입니다."),
    SCHEDULE_INVALID_INPUT(400, "SCHEDULE_002", "제목과 내용을 입력해주세요."),
    SCHEDULE_UPDATE_FAILED(404, "SCHEDULE_003", "일정 수정 실패"),
    SCHEDULE_DELETE_FAILED(404, "SCHEDULE_004", "일정 삭제 실패"),

    // 인증 관련
    UNAUTHORIZED_AUTHOR(403, "AUTH_001", "작성자만 일정을 수정할 수 있습니다."),
    PASSWORD_MISMATCH(401, "AUTH_002", "비밀번호가 일치하지 않습니다."),

    // 공통
    PAGE_OUT_OF_RANGE(400, "COMMON_001", "요청한 페이지 범위가 유효하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;

    ExceptionCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
