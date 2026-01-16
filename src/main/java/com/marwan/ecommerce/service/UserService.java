package com.marwan.ecommerce.service;

import com.marwan.ecommerce.controller.requests.users.RegisterRequest;
import com.marwan.ecommerce.domain.user.enums.UserRole;
import com.marwan.ecommerce.dto.UserDto;
import com.marwan.ecommerce.mapper.UserMapper;
import com.marwan.ecommerce.domain.user.entity.User;
import com.marwan.ecommerce.repository.UserRepository;
import com.marwan.ecommerce.exception.user.EmailExistsException;
import com.marwan.ecommerce.exception.user.UserIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(RegisterRequest request)
            throws EmailExistsException {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailExistsException(request.email());
        }
        User user = User.Create(
                request.firstName(),
                request.lastName(),
                UserRole.USER,
                request.email(),
                passwordEncoder.encode(request.password())
        );
        userRepository.save(user);
        return user;
    }

    public void deactivate(UUID id) throws UserIdNotFoundException {
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
}
