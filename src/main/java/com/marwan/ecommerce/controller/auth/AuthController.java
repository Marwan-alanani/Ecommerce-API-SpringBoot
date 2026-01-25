package com.marwan.ecommerce.controller.auth;

import com.marwan.ecommerce.controller.auth.request.LoginRequest;
import com.marwan.ecommerce.controller.auth.request.RegisterRequest;
import com.marwan.ecommerce.dto.user.AccessAndRefreshTokenDto;
import com.marwan.ecommerce.dto.user.AuthenticationDto;
import com.marwan.ecommerce.dto.user.UserDto;
import com.marwan.ecommerce.exception.authentication.InvalidTokenException;
import com.marwan.ecommerce.exception.user.UserIdNotFoundException;
import com.marwan.ecommerce.mapper.UserMapper;
import com.marwan.ecommerce.model.entity.User;
import com.marwan.ecommerce.security.JwtSettings;
import com.marwan.ecommerce.service.user.AuthenticationService;
import com.marwan.ecommerce.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController
{
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtSettings jwtSettings;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest request)
    {
        var command = userMapper.registerRequestToRegisterCommand(request);
        User user = userService.create(command);
        return ResponseEntity
                .created(URI.create("/users/" + user.getUserId().toString()))
                .body(userMapper.userToUserDto(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response)
    {
        var query = userMapper.loginRequestToLoginQuery(request);
        AccessAndRefreshTokenDto accessAndRefreshTokenDto = authenticationService.login(query);
        setRefreshTokenCookie(response, accessAndRefreshTokenDto.refreshToken());
        return ResponseEntity.ok(new AuthenticationDto(accessAndRefreshTokenDto.accessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationDto> refresh(@CookieValue String refreshToken)
            throws InvalidTokenException, UserIdNotFoundException
    {
        String accessToken = authenticationService.renewAccessToken(refreshToken);
        return ResponseEntity.ok(new AuthenticationDto(accessToken));
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken)
    {
        var cookie = new Cookie("refresh_token", refreshToken);
        cookie.setPath("/auth/refresh");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtSettings.getRefreshTokenExpirationInSeconds());
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

}
