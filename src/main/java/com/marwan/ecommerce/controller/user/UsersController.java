package com.marwan.ecommerce.controller.user;

import com.marwan.ecommerce.controller.user.request.UpdateUserRequest;
import com.marwan.ecommerce.dto.user.UserDto;
import com.marwan.ecommerce.exception.user.UserIdNotFoundException;
import com.marwan.ecommerce.mapper.UserMapper;
import com.marwan.ecommerce.model.entity.User;
import com.marwan.ecommerce.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController
{
    private final UserService userService;
    private final UserMapper userMapper;

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> remove(@PathVariable UUID userId)
            throws UserIdNotFoundException
    {

        userService.deactivate(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserDto> getUser(@PathVariable UUID userId)
            throws UserIdNotFoundException
    {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(userMapper.userToUserDto(user));
    }

    @PutMapping
    ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest request)
    {
        throw new NotImplementedException();
    }


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        List<User> userList = userService.getAll();
        List<UserDto> userDtoList = userMapper.userListToUserDtoList(userList);
        return ResponseEntity.ok(userDtoList);
    }

}
