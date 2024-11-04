package com.sparta.challengeoauth.api.auth.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {
    private String email;
    private String password;
    private String nickname;
}
