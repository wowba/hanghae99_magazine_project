<h3 align="center"><b>항해99 스프링 2레벨 프로젝트</b></h3>

<h4 align="center">📆 2022.02.18 ~ 2022.02.xx</h4>

사용한 버전 / JAVA 8 / SDK 1.8

설치한 Dependencies
- Spring web
- Lombok
- H2 Database
- Spring JPA
- MySQL Driver
- JSON in JAVA
- Swagger
- Thymeleaf

생성한 API 목록

0. 로그인 기능 구현
    - [x] 스프링 시큐리티를 이용해 로그인, 로그아웃 기능 구현.
    - [x] 로그인 여부에 따라 기능 제한 구현하기
1. 전체 게시글 목록 조회
    - [x] 제목, 작성자명, 작성 날짜를 조회하기
2. 게시글 작성
    - [x] 제목, 작성자명, 작성 내용을 입력하기
    - [x] 비로그인시 기능 제한
3. 게시글 조회
    - [x] 제목, 작성자명, 작성 날짜, 작성 내용, 좋아요 조회하기
4. 게시글 수정
    - [x] 제목, 작성자명, 작성 내용 중 원하는 내용을 수정하기
    - [x] 비로그인 및 유저 불일치시 기능 제한
5. 게시글 삭제
    - [x] 원하는 게시물을 삭제하기
    - [x] 비로그인 및 유저 불일치시 기능 제한
6. 게시글 좋아요 및 취소
    - [x] 좋아요 및 취소 가능.
    - [x] 비로그인시 기능 제한
7. 댓글 삭제
    - [x] 원하는 댓글을 삭제하기
    - [x] 비로그인 및 유저 불일치시 기능 제한


<h3 align="center"><b>🛠 Tech Stack 🛠</b></h3>
<p align="center">

<br>
<img src="https://img.shields.io/badge/java8-539bf5?style=for-the-badge&logo=java1.8&logoColor=white">
<img src="https://img.shields.io/badge/jpa-green?style=for-the-badge&logo=jpa&logoColor=white">
<img src="https://img.shields.io/badge/spring%20data%20jpa-green?style=for-the-badge&logo=springdatajpa&logoColor=white">
<img src="https://img.shields.io/badge/gradle-1f4954?style=for-the-badge&logo=gradle&logoColor=white">
<br>
<img src="https://img.shields.io/badge/Junit5-green?style=for-the-badge&logo=Junit5&logoColor=white">
<img src="https://img.shields.io/badge/mysql-skyblue?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/Swagger UI-green?style=for-the-badge&logo=Swagger UI&logoColor=white">
<br>
<img src="https://img.shields.io/badge/Thymeleaf-green?style=for-the-badge&logo=Thymeleaf&logoColor=white">
<img src="https://img.shields.io/badge/Spring Boot-green?style=for-the-badge&logoColor=white">
<img src="https://img.shields.io/badge/awsrds-orange?style=for-the-badge&logo=awsrds&logoColor=white">
<br>

아쉬웠던 점.

프로젝트 마지막 기능 점검때 갑자기 좋아요 기능에

failed to lazily initialize a collection of role

다음과 같은 문제가 생겨 급하게 fetctype = EAGER 를 사용할 수 밖에 없었다.

다음에는 미리 영속성 컨텍스트와 DB에 대해 좀 더 이해를 해서 사전에 문제를 방지하자.

그리고 프론트에서 처리해도 되지 않을 수 있게 되었는데, 급하게 마감하느라 결국 해당 기능을 제시간에 완성하지 못했다.

계속 리팩토링을 꾸준히 해 좀 더 발전시켜나갈 수 있도록 하자.

그리고 스프링 시큐리티는 참 좋다가 밉다가 왔다갔다 한다...