package com.spring.service.impl;

import com.spring.dto.LoginDto;
import com.spring.dto.RegisterDto;
import com.spring.enums.Role;
import com.spring.exception.InvalidCredentialsException;
import com.spring.exception.UserNotFoundException;
import com.spring.repository.UserRepository;
import com.spring.service.AuthService;
import com.spring.exception.UsernameAlreadyTakenException;
import com.spring.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyTakenException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        user.setRole(Role.USER);

        userRepository.save(user);

        return user;
    }

    @Override
    public User login(LoginDto request) {

        if (!userRepository.existsByUsername(request.getUsername())) {
            log.error("{} doesn't exist in database", request.getUsername());
            throw new UserNotFoundException("Given user doesn't exist in database");
        }

        User user = userRepository.findByUsername(request.getUsername());

        log.info("User with username {} retrieved from the database", request.getUsername());

        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return user;
        } else {
            log.error("Invalid password for user {}", request.getUsername());
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }
}
