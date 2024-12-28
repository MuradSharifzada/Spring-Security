package com.spring.controller;

import com.spring.dto.LoginDto;
import com.spring.dto.RegisterDto;
import com.spring.model.User;
import com.spring.repository.UserRepository;
import com.spring.security.JwtService;
import com.spring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/auth/register")
    public ResponseEntity<User> register(@RequestBody RegisterDto request) {

        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @GetMapping("/auth/login")
    public String login(@RequestBody LoginDto request) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.getUsername());
        } else
            return "Login Failed";


    }
}
