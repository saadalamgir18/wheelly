package com.saad.wheelly.service;


import com.saad.wheelly.dto.request.UserRequestDto;
import com.saad.wheelly.dto.response.UserResponseDto;
import jakarta.validation.Valid;

public interface UserService {
    UserResponseDto save(UserRequestDto request);
    UserResponseDto findUser(String email);
    void delete(Long id);
    UserResponseDto update(Long id,  UserRequestDto request);
}
