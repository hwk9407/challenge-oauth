# 제약조건
- Spring Security 혹은 Spring Security Oauth2 Client를 이용하지 않을 것

<br>

# 요구사항
1. 원하는 Oauth provider 를 1개 선택해 소셜 로그인 기능 개발 (카카오, 네이버, 구글, 애플 ...)
2. Accesstoken 을 사용하는 토큰인증방식의 로그인 구현 (단, RefreshToken 을 제외한 오직 AccessToken 만을 이용할 것)
3. 서버에 로그인한 사용자의 정보를 저장 (최초 소셜 로그인 시 회원가입처리)

<br>

# 추가 구현사항
1. README 에 해당 기능에 대한 시퀀스 다이어그램을 그려볼 것
2. 로그인 한 사용자만 이용할 수 있는 회원정보조회 API 개발
3. FE 화면을 연동해 소셜 로그인 사용자 시나리오 완성해보기 (X)
4. 나중에 추가 Oauth provider가 생겼을 때 내 코드가 조금 바뀌게 하면 좋은 코드!

<br>

# 시퀀스 다이어그램
![image](https://github.com/user-attachments/assets/94e49ecd-96c4-4856-b5d8-bf6bf1b234d9)
