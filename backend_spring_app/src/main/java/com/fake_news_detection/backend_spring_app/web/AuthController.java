package com.fake_news_detection.backend_spring_app.web;

import com.fake_news_detection.backend_spring_app.dto.LoginDto;
import com.fake_news_detection.backend_spring_app.dto.RegisterDto;
import com.fake_news_detection.backend_spring_app.model.User;
import com.fake_news_detection.backend_spring_app.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }
}