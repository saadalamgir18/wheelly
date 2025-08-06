package com.saad.wheelly.service.impl;

import com.saad.wheelly.dto.request.UserRegistrationRequestDto;
import com.saad.wheelly.dto.response.UserLoginResponse;
import com.saad.wheelly.dto.response.UserRegistrationResponseDto;
import com.saad.wheelly.model.Roles;
import com.saad.wheelly.model.WheellyUsers;
import com.saad.wheelly.model.enums.RoleType;
import com.saad.wheelly.repository.RoleRepository;
import com.saad.wheelly.repository.UserRepository;
import com.saad.wheelly.securityconfig.JwtService;
import com.saad.wheelly.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public UserRegistrationResponseDto save(UserRegistrationRequestDto request) {
       log.debug("coming request: {}" , request);
        Optional<WheellyUsers> user = userRepository.findWheellyUsersByEmail(request.email());
        if (user.isPresent()) {
            throw new IllegalStateException("User already exists with email: " + request.email());
        }


        Roles customerRole = roleRepository.findByName(RoleType.ROLE_CUSTOMER).orElseThrow(()-> new IllegalStateException("Customer role not set."));

            WheellyUsers newUser = WheellyUsers.builder()
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .userName(request.userName())
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .user_roles(List.of(customerRole))
                    .build();

            WheellyUsers dbUser =  userRepository.save(newUser);

            log.debug("saved user: {}", dbUser);

            return UserRegistrationResponseDto.toWheellyUsers(dbUser);


    }

    @Override
    public UserLoginResponse login(String email, String password) {

        userRepository.findWheellyUsersByEmail(email).orElseThrow( () -> {

            log.debug("user already exists with email or username: {}", email);

             throw new IllegalStateException("User found with email: " + email);
        });

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if (authentication.isAuthenticated()) {
            log.info("user logged in successfully");

            UserDetails user = (UserDetails) authentication.getPrincipal();

            List<String> roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            log.info("user roles: {}", roles);

            String token  = jwtService.generateJwtToken(user.getUsername(), roles);

            return UserLoginResponse.builder()
                    .token(token)
                    .build();
        }

        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserRegistrationResponseDto update(Long id, UserRegistrationRequestDto request) {
        return null;
    }
}
