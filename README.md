<h1>할일 및 일정 관리 API 애플리케이션</h1>
<h2>개요</h2>
이 프로젝트는 Spring Boot를 사용하여 제작된 REST API 애플리케이션으로, 두 가지 주요 기능을 제공합니다.

<h2>1. 할일(ToDo) API</h2> 할일을 등록하고, 4단계(PENDING, IN_PROGRESS, COMPLETED, DONE)로 관리하는 API.
<h2>2. 일정(Calendar) API</h2> 달력에 일정 추가 및 다른 사용자에게 등록된 일정 공유 기능이 있는 API. 

<h2></h2>
두 API 모두 CRUD 작업을 지원하며, MySQL 데이터베이스와 연결되어 있습니다.  

데이터베이스 스키마는 todo, member, calendar_event, shared_calendar_event 네 가지 주요 엔티티를 기반으로 구성되어 있습니다.  
추가적으로 일정 종료일 알림 기능과 일정 공유시 공유 알림 메일 전송 기능 같은 부가 기능도 제공하며,  
Swagger를 이용하여 API 명세를 문서화하였습니다.    

<h2>소스 빌드 및 실행 방법</h2>
docker-compose를 사용해서 MySQL 8.0을 생성하여 사용했습니다.  

하드코딩 된 상태로 이미지 생성하여 사용 가능합니다.   

메일을 사용한 알림기능을 사용하기 위해선 application.yml 파일의 mail.username, mail.password를 입력하셔야 사용이 가능합니다. (현재 패스워드는 환경변수로 관리 됨)   

이외의 기능은 Swagger API 문서를 보시고 사용 부탁드리며, 웹으로 접근할때 "/", "/todos", "/calendars" 경로로 접근하시면 간단한 View 화면으로 동작 확인하실수 있습니다.   

Todo, Calendar 기능 모두 로그인 후 이용 가능합니다. 

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
Getter, Setter, 생성자 등의 간편 애너테이션을 사용하여 코드를 간소화하기 위해 사용.

  <h4>JavaMailSender</h4>
사용자가 설정한 일정 종료일에 맞춰 알림 이메일을 자동으로 발송하기 위해 사용.

  <h4>EnableScheduling</h4>
일정 종료 날짜에 맞춰 자동으로 이메일을 발송하는 기능을 구현하기 위해 사용.
</div>

<h2>데이터 베이스 스키마</h2>
<img alt="erd" src="https://github.com/user-attachments/assets/164e7302-b8cf-441b-afb3-ff5a13110721" width="1000" height="600">
<h3>ERD 구성</h3>
<h4>todo</h4> 할일 정보를 담는 테이블
<h4>member</h4> 사용자 정보를 담는 테이블
<h4>calendar_event</h4> 일정 정보를 담는 테이블
<h4>shared_calendar_event</h4> 공유된 일정 정보를 담는 테이블

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

