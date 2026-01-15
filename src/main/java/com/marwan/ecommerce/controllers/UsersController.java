package com.marwan.ecommerce.controllers;

import com.marwan.ecommerce.controllers.requests.RegisterRequest;
import com.marwan.ecommerce.dto.AuthenticationDto;
import com.marwan.ecommerce.dto.UserDto;
import com.marwan.ecommerce.errors.exceptions.user.UserNotFoundException;
import com.marwan.ecommerce.services.UserService;
import com.marwan.ecommerce.errors.exceptions.user.EmailExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity remove(UUID userId)
            throws UserNotFoundException {

        userService.remove(userId);
        return ResponseEntity.ok().build();
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
