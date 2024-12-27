package com.spring.controller;

import com.spring.dto.LoginDto;
import com.spring.dto.RegisterDto;
import com.spring.model.User;
import com.spring.repository.UserRepository;
import com.spring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/auth/register")
    public ResponseEntity<User> register(@RequestBody RegisterDto request) {

        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @GetMapping("/auth/login")
    public ResponseEntity<User> login(@RequestBody LoginDto request) {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }
}
