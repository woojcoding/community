# 커뮤니티

## 개요
웹 페이지의 가장 기본이라 생각되는 커뮤니티를 개인 프로젝트로 개발하였습니다. 

- 개발 기간: 2023.07.25 ~ 2023.09.01
- 사용자와 관리자 간의 효율적인 상호작용을 위해 두 가지의 웹 어플리케이션을 만들게 되었습니다.
- 사용자 페이지는 SPA (Single Page Application)로 구현되었으며, 관리자 페이지는 MPA (Multi Page Application)로 구성되어 있습니다.
  
## 사용자 페이지

사용자 페이지는 Spring Boot와 Vue.js를 사용하여 Single Page Application (SPA)로 구현되었습니다.

- <a href="http://ec2-43-201-8-173.ap-northeast-2.compute.amazonaws.com:8080/">사용자 페이지 링크</a>
- 사용자 아이디: user1
- 사용자 비밀번호: 123q

## 관리자 페이지

관리자 페이지는 Spring Boot와 Thymeleaf를 사용하여 Multi Page Application (MPA)로 구현되었습니다.

- <a href="http://ec2-43-201-8-173.ap-northeast-2.compute.amazonaws.com:8081/admin/login">관리자 페이지 링크</a>
- 관리자 아이디: admin1
- 관리자 비밀번호: 123qwe!

## 사용 기술 및 도구

프로젝트 개발에 사용된 주요 기술 및 도구는 다음과 같습니다:

- Vue.js
- Spring Boot
- Thymeleaf

### 사용자 페이지
- Vue.js: SPA (Single Page Application) 개발에 사용된 프론트엔드 프레임워크
- Vuex: Vue.js 애플리케이션의 상태 관리를 위한 패턴 및 라이브러리
- Spring Boot 2.7: 백엔드 서버 개발에 사용된 스프링 부트 버전
- MyBatis: 데이터베이스와의 상호작용을 위한 프레임워크
- Spring Security: 보안 관련 기능 및 사용자 인증에 활용
- JWT (JSON Web Tokens): 사용자 인증 및 권한 부여를 위한 토큰 기반 인증 방식

### 관리자 페이지

- 타임리프 (Thymeleaf): 서버 사이드 렌더링을 위한 템플릿 엔진
- Spring Boot 2.7: 백엔드 서버 개발에 사용된 스프링 부트 버전
- MyBatis: 데이터베이스와의 상호작용을 위한 프레임워크
- Spring Security: 관리자 인증 및 권한 관리를 위한 보안 프레임워크

### 배포환경

프로젝트의 배포 환경:

- AWS EC2: 클라우드 환경에서 서버 호스팅
- AWS RDS: 관계형 데이터베이스 호스팅
- DB: MySQL 8.0을 데이터베이스로 사용
