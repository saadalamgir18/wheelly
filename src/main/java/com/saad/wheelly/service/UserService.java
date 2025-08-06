package com.saad.wheelly.service;


import com.saad.wheelly.dto.request.UserRegistrationRequestDto;
import com.saad.wheelly.dto.response.UserLoginResponse;
import com.saad.wheelly.dto.response.UserRegistrationResponseDto;

public interface UserService {
    UserRegistrationResponseDto save(UserRegistrationRequestDto request);
    UserLoginResponse login(String email, String password);
    void delete(Long id);
    UserRegistrationResponseDto update(Long id, UserRegistrationRequestDto request);
}
