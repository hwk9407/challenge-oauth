package com.sparta.challengeoauth.api.auth.controller;

import com.sparta.challengeoauth.api.auth.controller.dto.LoginRequest;
import com.sparta.challengeoauth.api.auth.controller.dto.LoginResponse;
import com.sparta.challengeoauth.api.auth.controller.dto.SignupRequest;
import com.sparta.challengeoauth.api.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;



    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequest req) {
        authService.signup(req);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        LoginResponse res = authService.login(req);
        return ResponseEntity.ok(res);
    }
}
