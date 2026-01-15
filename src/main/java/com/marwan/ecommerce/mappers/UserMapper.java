package com.marwan.ecommerce.mappers;

import com.marwan.ecommerce.dto.AuthenticationDto;
import com.marwan.ecommerce.dto.UserDto;
import com.marwan.ecommerce.models.users.entities.User;

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
                user.getEmail(),
                user.isEnabled()
        );
    }
}