CREATE TABLE shared_calendar_event (
  shared_calendar_event_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  calendar_event_calendar_event_id BIGINT,
  member_member_id BIGINT,
  FOREIGN KEY (calendar_event_calendar_event_id) REFERENCES calendar_event(calendar_event_id),
  FOREIGN KEY (member_member_id) REFERENCES member(member_id)
);
```

<h2>프로젝트 구조</h2>

```markdown
src
├─main
│  ├─java
│  │  └─com
│  │      └─clush
│  │          └─test
│  │              │  TestApplication.java
│  │              │
│  │              ├─config
│  │              │      QueryDslConfig.java
│  │              │      WebConfig.java
│  │              │
│  │              ├─domain
│  │              │  ├─calendar
│  │              │  │  ├─controller
│  │              │  │  │      CalendarController.java
│  │              │  │  │
│  │              │  │  ├─entity
│  │              │  │  │      CalendarEvent.java
│  │              │  │  │      CalendarEventDto.java
│  │              │  │  │      CalendarEventResponse.java
│  │              │  │  │      SharedCalendarEvent.java
│  │              │  │  │      SharedCalendarEventResponse.java
│  │              │  │  │      ShareEventDto.java
│  │              │  │  │
│  │              │  │  ├─repository
│  │              │  │  │      CalendarRepository.java
│  │              │  │  │      CalendarRepositoryCustom.java
│  │              │  │  │      CalendarRepositoryCustomImpl.java
│  │              │  │  │      SharedCalenderEventRepository.java
│  │              │  │  │
│  │              │  │  ├─service
│  │              │  │  │      CalendarService.java
│  │              │  │  │      CalendarServiceImpl.java
│  │              │  │  │      EmailService.java
│  │              │  │  │      NotificationService.java
│  │              │  │  │
│  │              │  │  └─util
│  │              │  │          CalendarUtil.java
│  │              │  │
│  │              │  ├─common
│  │              │  │      BaseEntity.java
│  │              │  │      BaseEntityListener.java
│  │              │  │
│  │              │  ├─member
│  │              │  │  ├─controller
│  │              │  │  │      MemberController.java
│  │              │  │  │
│  │              │  │  ├─entity
│  │              │  │  │      Member.java
│  │              │  │  │      MemberLoginDto.java
│  │              │  │  │      MemberPostDto.java
│  │              │  │  │
│  │              │  │  ├─repository
│  │              │  │  │      MemberRepository.java
│  │              │  │  │
│  │              │  │  └─service
│  │              │  │          MemberService.java
│  │              │  │          MemberServiceImpl.java
│  │              │  │
│  │              │  └─todo
│  │              │      ├─controller
│  │              │      │      TodoController.java
│  │              │      │
│  │              │      ├─entity
│  │              │      │      Todo.java
│  │              │      │      TodoDto.java
│  │              │      │      TodoResponse.java
│  │              │      │      TodoStatus.java
│  │              │      │
│  │              │      ├─repository
│  │              │      │      TodoRepository.java
│  │              │      │
│  │              │      └─service
│  │              │              TodoService.java
│  │              │              TodoServiceImpl.java
│  │              │
│  │              ├─global
│  │              │  ├─advice
│  │              │  │      ExControllerAdvice.java
│  │              │  │
│  │              │  ├─annotation
│  │              │  │      CheckSession.java
│  │              │  │
│  │              │  ├─aop
│  │              │  │      SessionCheckAspect.java
│  │              │  │
│  │              │  └─exception
│  │              │          BusinessLogicException.java
│  │              │          ExceptionCode.java
│  │              │
│  │              └─interceptor
│  │                      SessionInterceptor.java
│  │
│  ├─resources
│  │  │  application.yml
│  │  │
│  │  ├─static
│  │  └─templates
│  └─webapp
│      ├─css
│      │      style.css
│      │
│      ├─js
│      │      calendar.js
│      │      script.js
│      │
│      └─WEB-INF
│          └─views
│                  calendar-post-form.jsp
│                  calendar.jsp
│                  home.jsp
│                  login.jsp
│                  sign-up.jsp
│                  todo-home.jsp
│                  todo-post-form.jsp
│
└─test
    └─java
        └─com
            └─clush
                └─test
                    │  TestApplicationTests.java
                    │
                    └─domain
                        ├─calendar
                        │  ├─controller
                        │  │      CalendarControllerTest.java
                        │  │
                        │  └─service
                        │          CalendarServiceImplTest.java
                        │          EmailServiceTest.java
                        │
                        ├─member
                        │  ├─controller
                        │  │      MemberControllerTest.java
                        │  │
                        │  └─service
                        │          MemberServiceImplTest.java
                        │
                        └─todo
                            ├─controller
                            │      TodoControllerTest.java
                            │
                            └─service
                                    TodoServiceImplTest.java
```

<h2>API 명세</h2>
<h3>Swagger 문서 참조</h3>
<h4>http://localhost:8080/swagger-ui/index.html</h4>
<h4>※주의※ 회원가입, 로그인하셔야 다른 API 확인 가능합니다. (세션에서 Member Id를 가져옴)</h4></h4>
[swaggerAPI.pdf](https://github.com/user-attachments/files/16907175/swaggerAPI.pdf)

<h2>View</h2>
<h4>간단하게 작성된 view로 동작 확인 가능합니다.</h4>
<img alt="todo" src="https://github.com/user-attachments/assets/725b5458-749a-4c5e-b3a6-1db49bce19da" width="1000" height="600">
<img alt="calendar" src="https://github.com/user-attachments/assets/ed058499-99de-486f-917f-71a6b9c3e4ca" width="1000" height="600">
