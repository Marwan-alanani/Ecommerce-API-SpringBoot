package com.marwan.ecommerce.service.user;

import com.marwan.ecommerce.exception.user.InvalidPasswordException;
import com.marwan.ecommerce.model.enums.UserRole;
import com.marwan.ecommerce.model.entity.User;
import com.marwan.ecommerce.repository.UserRepository;
import com.marwan.ecommerce.exception.user.EmailExistsException;
import com.marwan.ecommerce.exception.user.UserNotFoundException;
import com.marwan.ecommerce.service.user.command.RegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User create(RegisterCommand command)
            throws EmailExistsException
    {
        if (userRepository.existsByEmail(command.email())) {
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

    @Transactional
    public void deactivate(UUID id) throws UserNotFoundException
    {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(id));
        user.deactivate();
        userRepository.save(user);
    }

    public Page<User> getAll(Pageable pageable)
    {
        return userRepository.findAll(pageable);
    }

    public User getUser(UUID id)
            throws UserNotFoundException
    {
        return userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(id));
    }

    public boolean userExists(UUID id)
    {
        return userRepository.existsByUserIdAndIsEnabled(id, true);
    }

    @Transactional
    public void resetPassword(UUID id, String currentPassword, String newPassword) {
        userRepository.findById(id)
            .map(user -> {
                if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                    throw new InvalidPasswordException();
                }
                user.setPassword(passwordEncoder.encode(newPassword));
                return userRepository.save(user);
            })
            .orElseThrow(() -> new UserNotFoundException(id));
    }

}
