package com.saad.wheelly.dto.response;

import com.saad.wheelly.model.WheellyUsers;
import com.saad.wheelly.model.enums.Gender;
import lombok.Builder;

@Builder
public record UserRegistrationResponseDto(
        String firstName,
        String lastName,
        String userName,
        String email,
        String phone,
        int age,
        Gender gender
) {
    public static UserRegistrationResponseDto toWheellyUsers(WheellyUsers users) {
        return UserRegistrationResponseDto.builder()
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
