# 원티드 프리온보딩 백엔드 인턴십 - 선발 과제

## 지원자의 성명

임상현

## 애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)

배포 주소인 http://43.202.65.20 (포트 8080) 에서 확인 가능합니다.

엔드포인트는 API 명세서에서 확인 가능합니다.


## Docker Compose


<details>

<summary><B>docker-compose.xml 열어보기</B></summary>

~~~
version: '3'

services:
  database:
    container_name: mysql_db
    image: mysql:8.0.33
    environment:
      MYSQL_DATABASE: community
      MYSQL_ROOT_PASSWORD: wantedpreonboarding
      TZ: 'Asia/Seoul'
    ports:
      - "3306:3306"
    volumes:
      - mysql_data_volume:/var/lib/mysql
      - ./sql:/docker-entrypoint-initdb.d
    command: [ '--character-set-server=utf8mb4', '--collation-server=utf8mb4_unicode_ci' ]
    networks:
      - myapp_network

  application:
    container_name: onboarding
    build:
      context: ./
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql_db:3306/community?serverTimezone=UTC
      SPRING_DATASOURCE_USERNAME: "root"
      SPRING_DATASOURCE_PASSWORD: "wantedpreonboarding"
    depends_on:
      - database
    networks:
      - myapp_network


networks:
  myapp_network:


volumes:
  mysql_data_volume:
~~~
</details> 

docker에 MySql images를 생성하여, container 종료시 DB가 사라지는 문제를 해결하기 위해 sql이 저장되는 Volume을 추가

docker-compose build 시 backup.sql에서 초기값 불러오도록 구현

## 데이터베이스 테이블 구조

![image](https://github.com/noyes5/wanted-pre-onboarding-backend/assets/116651434/4815b180-b951-46bd-9c2b-9f3028d56b31)

## 구현한 API의 동작을 촬영한 데모 영상 링크

<a href="https://youtu.be/5ILClDgR3PU" target="_blank">데모 영상 링크</a>

## 구현 방법 및 이유에 대한 간략한 설명

### 중복 이메일 예외 처리
 - 식별자인 user_id로 회원을 구분하므로, 중복 이메일로 가입될 여지가 있어 예외 처리

### 비밀번호 암호화
 - DB에 비밀번호가 직접적으로 저장되지 않고, BCryptPasswordEncoder로 encode 되어 저장

### 로그인 예외 처리
- 로그인 시에도 이메일에 @ 없거나 비밀번호가 8자리 미만이면 안되도록 처리

### 로그인시 header에 bearer 토큰 저장
 - 로그인 시 비밀번호와 DB에서 암호화된 비밀번호를 비교하여, 일치하면 header에 토큰 발급 
 - 게시글 작성 시 토큰이 있어야만 작성가능
 - 게시글 수정 or 삭제 시 본인 확인
 - 모든 토큰은 JwtTokenProvider 로 구현, secret-key는 application.yml에 별도 저장

### 기타 엔드포인트 접속 비허용
 - 허용된 엔드포인트에서만 접속하게 하여 보안 강화

### 게시판 페이지네이션
 - page와 size 값을 받아 글 목록을 볼 수 있음
 - 최신 글이 제일 위에 오도록 정렬
 - PaginationUtil을 만들어 페이지 번호 로직 구현
    ~~~
    ex) 현재페이지가 4인 경우 시작 페이지 1번, 끝 페이지 10번
    ex) 전체페이지가 14이고 현재페이지가 15라면, 시작페이지 11번 끝 페이지 14번
    ~~~  

### service영역 인터페이스화
 - service와 serviceImpl로 나누어 객체지향 SOLID 원칙을 지키고자 노력함

## API 명세(request/response 포함)

API명세는 아래 postman url에서 확인가능 

https://documenter.getpostman.com/view/28990636/2s9Xy3tXG3


## 사용 기술

![tets](https://github.com/noyes5/wanted-pre-onboarding-backend/assets/116651434/f9029d25-3285-4921-95d4-9d0ec66a2f7b)

## 구현 내역

- [X] 회원가입
    - [X] 이메일, 비밀번호로 회원가입
    - [X] 이메일, 비밀번호에 대한 유효성 검사
      - [X] 이메일 조건: @ 포함
      - [X] 비밀번호 조건: 8자 이상
    - [X] 비밀번호 암호화 
    - [X] Mapping -> API응답 가능하도록 변경

- [X] 사용자 로그인
    - [X] 이메일 및 비밀번호 검증, 이후 사용자 인증을 거쳐 JWT(JSON Web Token) 생성하여 사용자에게 반환
    - [X] 로그인에 대한 이메일, 비밀번호의 유효성 검사기능 구현
  
- [X] 게시글 작성 기능
    - [X] 글 작성 시 토큰값 확인 기능
 
- [X] 게시글 목록 조회
    - [X] Pagination 구현
  
- [X] 특정 게시글 조회
    - [X] 게시글 ID로 게시글 조회
  
- [X] 특정 게시글 수정
    - [X] 게시글 ID, 수정 내용을 받아 게시글 수정
    - [X] 게시글 수정은 게시글 작성한 사용자만 가능
  
- [X] 특정 게시글 삭제
    - [X] 게시글 ID를 받아 해당 게시글 삭제
    - [X] 게시글 삭제는 게시글 작성한 사용자만 가능
