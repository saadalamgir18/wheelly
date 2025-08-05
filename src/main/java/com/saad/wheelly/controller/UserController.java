package com.saad.wheelly.controller;

import com.saad.wheelly.dto.request.UserRequestDto;
import com.saad.wheelly.dto.response.UserResponseDto;
import com.saad.wheelly.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRequestDto request) {
        UserResponseDto response = userService.save(request);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
