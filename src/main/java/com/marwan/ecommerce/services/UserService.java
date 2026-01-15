package com.marwan.ecommerce.services;

import com.marwan.ecommerce.controllers.requests.RegisterRequest;
import com.marwan.ecommerce.dto.AuthenticationDto;
import com.marwan.ecommerce.dto.UserDto;
import com.marwan.ecommerce.mappers.UserMapper;
import com.marwan.ecommerce.models.entities.User;
import com.marwan.ecommerce.repositories.UserRepository;
import com.marwan.ecommerce.errors.exceptions.user.EmailExistsException;
import com.marwan.ecommerce.errors.exceptions.user.UserNotFoundException;
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

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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
        return UserMapper.mapToAuthenticationDto(user, "");
    }

    public void remove(UUID id) throws UserNotFoundException {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(id));
        user.setEnabled(false);
        userRepository.save(user);
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
