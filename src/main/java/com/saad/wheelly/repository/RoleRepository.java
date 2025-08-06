package com.saad.wheelly.repository;

import com.saad.wheelly.model.Roles;
import com.saad.wheelly.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(RoleType name);
}
