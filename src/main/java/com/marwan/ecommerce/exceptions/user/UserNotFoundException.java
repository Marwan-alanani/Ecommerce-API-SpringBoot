package com.marwan.ecommerce.exceptions.user;

import com.marwan.ecommerce.exceptions.abstractions.NotFoundException;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String email) {
        super("No user with the given email: " + email +" was found");
    }
}
