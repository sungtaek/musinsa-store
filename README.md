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

### Web Frontend
서버 구동 후, 하기 web page에서 테스트할 수 있습니다.  
https://sungtaek.github.io/musinsa-store

#### 1. 구동한 서버 url을 넣고 login 합니다.
![로그인](./docs/musinsa_web_1.png)

#### 2. 메뉴에 따라 상품 검색 및 브랜드/상품 관리를 할수 있습니다.
![상품 조회](./docs/musinsa_web_2.png)

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

![Architecture](./docs/architecture.png)

### 주요 구현내용
#### 1. 컴포넌트 구조
- Clean Architecture 기반으로 설계하였으며,  
  Interface Adaptors Layer의 역할을 하는 API Controller, Repository, Storage 영역과    
  Domain Layer 역할을 하는 Usecase, Domain Entity 영역으로 나뉘어 있습니다.  
- Persistent Entity를 Domain Entity와 분리하여 Repository 영역에 둠으로써, 향후 DB 연동관련 변경에도  
  Domain 로직이 영향을 받지 않도록 않도록 했습니다.  

#### 2. 기능
- 브랜드 및 상품 관리:  
  - API 요청을 받으면 BrandController가 기본적인 파라미터 검사 후, BrandService를 호출합니다.  
  - BrandService는 데이터 검사 후 BrandRepository를 이용하여 데이터 변경을 합니다.  
    데이터 변경이 되었을경우 내부 컴포넌트에게 알려주기 위하여 브랜드 변경 이벤트를 publish합니다.  
  - JpaBrandRepositoryAdaptor는 BrandRepository의 구현체로써, 내부적으로 JpaRepository를 사용합니다.  
    브랜드 데이터의 CRUD는 JPA 기본 method로 충분히 처리 가능하여 JpaRepository를 사용했습니다.  

- 상품 조회:
  - API 요청을 받으면 ProductController가 기본적인 파라미터 검사 후, ProductSearchService를 호출합니다.  
  - ProductSearchService는 검색 조건을 확인하여 ProductRepository를 이용하여 상품을 조회 합니다.  
  - QJpaProductRepository는 ProductRepository의 구현체로써, QueryDSL을 사용하여 검색 조건에 따른 동적 쿼리를 생성합니다.  
    향후에도 다양한 검색 query가 생길 수 있으므로, 동적 쿼리 생성을 사용했습니다.  

#### 3. 성능 및 확장
  - 상품 검색 쿼리는 비용이 큰 Operation이므로, 성능 향상을 위하여 캐쉬를 사용합니다.  
    캐쉬 데이터는 브랜드 변경시 삭제하여 데이터 일관성을 유지합니다.  
    (브랜드 변경 이벤트는 내부 Event Publisher를 사용하여 컴포넌트간 의존성을 분리했습니다)  
  - 현재 캐쉬는 내부 메로리를 사용하는 LocalCacheStorage를 사용하지만, 향후 다른 캐쉬 사용이 필요할 경우  
    CacheStorage interface만 구현하면 Redis와 같은 중앙 캐쉬로 확장할 수 있습니다.  
  - 카테고리 최고 최저값을 한번에 요청하는 API의 경우, 두 값을 병렬로 조회하는것이 성능에 도움이 되기때문에  
    CompletableFuture로 비동기 병렬처리를 사용했습니다.  


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

### Get Product Set API
`GET /api/v1/products/set`

*카테고리 별 최저/최고가격 브랜드와 상품 가격, 총액을 조회*

파라미터에 따라 최저/최고 가격, 전체/단일 브랜드에 대한 상품을 조회합니다.

<h4>Parameters</h4>

|Name|In|Type|Required|Default|Description|
|---|---|---|---|---|---|
|price|query|string(enum)<br>LOWEST: 최저가<br>HIGHEST: 최고가|false|LOWEST|최저/최고 가격|
|singleBrand|query|boolean|false|false|단일 브랜드 여부|

