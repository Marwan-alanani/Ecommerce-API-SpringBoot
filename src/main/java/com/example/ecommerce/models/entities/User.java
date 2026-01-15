package com.example.ecommerce.models.entities;

import com.example.ecommerce.models.enums.UserRole;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public final class User {
    @Id
    @GeneratedValue
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
            String firstName,
            String lastName,
            UserRole role,
            String email,
            String password,
            boolean isEnabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
    }


    public static User Create(String firstName, String lastName, String email, String password) {
        return new User(firstName, lastName, UserRole.Customer, email, password, true);
    }
}
