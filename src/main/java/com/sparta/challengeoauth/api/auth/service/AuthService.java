package com.sparta.challengeoauth.api.auth.service;

import com.sparta.challengeoauth.api.auth.controller.dto.LoginRequest;
import com.sparta.challengeoauth.api.auth.controller.dto.LoginResponse;
import com.sparta.challengeoauth.api.auth.controller.dto.SignupRequest;
import com.sparta.challengeoauth.api.util.PasswordEncoder;
import com.sparta.challengeoauth.common.JwtUtil;
import com.sparta.challengeoauth.domain.user.User;
import com.sparta.challengeoauth.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public void signup(SignupRequest req) {
        // todo @원경: 이메일, 사용자이름 중복 확인 로직 필요
        User user = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .nickname(req.getNickname())
                .build();
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("잘못된 이메일입니다."));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        String token = jwtUtil.generateAccessToken(user);
        return new LoginResponse(token);
    }
}
