package com.spring.springsecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@Slf4j
public class HelloController {
    @GetMapping("/hello")
    public String hello(String name, HttpServletRequest request) {
        log.info(toString());
        return "Hello " + request.getSession().getId();

    }
}
