# Musinsa Store Server
무신사 Store Server (Backend Engineer 채용과제)

## Requirements

다음 8개의 카테고리에서 상품을 하나씩 구매하여, 코디를 완성하는 서비스를 준비중입니다.
1. 상의
2. 아우터
3. 바지
4. 스니커즈
5. 가방
6. 모자
7. 양말
8. 액세서리

- 구매 가격 외의 추가적인 비용(예, 배송비 등)은 고려하지 않고,  
브랜드의 카테고리에는 1개의 상품은 존재하고,  
구매를 고려하는 모든 쇼핑몰에 상품 품절은 없다고 가정합니다.

- 초기 데이터  
  |브랜드|상의|아우터|바지|스니커즈|가방|모자|양말|액세서리| 
  |---|---|---|---|---|---|---|---|---|
  |A|11,200|5,500|4,200|9,000|2,000|1,700|1,800|2,300|
  |B|10,500|5,900|3,800|9,100|2,100|2,000|2,000|2,200|
  |C|10,000|6,200|3,300|9,200|2,200|1,900|2,200|2,100|
  |D|10,100|5,100|3,000|9,500|2,500|1,500|2,400|2,000|
  |E|10,700|5,000|3,800|9,900|2,300|1,800|2,100|2,100|
  |F|11,200|7,200|4,000|9,300|2,100|1,600|2,300|1,900|
  |G|10,500|5,800|3,900|9,000|2,200|1,700|2,100|2,000|
  |H|10,800|6,300|3,100|9,700|2,100|1,600|2,000|2,000|
  |I|11,400|6,700|3,200|9,500|2,400|1,700|1,700|2,400|

- 구현 API  
  * 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API  
  * 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와  
    카테고리의 상품가격, 총액을 조회하는 API  
  * 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API  
  * 브랜드 및 상품을 추가 / 업데이트 / 삭제하는 API  

## Development Enviroment
- Java 17
- SpringBoot 3.4.3
- SpringWeb 6.2.3
- SpringData JPA 3.4.3
- H2 database 2.3.232
- Gradle 8.12.1

## Quick Start

### Build
```
# ./gradlew build
```

### Test only
```
# ./gradlew unitTest          // Unit test
# ./gradlew integrationTest   // Integration test
```

### Run
```
# ./gradlew bootRun
```

## Package Structure

```
/
├── common/               // 공통 패키지
│   ├── config/             // Spring 설정
│   ├── exception/          // exception
│   ├── event/              // 내부 이벤트
│   ├── jpa/                // jpa 공통 Entity
│   ├── dto/                // 공통 DTO
│   └── cache/              // Cache 컴포넌트
│
├── product/              // 상품 기능 관련 패키지
│   ├── api/                // API Controller
│   │   └── dto/              // API DTO
│   ├── domain/             // Usecase 및 도메인 Entity
│   │   └── dto/              // 도메인 DTO
│   ├── repository/         // DB Repository
│   │   └── entity/           // 영속 Entity
│   └── exception/          // 기능 특화 exception
│
└── StoreApplication      // main application
```

## Application Architecture

![Application](./docs/app.png)


## Configuration
- application.yml
```
server:
  port: 8080      # binding port

logging:
  level:          # log level
    root: info
    com.musinsa.store: debug

cache:
  active: true    # 검색 cache 사용 여부
  ttl: 60         # 검색 cache ttl (sec)
```

## Database Schema
- brand: 브랜드 정보 저장
  - id: 브랜드 id
  - name: 브랜드 이름
  - total_price: 브랜드 상품 가격 총합
  - created_at: 생성시간
  - modified_at: 수정시간

- product: 상품 정보 저장
  - id: 상품 id
  - brand_id: 브랜드 id
  - category: 카테고리
  - price: 상품 가격

![ERD](./docs/erd.png)

## API Specification

Musinsa Store API Specification

Base URLs:

* <a href="http://localhost:8080">http://localhost:8080</a>

---
### Get Lowest Priced Product Set API
`GET /api/v1/products/lowest-set`

*카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회*

파라미터에 따라 전체 브랜드 또는 하나의 브랜드에 대한 상품을 조회합니다.

### Get Lowest & Highest Priced Cateogry Product API
`GET /api/v1/products/lowest-highest-category`

*카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회*

카테고리 이름을 파라미터로 받아서 해당 카테고리의 최저/최고 가격의 상품을 조회합니다.

### Get Brand List API
`GET /api/v1/brands`

*브랜드 리스트 조회*

브랜드 리스트를 페이지 단위로 조회합니다.


### Create Brand API
`POST /api/v1/brands`

*브랜드/상품 생성*

신규 브랜드 및 상품 정보를 받아서 생성합니다.

### Get Brand API
`GET /api/v1/brands/{id}`

*브랜드/상품 조회*

브랜드 id를 받아서 해당 브랜드 및 상품 정보를 조회합니다.

### Update Brand API
`POST /api/v1/brands/{id}`

*브랜드/상품 업데이트*

브랜드 id 와 브랜드 정보를 받아서 해당 브랜드 및 상품 정보를 업데이트 합니다.

### Delete Brand API
`DELETE /api/v1/brands/{id}`

*브랜드/상품 삭제*

브랜드 id를 받아서 해당 브랜드 및 상품 정보를 삭제 합니다.

