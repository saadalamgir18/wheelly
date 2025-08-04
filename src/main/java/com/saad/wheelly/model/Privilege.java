package com.saad.wheelly.model;

import com.saad.wheelly.model.enums.PrivilegeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Privilege extends BaseModel {

    @Enumerated(EnumType.STRING)
    private PrivilegeType name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Roles> user_roles;
}
