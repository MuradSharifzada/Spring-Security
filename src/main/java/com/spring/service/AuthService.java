package com.spring.service;

import com.spring.dto.LoginDto;
import com.spring.dto.RegisterDto;
import com.spring.model.User;

public interface AuthService {

     User register(RegisterDto request);

     User login(LoginDto request);
}
