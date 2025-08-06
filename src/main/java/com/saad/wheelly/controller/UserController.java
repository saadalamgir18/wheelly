package com.saad.wheelly.controller;

import com.saad.wheelly.dto.request.UserLoginRequest;
import com.saad.wheelly.dto.request.UserRegistrationRequestDto;
import com.saad.wheelly.dto.response.UserLoginResponse;
import com.saad.wheelly.dto.response.UserRegistrationResponseDto;
import com.saad.wheelly.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponseDto> register(@RequestBody @Valid UserRegistrationRequestDto request) {
        UserRegistrationResponseDto response = userService.save(request);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request) {
        log.info("login request: {}", request);
        UserLoginResponse response = userService.login(request.email(), request.password());
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
