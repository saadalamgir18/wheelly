package com.saad.wheelly.service.impl;

import com.saad.wheelly.dto.request.UserRequestDto;
import com.saad.wheelly.dto.response.UserResponseDto;
import com.saad.wheelly.exceptions.AlreadyExistsException;
import com.saad.wheelly.model.Roles;
import com.saad.wheelly.model.WheellyUsers;
import com.saad.wheelly.model.enums.RoleType;
import com.saad.wheelly.repository.UserRepository;
import com.saad.wheelly.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponseDto save(UserRequestDto request) {
       log.debug("coming request: {}" , request);
        WheellyUsers user = userRepository.findWheellyUsersByEmail(request.email());
        if (user != null) {

            log.debug("user already exists with email or username: {}, {}", user.getEmail(), user.getUserName());

            throw new AlreadyExistsException("user already exists with email or username");

        }else {
            Roles roles = Roles.builder()
                    .name(RoleType.ROLE_CUSTOMER)
                    .build();
            WheellyUsers newUser = WheellyUsers.builder()
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .userName(request.userName())
                    .email(request.email())
                    .password(request.password())
                    .user_roles(List.of(roles))
                    .build();

            WheellyUsers dbUser =  userRepository.save(newUser);

            log.debug("saved user: {}", dbUser);

            return UserResponseDto.toWheellyUsers(dbUser);
        }

    }

    @Override
    public UserResponseDto findUser(String email) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto request) {
        return null;
    }
}