<h4>Responses</h4>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Success|[ResponseProductSet](#response-product-set)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad request|[ResponseProductSet](#response-product-set)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal error|[ResponseProductSet](#response-product-set)|

<h5 id="response-product-set">Response Product Set</h5>

|Name|Type|Required|Description|
|---|---|---|---|
|code|string|true|result code|
|message|string|true|result message|
|data|[ResultProductSet](#result-product-set)|false|product set|


> Example

```json
> GET http://localhost:8080/api/v1/products/set?price=LOWEST&singleBrand=false

< 200 OK
{
  "code": "0000",
  "message": "Success",
  "data": {
    "lowestPrice": {
      "products": [
        {
         "category": "BAGS",
         "brandName": "A",
         "price": 2000
        },
        {
          "category": "TOPS",
          "brandName": "C",
          "price": 10000
        },
        {
          "category": "PANTS",
          "brandName": "D",
          "price": 3000
        },
        {
          "category": "HATS",
          "brandName": "D",
          "price": 1500
        },
        {
          "category": "OUTER",
          "brandName": "E",
          "price": 5000
        },
        {
          "category": "ACCESSORIES",
          "brandName": "F",
          "price": 1900
        },
        {
          "category": "SNEAKERS",
          "brandName": "G",
          "price": 9000
        },
        {
          "category": "SOCKS",
          "brandName": "I",
          "price": 1700
        }
      ],
      "totalPrice": 34100
    }
  }
}

```

---
### Get Product By Category API
`GET /api/v1/products/by-category`

*카테고리 이름으로 최저/최고 가격 브랜드와 상품 가격을 조회*

카테고리 이름을 파라미터로 받아서 해당 카테고리의 최저/최고 가격의 상품을 조회합니다.

<h4>Parameters</h4>

|Name|In|Type|Required|Default|Description|
|---|---|---|---|---|---|
|category|query|string([CategoryCode](#category-code))|true||카테고리 코드|
|price|query|string(enum)<br>LOWEST: 최저가<br>HIGHEST: 최고가<br>LOWEST_HIGHEST: 최저&최고가|false|LOWEST|최저/최고 가격|

<h4>Responses</h4>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Success|[ResponseProductCategory](#response-product-category)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad request|[ResponseProductCategory](#response-product-category)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal error|[ResponseProductCategory](#response-product-category)|

<h5 id="response-product-category">Response Product Category</h5>

|Name|Type|Required|Description|
|---|---|---|---|
|code|string|true|result code|
|message|string|true|result message|
|data|[ResultProductSet](#result-product-set)|false|상품 set|


> Example

```json
> GET http://localhost:8080/api/v1/products/by-category?category=TOPS&price=LOWEST_HIGHEST

< 200 OK
{
  "code": "0000",
  "message": "Success",
  "data": {
    "category": "TOPS",
    "lowestPrice": {
      "products": [
        {
          "category": "TOPS",
          "brandName": "C",
          "price": 10000
        }
      ]
    },
    "highestPrice": {
      "products": [
        {
          "category": "TOPS",
          "brandName": "I",
          "price": 11400
        }
      ]
    }
  }
}
```

---
### Get Brand List API
`GET /api/v1/brands`

*브랜드 리스트 조회*

브랜드 리스트를 페이지 단위로 조회합니다.

<h4>Parameters</h4>

|Name|In|Type|Required|Default|Description|
|---|---|---|---|---|---|
|page|query|integer|false|0|페이지 번호 (0부터시작)|
|size|query|integer|false|20|페이지 크기|

<h4>Responses</h4>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Success|[ResponseBrandList](#response-brand-list)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad request|[ResponseBrandList](#response-brand-list)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal error|[ResponseBrandList](#response-brand-list)|

<h5 id="response-brand-list">Response Brand List</h5>

|Name|Type|Required|Description|
|---|---|---|---|
|code|string|true|result code|
|message|string|true|result message|
|data|[[Brand](#brand)]|true|브랜드 리스트|
|page|integer|true|현재 page 번호|
|size|integer|true|page 크기|
|toalPage|integer|true|전체 page 수|


> Example

```json
> GET http://localhost:8080/api/v1/brands?page=0&size=5

< 200 OK
{
  "code": "0000",
  "message": "Success",
  "data": [
    {
      "id": 1,
      "name": "A",
      "products": []
    },
    {
      "id": 2,
      "name": "B",
      "products": []
    },
    {
      "id": 3,
      "name": "C",
      "products": []
    },
    {
      "id": 4,
      "name": "D",
      "products": []
    },
    {
      "id": 5,
      "name": "E",
      "products": []
    }
  ],
  "page": 0,
  "size": 5,
  "totalPage": 2
}
```

---
### Create Brand API
`POST /api/v1/brands`

*브랜드/상품 생성*

신규 브랜드 및 상품 정보를 받아서 생성합니다.

<h4>Parameters</h4>

|Name|In|Type|Required|Default|Description|
|---|---|---|---|---|---|
|brand|body|[Brand](#brand)|true||생성할 브랜드|

<h4>Responses</h4>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Success|[ResponseBrandCreate](#response-brand-create)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad request|[ResponseBrandCreate](#response-brand-create)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal error|[ResponseBrandCreate](#response-brand-create)|

<h5 id="response-brand-create">Response Brand Create</h5>

|Name|Type|Required|Description|
|---|---|---|---|
|code|string|true|result code|
|message|string|true|result message|
|data|[Brand](#brand)|true|생성된 브랜드|


> Example

```json
> POST http://localhost:8080/api/v1/brands
{
  "name": "Z",
  "products": [
    {
      "category": "TOPS",
      "price": 6000
    },
    {
      "category": "OUTER",
      "price": 8000
    },
    {
      "category": "PANTS",
      "price": 7000
    },
    {
      "category": "SNEAKERS",
      "price": 3000
    },
    {
      "category": "BAGS",
      "price": 3000
    },
    {
      "category": "HATS",
      "price": 1400
    },
    {
      "category": "SOCKS",
      "price": 800
    },
    {
      "category": "ACCESSORIES",
      "price": 1000
    }
  ]
}

< 200 OK
{
  "code": "0000",
  "message": "Success",
  "data": {
    "id": 10,
    "name": "Z",
    "products": [
      {
        "id": 73,
        "category": "TOPS",
        "price": 6000
      },
      {
        "id": 74,
        "category": "OUTER",
        "price": 8000
      },
      {
        "id": 75,
        "category": "PANTS",
        "price": 7000
      },
      {
        "id": 76,
        "category": "SNEAKERS",
        "price": 3000
      },
      {
        "id": 77,
        "category": "BAGS",
        "price": 3000
      },
      {
        "id": 78,
        "category": "HATS",
        "price": 1400
      },
      {
        "id": 79,
        "category": "SOCKS",
        "price": 800
      },
      {
        "id": 80,
        "category": "ACCESSORIES",
        "price": 1000
      }
    ]
  }
}
```

---
### Get Brand API
`GET /api/v1/brands/{id}`

*브랜드/상품 조회*

브랜드 id를 받아서 해당 브랜드 및 상품 정보를 조회합니다.

<h4>Parameters</h4>

|Name|In|Type|Required|Default|Description|
|---|---|---|---|---|---|
|id|path|string|true||조회할 브랜드 id|

<h4>Responses</h4>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Success|[ResponseBrandGet](#response-brand-get)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad request|[ResponseBrandGet](#response-brand-get)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|[ResponseBrandGet](#response-brand-get)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal error|[ResponseBrandGet](#response-brand-get)|

<h5 id="response-brand-get">Response Brand Get</h5>

|Name|Type|Required|Description|
|---|---|---|---|
|code|string|true|result code|
|message|string|true|result message|
|data|[Brand](#brand)|true|조회된 브랜드|


> Example

```json
> GET http://localhost:8080/api/v1/brands/10

< 200 OK
{
  "code": "0000",
  "message": "Success",
  "data": {
    "id": 10,
    "name": "Z",
    "products": [
      {
        "id": 73,
        "category": "TOPS",
        "price": 6000
      },
      {
        "id": 74,
        "category": "OUTER",
        "price": 8000
      },
      {
        "id": 75,
        "category": "PANTS",
        "price": 7000
      },
      {
        "id": 76,
        "category": "SNEAKERS",
        "price": 3000
      },
      {
        "id": 77,
        "category": "BAGS",
        "price": 3000
      },
      {
        "id": 78,
        "category": "HATS",
        "price": 1400
      },
      {
        "id": 79,
        "category": "SOCKS",
        "price": 800
      },
      {
        "id": 80,
        "category": "ACCESSORIES",
        "price": 1000
      }
    ]
  }
}
```

---
### Update Brand API
`POST /api/v1/brands/{id}`

*브랜드/상품 업데이트*

브랜드 id 와 브랜드 정보를 받아서 해당 브랜드 및 상품 정보를 업데이트 합니다.

<h4>Parameters</h4>

|Name|In|Type|Required|Default|Description|
|---|---|---|---|---|---|
|id|path|string|true||수정할 브랜드 id|
|brand|body|[Brand](#brand)|true||수정할 브랜드|

<h4>Responses</h4>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Success|[ResponseBrandUpdate](#response-brand-update)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad request|[ResponseBrandUpdate](#response-brand-update)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|[ResponseBrandUpdate](#response-brand-update)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal error|[ResponseBrandUpdate](#response-brand-update)|

<h5 id="response-brand-update">Response Brand Update</h5>

|Name|Type|Required|Description|
|---|---|---|---|
|code|string|true|result code|
|message|string|true|result message|
|data|[Brand](#brand)|true|수정된 브랜드|


> Example

```json
> POST http://localhost:8080/api/v1/brands/10
{
  "name": "ZZZ",
  "products": [
    {
      "id": 73,
      "category": "TOPS",
      "price": 6100
    },
    {
      "id": 74,
      "category": "OUTER",
      "price": 8100
    },
    {
      "id": 75,
      "category": "OUTER",
      "category": "PANTS",
      "price": 7100
    },
    {
      "id": 76,
      "category": "OUTER",
      "category": "SNEAKERS",
      "price": 3100
    },
    {
      "id": 77,
      "category": "OUTER",
      "category": "BAGS",
      "price": 3100
    },
    {
      "id": 78,
      "category": "OUTER",
      "category": "HATS",
      "price": 1400
    },
    {
      "id": 79,
      "category": "OUTER",
      "category": "SOCKS",
      "price": 800
    },
    {
      "id": 80,
      "category": "OUTER",
      "category": "ACCESSORIES",
      "price": 1100
    }
  ]
}

< 200 OK
{
  "code": "0000",
  "message": "Success",
  "data": {
    "id": 10,
    "name": "ZZZ",
    "products": [
      {
        "id": 73,
        "category": "TOPS",
        "price": 6100
      },
      {
        "id": 74,
        "category": "OUTER",
        "price": 8100
      },
      {
        "id": 75,
        "category": "PANTS",
        "price": 7100
      },
      {
        "id": 76,
        "category": "SNEAKERS",
        "price": 3100
      },
      {
        "id": 77,
        "category": "BAGS",
        "price": 3100
      },
      {
        "id": 78,
        "category": "HATS",
        "price": 1400
      },
      {
        "id": 79,
        "category": "SOCKS",
        "price": 800
      },
      {
        "id": 80,
        "category": "ACCESSORIES",
        "price": 1100
      }
    ]
  }
}
```

---
### Delete Brand API
`DELETE /api/v1/brands/{id}`

*브랜드/상품 삭제*

브랜드 id를 받아서 해당 브랜드 및 상품 정보를 삭제 합니다.

<h4>Parameters</h4>

|Name|In|Type|Required|Default|Description|
|---|---|---|---|---|---|
|id|path|string|true||삭제할 브랜드 id|

<h4>Responses</h4>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Success|[ResponseBrandDelete](#response-brand-delete)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad request|[ResponseBrandDelete](#response-brand-delete)|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal error|[ResponseBrandDelete](#response-brand-delete)|

<h5 id="response-brand-delete">Response Brand Delete</h5>

|Name|Type|Required|Description|
|---|---|---|---|
|code|string|true|result code|
|message|string|true|result message|


> Example

```json
> DELETE http://localhost:8080/api/v1/brands/10

< 200 OK
{
  "code": "0000",
  "message": "Success",
}
```

---
### Data Schema

<h5 id="result-product-set">Result Product Set</h5>

|Name|Type|Required|Description|
|---|---|---|---|
|category|string|false|카테고리명 (단일 카테고리 검색시)|
|lowestPrice|[CategoryProductSet](#category-product-set)|false|최저가 세트 (최저가 검색시)|
|highestPrice|[CategoryProductSet](#category-product-set)|false|최고가 세트 (최고가 검색시)|

<h5 id="category-product-set">Category Product Set</h5>

|Name|Type|Required|Description|
|---|---|---|---|
|bandName|string|false|브랜드명 (단일 브랜드 조회시)|
|products|[[CategoryProduct](#category-product)]|true|상품 리스트|
|totalPrice|integer|true|가격 총합|

<h5 id="category-product">Category Product</h5>

|Name|Type|Required|Description|
|---|---|---|---|
|category|string([CategoryCode](#category-code))|true|카테고리 코드|
|bandName|string|true|브랜드명|
|price|integer|true|가격|

<h5 id="brand">Brand</h5>

|Name|Type|Required|Description|
|---|---|---|---|
|id|long|false|브랜드 id (조회 및 업데이트시 존재)|
|name|string|true|브랜드명|
|products|[[Product](#product)]|true|상품 리스트|

<h5 id="product">Product</h5>

|Name|Type|Required|Description|
|---|---|---|---|
|id|long|false|상품 id (조회 및 업데이트시 존재)|
|category|string([CategoryCode](#category-code))|true|카테고리 코드|
|price|integer|true|가격|

<h5 id="category-code">Category Code</h5>

|Code|Description|
|---|---|
|TOPS|상의|
|OUTER|아우터|
|PANTS|바지|
|SNEAKERS|스니커즈|
|BAGS|가방|
|HATS|모자|
|SOCKS|양말|
|ACCESSORIES|악세서리|
