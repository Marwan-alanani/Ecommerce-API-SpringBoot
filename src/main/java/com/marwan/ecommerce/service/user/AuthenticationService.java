package com.marwan.ecommerce.service.user;

import com.marwan.ecommerce.dto.user.AccessAndRefreshTokenDto;
import com.marwan.ecommerce.exception.authentication.InvalidTokenException;
import com.marwan.ecommerce.exception.user.UserNotFoundException;
import com.marwan.ecommerce.model.entity.User;
import com.marwan.ecommerce.security.CustomUserDetails;
import com.marwan.ecommerce.security.JwtService;
import com.marwan.ecommerce.service.user.query.LoginQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AuthenticationService
{
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public AccessAndRefreshTokenDto login(LoginQuery query)
    {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(query.email(), query.password())
        );
        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();

        String accessToken = jwtService.generateAccessToken(customUserDetails);
        String refreshToken = jwtService.generateRefreshToken(customUserDetails);

        return new AccessAndRefreshTokenDto(accessToken, refreshToken);
    }

    public AccessAndRefreshTokenDto renewAccessToken(String refreshToken)
            throws InvalidTokenException, UserNotFoundException
    {
        if (refreshToken == null || !jwtService.isValidRefreshToken(refreshToken)) {
            throw new InvalidTokenException(refreshToken);
        }
        User user = userService.getUser(jwtService.extractUserId(refreshToken));
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        var access = jwtService.generateAccessToken(customUserDetails);
        var refresh = jwtService.generateRefreshToken(customUserDetails);
        return new AccessAndRefreshTokenDto(access, refresh);
    }
}