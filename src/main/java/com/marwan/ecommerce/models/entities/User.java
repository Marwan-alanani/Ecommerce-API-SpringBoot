package com.marwan.ecommerce.models.entities;
import com.marwan.ecommerce.models.enums.UserRole;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public final class User {
    @Id
    @Setter(AccessLevel.NONE) // Can't set id
    private UUID id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean isEnabled;

    private User(
            UUID id,
            String firstName,
            String lastName,
            UserRole role,
            String email,
            String password,
            boolean isEnabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
    }


    public static User Create(
            String firstName,
            String lastName,
            String email,
            String password) {
        return new User(UUID.randomUUID(),
                firstName,
                lastName,
                UserRole.Customer,
                email,
                password,
                true);
    }
}
