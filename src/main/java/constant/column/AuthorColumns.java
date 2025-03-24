package constant.column;

import lombok.Getter;

/**
 * 작성자(author) 테이블의 컬럼명을 정의한 Enum
 */
@Getter
public enum AuthorColumns {
    AUTHOR_ID("author_id"),           // 작성자 ID (PK)
    NAME("name"),                     // 작성자 이름
    EMAIL("email"),                   // 이메일
    CREATED_DATE("created_date"),     // 생성일시
    UPDATED_DATE("updated_date");     // 수정일시

    private final String columnName;

    AuthorColumns(String columnName) {
        this.columnName = columnName;
    }
}
