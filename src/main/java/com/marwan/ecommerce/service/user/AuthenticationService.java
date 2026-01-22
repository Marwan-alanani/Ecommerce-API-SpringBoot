package com.marwan.ecommerce.service.user;

import com.marwan.ecommerce.dto.user.AuthenticationDto;
import com.marwan.ecommerce.mapper.UserMapper;
import com.marwan.ecommerce.security.JwtService;
import com.marwan.ecommerce.service.user.query.LoginQuery;
import com.marwan.ecommerce.service.user.command.RegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService
{
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthenticationDto registerAndLogin(RegisterCommand command)
    {
        userService.create(command);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.email(), command.password())
        );

        String token = jwtService.generateToken(authentication);
        return userMapper.userDetailsToAuthenticationDto(
                (UserDetails) authentication.getPrincipal(),
                token);
    }

    public AuthenticationDto login(LoginQuery query)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(query.email(), query.password())
        );
        String token = jwtService.generateToken(authentication);
        return userMapper.userDetailsToAuthenticationDto(
                (UserDetails) authentication.getPrincipal(),
                token
        );
    }
}
