package com.example.ecommerce.mappers;

import com.example.ecommerce.dto.AuthenticationDto;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.models.entities.User;

public class UserMapper {
    public static AuthenticationDto mapToAuthenticationDto(User user, String token) {
        return new AuthenticationDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                token
        );
    }

    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
