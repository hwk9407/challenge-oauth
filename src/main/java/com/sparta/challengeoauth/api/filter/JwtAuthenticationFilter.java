package com.sparta.challengeoauth.api.filter;

import com.sparta.challengeoauth.common.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        if (!this.isApplicable(req)) {
            chain.doFilter(req, res);
            return;
        }
        String accessToken = Optional.ofNullable(req.getHeader("Authorization"))
                .map(header -> header.substring("Bearer ".length()))
                .orElseThrow(() -> new JwtException("잘못된 액세스 토큰입니다."));
        jwtUtil.validate(accessToken);
        chain.doFilter(req, res);
    }

    public boolean isApplicable(HttpServletRequest req) {
        return req.getRequestURI().startsWith("/api") && !req.getRequestURI().startsWith("/api/auth");
    }
}
