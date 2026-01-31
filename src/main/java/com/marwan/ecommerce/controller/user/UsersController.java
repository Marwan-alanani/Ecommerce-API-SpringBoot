package com.marwan.ecommerce.controller.user;

import com.marwan.ecommerce.controller.common.converter.BaseController;
import com.marwan.ecommerce.controller.user.request.UpdateUserRequest;
import com.marwan.ecommerce.dto.common.PageDto;
import com.marwan.ecommerce.dto.user.UserDto;
import com.marwan.ecommerce.dto.user.UserPagingOptions;
import com.marwan.ecommerce.exception.user.UserNotFoundException;
import com.marwan.ecommerce.mapper.UserMapper;
import com.marwan.ecommerce.model.entity.User;
import com.marwan.ecommerce.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController extends BaseController
{
    private final UserService userService;
    private final UserMapper userMapper;

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> remove(@PathVariable UUID userId)
            throws UserNotFoundException
    {

        userService.deactivate(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserDto> getUser(@PathVariable UUID userId)
            throws UserNotFoundException
    {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(userMapper.userToUserDto(user));
    }

    @PutMapping
    ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest request)
    {
        // I should have implemented this sooner
        throw new NotImplementedException();
    }


    @GetMapping
    public ResponseEntity<PageDto<UserDto>> getAllUsers(
            @Valid UserPagingOptions pagingOptions
    )
    {
        Page<User> userPage = userService.getAll(pagingOptions);
        List<UserDto> userDtoList = userMapper.userListToUserDtoList(userPage.getContent());

        return ResponseEntity.ok(toPageDto(userPage, userDtoList));
    }

}
