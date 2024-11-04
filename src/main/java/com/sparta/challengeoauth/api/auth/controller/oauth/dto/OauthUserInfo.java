package com.sparta.challengeoauth.api.auth.controller.oauth.dto;

import com.sparta.challengeoauth.domain.oauth.Provider;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OauthUserInfo {

    private String oauthId;
    private String nickname;
    private Provider provider;
}
