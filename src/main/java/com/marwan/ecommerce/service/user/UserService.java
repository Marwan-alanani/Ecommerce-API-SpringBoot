package com.marwan.ecommerce.service.user;

import com.marwan.ecommerce.dto.user.UserPagingOptions;
import com.marwan.ecommerce.model.enums.UserRole;
import com.marwan.ecommerce.model.entity.User;
import com.marwan.ecommerce.repository.UserRepository;
import com.marwan.ecommerce.exception.user.EmailExistsException;
import com.marwan.ecommerce.exception.user.UserNotFoundException;
import com.marwan.ecommerce.service.common.BaseService;
import com.marwan.ecommerce.service.user.command.RegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
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

    @Transactional
    public void deactivate(UUID id) throws UserNotFoundException
    {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException(id));
        user.setEnabled(false);
    }

    public Page<User> getAll(UserPagingOptions pagingOptions)
    {
        var pageable = constructPageable(pagingOptions);
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
}
