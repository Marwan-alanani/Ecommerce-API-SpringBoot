package com.marwan.ecommerce.controller;

import com.marwan.ecommerce.controller.requests.users.LoginRequest;
import com.marwan.ecommerce.controller.requests.users.RegisterRequest;
import com.marwan.ecommerce.dto.AuthenticationDto;
import com.marwan.ecommerce.dto.UserDto;
import com.marwan.ecommerce.exception.user.UserNotFoundException;
import com.marwan.ecommerce.service.UserService;
import com.marwan.ecommerce.exception.user.EmailExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationDto> register(@RequestBody RegisterRequest request)
            throws EmailExistsException {
        AuthenticationDto user = userService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/remove")
    public ResponseEntity remove(String email)
            throws UserNotFoundException {

        userService.remove(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(@RequestBody LoginRequest request) {
        AuthenticationDto user = userService.login(request);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = userService.getAll();
        return ResponseEntity.ok(userDtoList);
    }
}
