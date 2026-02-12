package com.marwan.ecommerce.controller.user;

import com.marwan.ecommerce.controller.user.request.UpdateUserPasswordRequest;
import com.marwan.ecommerce.controller.user.request.UpdateUserRequest;
import com.marwan.ecommerce.dto.common.PageDto;
import com.marwan.ecommerce.dto.user.UserDto;
import com.marwan.ecommerce.exception.user.UserNotFoundException;
import com.marwan.ecommerce.mapper.UserMapper;
import com.marwan.ecommerce.model.entity.User;
import com.marwan.ecommerce.security.CustomUserDetails;
import com.marwan.ecommerce.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.marwan.ecommerce.controller.common.BaseController.toPageDto;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController
{
    private final UserService userService;
    private final UserMapper userMapper;

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remove(@PathVariable UUID userId)
            throws UserNotFoundException
    {

        userService.deactivate(userId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    ResponseEntity<UserDto> getUser(@PathVariable UUID userId)
            throws UserNotFoundException
    {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(userMapper.userToUserDto(user));
    }

    @GetMapping("/me")
    ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails)
    {

        User user = userService.getUser(userDetails.getUserId());
        return ResponseEntity.ok(userMapper.userToUserDto(user));
    }

    @DeleteMapping
    ResponseEntity<?> deleteCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        userService.deactivate(userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me")
    ResponseEntity<?> updateUser(
            @Valid @RequestBody UpdateUserRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        // I should have implemented this sooner
        throw new NotImplementedException();
    }

    @PatchMapping("/me/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void resetPassword(@Valid @RequestBody UpdateUserPasswordRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.resetPassword(userDetails.getUserId(), request.getCurrentPassword(), request.getNewPassword());
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageDto<UserDto>> getAllUsers(
            @ParameterObject @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable
    )
    {
        Page<User> userPage = userService.getAll(pageable);
        List<UserDto> userDtoList = userMapper.userListToUserDtoList(userPage.getContent());

        return ResponseEntity.ok(toPageDto(userPage, userDtoList));
    }

}
