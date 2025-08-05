package com.saad.wheelly.dto.response;

import com.saad.wheelly.model.WheellyUsers;
import com.saad.wheelly.model.enums.Gender;
import lombok.Builder;

@Builder
public record UserResponseDto(
        String firstName,
        String lastName,
        String userName,
        String email,
        String phone,
        int age,
        Gender gender
) {
    public static UserResponseDto toWheellyUsers(WheellyUsers users) {
        return UserResponseDto.builder()
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .userName(users.getUserName())
                .email(users.getEmail())
                .phone(users.getPhone())
                .age(users.getAge())
                .gender(users.getGender())
                .build();
    }
}
