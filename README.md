PayWith DB 연동 REST API서버
==========================

Naming Rule
-----------
## API URL
1. relay, api, url 등에서 brand는 우선
> brand/{brandCd}/prpay/~
2. GET / POST / UPDATE / DELETE 기능 사용 (클라이언트 단의 RestTemplate 에서 미지원 이유로 PATCH는 추후 도입)

## Java Function
1. repository 기능의 파라미터 순서 : brand는 마지막(기능 사용 시 brand는 공통으로 들어가므로..)
> function void get(int a, String b, String brandCd)
> function void get(int a, String b, Brand brand)
