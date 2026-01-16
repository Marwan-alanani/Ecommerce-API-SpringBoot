package com.marwan.ecommerce.service;

import com.marwan.ecommerce.controller.requests.users.LoginRequest;
import com.marwan.ecommerce.controller.requests.users.RegisterRequest;
import com.marwan.ecommerce.dto.AuthenticationDto;
import com.marwan.ecommerce.dto.UserDto;
import com.marwan.ecommerce.exception.user.InvalidCredentialsException;
import com.marwan.ecommerce.mapper.UserMapper;
import com.marwan.ecommerce.domain.users.entities.User;
import com.marwan.ecommerce.repository.UserRepository;
import com.marwan.ecommerce.exception.user.EmailExistsException;
import com.marwan.ecommerce.exception.user.UserNotFoundException;
import com.marwan.ecommerce.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    //    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationDto register(RegisterRequest request)
            throws EmailExistsException {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailExistsException(request.email());
        }
        User user = User.Create(
                request.firstName(),
                request.lastName(),
                request.email(),
                passwordEncoder.encode(request.password())
        );
        userRepository.save(user);
        String token = jwtService.generate(user);
        return UserMapper.mapToAuthenticationDto(user, token);
    }

    public void remove(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new UserNotFoundException(email));
        user.setEnabled(false);
        userRepository.save(user);
    }

    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        users.forEach(user -> userDtoList.add(UserMapper.mapToUserDto(user)));
        return userDtoList;
    }

    public AuthenticationDto login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UserNotFoundException(loginRequest.email()));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        String token = jwtService.generate(user);
        return UserMapper.mapToAuthenticationDto(user, token);
    }


}
