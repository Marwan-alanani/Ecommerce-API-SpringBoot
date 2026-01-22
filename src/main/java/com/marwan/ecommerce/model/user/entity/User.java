package com.marwan.ecommerce.model.user.entity;

import com.marwan.ecommerce.model.user.enums.UserRole;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "[user]")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private Date createdDateTime;

    @Setter(AccessLevel.PRIVATE)
    @Column(nullable = false)
    private Date updatedDateTime;

    private User(
            UUID userId,
            String firstName,
            String lastName,
            UserRole role,
            String email,
            String password,
            boolean isEnabled,
            Date createdDateTime,
            Date updatedDateTime)
    {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }


    public static User create(
            String firstName,
            String lastName,
            UserRole role,
            String email,
            String password)
    {
        Date currentDate = new Date();
        return new User(UUID.randomUUID(),
                        firstName,
                        lastName,
                        role,
                        email,
                        password,
                        true,
                        currentDate,
                        currentDate);
    }
}
