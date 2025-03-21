# scheduler

## ERD설계
![ERD.png](ERD.png)
## 일정 관리 API 명세서

| 기능                  | Method | URL                               | Request (JSON) | Response (JSON) | 상태 코드    |
|---------------------|--------|-----------------------------------|---------------|-----------------|----------|
| **일정 생성**           | `POST` | `/schedules`                      | ```json { "title": "회의", "content": "팀 미팅", "date": "2025-03-20", "password": "1234" } ``` | ```json { "id": 1, "title": "회의", "content": "팀 미팅", "date": "2025-03-20", "create_date": "2025-03-19T12:00:00", "update_date": "2025-03-19T12:00:00" } ``` | `201 Created` |
| 일정 전체 조회       | GET    | /schedules                        | -              | ```json[ { "title": "회의", "content": "팀 미팅", "updatedDate": "2025-03-20", "name": "홍길동" } ]``` | 200 OK |
| 일정 작성자별 조회   | GET    | /schedules/author/{authorId}      | -              | ```json{ "title": "회의", "content": "팀 미팅", "updatedDate": "2025-03-20", "name": "홍길동" }``` | 200 OK |
| 일정 페이징 조회     | GET    | /schedules/paged?page=0&size=5    | -              | ```json[ { "title": "회의", "content": "팀 미팅", "updatedDate": "2025-03-20", "name": "홍길동" } ]``` | 200 OK / 빈 배열 |
| 일정 수정            | PUT    | /schedules/{scheduleId}           | ```json{ "title": "변경된 제목", "content": "변경된 내용", "authorId": 1, "password": "1234" }``` | ```json{ "title": "변경된 제목", "content": "변경된 내용", "updatedDate": "2025-03-21", "name": "홍길동" }``` | 200 OK / 401 / 403 / 404 |
| 일정 삭제            | DELETE | /schedules/{scheduleId}           | ```json{ "password": "1234" }``` | -                | 200 OK / 401 / 404 |
| 작성자 등록          | POST   | /authors                          | ```json{ "name": "홍길동", "email": "hong@example.com" }``` | ```json{ "id": 1, "name": "홍길동", "email": "hong@example.com", "updatedDate": "2025-03-21" }``` | 201 Created |
| 작성자 조회          | GET    | /authors/{authorId}               | -              | ```json{ "authorId": 1, "name": "홍길동", "email": "hong@example.com", "createdDate": "2025-03-20", "updatedDate": "2025-03-21" }``` | 200 OK |
| 작성자 수정          | PUT    | /authors/{authorId}               | ```json{ "name": "수정된 이름", "email": "new@email.com" }``` | ```json{ "authorId": 1, "name": "수정된 이름", "email": "new@email.com", "createdDate": "2025-03-20", "updatedDate": "2025-03-21" }``` | 200 OK / 400 / 404 |
| 작성자 삭제          | DELETE | /authors/{authorId}               | -              |


---
---

## 사용 기술
- **Spring Boot 3.4.3**
- **Java 21**
- **MySQL**
- **Gradle**
- **JDBC Template**
- **Postman**
---

