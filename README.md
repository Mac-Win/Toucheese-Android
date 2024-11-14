# 패키지 구조
```
com.example.toucheeseapp
│
├── data
│   ├── model          # 데이터 모델 (Entity, DTO 등)
│   ├── repository     # Repository 인터페이스 및 구현체
│   ├── source         # 데이터 소스 (Remote, Local 등)
│   │   ├── remote     # 원격 데이터 소스 (API, Retrofit 등)
│   │   └── local      # 로컬 데이터 소스 (Room DB, SharedPreferences 등)
│   └── mapper         # 데이터 변환 (예: Entity <-> DTO 변환)
│
├── domain
│   ├── model          # 도메인 모델
│   ├── repository     # Repository 인터페이스
│   └── usecase        # UseCase (비즈니스 로직 처리)
│
├── presentation       # UI 관련 패키지
│   ├── ui             # 각 화면의 UI 패키지
│   │   ├── feature1   # 특정 화면(Feature1)에 대한 Composable, State, Event 클래스 등
│   │   └── feature2   # 특정 화면(Feature2)에 대한 Composable, State, Event 클래스 등
│   ├── viewmodel      # ViewModel 클래스
│   └── navigation     # 화면 간 Navigation 처리
│
└── di                 # 의존성 주입 모듈 (예: Hilt 모듈)
```