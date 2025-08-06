package com.saad.wheelly.dto.response;

import lombok.Builder;

@Builder
public record UserLoginResponse(String token) {
}
