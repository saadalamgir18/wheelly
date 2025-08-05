package com.saad.wheelly.model;

import com.saad.wheelly.model.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Roles extends BaseModel {

    @Enumerated(EnumType.STRING)
    private RoleType name;
    @ManyToMany(mappedBy = "user_roles")
    private Collection<WheellyUsers> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;
}
