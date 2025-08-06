package com.saad.wheelly.repository;

import com.saad.wheelly.model.WheellyUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<WheellyUsers, Long> {
    Optional<WheellyUsers> findWheellyUsersByEmail(String email);
}
