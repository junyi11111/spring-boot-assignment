# 백엔드 프레임워크 실습 과제

Spring Boot로 책 관리 API 만든 과제로 DB는 안 쓰고 서버 메모리에 그냥 저장하는 방식이다.

## 실행

```
./gradlew bootRun
```

포트는 8081로 고정했다. (8080은 다른 과제 프로젝트가 쓰고 있어서 겹치면 안 돼서 바꿨다.)

## Book이 가진 값

- id (자동으로 번호 매겨짐, 등록할 때 직접 안 넣음)
- title
- author
- price
- stock

## API 목록

총 8개, POST/GET/PUT/DELETE 각각 2개씩입니다.

1. POST /books - 책 등록 (성공 201 / 실패 400 - 제목 비었거나 가격 음수일 때)
2. POST /books/bulk - 여러 권 한번에 등록 (성공 201 / 실패 400 - 빈 배열 보낼 때)
3. GET /books - 전체 목록 조회 (200) - id, title, stock만 보여줌
4. GET /books/{id} - 한 권 상세 조회 (200 / 없는 id면 404) - 이건 다 보여줌
5. PUT /books/{id}/stock - 재고 수정 (200 / 400 음수 / 404 없는 id)
6. PUT /books/{id}/price - 가격 수정 (200 / 400 음수 / 404 없는 id)
7. DELETE /books/{id} - 한 권 삭제 (200 / 404)
8. DELETE /books - 전체 삭제 (200)

목록이랑 상세 조회는 응답 내용이 다르게 나갑니다. 목록에서 다 보여주면 너무 기니까 요약만, 상세는 전체 다 보여주는 식으로 나눴습니다.

## 응답 형식

수업 때 배운 대로 응답 모양 통일했습니다.

성공하면:
```
{
  "status": "success",
  "data": { ... }
}
```

실패하면:
```
{
  "status": "error",
  "message": "에러 내용"
}
```

## 상태 코드 정리

- 200, 201 : 성공
- 400 : 요청값이 이상함 (빈 제목, 음수 가격/재고 등)
- 404 : 없는 id 조회/수정/삭제하려고 할 때
- 500 : 예상 못한 에러 났을 때 (공통 예외처리기가 잡아줌)
- 503 : 점검모드 켰을 때 (아래에 설명)

## 미들웨어 (로깅 + 점검모드)

RequestLoggingFilter라는 필터 하나 만들어서 모든 요청 들어올 때마다 로그 찍게 했습니다 (무슨 메소드, 어떤 주소, 상태코드, 걸린시간).

점검모드도 이 필터에서 같이 처리했습니다. application.properties에 있는 `app.maintenance-mode` 값을 true로 바꾸고 재시작하면 어떤 요청을 보내도 전부 503으로 막아버립니다.

## 예외 처리

GlobalExceptionHandler 하나 만들어서 여기서 다 모아서 처리합니다.
- BookNotFoundException -> 404
- IllegalArgumentException -> 400
- 그 외 나머지 에러 -> 500

## 테스트

Postman으로 테스트함. 캡처는 `screenshots` 폴더에 있음.
