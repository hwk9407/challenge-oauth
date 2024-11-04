package com.sparta.challengeoauth.domain.oauth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthUserRepository extends JpaRepository<OauthUser, Long> {
    Optional<OauthUser> findByOauthIdAndProvider(String oauthId, Provider provider);
}
