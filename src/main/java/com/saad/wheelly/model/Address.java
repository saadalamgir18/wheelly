package com.saad.wheelly.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity(name = "user_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends BaseModel {

    private String street;
    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String postcode;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private WheellyUsers user;
}
