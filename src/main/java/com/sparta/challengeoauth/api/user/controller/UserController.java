package com.sparta.challengeoauth.api.user.controller;

import com.sparta.challengeoauth.api.user.controller.dto.UserResponse;
import com.sparta.challengeoauth.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    @GetMapping("/user")
    public ResponseEntity<List<UserResponse>> retrieveAllUser() {
        List<UserResponse> res = userService.retrieveAllUser();
        return ResponseEntity.ok().body(res);
    }
}
