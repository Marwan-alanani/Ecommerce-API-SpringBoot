package com.marwan.ecommerce.security;

import com.marwan.ecommerce.exception.user.UserIdNotFoundException;
import com.marwan.ecommerce.domain.user.entity.User;
import com.marwan.ecommerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String idString) throws UserIdNotFoundException {
        UUID id = UUID.fromString(idString);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserIdNotFoundException(id));

        return new CustomUserDetails(user);
    }
}
