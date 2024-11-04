package com.sparta.challengeoauth.api.auth.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.challengeoauth.api.auth.controller.oauth.dto.OauthUserInfo;
import com.sparta.challengeoauth.api.util.PasswordEncoder;
import com.sparta.challengeoauth.domain.oauth.OauthUser;
import com.sparta.challengeoauth.domain.oauth.OauthUserRepository;
import com.sparta.challengeoauth.domain.oauth.Provider;
import com.sparta.challengeoauth.domain.user.User;
import com.sparta.challengeoauth.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverAuthService {

    private final UserRepository userRepository;
    private final OauthUserRepository oauthUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @Value("${naver.redirect.uri}")
    private String redirectUri;

    @Transactional
    public String naverLogin(String code) throws JsonProcessingException {
        String accessToken = getToken(code);
        OauthUserInfo naverUserInfo = getNaverUserInfo(accessToken);
        Optional<OauthUser> existingOauthUser = oauthUserRepository.findByOauthIdAndProvider(
                naverUserInfo.getOauthId(),
                naverUserInfo.getProvider()
        );
        // 이미 SNS 로그인 한적 있다면 Early Return
        if (existingOauthUser.isPresent()) return "Bearer " + accessToken;

        Optional<User> existingUser = userRepository.findByNickname(naverUserInfo.getNickname());

        // fixme @원경: 동명이인의 경우 같은 유저로 간주하여 연관관계를 가지므로 다른 방식을 고민해야 함.
        // 네이버는 정책상 이메일 정보을 받을 수 없다...ㅠ!
        User user = existingUser.orElseGet(() -> {
            User newUser = User.builder()
                    .nickname(naverUserInfo.getNickname())
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .build();
            return userRepository.save(newUser);
        });

        OauthUser naverUser = OauthUser.builder()
                .oauthId(naverUserInfo.getOauthId())
                .provider(naverUserInfo.getProvider())
                .user(user)
                .build();
        oauthUserRepository.save(naverUser);

        return "Bearer " + accessToken;
    }

    private String getToken(String code) throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromUriString("https://nid.naver.com/oauth2.0/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .build()
                .toUri();

        try {
            ResponseEntity<String> res = restTemplate.postForEntity(uri, null, String.class);

            JsonNode jsonNode = objectMapper.readTree(res.getBody());

            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            log.error("네이버 엑세스 토큰을 가져오는 중 에러가 발생하였습니다.", e);
            throw new RuntimeException("네이버 엑세스 토큰을 가져오는 중 에러가 발생하였습니다", e);
        }
    }

    private OauthUserInfo getNaverUserInfo(String accessToken) {

        String url = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<JsonNode> res = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);

            JsonNode responseBody = res.getBody();
            JsonNode userInfoNode = responseBody.get("response");

            OauthUserInfo userInfo = OauthUserInfo.builder()
                    .oauthId(userInfoNode.get("id").asText())
                    .nickname(userInfoNode.get("name").asText())
                    .provider(Provider.NAVER)
                    .build();

            return userInfo;
        } catch (Exception e) {
            log.error("네이버 사용자 정보를 가져오는 중 에러가 발생하였습니다.", e);
            throw new RuntimeException("네이버 사용자 정보를 가져오는 중 에러가 발생하였습니다.", e);
        }
    }
}
