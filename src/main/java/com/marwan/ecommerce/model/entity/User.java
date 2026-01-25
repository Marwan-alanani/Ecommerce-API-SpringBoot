package com.marwan.ecommerce.model.entity;

import com.marwan.ecommerce.model.enums.UserRole;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "[user]")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public final class User
{
    @Id
    @Setter(AccessLevel.NONE) // Can't set id
    private UUID userId;
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

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDateTime;

    @Column(nullable = false)
    private LocalDateTime updatedDateTime;


    public static User create(
            String firstName,
            String lastName,
            UserRole role,
            String email,
            String password)
    {
        LocalDateTime now = LocalDateTime.now();
        return new User(UUID.randomUUID(),
                firstName,
                lastName,
                role,
                email,
                password,
                true,
                now,
                now);
    }
}
