package com.sparta.challengeoauth.api.user.service;

import com.sparta.challengeoauth.api.user.controller.dto.UserResponse;
import com.sparta.challengeoauth.domain.user.User;
import com.sparta.challengeoauth.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> retrieveAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponse::from)
                .toList();
    }
}
