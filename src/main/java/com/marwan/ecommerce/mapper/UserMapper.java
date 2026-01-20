package com.marwan.ecommerce.mapper;
import com.marwan.ecommerce.controller.user.request.LoginRequest;
import com.marwan.ecommerce.controller.user.request.RegisterRequest;
import com.marwan.ecommerce.dto.user.AuthenticationDto;
import com.marwan.ecommerce.dto.user.UserDto;
import com.marwan.ecommerce.model.user.entity.User;
import com.marwan.ecommerce.service.user.query.LoginQuery;
import com.marwan.ecommerce.service.user.command.RegisterCommand;
import org.springframework.security.core.userdetails.UserDetails;

public class UserMapper
{
    public static AuthenticationDto mapUserDetailsToAuthenticationDto(
            UserDetails user,
            String token)
    {
        return new AuthenticationDto(
                user.getUsername(),
                user.getAuthorities().stream().findFirst().get().getAuthority(),
                token
        );
    }

    public static UserDto mapUserToUserDto(User user)
    {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isEnabled()
        );
    }

    public static LoginQuery mapLoginRequestToLoginQuery(LoginRequest request)
    {
        return new LoginQuery(request.email(), request.password());
    }

    public static RegisterCommand mapRegisterRequestToRegisterCommand(RegisterRequest request)
    {
        return new RegisterCommand(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.password()
        );
    }
}
