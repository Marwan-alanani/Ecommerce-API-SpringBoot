package com.marwan.ecommerce.service.user;

import com.marwan.ecommerce.model.user.enums.UserRole;
import com.marwan.ecommerce.dto.user.UserDto;
import com.marwan.ecommerce.mapper.UserMapper;
import com.marwan.ecommerce.model.user.entity.User;
import com.marwan.ecommerce.repository.UserRepository;
import com.marwan.ecommerce.exception.user.EmailExistsException;
import com.marwan.ecommerce.exception.user.UserIdNotFoundException;
import com.marwan.ecommerce.service.user.command.RegisterCommand;
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
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(RegisterCommand command)
            throws EmailExistsException
    {
        if (userRepository.findByEmail(command.email()).isPresent()) {
            throw new EmailExistsException(command.email());
        }
        User user = User.create(
                command.firstName(),
                command.lastName(),
                UserRole.USER,
                command.email(),
                passwordEncoder.encode(command.password()));

        userRepository.save(user);
        return user;
    }

    public void deactivate(UUID id) throws UserIdNotFoundException
    {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserIdNotFoundException(id));
        user.setEnabled(false);
    }

    public List<User> getAll()
    {
        return userRepository.findAll();
    }
}
