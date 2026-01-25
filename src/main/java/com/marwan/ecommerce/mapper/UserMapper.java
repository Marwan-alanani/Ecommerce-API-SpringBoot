package com.marwan.ecommerce.mapper;

import com.marwan.ecommerce.controller.auth.request.LoginRequest;
import com.marwan.ecommerce.controller.auth.request.RegisterRequest;
import com.marwan.ecommerce.dto.user.AuthenticationDto;
import com.marwan.ecommerce.dto.user.UserDto;
import com.marwan.ecommerce.model.entity.User;
import com.marwan.ecommerce.service.user.query.LoginQuery;
import com.marwan.ecommerce.service.user.command.RegisterCommand;
import org.mapstruct.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper
{
    LoginQuery loginRequestToLoginQuery(LoginRequest request);

    RegisterCommand registerRequestToRegisterCommand(RegisterRequest request);

    UserDto userToUserDto(User user);

    List<UserDto> userListToUserDtoList(List<User> user);

}