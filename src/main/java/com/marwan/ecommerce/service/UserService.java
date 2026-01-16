package com.marwan.ecommerce.service;

import com.marwan.ecommerce.controller.requests.users.LoginRequest;
import com.marwan.ecommerce.controller.requests.users.RegisterRequest;
import com.marwan.ecommerce.domain.user.enums.UserRole;
import com.marwan.ecommerce.dto.AuthenticationDto;
import com.marwan.ecommerce.dto.UserDto;
import com.marwan.ecommerce.exception.user.InvalidCredentialsException;
import com.marwan.ecommerce.mapper.UserMapper;
import com.marwan.ecommerce.domain.user.entity.User;
import com.marwan.ecommerce.repository.UserRepository;
import com.marwan.ecommerce.exception.user.EmailExistsException;
import com.marwan.ecommerce.exception.user.UserIdNotFoundException;
import com.marwan.ecommerce.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    //    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationDto register(RegisterRequest request)
            throws EmailExistsException {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailExistsException(request.email());
        }
        User user = User.Create(
                request.firstName(),
                request.lastName(),
                UserRole.Customer,
                request.email(),
                passwordEncoder.encode(request.password())
        );
        userRepository.save(user);
        String token = jwtService.generate(user);
        return UserMapper.mapToAuthenticationDto(user, token);
    }

    public void remove(UUID id) throws UserIdNotFoundException {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserIdNotFoundException(id));
        user.setEnabled(false);
    }

    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        users.forEach(user -> userDtoList.add(UserMapper.mapToUserDto(user)));
        return userDtoList;
    }

    public AuthenticationDto login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        String token = jwtService.generate(user);
        return UserMapper.mapToAuthenticationDto(user, token);
    }

}
