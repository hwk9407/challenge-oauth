package com.sparta.challengeoauth.api.user.controller.dto;

import com.sparta.challengeoauth.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {
    private Long userid;
    private String nickname;
    private String email;

    public static UserResponse from(User user) {
        // 튜터님이 Record?로 UserResponse 객체 구현하신게 어떻게 하는 건지 몰라 비슷하게 따라해봤습니다.
        UserResponse userResponse = new UserResponse();
        userResponse.userid = user.getId();
        userResponse.nickname = user.getNickname();
        userResponse.email = user.getEmail();
        return userResponse;
    }
}
