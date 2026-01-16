package com.marwan.ecommerce.controller;

import com.marwan.ecommerce.controller.requests.users.LoginRequest;
import com.marwan.ecommerce.controller.requests.users.RegisterRequest;
import com.marwan.ecommerce.dto.AuthenticationDto;
import com.marwan.ecommerce.dto.UserDto;
import com.marwan.ecommerce.exception.user.UserIdNotFoundException;
import com.marwan.ecommerce.security.JwtService;
import com.marwan.ecommerce.service.AuthService;
import com.marwan.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UsersController {
    private final UserService userService;
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationDto register(@RequestBody RegisterRequest request) {
        return authService.registerAndLogin(request);
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(UUID id)
            throws UserIdNotFoundException {

        userService.deactivate(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = userService.getAll();
        return ResponseEntity.ok(userDtoList);
    }
}
