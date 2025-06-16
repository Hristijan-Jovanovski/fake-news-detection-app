package com.fake_news_detection.backend_spring_app.service;

import com.fake_news_detection.backend_spring_app.dto.LoginDto;
import com.fake_news_detection.backend_spring_app.dto.RegisterDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> register(RegisterDto registerDto);
    ResponseEntity<?> login(LoginDto loginDto);
}
