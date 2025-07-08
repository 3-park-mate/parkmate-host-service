# 🏠 Host Service

> 호스트의 매출 조회 및 정산 기능을 제공하는 Spring Boot 애플리케이션

## 📋 목차

- [개요](#-개요)
- [기술 스택](#-기술-스택)
- [아키텍처](#-아키텍처)
- [주요 기능](#-주요-기능)
- [API 문서](#-api-문서)
- [설치 및 실행](#-설치-및-실행)
- [개발 환경 설정](#-개발-환경-설정)
- [배포](#-배포)
- [프로젝트 구조](#-프로젝트-구조)

## 🎯 개요

Host Service는 호스트의 매출 데이터를 조회하고 정산하는 기능을 제공하는 마이크로서비스입니다. 다음과 같은 특징을 가지고 있습니다:

- **매출 조회**: 호스트별 일별/월별 매출 정보 조회
- **OpenFeign 통신**: 배치 서비스와의 통신을 통한 정산 데이터 조회
- **실시간 데이터**: 호스트별 실시간 매출 정보 제공
- **마이크로서비스 아키텍처**: 독립적인 호스트 매출 관리 서비스

## 🛠 기술 스택

| 분류 | 기술 | 버전 |
|------|------|------|
| **Backend** | Java | 11 |
| **Framework** | Spring Boot | 2.7.x |
| **Build Tool** | Gradle | 7.0+ |
| **Database** | MySQL/PostgreSQL | 8.0+ |
| **Service Communication** | Spring Cloud OpenFeign | 3.1.x |
| **Documentation** | Swagger/OpenAPI | 3.0 |
| **DevOps** | Docker | Latest |
| **CI/CD** | GitHub Actions | - |

## 🏗 아키텍처

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client App    │    │  Host Service   │    │  Batch Service  │
└─────────┬───────┘    └─────────┬───────┘    └─────────┬───────┘
          │                      │                      │
          │                      │                      │
          ▼                      ▼                      ▼
┌─────────────────────────────────────────────────────────────────┐
│                    API Gateway / Load Balancer                  │
└─────────────────────────┬───────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Host Service                                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐            │
│  │   Controller│  │   Service   │  │  Repository │            │
│  └─────────────┘  └─────────────┘  └─────────────┘            │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐            │
│  │   OpenFeign │  │   Feign     │  │   Database  │            │
│  │   Client    │  │   Client    │  │             │            │
│  └─────────────┘  └─────────────┘  └─────────────┘            │
└─────────────────────────────────────────────────────────────────┘
                          │
          ┌───────────────┼───────────────┐
          ▼               ▼               ▼
┌─────────────┐  ┌─────────────┐  ┌─────────────┐
│   Database  │  │   Batch     │  │   External  │
│             │  │   Service   │  │   Service   │
└─────────────┘  └─────────────┘  └─────────────┘
```

## 🚀 주요 기능

### 1. 호스트 매출 조회
- 호스트별 일별 매출 조회
- 호스트별 월별 매출 조회
- 실시간 매출 데이터 집계
- 예약 건수 통계

### 2. 배치 서비스 통신
- OpenFeign을 통한 배치 서비스와의 통신
- 정산된 매출 데이터 조회
- 실시간 데이터와 배치 데이터 통합

### 3. 데이터 통합
- 실시간 매출 데이터와 정산 데이터 결합
- 호스트별 통합 매출 정보 제공

## 📚 API 문서

### Swagger UI
- **URL**: http://localhost:8080/swagger-ui.html
- API 문서 및 테스트 가능

### 주요 API 엔드포인트

#### 1. 호스트 일매출 조회
```
GET /api/v1/hosts/{hostUuid}/sales/daily?date=YYYY-MM-DD
```

**Parameters**:
- `hostUuid` (path): 호스트 UUID
- `date` (query): 조회 날짜 (YYYY-MM-DD 형식)

**Response**:
```json
{
  "httpStatus": "OK",
  "message": "일매출 조회 성공",
  "code": 200,
  "data": {
    "date": "2025-05-15",
    "totalSales": 150000,
    "totalReservations": 5
  }
}
```

#### 2. 호스트 월매출 조회
```
GET /api/v1/hosts/{hostUuid}/sales/monthly?year=YYYY&month=MM
```

**Parameters**:
- `hostUuid` (path): 호스트 UUID
- `year` (query): 조회 년도
- `month` (query): 조회 월 (1-12)

**Response**:
```json
{
  "httpStatus": "OK",
  "message": "월매출 조회 성공",
  "code": 200,
  "data": {
    "yearMonth": "2025-05",
    "totalSales": 4500000,
    "totalReservations": 150
  }
}
```

## 💻 설치 및 실행

### 사전 요구사항
- Java 11
- Docker & Docker Compose
- MySQL/PostgreSQL
- Gradle 7.0+
- Batch Service (OpenFeign 통신 대상)

### 1. 프로젝트 클론
```bash
git clone <repository-url>
cd hostservice
```

### 2. 환경 변수 설정
`.env` 파일을 생성하고 다음 환경변수를 설정하세요:

```bash
DATABASE_URL=your_database_url
DATABASE_USERNAME=your_database_username
DATABASE_PASSWORD=your_database_password
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=local
BATCH_SERVICE_URL=http://batch-service:8081
```

### 3. 애플리케이션 설정
`src/main/resources/application.yml` 파일을 생성하고 설정하세요:

```yaml
spring:
  application:
    name: host-service
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USERNAME}
    password: ${DATABASE_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  cloud:
    openfeign:
      client:
        config:
          default:
            connectTimeout: 5000
            readTimeout: 5000

server:
  port: ${SERVER_PORT}

batch:
  service:
    url: ${BATCH_SERVICE_URL}

logging:
  level:
    com.parkmate.hostservice: DEBUG
    feign: DEBUG
```

### 4. 로컬 실행
```bash
# Gradle 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```

### 5. Docker 실행
```bash
# Docker 이미지 빌드
docker build -t host-service .

# Docker Compose 실행
docker-compose -f docker-compose-host.yml up -d
```

## 🔧 개발 환경 설정

### IDE 설정
- IntelliJ IDEA 또는 Eclipse 권장
- Lombok 플러그인 설치
- Spring Boot 플러그인 설치

### 코드 스타일
- Java 11 문법 사용
- Lombok 어노테이션 활용
- Builder 패턴 사용
- 레이어드 아키텍처 적용

### 테스트
```bash
# 단위 테스트 실행
./gradlew test

# 통합 테스트 실행
./gradlew integrationTest
```

## 🚀 배포

### GitHub Actions CI/CD
프로젝트는 GitHub Actions를 통해 자동 배포됩니다:

- `dev` 브랜치에 푸시 시 자동 배포
- AWS ECR에 Docker 이미지 푸시
- EC2 서버에 자동 배포

### 수동 배포
```bash
# 1. 빌드
./gradlew build -x test

# 2. Docker 이미지 생성
docker build -t host-service .

# 3. ECR 푸시
docker tag host-service:latest {ecr-repository}/host-service:latest
docker push {ecr-repository}/host-service:latest

# 4. 서버 배포
docker-compose -f docker-compose-host.yml down
docker-compose -f docker-compose-host.yml up -d
```

## 📁 프로젝트 구조

```
src/main/java/com/parkmate/hostservice/
├── common/                          # 공통 모듈
│   ├── config/                      # 설정 클래스
│   ├── exception/                   # 예외 처리
│   └── response/                    # 공통 응답 모델
├── settlement/                      # 정산 관련
│   ├── controller/                  # 컨트롤러 계층
│   │   └── SettlementController.java
│   ├── service/                     # 서비스 계층
│   │   ├── SettlementService.java
│   │   └── SettlementServiceImpl.java
│   ├── repository/                  # 데이터 접근 계층
│   │   ├── DailySettlementRepository.java
│   │   └── MonthlySettlementRepository.java
│   ├── entity/                      # 엔티티
│   │   ├── DailySettlement.java
│   │   └── MonthlySettlement.java
│   ├── dto/                         # 데이터 전송 객체
│   │   └── response/
│   │       ├── DailySalesResponseDto.java
│   │       └── MonthlySalesResponseDto.java
│   └── client/                      # OpenFeign 클라이언트
│       └── BatchServiceClient.java
└── HostserviceApplication.java      # 메인 애플리케이션
```

### 주요 클래스 설명

#### Domain Models
- `DailySettlement`: 일별 정산 엔티티
- `MonthlySettlement`: 월별 정산 엔티티
- `SettlementStatus`: 정산 상태 열거형

#### Services
- `SettlementService`: 정산 비즈니스 로직
- `SettlementServiceImpl`: 정산 서비스 구현체

#### Controllers
- `SettlementController`: REST API 엔드포인트

#### OpenFeign Clients
- `BatchServiceClient`: 배치 서비스와의 통신을 위한 Feign 클라이언트

## 🔄 OpenFeign 통신 처리

### 배치 서비스 통신
- **목적**: 정산된 매출 데이터 조회
- **방식**: OpenFeign을 통한 HTTP 통신
- **대상 서비스**: Batch Service

### 통신 흐름
1. **호스트 매출 조회 요청** → Host Service
2. **실시간 데이터 조회** → 로컬 데이터베이스
3. **정산 데이터 조회** → Batch Service (OpenFeign)
4. **데이터 통합** → 실시간 + 정산 데이터 결합
5. **응답 반환** → 클라이언트

### Feign Client 설정
```java
@FeignClient(name = "batch-service", url = "${batch.service.url}")
public interface BatchServiceClient {
    
    @GetMapping("/api/v1/settlements/daily/{hostUuid}")
    DailySettlementResponse getDailySettlement(@PathVariable UUID hostUuid, 
                                              @RequestParam LocalDate date);
    
    @GetMapping("/api/v1/settlements/monthly/{hostUuid}")
    MonthlySettlementResponse getMonthlySettlement(@PathVariable UUID hostUuid,
                                                  @RequestParam int year,
                                                  @RequestParam int month);
}
```

## 📊 모니터링 및 로깅

### 로그 레벨
- `DEBUG`: 개발 환경에서 상세 로그
- `INFO`: 일반적인 애플리케이션 로그
- `WARN`: 경고 상황
- `ERROR`: 오류 상황

### 주요 로그 포인트
- API 요청/응답
- OpenFeign 통신 로그
- 데이터베이스 쿼리 실행
- 데이터 통합 처리

## 🤝 기여하기

1. **Fork the Project**
2. **Create your Feature Branch** (`git checkout -b feature/AmazingFeature`)
3. **Commit your Changes** (`git commit -m 'Add some AmazingFeature'`)
4. **Push to the Branch** (`git push origin feature/AmazingFeature`)
5. **Open a Pull Request**

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## 📞 문의

프로젝트에 대한 문의사항이 있으시면 이슈를 생성해 주세요.

**ParkMate Team © 2025**