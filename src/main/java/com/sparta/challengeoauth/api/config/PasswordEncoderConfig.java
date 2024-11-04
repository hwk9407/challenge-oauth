package com.sparta.challengeoauth.api.config;

import com.sparta.challengeoauth.api.util.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
 PasswordEncoder 를 수동으로 등록하는 설정 클래스
 PasswordEncoder 에 @Component 를 직접 달아 자동으로 Bean 으로 등록할 수 있지만,
 이 config 을 사용하면 어떤 인스턴스를 주입할지 유연하게 결정할 수 있음!
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder();
    }
}
