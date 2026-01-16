package com.marwan.ecommerce.service;

import com.marwan.ecommerce.controller.requests.users.LoginRequest;
import com.marwan.ecommerce.controller.requests.users.RegisterRequest;
import com.marwan.ecommerce.domain.user.entity.User;
import com.marwan.ecommerce.dto.AuthenticationDto;
import com.marwan.ecommerce.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthenticationDto registerAndLogin(RegisterRequest request) {
        User user = userService.create(request);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        String token = jwtService.generateToken(authentication);
        return new AuthenticationDto(token);
    }

    public AuthenticationDto login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        String token = jwtService.generateToken(auth);
        return new AuthenticationDto(token);
    }


}
