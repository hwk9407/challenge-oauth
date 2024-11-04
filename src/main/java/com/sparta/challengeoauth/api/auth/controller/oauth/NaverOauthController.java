package com.sparta.challengeoauth.api.auth.controller.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.challengeoauth.api.auth.service.oauth.NaverAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/naver")
public class NaverOauthController {

    private final NaverAuthService naverAuthService;

    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> redirectToNaverLogin(
            @Value("${naver.client.id}") String clientId,
            @Value("${naver.auth.url}") String authUrl,
            @Value("${naver.redirect.uri}") String redirectUri
    ) {
        String redirectUrl = authUrl + "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri;

        Map<String, String> response = new HashMap<>();
        response.put("naverLoginUrl", redirectUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/callback")
    public String naverLogin(@RequestParam("code") String code) throws JsonProcessingException {
        String token = naverAuthService.naverLogin(code);

        return token;
    }
}
