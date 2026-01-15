package com.example.ecommerce.services;

import com.example.ecommerce.controllers.requests.RegisterRequest;
import com.example.ecommerce.dto.AuthenticationDto;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.mappers.UserMapper;
import com.example.ecommerce.models.entities.User;
import com.example.ecommerce.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
//    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
//        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationDto register(RegisterRequest request) {
        User user = User.Create(
                request.firstName(),
                request.lastName(),
                request.email(),
                passwordEncoder.encode(request.password())
        );
        userRepository.save(user);
        return UserMapper.mapToAuthenticationDto(user, "");
    }

    public void remove(UUID id) {
        userRepository.deleteById(id);
    }

    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        users.forEach(user -> userDtoList.add(UserMapper.mapToUserDto(user)));
        return userDtoList;
    }

//    public AuthenticationResponse login(LoginRequest request) {
////        Optional<User> user = userRepository.findByEmail(request.email());
////        if (user.isEmpty()) {
////            throw new Error("Invalid user id");
////        }
////        if (!passwordEncoder.matches(request.password(), user.get().getPassword())) {
////            throw new Error("Invalid Credentials");
////        }
////        return UserMapper.mapToAuthenticationResponse(user.get(), "");
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.email(),
//                        request.password()
//                )
//        );
//    }

}
