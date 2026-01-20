package com.marwan.ecommerce.controller.user;

import com.marwan.ecommerce.controller.user.request.LoginRequest;
import com.marwan.ecommerce.controller.user.request.RegisterRequest;
import com.marwan.ecommerce.dto.user.AuthenticationDto;
import com.marwan.ecommerce.dto.user.UserDto;
import com.marwan.ecommerce.exception.user.UserIdNotFoundException;
import com.marwan.ecommerce.mapper.UserMapper;
import com.marwan.ecommerce.model.user.entity.User;
import com.marwan.ecommerce.service.user.AuthenticationService;
import com.marwan.ecommerce.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UsersController
{
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationDto register(@Valid @RequestBody RegisterRequest request)
    {

        return authenticationService.registerAndLogin(
                UserMapper.mapRegisterRequestToRegisterCommand(request)
                                                     );
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(UUID id)
            throws UserIdNotFoundException
    {

        userService.deactivate(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(@Valid @RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authenticationService.login(
                UserMapper.mapLoginRequestToLoginQuery(request)
                                                            ));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        List<User> userList = userService.getAll();
        List<UserDto> userDtoList = new ArrayList<>();
        userList.forEach(user -> userDtoList.add(UserMapper.mapUserToUserDto(user)));
        return ResponseEntity.ok(userDtoList);
    }
}
