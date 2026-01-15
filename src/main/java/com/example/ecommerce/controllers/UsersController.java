package com.example.ecommerce.controllers;

import com.example.ecommerce.controllers.requests.RegisterRequest;
import com.example.ecommerce.dto.AuthenticationDto;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.services.UserService;
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
    public ResponseEntity<AuthenticationDto> register(@RequestBody RegisterRequest request) {
        AuthenticationDto user = userService.register(request);
        return ResponseEntity.ok(user);
    }

//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
//        AuthenticationResponse user = userService.login(request);
//        return ResponseEntity.ok(user);
//    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = userService.getAll();
        return ResponseEntity.ok(userDtoList);
    }
}
