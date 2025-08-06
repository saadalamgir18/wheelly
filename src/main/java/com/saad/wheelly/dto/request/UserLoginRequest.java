package com.saad.wheelly.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserLoginRequest(
        @NotBlank(message = "email is required")
        String email,
        @NotBlank(message = "password is required")
        String password) {
}
