package com.saad.wheelly.securityconfig;

import com.saad.wheelly.model.WheellyUsers;
import com.saad.wheelly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        WheellyUsers user = userRepository.findWheellyUsersByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found with username or email: " + username));


        List<GrantedAuthority> authorities = user.getUser_roles()
                .stream()
                .flatMap(role -> {
                    List<GrantedAuthority> roleAuth = new ArrayList<>();
                    roleAuth.add(new SimpleGrantedAuthority(role.getName().name()));

                    if (role.getPrivileges() != null) {
                        roleAuth.addAll(
                                role.getPrivileges().stream()
                                        .map(priv -> new SimpleGrantedAuthority(priv.getName().name())) // e.g., READ_PRIVILEGE
                                        .toList()
                        );
                    }
                    return roleAuth.stream();
                }).toList();

        return new User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
