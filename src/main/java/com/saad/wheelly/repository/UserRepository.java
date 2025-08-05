package com.saad.wheelly.repository;

import com.saad.wheelly.model.WheellyUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<WheellyUsers, Long> {
    WheellyUsers findWheellyUsersByEmail(String email);
}
