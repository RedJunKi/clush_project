<h1>할일 및 일정 관리 API 애플리케이션</h1>
<h2>개요</h2>
이 프로젝트는 Spring Boot를 사용하여 제작된 REST API 애플리케이션으로, 두 가지 주요 기능을 제공합니다.

1. <h2>할일(ToDo) API</h2> 할일을 등록하고, 관리하는 API.
2. <h2>일정(Calendar) API</h2> 일정을 등록하고, 관리하는 API.

두 API 모두 CRUD 작업을 지원하며, MySQL 데이터베이스와 연결되어 있습니다.  
데이터베이스 스키마는 todo, member, calendar_event 세 가지 주요 엔티티를 기반으로 구성되어 있습니다.  
추가적으로 일정 알람 기능과 같은 부가 기능도 제공하며, Swagger를 이용하여 API 명세를 문서화하였습니다.  

<div>
  <h2>사용된 기술</h2>
  <h4>Spring Boot 3.3.3</h4>
애플리케이션의 라이프사이클을 관리하고, REST API를 구축하는 데 사용.

  <h4>Spring Data JPA</h4>
객체 관계 매핑(ORM)을 사용해 데이터베이스와 상호작용을 관리.
데이터를 객체처럼 사용하기위해 사용.

  <h4>QueryDSL</h4>
커스텀 쿼리를 작성하기 위해 사용.

  <h4>MySQL</h4>
애플리케이션 데이터를 저장하기 위한 관계형 데이터베이스.

  <h4>Swagger</h4>
API 문서화를 간편하게 하기 위한 도구.

  <h4>Lombok</h4>
Getter, Setter, 생성자 등의 간편 애너테이션을 사용하기 위해 사용.
</div>

<h2>데이터 베이스 스키마</h2>
<img alt="erd" src="https://github.com/user-attachments/assets/74b789ba-7e75-4d76-b867-ee90865351a0" width="1000" height="600">
<h3>ERD 구성</h3>
<h4>todo</h4> 할일 정보를 담는 테이블
<h4>member</h4> 사용자 정보를 담는 테이블
<h4>calendar_event</h4> 일정 정보를 담는 테이블

```sql
CREATE TABLE member (
  member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255),
  password VARCHAR(255),
  username VARCHAR(255),
  created_at DATETIME(6),
  modified_at DATETIME(6)
);

CREATE TABLE todo (
  todo_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255),
  description VARCHAR(255),
  status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED', 'DONE'),
  created_at DATETIME(6),
  modified_at DATETIME(6),
  member_member_id BIGINT,
  FOREIGN KEY (member_member_id) REFERENCES member(member_id)
);

CREATE TABLE calendar_event (
  calendar_event_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255),
  description VARCHAR(255),
  start_date DATETIME(6),
  end_date DATETIME(6),
  created_at DATETIME(6),
  modified_at DATETIME(6),
  member_member_id BIGINT,
  FOREIGN KEY (member_member_id) REFERENCES member(member_id)
);
```

<h2>프로젝트 구조</h2>

```markdown
C:.
│  .gitignore
│  build.gradle
│  docker-compose.yml
│  dockerfile
│  gradlew
│  gradlew.bat
│  HELP.md
│  settings.gradle
│
├─.gradle
│  │  file-system.probe
│  │
│  ├─8.8
│  │  │  gc.properties
│  │  │
```

<h2>API 명세</h2>
<h3>Swagger 문서 참조</h3>
<h4>http://localhost:8080/swagger-ui/index.html</h4>
[swaggerAPI.pdf](https://github.com/user-attachments/files/16907175/swaggerAPI.pdf)

